package com.checkin.maceducation.checkinapplication.Entity;

/**
 * Created by jenwit on 14/9/2558.
 */
public class Entity_School {
    private String DistrictID;

    public String getDistrictID() {
        return DistrictID;
    }

    public void setDistrictID(String districtID) {
        DistrictID = districtID;
    }

    public int getSchoolID() {
        return SchoolID;
    }

    public void setSchoolID(int schoolID) {
        SchoolID = schoolID;
    }

    public String getSchoolName() {
        return SchoolName;
    }

    public void setSchoolName(String schoolName) {
        SchoolName = schoolName;
    }

    private int SchoolID;
    private String SchoolName;


}
