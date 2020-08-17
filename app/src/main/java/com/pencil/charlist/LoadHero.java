package com.pencil.charlist;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class LoadHero extends Activity {

    public String myPath = Choose_Hero.myPath;
    public DBHelper mDBHelper = Choose_Hero.mDBHelper;
    public SQLiteDatabase database = Choose_Hero.database;

    private Spinner AllCharAvalible;

    ArrayAdapter<String> adapter;
    String selectItem;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_hero);

        AllCharAvalible = findViewById(R.id.spinChar);

       // String[] str = GetAvalibleChar();


        Button butLoad = findViewById(R.id.butLoad);
        final ArrayList list = new ArrayList();

       /* for(int i=0; i<str.length; i++) {
            list.add(str[i]);
        }*/

        mDBHelper.getAvalibleChar(database, list);

        adapter = new ArrayAdapter<String>(LoadHero.this, android.R.layout.simple_spinner_dropdown_item, list) {
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        AllCharAvalible.setAdapter(adapter);

        AllCharAvalible.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectItem = parent.getItemAtPosition(position).toString();
                //Log.d(LOG_TAG, "Selected "+selectItem);
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        butLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(list.size() == 0) {
                Toast.makeText(getApplicationContext(), "Персонаж не выбран", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(LoadHero.this, MainActivity.class);
                intent.putExtra("NameChar", selectItem);

                startActivity(intent);
                finish();
            }
            }
        });
    }

    /*@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String[] GetAvalibleChar() {
       // ArrayList<String> arrAvalibleChar = new ArrayList<>();

        String[] str = new String[0];
        if (SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY) != null) {
            if (mDBHelper.isTableExists(database, "AllPerson")) {
                Cursor cursor = database.rawQuery("SELECT * FROM AllPerson", null);
                if (cursor.getCount() > 0) {
                    str = new String[cursor.getCount()];
                    cursor.moveToFirst();
                    int i = 0;
                    while (!cursor.isAfterLast()) {
                        // arrAvalibleChar.add(cursor.getString(1));
                        str[i] = cursor.getString(1);
                        cursor.moveToNext();
                        i++;
                    }
                    i=0;
                    cursor.close();
                }
                //else Log.d(LOG_TAG, "Table '"+ "AllPerson" +"'is Empty");
            }else {
                database.execSQL("CREATE TABLE AllPerson (_id integer, Name text, PRIMARY KEY (_id))");
            }
        }//else Log
        return str;
    }*/
}
