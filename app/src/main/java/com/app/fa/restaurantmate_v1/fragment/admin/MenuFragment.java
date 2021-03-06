package com.app.fa.restaurantmate_v1.fragment.admin;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.app.fa.restaurantmate_v1.Activity.AdminActivity;
import com.app.fa.restaurantmate_v1.Activity.admin.FoodActivity;
import com.app.fa.restaurantmate_v1.DividerItemDecoration;
import com.app.fa.restaurantmate_v1.MyApplication;
import com.app.fa.restaurantmate_v1.R;
import com.app.fa.restaurantmate_v1.adapter.CatAdapter;
import com.app.fa.restaurantmate_v1.adapter.admin.MenuAdapter;
import com.app.fa.restaurantmate_v1.model.FoodGroup;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {
    private RecyclerView mRecyclerView;
    //private RecyclerView.Adapter mAdapter;
    private FirebaseRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    //private List<String[]> myDataset;
    private FloatingActionButton fab;
    private MyApplication myApplication;
    public MenuFragment() {
//        myDataset = new ArrayList<>();
//        for(int i=1; i<=20; i++){
//            String[] list = new String[1];
//            list[0] = "ปลา";
//            myDataset.add(list);
//        }


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

        myApplication = (MyApplication)(getActivity().getApplicationContext());

        final DatabaseReference foodGroupQuery = myApplication.getDatabaseReference().child("foodgroup");
        //Query foodGroupQuery = foodGroupRef.orderByChild("name");
        mAdapter = new FirebaseRecyclerAdapter<FoodGroup, FoodGroupViewHolder>(FoodGroup.class, R.layout.admin_cat_list, FoodGroupViewHolder.class, foodGroupQuery) {
            @Override
            protected void populateViewHolder(final FoodGroupViewHolder viewHolder, final FoodGroup model, final int position) {
                viewHolder.name.setText(model.getName());
                final String foodGroupId = mAdapter.getRef(position).getKey();
                viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), FoodActivity.class);
                        intent.putExtra("title",model.getName());
                        intent.putExtra("foodGroupId", foodGroupId);
                        getContext().startActivity(intent);
                    }
                });
                viewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPopup(viewHolder.imageButton,foodGroupId);
                    }
                });
            }
        };

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.cat_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));

        //mAdapter = new MenuAdapter(getContext(),myDataset);
        mRecyclerView.setAdapter(mAdapter);

        fab = (FloatingActionButton) getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                View viewRoot = null;
                viewRoot = getActivity().getLayoutInflater().inflate(R.layout.admin_cat_dialog, null);
                final TextView textView = (TextView)viewRoot.findViewById(R.id.cat_name);
                Log.d("FloatingActionButton","FloatingActionButton");
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(getContext(),R.style.YourDialogStyle);
                builder.setTitle("เพิ่มประเภทอาหาร");
                builder.setView(viewRoot);
                builder.setPositiveButton("เพิ่ม", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("foodNowDialog","OK");
                        myApplication.getDatabaseReference().child("foodgroup").push().child("name").setValue(textView.getText().toString());
                        Toast toast = Toast.makeText(getContext(),"เพิ่มประเภทอาหารเรียบร้อยแล้ว", Toast.LENGTH_LONG);
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

    public static class FoodGroupViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        View rootView;
        ImageButton imageButton;

        public FoodGroupViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            name = (TextView)itemView.findViewById(R.id.text1_textview);
            imageButton = (ImageButton)itemView.findViewById(R.id.menu_popup);
        }
    }

    private void showPopup(View view, final String foodGroupId) {
        // pass the imageview id
        //View menuItemView = view.findViewById(R.id.btn_song_list_more);
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflate = popup.getMenuInflater();
        inflate.inflate(R.menu.item_menu, popup.getMenu());


        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.edit:
                        showDialog("edit",foodGroupId);
                        break;
                    case R.id.delete:
                        showDialog("delete",foodGroupId);
                        break;
                }
                return false;
            }
        });
        popup.show();
    }

    private void showDialog(String string, final String foodGroupId) {
        if(string.equals("edit")) {

            View viewRoot = null;
            AdminActivity adminActivity = (AdminActivity) getActivity();
            viewRoot = adminActivity.getLayoutInflater().inflate(R.layout.admin_cat_dialog, null);
            final TextView name = (TextView)viewRoot.findViewById(R.id.cat_name);
            Log.d("FloatingActionButton", "FloatingActionButton");
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(getActivity(), R.style.YourDialogStyle);
            builder.setTitle("แก้ไขประเภทอาหาร");
            builder.setView(viewRoot);
            builder.setPositiveButton("แก้ไข", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d("foodNowDialog", "OK");
                    myApplication.getDatabaseReference().child("foodgroup").child(foodGroupId).child("name").setValue(name.getText().toString());
                    Toast toast = Toast.makeText(getActivity(), "แก้ไขประเภทอาหารเรียบร้อยแล้ว", Toast.LENGTH_LONG);
                    toast.show();
                }
            });
            builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d("foodNowDialog", "Cancel");
                }
            });
            builder.show();
        }
        else if(string.equals("delete")){
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(getActivity(),R.style.YourDialogStyle);
            builder.setTitle("แน่ใจหรือเปล่า?");
            builder.setMessage("ประเภทอาหารนี้จะถูกลบ");
            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d("delete",foodGroupId);
                    myApplication.getDatabaseReference().child("foodgroup").child(foodGroupId).removeValue();
                    Toast toast = Toast.makeText(getActivity(),"ลบเรียบร้อยแล้ว", Toast.LENGTH_LONG);
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

    }
}
