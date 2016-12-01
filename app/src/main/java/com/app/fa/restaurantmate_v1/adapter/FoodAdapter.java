package com.app.fa.restaurantmate_v1.adapter;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.app.fa.restaurantmate_v1.Activity.ChoseFoodActivity;
import com.app.fa.restaurantmate_v1.Activity.OrderActivity;
import com.app.fa.restaurantmate_v1.MyApplication;
import com.app.fa.restaurantmate_v1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.List;

/**
 * Created by Foremost on 8/10/2559.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder>{
    private Context mContext;
    private List<DataSnapshot> catList;
    private MyApplication myApplication;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private String from;


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


    public FoodAdapter(Context mContext, List<DataSnapshot> catList, String from) {
        this.mContext = mContext;
        this.catList = catList;
        this.from = from;
        this.myApplication = (MyApplication)mContext.getApplicationContext();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_avatar_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {


        final DatabaseReference foodRef = myApplication.getDatabaseReference().child("food").child(catList.get(position).getKey());
        foodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null) {
                    String foodName = dataSnapshot.child("name").getValue().toString();
                    holder.text1.setText(foodName);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String foodName = dataSnapshot.child("name").getValue().toString();
                        showLocationDialog(foodName);
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
        return catList.size();
    }

    private void showLocationDialog(String foodName) {
        View viewRoot = null;
        if(this.from.equals("foodActivity")) {
            viewRoot = ((ChoseFoodActivity) mContext).getLayoutInflater().inflate(R.layout.custom_dialog, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext,R.style.YourDialogStyle);
            builder.setTitle(foodName);
            builder.setView(viewRoot);
            //custom view
            AppCompatSpinner spinner = (AppCompatSpinner) viewRoot.findViewById(R.id.planets_spinner);
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                    R.array.planets_array, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spinner.setAdapter(adapter);


            String positiveText = "สั่ง";
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("foodDialog","agree2");
                            Toast toast = Toast.makeText(mContext,"สั่งเรียบร้อยแล้ว", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });

            String negativeText = "ยกเลิก";
            builder.setNegativeButton(negativeText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("foodDialog","cancel2");
                        }
                    });

            AlertDialog dialog = builder.create();
            // display dialog
            dialog.show();
        }
        else if(this.from.equals("foodNow")){
            viewRoot = ((OrderActivity) mContext).getLayoutInflater().inflate(R.layout.custom_dialog, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext,R.style.YourDialogStyle);
            builder.setTitle("ปลากระพงทอดน้ำปลา");
            builder.setView(viewRoot);
            //custom view
            AppCompatSpinner spinner = (AppCompatSpinner) viewRoot.findViewById(R.id.planets_spinner);
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                    R.array.planets_array, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spinner.setAdapter(adapter);


            String positiveText = "สั่ง";
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("foodDialog","agree2");
                            Toast toast = Toast.makeText(mContext,"สั่งเรียบร้อยแล้ว", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });

            String negativeText = "ยกเลิก";
            builder.setNegativeButton(negativeText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("foodDialog","cancel2");
                        }
                    });
            String neutralText = "ลบ";
            builder.setNeutralButton(neutralText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("foodDialog","cancel3");
                            Toast toast = Toast.makeText(mContext,"ลบเรียบร้อยแล้ว", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });
            AlertDialog dialog = builder.create();
            // display dialog
            dialog.show();
        }
    }
}
