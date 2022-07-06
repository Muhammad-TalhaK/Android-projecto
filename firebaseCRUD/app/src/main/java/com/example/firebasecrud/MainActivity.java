package com.example.firebasecrud;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements CourseRvAdapter.CourseClickInterface {

    private RelativeLayout RlBottomSheet;
    private RecyclerView RcView;
    private ProgressBar pbRcv;
    private FloatingActionButton addFloatBtn;
    private ArrayList<Employee> employeeArrayList;
    private FirebaseDatabase database;
    private DatabaseReference db;
    private CourseRvAdapter courseRvAdapter;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RcView = findViewById(R.id.RcView);
        pbRcv = findViewById(R.id.pbRcv);
        RlBottomSheet = findViewById(R.id.RlBottomSheet);
        addFloatBtn = findViewById(R.id.addFloatBtn);
        employeeArrayList = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        db = database.getReference("Courses");
        courseRvAdapter = new CourseRvAdapter(employeeArrayList,this,this);
        RcView.setLayoutManager(new LinearLayoutManager(this));
        RcView.setAdapter(courseRvAdapter);

        //float button
        addFloatBtn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,AddCourse.class)));
        getAllCourse();
    }

    public void getAllCourse(){
        employeeArrayList.clear();
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            employeeArrayList.add(snapshot.getValue(Employee.class));
            pbRcv.setVisibility(View.GONE);
            courseRvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                pbRcv.setVisibility(View.GONE);
                courseRvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                pbRcv.setVisibility(View.GONE);
                courseRvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                pbRcv.setVisibility(View.GONE);
                courseRvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    @Override
    public void onCourseClick(int position) {
        displayBottomSheet(employeeArrayList.get(position));
    }

    private void displayBottomSheet(Employee emp){

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_layout,RlBottomSheet);

        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView coursename = layout.findViewById(R.id.bsCourseName);
        TextView courseprice = layout.findViewById(R.id.bsPrice);
        ImageView courseimageview = layout.findViewById(R.id.bsImgView);
        TextView coursesuitedfor = layout.findViewById(R.id.bsSuitedFor);
        TextView coursedescription = layout.findViewById(R.id.bsCourseDesc);
        Button editBtn = layout.findViewById(R.id.bsEditBtn);
        Button viewBtn= layout.findViewById(R.id.bsViewBtn);

        coursename.setText(emp.getCname());
        courseprice.setText(emp.getCprice());
        coursedescription.setText(emp.getCdesc());
        coursesuitedfor.setText(emp.getCsuitedfor());
        Picasso.get().load(emp.getCimglink()).into(courseimageview);

        editBtn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,EditCourse.class).putExtra("course",emp)));

        viewBtn.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(emp.getClink()));
            startActivity(i);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logOutOption) {
            mAuth.signOut();
            startActivity(new Intent(this, Login.class));
            this.finish();
        }
        return super.onOptionsItemSelected(item);

    }
}