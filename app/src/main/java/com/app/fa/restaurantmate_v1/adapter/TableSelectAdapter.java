package com.app.fa.restaurantmate_v1.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.TextView;

import com.app.fa.restaurantmate_v1.Activity.OrderActivity;
import com.app.fa.restaurantmate_v1.MainActivity;
import com.app.fa.restaurantmate_v1.MyApplication;
import com.app.fa.restaurantmate_v1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by Foremost on 8/10/2559.
 */

public class TableSelectAdapter extends RecyclerView.Adapter<TableSelectAdapter.MyViewHolder>{
    private Context mContext;
    private List<Integer> tableList;
    private MyApplication myApplication;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    //**************************view Holder**********************************
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text1;
        //public TextView text2;
        public View view;

        public MyViewHolder(View view) {
            super(view);
            text1 = (TextView) view.findViewById(R.id.table);
            this.view = view;
        }
    }


    public TableSelectAdapter(Context mContext, List<Integer> tableList) {
        this.mContext = mContext;
        this.tableList = tableList;
        this.myApplication = (MyApplication)mContext.getApplicationContext();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_select_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.text1.setText(tableList.get(position).toString());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tableNum = tableList.get(position).toString();
                final DatabaseReference activeTable = myApplication.getDatabaseReference().child("activeTable");
                final DatabaseReference orderRef = myApplication.getDatabaseReference().child("order");

                activeTable.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean b = true;
                        for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                            if(messageSnapshot.getKey().equals(tableNum)){
                                String orderId = messageSnapshot.child("orderId").getValue().toString();
                                Intent intent = new Intent(mContext, OrderActivity.class);
                                intent.putExtra("tableNum",tableNum);
                                Log.d("ifmeet",orderId);
                                intent.putExtra("orderId",orderId);
                                mContext.startActivity(intent);
                                b = false;
                            }
                        }
                        if(b != false){
                            DatabaseReference orderPush = orderRef.push();
                            String orderID = orderPush.getKey();
                            Log.d("orderID",orderID);
                            orderPush.child("tablenum").setValue(tableNum);

                            activeTable.child(tableList.get(position).toString()).child("orderId").setValue(orderID);

                            Intent intent = new Intent(mContext, OrderActivity.class);
                            intent.putExtra("tableNum",tableNum);
                            intent.putExtra("orderId",orderID);
                            mContext.startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });
    }


    @Override
    public int getItemCount() {
        return tableList.size();
    }
}
