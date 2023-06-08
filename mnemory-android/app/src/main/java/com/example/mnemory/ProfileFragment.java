package com.example.mnemory;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mnemory.User.User;
import com.example.mnemory.User.UserDTO;
import com.example.mnemory.User.UserManager;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        EditText profileUsername = view.findViewById(R.id.profileUsername);
        profileUsername.setText(UserManager.getInstance().getCurrentUser().getUsername());

        EditText profileEmail = view.findViewById(R.id.profileEmail);
        profileEmail.setText(UserManager.getInstance().getCurrentUser().getEmail());

        EditText profilePassword = view.findViewById(R.id.profilePassword);
        profilePassword.setText(UserManager.getInstance().getCurrentUser().getPassword());

        EditText profilePassword2 = view.findViewById(R.id.profilePassword2);
        profilePassword2.setText(UserManager.getInstance().getCurrentUser().getPassword());

        Button btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            UserManager.getInstance().logout();
            startActivity(new Intent(inflater.getContext(), LoginActivity.class));
        });

        Button btnUpdate = view.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(v -> {

            String strUsername = profileUsername.getText().toString().trim();
            String strEmail = profileEmail.getText().toString().trim();
            String strPassword = profilePassword.getText().toString().trim();
            String strPassword2 = profilePassword2.getText().toString().trim();

            if(strUsername.length() == 0 ||
                    strEmail.length() == 0 ||
                        strPassword.length() == 0 ||
                            strPassword2.length() == 0){
                Toast.makeText(inflater.getContext(), "Molimo popunite prazna polja.", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!RegisterActivity.validate(strEmail)){
                Toast.makeText(inflater.getContext(), "Molimo unesite pravilnu email adresu.", Toast.LENGTH_SHORT).show();
                return;
            }

            if(strPassword.length() < 6){
                Toast.makeText(inflater.getContext(), "Lozinka mora imati bar 6 znakova.", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!strPassword.equals(strPassword2)){
                Toast.makeText(inflater.getContext(), "Lozinke se ne podudaraju.", Toast.LENGTH_SHORT).show();
                return;
            }

            User updatedUser = new User(UserManager.getInstance().getCurrentUser().getId(),
                    strUsername, strPassword, strEmail);

            //Toast.makeText(view.getContext(), updatedUser.toString(), Toast.LENGTH_SHORT).show();

            Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
            Call<ResponseBody> call = methods.updateUser(updatedUser);

            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        try {
                            Toast.makeText(inflater.getContext(), response.body().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {

                        }
                        UserManager.getInstance().setCurrentUser(updatedUser);

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);

                    }
                    else {
                        Toast.makeText(inflater.getContext(), "Doslo je do pogreske", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(inflater.getContext(),"Došlo je do greške u konekciji.", Toast.LENGTH_SHORT).show();
                }


            });




        });


        return view;
    }
}
