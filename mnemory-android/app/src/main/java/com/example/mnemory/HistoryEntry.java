package com.example.mnemory;

public class HistoryEntry {
    private int idUser;
    private String mnemonicSentence;
    private String dateGenerated;
    private String wordsUsed;
    private int noOfWords;
    private int rating;

    public int getIdUser() {
        return idUser;
    }

    public String getMnemonicSentence() {
        return mnemonicSentence;
    }

    public String getDateGenerated() {
        return dateGenerated;
    }

    public String getWordsUsed() {
        return wordsUsed;
    }

    public int getNoOfWords() {
        return noOfWords;
    }

    public int getRating() {
        return rating;
    }

    public HistoryEntry(int idUser, String mnemonicSentence, String dateGenerated,
                        String wordsUsed, int noOfWords, int rating){
        this.idUser = idUser;
        this.mnemonicSentence = mnemonicSentence;
        this.dateGenerated = dateGenerated;
        this.wordsUsed = wordsUsed;
        this.noOfWords = noOfWords;
        this.rating = rating;
    }


}
