package com.example.solutiontofarming.data;

import java.io.Serializable;

public class LandUser implements Serializable {

    private String userName;
    private String userEmailId;

    public LandUser(String userName, String userEmailId) {
        this.userName = userName;
        this.userEmailId = userEmailId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    @Override
    public String toString() {
        return "LandUser{" +
                "userName='" + userName + '\'' +
                ", userEmailId='" + userEmailId + '\'' +
                '}';
    }
}
