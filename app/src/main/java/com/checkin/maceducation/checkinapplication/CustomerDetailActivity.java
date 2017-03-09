package com.checkin.maceducation.checkinapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.checkin.maceducation.checkinapplication.AdapterData.CustomerListViewData;
import com.checkin.maceducation.checkinapplication.Entity.Entity_Customer;
import com.checkin.maceducation.checkinapplication.Entity.ListEntity_Customer;
import com.checkin.maceducation.checkinapplication.QueryDatabase.CallSoap;
import com.checkin.maceducation.checkinapplication.Utility.CallBack;
import com.checkin.maceducation.checkinapplication.Utility.Utility;
import com.google.gson.Gson;

import java.util.List;

public class CustomerDetailActivity extends AppCompatActivity {
    CallBack cb;
    Gson gson;
    Utility utl;
    Intent intent;
    String m_CardName;
    Integer m_CardCode;
    TextView txtCardName,txtCustomerName,txtAddress,txtTel,txtProvince,txtContactName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);
        GetIntentData();
        StartActivity();
        new ListCustomerTask().execute();
    }
    private void GetIntentData()
    {
        intent = getIntent();
        m_CardCode= intent.getIntExtra("CardCode", 0);
        m_CardName= intent.getStringExtra("CardName");
    }


    private void StartActivity() {
        utl =new Utility(CustomerDetailActivity.this);

        if (utl.isNetworkAvailable())
        {
         //   Toast.makeText(getApplication(), "NetworkAvailable", Toast.LENGTH_LONG).show();
        }else
        {
//            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
//            startActivity(intent);
            Toast.makeText(getApplication(), getString(R.string.strNetworkLost), Toast.LENGTH_LONG).show();
        }
        txtCardName= (TextView)findViewById(R.id.txtCardName);
        txtCardName.setText(m_CardName);

        txtCustomerName= (TextView)findViewById(R.id.txtCustomerName);
        txtAddress= (TextView)findViewById(R.id.txtAddress);
        txtTel= (TextView)findViewById(R.id.txtTel);
        txtProvince= (TextView)findViewById(R.id.txtProvince);
        txtContactName= (TextView)findViewById(R.id.txtContactName);

    }
    public class ListCustomerTask extends AsyncTask<Void, Void, CallBack> {


        List<Entity_Customer> posts;
        ListCustomerTask() {

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
                CallSoap cs = new CallSoap("SelectCustomerDetail");
                cb = cs.SelectCustomerDetail(m_CardCode);
                gson =new Gson();
                ListEntity_Customer jsonResponse = gson.fromJson(cb.getJsonString(), ListEntity_Customer.class);
                posts = jsonResponse.get$values();

                if (posts.size()>0) {


                    cb.setIsSuccsess(true);
                    cb.setStrSuccess("Success");
                    return cb;
                } else {
                    cb.setIsSuccsess(false);
                    cb.setStrError("No data");
                    return cb;
                }


            } catch (Exception e) {
                cb.setIsSuccsess(false);
                cb.setStrError("cannot call webservice");
                return cb;
            }

        }


        @Override
        protected void onPostExecute(final CallBack cb) {

            if(cb.getIsSuccsess()) {
                txtCustomerName.setText(posts.get(0).getCardName());
                txtAddress.setText(posts.get(0).getAddress());
                txtTel.setText(posts.get(0).getPhone());
                txtProvince.setText(posts.get(0).getCounty());
                txtContactName.setText(posts.get(0).getSlpName());
            }else

            {

            }
            utl.CloseDialogProgres();


        }

        @Override
        protected void onCancelled(CallBack callBack) {
            super.onCancelled(callBack);
            utl.CloseDialogProgres();
        }


    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_customer_detail, menu);
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
}
