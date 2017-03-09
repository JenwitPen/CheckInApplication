package com.checkin.maceducation.checkinapplication.Entity;

/**
 * Created by jenwit on 14/9/2558.
 */
public class Entity_CheckInActivityList {
   private Integer ActivityID;
    private String ActivityName;
    private Integer CheckInID;
    private String  CreateDate;

    public Integer getActivityID() {
        return ActivityID;
    }

    public void setActivityID(Integer activityID) {
        ActivityID = activityID;
    }

    public String getActivityName() {
        return ActivityName;
    }

    public void setActivityName(String activityName) {
        ActivityName = activityName;
    }

    public Integer getCheckInID() {
        return CheckInID;
    }

    public void setCheckInID(Integer checkInID) {
        CheckInID = checkInID;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public Entity_CheckInActivityList(Integer activityID, String activityName, Integer checkInID, String createDate) {
        ActivityID = activityID;
        ActivityName = activityName;
        CheckInID = checkInID;
        CreateDate = createDate;

    }
}
