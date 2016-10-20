package com.app.fa.restaurantmate_v1.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.fa.restaurantmate_v1.Activity.ChoseFoodActivity;
import com.app.fa.restaurantmate_v1.Activity.OrderActivity;
import com.app.fa.restaurantmate_v1.R;

import java.util.List;

/**
 * Created by Foremost on 8/10/2559.
 */

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.MyViewHolder>{
    private Context mContext;
    private List<String[]> catList;

    //**************************view Holder**********************************
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text1;
        public TextView text2;
        public View view;

        public MyViewHolder(View view) {
            super(view);
            text1 = (TextView) view.findViewById(R.id.text1_textview);
            this.view = view;
            //text2 = (TextView) view.findViewById(R.id.text2_textview);
        }
    }


    public ReceiptAdapter(Context mContext, List<String[]> catList) {
        this.mContext = mContext;
        this.catList = catList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.receipt_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.text1.setText("ปลากระพงทอดน้ำปลา (1)");
        //holder.text2.setText(catList.get(position)[1]);
    }


    @Override
    public int getItemCount() {
        return catList.size();
    }


}
