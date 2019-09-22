package com.example.hack123;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.BufferedReader;

public class UploadFile extends AppCompatActivity {

    Button uploadImageButton;
    private static final int GALLERY_PICK=1;
    StorageReference mImageStorage;
    DatabaseReference mUserDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);

        uploadImageButton=(Button)findViewById(R.id.upload_image_btn);
        mImageStorage= FirebaseStorage.getInstance().getReference();
        mUserDatabaseRef= FirebaseDatabase.getInstance().getReference().child("users").child("student");

        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent =new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent,"SELECT IMAGE"),GALLERY_PICK);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GALLERY_PICK ){
            Uri imageUri=data.getData();
            final String curUid= FirebaseAuth.getInstance().getCurrentUser().getUid();

            final StorageReference filepath=mImageStorage.child("documents").child(curUid+".jpg");

            filepath.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){

                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageDownloadUrl=uri.toString();
                                mUserDatabaseRef.child(curUid).child("docURL").setValue(imageDownloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){

                                            Toast.makeText(UploadFile.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });

                    }else{
                        Toast.makeText(UploadFile.this, "Error Occured", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }

    }
}
