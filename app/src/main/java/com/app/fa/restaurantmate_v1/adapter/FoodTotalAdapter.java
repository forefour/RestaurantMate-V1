package com.app.fa.restaurantmate_v1.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.fa.restaurantmate_v1.Activity.OrderActivity;
import com.app.fa.restaurantmate_v1.R;

import java.util.List;

/**
 * Created by Foremost on 8/10/2559.
 */

public class FoodTotalAdapter extends RecyclerView.Adapter<FoodTotalAdapter.MyViewHolder>{
    private Context mContext;
    private List<String[]> catList;


    //**************************view Holder**********************************
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text1;
        public TextView text2;
        public View view;
        public ImageButton imageButton;

        public MyViewHolder(View view) {
            super(view);
            text1 = (TextView) view.findViewById(R.id.text1_textview);
            this.view = view.findViewById(R.id.root_food_total);
            this.imageButton = (ImageButton)view.findViewById(R.id.menu_popup);
        }
    }


    public FoodTotalAdapter(Context mContext, List<String[]> catList) {
        this.mContext = mContext;
        this.catList = catList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_total_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.text1.setText(catList.get(position)[0]);
        //holder.text2.setText(catList.get(position)[1]);
//        holder.view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showLocationDialog();
//
//            }
//        });
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(holder.imageButton,position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return catList.size();
    }

    private void showLocationDialog() {
        View viewRoot = null;


        viewRoot = ((OrderActivity) mContext).getLayoutInflater().inflate(R.layout.custom_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
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
                    }
                });
        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();

    }

    private void showPopup(View view, final int position) {
        // pass the imageview id
        //View menuItemView = view.findViewById(R.id.btn_song_list_more);
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflate = popup.getMenuInflater();
        inflate.inflate(R.menu.item_menu, popup.getMenu());


        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.edit:
                        Log.d("popupmenu-> ","delete");
                        showLocationDialog();


                }
                return false;
            }
        });
        popup.show();
    }
}
