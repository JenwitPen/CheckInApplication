package com.checkin.maceducation.checkinapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ComponentName;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Paint;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import com.checkin.maceducation.checkinapplication.Entity.*;
import com.checkin.maceducation.checkinapplication.QueryDatabase.*;
import com.checkin.maceducation.checkinapplication.Utility.CallBack;
import com.checkin.maceducation.checkinapplication.Utility.Utility;
import com.google.gson.Gson;


/**
 * Android login screen Activity
 */
public class LoginActivity extends Activity implements LoaderCallbacks<Cursor> {


    private UserLoginTask userLoginTask = null;
    private View loginFormView;
    private View progressView;
    private AutoCompleteTextView emailTextView;
    private EditText passwordTextView;
    private TextView signUpTextView;
    SharedPreferences sp;
    CallBack cb;
    Utility utl;
    List<Entity_EA> list_ent_ea;
    final String P_NAME = "App_Config";
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        StartActivity();


        sp = getSharedPreferences(P_NAME, Context.MODE_PRIVATE);
        String strEmail = sp.getString("USERNAME", "");
        emailTextView = (AutoCompleteTextView) findViewById(R.id.email);
        emailTextView.setText(strEmail);



        passwordTextView = (EditText) findViewById(R.id.password);
        passwordTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_NULL) {
                    initLogin();
                    return true;
                }
                return false;
            }
        });

        Button loginButton = (Button) findViewById(R.id.email_sign_in_button);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                initLogin();
            }
        });

        loginFormView = findViewById(R.id.login_form);
        progressView = findViewById(R.id.login_progress);

    }
    private void StartActivity() {
        utl = new Utility(LoginActivity.this);

        checkGPS();
        checkInternet();





    }
    private void checkInternet()
    {
        if (!utl.isNetworkAvailable())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Internet ไม่ทำงาน");  // GPS not found
            builder.setMessage("คุณต้องเปิด Internet เพื่อใช้งาน Application"); // Want to enable?
            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("ไม่", null);
            builder.create().show();
            return;
        }

    }
private  void checkGPS()
{
    try{

    if(  utl.isGpsAvailabel())  {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GPS ไม่ทำงาน");  // GPS not found
        builder.setMessage("คุณต้องเปิด GPS เพื่อความแม่นยำ"); // Want to enable?
        builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        builder.setNegativeButton("ไม่", null);
        builder.create().show();
        return;
    }}catch (Exception ex){

    }
}



    /**
     * Validate Login form and authenticate.
     */
    public void initLogin() {
        if (userLoginTask != null) {
            return;
        }

        emailTextView.setError(null);
        passwordTextView.setError(null);

        String email = emailTextView.getText().toString();
        String password = passwordTextView.getText().toString();

        boolean cancelLogin = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordTextView.setError(getString(R.string.invalid_password));
            focusView = passwordTextView;
            cancelLogin = true;
        }

        if (TextUtils.isEmpty(email)) {
            emailTextView.setError(getString(R.string.field_required));
            focusView = emailTextView;
            cancelLogin = true;
        } else if (!isEmailValid(email)) {
            emailTextView.setError(getString(R.string.invalid_email));
            focusView = emailTextView;
            cancelLogin = true;
        }

        if (cancelLogin) {
            // error in login
            focusView.requestFocus();
        } else {
            // show progress spinner, and start background task to login
//            showProgress(true);
            sp = getSharedPreferences(P_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("USERNAME", email);
            editor.commit();
            userLoginTask = new UserLoginTask(email, password);
            userLoginTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //add your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //add your own logic
        return password.length() > 2;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        emailTextView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Async Login Task to authenticate
     */
    public class UserLoginTask extends AsyncTask<Void, Void, CallBack> {

        private final String emailStr;
        private final String passwordStr;

        UserLoginTask(String email, String password) {
            emailStr = email;
            passwordStr = password;
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

                CallSoap cs = new CallSoap("SelectEA");
                cb = cs.SelectEA(emailStr, passwordStr);
                Gson gson =new Gson();
                ListEntity_EA jsonResponse = gson.fromJson(cb.getJsonString(), ListEntity_EA.class);
                list_ent_ea = jsonResponse.get$values();


                if (list_ent_ea.size()>0) {
                    cb.setIsSuccsess(true);
                    cb.setStrSuccess("Success");
                    return cb;
                } else {
                    cb.setIsSuccsess(false);
                    cb.setStrError(getString(R.string.incorrect_password));
                    return cb;
                }


            } catch (Exception e) {
                cb.setIsSuccsess(false);
                cb.setStrError("Cannot call webservice");
                return cb;
            }

        }

        @Override
        protected void onPostExecute(final CallBack cb) {
            userLoginTask = null;
            //stop the progress spinner
//            showProgress(false);
            utl.CloseDialogProgres();

            if (cb.getIsSuccsess()) {
                intent = new Intent(LoginActivity.this, CustomerListActivity.class);
                intent.putExtra("EAID",list_ent_ea.get(0).getEAID());
                intent.putExtra("TripName",list_ent_ea.get(0).getTripName());
                intent.putExtra("TripEaID",list_ent_ea.get(0).getTripEaID());
                LoginActivity.this.startActivity(intent);

            } else {
                // login failure
//                passwordTextView.setError(getString(R.string.incorrect_password));
//                passwordTextView.requestFocus();
                Toast.makeText(getApplication(), cb.getStrError(), Toast.LENGTH_LONG).show();
//            }
            }
        }
        @Override
        protected void onCancelled() {
            userLoginTask = null;
//            showProgress(false);
        }
    }
    @Override
    protected void onResume() {


        super.onResume();
    }
}
