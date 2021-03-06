package com.app.fa.restaurantmate_v1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.app.fa.restaurantmate_v1.R;
import com.app.fa.restaurantmate_v1.fragment.MenuTabFragment;
import com.app.fa.restaurantmate_v1.fragment.OrderFoodNowTabFragment;
import com.app.fa.restaurantmate_v1.fragment.OrderFoodTotalTabFragment;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String tableNum;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTableNum() {
        return tableNum;
    }

    public void setTableNum(String tableNum) {
        this.tableNum = tableNum;
    }

    String orderId;

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("tableNum", tableNum);
        bundle.putString("orderId", orderId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null){
            tableNum = savedInstanceState.getString("tableNum");
            orderId = savedInstanceState.getString("orderId");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Intent intent = getIntent();
        tableNum = intent.getStringExtra("tableNum");
        orderId = intent.getStringExtra("orderId");
        Log.d("orderActivity",tableNum);
        Log.d("orderActivity",orderId);
        toolbar.setTitle("โต๊ะที่ " +tableNum);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set up tabs
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        //setupTabIcons();
    }

    //*******************set up tab***************************
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MenuTabFragment(), "เมนู");
        adapter.addFragment(new OrderFoodNowTabFragment(), "อาหารสั่งตอนนี้");
        adapter.addFragment(new OrderFoodTotalTabFragment(), "รวมอาหารที่สั่ง");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public Toolbar getToolbar(){
        return toolbar;
    }

//    private void setupTabIcons() {
//        tabLayout.getTabAt(0).setIcon(R.drawable.noodles_select1);
////        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
////        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
//    }
    //********END***********set up tab***************************
}
