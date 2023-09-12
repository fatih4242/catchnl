package com.tokersoftware.catchnl.model.response;

import com.google.gson.annotations.SerializedName;

public class Complaint {
    /*
    "id":"1","licencePlate":"1","date":"2023-09-07","incident":"1","userID":"1","place":"place
     */

    public Complaint(String id, String licencePlate, String date, String incident, String userID, String place) {
        this.id = id;
        this.licencePlate = licencePlate;
        this.date = date;
        this.incident = incident;
        this.userID = userID;
        this.place = place;
    }

    @SerializedName("id")
    String id;
    @SerializedName("licencePlate")
    String licencePlate;
    @SerializedName("date")
    String date;
    @SerializedName("incident")
    String incident;
    @SerializedName("userID")
    String userID;
    @SerializedName("place")
    String place;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIncident() {
        return incident;
    }

    public void setIncident(String incident) {
        this.incident = incident;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
