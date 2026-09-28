package com.example.kovenkopr19;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.window.SplashScreen;

public class MainActivity extends AppCompatActivity {

    ListView userList;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userList = findViewById(R.id.list);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Activity_user.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        databaseHelper = new DatabaseHelper(getApplicationContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        db = databaseHelper.getReadableDatabase();


        // Формируем SQL-запрос, склеивающий 4 поля в 2 выводимые строки (info1 и info2)
        String query = "SELECT " + DatabaseHelper.COLUMN_ID + ", " +
                "(" + DatabaseHelper.COLUMN_NAME + " || char(10) || " + DatabaseHelper.COLUMN_YEAR + ") AS info1, " +
                "(" + DatabaseHelper.COLUMN_EMAIL + " || char(10) || " + DatabaseHelper.COLUMN_PHONE + ") AS info2 " +
                "FROM " + DatabaseHelper.TABLE;
        userCursor = db.rawQuery(query, null);

        // Используем стандартный встроенный двухстрочный макет Android
        String[] headers = new String[]{"info1", "info2"};
        int[] to = new int[]{android.R.id.text1, android.R.id.text2};

        userAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.two_line_list_item,
                userCursor,
                headers,
                to,
                0
        );
        userList.setAdapter(userAdapter);
    }

    public void add(View view) {
        Intent intent = new Intent(this, Activity_user.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (userCursor != null && !userCursor.isClosed()) {
            userCursor.close();
        }
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}