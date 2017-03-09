package com.checkin.maceducation.checkinapplication;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.checkin.maceducation.checkinapplication.AdapterData.CustomerListViewData;
import com.checkin.maceducation.checkinapplication.Entity.ListEntity_EA;
import com.checkin.maceducation.checkinapplication.QueryDatabase.CallSoap;
import com.checkin.maceducation.checkinapplication.QueryDatabase.GreenDaoApplication;
import com.checkin.maceducation.checkinapplication.Utility.CallBack;
import com.checkin.maceducation.checkinapplication.Utility.CheckInType;
import com.checkin.maceducation.checkinapplication.Utility.Utility;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import checkin.maceducation.daogenerator.CheckIn;
import checkin.maceducation.daogenerator.CheckInDao;
import checkin.maceducation.daogenerator.DaoSession;

public class CheckinDetailActivity extends AppCompatActivity {
    Intent intent;
    Double m_Longitude, m_Latitude, m_EnergyPrice;
    Long m_id;
    Integer m_EAID, m_TripEaID, m_CheckInType, m_TripEaCustomerID, m_EnergyTypeID = 0, m_SubjectID = null, m_ActivityID;
    String m_CardName, m_ActivityContact, m_ActivityRemark, m_ActivityTel, m_ActivityEmail, m_ActivityPosition;
    EditText edtLocationName, edtAddress, edtEnergyMoney, edtActivityContact, edtActivityRemark, edtActivityContactTel, edtActivityContractEmail, edtActivityContractPosition;
    TextView txtDateTime;
    ImageView imgLocation;
    RadioGroup rdgEnergy;
    RadioButton rdbNoEnnergy, rdbOil, rdbGas;
    Spinner spnActivity, spnSubject;
    List<Address> addresses;
    private String imgBase64, m_currentDate;
    String m_LocationName, m_Address, m_Remark, m_CheckInName;
    public static final int REQUEST_CAMERA = 2;
    CallBack cb;
    Utility utl;
    Calendar c;
    GreenDaoApplication mApplication;
    CheckInDao mCheckInDaoDao;
    DaoSession mDaoSession;

    private SaveDetailsCheckIn saveDetailsCheckIn;
    CheckIn dbTempCheckIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin_detail);
        GetIntentData();
        StartActivity();
        edtAddress.setText(getAddress(m_Latitude, m_Longitude).replace("null\n", ""), TextView.BufferType.EDITABLE);

        c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        m_currentDate = df.format(new Date(System.currentTimeMillis()));

        txtDateTime.setText(m_currentDate);
        if (m_CheckInType.equals(CheckInType.CheckInCustomer)) {
            edtLocationName.setText(m_CardName, TextView.BufferType.EDITABLE);
        } else {
            edtLocationName.setText(m_LocationName, TextView.BufferType.EDITABLE);
        }
        imgBase64 = ConvertencodeToString(imgLocation.getDrawingCache());
        rdgEnergy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.rdbGas) {
                    edtEnergyMoney.setEnabled(true);
                    m_EnergyTypeID = 2;

                } else if (checkedId == R.id.rdbOil) {
                    edtEnergyMoney.setEnabled(true);
                    m_EnergyTypeID = 1;
                } else {
                    edtEnergyMoney.setEnabled(false);
                    edtEnergyMoney.setText("0");
                    m_EnergyTypeID = 0;
                }
            }

        });
        spnActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    spnSubject.setEnabled(true);
                } else {
                    spnSubject.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void GetIntentData() {
        Intent intent = getIntent();
        m_Latitude = intent.getDoubleExtra("Latitude", 0);
        m_Longitude = intent.getDoubleExtra("Longitude", 0);
        m_EAID = intent.getIntExtra("EAID", 0);
        m_TripEaID = intent.getIntExtra("TripEaID", 0);
        m_CardName = intent.getStringExtra("CardName");
        m_CheckInType = intent.getIntExtra(CheckInType.CheckInType, 0);
        m_TripEaCustomerID = intent.getIntExtra("TripEaCustomerID", 0);

    }

    private void StartActivity() {


        edtLocationName = (EditText) findViewById(R.id.edtLocationName);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtActivityContactTel = (EditText) findViewById(R.id.edtActivityContactTel);
        edtActivityContractEmail = (EditText) findViewById(R.id.edtActivityContractEmail);
        edtActivityContractPosition = (EditText) findViewById(R.id.edtActivityContractPosition);
        txtDateTime = (TextView) findViewById(R.id.txtDateTime);
        imgLocation = (ImageView) findViewById(R.id.imgLocation);
//        edtRemark = (EditText) findViewById(R.id.edtRemark);
        utl = new Utility(CheckinDetailActivity.this);
        mApplication = (GreenDaoApplication) getApplication();
        mDaoSession = mApplication.getDaoSession();
        rdgEnergy = (RadioGroup) findViewById(R.id.rdgEnergy);
        rdbNoEnnergy = (RadioButton) findViewById(R.id.rdbNoEnnergy);
        rdbOil = (RadioButton) findViewById(R.id.rdbOil);
        rdbGas = (RadioButton) findViewById(R.id.rdbGas);
        edtEnergyMoney = (EditText) findViewById(R.id.edtEnergyMoney);

        spnActivity = (Spinner) findViewById(R.id.spnActivity);
        spnSubject = (Spinner) findViewById(R.id.spnSubject);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.activity_name, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnActivity.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this, R.array.subject_name, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSubject.setAdapter(adapter2);

        edtActivityContact = (EditText) findViewById(R.id.edtActivityContact);
        edtActivityRemark = (EditText) findViewById(R.id.edtActivityRemark);

        if (utl.isNetworkAvailable()) {
//            Toast.makeText(getApplication(), "NetworkAvailable", Toast.LENGTH_LONG).show();
        } else {
//            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
//            startActivity(intent);
            Toast.makeText(getApplication(), getString(R.string.strNetworkLost), Toast.LENGTH_LONG).show();
        }
    }

    private int GetActivityValue() {
        int spinner_pos = spnActivity.getSelectedItemPosition();
        String[] size_values = getResources().getStringArray(R.array.activity_id);
        int size = Integer.valueOf(size_values[spinner_pos]); //  1:เสนอขาย2:รับคืน3:วางบิล4:เก็บเงิน5:หาสมาชิกสมาคมครู6:หาสมาชิกกิตติมศักดิ์7:อบรมครู8:ประชุม
        return size;
    }

    private int GetSubjectValue() {
        int spinner_pos = spnSubject.getSelectedItemPosition();
        String[] size_values = getResources().getStringArray(R.array.subject_id);
        int size = Integer.valueOf(size_values[spinner_pos]); //  1:เสนอขาย2:รับคืน3:วางบิล4:เก็บเงิน5:หาสมาชิกสมาคมครู6:หาสมาชิกกิตติมศักดิ์7:อบรมครู8:ประชุม
        return size;
    }

    public void Click_Image(View v) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CAMERA);
        }
    }

    public void Click_Save(View v) {

        //  if (!(edtRemark.getText().toString().trim().length() == 0)) {
        // TODO Auto-generated method stub

        m_CheckInName = edtLocationName.getText().toString();
        m_Address = edtAddress.getText().toString();
        m_Remark ="";

        if (!edtEnergyMoney.getText().toString().equals("")) {
            try {
                m_EnergyPrice = Double.parseDouble(edtEnergyMoney.getText().toString());

            } catch (Exception ex) {
                m_EnergyPrice = 0.0;
            }

        } else {
            m_EnergyPrice = 0.0;
        }


        m_ActivityID = GetActivityValue();
        m_ActivityContact = edtActivityContact.getText().toString();
        m_ActivityRemark = edtActivityRemark.getText().toString();
        m_ActivityPosition = edtActivityContractPosition.getText().toString();
        m_ActivityEmail = edtActivityContractEmail.getText().toString();
        m_ActivityTel = edtActivityContactTel.getText().toString();
        m_SubjectID = GetSubjectValue();
        saveDetailsCheckIn = new SaveDetailsCheckIn(m_TripEaID, m_CheckInName, m_Address, m_currentDate
                , m_Latitude.toString(), m_Longitude.toString(), m_Remark, imgBase64, m_CheckInType, m_TripEaCustomerID, m_EnergyTypeID
                , m_EnergyPrice.toString(), m_ActivityID, m_ActivityContact, m_ActivityRemark, m_ActivityPosition, m_ActivityEmail, m_ActivityTel, m_SubjectID,m_EAID);

//        SaveDetailsCheckIn(Integer TripEaID, String CheckInName, String CheckInAddress, String CheckInDate
//                , String Latitude, String Longitude, String Remark, String base64String, Integer CheckInType,Integer TripEaCustomerID)
        saveDetailsCheckIn.execute((Void) null);
    }

    //    else
//    {
//        edtRemark.requestFocus();
//        edtRemark.setError(getResources().getString(R.string.strRequestActivity));
//    }
//}
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    final Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            imgLocation.setImageDrawable(null);
            imgLocation.destroyDrawingCache();
            Bundle extras = data.getExtras();
            Bitmap imagebitmap = (Bitmap) extras.get("data");
            imagebitmap = utl.CropCenterImage(imagebitmap, 100);
            if (imagebitmap != null) {
                imgLocation.setImageBitmap(imagebitmap);
                imgBase64 = ConvertencodeToString(imagebitmap);
            }

        }

    }


    private String ConvertencodeToString(Bitmap btm) {
        String encoded;
        try {
            if (btm != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                btm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            } else {
                encoded = "";
            }


        } catch (Exception ex) {
            encoded = "";
        }
        return encoded;
    }

    //get address
    private String getAddress(double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        try {
            Locale locale = new Locale("th");
            Geocoder geocoder = new Geocoder(this, locale);
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                //ชื่อซอย หรือชื่อตำบล
                result.append(address.getAddressLine(0)).append("\n");
                //อำเภอ
                result.append(address.getLocality()).append("\n");
                //จังหวัด
                result.append(address.getAdminArea()).append("\n");

//                //ชื่อประเทศ
//                result.append(address.getCountryName()).append("\n");

//                //รหัสประเทศ
//                result.append(address.getCountryCode()).append("\n");
                //รหัสไปรษณีย์
                result.append(address.getPostalCode().replace("null", "")).append("\n");
                m_LocationName = addresses.get(0).getThoroughfare();
            }

            return result.toString();
        } catch (IOException e) {
            return "";
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_checkin_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SaveDetailsCheckIn extends AsyncTask<Void, Void, CallBack> {

        private Integer strTripEaID = null;
        private String strCheckInName = "";
        private String strCheckInAddress = "";
        private String strCheckInDate = "";
        private String strLatitude = "";
        private String strLongitude = "";
        private String strRemark = "";
        private String strbase64String = "";
        private Integer strCheckInType = null;
        private Integer strTripEaCustomerID = null;
        private Integer strEnergyTypeID = null;
        private String strEnergyPrice = null;
        private Integer strActivityID = null;
        private String strActivityContact = "";
        private String strActivityRemark = "";
        private String strActivityPosition = "";
        private String strActivityEmail = "";
        private String strActivityTel = "";
        private Integer strSubjectID = null;
        private Integer strEAID= null;

        SaveDetailsCheckIn(Integer TripEaID, String CheckInName, String CheckInAddress, String CheckInDate
                , String Latitude, String Longitude, String Remark, String base64String, Integer CheckInType, Integer TripEaCustomerID, Integer EnergyTypeID
                , String EnergyPrice
                , Integer ActivityID, String ActivityContact, String ActivityRemark, String ActivityPosition, String ActivityEmail, String ActivityTel, Integer SubjectID,Integer EAID) {
            strTripEaID = TripEaID;
            strCheckInName = CheckInName;
            strCheckInAddress = CheckInAddress;
            strCheckInDate = CheckInDate;
            strLatitude = Latitude;
            strLongitude = Longitude;
            strRemark = Remark;
            strbase64String = base64String;
            strCheckInType = CheckInType;
            strTripEaCustomerID = TripEaCustomerID;
            strEnergyTypeID = EnergyTypeID;
            strEnergyPrice = EnergyPrice;
            strActivityID = ActivityID;
            strActivityContact = ActivityContact;
            strActivityRemark = ActivityRemark;
            strActivityPosition = ActivityPosition;
            strActivityEmail = ActivityEmail;
            strActivityTel = ActivityTel;
            strSubjectID = SubjectID;
            strEAID =  EAID;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            utl.showDialogProgres(getString(R.string.strLoading));
        }

        @Override
        protected CallBack doInBackground(Void... params) {
            //this is where you should write your authentication code
            // or call external service
            // following try-catch just simulates network access
//           ArrayList<Entity_EA> ea_List = new ArrayList<Entity_EA>();
            try {
                Thread.sleep(1000);

                CallSoap cs = new CallSoap("InsertCheckIn3");
                cb = cs.InsertCheckIn3(strTripEaID, strCheckInName, strCheckInAddress, strCheckInDate
                        , strLatitude, strLongitude, strRemark, strbase64String, strCheckInType, strTripEaCustomerID
                        , strEnergyTypeID, strEnergyPrice.toString()
                        , strActivityID, strActivityContact, strActivityRemark, strActivityPosition, strActivityEmail, strActivityTel, strSubjectID);

                if (cb.getIsSuccsess()) {
                    cb.setStrSuccess(getString(R.string.strSuccessSave));
                    cb.setIsSuccsess(true);
                } else {
                    cb.setStrError(getString((R.string.strErrorService)));
                    cb.setIsSuccsess(false);
                }
            } catch (Exception e) {


                cb.setStrError(getString((R.string.strErrorService)));
                cb.setIsSuccsess(false);
            }
            return cb;
        }

        @Override
        protected void onPostExecute(final CallBack cb) {

            if (cb.getIsSuccsess()) {
//


//                List<CheckIn> listCheckIn = mCheckInDaoDao.queryBuilder()
//                        .where(CheckInDao.Properties.Id.eq(m_id))
//                        .list();

                Toast.makeText(getApplication(), cb.getStrSuccess(), Toast.LENGTH_LONG).show();

            } else {
                mCheckInDaoDao = mDaoSession.getCheckInDao();
                List<CheckIn> listCheckIn = mCheckInDaoDao.queryBuilder()
                        .orderDesc(CheckInDao.Properties.Id)
                        .limit(1).list();

                if (listCheckIn.size() > 0) {
                    m_id = listCheckIn.get(0).getId() + 1;

                } else {
                    m_id = 0L;
                }

// CheckIn(Long id, Integer TripEaID, String CheckInName, String CheckInAddress, String CheckInDate, String Latitude, String Longitude,
// String Remark, String Image, Integer CheckInType, Integer StatusSend,Integer TripEaCustomerID, Integer EnergyTypeID, Double EnergyPrice, Integer ActivityID, String CheckInActivityRemark, String CheckInActivityContact,
// String CheckInActivityPosition, String CheckInActivityEmail, String CheckInActivityTel, Integer SubjectID, Integer EAID)

//                dbTempCheckIn = new CheckIn(m_id,strTripEaID, strCheckInName, strCheckInAddress, strCheckInDate
//                        , strLatitude, strLongitude, strRemark, strbase64String, strCheckInType, strTripEaCustomerID
//                        , strEnergyTypeID,Double.parseDouble( strEnergyPrice)
//                        , strActivityID, strActivityRemark, strActivityContact, strActivityPosition, strActivityEmail, strActivityTel, strSubjectID,strEAID);
                dbTempCheckIn = new CheckIn(m_id, strTripEaID, strCheckInName, strCheckInAddress, strCheckInDate, strLatitude.toString(), strLongitude.toString()
                        , strRemark, strbase64String, strCheckInType, 0, strTripEaCustomerID
                        , strEnergyTypeID, Double.parseDouble( strEnergyPrice), strActivityID, strActivityRemark, strActivityContact, strActivityPosition, strActivityEmail, strActivityTel, strSubjectID,strEAID);

                mCheckInDaoDao.insert(dbTempCheckIn);
                Toast.makeText(getApplication(), cb.getStrError(), Toast.LENGTH_LONG).show();
            }

            utl.CloseDialogProgres();
            intent = new Intent(CheckinDetailActivity.this, CheckInListActivity.class);
            intent.putExtra("TripEaID", m_TripEaID);
            intent.putExtra("EAID", m_EAID);
//            intent.putExtra("TripName","");
            startActivity(intent);
            finish();

        }

        @Override
        protected void onCancelled(CallBack callBack) {
            super.onCancelled(callBack);
            utl.CloseDialogProgres();
        }


    }
}


