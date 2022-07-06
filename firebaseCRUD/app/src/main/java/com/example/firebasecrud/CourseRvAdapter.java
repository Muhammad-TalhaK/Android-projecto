package com.example.firebasecrud;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CourseRvAdapter extends RecyclerView.Adapter<CourseRvAdapter.ViewHolder> {

    private ArrayList<Employee> employeeArrayList;
    private Context context;
    private int lastPosition = -1;
    private CourseClickInterface courseClickInterface;

    public CourseRvAdapter(ArrayList<Employee> employeeArrayList, Context context, CourseClickInterface courseClickInterface) {
        this.employeeArrayList = employeeArrayList;
        this.context = context;
        this.courseClickInterface = courseClickInterface;
    }

    @NonNull
    @Override
    public CourseRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Employee emp = employeeArrayList.get(position);
        holder.nameRv.setText(emp.getCname());
        holder.priceRv.setText("Rs. "+emp.getCprice());
        Picasso.get().load(emp.getCimglink()).into(holder.imgRv);
        AddAnimation(holder.itemView,position);
        holder.imgRv.setOnClickListener(view -> courseClickInterface.onCourseClick(position));


    }

    private void AddAnimation(View itemView,int position){

        if(position>lastPosition){
            Animation animation = AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return employeeArrayList.size();
    }

    public interface CourseClickInterface{
        void onCourseClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgRv;
        TextView nameRv,priceRv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRv = itemView.findViewById(R.id.imgRv);
            nameRv = itemView.findViewById(R.id.nameRv);
            priceRv = itemView.findViewById(R.id.priceRv);
        }
    }


}
