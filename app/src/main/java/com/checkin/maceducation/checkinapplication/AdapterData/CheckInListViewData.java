package com.checkin.maceducation.checkinapplication.AdapterData;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.checkin.maceducation.checkinapplication.CheckInActivityListActivity;
import com.checkin.maceducation.checkinapplication.CheckInListActivity;
import com.checkin.maceducation.checkinapplication.Entity.Entity_CheckInList;
import com.checkin.maceducation.checkinapplication.QueryDatabase.CallSoap;
import com.checkin.maceducation.checkinapplication.QueryDatabase.GreenDaoApplication;
import com.checkin.maceducation.checkinapplication.R;
import com.checkin.maceducation.checkinapplication.Utility.CallBack;
import com.checkin.maceducation.checkinapplication.Utility.Utility;

import java.util.List;

import checkin.maceducation.daogenerator.CheckIn;
import checkin.maceducation.daogenerator.CheckInDao;
import checkin.maceducation.daogenerator.DaoSession;

/**
 * Created by jenwit on 15/9/2558.
 */
public class CheckInListViewData extends BaseAdapter {
    Intent intent;
    private LayoutInflater mInflater;
    private Context context; //รับ Context จาก CustomListViewActivity
    private List<Entity_CheckInList> listData;
    private List<CheckIn> listTempCheckIn;
    CallBack cb;
    Utility utl;
    private SaveDetailsCheckIn saveDetailsCheckIn = null;
    private SaveActivityrTask saveActivityrTask = null;
    CheckIn dbCheckIn;
    GreenDaoApplication mApplication;
    CheckInDao mCheckInDaoDao;
    DaoSession mDaoSession;
    int m_EAID;
    View promptsView;

    public CheckInListViewData(Context c, List<Entity_CheckInList> listD, List<CheckIn> listCheckIn, GreenDaoApplication mApplication, int EAID) {
        // TODO Auto-generated constructor stub
        context = c;
        mInflater = LayoutInflater.from(context);
        listData = listD;
        this.listTempCheckIn = listCheckIn;
        utl = new Utility(context);
        this.mApplication = mApplication;
        mDaoSession = mApplication.getDaoSession();
        mCheckInDaoDao = mDaoSession.getCheckInDao();
        m_EAID = EAID;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return listData.size(); //ส่งขนาดของ List ที่เก็บข้อมุลอยู่
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        final int positionlist = getCount() - position - 1;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_checkin_row, null);
        }
        TextView txtCheckIName = (TextView) convertView.findViewById(R.id.txtCheckIName);
        TextView txtCheckInDate = (TextView) convertView.findViewById(R.id.txtCheckInDate);
        txtCheckIName.setText(positionlist + 1 + " : " + listData.get(positionlist).getCheckInName());
        String date = listData.get(positionlist).getCheckInDate().replace('T', ' ');
        txtCheckInDate.setText(date.substring(0, date.length() - 3));
        ImageButton imgResend = (ImageButton) convertView.findViewById(R.id.imgResend);
        ImageButton imgDelete = (ImageButton) convertView.findViewById(R.id.imgDelete);
        ImageButton imgAddActivity = (ImageButton) convertView.findViewById(R.id.imgAddActivity);

        if (listData.get(positionlist).getStatusSend() != null) {
            if (listData.get(positionlist).getStatusSend() == 1) {
                imgResend.setVisibility(View.GONE);
                imgDelete.setVisibility(View.GONE);
                imgAddActivity.setVisibility(View.VISIBLE);
            } else {
                imgResend.setVisibility(View.VISIBLE);
                imgDelete.setVisibility(View.VISIBLE);
                imgAddActivity.setVisibility(View.GONE);
            }
        } else {
            imgResend.setVisibility(View.GONE);
            imgDelete.setVisibility(View.GONE);
            imgAddActivity.setVisibility(View.GONE);
        }
//            TextView  txtCheckInDate = (TextView) convertView.findViewById(R.id.txtCheckInDate);
//            txtCheckInDate.setText(listData.get(position).getCheckInDate());
        imgResend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                saveDetailsCheckIn = new SaveDetailsCheckIn(listData.get(positionlist).getTempCheckInId(), listData.get(positionlist).getTripEaID(), listData.get(positionlist).getCheckInName()
                        , listData.get(positionlist).getCheckInAddress(), listData.get(positionlist).getCheckInDate()
                        , listData.get(positionlist).getLatitude(), listData.get(positionlist).getLongitude()
                        , listData.get(positionlist).getRemark(), listData.get(positionlist).getImage()
                        , listData.get(positionlist).getCheckInType(), listData.get(positionlist).getTripEaCustomerID()
                        , listData.get(positionlist).getEnergyTypeID(), listData.get(positionlist).getEnergyPrice()
                        , listData.get(positionlist).getActivityID(), listData.get(positionlist).getCheckInActivityRemark(), listData.get(positionlist).getCheckInActivityContact()
                        , listData.get(positionlist).getCheckInActivityPosition(), listData.get(positionlist).getCheckInActivityEmail(), listData.get(positionlist).getCheckInActivityTel(), listData.get(positionlist).getSubjectID(), listData.get(positionlist).getEAID());
                saveDetailsCheckIn.execute((Void) null);
            }
        });
        imgDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                CheckIn(Long id, Integer TripEaID, String CheckInName, String CheckInAddress, String CheckInDate, String Latitude, String Longitude, String Remark, String Image, Integer CheckInType, Integer StatusSend, Integer TripEaCustomerID,
//                        Integer EnergyTypeID, Double EnergyPrice, Integer ActivityID, String CheckInActivityRemark, String CheckInActivityContact, String CheckInActivityPosition, String CheckInActivityEmail, String CheckInActivityTel, Integer SubjectID, Integer EAID) {
                dbCheckIn = new CheckIn(listData.get(positionlist).getTempCheckInId(), listData.get(positionlist).getTripEaID(), listData.get(positionlist).getCheckInName()
                        , listData.get(positionlist).getCheckInAddress(), listData.get(positionlist).getCheckInDate()
                        , listData.get(positionlist).getLatitude(), listData.get(positionlist).getLongitude()
                        , listData.get(positionlist).getRemark(), listData.get(positionlist).getImage()
                        , listData.get(positionlist).getCheckInType(), 1, listData.get(positionlist).getTripEaCustomerID()
                        , listData.get(positionlist).getEnergyTypeID(), listData.get(positionlist).getEnergyPrice()
                        , listData.get(positionlist).getActivityID(), listData.get(positionlist).getCheckInActivityRemark(), listData.get(positionlist).getCheckInActivityContact()
                        , listData.get(positionlist).getCheckInActivityPosition(), listData.get(positionlist).getCheckInActivityEmail(), listData.get(positionlist).getCheckInActivityTel(), listData.get(positionlist).getSubjectID(), listData.get(positionlist).getEAID());
                mCheckInDaoDao.update(dbCheckIn);

                intent = new Intent(context, CheckInListActivity.class);
                intent.putExtra("TripEaID", listData.get(positionlist).getTripEaID());
                intent.putExtra("EAID", m_EAID);
//        intent.putExtra("TripName",m_TripName);
                context.startActivity(intent);
                ((Activity) context).finish();
                Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();

            }
        });
        imgAddActivity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                                intent = new Intent(context, CheckInActivityListActivity.class);
                intent.putExtra("CheckInID", listData.get(position).getCheckInID());
                intent.putExtra("CheckInName", listData.get(position).getCheckInName());
                context.startActivity(intent);
              //  ((Activity) context).finish();
                // get prompts.xml view
//                LayoutInflater li = LayoutInflater.from(context);
//                promptsView = li.inflate(R.layout.pop_up_activity, null);
//
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                        context);
//
//                // set prompts.xml to alertdialog builder
//                alertDialogBuilder.setView(promptsView);
//
//                final EditText edtActivityContact = (EditText) promptsView
//                        .findViewById(R.id.edtActivityContact);
//                final EditText edtActivityRemark = (EditText) promptsView
//                        .findViewById(R.id.edtActivityRemark);
//
//                final EditText edtActivityContactTel = (EditText) promptsView
//                        .findViewById(R.id.edtActivityContactTel);
//                final EditText edtActivityContractEmail = (EditText) promptsView
//                        .findViewById(R.id.edtActivityContractEmail);
//
//                final EditText edtActivityContractPosition = (EditText) promptsView
//                        .findViewById(R.id.edtActivityContractPosition);
//
//                final Spinner spnActivity = (Spinner) promptsView
//                        .findViewById(R.id.spnActivity);
//                final Spinner spnSubject = (Spinner) promptsView
//                        .findViewById(R.id.spnSubject);
//
//                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//                        context, R.array.activity_name, android.R.layout.simple_spinner_item);
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spnActivity.setAdapter(adapter);
//
//                ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
//                        context, R.array.subject_name, android.R.layout.simple_spinner_item);
//                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spnSubject.setAdapter(adapter2);
//
//                spnActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                        if (position == 0) {
//                            spnSubject.setEnabled(true);
//                        } else {
//                            spnSubject.setEnabled(false);
//                        }
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//                    }
//                });
//                // set dialog message
//                alertDialogBuilder
//                        .setCancelable(false)
//                        .setPositiveButton("OK",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        // get user input and set it to result
//                                        // edit text
//                                        saveActivityrTask = new SaveActivityrTask(listData.get(positionlist).getCheckInID(), GetActivityValue(spnActivity)
//                                                , edtActivityContact.getText().toString(), edtActivityRemark.getText().toString()
//                                        ,edtActivityContractPosition.getText().toString(),edtActivityContractEmail.getText().toString(),edtActivityContactTel.getText().toString(),GetActivityValue(spnSubject));
//                                        saveActivityrTask.execute((Void) null);
//                                        dialog.cancel();
//                                    }
//                                })
//                        .setNegativeButton("Cancel",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//
//                                    }
//                                });
//
//                // create alert dialog
//                AlertDialog alertDialog = alertDialogBuilder.create();
//
//                // show it
//                alertDialog.show();
            }
        });
        return convertView;
    }

    private int GetActivityValue(Spinner spnActivity) {
        int spinner_pos = spnActivity.getSelectedItemPosition();
        String[] size_values = context.getResources().getStringArray(R.array.activity_id);
        int size = Integer.valueOf(size_values[spinner_pos]); //  1:เสนอขาย2:รับคืน3:วางบิล4:เก็บเงิน5:หาสมาชิกสมาคมครู6:หาสมาชิกกิตติมศักดิ์7:อบรมครู8:ประชุม
        return size;
    }

    private int GetSubjectValue(Spinner spnSubject) {
        int spinner_pos = spnSubject.getSelectedItemPosition();
        String[] size_values = context.getResources().getStringArray(R.array.subject_id);
        int size = Integer.valueOf(size_values[spinner_pos]); //  1:เสนอขาย2:รับคืน3:วางบิล4:เก็บเงิน5:หาสมาชิกสมาคมครู6:หาสมาชิกกิตติมศักดิ์7:อบรมครู8:ประชุม
        return size;
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
        private Integer strEAID;
        private Long strTempCheckInId;
        private Integer strEnergyTypeID = null;
        private Double strEnergyPrice = null;
        private Integer strActivityID = null;

        private String strActivityContact = "";
        private String strActivityRemark = "";
        private String strActivityPosition = "";
        private String strActivityEmail = "";
        private String strActivityTel = "";
        private Integer strSubjectID = null;

        SaveDetailsCheckIn(Long TempCheckInId, Integer TripEaID, String CheckInName, String CheckInAddress, String CheckInDate
                , String Latitude, String Longitude, String Remark, String base64String, Integer CheckInType, Integer TripEaCustomerID, Integer EnergyTypeID
                , Double EnergyPrice
                , Integer ActivityID, String ActivityContact, String ActivityRemark, String ActivityPosition, String ActivityEmail, String ActivityTel, Integer SubjectID, Integer EAID) {


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
            strTempCheckInId = TempCheckInId;
            strEAID = EAID;
            strEnergyTypeID = EnergyTypeID;
            strEnergyPrice = EnergyPrice;
            strActivityID = ActivityID;
            strActivityContact = ActivityContact;
            strActivityRemark = ActivityRemark;
            strActivityPosition = ActivityPosition;
            strActivityEmail = ActivityEmail;
            strActivityTel = ActivityTel;
            strSubjectID = SubjectID;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            utl.showDialogProgres(context.getString(R.string.strLoading));
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
                        , strLatitude, strLongitude, strRemark, strbase64String, strCheckInType, strTripEaCustomerID,
                        strEnergyTypeID, strEnergyPrice.toString(), strActivityID
                        , strActivityContact,
                        strActivityRemark,
                        strActivityPosition,
                        strActivityEmail,
                        strActivityTel,
                        strSubjectID);

                return cb;

            } catch (Exception e) {
                cb.setIsSuccsess(false);
                cb.setStrError("cannot call webservice");
                return cb;
            }
        }

        @Override
        protected void onPostExecute(final CallBack cb) {

            if (cb.getIsSuccsess()) {

// CheckIn(Long id, Integer TripEaID, String CheckInName, String CheckInAddress, String CheckInDate, String Latitude, String Longitude,
// String Remark, String Image, Integer CheckInType, Integer StatusSend,Integer TripEaCustomerID, Integer EnergyTypeID, Double EnergyPrice, Integer ActivityID, String CheckInActivityRemark, String CheckInActivityContact,
// String CheckInActivityPosition, String CheckInActivityEmail, String CheckInActivityTel, Integer SubjectID, Integer EAID)
                dbCheckIn = new CheckIn(strTempCheckInId, strTripEaID, strCheckInName, strCheckInAddress, strCheckInDate
                        , strLatitude, strLongitude
                        , strRemark, strbase64String, strCheckInType, 1, strTripEaCustomerID, strEnergyTypeID
                        , strEnergyPrice, strActivityID, strActivityContact,
                        strActivityRemark,
                        strActivityPosition,
                        strActivityEmail,
                        strActivityTel,
                        strSubjectID, strEAID);
                mCheckInDaoDao.update(dbCheckIn);
                intent = new Intent(context, CheckInListActivity.class);
                intent.putExtra("TripEaID", strTripEaID);
                intent.putExtra("EAID", m_EAID);
//        intent.putExtra("TripName",m_TripName);
                context.startActivity(intent);
                ((Activity) context).finish();
                Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(context, "False", Toast.LENGTH_LONG).show();
            }

            utl.CloseDialogProgres();


        }

        @Override
        protected void onCancelled(CallBack callBack) {
            super.onCancelled(callBack);
            utl.CloseDialogProgres();
        }


    }

    public class SaveActivityrTask extends AsyncTask<Void, Void, CallBack> {


        private Integer strCheckInID = null;
        private Integer strActivityID = null;
        private String strCheckInActivityContact = "";
        private String strCheckInActivityRemark = "";
        private String strCheckInActivityPosition = "";
        private String strCheckInActivityEmail = "";
        private String strCheckInActivityTel = "";
        private Integer strSubjectID = null;
        ;

        SaveActivityrTask(Integer CheckInID, Integer ActivityID, String CheckInActivityContact, String CheckInActivityRemark
                , String CheckInActivityPosition
                , String CheckInActivityEmail
                , String CheckInActivityTel
                , Integer SubjectID) {
            strCheckInID = CheckInID;
            strActivityID = ActivityID;
            strCheckInActivityContact = CheckInActivityContact;
            strCheckInActivityRemark = CheckInActivityRemark;
            strCheckInActivityPosition = CheckInActivityPosition;
            strCheckInActivityEmail = CheckInActivityEmail;
            strCheckInActivityTel = CheckInActivityTel;
            strSubjectID = SubjectID;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            utl.showDialogProgres(context.getString(R.string.strLoading));
        }

        @Override
        protected CallBack doInBackground(Void... params) {
            //this is where you should write your authentication code
            // or call external service
            // following try-catch just simulates network access
//           ArrayList<Entity_EA> ea_List = new ArrayList<Entity_EA>();
            try {
                Thread.sleep(1000);

                CallSoap cs = new CallSoap("InsertCheckInActivity");
                cb = cs.InsertCheckInActivity(strCheckInID, strActivityID, strCheckInActivityContact, strCheckInActivityRemark , strCheckInActivityPosition
                        , strCheckInActivityEmail
                        , strCheckInActivityTel
                        , strSubjectID);

                return cb;

            } catch (Exception e) {
                cb.setIsSuccsess(false);
                cb.setStrError("cannot call webservice");
                return cb;
            }
        }


        @Override
        protected void onPostExecute(final CallBack cb) {

            if (cb.getIsSuccsess()) {


                Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(context, "False", Toast.LENGTH_LONG).show();
            }

            utl.CloseDialogProgres();
        }

        @Override
        protected void onCancelled(CallBack callBack) {
            super.onCancelled(callBack);
            utl.CloseDialogProgres();
        }


    }
}