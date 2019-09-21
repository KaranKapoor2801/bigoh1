package com.example.hack123;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button mystudenllogin,myadminlogin,mypcclogin,mywardenlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mystudenllogin=findViewById(R.id.studentLogin);
        myadminlogin=findViewById(R.id.adminlogin);
        mypcclogin=findViewById(R.id.PCCLogin);
        mywardenlogin=findViewById(R.id.WARDENLogin);
        mystudenllogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I1=new Intent(MainActivity.this,RegisterActivity.class);
                I1.putExtra("user","student");
                startActivity(I1);
            }
        });
        mywardenlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I1=new Intent(MainActivity.this,RegisterActivity.class);
                I1.putExtra("user","warden");
                startActivity(I1);
            }
        });
        mypcclogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I1=new Intent(MainActivity.this,RegisterActivity.class);
                I1.putExtra("user","pcc");
                startActivity(I1);
            }
        });
        myadminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I1=new Intent(MainActivity.this,RegisterActivity.class);
                I1.putExtra("user","admin");
                startActivity(I1);
            }
        });
    }
}
