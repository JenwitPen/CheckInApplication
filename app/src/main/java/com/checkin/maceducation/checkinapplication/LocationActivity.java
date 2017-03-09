package com.checkin.maceducation.checkinapplication;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.widget.Toast;

import com.checkin.maceducation.checkinapplication.QueryDatabase.GreenDaoApplication;
import com.checkin.maceducation.checkinapplication.Utility.BestLocationListener;
import com.checkin.maceducation.checkinapplication.Utility.BestLocationProvider;
import com.checkin.maceducation.checkinapplication.Utility.CheckInType;
import com.checkin.maceducation.checkinapplication.Utility.Utility;
import com.google.android.gms.maps.model.LatLng;

import checkin.maceducation.daogenerator.CheckIn;
import checkin.maceducation.daogenerator.CheckInDao;
import checkin.maceducation.daogenerator.DaoSession;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
public class LocationActivity extends Activity {
    Integer m_EAID, m_TripEaID, m_CheckInType, m_TripEaCustomerID;
    Intent intent;
    private static String TAG = "BestLocationProvider";
    private Button btnCheckIn;
    String m_CardName;
    private TextView txtlatlong;
    List<CheckIn> listTempCheckIn = null;
    private BestLocationProvider mBestLocationProvider;
    private BestLocationListener mBestLocationListener;
    Utility utl;
    GreenDaoApplication mApplication;
    CheckInDao mCheckInDaoDao;
    DaoSession mDaoSession;
    LatLng startPosition = null;
    List<Address> addresses;  /* GPS Constant Permission */
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;
    private String mProviderName;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        StartActivity();
        GetIntentData();
    }

    private void StartActivity() {
        utl = new Utility(LocationActivity.this);

        txtlatlong = (TextView) findViewById(R.id.txtlatlong);
        txtlatlong.setText("");
        startPosition = null;

        btnCheckIn = (Button) findViewById(R.id.btnCheckIn);
        if (utl.isNetworkAvailable()) {

        } else {

            Toast.makeText(getApplication(), getString(R.string.strNetworkLost), Toast.LENGTH_LONG).show();
        }

        mApplication = (GreenDaoApplication) getApplication();
        mDaoSession = mApplication.getDaoSession();
        mCheckInDaoDao = mDaoSession.getCheckInDao();


        startPosition = new LatLng(13.851867, 100.567519);
        txtlatlong.setText("พิกัด :"+startPosition.latitude+","+startPosition.longitude);
        // API 23: we have to check if ACCESS_FINE_LOCATION and/or ACCESS_COARSE_LOCATION permission are granted

    }

    private void GetIntentData() {
        intent = getIntent();
        m_EAID = intent.getIntExtra("EAID", 0);
        m_TripEaID = intent.getIntExtra("TripEaID", 0);
        m_CheckInType = intent.getIntExtra(CheckInType.CheckInType, 0);
        m_TripEaCustomerID = intent.getIntExtra("TripEaCustomerID", 0);
        m_CardName = intent.getStringExtra("CardName");
    }

    public void Click_MyLocation(View v)
    {
        //startPosition = new LatLng(13.851867, 100.567519);
        if(txtlatlong.getText()!=""&&startPosition!=null) {
            String adddress=getAddress(startPosition.latitude,startPosition.longitude);
          if(  adddress==""){
              Toast.makeText(this, "ไม่สามารถหาที่อยู่ได้", Toast.LENGTH_SHORT);
          }else
          {
              ShowAlertDialogGetAddress(adddress);
          }
        }else
        {
            Toast.makeText(this,"ไม่สามารถหาพิกัดได้",Toast.LENGTH_SHORT).show();
        }
    }
    public void Click_CheckinLocation(View v) {

        listTempCheckIn = mCheckInDaoDao.queryBuilder()
                .where(CheckInDao.Properties.StatusSend.eq(0), CheckInDao.Properties.EAID.eq(m_EAID), CheckInDao.Properties.TripEaID.eq(m_TripEaID))
                .list();
        if (listTempCheckIn.size() > 0) {
            ShowAlertDialog();
        } else {

            if (txtlatlong.getText() != "") {


                    intent = new Intent(LocationActivity.this, CheckinDetailActivity.class);
                    intent.putExtra("Latitude", startPosition.latitude);
                    intent.putExtra("Longitude", startPosition.longitude);
                    intent.putExtra("EAID", m_EAID);
                    intent.putExtra("TripEaID", m_TripEaID);
                    intent.putExtra("CardName", m_CardName);
                    intent.putExtra("TripEaCustomerID", m_TripEaCustomerID);
                    intent.putExtra(CheckInType.CheckInType, m_CheckInType);
                    this.startActivity(intent);
                    finish();

            } else {
                Toast.makeText(LocationActivity.this
                        , getResources().getString(R.string.strLocationFail), Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void ShowAlertDialogGetAddress(String Address) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //  builder.setTitle("Do this action");
        builder.setMessage(Address);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });


        AlertDialog alert = builder.create();
        alert.show();
    }
    private void ShowAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //  builder.setTitle("Do this action");
        builder.setMessage(getString(R.string.strTempLocation));

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do do my action here
                intent = new Intent(LocationActivity.this, CheckInListActivity.class);
                intent.putExtra("TripEaID", m_TripEaID);
                intent.putExtra("EAID", m_EAID);

//        intent.putExtra("TripName",m_TripName);
                startActivity(intent);
                dialog.dismiss();
                finish();
            }

        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // I do not need any action here you might
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
    private String getAddress(double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        try {
            Locale locale = new Locale("th");
            Geocoder geocoder = new Geocoder(this, locale);
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                //ชื่อซอย หรือชื่อตำบล
                result.append(address.getAddressLine(0)).append("\t");
                //อำเภอ
                result.append(address.getLocality()).append("\t");
                //จังหวัด
                result.append(address.getAdminArea()).append("\t");

//                //ชื่อประเทศ
                result.append(address.getCountryName()).append("\t");

//                //รหัสประเทศ
//                result.append(address.getCountryCode()).append("\n");
                //รหัสไปรษณีย์
                result.append(address.getPostalCode().replace("null", "")).append("\t");

            }

            return result.toString();
        } catch (IOException e) {
            return "";
        }}

    private void initLocation() {
        if (mBestLocationListener == null) {

            mBestLocationListener = new BestLocationListener() {

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    Log.i(TAG, "onStatusChanged PROVIDER:" + provider + " STATUS:" + String.valueOf(status));
                }

                @Override
                public void onProviderEnabled(String provider) {
                    Log.i(TAG, "onProviderEnabled PROVIDER:" + provider);
                }

                @Override
                public void onProviderDisabled(String provider) {
                    Log.i(TAG, "onProviderDisabled PROVIDER:" + provider);
                }

                @Override
                public void onLocationUpdateTimeoutExceeded(BestLocationProvider.LocationType type) {
                    Log.w(TAG, "onLocationUpdateTimeoutExceeded PROVIDER:" + type);
                }

                @Override
                public void onLocationUpdate(Location location, BestLocationProvider.LocationType type,
                                             boolean isFresh) {

                    txtlatlong.setText("พิกัด :"+location.getLatitude()+","+location.getLongitude());
                    startPosition = new LatLng(location.getLatitude(), location.getLongitude());
                }
            };

            if (mBestLocationProvider == null) {
                mBestLocationProvider = new BestLocationProvider(this, true, true, 5000, 500, 2, 0);
            }
        }
    }

    @Override
    protected void onResume() {
        StartActivity();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            initLocation();

            mBestLocationProvider.startLocationUpdatesWithListener(mBestLocationListener);

            // One or both permissions are denied.
        } else {
            // The ACCESS_COARSE_LOCATION is denied, then I request it and manage the result in
            // onRequestPermissionsResult() using the constant MY_PERMISSION_ACCESS_FINE_LOCATION
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSION_ACCESS_COARSE_LOCATION);
            }
            // The ACCESS_FINE_LOCATION is denied, then I request it and manage the result in
            // onRequestPermissionsResult() using the constant MY_PERMISSION_ACCESS_FINE_LOCATION
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this,
                        new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                        MY_PERMISSION_ACCESS_FINE_LOCATION);
            }
        }


        super.onResume();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                } else {
                    // permission denied
                }
                break;

                case MY_PERMISSION_ACCESS_FINE_LOCATION: {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // permission was granted
                    } else {
                        // permission denied
                    }
                    break;
                }


        }}
    @Override
    protected void onPause() {
        initLocation();
        mBestLocationProvider.stopLocationUpdates();


        super.onPause();
    }

}