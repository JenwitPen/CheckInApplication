package com.checkin.maceducation.checkinapplication.AdapterData;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.checkin.maceducation.checkinapplication.CustomerDetailActivity;
import com.checkin.maceducation.checkinapplication.Entity.Entity_Customer;

import com.checkin.maceducation.checkinapplication.LocationActivity;
import com.checkin.maceducation.checkinapplication.R;
import com.checkin.maceducation.checkinapplication.Utility.CheckInType;

/**
 * Created by jenwit on 15/9/2558.
 */
public class CustomerListViewData extends BaseAdapter {
    Intent intent;
    private LayoutInflater mInflater;
    private Context context; //รับ Context จาก CustomListViewActivity
    private List<Entity_Customer>  listData ;
    private ArrayList<Entity_Customer> arraylist;
    int m_EAID;
    public CustomerListViewData(Context c,List<Entity_Customer> listDa,int EAID) {
        // TODO Auto-generated constructor stub
        this.context = c;
        this.mInflater = LayoutInflater.from(context);
        this.listData = listDa;
        this.arraylist = new ArrayList<Entity_Customer>();
        this.arraylist.addAll(listData);
        this.m_EAID=EAID;
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

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_customer_row, null);
        }

            TextView   txtLocationName = (TextView) convertView.findViewById(R.id.txtLocationName);
            txtLocationName.setText(listData.get(position).getCardName());
        if (listData.get(position).getCustomerType().equals("L")) {

            txtLocationName.setTextColor(Color.BLACK);
        }
        else    if (listData.get(position).getCustomerType().equals("C"))
        {

            txtLocationName.setTextColor(Color.BLUE);
        }else
        {

            txtLocationName.setTextColor(Color.RED);
        }


//            ImageButton imgBtnActivity = (ImageButton)convertView.findViewById(R.id.imgBtnActivity);
//        if(listData.get(position).getTotal()>0) {
//            imgBtnActivity.setVisibility(View.VISIBLE);
//        }else {
//            imgBtnActivity.setVisibility(View.INVISIBLE);
//        }

//            imgBtnActivity.setOnClickListener(new OnClickListener()
//            {
//                @Override
//                public void onClick(View v)
//                {
//                    // Your code that you want to execute on this button click
//                    intent = new Intent(context, CustomerCheckInListActivity.class);
//                    intent.putExtra("TripEaCustomerID", listData.get(position).getTripEaCustomerID());
//                    intent.putExtra("EAID", m_EAID);
//                    intent.putExtra(CheckInType.CheckInType,CheckInType.CheckInCustomer);
//                            context.startActivity(intent);
//                }
//
//
//            });
            ImageButton imgBtnCheckin = (ImageButton)convertView.findViewById(R.id.imgBtnCheckin);
            imgBtnCheckin.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    // Your code that you want to execute on this button click
                    intent = new Intent(context, LocationActivity.class);

                    intent.putExtra("TripEaID", listData.get(position).getTripEaID());
                    intent.putExtra("CardCode", listData.get(position).getCardCode());
                    intent.putExtra("CardCode", listData.get(position).getCardCode());
                    intent.putExtra("TripEaCustomerID", listData.get(position).getTripEaCustomerID());
                    intent.putExtra("CardName", listData.get(position).getCardName());

                    intent.putExtra("EAID",m_EAID);
                    context.startActivity(intent);

                }

            });
        convertView.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                intent = new Intent(context, CustomerDetailActivity.class);
                intent.putExtra("CardCode", listData.get(position).getCardCode());
                intent.putExtra("CardName", listData.get(position).getCardName());
                intent.putExtra("EAID",m_EAID);
                context.startActivity(intent);

            }
        });

        return convertView;
    }
    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        listData.clear();
        if (charText.length() == 0) {
            listData.addAll(arraylist);
        }
        else
        {
            for (Entity_Customer wp : arraylist)
            {
                if (wp.getCardName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    listData.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}