package com.app.fa.restaurantmate_v1.Activity.cashier;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.app.fa.restaurantmate_v1.R;
import com.app.fa.restaurantmate_v1.adapter.FoodTotalAdapter;
import com.app.fa.restaurantmate_v1.adapter.cashier.OrderAdapter;

import java.util.ArrayList;
import java.util.List;

public class CashierActivity extends AppCompatActivity {
    private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String[]> myDataset;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDataset = new ArrayList<>();
        for(int i=1; i<=20; i++){
            String[] list = new String[1];
            list[0] = "โต๊ะที่: 08 , OrderID:0001";
            myDataset.add(list);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Cashier");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        mRecyclerView = (RecyclerView)findViewById(R.id.order_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new OrderAdapter(this,myDataset);
        mRecyclerView.setAdapter(mAdapter);

        if(savedInstanceState != null)
        {
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }

    public void onStart(){
        super.onStart();
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, mRecyclerView.getLayoutManager().onSaveInstanceState());
    }
}
