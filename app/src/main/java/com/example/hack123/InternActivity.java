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

public class InternActivity extends AppCompatActivity {

    RecyclerView internRecyclerView;
    DatabaseReference internDatabaseRef;
    ArrayList<String> internCompanyUid;
    ArrayList<InternCompanyModel> internCompanyModelArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intern);

        internRecyclerView=(RecyclerView)findViewById(R.id.inter_recyclerView);
        internDatabaseRef= FirebaseDatabase.getInstance().getReference().child("companies").child("intern");

        internCompanyUid=new ArrayList<>();
        internCompanyModelArrayList=new ArrayList<>();

        internDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(InternActivity.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                for (DataSnapshot post: dataSnapshot.getChildren()){
                    internCompanyUid.add(post.getKey());
                    InternCompanyModel internCompanyModel=post.getValue(InternCompanyModel.class);
                    internCompanyModelArrayList.add(internCompanyModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        InternCompanyAdapter internCompanyAdapter=new InternCompanyAdapter(InternActivity.this,internCompanyUid,internCompanyModelArrayList);
        internRecyclerView.setLayoutManager(new LinearLayoutManager(InternActivity.this));
        internRecyclerView.setAdapter(internCompanyAdapter);
    }
}
