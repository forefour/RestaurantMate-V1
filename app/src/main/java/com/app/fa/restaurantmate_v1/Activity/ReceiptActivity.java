package com.app.fa.restaurantmate_v1.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.app.fa.restaurantmate_v1.DividerItemDecoration;
import com.app.fa.restaurantmate_v1.R;
import com.app.fa.restaurantmate_v1.adapter.FoodAdapter;
import com.app.fa.restaurantmate_v1.adapter.ReceiptAdapter;

import java.util.ArrayList;
import java.util.List;

public class ReceiptActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String[]> myDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        String title = getIntent().getStringExtra("title");
        toolbar.setTitle("ใบเสร็จรับเงิน "+title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        myDataset = new ArrayList<>();
        for(int i=1; i<=20; i++){
            String[] list = new String[1];
            list[0] = "ปลากระพงทอดน้ำปลา";
            myDataset.add(list);
        }
        mRecyclerView = (RecyclerView)findViewById(R.id.receipt_recycler_view);

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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.receipt_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.receipt_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.printReceipt:
                Log.d("Menu_bar","printReceipt");
                return true;
            case R.id.clearTable:
                Log.d("Menu_bar","clearTable");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
