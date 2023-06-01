package com.example.mnemory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView linkToLogin = findViewById(R.id.linkToLogin);
        Button btnRegister = findViewById(R.id.btnRegister);



        linkToLogin.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this, MainActivity.class)));

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputUsername = (EditText) findViewById(R.id.inputUsername);
                EditText inputEmail = (EditText) findViewById(R.id.inputEmail);
                EditText inputPassword = (EditText) findViewById(R.id.inputPassword);

                if(inputUsername.getText().length() == 0 || inputEmail.getText().length() == 0){
                    Toast.makeText(getApplicationContext(), "Molimo popunite prazna polja.", Toast.LENGTH_SHORT).show();
                }

                if(inputPassword.getText().length() < 6){
                    Toast.makeText(getApplicationContext(), "Lozinka mora imati bar 6 znakova.", Toast.LENGTH_SHORT).show();
                }

                if(!validate(String.valueOf(inputEmail.getText()))){
                    Toast.makeText(getApplicationContext(), "Molimo unesite pravilnu email adresu.", Toast.LENGTH_SHORT).show();
                }

            }
        });






    }
}
