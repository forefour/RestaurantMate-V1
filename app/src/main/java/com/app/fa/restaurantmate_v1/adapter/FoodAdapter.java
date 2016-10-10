package com.app.fa.restaurantmate_v1.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.app.fa.restaurantmate_v1.Activity.ChoseFoodActivity;
import com.app.fa.restaurantmate_v1.R;

import java.util.List;

/**
 * Created by Foremost on 8/10/2559.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder>{
    private Context mContext;
    private List<String[]> catList;

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


    public FoodAdapter(Context mContext, List<String[]> catList) {
        this.mContext = mContext;
        this.catList = catList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_avatar_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.text1.setText(catList.get(position)[0]);
        //holder.text2.setText(catList.get(position)[1]);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog materialDialog = new MaterialDialog.Builder(mContext)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Log.d("foodDialog","agree");
                            }
                        }).onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Log.d("foodDialog","cancel");
                            }
                        }).title("title")
                        .content("content")
                        .positiveText("agree")
                        .negativeText("cancel")
                        .show();

            }
        });
    }


    @Override
    public int getItemCount() {
        return catList.size();
    }
}
