package com.app.fa.restaurantmate_v1.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.app.fa.restaurantmate_v1.MyApplication;
import com.app.fa.restaurantmate_v1.R;
import com.app.fa.restaurantmate_v1.fragment.MenuTabFragment;
import com.app.fa.restaurantmate_v1.fragment.TableSelectFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TableActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    MyApplication myApplication;

    private TableSelectFragment tab1 = new TableSelectFragment();
    private TableSelectFragment tab2 = new TableSelectFragment();
    private TableSelectFragment tab3 = new TableSelectFragment();


    private ArrayList<Integer> tables = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("เลือกโต๊ะ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myApplication = (MyApplication)getApplicationContext();
        final DatabaseReference tableNumRef = myApplication.getDatabaseReference().child("tableNum");
        tableNumRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int countTable = Integer.parseInt(dataSnapshot.getValue().toString());
                if (countTable % 3 == 0) {
                    tab1.setStart(1);
                    tab1.setEnd(countTable/3);

                    tab2.setStart(countTable/3 + 1);
                    tab2.setEnd(countTable/3 * 2);

                    tab3.setStart(countTable/3 * 2 + 1);
                    tab3.setEnd(countTable);
                }

                else if (countTable % 3 == 1) {
                    tab1.setStart(1);
                    tab1.setEnd(countTable/3 + 1);


                    tab2.setStart(countTable/3 + 2);
                    tab2.setEnd(countTable/3 * 2 + 1);

                    tab3.setStart(countTable/3 * 2 + 2);
                    tab3.setEnd(countTable);
                }

                else if (countTable % 3 == 2) {
                    tab1.setStart(1);
                    tab1.setEnd(countTable/3 + 1);

                    tab2.setStart(countTable/3 + 2);
                    tab2.setEnd(countTable/3 * 2 + 2);

                    tab3.setStart(countTable/3 * 2 + 3);
                    tab3.setEnd(countTable);
                }

                viewPager = (ViewPager) findViewById(R.id.viewpager);
                setupViewPager(viewPager);
                tabLayout = (TabLayout) findViewById(R.id.tabs);
                tabLayout.setupWithViewPager(viewPager);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(tab1, tab1.getStart()+"-"+tab1.getEnd());
        adapter.addFragment(tab2, tab2.getStart()+"-"+tab2.getEnd());
        adapter.addFragment(tab3, tab3.getStart()+"-"+tab3.getEnd());
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

}
