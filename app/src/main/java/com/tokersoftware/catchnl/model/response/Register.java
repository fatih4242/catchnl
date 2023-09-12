package com.tokersoftware.catchnl.model.response;

import com.google.gson.annotations.SerializedName;

public class Register {
    @SerializedName("error")
    int error;
    @SerializedName("message")
    String message;
    @SerializedName("userID")
    String userID;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
