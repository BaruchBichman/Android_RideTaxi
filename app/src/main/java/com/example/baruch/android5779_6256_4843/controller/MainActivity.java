package com.example.baruch.android5779_6256_4843.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.baruch.android5779_6256_4843.R;


public class MainActivity extends AppCompatActivity {

    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton=(Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOrderRideActivity();
            }
        });
    }

    public void openOrderRideActivity(){
        Intent intent=new Intent(this,OrderRideActivity.class);
        startActivity(intent);
    }
}
