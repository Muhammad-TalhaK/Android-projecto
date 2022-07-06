package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.whatsappclone.Adapters.MessageAdapter;
import com.example.whatsappclone.Models.Messages;
import com.example.whatsappclone.databinding.ActivityChatDetailBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetail extends AppCompatActivity {

    ActivityChatDetailBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary));

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        final String senderId = auth.getCurrentUser().getUid();
        final String receiverId = getIntent().getStringExtra("userId");
        String receiverName = getIntent().getStringExtra("username");
        String receiverProfile = getIntent().getStringExtra("userProfile");

        binding.textViewCD.setText(receiverName);
        Picasso.get().load(receiverProfile).placeholder(R.drawable.ic_icons8_whatsapp).into(binding.profileImageCD);

        binding.imageViewCD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatDetail.this, MainActivity.class));
            }
        });

        ArrayList<Messages> messageList = new ArrayList<>();
        //recyclerview
        MessageAdapter messageAdapter = new MessageAdapter(messageList, this,receiverId);
        binding.rcvCD.setAdapter(messageAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rcvCD.setLayoutManager(linearLayoutManager);

        String senderRoom = senderId + receiverId;
        String receiverRoom = receiverId + senderId;

        database.getReference("Chats").child(senderRoom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Messages messages = snapshot1.getValue(Messages.class);
                    messages.setMsgId(snapshot1.getKey());
                    messageList.add(messages);
                }
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //send button
        binding.sendBtnCD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageCD = binding.messageCD.getText().toString();
                if(TextUtils.isEmpty(binding.messageCD.getText().toString())){
                    binding.messageCD.setError("Message can't be empty");
                }
                final Messages messagesModel = new Messages(senderId, messageCD);
                messagesModel.setTimeStamp(new Date().getTime());
                binding.messageCD.setText("");
                database.getReference().child("Chats").child(senderRoom).push().setValue(messagesModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        database.getReference().child("Chats").child(receiverRoom).push().setValue(messagesModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
                    }
                });
            }
        });
    }
}