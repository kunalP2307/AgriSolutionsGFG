package com.example.solutiontofarming.data;

public class ChatMessage {
    private String email;
    private String name;
    private String message;
    private String timestamp;
    private String chat_image;
    private String user_image;

    public ChatMessage(String email, String name, String message, String timestamp, String chat_image, String user_image) {
        this.email = email;
        this.name = name;
        this.message = message;
        this.timestamp = timestamp;
        this.chat_image = chat_image;
        this.user_image = user_image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getChat_image() {
        return chat_image;
    }

    public void setChat_image(String chat_image) {
        this.chat_image = chat_image;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }
}
