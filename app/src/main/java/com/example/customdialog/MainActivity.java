package com.example.customdialog;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        Button button = findViewById(R.id.btn);
        button.setOnClickListener(v ->CustomDialog.dialog(new CustomDialog.ClickListener() {
            @Override
            public void hungry(Dialog dialog) {
                Toast.makeText(MainActivity.this, "졸리다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void sleep(Dialog dialog) {
                Toast.makeText(MainActivity.this, "배고프다.", Toast.LENGTH_SHORT).show();

            }
        }).show(getSupportFragmentManager(), "tag")  );
    }
}
