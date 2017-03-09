package com.checkin.maceducation.checkinapplication.QueryDatabase;

import android.media.Image;

import com.checkin.maceducation.checkinapplication.Utility.CallBack;
import com.checkin.maceducation.checkinapplication.Utility.WebServiceConfig;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Date;
import java.util.Objects;


public class CallSoap {

    SoapSerializationEnvelope envelope;
    SoapObject request;
    HttpTransportSE httpTransport;
    PropertyInfo pi;
    private String OPERATION_NAME = "";
    CallBack respon = new CallBack();

    public CallSoap(String methodName) {

        this.OPERATION_NAME = methodName;
        request = new SoapObject(WebServiceConfig.WSDL_TARGET_NAMESPACE, OPERATION_NAME);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        httpTransport = new HttpTransportSE(WebServiceConfig.SOAP_ADDRESS);
    }

    private void sendParameterPropertyInfo(String setName, Object setValue, Object setType) {
        pi = new PropertyInfo();
        pi.setName(setName);
        pi.setValue(setValue);
        pi.setType(setType);
        request.addProperty(pi);
    }

    public CallBack SelectEA(String Email, String PassWord) {

        sendParameterPropertyInfo("Email", Email, Integer.class);
        sendParameterPropertyInfo("PassWord", PassWord, Integer.class);
        envelope.setOutputSoapObject(request);
        try {
            httpTransport.call(WebServiceConfig.SOAP_ACTION + OPERATION_NAME, envelope);
            respon.setJsonString(envelope.getResponse().toString());
            respon.setIsSuccsess(true);

        } catch (Exception exception) {

            respon.setIsSuccsess(false);
            respon.setJsonString("");
        }
        return respon;
    }
    public CallBack SelectCustomerList3(Integer EAID,String CustomerType,String CustomerName) {

        sendParameterPropertyInfo("EAID", EAID, Integer.class);
        sendParameterPropertyInfo("CustomerType", CustomerType, String.class);
        sendParameterPropertyInfo("CustomerName", CustomerName, String.class);

        envelope.setOutputSoapObject(request);
        try {
            httpTransport.call(WebServiceConfig.SOAP_ACTION + OPERATION_NAME, envelope);
            respon.setJsonString(envelope.getResponse().toString());
            respon.setIsSuccsess(true);

        } catch (Exception exception) {

            respon.setIsSuccsess(false);
            respon.setJsonString("");
        }
        return respon;
    }
    public CallBack SelectCheckInActivity(Integer CheckInID) {

        sendParameterPropertyInfo("CheckInID", CheckInID, Integer.class);
        envelope.setOutputSoapObject(request);
        try {
            httpTransport.call(WebServiceConfig.SOAP_ACTION + OPERATION_NAME, envelope);
            respon.setJsonString(envelope.getResponse().toString());
            respon.setIsSuccsess(true);
        } catch (Exception exception) {
            respon.setIsSuccsess(false);
            respon.setJsonString("");
        }
        return respon;
    }
    public CallBack SelectCustomerDetail(Integer CardCode) {

        sendParameterPropertyInfo("CardCode", CardCode, Integer.class);
        envelope.setOutputSoapObject(request);
        try {
            httpTransport.call(WebServiceConfig.SOAP_ACTION + OPERATION_NAME, envelope);
            respon.setJsonString(envelope.getResponse().toString());
            respon.setIsSuccsess(true);
        } catch (Exception exception) {
            respon.setIsSuccsess(false);
            respon.setJsonString("");
        }
        return respon;
    }
    public CallBack SelectCheckInList(Integer TripEaID) {

        sendParameterPropertyInfo("TripEaID", TripEaID, Integer.class);
        envelope.setOutputSoapObject(request);
        try {
            httpTransport.call(WebServiceConfig.SOAP_ACTION + OPERATION_NAME, envelope);
            respon.setJsonString(envelope.getResponse().toString());
            respon.setIsSuccsess(true);
        } catch (Exception exception) {
            respon.setIsSuccsess(false);
            respon.setJsonString("");
        }
        return respon;
    }
    public CallBack InsertCheckIn3(Integer TripEaID, String CheckInName, String CheckInAddress, String CheckInDate
            , String Latitude, String Longitude, String Remark, String base64String, Integer CheckInType, Integer TripEaCustomerID
            , Integer EnergyTypeID, String EnergyPrice
            ,Integer ActivityID,String CheckInActivityContact,String CheckInActivityRemark,String CheckInActivityPosition,String CheckInActivityEmail,String CheckInActivityTel,Integer SubjectID) {

        sendParameterPropertyInfo("TripEaID", TripEaID, Integer.class);
        sendParameterPropertyInfo("CheckInName", CheckInName, String.class);
        sendParameterPropertyInfo("CheckInAddress", CheckInAddress, String.class);
        sendParameterPropertyInfo("CheckInDate", CheckInDate, String.class);
        sendParameterPropertyInfo("Latitude", Latitude, String.class);
        sendParameterPropertyInfo("Longitude", Longitude, String.class);
        sendParameterPropertyInfo("Remark", Remark, String.class);
        sendParameterPropertyInfo("base64String", base64String, String.class);
        sendParameterPropertyInfo("CheckInType", CheckInType, Integer.class);
        sendParameterPropertyInfo("TripEaCustomerID", TripEaCustomerID, Integer.class);
        sendParameterPropertyInfo("EnergyTypeID", EnergyTypeID, Integer.class);
        sendParameterPropertyInfo("EnergyPrice", EnergyPrice, String.class);
        sendParameterPropertyInfo("ActivityID", ActivityID, Integer.class);
        sendParameterPropertyInfo("CheckInActivityContact", CheckInActivityContact, String.class);
        sendParameterPropertyInfo("CheckInActivityRemark", CheckInActivityRemark, String.class);
        sendParameterPropertyInfo("CheckInActivityPosition", CheckInActivityPosition, String.class);
        sendParameterPropertyInfo("CheckInActivityEmail", CheckInActivityEmail, String.class);
        sendParameterPropertyInfo("CheckInActivityTel", CheckInActivityTel, String.class);
        sendParameterPropertyInfo("SubjectID",SubjectID, Integer.class);
        envelope.setOutputSoapObject(request);
        try {
            httpTransport.call(WebServiceConfig.SOAP_ACTION + OPERATION_NAME, envelope);
            respon.setJsonString(envelope.getResponse().toString());
            respon.setIsSuccsess(true);
        } catch (Exception exception) {
            respon.setIsSuccsess(false);
            respon.setJsonString("");
        }
        return respon;
    }
    public CallBack InsertCheckInActivity(Integer CheckInID,Integer ActivityID, String CheckInActivityContact, String CheckInActivityRemark , String CheckInActivityPosition
            , String CheckInActivityEmail
            , String CheckInActivityTel
            , Integer SubjectID) {
        //   (int CustomerCheckInID, int ActivityID, int SubjectID, String ContractName, String ContractTel, String ActivityRemark)
        sendParameterPropertyInfo("CheckInID", CheckInID, Integer.class);
        sendParameterPropertyInfo("ActivityID", ActivityID, Integer.class);
        sendParameterPropertyInfo("CheckInActivityContact", CheckInActivityContact, String.class);
        sendParameterPropertyInfo("CheckInActivityRemark", CheckInActivityRemark, String.class);
        sendParameterPropertyInfo("CheckInActivityPosition", CheckInActivityPosition, String.class);
        sendParameterPropertyInfo("CheckInActivityEmail", CheckInActivityEmail, String.class);
        sendParameterPropertyInfo("CheckInActivityTel", CheckInActivityTel, String.class);
        sendParameterPropertyInfo("SubjectID", SubjectID, Integer.class);
        envelope.setOutputSoapObject(request);
        try {
            httpTransport.call(WebServiceConfig.SOAP_ACTION + OPERATION_NAME, envelope);
            respon.setJsonString(envelope.getResponse().toString());
            respon.setIsSuccsess(true);
        } catch (Exception exception) {
            respon.setIsSuccsess(false);
            respon.setJsonString("");
        }
        return respon;
    }

    public CallBack SelectCustomerList(Integer EAID) {

        sendParameterPropertyInfo("EAID", EAID, Integer.class);

        envelope.setOutputSoapObject(request);
        try {
            httpTransport.call(WebServiceConfig.SOAP_ACTION + OPERATION_NAME, envelope);
            respon.setJsonString(envelope.getResponse().toString());
            respon.setIsSuccsess(true);

        } catch (Exception exception) {

            respon.setIsSuccsess(false);
            respon.setJsonString("");
        }
        return respon;
    }
    public CallBack SelectProvinceList(Integer EAID) {

        sendParameterPropertyInfo("EAID", EAID, Integer.class);
        envelope.setOutputSoapObject(request);
        try {
            httpTransport.call(WebServiceConfig.SOAP_ACTION + OPERATION_NAME, envelope);
            respon.setJsonString(envelope.getResponse().toString());
            respon.setIsSuccsess(true);

        } catch (Exception exception) {

            respon.setIsSuccsess(false);
            respon.setJsonString("");
        }
        return respon;
    }
    public CallBack SelectDistrictList(String ProvinceID) {

        sendParameterPropertyInfo("ProvinceID", ProvinceID, String.class);
        envelope.setOutputSoapObject(request);
        try {
            httpTransport.call(WebServiceConfig.SOAP_ACTION + OPERATION_NAME, envelope);
            respon.setJsonString(envelope.getResponse().toString());
            respon.setIsSuccsess(true);

        } catch (Exception exception) {

            respon.setIsSuccsess(false);
            respon.setJsonString("");
        }
        return respon;
    }
    public CallBack SelectSchoolList(String DistrictID) {

        sendParameterPropertyInfo("DistrictID", DistrictID, String.class);
        envelope.setOutputSoapObject(request);
        try {
            httpTransport.call(WebServiceConfig.SOAP_ACTION + OPERATION_NAME, envelope);
            respon.setJsonString(envelope.getResponse().toString());
            respon.setIsSuccsess(true);

        } catch (Exception exception) {

            respon.setIsSuccsess(false);
            respon.setJsonString("");
        }
        return respon;
    }
    public CallBack SelectCustomerActivity(Integer CustomerCheckInID) {

        sendParameterPropertyInfo("CustomerCheckInID", CustomerCheckInID, Integer.class);
        envelope.setOutputSoapObject(request);
        try {
            httpTransport.call(WebServiceConfig.SOAP_ACTION + OPERATION_NAME, envelope);
            respon.setJsonString(envelope.getResponse().toString());
            respon.setIsSuccsess(true);
        } catch (Exception exception) {
            respon.setIsSuccsess(false);
            respon.setJsonString("");
        }
        return respon;
    }
    public CallBack SelectActivityMaster() {

        envelope.setOutputSoapObject(request);
        try {
            httpTransport.call(WebServiceConfig.SOAP_ACTION + OPERATION_NAME, envelope);
            respon.setJsonString(envelope.getResponse().toString());
            respon.setIsSuccsess(true);
        } catch (Exception exception) {
            respon.setIsSuccsess(false);
            respon.setJsonString("");
        }
        return respon;
    }
    public CallBack SelectSubjectMaster() {

        envelope.setOutputSoapObject(request);
        try {
            httpTransport.call(WebServiceConfig.SOAP_ACTION + OPERATION_NAME, envelope);
            respon.setJsonString(envelope.getResponse().toString());
            respon.setIsSuccsess(true);
        } catch (Exception exception) {
            respon.setIsSuccsess(false);
            respon.setJsonString("");
        }
        return respon;
    }
    public CallBack SelectCustomerCheckInList(Integer TripEaCustomerID) {

        sendParameterPropertyInfo("TripEaCustomerID", TripEaCustomerID, Integer.class);
        envelope.setOutputSoapObject(request);
        try {
            httpTransport.call(WebServiceConfig.SOAP_ACTION + OPERATION_NAME, envelope);
            respon.setJsonString(envelope.getResponse().toString());
            respon.setIsSuccsess(true);
        } catch (Exception exception) {
            respon.setIsSuccsess(false);
            respon.setJsonString("");
        }
        return respon;
    }
    public CallBack SelectCheckInList2(Integer TripEaID) {

        sendParameterPropertyInfo("TripEaID", TripEaID, Integer.class);
        envelope.setOutputSoapObject(request);
        try {
            httpTransport.call(WebServiceConfig.SOAP_ACTION + OPERATION_NAME, envelope);
            respon.setJsonString(envelope.getResponse().toString());
            respon.setIsSuccsess(true);
        } catch (Exception exception) {
            respon.setIsSuccsess(false);
            respon.setJsonString("");
        }
        return respon;
    }
    public CallBack InsertCheckIn(Integer TripEaID, String CheckInName, String CheckInAddress, String CheckInDate
            , String Latitude, String Longitude, String Remark, String base64String, Integer CheckInType, Integer TripEaCustomerID, Integer EnergyTypeID, String EnergyPrice, String DistrictID, Integer SchoolID) {

        sendParameterPropertyInfo("TripEaID", TripEaID, Integer.class);
        sendParameterPropertyInfo("CheckInName", CheckInName, String.class);
        sendParameterPropertyInfo("CheckInAddress", CheckInAddress, String.class);
        sendParameterPropertyInfo("CheckInDate", CheckInDate, String.class);
        sendParameterPropertyInfo("Latitude", Latitude, String.class);
        sendParameterPropertyInfo("Longitude", Longitude, String.class);
        sendParameterPropertyInfo("Remark", Remark, String.class);
        sendParameterPropertyInfo("base64String", base64String, String.class);
        sendParameterPropertyInfo("CheckInType", CheckInType, Integer.class);
        sendParameterPropertyInfo("TripEaCustomerID", TripEaCustomerID, Integer.class);
        sendParameterPropertyInfo("EnergyTypeID", EnergyTypeID, Integer.class);
        sendParameterPropertyInfo("EnergyPrice", EnergyPrice, String.class);
        sendParameterPropertyInfo("DistrictID", DistrictID, String.class);
        sendParameterPropertyInfo("SchoolID", SchoolID, Integer.class);

        envelope.setOutputSoapObject(request);
        try {
            httpTransport.call(WebServiceConfig.SOAP_ACTION + OPERATION_NAME, envelope);
            respon.setJsonString(envelope.getResponse().toString());
            respon.setIsSuccsess(true);
        } catch (Exception exception) {
            respon.setIsSuccsess(false);
            respon.setJsonString("");
        }
        return respon;
    }
    public CallBack InsertCheckIn2(Integer TripEaID, String CheckInName, String CheckInAddress, String CheckInDate
            , String Latitude, String Longitude, String Remark, String base64String, Integer CheckInType, Integer TripEaCustomerID, Integer EnergyTypeID, String EnergyPrice, String DistrictID, Integer SchoolID) {

        sendParameterPropertyInfo("TripEaID", TripEaID, Integer.class);
        sendParameterPropertyInfo("CheckInName", CheckInName, String.class);
        sendParameterPropertyInfo("CheckInAddress", CheckInAddress, String.class);
        sendParameterPropertyInfo("CheckInDate", CheckInDate, String.class);
        sendParameterPropertyInfo("Latitude", Latitude, String.class);
        sendParameterPropertyInfo("Longitude", Longitude, String.class);
        sendParameterPropertyInfo("Remark", Remark, String.class);
        sendParameterPropertyInfo("base64String", base64String, String.class);
        sendParameterPropertyInfo("CheckInType", CheckInType, Integer.class);
        sendParameterPropertyInfo("TripEaCustomerID", TripEaCustomerID, Integer.class);
        sendParameterPropertyInfo("EnergyTypeID", EnergyTypeID, Integer.class);
        sendParameterPropertyInfo("EnergyPrice", EnergyPrice, String.class);
        sendParameterPropertyInfo("DistrictID", DistrictID, String.class);
        sendParameterPropertyInfo("SchoolID", SchoolID, Integer.class);

        envelope.setOutputSoapObject(request);
        try {
            httpTransport.call(WebServiceConfig.SOAP_ACTION + OPERATION_NAME, envelope);
            respon.setJsonString(envelope.getResponse().toString());
            respon.setIsSuccsess(true);
        } catch (Exception exception) {
            respon.setIsSuccsess(false);
            respon.setJsonString("");
        }
        return respon;
    }
    public CallBack InsertActivity(Integer CustomerCheckInID, Integer ActivityID, int SubjectID, String ContractName, String ContractTel, String ActivityRemark) {
        //   (int CustomerCheckInID, int ActivityID, int SubjectID, String ContractName, String ContractTel, String ActivityRemark)
        sendParameterPropertyInfo("CustomerCheckInID", CustomerCheckInID, Integer.class);
        sendParameterPropertyInfo("ActivityID", ActivityID, Integer.class);
        sendParameterPropertyInfo("SubjectID", SubjectID, Integer.class);
        sendParameterPropertyInfo("ContractName", ContractName, String.class);
        sendParameterPropertyInfo("ContractTel", ContractTel, String.class);
        sendParameterPropertyInfo("ActivityRemark", ActivityRemark, String.class);
        envelope.setOutputSoapObject(request);
        try {
            httpTransport.call(WebServiceConfig.SOAP_ACTION + OPERATION_NAME, envelope);
            respon.setJsonString(envelope.getResponse().toString());
            respon.setIsSuccsess(true);
        } catch (Exception exception) {
            respon.setIsSuccsess(false);
            respon.setJsonString("");
        }
        return respon;
    }

}