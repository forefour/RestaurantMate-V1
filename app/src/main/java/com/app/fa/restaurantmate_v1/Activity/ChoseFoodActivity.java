package com.app.fa.restaurantmate_v1.Activity;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.app.fa.restaurantmate_v1.DividerItemDecoration;
import com.app.fa.restaurantmate_v1.MyApplication;
import com.app.fa.restaurantmate_v1.R;
import com.app.fa.restaurantmate_v1.adapter.CatAdapter;
import com.app.fa.restaurantmate_v1.adapter.FoodAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChoseFoodActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<DataSnapshot> myDataset;
    private MyApplication myApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_food);
        myApplication = (MyApplication)getApplicationContext();
        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        String foodGroupId = getIntent().getStringExtra("title");
        DatabaseReference thisFoodGroupRef = myApplication.getDatabaseReference().child("foodgroup").child(foodGroupId);
        thisFoodGroupRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                toolbar.setTitle(dataSnapshot.child("name").getValue().toString());
//        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
//        params.setScrollFlags(0);
                setSupportActionBar(toolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        myDataset = new ArrayList<>();
        mRecyclerView = (RecyclerView)findViewById(R.id.food_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        mAdapter = new FoodAdapter(this,myDataset,"foodActivity");
        mRecyclerView.setAdapter(mAdapter);

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



    }
}
