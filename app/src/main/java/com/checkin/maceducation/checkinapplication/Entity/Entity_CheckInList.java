package com.checkin.maceducation.checkinapplication.Entity;

/**
 * Created by jenwit on 14/9/2558.
 */
public class Entity_CheckInList {
    private Integer CheckInID;
    private Integer TripEaID;
    private String CheckInName;
    private String CheckInAddress;
    private String CheckInDate;
    private String Latitude;
    private String Longitude;
    private String Remark;
    private String Image;
    private Integer CheckInType;
    private Integer StatusSend;
    private Integer TripEaCustomerID;
    private Integer EnergyTypeID;
    private Double EnergyPrice;
    private Long TempCheckInId;
    private Integer ActivityID;
    private String CheckInActivityRemark;
    private String CheckInActivityContact;
    private String CheckInActivityPosition;
    private String CheckInActivityEmail;
    private String CheckInActivityTel;
    private Integer SubjectID;
    private Integer EAID;

    public Entity_CheckInList(Integer checkInID, Integer tripEaID, String checkInName, String checkInAddress, String checkInDate, String latitude, String longitude, String remark, String image, Integer checkInType, Integer statusSend, Integer tripEaCustomerID, Integer energyTypeID, Double energyPrice, Long tempCheckInId, Integer activityID, String checkInActivityRemark, String checkInActivityContact, String checkInActivityPosition, String checkInActivityEmail, String checkInActivityTel, Integer subjectID, Integer EAID) {
        CheckInID = checkInID;
        TripEaID = tripEaID;
        CheckInName = checkInName;
        CheckInAddress = checkInAddress;
        CheckInDate = checkInDate;
        Latitude = latitude;
        Longitude = longitude;
        Remark = remark;
        Image = image;
        CheckInType = checkInType;
        StatusSend = statusSend;
        TripEaCustomerID = tripEaCustomerID;
        EnergyTypeID = energyTypeID;
        EnergyPrice = energyPrice;
        TempCheckInId = tempCheckInId;
        ActivityID = activityID;
        CheckInActivityRemark = checkInActivityRemark;
        CheckInActivityContact = checkInActivityContact;
        CheckInActivityPosition = checkInActivityPosition;
        CheckInActivityEmail = checkInActivityEmail;
        CheckInActivityTel = checkInActivityTel;
        SubjectID = subjectID;
        this.EAID = EAID;
    }

    public Integer getCheckInID() {
        return CheckInID;
    }

    public void setCheckInID(Integer checkInID) {
        CheckInID = checkInID;
    }

    public Integer getTripEaID() {
        return TripEaID;
    }

    public void setTripEaID(Integer tripEaID) {
        TripEaID = tripEaID;
    }

    public String getCheckInName() {
        return CheckInName;
    }

    public void setCheckInName(String checkInName) {
        CheckInName = checkInName;
    }

    public String getCheckInAddress() {
        return CheckInAddress;
    }

    public void setCheckInAddress(String checkInAddress) {
        CheckInAddress = checkInAddress;
    }

    public String getCheckInDate() {
        return CheckInDate;
    }

    public void setCheckInDate(String checkInDate) {
        CheckInDate = checkInDate;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public Integer getCheckInType() {
        return CheckInType;
    }

    public void setCheckInType(Integer checkInType) {
        CheckInType = checkInType;
    }

    public Integer getStatusSend() {
        return StatusSend;
    }

    public void setStatusSend(Integer statusSend) {
        StatusSend = statusSend;
    }

    public Integer getTripEaCustomerID() {
        return TripEaCustomerID;
    }

    public void setTripEaCustomerID(Integer tripEaCustomerID) {
        TripEaCustomerID = tripEaCustomerID;
    }

    public Integer getEnergyTypeID() {
        return EnergyTypeID;
    }

    public void setEnergyTypeID(Integer energyTypeID) {
        EnergyTypeID = energyTypeID;
    }

    public Double getEnergyPrice() {
        return EnergyPrice;
    }

    public void setEnergyPrice(Double energyPrice) {
        EnergyPrice = energyPrice;
    }

    public Long getTempCheckInId() {
        return TempCheckInId;
    }

    public void setTempCheckInId(Long tempCheckInId) {
        TempCheckInId = tempCheckInId;
    }

    public Integer getActivityID() {
        return ActivityID;
    }

    public void setActivityID(Integer activityID) {
        ActivityID = activityID;
    }

    public String getCheckInActivityRemark() {
        return CheckInActivityRemark;
    }

    public void setCheckInActivityRemark(String checkInActivityRemark) {
        CheckInActivityRemark = checkInActivityRemark;
    }

    public String getCheckInActivityContact() {
        return CheckInActivityContact;
    }

    public void setCheckInActivityContact(String checkInActivityContact) {
        CheckInActivityContact = checkInActivityContact;
    }

    public String getCheckInActivityPosition() {
        return CheckInActivityPosition;
    }

    public void setCheckInActivityPosition(String checkInActivityPosition) {
        CheckInActivityPosition = checkInActivityPosition;
    }

    public String getCheckInActivityEmail() {
        return CheckInActivityEmail;
    }

    public void setCheckInActivityEmail(String checkInActivityEmail) {
        CheckInActivityEmail = checkInActivityEmail;
    }

    public String getCheckInActivityTel() {
        return CheckInActivityTel;
    }

    public void setCheckInActivityTel(String checkInActivityTel) {
        CheckInActivityTel = checkInActivityTel;
    }

    public Integer getSubjectID() {
        return SubjectID;
    }

    public void setSubjectID(Integer subjectID) {
        SubjectID = subjectID;
    }

    public Integer getEAID() {
        return EAID;
    }

    public void setEAID(Integer EAID) {
        this.EAID = EAID;
    }
}
