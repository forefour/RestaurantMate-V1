package com.app.fa.restaurantmate_v1.Activity.cashier;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.app.fa.restaurantmate_v1.R;
import com.app.fa.restaurantmate_v1.adapter.ReceiptAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {
    private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String[]> myDataset;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Cashier");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myDataset = new ArrayList<>();
        for(int i=1; i<=20; i++){
            String[] list = new String[1];
            list[0] = "ปลากระพงทอดน้ำปลา";
            myDataset.add(list);
        }
        mRecyclerView = (RecyclerView)findViewById(R.id.order_detail_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        mAdapter = new ReceiptAdapter(this,myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }
}
