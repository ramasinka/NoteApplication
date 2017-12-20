package noteapp.voltasit.lt.noteapplication.views;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import noteapp.voltasit.lt.noteapplication.R;
import noteapp.voltasit.lt.noteapplication.adapters.NotesAdapter;
import noteapp.voltasit.lt.noteapplication.decorations.DividerItemDecorate;
import noteapp.voltasit.lt.noteapplication.model.Note;
import noteapp.voltasit.lt.noteapplication.views.activity.NewNoteActivity;

/**
 * Created by Romas Noreika on 2017-12-17.
 */

public class NotesView extends AppCompatActivity {
    private List<Note> notesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NotesAdapter notesAdapter;
    private GoogleApiClient mGoogleApiClient;
    private FloatingActionButton createNoteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.activity_main);

        notesList = new ArrayList<>();
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        createNoteBtn = findViewById(R.id.action_create_note);
        recyclerView = findViewById(R.id.recycler_view);

        notesAdapter = new NotesAdapter(notesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(notesAdapter);

        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.item_decorator);

        recyclerView.addItemDecoration(new DividerItemDecorate(dividerDrawable));
        refreshNotesList();
        setProgressBarIndeterminateVisibility(false);

        createNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewNoteActivity.class);
                intent.putExtra("email", getIntent().getStringExtra("email"));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            loadLoginView();
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case R.id.action_refresh: {
                refreshNotesList();
                break;
            }

            case R.id.action_logout: {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                loadLoginView();
                            }
                        });
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void loadLoginView() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void refreshNotesList() {
        final String email = getIntent().getStringExtra("email");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("NotesParse");
        setProgressBarIndeterminateVisibility(true);

        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> postList, ParseException e) {
                if (e == null) {
                    notesList.clear();
                    for (ParseObject post : postList) {
                        if (post.get("email").equals(email)) {
                            Note note = new Note(post.getObjectId(), post.getString("title"), post.getString("content"));
                            notesList.add(note);
                        }
                    }
                    notesAdapter.notifyDataSetChanged();
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });
    }
}
