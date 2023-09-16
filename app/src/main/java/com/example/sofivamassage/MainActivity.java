package com.example.sofivamassage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btn_main_head = findViewById(R.id.btn_main_head);
        btn_main_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginMainActivity2.class);
                startActivity(intent);
            }
        });

        Button btn_register_head = findViewById(R.id.btn_register_head);
        btn_register_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterMainActivity2.class);
                startActivity(intent);
            }
        });
    }
}