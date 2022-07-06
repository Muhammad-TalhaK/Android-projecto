package com.example.whatsappclone.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclone.Models.Messages;
import com.example.whatsappclone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter{
    ArrayList<Messages> messagesList;
    Context context;
    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;
    private String recId;

    public MessageAdapter(ArrayList<Messages> messagesList, Context context) {
        this.messagesList = messagesList;
        this.context = context;
    }

    public MessageAdapter(ArrayList<Messages> messagesList, Context context,String recId) {
        this.messagesList = messagesList;
        this.context = context;
        this.recId = recId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==SENDER_VIEW_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.sender,parent,false);
            return new SenderViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.receiver,parent,false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Messages messages = messagesList.get(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete Message")
                        .setMessage("Are You Sure You Want to Delete This Message?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String senderRoom = FirebaseAuth.getInstance().getUid() + recId;
                                FirebaseDatabase.getInstance().getReference("Chats")
                                        .child(senderRoom).child(messages.getMsgId()).setValue(null);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

                return false;
            }
        });

        if(holder.getClass()== SenderViewHolder.class){
            ((SenderViewHolder)holder).sndMsg.setText(messages.getMsg());
        }
        else{
            ((ReceiverViewHolder)holder).rcvMsg.setText(messages.getMsg());
        }

    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(messagesList.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())){
            return SENDER_VIEW_TYPE;
        }
        else{
            return RECEIVER_VIEW_TYPE;
        }
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder{
        TextView rcvMsg,rcvTime;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            rcvMsg = itemView.findViewById(R.id.rcvMsg);
            rcvTime = itemView.findViewById(R.id.rcvTime);
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder{
        TextView sndMsg,sndTime;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            sndMsg = itemView.findViewById(R.id.sndMsg);
            sndTime = itemView.findViewById(R.id.sndTime);
        }
    }
}
