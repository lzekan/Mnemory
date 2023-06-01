package com.example.mnemory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        TextView linkToRegister = findViewById(R.id.linkToRegister);

        linkToRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                EditText inputUsername = (EditText) findViewById(R.id.inputUsername);
                EditText inputPassword = (EditText) findViewById(R.id.inputPassword);

                if(inputUsername.getText().length() == 0 || inputPassword.getText().length() == 0){
                    Toast.makeText(getApplicationContext(), "Molimo popunite prazna polja.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}