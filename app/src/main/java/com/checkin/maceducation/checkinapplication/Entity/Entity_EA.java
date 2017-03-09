package com.checkin.maceducation.checkinapplication.Entity;

/**
 * Created by jenwit on 14/9/2558.
 */
public class Entity_EA {
    //    new {ea.EAID,ea.UserID, ea.Name ,ea.Email});
    private int EAID;
    private int UserID;
    private String Name;
    private String TripName;
    private String TripID;
    private int TripEaID;
    private String Email;

    public String getTripName() {
        return TripName;
    }

    public void setTripName(String tripName) {
        TripName = tripName;
    }

    public String getTripID() {
        return TripID;
    }

    public void setTripID(String tripID) {
        TripID = tripID;
    }

    public int getTripEaID() {
        return TripEaID;
    }

    public void setTripEaID(int tripEaID) {
        TripEaID = tripEaID;
    }


    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getEAID() {
        return EAID;
    }

    public void setEAID(int EAID) {
        this.EAID = EAID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


}
