package com.checkin.maceducation.checkinapplication.AdapterData;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.checkin.maceducation.checkinapplication.CheckInActivityListActivity;
import com.checkin.maceducation.checkinapplication.Entity.Entity_CheckInActivityList;
import com.checkin.maceducation.checkinapplication.QueryDatabase.CallSoap;
import com.checkin.maceducation.checkinapplication.QueryDatabase.GreenDaoApplication;
import com.checkin.maceducation.checkinapplication.R;
import com.checkin.maceducation.checkinapplication.Utility.CallBack;
import com.checkin.maceducation.checkinapplication.Utility.Utility;

import java.util.List;


/**
 * Created by jenwit on 15/9/2558.
 */
public class CheckInActivityListViewData extends BaseAdapter {
    /// Intent intent;
    private LayoutInflater mInflater;
    private Context context; //รับ Context จาก CustomListViewActivity
    private List<Entity_CheckInActivityList> listData;
//    GreenDaoApplication mApplication;
//    DaoSession mDaoSession;


    public CheckInActivityListViewData(Context c, List<Entity_CheckInActivityList> listD) {
        // TODO Auto-generated constructor stub
        this.context = c;
        this.mInflater = LayoutInflater.from(context);
   //     mDaoSession = mApplication.getDaoSession();
        this.listData = listD;


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
            convertView = inflater.inflate(R.layout.list_checkinactivity_row, null);
        }
        TextView txtActivity = (TextView) convertView.findViewById(R.id.txtActivity);
        txtActivity.setText(positionlist + 1 + " : " + listData.get(positionlist).getActivityName());

        TextView txtActivityDate = (TextView) convertView.findViewById(R.id.txtActivityDate);
        String date = listData.get(positionlist).getCreateDate().replace('T', ' ');
        txtActivityDate.setText(date.substring(0, date.length() - 3));


        return convertView;
    }

//    public class SaveActivityrTask extends AsyncTask<Void, Void, CallBack> {
//
//
//        private Integer strCustomerCheckInID = null;
//        private Integer strActivityID = null;
//        private Long strTempCustomerActivityID = null;
//        private String strActivityName = "";
//        private Integer strEAID = null;
//        private String strCheckInName = "";
//        private Integer  strSubjectID=null;
//        private String      strContractName= "";
//        private String  strContractTel= "";
//        private String    strActivityRemark= "";
//        SaveActivityrTask(Long TempCustomerActivityID, Integer CustomerCheckInID, Integer ActivityID, String ActivityName, Integer EAID, String CheckInName,Integer SubjectID,String ContractName,String ContractTel,String ActivityRemark) {
//            strCustomerCheckInID = CustomerCheckInID;
//            strActivityID = ActivityID;
//            strTempCustomerActivityID = TempCustomerActivityID;
//            strActivityName = ActivityName;
//            strEAID = EAID;
//            strCheckInName = CheckInName;
//           strSubjectID=SubjectID;
//            strContractName= ContractName;
//           strContractTel= ContractTel;
//            strActivityRemark= ActivityRemark;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            //     utl.showDialogProgres(context.getString(R.string.strLoading));
//        }
//
//        @Override
//        protected CallBack doInBackground(Void... params) {
//            try {
//
//
//                CallSoap cs = new CallSoap("InsertActivity");
//                cb = cs.InsertActivity(strCustomerCheckInID, strActivityID,strSubjectID,strContractName,strContractTel,strActivityRemark);
//
//
//            } catch (Exception e) {
//
//
//            }
//            return cb;
//        }
//
//
//        @Override
//        protected void onPostExecute(final CallBack cb) {
//
//            if (cb.getIsSuccsess()) {
//
//                dbActivity = new Activity(strTempCustomerActivityID, strCustomerCheckInID, strActivityID, strActivityName, 1, strEAID,strSubjectID,strContractName,strContractTel,strActivityRemark);
//                mActivityDao.update(dbActivity);
//                intent = new Intent(context, CheckInActivityListActivity.class);
//                intent.putExtra("CustomerCheckInID", strCustomerCheckInID);
//                intent.putExtra("CheckInName", strCheckInName);
//                intent.putExtra("EAID", m_EAID);
//                context.startActivity(intent);
//                ((android.app.Activity) context).finish();
//                Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
//            }
//
//
//        }
//
//        @Override
//        protected void onCancelled(CallBack callBack) {
//            super.onCancelled(callBack);
//            //       utl.CloseDialogProgres();
//        }
//
//
//    }

}