package com.checkin.maceducation.checkinapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.checkin.maceducation.checkinapplication.AdapterData.CheckInActivityListViewData;
import com.checkin.maceducation.checkinapplication.Entity.Entity_CheckInActivityList;
import com.checkin.maceducation.checkinapplication.Entity.ListEntity_CheckInActivity;
import com.checkin.maceducation.checkinapplication.QueryDatabase.CallSoap;
import com.checkin.maceducation.checkinapplication.Utility.CallBack;
import com.checkin.maceducation.checkinapplication.Utility.Utility;
import com.google.gson.Gson;

import java.util.List;

public class CheckInActivityListActivity extends AppCompatActivity {
    CallBack cb;
    Utility utl;
    Intent intent;
    Integer m_CheckInID;
  String m_CheckInName;
    Gson gson;
    CheckInActivityListViewData checkInActivityListViewData;
    ListView listCheckinActivity;
    TextView txtCheckInName;

//    List<Activity> listTempActivity = null;
//    GreenDaoApplication mApplication;
//    ActivityDao mActivityDaoDao;
//    DaoSession mDaoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkinactivity);
        GetIntentData();
        StartActivity();
        new ListActivityCheckInTask().execute();
    }

    private void GetIntentData() {
        Intent intent = getIntent();
        m_CheckInID = intent.getIntExtra("CheckInID", 0);
        m_CheckInName= intent.getStringExtra("CheckInName");
    }

    public void Click_AddActivity(View v) {
        intent = new Intent(CheckInActivityListActivity.this, ActivityDetailsActivity.class);
        intent.putExtra("CheckInID", m_CheckInID);
        intent.putExtra("CheckInName", m_CheckInName);
        CheckInActivityListActivity.this.startActivity(intent);
        finish();

    }

    private void StartActivity() {
        utl = new Utility(CheckInActivityListActivity.this);
        if (utl.isNetworkAvailable()) {
            //     Toast.makeText(getApplication(), "NetworkAvailable", Toast.LENGTH_LONG).show();
        } else {
//            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
//            startActivity(intent);
            Toast.makeText(getApplication(), getString(R.string.strNetworkLost), Toast.LENGTH_LONG).show();
        }
        txtCheckInName = (TextView) findViewById(R.id.txtCheckInName);
        txtCheckInName.setText(m_CheckInName);
        listCheckinActivity = (ListView) findViewById(R.id.listCheckinActivity);
//        mApplication = (GreenDaoApplication) getApplication();
//        mDaoSession = mApplication.getDaoSession();


    }

    public class ListActivityCheckInTask extends AsyncTask<Void, Void, CallBack> {


        List<Entity_CheckInActivityList> posts;

        ListActivityCheckInTask() {

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
               // mActivityDaoDao = mDaoSession.getActivityDao();

//               String x= mCustomerActivityDaoDao.getTablename();
//
//                listTempActivity = mActivityDaoDao.queryBuilder()
//                        .where(ActivityDao.Properties.StatusSend.eq(0), ActivityDao.Properties.EAID.eq(m_EAID), ActivityDao.Properties.CustomerCheckInID.eq(m_CustomerCheckInID)
//                        ).list();
                //   listTempActivity = mCustomerActivityDaoDao.queryBuilder().list();

                CallSoap cs = new CallSoap("SelectCheckInActivity");
                cb = cs.SelectCheckInActivity(m_CheckInID);
                gson = new Gson();
                ListEntity_CheckInActivity jsonResponse = gson.fromJson(cb.getJsonString(), ListEntity_CheckInActivity.class);
                posts = jsonResponse.get$values();

//                if (posts.size() > 0) {
//                    if (listTempActivity.size() > 0) {
//                        for (Activity item : listTempActivity) {
//                            //(int customerActivityID, int customerCheckInID, int activityID, String activityName, int statusSend,int subjectID,String contractName,String contractTel
//                            // String activityRemark, Long tempCustomerActivityID)
//                            posts.add(new Entity_ActivityList(0, item.getCustomerCheckInID(), item.getActivityID(), item.getActivityName(), item.getStatusSend(), item.getSubjectID(), item.getContractName(), item.getContractTel(), item.getActivityRemark(), item.getId()));
//                        }
//                    }
//                } else {
//
//                }
                if (posts.size() > 0) {

                    cb.setIsSuccsess(true);
                    cb.setStrSuccess(getString(R.string.strSuccess));

                } else {
                    cb.setIsSuccsess(false);
                    cb.setStrError(getString(R.string.strNodata));


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
                checkInActivityListViewData = new CheckInActivityListViewData(CheckInActivityListActivity.this, posts);

                listCheckinActivity.setAdapter(checkInActivityListViewData);
                Toast.makeText(getApplication(),cb.getStrSuccess(),Toast.LENGTH_SHORT).show();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            new ListActivityCheckInTask().execute();
        }

        return super.onOptionsItemSelected(item);
    }
}
