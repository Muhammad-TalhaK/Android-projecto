package com.example.whatsappclone.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.whatsappclone.Adapters.userAdapter;
import com.example.whatsappclone.Models.User;
import com.example.whatsappclone.R;
import com.example.whatsappclone.databinding.FragmentChatFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class Chat_fragment extends Fragment {
    FragmentChatFragmentBinding binding;
    ArrayList<User> userList;
    FirebaseDatabase database;
    userAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding = FragmentChatFragmentBinding.inflate(inflater, container, false);


       userList = new ArrayList<>();
       database = FirebaseDatabase.getInstance();
       adapter = new userAdapter(userList,getContext());
       binding.chatRcv.setAdapter(adapter);
       LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
       binding.chatRcv.setLayoutManager(layoutManager);

       database.getReference("Users").addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               if(snapshot.getValue(User.class).getuId().equals(FirebaseAuth.getInstance().getUid())){
                   adapter.notifyDataSetChanged();
               }
               else{
                   userList.add(snapshot.getValue(User.class));
                   adapter.notifyDataSetChanged();
               }

           }

           @Override
           public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               adapter.notifyDataSetChanged();
           }

           @Override
           public void onChildRemoved(@NonNull DataSnapshot snapshot) {
               adapter.notifyDataSetChanged();
           }

           @Override
           public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               adapter.notifyDataSetChanged();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });


        return binding.getRoot();
    }
}