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

public class AddCourse extends AppCompatActivity {

    private TextInputEditText coursename, courseprice, courselink, courseimagelink, coursedescription, coursesuitedfor;
    private Button addcourseBtn;
    private ProgressBar pbAddcourse;
    private FirebaseDatabase database;
    private DatabaseReference db;
    private String CourseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        coursename = findViewById(R.id.coursename);
        courseprice = findViewById(R.id.courseprice);
        courselink = findViewById(R.id.courselink);
        courseimagelink = findViewById(R.id.courseimglink);
        courseprice = findViewById(R.id.courseprice);
        coursedescription = findViewById(R.id.coursedesc);
        coursesuitedfor = findViewById(R.id.coursesuitedfor);
        addcourseBtn = findViewById(R.id.addcourseBtn);
        pbAddcourse = findViewById(R.id.pbAddcourse);
        database = FirebaseDatabase.getInstance();
        db = database.getReference("Courses");


        addcourseBtn.setOnClickListener(view -> {
            String cname = coursename.getText().toString();
            String cprice = courseprice.getText().toString();
            String csuitedfor = coursesuitedfor.getText().toString();
            String cimglink = courseimagelink.getText().toString();
            String clink = courselink.getText().toString();
            String cdesc = coursedescription.getText().toString();
            CourseId = cname;
            pbAddcourse.setVisibility(View.VISIBLE);

            Employee emp = new Employee(cname, cprice, csuitedfor, cimglink, clink, cdesc, CourseId);

            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    db.child(CourseId).setValue(emp);
                    pbAddcourse.setVisibility(View.GONE);
                    Toast.makeText(AddCourse.this, "Course Successfully added", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddCourse.this, MainActivity.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    pbAddcourse.setVisibility(View.GONE);
                    Toast.makeText(AddCourse.this, "" + error, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddCourse.this, MainActivity.class));
                }
            });
        });
    }
}