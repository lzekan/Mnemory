package com.example.mnemory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class HistoryEntrySorting {

    public static List<HistoryEntry> sortByFirstLetter(List<HistoryEntry> historyEntries) {
        Collections.sort(historyEntries, new Comparator<HistoryEntry>() {
            @Override
            public int compare(HistoryEntry entry1, HistoryEntry entry2) {
                String sentence1 = entry1.getMnemonicSentence();
                String sentence2 = entry2.getMnemonicSentence();
                return Character.compare(sentence1.charAt(0), sentence2.charAt(0));
            }
        });

        return historyEntries;
    }

    // Sorts the list of HistoryEntry instances by the noOfWords field in ascending order
    public static List<HistoryEntry> sortByWordCount(List<HistoryEntry> historyEntries) {
        Collections.sort(historyEntries, new Comparator<HistoryEntry>() {
            @Override
            public int compare(HistoryEntry entry1, HistoryEntry entry2) {
                return Integer.compare(entry1.getNoOfWords(), entry2.getNoOfWords());
            }
        });

        return historyEntries;
    }

    // Sorts the list of HistoryEntry instances by the rating field in descending order
    public static List<HistoryEntry> sortByRating(List<HistoryEntry> historyEntries) {
        Collections.sort(historyEntries, new Comparator<HistoryEntry>() {
            @Override
            public int compare(HistoryEntry entry1, HistoryEntry entry2) {
                return Integer.compare(entry2.getRating(), entry1.getRating());
            }
        });

        return historyEntries;
    }

    public static List<HistoryEntry> sortByDateGenerated(List<HistoryEntry> historyEntries) {
        Collections.sort(historyEntries, new Comparator<HistoryEntry>() {
            @Override
            public int compare(HistoryEntry entry1, HistoryEntry entry2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date date1 = format.parse(entry1.getDateGenerated());
                    Date date2 = format.parse(entry2.getDateGenerated());
                    return date1.compareTo(date2);
                } catch (ParseException e) {
                    // Handle the parse exception if needed
                    e.printStackTrace();
                }
                return 0;
            }
        });

        return historyEntries;
    }
}
