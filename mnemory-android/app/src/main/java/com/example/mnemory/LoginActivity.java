package com.example.mnemory;

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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        TextView linkToRegister = findViewById(R.id.linkToRegister);
        EditText txtUsername = findViewById(R.id.inputUsername);
        EditText txtPassword = findViewById(R.id.inputPassword);
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

                if(txtUsername.getText().length() == 0 || txtPassword.getText().length() == 0){
                    Toast.makeText(getApplicationContext(), "Molimo popunite prazna polja.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
                Call<ResponseBody> call = methods.findUserByUsername(String.valueOf(txtUsername.getText()));
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                String responseData = response.body().string();
                                JSONObject json = new JSONObject(responseData);

                                String password = json.get("password").toString();
                                Log.d(password, txtPassword.getText().toString());

                                if(password.equals(txtPassword.getText().toString())){
                                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Netočan unos korisničkog imena ili lozinke.", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException | IOException e) {
                                Toast.makeText(getApplicationContext(), "Netočan unos korisničkog imena ili lozinke.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Došlo je do problema u konekciji.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });



            }
        });

    }


}
