package com.example.mnemory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mnemory.User.UserManager;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChoosePreferenceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private List<String> preferences = new ArrayList<>();
    private LinearLayout layoutPreferences;
    private static boolean creation = true;
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_preference);

        layoutPreferences = findViewById(R.id.layoutPreferences);
        btnConfirm = findViewById(R.id.btnConfirm);


        ImageView x = findViewById(R.id.closeButton);
        x.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MainActivity.class);
            startActivity(intent);
        });

        TextView pickLater = findViewById(R.id.pickLater);
        pickLater.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MainActivity.class);
            startActivity(intent);
        });

        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<ResponseBody> call = methods.getAllPreferences();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){

                    String data;
                    List<String> preferencesCopy = new ArrayList<>();
                    try {
                        data = response.body().string();
                        data = data.substring(1, data.length() - 1);

                        preferencesCopy = List.of(data.split(","));

                        for(String pref : preferencesCopy){
                            preferences.add(pref.substring(1, pref.length() - 1).toUpperCase());
                        }

                        Spinner spinner = findViewById(R.id.preferencesSpinner);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(ChoosePreferenceActivity.this, android.R.layout.simple_spinner_dropdown_item, preferences);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);

                        spinner.setOnItemSelectedListener(ChoosePreferenceActivity.this);


                    } catch (Exception e) {

                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Doslo je do pogreske u konekciji.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> preferences = new ArrayList<>();

                for(int i = 0; i < layoutPreferences.getChildCount(); i++){
                    EditText preference = (EditText) layoutPreferences.getChildAt(i);

                    if(preference.getText().toString().length() > 0){
                        preferences.add(preference.getText().toString().toLowerCase());
                    }

                }

                if(preferences.size() == 0){
                    Toast.makeText(getApplicationContext(), "Odaberite bar jednu temu ili preskočite ovaj korak.", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserManager.getInstance().getCurrentUser().setPreferences(preferences);
                addPreferencesToUser(preferences, UserManager.getInstance().getCurrentUser().getId());

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String selectedItem = parent.getItemAtPosition(position).toString();

        for(int i = 0; i < 3; i++){
            EditText pref = (EditText) layoutPreferences.getChildAt(i);

            if(selectedItem.equals(pref.getText().toString())){
                Toast.makeText(getApplicationContext(), "Tema je već odabrana,", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!creation && pref.getText().toString().equals("")){
                pref.setText(selectedItem);
                return;
            }

            if(creation) pref.setText("");

        }

        creation = false;


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void addPreferencesToUser(List<String> preferences, int userId){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<ResponseBody> call = methods.addPreferencesToUser(userId, preferences);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    startActivity(new Intent(ChoosePreferenceActivity.this, MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Toast.makeText(getApplicationContext(), "Neuspjeh.", Toast.LENGTH_SHORT).show();
            }
        });

    }



}
