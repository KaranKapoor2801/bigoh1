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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    Button registerButton,loginButton;
    DatabaseReference userDatabaseRef;
    private FirebaseAuth mAuth;
    EditText nameEditText,phoneEditText,emailEditText,passwordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton=(Button)findViewById(R.id.loginsubmit);
        nameEditText=(EditText)findViewById(R.id.name);
        phoneEditText=(EditText)findViewById(R.id.contact);
        emailEditText=(EditText)findViewById(R.id.email_edittext);
        passwordEditText=(EditText)findViewById(R.id.password_edittext);
        loginButton=(Button)findViewById(R.id.login_btn);


        mAuth=FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent=new Intent(RegisterActivity.this,LoginActivity.class);
                loginIntent.putExtra("user",getIntent().getStringExtra("user"));
                startActivity(loginIntent);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nameEditText.getText().toString();
                String email=emailEditText.getText().toString();
                String password=passwordEditText.getText().toString();
                String phone=phoneEditText.getText().toString();

                registerUser(name,email,password,phone);
            }
        });
    }
    public void registerUser(String name,String email, String password,String phone){
        final HashMap<String, String> userMap = new HashMap<>();
        userMap.put("name", name);
        userMap.put("email", email);
        userMap.put("phone", phone);
        userMap.put("password",password);

        if(getIntent().getStringExtra("user").equals("student")){
            userMap.put("mess_bill","false");
            userMap.put("verify","false");
            userMap.put("docURL","https://firebasestorage.googleapis.com/v0/b/chatapplication-da329.appspot.com/o/profile_images%2Fdefault%20avatar.png?alt=media&token=3f91b549-9664-4dcd-acbf-47e2fbf8e462");
        }

        mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                FirebaseUser currentUser=mAuth.getCurrentUser();
                String uid=currentUser.getUid();
                userDatabaseRef=FirebaseDatabase.getInstance().getReference().child("users").child(getIntent().getStringExtra("user")).child(uid);
                userDatabaseRef.setValue(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(RegisterActivity.this, "Data Uploaded", Toast.LENGTH_SHORT).show();
                        Intent loginIntent=new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(loginIntent);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

}
