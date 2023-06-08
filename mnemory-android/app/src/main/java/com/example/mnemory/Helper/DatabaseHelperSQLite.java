package com.example.mnemory.Helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelperSQLite extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "History";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelperSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE history " +
                "(idUser INTEGER, " +
                "mnemonicSentence TEXT, " +
                "dateGenerated DATETIME, " +
                "wordsUsed TEXT, " +
                "noOfWords INTEGER, " +
                "rating INTEGER)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getRowsById(int id) {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                "idUser",
                "mnemonicSentence",
                "dateGenerated",
                "wordsUsed",
                "noOfWords",
                "rating"
        };

        String selection = "idUser = ?";
        String[] selectionArgs = {String.valueOf(id)};

        return db.query(
                "history",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }

}
