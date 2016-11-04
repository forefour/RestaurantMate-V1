package com.app.fa.restaurantmate_v1.fragment.admin;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.fa.restaurantmate_v1.DividerItemDecoration;
import com.app.fa.restaurantmate_v1.R;
import com.app.fa.restaurantmate_v1.adapter.CatAdapter;
import com.app.fa.restaurantmate_v1.adapter.admin.MenuAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String[]> myDataset;
    private FloatingActionButton fab;

    public MenuFragment() {
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
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
        mAdapter = new MenuAdapter(getContext(),myDataset);
        mRecyclerView.setAdapter(mAdapter);

        fab = (FloatingActionButton) getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Log.d("FloatingActionButton","FloatingActionButton");
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(getContext(),R.style.YourDialogStyle);
                builder.setTitle("แน่ใจหรือเปล่า?");
                builder.setMessage("อาหารทั้งหมดในหน้านี้จะถูกส่งไปยังครัว");
                builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("foodNowDialog","OK");
                        Toast toast = Toast.makeText(getContext(),"ส่งไปยังครัวเรียบร้อยแล้ว", Toast.LENGTH_LONG);
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


}
