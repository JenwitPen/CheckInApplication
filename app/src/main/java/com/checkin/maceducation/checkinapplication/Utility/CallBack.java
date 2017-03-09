package com.checkin.maceducation.checkinapplication.Utility;

/**
 * Created by jenwit on 15/9/2558.
 */
public class CallBack {
    private String JsonString;
    private Boolean isSuccsess;
    private String strError;
    private  String strSuccess;
    public String getStrSuccess() {
        return strSuccess;
    }

    public void setStrSuccess(String strSuccess) {
        this.strSuccess = strSuccess;
    }


    public String getJsonString() {
        return JsonString;
    }

    public void setJsonString(String jsonString) {
        JsonString = jsonString;
    }

    public Boolean getIsSuccsess() {
        return isSuccsess;
    }

    public void setIsSuccsess(Boolean isSuccsess) {
        this.isSuccsess = isSuccsess;
    }

    public String getStrError() {
        return strError;
    }

    public void setStrError(String strError) {
        this.strError = strError;
    }
}
