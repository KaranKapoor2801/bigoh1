package com.example.hack123;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    DatabaseReference userDatabaseRef;
    ArrayList<UserInfoModel> userInfoList;
    ArrayList<String>userUidArrayList;
    AdminRecyclerViewAdapter adminRecyclerViewAdapter;
    RecyclerView userRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        userDatabaseRef= FirebaseDatabase.getInstance().getReference().child("users").child("student");
        userInfoList=new ArrayList<>();
        userUidArrayList=new ArrayList<>();
        userRecyclerView=(RecyclerView)findViewById(R.id.admin_recyclerView);

        userDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot post: dataSnapshot.getChildren()){
                    UserInfoModel userInfoModel=post.getValue(UserInfoModel.class);
                    if(userInfoModel.getVerify().equals("false")){
                        userInfoList.add(userInfoModel);
                        userUidArrayList.add(post.getKey());
                        //Toast.makeText(AdminActivity.this, userInfoModel.getName(), Toast.LENGTH_SHORT).show();
                    }
                }

                adminRecyclerViewAdapter=new AdminRecyclerViewAdapter(AdminActivity.this,userInfoList,userUidArrayList);
                userRecyclerView.setLayoutManager(new LinearLayoutManager(AdminActivity.this));
                userRecyclerView.setAdapter(adminRecyclerViewAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
