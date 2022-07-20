package com.example.to_dolist.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_dolist.Model.Note;
import com.example.to_dolist.R;
import com.example.to_dolist.databinding.SampleRcvBinding;

public class rcvAdapter extends ListAdapter<Note,rcvAdapter.ViewHolder> {

   public rcvAdapter(){
       super(CALLBACK);
   }

   private static final DiffUtil.ItemCallback<Note> CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId()== newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getDesc().equals(newItem.getDesc());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_rcv,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       Note note = getNote(position);
        holder.binding.tvTitle.setText(note.getTitle());
        holder.binding.tvDesc.setText(note.getDesc());
    }

    public Note getNote(int position) {
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        SampleRcvBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleRcvBinding.bind(itemView);
        }
    }
}
