package noteapp.voltasit.lt.noteapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import noteapp.voltasit.lt.noteapplication.R;
import noteapp.voltasit.lt.noteapplication.model.Note;
import noteapp.voltasit.lt.noteapplication.views.activity.EditNoteActivity;

/**
 * Created by Romas_Noreika on 2017-12-19.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private List<Note> noteList;
    private Context context;

    public NotesAdapter(List<Note> noteList, Context context) {
        this.context = context;
        this.noteList = noteList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notes_list_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final NotesAdapter.ViewHolder holder, final int position) {
        final Note note = noteList.get(position);
        holder.noteTitle.setText(note.getTitle());
        holder.noteContent.setText(note.getContent());

        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, holder.buttonViewOption);
                popup.inflate(R.menu.note_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.editNote:

                                Intent intent = new Intent(context, EditNoteActivity.class);
                                intent.putExtra("noteId", note.getId());
                                intent.putExtra("noteTitle", note.getTitle());
                                intent.putExtra("noteContent", note.getContent());
                                context.startActivity(intent);
                                break;
                            case R.id.removeNote:
                                break;
                        }
                        return false;
                    }


                });
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView noteTitle, noteContent;
        public TextView buttonViewOption;

        public ViewHolder(View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.noteTitle);
            noteContent = itemView.findViewById(R.id.noteContent);
            buttonViewOption = itemView.findViewById(R.id.textViewOptions);
        }
    }
}


