package com.pencil.charlist;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;


public class CreateHero extends Activity {
    final String LOG_TAG = "myLogs";
    public String myPath = Choose_Hero.myPath;
    public DBHelper mDBHelper = Choose_Hero.mDBHelper;
    public static SQLiteDatabase database = Choose_Hero.database;

    Button butCreateHero;
    EditText name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_hero);

        butCreateHero = findViewById(R.id.butCreate);
        name = findViewById(R.id.textNameHero);


        butCreateHero.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Герой должен иметь имя", Toast.LENGTH_SHORT).show();
                } else {
                    mDBHelper.CreateTableChar(database, name.getText().toString());
                    mDBHelper.SaveStatusHero(database, name.getText().toString(), 0, 0, 0, 0, 0, 0, 0);

                    Intent intent = new Intent(CreateHero.this, MainActivity.class);
                    intent.putExtra("NameChar", name.getText().toString());
                    startActivity(intent);
                    finish();
                }


                //Log.d(LOG_TAG, name.getText().toString());


            }
        });

    }
}
