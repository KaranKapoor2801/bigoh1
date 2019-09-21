package com.example.hack123;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button loginBtn;
    EditText emailEditText,passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();

        loginBtn=(Button)findViewById(R.id.login_button_activity);
        emailEditText=(EditText)findViewById(R.id.email_login);
        passwordEditText=(EditText)findViewById(R.id.password_login);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailEditText.getText().toString();
                String password=passwordEditText.getText().toString();
                loginUser(email,password);
            }
        });
    }

    public void loginUser(String email,String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                String user=getIntent().getStringExtra("user");
                if(user.equals("student")){
                    Intent intent=new Intent(LoginActivity.this,StudentActivity.class);
                    startActivity(intent);
                }else if(user.equals("admin")){
                    Intent intent=new Intent(LoginActivity.this,AdminActivity.class);
                    startActivity(intent);
                }else if(user.equals("pcc")){
                    Intent intent=new Intent(LoginActivity.this,PccActivity.class);
                    startActivity(intent);
                }else if(user.equals("warden")){
                    Intent intent=new Intent(LoginActivity.this,WardenActivity.class);
                    startActivity(intent);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
