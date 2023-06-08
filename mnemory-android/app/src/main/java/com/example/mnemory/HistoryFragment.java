package com.example.mnemory;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mnemory.Helper.DatabaseHelperSQLite;
import com.example.mnemory.User.UserManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistoryFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private DatabaseHelperSQLite dbHelper = null;
    private List<HistoryEntry> historyEntries = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        TextView txtCreate = view.findViewById(R.id.txtCreate);
        txtCreate.setOnClickListener(v -> {
            startActivity(new Intent(inflater.getContext(), MainActivity.class));
        });

        Spinner dropdownMenu = view.findViewById(R.id.dropdown_menu);
        List<String> items = List.of("Abeceda", "Broj rijeci", "Ocjena");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_spinner_dropdown_item, items);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dropdownMenu.setAdapter(adapter);
        dropdownMenu.setOnItemSelectedListener(this);

        dbHelper = new DatabaseHelperSQLite(getContext());

        Cursor cursor = dbHelper.getRowsById(UserManager.getInstance().getCurrentUser().getId());
        historyEntries = new ArrayList<>();

        if (cursor != null) {
            LinearLayout layoutNoHistory = view.findViewById(R.id.layoutNoHistory);
            layoutNoHistory.setVisibility(View.INVISIBLE);

            if (cursor.moveToFirst()) {
                do {
                    int idUser = cursor.getInt(0);
                    String mnemonicSentence = cursor.getString(1);
                    String dateGenerated = cursor.getString(2);
                    String wordsUsed = cursor.getString(3);
                    int noOfWords = cursor.getInt(4);
                    int rating = cursor.getInt(5);

                    historyEntries.add(new HistoryEntry(idUser, mnemonicSentence, dateGenerated,
                            wordsUsed, noOfWords, rating));

                } while (cursor.moveToNext());

                for(HistoryEntry historyEntry : historyEntries){
                    LinearLayout newInput = new LinearLayout(view.getContext());

                    
                }



            } else {
                Toast.makeText(getContext(), "No rows found.", Toast.LENGTH_SHORT).show();
            }

            cursor.close();
        }

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
