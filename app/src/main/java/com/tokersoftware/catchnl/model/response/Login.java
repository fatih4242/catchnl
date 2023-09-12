package com.tokersoftware.catchnl.model.response;

import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;
    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;
    @SerializedName("licencePlate")
    String licencePlate;
    @SerializedName("licenceFile")
    String licenceFile;
    @SerializedName("isAccepted")
    String isAccepted;
    @SerializedName("error")
    int error;
    @SerializedName("message")
    String message;
    @SerializedName("complaintPerMonthLeft")
    String complaintPerMonthLeft;

    public String getComplaintPerMonthLeft() {
        return complaintPerMonthLeft;
    }

    public void setComplaintPerMonthLeft(String complaintPerMonthLeft) {
        this.complaintPerMonthLeft = complaintPerMonthLeft;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public String getLicenceFile() {
        return licenceFile;
    }

    public void setLicenceFile(String licenceFile) {
        this.licenceFile = licenceFile;
    }

    public String getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(String isAccepted) {
        this.isAccepted = isAccepted;
    }

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
}
