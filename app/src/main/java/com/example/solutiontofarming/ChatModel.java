package com.example.solutiontofarming;

public class ChatModel {

    private String sender;
    private String message;
    private String userImage;

    public ChatModel(String sender, String message, String userImage) {
        this.sender = sender;
        this.message = message;
        this.userImage = userImage;
    }

    public String getUserImage() {
        return userImage;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }
}
