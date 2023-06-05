package com.example.mnemory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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



        linkToLogin.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputUsername = (EditText) findViewById(R.id.txtUsername);
                EditText inputEmail = (EditText) findViewById(R.id.txtEmail);
                EditText inputPassword = (EditText) findViewById(R.id.txtPassword);

                if(inputUsername.getText().length() == 0 || inputEmail.getText().length() == 0){
                    Toast.makeText(getApplicationContext(), "Molimo popunite prazna polja.", Toast.LENGTH_SHORT).show();
                }

                else if(inputPassword.getText().length() < 6){
                    Toast.makeText(getApplicationContext(), "Lozinka mora imati bar 6 znakova.", Toast.LENGTH_SHORT).show();
                }

                else if(!validate(String.valueOf(inputEmail.getText()))){
                    Toast.makeText(getApplicationContext(), "Molimo unesite pravilnu email adresu.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
                    Call<ResponseBody> call = methods.findUserByUsername(String.valueOf(inputUsername.getText()));

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                try {
                                    String responseData = response.body().string();
                                    JSONObject json = new JSONObject(responseData);

                                    if(json.get("username").toString().length() != 0){
                                        Toast.makeText(getApplicationContext(), "Korisničko ime se već koristi.", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception e) {
                                    Call<ResponseBody> call2 = methods.findUserByEmail(String.valueOf(inputEmail.getText()));

                                    call2.enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call2, Response<ResponseBody> response) {
                                            if (response.isSuccessful()) {
                                                try {
                                                    String responseData = response.body().string();
                                                    JSONObject json = new JSONObject(responseData);

                                                    if(json.get("username").toString() != null){
                                                        Toast.makeText(getApplicationContext(), "Email adresa se već koristi.", Toast.LENGTH_SHORT).show();
                                                    }

                                                } catch (Exception e) {
                                                    UserDTO userDTO = new UserDTO(inputUsername.getText().toString(), inputPassword.getText().toString(),
                                                            inputEmail.getText().toString());
                                                    Call<ResponseBody> call3 = methods.addNewUser(userDTO);

                                                    call3.enqueue(new Callback<ResponseBody>() {
                                                        @Override
                                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                            //Toast.makeText(getApplicationContext(), "Uspjesno kreiran novi korisnik.", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                                        }

                                                        @Override
                                                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                                                        }
                                                    });
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





                }






        });






    }
}
