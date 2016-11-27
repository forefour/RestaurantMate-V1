package com.app.fa.restaurantmate_v1.adapter.admin;

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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.app.fa.restaurantmate_v1.Activity.AdminActivity;
import com.app.fa.restaurantmate_v1.Activity.admin.FoodActivity;
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

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder>{
    private Context mContext;
    private List<DataSnapshot> catList;
    private MyApplication myApplication;
    private String foodGroupId;

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
            text1 = (TextView) view.findViewById(R.id.text1_textview);
            this.view = view;
            this.imageButton = (ImageButton)view.findViewById(R.id.menu_popup);
        }
    }


    public FoodAdapter(Context mContext, List<DataSnapshot> catList, String foodGroupId) {
        this.mContext = mContext;
        this.catList = catList;
        this.myApplication = (MyApplication)mContext.getApplicationContext();
        this.foodGroupId = foodGroupId;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_cat_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        DatabaseReference foodRef = myApplication.getDatabaseReference().child("food").child(catList.get(position).getKey());
        foodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null) {
                    String foodName = dataSnapshot.child("name").getValue().toString();
                    String price = dataSnapshot.child("price").getValue().toString();
                    holder.text1.setText(foodName + " (" + price + "บ.)");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //holder.text1.setText(catList.get(position).getKey()+" (250บ.)");
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
                        showDialog("edit",position);
                        break;
                    case R.id.delete:
                        showDialog("delete",position);
                        break;
                }
                return false;
            }
        });
        popup.show();
    }

    private void showDialog(String string, final int position) {
        if(string.equals("edit")) {
            FoodActivity foodActivity = (FoodActivity)mContext;
            View viewRoot = null;
            viewRoot = foodActivity.getLayoutInflater().inflate(R.layout.admin_food_dialog, null);
            final TextView name,price;
            name = (TextView)viewRoot.findViewById(R.id.name);
            price = (TextView)viewRoot.findViewById(R.id.price);
            Log.d("FloatingActionButton","FloatingActionButton");
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(mContext,R.style.YourDialogStyle);
            builder.setTitle("แก้ไขรายการอาหาร");
            builder.setView(viewRoot);
            builder.setPositiveButton("แก้ไข", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d("foodNowDialog","OK");
                    DatabaseReference foodRef = myApplication.getDatabaseReference().child("food").child(catList.get(position).getKey());
                    foodRef.child("name").setValue(name.getText().toString());
                    foodRef.child("price").setValue(price.getText().toString());
                    //notifyDataSetChanged();
                    Toast toast = Toast.makeText(mContext,"แก้ไขรายการอาหารเรียบร้อยแล้ว", Toast.LENGTH_LONG);
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
        else if(string.equals("delete")){
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(mContext,R.style.YourDialogStyle);
            builder.setTitle("แน่ใจหรือเปล่า?");
            builder.setMessage("รายการอาหารนี้จะถูกลบ");
            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d("foodNowDialog","OK");
                    myApplication.getDatabaseReference().child("food").child(catList.get(position).getKey()).removeValue();
                    myApplication.getDatabaseReference().child("foodgroup").child(foodGroupId).child("food").child(catList.get(position).getKey()).removeValue();
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
