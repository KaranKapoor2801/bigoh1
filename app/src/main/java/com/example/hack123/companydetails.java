package com.example.hack123;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class companydetails extends AppCompatActivity {

    EditText mycompanyname,mycompanyprofile,mycompanylocation,mycompanystipined;
    DatabaseReference mdata;
    Button mycompanysubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companydetails);
        mycompanylocation=findViewById(R.id.companylocation);
        mycompanyname=findViewById(R.id.companyname);
        mycompanyprofile=findViewById(R.id.companyprofile);
        mycompanystipined=findViewById(R.id.companystipined);
        mycompanysubmit=findViewById(R.id.companysubmit);
        mycompanysubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name=mycompanyname.getText().toString();
                String location=mycompanylocation.getText().toString();
                String profile=mycompanyprofile.getText().toString();
                String stipined=mycompanystipined.getText().toString();
                mdata= FirebaseDatabase.getInstance().getReference();
                String key=mdata.child("companies").child("intern").push().getKey();


                mdata.child("companies").child("intern").child(key).child("name").setValue(name);
                mdata.child("companies").child("intern").child(key).child("location").setValue(location);
                mdata.child("companies").child("intern").child(key).child("profile").setValue(profile);
                mdata.child("companies").child("intern").child(key).child("stipend").setValue(stipined).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(companydetails.this, "Information Added", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(companydetails.this, "Error in Uploading", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }
}
