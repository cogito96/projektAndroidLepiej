package com.example.aa.systemymobilne.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.aa.systemymobilne.model.PytaniaGra;

import java.util.ArrayList;

/**
 * Created by aa on 22.01.2018.
 */

public class PytaniaDbAdapter {

    private static final String DEBUG_TAG = "SqLiteTodoManager";

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "database.db";
    private static final String DB_TODO_TABLE = "todo";

    public static final String KLUCZ_ID = "_id";
    public static final String ID_OPCJE = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final int ID_COLUMN = 0;
    public static final String KLUCZ_PYTANIE = "description";
    public static final String PYTANIE_OPCJE = "TEXT NOT NULL";
    public static final int PYTANIE_KOLUMNA = 1;


    public static final String KLUCZ_ODPOWIEDZ = "completed";
    public static final String ODPOWIEDZ_OPCJE = "TEXT NOT NULL";
    public static final int ODPOWIEDZ_KOLUMNA = 2;

    public static final String KLUCZ_KATEGORIA = "kategoria";
    public static final String KATEGORIA_OPCJE = "TEXT NOT NULL";
    public static final int KATEGORIA_KOLUMNA = 3;

    private static final String DB_CREATE_TODO_TABLE =
            "CREATE TABLE " + DB_TODO_TABLE + "( " +
                    KLUCZ_ID + " " + ID_OPCJE + ", " +
                    KLUCZ_PYTANIE + " " + PYTANIE_OPCJE + ", " +
                    KLUCZ_ODPOWIEDZ + " " + ODPOWIEDZ_OPCJE + ", " +
                    KLUCZ_KATEGORIA + " " + KATEGORIA_OPCJE +
                    " );";
    private static final String DROP_TODO_TABLE =
            "DROP TABLE IF EXISTS " + DB_TODO_TABLE;

    private SQLiteDatabase db;
    private Context context;
    private DatabaseHelper dbHelper;

    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE_TODO_TABLE);

            Log.d(DEBUG_TAG, "Database creating...");
            Log.d(DEBUG_TAG, "Table " + DB_TODO_TABLE + " ver." + DB_VERSION + " created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_TODO_TABLE);

            Log.d(DEBUG_TAG, "Database updating...");
            Log.d(DEBUG_TAG, "Table " + DB_TODO_TABLE + " updated from ver." + oldVersion + " to ver." + newVersion);
            Log.d(DEBUG_TAG, "All data is lost.");

            onCreate(db);
        }
    }

    public PytaniaDbAdapter(Context context) {
        this.context = context;
    }

    public PytaniaDbAdapter open() {
        dbHelper = new DatabaseHelper(context, DB_NAME, null, DB_VERSION);
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long insertTodo(String pytanie, String odpowiedz , String  kategoria) {
        ContentValues newTodoValues = new ContentValues();
        newTodoValues.put(KLUCZ_PYTANIE, pytanie);
        newTodoValues.put(KLUCZ_ODPOWIEDZ, odpowiedz);
        newTodoValues.put(KLUCZ_KATEGORIA, kategoria);

        return db.insert(DB_TODO_TABLE, null, newTodoValues);
    }

    public boolean updateTodo(PytaniaGra task) {
        long id = task.getId();
        String description = task.getPytanie();
        String odpowiedz = task.getPytanie();
        String kategoria = task.getKategoria();
        return updateTodo(id, description, odpowiedz , kategoria);
    }

    public boolean updateTodo(long id, String description, String odpowiedz , String kategoria) {
        String where = KLUCZ_ID + "=" + id;
        ContentValues updateTodoValues = new ContentValues();
        updateTodoValues.put(KLUCZ_PYTANIE, description);
        updateTodoValues.put(KLUCZ_ODPOWIEDZ, odpowiedz);
        updateTodoValues.put(KLUCZ_KATEGORIA, kategoria);
        return db.update(DB_TODO_TABLE, updateTodoValues, where, null) > 0;
    }

    public boolean deleteTodo(long id) {
        String where = KLUCZ_ID + "=" + id;
        return db.delete(DB_TODO_TABLE, where, null) > 0;
    }

    public Cursor getAllTodos() {
        String[] columns = {KLUCZ_ID, KLUCZ_PYTANIE, KLUCZ_ODPOWIEDZ , KLUCZ_KATEGORIA};
        return db.query(DB_TODO_TABLE, columns, null, null, null, null, null);
    }


    public PytaniaGra getTodo(long id) {
        String[] columns = {KLUCZ_ID, KLUCZ_PYTANIE, KLUCZ_ODPOWIEDZ , KLUCZ_KATEGORIA};
        String where = KLUCZ_ID + "=" + id;
        Cursor cursor = db.query(DB_TODO_TABLE, columns, where, null, null, null, null);
        PytaniaGra task = null;

        if (cursor != null && cursor.moveToFirst()) {
            String description = cursor.getString(PYTANIE_KOLUMNA);
            String completed = cursor.getString(ODPOWIEDZ_KOLUMNA);
            String kategoria = cursor.getString(KATEGORIA_KOLUMNA);

            task = new PytaniaGra(id, description, completed , kategoria);
        }
        return task;
    }
}