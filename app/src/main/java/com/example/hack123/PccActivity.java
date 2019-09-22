package com.example.hack123;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PccActivity extends AppCompatActivity {

    Button internUploadButton,placementUploadButton,internInterrestedStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcc);

        internUploadButton=(Button)findViewById(R.id.documentbutton);
        placementUploadButton=(Button)findViewById(R.id.placement_details);
        internInterrestedStudents=(Button)findViewById(R.id.companyinterestedstudent);

        internUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PccActivity.this,companydetails.class);
                startActivity(intent);
            }
        });

        internInterrestedStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
