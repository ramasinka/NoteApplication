package noteapp.voltasit.lt.noteapplication.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import noteapp.voltasit.lt.noteapplication.R;
import noteapp.voltasit.lt.noteapplication.adapters.NotesAdapter;
import noteapp.voltasit.lt.noteapplication.model.Note;

/**
 * Created by Romas Noreika on 2017-12-17.
 */

public class NotesView extends AppCompatActivity {
    private List<Note> notesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.activity_main);

        notesList = new ArrayList<>();
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view);

        notesAdapter = new NotesAdapter(notesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(notesAdapter);

        refreshNotesList();
        setProgressBarIndeterminateVisibility(false);
    }

    private void refreshNotesList() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("NotesParse");
        setProgressBarIndeterminateVisibility(true);

        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> postList, ParseException e) {
                if (e == null) {
                    // If there are results, update the list of posts
                    // and notify the adapter
                    notesList.clear();
                    for (ParseObject post : postList) {
                        Note note = new Note(post.getObjectId(), post.getString("title"), post.getString("content"));
                        notesList.add(note);
                    }
                    notesAdapter.notifyDataSetChanged();
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });
    }

    private void prepareNotesData() {
//        Note note = new Note(post.getObjectId(), "test", "test");
//        notesList.add(note);
//
//        note = new Note(post.getObjectId(), "Inside Out", "Animation, Kids & Family");
//        notesList.add(note);
//
//        notesAdapter.notifyDataSetChanged();
    }


}
