package com.app.fa.restaurantmate_v1.Activity;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.app.fa.restaurantmate_v1.DividerItemDecoration;
import com.app.fa.restaurantmate_v1.R;
import com.app.fa.restaurantmate_v1.adapter.CatAdapter;
import com.app.fa.restaurantmate_v1.adapter.FoodAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChoseFoodActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String[]> myDataset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_food);
        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        String title = getIntent().getStringExtra("title");
        toolbar.setTitle(title);
//        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
//        params.setScrollFlags(0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myDataset = new ArrayList<>();
        for(int i=1; i<=20; i++){
            String[] list = new String[1];
            list[0] = "ปลากระพงทอดน้ำปลา";
            myDataset.add(list);
        }
        mRecyclerView = (RecyclerView)findViewById(R.id.food_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        // specify an adapter (see also next example)
        mAdapter = new FoodAdapter(this,myDataset,"foodActivity");
        mRecyclerView.setAdapter(mAdapter);


    }
}
