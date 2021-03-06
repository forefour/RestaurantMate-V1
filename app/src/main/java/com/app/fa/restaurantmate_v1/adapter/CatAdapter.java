package com.app.fa.restaurantmate_v1.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.fa.restaurantmate_v1.Activity.ChoseFoodActivity;
import com.app.fa.restaurantmate_v1.MyApplication;
import com.app.fa.restaurantmate_v1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by Foremost on 8/10/2559.
 */

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.MyViewHolder>{
    private Context mContext;
    private List<DataSnapshot> catList;
    private MyApplication myApplication;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

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


    public CatAdapter(Context mContext, List<DataSnapshot> catList) {
        this.mContext = mContext;
        this.catList = catList;
        this.myApplication = (MyApplication)mContext.getApplicationContext();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_avatar_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.text1.setText(catList.get(position).child("name").getValue().toString());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Foremost","123");
                Intent intent = new Intent(mContext, ChoseFoodActivity.class);
                intent.putExtra("title",catList.get(position).getKey());
                mContext.startActivity(intent);
            }
        });
        //holder.text2.setText(catList.get(position)[1]);
    }


    @Override
    public int getItemCount() {
        return catList.size();
    }
}
