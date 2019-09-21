package com.example.hack123;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class VerifyDocumentActivity extends AppCompatActivity {

    ImageView documentDisplayImageView;
    Button verifyDocumentBtn;
    DatabaseReference userDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_document);

        documentDisplayImageView=(ImageView)findViewById(R.id.document_display_imageview);
        verifyDocumentBtn=(Button)findViewById(R.id.verify_doc_btn);


        Picasso.get().load(getIntent().getStringExtra("doc_url")).placeholder(R.drawable.loading).into(documentDisplayImageView);

        verifyDocumentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDatabaseRef= FirebaseDatabase.getInstance().getReference().child("users")
                        .child("student").child(getIntent().getStringExtra("current_uid")).child("verify");

                userDatabaseRef.setValue("true").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(VerifyDocumentActivity.this, "Document Verified", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(VerifyDocumentActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}
