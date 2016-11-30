package com.app.fa.restaurantmate_v1.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.fa.restaurantmate_v1.DividerItemDecoration;
import com.app.fa.restaurantmate_v1.MyApplication;
import com.app.fa.restaurantmate_v1.R;
import com.app.fa.restaurantmate_v1.adapter.CatAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MenuTabFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyApplication myApplication;
    private List<DataSnapshot> myDataset;
    public MenuTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_tab, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();
        myDataset = new ArrayList<>();
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.cat_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        mAdapter = new CatAdapter(getContext(), myDataset);
        mRecyclerView.setAdapter(mAdapter);


        myApplication = (MyApplication)(getActivity().getApplicationContext());
        DatabaseReference foodGroupRef = myApplication.getDatabaseReference().child("foodgroup");
        foodGroupRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("dataSnapshot",dataSnapshot.toString());
                myDataset.clear();
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
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
