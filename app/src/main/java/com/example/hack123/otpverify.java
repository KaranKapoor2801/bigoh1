package com.example.hack123;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class otpverify extends AppCompatActivity {

    EditText myoypedit;
    Button  myotpsubmit,myotpback;
    String phonenumber;
    FirebaseAuth mAuth;
    private String mVerificationId,code;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    int otpFlag =0;
    private EditText codeText;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_otpverify);
        myoypedit=findViewById(R.id.otptext);
        myotpsubmit=findViewById(R.id.otpsubmit);
        myotpback=findViewById(R.id.otpback);
        myotpback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I1=new Intent(otpverify.this,RegisterActivity.class);
                startActivity(I1);
            }
        });
        authinit();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phonenumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        myotpsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code= myoypedit.getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
                signInWithPhoneAuthCredential(credential);
            }
        });
    }

    private void authinit() {
        phonenumber=getIntent().getExtras().getString("phone");
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                signInWithPhoneAuthCredential(phoneAuthCredential);
                Intent I1=new Intent(otpverify.this, studentmain.class);
                startActivity(I1);
          /*      mdata= FirebaseDatabase.getInstance().getReference();
                mdata.child("Student").child(mAuth.getUid()).child("Student_Name").push();
                mdata.child("Student").child(mAuth.getUid()).child("Student_Name").setValue(studentname);

                int a1;
                mdata.child("Student").child(mAuth.getUid()).child("PhoneNo").push();
                mdata.child("Student").child(mAuth.getUid()).child("PhoneNo").setValue(phoneNumber);
                mdata.child("Student").child(mAuth.getUid()).child("Roll_Number").push();
                mdata.child("Student").child(mAuth.getUid()).child("Roll_Number").setValue(studentroll);
                mdata.child("Student").child(mAuth.getUid()).child("Student_class").push();
                mdata.child("Student").child(mAuth.getUid()).child("Student_class").setValue(studentroll);
            */
                //Toast.makeText(otpverify.this,"welcome",Toast.LENGTH_LONG).show();
                finish();
            }
            @Override
            public void onVerificationFailed(FirebaseException e) {

                Toast.makeText(otpverify.this,e.toString(),Toast.LENGTH_LONG).show();

            }
            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                mVerificationId = verificationId;
                mResendToken = token;
            }

        };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth=FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent loggedIn = new Intent(otpverify.this, studentmain.class);

                            startActivity(loggedIn);

                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(otpverify.this,"error",Toast.LENGTH_LONG).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
}
