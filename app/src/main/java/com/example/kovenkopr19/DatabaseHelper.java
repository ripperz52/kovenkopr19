package com.example.kovenkopr19;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "userstore.db";
    private static final int SCHEMA = 2; // Увеличили версию базы данных для обновления схемы
    static final String TABLE = "users";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_YEAR = "year";

    // Новые столбцы для Задания 4
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Добавляем новые столбцы в запрос CREATE TABLE
        db.execSQL("CREATE TABLE " + TABLE + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_YEAR + " INTEGER, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_PHONE + " TEXT);");

        // Добавляем начальную запись с учетом новых полей
        db.execSQL("INSERT INTO " + TABLE + " ("
                + COLUMN_NAME + ", "
                + COLUMN_YEAR + ", "
                + COLUMN_EMAIL + ", "
                + COLUMN_PHONE + ") VALUES ('Том Смит', 1981, 'tom@example.com', '+79991112233');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
}