package com.example.mnemory;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mnemory.Helper.DatabaseHelperSQLite;
import com.example.mnemory.User.UserManager;

import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment {

    private LinearLayout containerLayout;
    private String template = "";
    private DatabaseHelperSQLite dbHelper = null;
    private Button minusBtnExample;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        minusBtnExample = view.findViewById(R.id.minusButton_1);

        dbHelper = new DatabaseHelperSQLite(requireContext());

        Button btnAdd = view.findViewById(R.id.btnAdd);
        containerLayout = view.findViewById(R.id.containerLayout);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewEditText();
            }
        });

        Button btnMinus1 = view.findViewById(R.id.minusButton_1);
        btnMinus1.setOnClickListener(v -> removeEditText((View) btnMinus1.getParent()));

        Button btnMinus2 = view.findViewById(R.id.minusButton_2);
        btnMinus2.setOnClickListener(v -> removeEditText((View) btnMinus2.getParent()));

        Button btnMinus3 = view.findViewById(R.id.minusButton_3);
        btnMinus3.setOnClickListener(v -> removeEditText((View) btnMinus3.getParent()));

        Button btnGenerate = view.findViewById(R.id.btnGenerate);
        btnGenerate.setOnClickListener(v -> {
            try {
                generateSentence();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        return view;
    }

    private void addNewEditText() {
        EditText regularInput = requireView().findViewById(R.id.input);
        EditText editText = new EditText(requireContext());

        editText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        editText.setHint("Unesi novi pojam...");
        editText.setLayoutParams(regularInput.getLayoutParams());
        editText.setBackgroundResource(R.drawable.custom_edittext);
        editText.setId(containerLayout.getChildCount() + 1);
        editText.setPadding(regularInput.getPaddingLeft(), 0, 0 , 0);

        Button exampleButton = requireView().findViewById(R.id.minusButton);
        Button minusButton = new Button(requireContext());

        minusButton.setLayoutParams(minusBtnExample.getLayoutParams());
        minusButton.setText("X");
        minusButton.setTag("minusButton" + containerLayout.getChildCount() + 1);
        minusButton.setBackground(minusBtnExample.getBackground());
        minusButton.setTextColor(getView().getResources().getColor(R.color.white));

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeEditText((View) minusButton.getParent());
            }
        });

        LinearLayout newInput = new LinearLayout(requireContext());

        newInput.setLayoutParams(requireView().findViewById(R.id.inputLayout).getLayoutParams());
        newInput.setPadding(requireView().findViewById(R.id.inputLayout).getPaddingLeft(), requireView().findViewById(R.id.inputLayout).getPaddingTop(),
                requireView().findViewById(R.id.inputLayout).getPaddingRight(), requireView().findViewById(R.id.inputLayout).getPaddingBottom());

        newInput.addView(editText);
        newInput.addView(minusButton);

        newInput.setId(containerLayout.getChildCount());

        containerLayout.addView(newInput);
        newInput.setId(containerLayout.getChildCount());

        if(containerLayout.getChildCount() == 10){
            Button btnAdd = requireView().findViewById(R.id.btnAdd);
            btnAdd.setVisibility(View.INVISIBLE);
        }
    }

    private void removeEditText(View vp){
        Button btnAdd = requireView().findViewById(R.id.btnAdd);
        btnAdd.setVisibility(View.VISIBLE);

        if(containerLayout.getChildCount() == 3){
            Toast.makeText(requireContext(), "Minimalan broj dopuštenih pojmova je 3.", Toast.LENGTH_SHORT).show();
        } else {
            containerLayout.removeView(vp);
        }
    }

    private void generateSentence() throws ExecutionException, InterruptedException {
        List<String> listOfWords = new ArrayList<>();
        List<String> userPreferences = UserManager.getInstance().getCurrentUser().getPreferences();

        //Toast.makeText(getContext(), userPreferences.toString(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(getContext(), " " + userPreferences.toString(), Toast.LENGTH_SHORT).show();

        for(int i = 0; i < containerLayout.getChildCount(); i++){
            ViewGroup vg = (ViewGroup) containerLayout.getChildAt(i);

            String word = ((EditText) vg.getChildAt(0)).getText().toString().trim().toLowerCase();

            if(word.length() == 0){
                Toast.makeText(requireContext(), "Popunite prazna polja.", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!Character.isAlphabetic(word.charAt(0))){
                Toast.makeText(requireContext(), "Svi pojmovi trebaju započinjati slovom.", Toast.LENGTH_SHORT).show();
                return;
            }

            listOfWords.add(word);
        }

        Switch switchRandom = requireView().findViewById(R.id.switchRandom);
        if(switchRandom.isChecked()){
            Collections.shuffle(listOfWords);
        }

        template = getTemplate(containerLayout.getChildCount());

        List<String> wordTypesInATemplate = List.of(template.split("-"));

        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<ResponseBody> call = methods.getSentenceFromDictionary(listOfWords, wordTypesInATemplate, userPreferences);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        String generatedSentence = response.body().string();
                        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

                        builder.setTitle("Želite li ocijeniti generiranu rečenicu?")
                                .setMessage(generatedSentence)
                                .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        AlertDialog.Builder ratingBuilder = new AlertDialog.Builder(requireContext());
                                        View ratingDialogView = getLayoutInflater().inflate(R.layout.rating_dialog_layout, null);
                                        ratingBuilder.setView(ratingDialogView);

                                        SeekBar ratingSeekBar = ratingDialogView.findViewById(R.id.ratingSeekBar);
                                        ratingSeekBar.setMax(10);
                                        ratingSeekBar.setProgress(0);
                                        
                                        TextView ratingValueText = ratingDialogView.findViewById(R.id.ratingValueText);

                                        ratingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                            @Override
                                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                                ratingValueText.setText(String.valueOf(progress));
                                            }

                                            @Override
                                            public void onStartTrackingTouch(SeekBar seekBar) {
                                                // Not needed for this implementation
                                            }

                                            @Override
                                            public void onStopTrackingTouch(SeekBar seekBar) {
                                                // Not needed for this implementation
                                            }
                                        });


                                        ratingBuilder.setPositiveButton("Spremi", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                Integer idUser = UserManager.getInstance().getCurrentUser().getId();
                                                String sentence = generatedSentence;
                                                Timestamp ts = new Timestamp(new Date().getTime());
                                                String wordsUsed = listOfWords.toString();
                                                int noOfWords = listOfWords.size();
                                                int rating = ratingSeekBar.getProgress();

                                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                                ContentValues values = new ContentValues();

                                                values.put("idUser", idUser);
                                                values.put("mnemonicSentence", sentence);
                                                values.put("dateGenerated", ts.toString());
                                                values.put("wordsUsed", wordsUsed);
                                                values.put("noOfWords", noOfWords);
                                                values.put("rating", rating);
                                                db.insert("History", null, values);
                                                db.close();


                                            }
                                        });

                                        ratingBuilder.setNegativeButton("Odustani", null);

                                        AlertDialog ratingDialog = ratingBuilder.create();
                                        ratingDialog.show();
                                    }
                                })
                                .setNegativeButton("Ne", null);

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getTemplate(int wordCount){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<ResponseBody> call = methods.getTemplateByLength(wordCount);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseData = response.body().string();
                        template = responseData.split(",")[1];
                    } catch (IOException e) {
                        Toast.makeText(requireContext(), "Došlo je do pogreške pri komunikaciji s bazom.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(requireContext(), "Došlo je do problema u konekciji.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(requireContext(), "Došlo je do problema u konekciji.", Toast.LENGTH_SHORT).show();
            }
        });

        return template;
    }

}
