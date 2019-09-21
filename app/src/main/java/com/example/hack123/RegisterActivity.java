package com.example.hack123;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText myname,mycontact;
    Button mysubmitlogin;
    DatabaseReference mdata;
    String s2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myname=findViewById(R.id.name);
        mycontact=findViewById(R.id.contact);
        mysubmitlogin=findViewById(R.id.loginsubmit);
        mysubmitlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this,"hi",Toast.LENGTH_LONG).show();;
                Intent I1=new Intent(RegisterActivity.this,otpverify.class);
                String s1=mycontact.getText().toString();
                I1.putExtra("phone",s1);

                s2=getIntent().getExtras().getString("user");
                /*mdata.child("users").child(s2).child("1").child("name").push();
                mdata.child("users").child(s2).child("1").child("name").setValue(myname);
                mdata.child("users").child(s2).child("1").child("contact").push();
                mdata.child("users").child(s2).child("1").child("contact").setValue(mycontact);*/
                startActivity(I1);
            }
        });
    }

}
