package noteapp.voltasit.lt.noteapplication.views.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import noteapp.voltasit.lt.noteapplication.R;
import noteapp.voltasit.lt.noteapplication.model.Note;

/**
 * Created by Romas Noreika on 2017-12-17.
 */

public class NewNoteActivity extends AppCompatActivity {
    private Note note;
    private EditText titleEditText;
    private EditText contentEditText;
    private String postTitle;
    private String postContent;
    private Button saveNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        titleEditText = findViewById(R.id.noteTitle);
        contentEditText = findViewById(R.id.noteContent);

        saveNoteButton = findViewById(R.id.saveNote);
        saveNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }

            private void saveNote() {

            }
        });

    }
}

