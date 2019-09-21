package com.example.hack123;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class InternActivity extends AppCompatActivity {

    RecyclerView internRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intern);

        internRecyclerView=(RecyclerView)findViewById(R.id.inter_recyclerView);

    }
}
