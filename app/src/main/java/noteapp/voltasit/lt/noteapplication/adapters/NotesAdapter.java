package noteapp.voltasit.lt.noteapplication.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import noteapp.voltasit.lt.noteapplication.R;
import noteapp.voltasit.lt.noteapplication.model.Note;

/**
 * Created by Romas_Noreika on 2017-12-19.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    private List<Note> noteList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView noteTitle, noteContent;

        public MyViewHolder(View view) {
            super(view);
            noteTitle = (TextView) view.findViewById(R.id.noteTitle);
            noteContent = (TextView) view.findViewById(R.id.noteContent);
        }
    }


    public NotesAdapter(List<Note> noteList) {
        this.noteList = noteList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.noteTitle.setText(note.getTitle());
        holder.noteContent.setText(note.getContent());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }
}