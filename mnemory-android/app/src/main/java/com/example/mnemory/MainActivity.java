package com.example.mnemory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView;

        Button btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout = findViewById(R.id.linearLayout2);

                EditText input3 = findViewById(R.id.input3);

                EditText newEditText = new EditText(getApplicationContext());
                newEditText.setLayoutParams(input3.getLayoutParams());
                newEditText.setBackground(input3.getBackground());
                newEditText.setHint("");
                newEditText.setPadding(input3.getPaddingLeft(), input3.getPaddingTop(), input3.getPaddingRight(), input3.getPaddingBottom());
                newEditText.setTextColor(input3.getTextColors());

                linearLayout.addView(newEditText);
            }
        });




    }

}