package com.example.roomdbpract;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdbpract.DB.Expense;
import com.example.roomdbpract.databinding.SampleRcvBinding;

import java.util.List;

public class Adapter extends ListAdapter<Expense,Adapter.ViewHolder> {

    public Adapter(){
        super(CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Expense> CALLBACK = new DiffUtil.ItemCallback<Expense>() {
        @Override
        public boolean areItemsTheSame(@NonNull Expense oldItem, @NonNull Expense newItem) {
            return oldItem.getId()== newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Expense oldItem, @NonNull Expense newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getAmount().equals(newItem.getAmount());
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
        Expense expense = getExpense(position);
        holder.binding.textView.setText(expense.getTitle());
        holder.binding.textView2.setText(expense.getAmount()+"$");
    }

    public Expense getExpense(int position){
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
