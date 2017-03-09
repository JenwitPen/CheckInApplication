package com.checkin.maceducation.checkinapplication;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.checkin.maceducation.checkinapplication.AdapterData.CustomerListViewData;
import com.checkin.maceducation.checkinapplication.Utility.CallBack;
import com.checkin.maceducation.checkinapplication.Entity.Entity_Customer;
import com.checkin.maceducation.checkinapplication.Entity.ListEntity_Customer;
import com.checkin.maceducation.checkinapplication.QueryDatabase.CallSoap;
import com.checkin.maceducation.checkinapplication.Utility.CheckInType;
import com.checkin.maceducation.checkinapplication.Utility.Utility;
import com.google.gson.Gson;

import java.util.List;

public class CustomerListActivity extends AppCompatActivity {
    CallBack cb;
    Gson gson;
    Utility utl;
    Intent intent;
   // TabHost mTabHost;
    String tab_1 = "tab_1", tab_2 = "tab_2", tab_3 = "tab_3";
    private ListView listCustomerTab1, listCustomerTab2, listCustomerTab3,listCustomer;
    private TextView txtTripName;
    private String m_TripName;
    Integer m_EAID, m_TripEaID;
    Button btnFreeLocation, btnCheckInList;
    CustomerListViewData customerListViewData;
    SearchView searchView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        GetIntentData();
        StartActivity();
//        Toast.makeText(this,, Toast.LENGTH_LONG).show();
        showToastMessage(getString(R.string.strpopup));


        new ListCustomerTask().execute();
//        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
//            @Override
//            public void onTabChanged(String tabId) {
//                if (tabId.equals(tab_1)) {
//                    customerListViewData.clear();
//                    new ListCustomerTask("L",m_CustomerName).execute();
//                } else if (tabId.equals(tab_2)) {
//                    customerListViewData.clear();
//                    new ListCustomerTask("B",m_CustomerName).execute();
//                } else {
//                    customerListViewData.clear();
//                    new ListCustomerTask("C",m_CustomerName).execute();
//                }
//
//            }
//        });

    }


    private void GetIntentData() {
        intent = getIntent();
        m_EAID = intent.getIntExtra("EAID", 0);
        m_TripEaID = intent.getIntExtra("TripEaID", 0);
        m_TripName = intent.getStringExtra("TripName");
    }


    private void StartActivity() {
        utl = new Utility(CustomerListActivity.this);
//        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
//        mTabHost.setup();

//        mTabHost.addTab(mTabHost.newTabSpec(tab_1).setIndicator("ลูกค้าใหม่").setContent(R.id.listCustomerTab1));
//        mTabHost.addTab(mTabHost.newTabSpec(tab_2).setIndicator("ลูกค้าผ่านร้าน").setContent(R.id.listCustomerTab2));
//        mTabHost.addTab(mTabHost.newTabSpec(tab_3).setIndicator("ลูกค้าปัจจุบัน").setContent(R.id.listCustomerTab3));
//        mTabHost.setCurrentTab(0);

//        listCustomerTab1 = (ListView) findViewById(R.id.listCustomerTab1);
//        listCustomerTab2 = (ListView) findViewById(R.id.listCustomerTab2);
//        listCustomerTab3 = (ListView) findViewById(R.id.listCustomerTab3);
        listCustomer = (ListView) findViewById(R.id.listCustomer);
        btnFreeLocation = (Button) findViewById(R.id.btnFreeLocation);
        btnCheckInList = (Button) findViewById(R.id.btnCheckInList);
        txtTripName = (TextView) findViewById(R.id.txtTripName);
        if (m_TripEaID != 0) {
            txtTripName.setText(m_TripName);
            btnFreeLocation.setEnabled(true);
            btnCheckInList.setEnabled(true);

       //     mTabHost.getTabWidget().setEnabled(true);
        } else {
            btnFreeLocation.setEnabled(false);
            btnCheckInList.setEnabled(false);
           // mTabHost.getTabWidget().setEnabled(false);

            txtTripName.setText( getString(R.string.strNotrip));
        }
        if (utl.isNetworkAvailable()) {
//            Toast.makeText(getApplication(),"NetworkAvailable",Toast.LENGTH_LONG).show();
        } else {
//            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
//            startActivity(intent);
            Toast.makeText(getApplication(), getString(R.string.strNetworkLost), Toast.LENGTH_LONG).show();
        }

    }
    public void showToastMessage(String text){


        AlertDialog.Builder alert = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
        LayoutInflater inflater = this.getLayoutInflater();

        alert.setView(inflater.inflate(R.layout.popup_first, null));

        alert.create().show();

    }
    public void Click_CheckInList(View v) {

        intent = new Intent(CustomerListActivity.this, CheckInListActivity.class);
        intent.putExtra("TripEaID", m_TripEaID);
        intent.putExtra("EAID", m_EAID);
//        intent.putExtra("TripName",m_TripName);
        this.startActivity(intent);
    }

    //    public void Click_CheckIn(View v) {
//
//                intent = new Intent(CustomerListActivity.this, LocationActivity.class);
//        intent.putExtra("EAID",m_EAID);
//        intent.putExtra("TripEaID", m_TripEaID);
//        intent.putExtra("CardName", "");
//        intent.putExtra(CheckInType.CheckInType, CheckInType.CheckInLead);
//        this.startActivity(intent);
//    }
    public void Click_CheckInFree(View v) {


        intent = new Intent(CustomerListActivity.this, LocationActivity.class);
        intent.putExtra("EAID", m_EAID);
        intent.putExtra("TripEaID", m_TripEaID);
        intent.putExtra("CardName", "");
        intent.putExtra(CheckInType.CheckInType, CheckInType.CheckInFreeLocation);
        this.startActivity(intent);

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
                CallSoap cs = new CallSoap("SelectCustomerList");
                cb = cs.SelectCustomerList(m_EAID);
                gson = new Gson();
                ListEntity_Customer jsonResponse = gson.fromJson(cb.getJsonString(), ListEntity_Customer.class);
                posts = jsonResponse.get$values();


                if (posts.size() > 0) {

//                    m_TripName = posts.get(0).getTripName();
//                    m_TripEaID = posts.get(0).getTripEaID();

                    cb.setIsSuccsess(true);
                    cb.setStrSuccess(getString(R.string.strSuccess));
//
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


                customerListViewData = new CustomerListViewData(CustomerListActivity.this, posts, m_EAID);
//if (customerListViewData.getCount()>0) {
//
//    if (strCustomerType.equals("L")) {
//        listCustomerTab1.setAdapter(customerListViewData);
//    } else if (strCustomerType.equals("B")) {
//        listCustomerTab2.setAdapter(customerListViewData);
//    } else {
//        listCustomerTab3.setAdapter(customerListViewData);
//    }
//}
                listCustomer.setAdapter(customerListViewData);
                txtTripName.setText(m_TripName);
//                Toast.makeText(getApplication(), cb.getStrSuccess(), Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(getApplication(), cb.getStrError(), Toast.LENGTH_LONG).show();
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
        getMenuInflater().inflate(R.menu.menu_customer, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        searchView.setSearchableInfo(searchableInfo);
        searchView.setIconifiedByDefault(false);
        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {

                customerListViewData.filter(newText);
              //  m_CustomerName=newText;
                //   listCustomer.setAdapter(customerListViewData);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
               customerListViewData.filter(query);
             //   m_CustomerName=query;
                //  listCustomer.setAdapter(customerListViewData);

                return true;
            }
        };


        searchView.setOnQueryTextListener(textChangeListener);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_refresh) {
            new ListCustomerTask().execute();


                searchView.setQuery("", false);
                searchView.clearFocus();
          //      m_CustomerName="";


//            if (mTabHost.getCurrentTabTag().equals(tab_1)) {
//                new ListCustomerTask("L",m_CustomerName).execute();
//            } else if (mTabHost.getCurrentTabTag().equals(tab_2)) {
//                new ListCustomerTask("B",m_CustomerName).execute();
//            } else {
//                new ListCustomerTask("C",m_CustomerName).execute();
////            }

        }
        return super.onOptionsItemSelected(item);
    }

}
