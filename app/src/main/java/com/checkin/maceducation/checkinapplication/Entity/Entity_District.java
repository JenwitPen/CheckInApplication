package com.checkin.maceducation.checkinapplication.Entity;

/**
 * Created by jenwit on 15/9/2558.
 */
public class Entity_District {

    private String DistrictID;
    private String ProvinceID;

    public String getDistrictID() {
        return DistrictID;
    }

    public void setDistrictID(String districtID) {
        DistrictID = districtID;
    }

    public String getDistrictNameEN() {
        return DistrictNameEN;
    }

    public void setDistrictNameEN(String districtNameEN) {
        DistrictNameEN = districtNameEN;
    }

    public String getDistrictNameTH() {
        return DistrictNameTH;
    }

    public void setDistrictNameTH(String districtNameTH) {
        DistrictNameTH = districtNameTH;
    }

    public String getProvinceID() {
        return ProvinceID;
    }

    public void setProvinceID(String provinceID) {
        ProvinceID = provinceID;
    }

    private  String DistrictNameEN;
    private  String DistrictNameTH;
}
