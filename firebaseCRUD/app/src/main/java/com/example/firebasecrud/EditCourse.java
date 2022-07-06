package com.example.firebasecrud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditCourse extends AppCompatActivity {

    private TextInputEditText coursename, courseprice, courselink, courseimagelink, coursedescription, coursesuitedfor;
    private Button deletecourseBtn,updatecourseBtn;
    private ProgressBar pbAddcourse;
    private FirebaseDatabase database;
    private DatabaseReference db;
    private String CourseId;
    private Employee emp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        coursename = findViewById(R.id.coursename);
        courseprice = findViewById(R.id.courseprice);
        courselink = findViewById(R.id.courselink);
        courseimagelink = findViewById(R.id.courseimglink);
        courseprice = findViewById(R.id.courseprice);
        coursedescription = findViewById(R.id.coursedesc);
        coursesuitedfor = findViewById(R.id.coursesuitedfor);
        deletecourseBtn = findViewById(R.id.deletecourseBtn);
        updatecourseBtn = findViewById(R.id.updatecourseBtn);
        pbAddcourse = findViewById(R.id.pbAddcourse);
        database = FirebaseDatabase.getInstance();
        emp = getIntent().getParcelableExtra("course");

        //Getting data of previous course which is to be edited
        if(emp != null){
            coursename.setText(emp.getCname());
            courseprice.setText(emp.getCprice());
            courselink.setText(emp.getClink());
            courseimagelink.setText(emp.getCimglink());
            coursedescription.setText(emp.getCdesc());
            coursesuitedfor.setText(emp.getCsuitedfor());
            CourseId = emp.getCid();
        }

        db = database.getReference("Courses").child(CourseId);

        //update button
        updatecourseBtn.setOnClickListener(view -> {
            pbAddcourse.setVisibility(View.VISIBLE);
            String cname = coursename.getText().toString();
            String cprice = courseprice.getText().toString();
            String csuitedfor = coursesuitedfor.getText().toString();
            String cimglink = courseimagelink.getText().toString();
            String clink = courselink.getText().toString();
            String cdesc = coursedescription.getText().toString();

            Map<String,Object> map = new HashMap<>();
            map.put("cname",cname);
            map.put("cprice",cprice);
            map.put("csuitedfor",csuitedfor);
            map.put("cimglink",cimglink);
            map.put("clink",clink);
            map.put("cdesc",cdesc);
            map.put("cid",CourseId);

            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    pbAddcourse.setVisibility(View.GONE);
                    db.updateChildren(map);
                    Toast.makeText(EditCourse.this, "Course Updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditCourse.this,MainActivity.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(EditCourse.this, "Failed to Update Course", Toast.LENGTH_SHORT).show();
                }
            });

        });

        //Delete button
        deletecourseBtn.setOnClickListener(view -> deleteCourse());
    }
    
    private void deleteCourse(){
        db.removeValue();
        Toast.makeText(this, "Course Removed", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditCourse.this,MainActivity.class));
    }
}