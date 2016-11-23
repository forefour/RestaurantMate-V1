package com.app.fa.restaurantmate_v1.Activity.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.fa.restaurantmate_v1.DividerItemDecoration;
import com.app.fa.restaurantmate_v1.MyApplication;
import com.app.fa.restaurantmate_v1.R;
import com.app.fa.restaurantmate_v1.adapter.admin.FoodAdapter;
import com.app.fa.restaurantmate_v1.fragment.admin.MenuFragment;
import com.app.fa.restaurantmate_v1.model.Food;
import com.app.fa.restaurantmate_v1.model.FoodGroup;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FoodActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<DataSnapshot> myDataset;
    private FloatingActionButton fab;
    //private FirebaseRecyclerAdapter mAdapter;
    //private MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        final MyApplication myApplication = (MyApplication)getApplicationContext();

        //get intent from admin-> MenuFragment
        Intent intent = getIntent();
        final String foodGroupId = intent.getStringExtra("foodGroupId");
        String title = intent.getStringExtra("title");
        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        myDataset = new ArrayList<>();
//        for(int i=1; i<=20; i++){
//            String[] list = new String[1];
//            list[0] = "ปลากระพงทอดน้ำปลา";
//            myDataset.add(list);
//        }
        myDataset = new ArrayList<>();
        DatabaseReference foodRef = myApplication.getDatabaseReference().child("foodgroup").child(foodGroupId).child("food");
        foodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myDataset.clear();
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    Log.d("text",messageSnapshot.getKey());
                    myDataset.add(messageSnapshot);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        final DatabaseReference foodRef = myApplication.getDatabaseReference().child("foodgroup").child(foodGroupId).child("food");
//        mAdapter = new FirebaseRecyclerAdapter<Food, FoodActivity.FoodViewHolder>(Food.class, R.layout.admin_cat_list, FoodActivity.FoodViewHolder.class, foodRef) {
//
//            @Override
//            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
//                //viewHolder.name.setText(model.g);
//                Log.d("Text",mAdapter.getRef(position).getKey());
//
//
//            }
//        };

        mRecyclerView = (RecyclerView)findViewById(R.id.food_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FoodAdapter(this,myDataset,foodGroupId);
        mRecyclerView.setAdapter(mAdapter);

        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                View viewRoot = null;
                viewRoot = FoodActivity.this.getLayoutInflater().inflate(R.layout.admin_food_dialog, null);
                final TextView name = (TextView)viewRoot.findViewById(R.id.name);
                final TextView price = (TextView)viewRoot.findViewById(R.id.price);
                Log.d("FloatingActionButton","FloatingActionButton");
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(FoodActivity.this,R.style.YourDialogStyle);
                builder.setTitle("เพิ่มรายการอาหาร");
                builder.setView(viewRoot);
                builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("foodNowDialog","OK");
                        DatabaseReference foodRef = myApplication.getDatabaseReference().child("food");
                        foodRef = foodRef.push();
                        String foodId = foodRef.getKey();
                        foodRef.child("name").setValue(name.getText().toString());
                        foodRef.child("price").setValue(price.getText().toString());
                        foodRef.child("foodgroup").child(foodGroupId).setValue("true");

                        DatabaseReference foodGroupRef = myApplication.getDatabaseReference().child("foodgroup");
                        foodGroupRef.child(foodGroupId).child("food").child(foodId).setValue("true");
                        //myApplication.getDatabaseReference().child("food").push().child("name").setValue(textView.getText().toString());
                        Toast toast = Toast.makeText(FoodActivity.this,"เพิ่มรายการอาหารเรียบร้อยแล้ว", Toast.LENGTH_LONG);
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
        });
    }

//    public static class FoodViewHolder extends RecyclerView.ViewHolder {
//        TextView name;
//        TextView price;
//        View rootView;
//
//        public FoodViewHolder(View itemView) {
//            super(itemView);
//            rootView = itemView;
//            name = (TextView)itemView.findViewById(R.id.text1_textview);
////            name = (TextView)itemView.findViewById(R.id.name);
////            price = (TextView)itemView.findViewById(R.id.price);
//
//        }
//    }
}
