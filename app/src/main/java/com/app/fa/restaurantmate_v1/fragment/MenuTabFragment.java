package com.app.fa.restaurantmate_v1.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.fa.restaurantmate_v1.DividerItemDecoration;
import com.app.fa.restaurantmate_v1.R;
import com.app.fa.restaurantmate_v1.adapter.CatAdapter;
import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuTabFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String[]> myDataset;
    public MenuTabFragment() {
        // Required empty public constructor
        myDataset = new ArrayList<>();
        for(int i=1; i<=20; i++){
            String[] list = new String[1];
            list[0] = "ปลา";
            myDataset.add(list);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_tab, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();
        // Inflate the layout for this fragment
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.cat_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        // specify an adapter (see also next example)
        mAdapter = new CatAdapter(getContext(),myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }

}
