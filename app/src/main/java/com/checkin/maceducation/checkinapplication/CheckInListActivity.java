package com.checkin.maceducation.checkinapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.checkin.maceducation.checkinapplication.AdapterData.CheckInListViewData;
import com.checkin.maceducation.checkinapplication.Entity.Entity_CheckInList;
import com.checkin.maceducation.checkinapplication.Entity.ListEntity_CheckInList;
import com.checkin.maceducation.checkinapplication.QueryDatabase.CallSoap;
import com.checkin.maceducation.checkinapplication.QueryDatabase.GreenDaoApplication;
import com.checkin.maceducation.checkinapplication.Utility.CallBack;
import com.checkin.maceducation.checkinapplication.Utility.Utility;
import com.google.gson.Gson;

import java.util.List;

import checkin.maceducation.daogenerator.CheckIn;
import checkin.maceducation.daogenerator.CheckInDao;
import checkin.maceducation.daogenerator.DaoSession;


public class CheckInListActivity extends AppCompatActivity {
    CallBack cb;
    Gson gson;
    Utility utl;
    Intent intent;
    private ListView listCheckIn;
    //    private TextView txtTripName;
//    private String m_TripName;
    Integer m_EAID, m_TripEaID;
    CheckInListViewData checkInListViewData;
    GreenDaoApplication mApplication;
    CheckInDao mCheckInDaoDao;
    DaoSession mDaoSession;
    List<CheckIn> listTempCheckIn=null;
    List<Entity_CheckInList> ListCheckIn=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_list);
        StartActivity();
        GetIntentData();
        new ListCheckInTask().execute();
    }

    private void GetIntentData() {
        intent = getIntent();
        m_TripEaID = intent.getIntExtra("TripEaID", 0);
        m_EAID= intent.getIntExtra("EAID", 0);
//        m_TripName= intent.getStringExtra("TripName");
//        txtTripName.setText(m_TripName);
    }


    private void StartActivity() {
        utl = new Utility(CheckInListActivity.this);
        listCheckIn = (ListView) findViewById(R.id.listCheckIn);
//        txtTripName= (TextView)findViewById(R.id.txtTripName);
        mApplication = (GreenDaoApplication) getApplication();
        mDaoSession = mApplication.getDaoSession();
        mCheckInDaoDao = mDaoSession.getCheckInDao();
        if (utl.isNetworkAvailable()) {
//            Toast.makeText(getApplication(), "NetworkAvailable", Toast.LENGTH_LONG).show();
        } else {
//            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
//            startActivity(intent);
            Toast.makeText(getApplication(), getString(R.string.strNetworkLost), Toast.LENGTH_LONG).show();
        }
    }


    public class ListCheckInTask extends AsyncTask<Void, Void, CallBack> {




        ListCheckInTask() {

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
                listTempCheckIn = mCheckInDaoDao.queryBuilder()
                        .where(CheckInDao.Properties.StatusSend.eq(0), CheckInDao.Properties.EAID.eq(m_EAID), CheckInDao.Properties.TripEaID.eq(m_TripEaID))
                        .list();
                CallSoap cs = new CallSoap("SelectCheckInList");
                cb = cs.SelectCheckInList(m_TripEaID);
                gson = new Gson();
                ListEntity_CheckInList jsonResponse = gson.fromJson(cb.getJsonString(), ListEntity_CheckInList.class);
                ListCheckIn = jsonResponse.get$values();

                if (ListCheckIn.size() > 0) {

                    cb.setIsSuccsess(true);
                    cb.setStrSuccess(getString(R.string.strSuccess));

                }
                else {
                    cb.setIsSuccsess(true);
                    cb.setStrError(getString(R.string.strNodata));

                }

                if (listTempCheckIn.size() > 0) {
                    for (CheckIn item : listTempCheckIn) {
//                            (Long id, Integer TripEaID, String CheckInName, String CheckInAddress, String CheckInDate, String Latitude, String Longitude, String Remark, String Image, Integer CheckInType, Integer StatusSend, Integer TripEaCustomerID,
//                              Integer EnergyTypeID, Double EnergyPrice, Integer ActivityID, String CheckInActivityRemark, String CheckInActivityContact, String CheckInActivityPosition, String CheckInActivityEmail, String CheckInActivityTel, Integer SubjectID, Integer EAID) {
                        ListCheckIn.add(new Entity_CheckInList(0,  item.getTripEaID()
                                , item.getCheckInName(), item.getCheckInAddress(), item.getCheckInDate(),  item.getLatitude(), item.getLongitude()
                                , item.getRemark(), item.getImage()
                                , item.getCheckInType(), item.getStatusSend(),item.getTripEaCustomerID(),item.getEnergyTypeID(),item.getEnergyPrice(),item.getId()
                                ,item.getActivityID(),item.getCheckInActivityRemark(),item.getCheckInActivityContact(),item.getCheckInActivityPosition(),item.getCheckInActivityEmail(),item.getCheckInActivityTel(),item.getSubjectID(),item.getEAID()));


                    }
                    cb.setIsSuccsess(true);
                    cb.setStrSuccess(getString(R.string.strSuccess));
                }else
                {

                }
            } catch (Exception e) {
                cb.setIsSuccsess(false);
                cb.setStrError(getString((R.string.strErrorService)));

            }
            return cb;
        }


        @Override
        protected void onPostExecute(final CallBack cb) {

            if (cb.getIsSuccsess()) {
                checkInListViewData = new CheckInListViewData(CheckInListActivity.this, ListCheckIn, listTempCheckIn, mApplication,m_EAID);
                listCheckIn.setAdapter(checkInListViewData);
                Toast.makeText(getApplication(), cb.getStrSuccess(), Toast.LENGTH_SHORT).show();
            }else {

                Toast.makeText(getApplication(),cb.getStrError(),Toast.LENGTH_LONG).show();
            }
            utl.CloseDialogProgres();


        }

        @Override
        protected void onCancelled(CallBack callBack) {
            super.onCancelled(callBack);
            utl.CloseDialogProgres();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_check_in_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

       if (id == R.id.action_refresh) {
            new ListCheckInTask().execute();
        }

        return super.onOptionsItemSelected(item);
    }
}
