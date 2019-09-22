package com.example.hack123;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentActivity extends AppCompatActivity {

    Button uploadDocuments,checkEvents,checkPlacements,checkIntern,checkMessBill,goToMap;
    FirebaseUser currentUser;
    String currentUid;
    DatabaseReference studentDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        uploadDocuments=(Button)findViewById(R.id.upload);
        checkIntern=(Button)findViewById(R.id.intern);
        checkEvents=(Button)findViewById(R.id.events);
        checkMessBill=(Button)findViewById(R.id.mess);
        checkPlacements=(Button)findViewById(R.id.placements);
        goToMap=(Button)findViewById(R.id.go_to_map);

        currentUser= FirebaseAuth.getInstance().getCurrentUser();
        currentUid=currentUser.getUid();

        studentDatabaseRef= FirebaseDatabase.getInstance().getReference().child("users").child("student").child(currentUid);
        studentDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("verify").getValue().equals("true")){
                    uploadDocuments.setVisibility(View.INVISIBLE);
                    uploadDocuments.setEnabled(false);
                }else{
                    checkEvents.setVisibility(View.INVISIBLE);
                    checkMessBill.setVisibility(View.INVISIBLE);
                    checkIntern.setVisibility(View.INVISIBLE);
                    checkPlacements.setVisibility(View.INVISIBLE);
                    goToMap.setVisibility(View.INVISIBLE);

                    checkEvents.setEnabled(false);
                    checkMessBill.setEnabled(false);
                    checkIntern.setEnabled(false);
                    checkPlacements.setEnabled(false);
                    goToMap.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        checkMessBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentDatabaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("mess_bill").getValue().equals("true")){
                            checkMessBill.setText("Bill Paid");
                        }else{
                            checkMessBill.setText("Bill Pending");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        checkIntern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StudentActivity.this,InternActivity.class);
                startActivity(intent);
            }
        });

        uploadDocuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StudentActivity.this,UploadFile.class);
                startActivity(intent);
            }
        });

        goToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StudentActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });
    }
}
