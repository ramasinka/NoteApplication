package noteapp.voltasit.lt.noteapplication.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import noteapp.voltasit.lt.noteapplication.R;
import noteapp.voltasit.lt.noteapplication.model.Note;
import noteapp.voltasit.lt.noteapplication.views.NotesView;

/**
 * Created by Romas Noreika on 2017-12-17.
 */

public class EditNoteActivity extends AppCompatActivity {
    private Note note;
    private EditText titleEditText;
    private EditText contentEditText;
    private String postTitle;
    private String postContent;
    private Button saveNoteButton;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        Intent intent = this.getIntent();

        titleEditText = findViewById(R.id.noteTitle);
        contentEditText = findViewById(R.id.noteContent);

        if (intent.getExtras() != null) {
            String noteId = (String) intent.getExtras().get("noteId");
            String noteTitle = intent.getExtras().getString("noteTitle");
            String noteContent = intent.getExtras().getString("noteContent");
            email = intent.getExtras().getString("email");
            note = new Note(noteId, noteTitle, noteContent);

            titleEditText.setText(note.getTitle());
            contentEditText.setText(note.getContent());
        }

        saveNoteButton = findViewById(R.id.saveNote);
        saveNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void saveNote() {

        postTitle = titleEditText.getText().toString();
        postContent = contentEditText.getText().toString();

        postTitle = postTitle.trim();
        postContent = postContent.trim();

        if (!postTitle.isEmpty()) {
            if (note.getId() == null) {
                ParseObject post = new ParseObject("NotesParse");
                post.put("title", postTitle);
                post.put("content", postContent);
                post.put("email", email);
                post.saveInBackground(new SaveCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            loadNotesView();
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to Save", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("NotesParse");
                query.getInBackground(note.getId(), new GetCallback<ParseObject>() {
                    public void done(ParseObject post, ParseException e) {
                        if (e == null) {
                            post.put("title", postTitle);
                            post.put("content", postContent);
                            post.saveInBackground(new SaveCallback() {
                                public void done(ParseException e) {
                                    if (e == null) {
                                        loadNotesView();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Failed to Save", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        } else {
            Toast.makeText(getApplicationContext(), "Note title must be filled", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadNotesView() {
        Intent intent = new Intent(getApplicationContext(), NotesView.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }
}

