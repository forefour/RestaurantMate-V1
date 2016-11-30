package com.app.fa.restaurantmate_v1.fragment.admin;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.fa.restaurantmate_v1.MyApplication;
import com.app.fa.restaurantmate_v1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TableFragment extends Fragment {
    private FloatingActionButton fab;
    MyApplication myApplication;

    public TableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_table, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();
        myApplication = (MyApplication)(getActivity().getApplicationContext());
        final TextView tableNum = (TextView)getView().findViewById(R.id.table_num);
        final DatabaseReference tableNumRef = myApplication.getDatabaseReference().child("tableNum");
        tableNumRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tableNum.setText(dataSnapshot.getValue().toString()+" โต๊ะ");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        fab = (FloatingActionButton) getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                View viewRoot = null;
                viewRoot = getActivity().getLayoutInflater().inflate(R.layout.admin_table_dialog, null);
                final EditText tableNumEdit = (EditText)viewRoot.findViewById(R.id.table_num_edit);
                Log.d("FloatingActionButton","FloatingActionButton");
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(getContext(),R.style.YourDialogStyle);
                builder.setTitle("แก้ไขจำนวนโต๊ะอาหาร");
                builder.setView(viewRoot);
                builder.setPositiveButton("แก้ไข", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tableNumRef.setValue(tableNumEdit.getText().toString());
                        Toast toast = Toast.makeText(getContext(),"แก้ไขจำนวนโต๊ะเรียบร้อยแล้ว", Toast.LENGTH_LONG);
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
