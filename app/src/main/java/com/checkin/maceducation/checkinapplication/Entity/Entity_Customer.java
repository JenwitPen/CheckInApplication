package com.checkin.maceducation.checkinapplication.Entity;

/**
 * Created by jenwit on 15/9/2558.
 */
public class Entity_Customer {

    public int getCardCode() {
        return CardCode;
    }

    public void setCardCode(int cardCode) {
        CardCode = cardCode;
    }

    public String getCardName() {
        return CardName;
    }

    public void setCardName(String cardName) {
        CardName = cardName;
    }

    public String getCounty() {
        return County;
    }

    public void setCounty(String county) {
        County = county;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getSlpName() {
        return SlpName;
    }

    public void setSlpName(String slpName) {
        SlpName = slpName;
    }

    private int CardCode;
    private String CardName;
    private String County;
    private String Address;
    private String SlpName;

    public String getCustomerType() {
        return CustomerType;
    }

    public void setCustomerType(String customerType) {
        CustomerType = customerType;
    }

    private  String CustomerType;
    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    private int Total;

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    private String Phone;
    public int getTripEaCustomerID() {
        return TripEaCustomerID;
    }

    public void setTripEaCustomerID(int tripEaCustomerID) {
        TripEaCustomerID = tripEaCustomerID;
    }

    public int getTripEaID() {
        return TripEaID;
    }

    public void setTripEaID(int tripEaID) {
        TripEaID = tripEaID;
    }

    private int TripEaID;
    private int TripEaCustomerID;

    public String getTripName() {
        return TripName;
    }

    public void setTripName(String tripName) {
        TripName = tripName;
    }

    String TripName;
}
