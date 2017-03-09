package com.checkin.maceducation.checkinapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.checkin.maceducation.checkinapplication.QueryDatabase.CallSoap;
import com.checkin.maceducation.checkinapplication.Utility.CallBack;
import com.checkin.maceducation.checkinapplication.Utility.Utility;
import com.google.gson.Gson;

public class ActivityDetailsActivity extends AppCompatActivity {
    CallBack cb;
    Gson gson;
    Utility utl;
    Intent intent;
    EditText edtActivityContact,edtActivityContractPosition,edtActivityContractEmail,edtActivityContactTel,edtActivityRemark;
    Integer m_CheckInID, m_ActivityID,  m_SubjectID;
    String m_CheckInName;
    Spinner spnActivity, spnSubject;
    Long m_id;
    SaveActivityrTask saveActivityrTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_details);
        GetIntentData();
        StartActivity();
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
//        new ListDropDownTask().execute();

    }

    private void GetIntentData() {
        intent = getIntent();
        m_CheckInID = intent.getIntExtra("CheckInID", 0);
        m_CheckInName = intent.getStringExtra("CheckInName");

    }


    private void StartActivity() {
        utl = new Utility(ActivityDetailsActivity.this);
        spnActivity = (Spinner) findViewById(R.id.spnActivity);
        spnSubject = (Spinner) findViewById(R.id.spnSubject);
        edtActivityContact= (EditText)findViewById(R.id.edtActivityContact);
        edtActivityContractPosition= (EditText)findViewById(R.id.edtActivityContractPosition);
        edtActivityContractEmail= (EditText)findViewById(R.id.edtActivityContractEmail);
        edtActivityContactTel= (EditText)findViewById(R.id.edtActivityContactTel);
        edtActivityRemark= (EditText)findViewById(R.id.edtActivityRemark);

        if (utl.isNetworkAvailable()) {
         //   Toast.makeText(getApplication(), "NetworkAvailable", Toast.LENGTH_LONG).show();
        } else {
//            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
//            startActivity(intent);
            Toast.makeText(getApplication(), getString(R.string.strNetworkLost), Toast.LENGTH_LONG).show();
        }
//        mApplication = (GreenDaoApplication) getApplication();
//        mDaoSession = mApplication.getDaoSession();
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
    public void Click_SaveActivity(View v) {
        m_ActivityID= GetActivityValue();
        m_SubjectID=GetSubjectValue();
        saveActivityrTask = new SaveActivityrTask(m_CheckInID,m_ActivityID,edtActivityContact.getText().toString(),edtActivityRemark.getText().toString(),edtActivityContractPosition.getText().toString(),edtActivityContractEmail.getText().toString(),edtActivityContactTel.getText().toString(),m_SubjectID);
        saveActivityrTask.execute((Void) null);
    }

    public class SaveActivityrTask extends AsyncTask<Void, Void, CallBack> {


        private Integer strCheckInID = null;
        private Integer strActivityID = null;
        private String strCheckInActivityContact = null;
        private String strCheckInActivityRemark = null;
        private String strCheckInActivityPosition = null;
        private String strCheckInActivityEmail = null;
        private String strCheckInActivityTel = null;
        private Integer strSubjectID = null;
        SaveActivityrTask(Integer CheckInID, Integer ActivityID,String CheckInActivityContact,String CheckInActivityRemark,String CheckInActivityPosition,String CheckInActivityEmail, String CheckInActivityTel,Integer SubjectID) {
            strCheckInID = CheckInID;
            strActivityID = ActivityID;
            strCheckInActivityContact = CheckInActivityContact;
            strCheckInActivityRemark = CheckInActivityRemark;
            strCheckInActivityPosition = CheckInActivityPosition;
            strCheckInActivityEmail=CheckInActivityEmail;
            strCheckInActivityTel=CheckInActivityTel;
            strSubjectID = SubjectID;
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
                CallSoap cs = new CallSoap("InsertCheckInActivity");
                cb = cs.InsertCheckInActivity(strCheckInID, strActivityID,strCheckInActivityContact,strCheckInActivityRemark,strCheckInActivityPosition,strCheckInActivityEmail,strCheckInActivityTel,strSubjectID);
                if (cb.getIsSuccsess()) {
                    cb.setStrSuccess(getString(R.string.strSuccessSave));
                    cb.setIsSuccsess(true);
                } else {
                    cb.setStrError(getString((R.string.strErrorService)));
                    cb.setIsSuccsess(false);
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
                Toast.makeText(getApplication(), cb.getStrSuccess(), Toast.LENGTH_LONG).show();
            } else {
             //   mActivityDao = mDaoSession.getActivityDao();
//                List<Activity> listCustomerActivity = mActivityDao.queryBuilder()
//                        .where(ActivityDao.Properties.EAID.eq(m_EAID))
//                        .orderDesc(ActivityDao.Properties.Id)
//                        .limit(1).list();

//                if (listCustomerActivity.size() > 0) {
//                    m_id = listCustomerActivity.get(0).getId() + 1;
//
//                } else {
//                    m_id = 0L;
//                }
//               // (Long id, Integer CustomerCheckInID, Integer ActivityID, String ActivityName, Integer StatusSend, Integer EAID, Integer SubjectID
//               //  , String ContractName, String ContractTel, String ActivityRemark)
//                dbTempActivity = new Activity(m_id, strCustomerCheckInID, strActivityID, m_ActivityName, 0, m_EAID,m_SubjectID,
//                       strContractName,strContractTel,strActivityRemark );
//                mActivityDao.insert(dbTempActivity);

                Toast.makeText(getApplication(), cb.getStrError(), Toast.LENGTH_LONG).show();

            }

            utl.CloseDialogProgres();
            intent = new Intent(ActivityDetailsActivity.this, CheckInActivityListActivity.class);
            intent.putExtra("CheckInID", m_CheckInID);
            intent.putExtra("CheckInName", m_CheckInName);

            startActivity(intent);
            finish();
        }

        @Override
        protected void onCancelled(CallBack callBack) {
            super.onCancelled(callBack);
            utl.CloseDialogProgres();
        }


    }

       //
//    public class ListDropDownTask extends AsyncTask<Void, Void, CallBack> {
//
//
//        List<Entity_Subject> posts_S;
//        List<Entity_Activity> posts_A;
//        CallSoap cs;
//
//        ListDropDownTask() {
//
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            utl.showDialogProgres(getString(R.string.strLoading));
//        }
//
//        @Override
//        protected CallBack doInBackground(Void... params) {
//            //this is where you should write your authentication code
//            // or call external service
//            // following try-catch just simulates network access
////           ArrayList<Entity_EA> ea_List = new ArrayList<Entity_EA>();
//            try {
//
//                cs = new CallSoap("SelectSubjectMaster");
//                cb = cs.SelectSubjectMaster();
//                gson = new Gson();
//                ListEntity_Subject jsonResponse_S = gson.fromJson(cb.getJsonString(), ListEntity_Subject.class);
//                posts_S = jsonResponse_S.get$values();
//
//                cs = new CallSoap("SelectActivityMaster");
//                cb = cs.SelectActivityMaster();
//                gson = new Gson();
//                ListEntity_Activity jsonResponse_A = gson.fromJson(cb.getJsonString(), ListEntity_Activity.class);
//                posts_A = jsonResponse_A.get$values();
//
//
//            } catch (Exception e) {
//
//            }
//            return cb;
//        }
//
//
//        @Override
//        protected void onPostExecute(final CallBack cb) {
//            ArrayList<String> worldlist;
//            if (cb.getIsSuccsess()) {
//                if (posts_S.size() > 0) {
//                    worldlist = new ArrayList<String>();
//                    for (int i = 0; i < posts_S.size(); i++) {
//                        worldlist.add(i, posts_S.get(i).getSubjectName());
//                        spSubject.setAdapter(new ArrayAdapter<String>(ActivityDetailsActivity.this,
//                                android.R.layout.simple_dropdown_item_1line,
//                                worldlist));
//
//                    }
//                }
//                if (posts_A.size() > 0) {
//                    worldlist = new ArrayList<String>();
//                    for (int i = 0; i < posts_A.size(); i++) {
//
//                        worldlist.add(i, posts_A.get(i).getActivityName());
//                        spActivity.setAdapter(new ArrayAdapter<String>(ActivityDetailsActivity.this,
//                                android.R.layout.simple_dropdown_item_1line,
//                                worldlist));
//                    }
//                }
//                spSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> arg0,
//                                               View arg1, int position, long arg3) {
//                        // TODO Auto-generated method stub
//                        m_SubjectID = posts_S.get(position).getSubjectID();
//                        m_SubjectName = posts_S.get(position).getSubjectName();
//                        Toast.makeText(getApplicationContext(),posts_S.get(position).getSubjectID()+posts_S.get(position).getSubjectName(),Toast.LENGTH_SHORT).show();
//                    }
//                    @Override
//                    public void onNothingSelected(AdapterView<?> arg0) {
//                        // TODO Auto-generated method stub
//                    }
//                });
//                spActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//                    @Override
//                    public void onItemSelected(AdapterView<?> arg0,
//                                               View arg1, int position, long arg3) {
//                        // TODO Auto-generated method stub
//                        m_ActivityID = posts_A.get(position).getActivityID();
//                        m_ActivityName = posts_A.get(position).getActivityName();
////                        Toast.makeText(getApplicationContext(),posts_A.get(position).getActivityID()+ posts_A.get(position).getActivityName(),Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> arg0) {
//                        // TODO Auto-generated method stub
//                    }
//                });
//            }
//            utl.CloseDialogProgres();
//        }
//        @Override
//        protected void onCancelled(CallBack callBack) {
//            super.onCancelled(callBack);
//            utl.CloseDialogProgres();
//        }
//
//    }
}
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_activity_details, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

