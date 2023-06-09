package com.example.mnemory;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
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

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.mnemory.Helper.DatabaseHelperSQLite;
import com.example.mnemory.User.UserManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistoryFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private DatabaseHelperSQLite dbHelper = null;
    private List<HistoryEntry> historyEntries = new ArrayList<>();
    private boolean isFirstSelection = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        TextView txtCreate = view.findViewById(R.id.txtCreate);
        txtCreate.setOnClickListener(v -> {
            startActivity(new Intent(inflater.getContext(), MainActivity.class));
        });

        Spinner dropdownMenu = view.findViewById(R.id.dropdown_menu);
        List<String> items = List.of("Abeceda", "Broj riječi", "Ocjena", "Datum izrade");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_spinner_dropdown_item, items);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dropdownMenu.setAdapter(adapter);
        dropdownMenu.setOnItemSelectedListener(this);

        dbHelper = new DatabaseHelperSQLite(view.getContext());

        Cursor cursor = dbHelper.getRowsById(UserManager.getInstance().getCurrentUser().getId());
        historyEntries = new ArrayList<>();

        if (cursor != null) {

            if (cursor.moveToFirst()) {

                LinearLayout layoutNoHistory = view.findViewById(R.id.layoutNoHistory);
                layoutNoHistory.setVisibility(View.INVISIBLE);

                LinearLayout layoutHistory = view.findViewById(R.id.layoutHistory);
                layoutHistory.setVisibility(View.VISIBLE);

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

                historyEntries = HistoryEntrySorting.sortByFirstLetter(historyEntries);

                for(HistoryEntry historyEntry : historyEntries){

                    LinearLayout layoutSentences = view.findViewById(R.id.layoutSentences);
                    layoutSentences.addView(createHistoryEntry(historyEntry, view));

                }

//                LinearLayout layoutSentences = view.findViewById(R.id.layoutSentences);
//                Toast.makeText(getContext(),
//                        String.valueOf(layoutSentences.getChildCount()), Toast.LENGTH_SHORT).show();

            } else {

            }

            cursor.close();
        }

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (isFirstSelection) {
            isFirstSelection = false;
            return;
        }

        String selectedItem = parent.getItemAtPosition(position).toString();
        //Toast.makeText(view.getContext(), selectedItem + " " + selectedItem.length(), Toast.LENGTH_SHORT).show();
        LinearLayout layoutSentences = getActivity().findViewById(R.id.layoutSentences);

        if(layoutSentences != null){

            if(selectedItem.equals("Abeceda")){
                historyEntries = HistoryEntrySorting.sortByFirstLetter(historyEntries);
            } else if(selectedItem.equals("Ocjena")){
                historyEntries = HistoryEntrySorting.sortByRating(historyEntries);
            } else if(selectedItem.equals("Broj riječi")){
                historyEntries = HistoryEntrySorting.sortByWordCount(historyEntries);
            } else {
                historyEntries = HistoryEntrySorting.sortByDateGenerated(historyEntries);
            }

            View first = (layoutSentences.getChildAt(0));

            layoutSentences.removeAllViews();

            layoutSentences.addView(first);

            for(HistoryEntry historyEntry : historyEntries){
                layoutSentences.addView(createHistoryEntry(historyEntry, getView()));
            }
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public LinearLayout createHistoryEntry(HistoryEntry historyEntry, View view){


        LinearLayout layout = new LinearLayout(view.getContext());
        layout.setId(R.id.layoutExampleHistory);

        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        layout.setOrientation(LinearLayout.HORIZONTAL);

        layout.setPadding(view.findViewById(R.id.layoutExampleHistory).getPaddingLeft(),
                view.findViewById(R.id.layoutExampleHistory).getPaddingTop(),
                view.findViewById(R.id.layoutExampleHistory).getPaddingRight(),
                view.findViewById(R.id.layoutExampleHistory).getPaddingBottom());
        layout.setBackgroundResource(R.drawable.custom_edittext);

        LinearLayout.LayoutParams layoutParamsTotal = (LinearLayout.LayoutParams) layout.getLayoutParams();
        layoutParamsTotal.setMargins(0, 30, 0, 0);
        layout.setLayoutParams(layoutParamsTotal);

        TextView textView1 = new TextView(getContext());
        textView1.setLayoutParams(new LinearLayout.LayoutParams(
                650, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView1.setTextSize(20);
        textView1.setText(historyEntry.getMnemonicSentence());
        textView1.setPadding(view.findViewById(R.id.rightTextView).getPaddingLeft(),
                view.findViewById(R.id.rightTextView).getPaddingTop(),
                view.findViewById(R.id.rightTextView).getPaddingRight(),
                view.findViewById(R.id.rightTextView).getPaddingBottom());
        textView1.setSingleLine(true);
        textView1.setEllipsize(TextUtils.TruncateAt.END);

        TextView textView2 = new TextView(view.getContext());
        textView2.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
        textView2.setTextSize(20);
        textView2.setText(String.valueOf(historyEntry.getRating()));
        textView2.setPadding(view.findViewById(R.id.leftTextView).getPaddingLeft(),
                view.findViewById(R.id.leftTextView).getPaddingTop(),
                view.findViewById(R.id.leftTextView).getPaddingRight(),
                view.findViewById(R.id.leftTextView).getPaddingBottom());

        if(historyEntry.getRating() < 4) textView2.setTextColor(getResources().getColor(R.color.red));
        else if(historyEntry.getRating() < 8) textView2.setTextColor(getResources().getColor(R.color.mustard));
        else textView2.setTextColor(getResources().getColor(R.color.green));

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView2.getLayoutParams();
        layoutParams.leftMargin = 140;
        textView2.setLayoutParams(layoutParams);

        layout.addView(textView1);
        layout.addView(textView2);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle(historyEntry.getMnemonicSentence())
                        .setMessage("Uneseni pojmovi: " +
                                historyEntry.getWordsUsed().substring(1, historyEntry.getWordsUsed().length()-1) + '\n' +
                                "Datum izrade: " + historyEntry.getDateGenerated() + '\n' +
                                "Vaša ocjena: " + historyEntry.getRating())
                        .setPositiveButton("OK", null)
                        .create()
                        .show();
            }
        });

        return layout;

    }



}
