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
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.util.ArrayList;


public class Create_item extends Activity {
    EditText nameItem;
    EditText valueItem;
    EditText descrItem;
    Button butCreate, butAdd;

    ListView ListOfItemView;

    ArrayList<ItemList> arrlistItem = new ArrayList<>();
    MultipleListAdapter listAdapter;


    final String LOG_TAG = "myLogs";
    String attachment;
    ArrayList list;

    //ExpandableListAdapter adapter = Armor_item.listAdapter;
    String name = MainActivity.nameHero;
    public DBHelper mDBHelper = Choose_Hero.mDBHelper;
    SQLiteDatabase database = Choose_Hero.database;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_item);
        nameItem = findViewById(R.id.editNameItem);
        valueItem = findViewById(R.id.editDefItem);
        descrItem = findViewById(R.id.editDescItem);
        butCreate = findViewById(R.id.butCreateItem);
        butAdd = findViewById(R.id.butAddItem);


        final Intent intent = getIntent();
        attachment = intent.getStringExtra("Attachment");
        Log.d(LOG_TAG, "attachment " + attachment);
        ListOfItemView = findViewById(R.id.listAllItems);

        arrlistItem.clear();
        //listDataHeader = new ArrayList<String>();
        //listDataChild = new HashMap<String, List<String>>();
        if (attachment.equals("armor")) {
          mDBHelper.LoadArmorItem(database, "Armor_items", arrlistItem);
        } else if (attachment.equals("weapon")) {
            mDBHelper.LoadWeaponItem(database, "Weapon_items", arrlistItem);
        }else if (attachment.equals("misc")) {
            mDBHelper.LoadMiscItem(database, "Misc_items", arrlistItem);
        }

        //arrlistItem.add(new ItemList("TUNICA", "35", "Simple", false));

        listAdapter = new MultipleListAdapter(this, arrlistItem, "inventory", false);
        //Log.d(LOG_TAG, "LIST OF ITEM " + listAdapter.getItemToPos(0));
        ListOfItemView.setAdapter(listAdapter);
       // listAdapter = new ExpandableListAdapter(Create_item.this, listDataHeader, listDataChild, true, null, null);

        // setting list adapter
        //expListView.setAdapter(listAdapter);
        //list = new ArrayList();


        butCreate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                boolean b_name;
                boolean b_value;
                if(nameItem.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Отсутствует название", Toast.LENGTH_SHORT).show();
                    b_name=false;
                } else {b_name=true;}
                if(valueItem.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Отсутствует значение", Toast.LENGTH_SHORT).show();
                    b_value=false;
                } else {b_value=true;}

                Log.d(LOG_TAG, "attachment " + attachment);
                if (b_name && b_value) {
                    mDBHelper.CreateItem(database, name, nameItem.getText().toString(), valueItem.getText().toString(), descrItem.getText().toString(), attachment);
                    Log.d(LOG_TAG, "CREATE ITEM FINISH!!!");
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });

        butAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listAdapter.getBox().size() == 0) {
                    Toast.makeText(getApplicationContext(), "Предметы не выбраны", Toast.LENGTH_SHORT).show();
                } else {
                    mDBHelper.AddItems(database, name, arrlistItem, attachment);
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });

    }


}
