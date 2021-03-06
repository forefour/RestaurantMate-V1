package com.app.fa.restaurantmate_v1.adapter.admin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.app.fa.restaurantmate_v1.Activity.AdminActivity;
import com.app.fa.restaurantmate_v1.Activity.OrderActivity;
import com.app.fa.restaurantmate_v1.Activity.admin.FoodActivity;
import com.app.fa.restaurantmate_v1.R;

import java.util.List;

/**
 * Created by Foremost on 8/10/2559.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder>{
    private Context mContext;
    private List<String[]> catList;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    //**************************view Holder**********************************
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text1;
        public TextView text2;
        public View view;
        public ImageButton imageButton;

        public MyViewHolder(View view) {
            super(view);
            this.text1 = (TextView) view.findViewById(R.id.text1_textview);
            this.view = view;
            this.imageButton = (ImageButton)view.findViewById(R.id.menu_popup);

        }
    }


    public MenuAdapter(Context mContext, List<String[]> catList) {
        this.mContext = mContext;
        this.catList = catList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_cat_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.text1.setText(catList.get(position)[0]);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Foremost","123");
                Intent intent = new Intent(mContext, FoodActivity.class);
                intent.putExtra("title",catList.get(position)[0]);
                mContext.startActivity(intent);
            }
        });
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
                        showDialog("edit");
                        break;
                    case R.id.delete:
                        showDialog("delete");
                        break;
                }
                return false;
            }
        });
        popup.show();
    }

    private void showDialog(String string) {
        if(string.equals("edit")) {

            View viewRoot = null;
            AdminActivity adminActivity = (AdminActivity) mContext;
            viewRoot = adminActivity.getLayoutInflater().inflate(R.layout.admin_cat_dialog, null);
            Log.d("FloatingActionButton", "FloatingActionButton");
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(mContext, R.style.YourDialogStyle);
            builder.setTitle("แก้ไขประเภทอาหาร");
            builder.setView(viewRoot);
            builder.setPositiveButton("แก้ไข", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d("foodNowDialog", "OK");
                    Toast toast = Toast.makeText(mContext, "แก้ไขประเภทอาหารเรียบร้อยแล้ว", Toast.LENGTH_LONG);
                    toast.show();
                }
            });
            builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d("foodNowDialog", "Cancel");
                }
            });
            builder.show();
        }
        else if(string.equals("delete")){
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(mContext,R.style.YourDialogStyle);
            builder.setTitle("แน่ใจหรือเปล่า?");
            builder.setMessage("ประเภทอาหารนี้จะถูกลบ");
            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d("foodNowDialog","OK");
                    Toast toast = Toast.makeText(mContext,"ลบเรียบร้อยแล้ว", Toast.LENGTH_LONG);
                    toast.show();
                }
            });
            builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d("foodNowDialog","Cancel");
                }
            });
            builder.show();
        }

    }
}
