package com.example.solutiontofarming;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<ChatModel> chatList;

    public ChatAdapter(List<ChatModel> chatList) {
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_item, parent, false);

        return new ChatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ChatViewHolder holder, int position) {
        ChatModel chat = chatList.get(position);
        holder.bind(chat);
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        private TextView senderTextView;
        private TextView messageTextView;
        private ImageView userImage;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            senderTextView = itemView.findViewById(R.id.tv_sender_chat);
            messageTextView = itemView.findViewById(R.id.tv_message_chat);
            userImage = itemView.findViewById(R.id.image_sender_chat);

        }

        public void bind(ChatModel chat) {
            senderTextView.setText(chat.getSender());
            messageTextView.setText(chat.getMessage());

//            Glide.with(itemView)
//                    .load(chat.getUserImage())
//                    .centerCrop()
//                    .into(userImage);

            userImage.setImageResource(R.drawable.ic_chat_user_image);
        }
    }
}
