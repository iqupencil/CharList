package com.pencil.charlist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class Choose_Hero extends Activity {

    Button butCreate, butLoad;

    public static String myPath = DBHelper.Database_path + DBHelper.Database_name;
    public static DBHelper mDBHelper;
    public static SQLiteDatabase database;


    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_hero);

        mDBHelper = new DBHelper(this);
        database = mDBHelper.getWritableDatabase();



        butCreate = findViewById(R.id.butCreateHero);
        butLoad = findViewById(R.id.butLoadHero);
        List list = new ArrayList<>();

        mDBHelper.getAvalibleChar(database, list);
        if (list.size() == 0) {
            butLoad.setBackgroundColor(Color.GRAY);
            butLoad.setTextColor(R.color.disableText);
            butLoad.setEnabled(false);
        }

        butCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Choose_Hero.this, CreateHero.class);
                startActivity(intent);
            }
        });

        butLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Choose_Hero.this, LoadHero.class);
                startActivity(intent);
            }
        });
    }
}
