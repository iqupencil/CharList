package com.pencil.charlist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {
    final String LOG_TAG = "myLogs";
    public static final int Database_version = 1;
    public static final String Database_name = "Characters";
    public static final String Database_path = "/data/data/com.pencil.charlist/databases/";
    public static final String myPath = Database_path + Database_name;
    Context contextApp;

    public DBHelper(@Nullable Context context) {
        super(context, Database_name, null, Database_version);
        contextApp = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(SQLiteDatabase db) {
        //long time= System.currentTimeMillis();
        if (SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY) != null) {
            if (!isTableExists(db, "Armor_items")) {
                //Log.d(LOG_TAG, "-----CREATE TABLE-----");
                db.execSQL("CREATE TABLE " + "Armor_items" + " (_id integer, "
                        + "Name text not null, "
                        + "Defense integer not null, "
                        + "Description text, "
                        + "id_item integer not null,"
                        + "PRIMARY KEY (_id))");
                    //Log.d(LOG_TAG, "TABLE " + NameTable + " is created");

               // Toast.makeText(contextApp, "Таблица защиты создана", Toast.LENGTH_SHORT).show();
                    //Log.d(LOG_TAG, "Inserting in AllPerson");
               // db.execSQL("INSERT INTO Armor_items (Name, Defense, Description, id_item) VALUES ('Tunica', 1, 'Simple armor', "+time+")");

                    //Log.d(LOG_TAG, "-----TABLE CREATE-----");
            } else {
                //Toast.makeText(contextApp, "Таблица защиты уже существует", Toast.LENGTH_SHORT).show();
                //Log.d(LOG_TAG, "Table '"+NameTable+"' exists");
            }
            if (!isTableExists(db, "Weapon_items")) {
                //Log.d(LOG_TAG, "-----CREATE TABLE-----");
                db.execSQL("CREATE TABLE " + "Weapon_items" + " (_id integer, "
                        + "Name text not null, "
                        + "Damage text not null, "
                        + "Description text, "
                        + "id_item integer not null,"
                        + "PRIMARY KEY (_id))");
                //Log.d(LOG_TAG, "TABLE " + NameTable + " is created");

               // Toast.makeText(contextApp, "Таблица оружия создана", Toast.LENGTH_SHORT).show();
                //Log.d(LOG_TAG, "Inserting in AllPerson");
               // db.execSQL("INSERT INTO Weapon_items (Name, Damage, Description, id_item) VALUES ('Rust Sword', '1d4', 'Bad short sword', "+time+")");

                //Log.d(LOG_TAG, "-----TABLE CREATE-----");
            }
            if (!isTableExists(db, "Misc_items")) {
                //Log.d(LOG_TAG, "-----CREATE TABLE-----");
                db.execSQL("CREATE TABLE " + "Misc_items" + " (_id integer, "
                        + "Name text not null, "
                        + "Effect text, "
                        + "Description text, "
                        + "id_item integer not null,"
                        + "PRIMARY KEY (_id))");
                //Log.d(LOG_TAG, "TABLE " + NameTable + " is created");

               // Toast.makeText(contextApp, "Таблица разное создана", Toast.LENGTH_SHORT).show();
                //Log.d(LOG_TAG, "Inserting in AllPerson");
                //db.execSQL("INSERT INTO Misc_items (Name, Effect, Description, id_item) VALUES ('Rock', 'Can be thrown or not', 'Small rock', "+time+")");

                //Log.d(LOG_TAG, "-----TABLE CREATE-----");

            }

        } //else Log.d(LOG_TAG, "Database not exists");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getAvalibleChar(SQLiteDatabase db, List AvaliblePerson) {

        if (SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY) != null) {
            if (isTableExists(db, "AllPerson")) {
                Cursor cursor = db.rawQuery("SELECT * FROM AllPerson", null);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    int i = 0;
                    while (!cursor.isAfterLast()) {
                        AvaliblePerson.add(cursor.getString(1));
                        cursor.moveToNext();
                        i++;
                    }
                    i=0;
                    cursor.close();
                }
            }else {
                db.execSQL("CREATE TABLE AllPerson (_id integer, Name text, PRIMARY KEY (_id))");
            }
        }//else Log
    }

    //// Создание новой таблицы персонажа со значениями поумолчанию + создание и внесение в общую таблицу персонажей
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void CreateTableChar (SQLiteDatabase db, String NameTable) {
        if (SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY) != null) {
            if (!isTableExists(db, NameTable)) {
                //Log.d(LOG_TAG, "-----CREATE TABLE-----");
                db.execSQL("CREATE TABLE " + NameTable + " (_id integer, "
                        + "Strenght integer, "
                        + "Consitution integer, "
                        + "Agility integer, "
                        + "Intelligence integer, "
                        + "Charisma integer, "
                        + "Health integer, "
                        + "Mana integer,"
                        + "PRIMARY KEY (_id))");
                //Log.d(LOG_TAG, "TABLE " + NameTable + " is created");

                db.execSQL("CREATE TABLE " + NameTable + "_inventory" + " (_id integer, "
                        + "id_item integer not null, "
                        + "attachment text not null, "
                        + "PRIMARY KEY (_id))");

                db.execSQL("CREATE TABLE " + NameTable + "_equipment" + " (_id integer, "
                        + "id_item integer not null, "
                        + "attachment text not null, "
                        + "PRIMARY KEY (_id))");

                /*Cursor a_item = db.rawQuery("SELECT id_item  FROM Armor_items WHERE _id=1", null);
                a_item.moveToFirst();
                String strId_item = a_item.getString(a_item.getColumnIndex("id_item"));
                db.execSQL("INSERT INTO " + NameTable + "_inventory" + " (id_item, attachment)  VALUES ("+ strId_item +", 'armor')");
                a_item.close();

                a_item = db.rawQuery("SELECT id_item  FROM Weapon_items WHERE _id=1", null);
                a_item.moveToFirst();
                strId_item = a_item.getString(a_item.getColumnIndex("id_item"));
                db.execSQL("INSERT INTO " + NameTable + "_inventory" + " (id_item, attachment)  VALUES ("+ strId_item +", 'weapon')");
                a_item.close();

                a_item = db.rawQuery("SELECT id_item  FROM Misc_items WHERE _id=1", null);
                a_item.moveToFirst();
                strId_item = a_item.getString(a_item.getColumnIndex("id_item"));
                db.execSQL("INSERT INTO " + NameTable + "_inventory" + " (id_item, attachment)  VALUES ("+ strId_item +", 'misc')");
                a_item.close();*/
                //Toast.makeText(contextApp, "Персонаж успешно создан", Toast.LENGTH_SHORT).show();

                if (isTableExists(db, "AllPerson")) {
                    //Log.d(LOG_TAG, "Inserting in AllPerson");
                    db.execSQL("INSERT INTO AllPerson (Name) VALUES ('"+NameTable+"')");
                } else {
                    //Log.d(LOG_TAG, "Created and Inserting in AllPerson");
                    db.execSQL("CREATE TABLE AllPerson (_id integer, Name text, PRIMARY KEY (_id))");
                    db.execSQL("INSERT INTO AllPerson (Name) VALUES ('"+NameTable+"')");
                }
                //Log.d(LOG_TAG, "-----TABLE CREATE-----");

            } else {
                //Toast.makeText(contextApp, "Персонаж уже существует", Toast.LENGTH_SHORT).show();
                //Log.d(LOG_TAG, "Table '"+NameTable+"' exists");
            }
        } //else Log.d(LOG_TAG, "Database not exists");
    }

    //// Обновить поля в БД путем копирования данных из приложения
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void SaveStatusHero(SQLiteDatabase db, String NameTable, Integer Strength, Integer Constitution, Integer Agility, Integer Intelligence, Integer Charisma, Integer Health, Integer Mana) {
        Cursor cursor = db.rawQuery("SELECT * FROM "+ NameTable, null);
        if (SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY) != null) {
            if(isTableExists(db, NameTable)) {
                if (cursor.getCount() > 0) {
                    db.execSQL("UPDATE "+ NameTable +" SET " + "Strenght" + "=" + Strength + ", "
                            + "Consitution" + "=" + Constitution + ", "
                            + "Agility" + "=" + Agility + ", "
                            + "Intelligence" +  "=" + Intelligence + ", "
                            + "Charisma" +   "=" + Charisma + ","
                            + "Health" +  "=" + Health + ", "
                            + "Mana" +  "=" + Mana);
                    //Log.d(LOG_TAG, "Update Entry");
                }else {
                    db.execSQL("INSERT INTO "+ NameTable + "(Strenght, Consitution, Agility, Intelligence, Charisma, Health, Mana) VALUES ("
                            + Strength +", "+ Constitution +", "+ Agility +", "+ Intelligence +", "+ Charisma+", "+ Health +", "+ Mana +")");
                    //Log.d(LOG_TAG, "Create New Entry");
                }
            }//Toast.makeText(contextApp, "Персонаж успешно сохранен", Toast.LENGTH_SHORT).show();
            //else Log.d(LOG_TAG, "Table '"+ NameTable +"'not exists");
        }//else Log.d(LOG_TAG, "Database not exists");
    }


    //// Загрузить параметры из БД в приложение
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ArrayList<String> LoadTable(SQLiteDatabase db, String NameTable) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + NameTable, null);
        ArrayList arrayList = new ArrayList<String> ();

        if (SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY) != null) {
            if (isTableExists(db, NameTable)) {
                if (cursor.getCount() > 0) {
                    //Log.d(LOG_TAG, "Table '"+ NameTable +"' Loading");
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        arrayList.add(cursor.getString(1));
                        //Log.d(LOG_TAG, "Strenght "+ cursor.getString(1));

                        arrayList.add(cursor.getString(2));
                        //Log.d(LOG_TAG, "Constitution "+ cursor.getString(2));

                        arrayList.add(cursor.getString(3));
                        //Log.d(LOG_TAG, "Agility "+ cursor.getString(3));

                        arrayList.add(cursor.getString(4));
                        //Log.d(LOG_TAG, "Intelligence "+ cursor.getString(4));

                        arrayList.add(cursor.getString(5));
                        //Log.d(LOG_TAG, "Charisma "+ cursor.getString(5));

                        arrayList.add(cursor.getString(6));

                        arrayList.add(cursor.getString(7));
                        cursor.moveToNext();
                    }cursor.close();
                    arrayList.add(NameTable);
                    //Log.d(LOG_TAG, "Table '"+ NameTable +"'is Loaded");

                }//else Log.d(LOG_TAG, "Table '"+ NameTable +"'is Empty");
            }//else Log.d(LOG_TAG, "Table '"+ NameTable +"'not exists");
        }//else Log.d(LOG_TAG, "Database not exists");

        return arrayList;
    }

    //// Проверка наличия таблицы
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean isTableExists(SQLiteDatabase db, String tableName) {
        String query = "SELECT DISTINCT tbl_name FROM sqlite_master WHERE tbl_name = '"+tableName+"'";
        try (Cursor cursor = db.rawQuery(query, null)) {
            if(cursor!=null)
                if(cursor.getCount() > 0)
                    return true;
            return false;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void LoadArmorItem (SQLiteDatabase db, String NameTable, ArrayList<ItemList> listItem) {

        Log.d(LOG_TAG,"NAME " + NameTable);
              // Log.d(LOG_TAG,"LOAD ITEM!!!!!");
        if (SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY) != null) {
            int i = 0;
            if (NameTable.equals("Armor_items")) {
                Cursor item = db.rawQuery("Select Name, Defense, Description From Armor_items", null);
                item.moveToFirst();

                /*Log.d(LOG_TAG,"COUNT!!!! " + item.getCount());
                Log.d(LOG_TAG,"--------------------------");*/

                while (!item.isAfterLast()) {
                  /*  Log.d(LOG_TAG,"Size1-- "+ listDataHeader.size());
                    Log.d(LOG_TAG,"Size2-- "+ listDataChild.size());*/

                  listItem.add(new ItemList(item.getString(item.getColumnIndex("Name")),
                          "Защита: "+item.getString(item.getColumnIndex("Defense")),
                          "Описание: "+item.getString(item.getColumnIndex("Description")), false, false));
                    /*List<String> group1 = new ArrayList<String>();
                    //listDataHeader.add(item.getString(item.getColumnIndex("Name")));
                    // Log.d(LOG_TAG,item.getString(item.getColumnIndex("Name")));

                    group1.add("Защита: "+item.getString(item.getColumnIndex("Defense")));
                    //Log.d(LOG_TAG,item.getString(item.getColumnIndex("Defense")));

                    group1.add("Описание: "+item.getString(item.getColumnIndex("Description")));
                    //  Log.d(LOG_TAG,item.getString(item.getColumnIndex("Description")));

                    listDataChild.put(listDataHeader.get(i), group1);
                    i++;*/
                    item.moveToNext();
                }
                //Log.d(LOG_TAG,"--------------------------");
                item.close();

            }else if(isTableExists(db, NameTable)) {
                    Cursor item = db.rawQuery("Select Name, Defense, Description From Armor_items WHERE id_item IN (SELECT id_item FROM "+ NameTable +"_inventory)", null);
                    item.moveToFirst();

                    /*Log.d(LOG_TAG,"COUNT!!!! " + item.getCount());
                    Log.d(LOG_TAG,"--------------------------");*/
                    while (!item.isAfterLast()) {

                        listItem.add(new ItemList(item.getString(item.getColumnIndex("Name")),
                                "Защита: "+item.getString(item.getColumnIndex("Defense")),
                                "Описание: "+item.getString(item.getColumnIndex("Description")), false, false));
                        item.moveToNext();
                    }
//                    Log.d(LOG_TAG,"--------------------------");
                    item.close();
            }
            //else Log.d(LOG_TAG, "Table '"+ NameTable +"'not exists");
        }//else Log.d(LOG_TAG, "Database not exists");
       /* Log.d(LOG_TAG,"Count Group "+ listDataHeader.size());
        Log.d(LOG_TAG,"Count Child "+ listDataChild.size());
        Log.d(LOG_TAG,"Header "+ listDataHeader.get(0));
        Log.d(LOG_TAG,"Child "+ listDataChild.values());*/
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void LoadWeaponItem (SQLiteDatabase db, String NameTable, ArrayList<ItemList> listItem) {

        Log.d(LOG_TAG,"NAME " + NameTable);
        // Log.d(LOG_TAG,"LOAD ITEM!!!!!");
        if (SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY) != null) {
            int i = 0;
            if (NameTable.equals("Weapon_items")) {
                Cursor item = db.rawQuery("Select Name, Damage, Description From Weapon_items", null);
                item.moveToFirst();

                /*Log.d(LOG_TAG,"COUNT!!!! " + item.getCount());
                Log.d(LOG_TAG,"--------------------------");*/

                while (!item.isAfterLast()) {
                    listItem.add(new ItemList(item.getString(item.getColumnIndex("Name")),
                            "Урон: "+item.getString(item.getColumnIndex("Damage")),
                            "Описание: "+item.getString(item.getColumnIndex("Description")), false, false));

                    item.moveToNext();
                }
                //Log.d(LOG_TAG,"--------------------------");
                item.close();

            }else if(isTableExists(db, NameTable)) {
                Cursor item = db.rawQuery("Select Name, Damage, Description From Weapon_items WHERE id_item IN (SELECT id_item FROM "+ NameTable +"_inventory)", null);
                item.moveToFirst();

                    /*Log.d(LOG_TAG,"COUNT!!!! " + item.getCount());
                    Log.d(LOG_TAG,"--------------------------");*/
                while (!item.isAfterLast()) {
                    listItem.add(new ItemList(item.getString(item.getColumnIndex("Name")),
                            "Урон: "+item.getString(item.getColumnIndex("Damage")),
                            "Описание: "+item.getString(item.getColumnIndex("Description")), false, false));

                    item.moveToNext();
                }
                Log.d(LOG_TAG,"--------------------------");
                item.close();
            }
            //else Log.d(LOG_TAG, "Table '"+ NameTable +"'not exists");
        }//else Log.d(LOG_TAG, "Database not exists");
       /* Log.d(LOG_TAG,"Count Group "+ listDataHeader.size());
        Log.d(LOG_TAG,"Count Child "+ listDataChild.size());
        Log.d(LOG_TAG,"Header "+ listDataHeader.get(0));
        Log.d(LOG_TAG,"Child "+ listDataChild.values());*/
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void LoadMiscItem (SQLiteDatabase db, String NameTable, ArrayList<ItemList> listItem) {

        Log.d(LOG_TAG,"NAME " + NameTable);
        // Log.d(LOG_TAG,"LOAD ITEM!!!!!");
        if (SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY) != null) {
            int i = 0;
            if (NameTable.equals("Misc_items")) {
                Cursor item = db.rawQuery("Select Name, Effect, Description From Misc_items", null);
                item.moveToFirst();

                /*Log.d(LOG_TAG,"COUNT!!!! " + item.getCount());
                Log.d(LOG_TAG,"--------------------------");*/

                while (!item.isAfterLast()) {
                    listItem.add(new ItemList(item.getString(item.getColumnIndex("Name")),
                            "Эффект: "+item.getString(item.getColumnIndex("Effect")),
                            "Описание: "+item.getString(item.getColumnIndex("Description")), false, false));

                    item.moveToNext();
                }
                //Log.d(LOG_TAG,"--------------------------");
                item.close();

            }else if(isTableExists(db, NameTable)) {
                Cursor item = db.rawQuery("Select Name, Effect, Description From Misc_items WHERE id_item IN (SELECT id_item FROM "+ NameTable +"_inventory)", null);
                item.moveToFirst();

                    /*Log.d(LOG_TAG,"COUNT!!!! " + item.getCount());
                    Log.d(LOG_TAG,"--------------------------");*/
                while (!item.isAfterLast()) {
                    listItem.add(new ItemList(item.getString(item.getColumnIndex("Name")),
                            "Эффект: "+item.getString(item.getColumnIndex("Effect")),
                            "Описание: "+item.getString(item.getColumnIndex("Description")), false, false));

                    item.moveToNext();
                }
                Log.d(LOG_TAG,"--------------------------");
                item.close();
            }
            //else Log.d(LOG_TAG, "Table '"+ NameTable +"'not exists");
        }//else Log.d(LOG_TAG, "Database not exists");
       /* Log.d(LOG_TAG,"Count Group "+ listDataHeader.size());
        Log.d(LOG_TAG,"Count Child "+ listDataChild.size());
        Log.d(LOG_TAG,"Header "+ listDataHeader.get(0));
        Log.d(LOG_TAG,"Child "+ listDataChild.values());*/
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void CreateItem (SQLiteDatabase db, String NameTable, String NameItem, String Value, String Description, String Attachment) {
        long time = System.currentTimeMillis();
        if (SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY) != null) {
            if(Attachment.equals("armor")) {
                if(isTableExists(db, "Armor_items")) {
                    db.execSQL("INSERT INTO Armor_items (Name, Defense, Description, id_item) VALUES ('"+ NameItem +"', '"+ Value +"', '"+ Description +"', "+ time +")");

                    Cursor c_item = db.rawQuery("SELECT id_item  FROM Armor_items WHERE id_item="+time+"", null);
                    c_item.moveToFirst();
                    String strId_item = c_item.getString(c_item.getColumnIndex("id_item"));
                    db.execSQL("INSERT INTO " + NameTable + "_inventory" + " (id_item, attachment)  VALUES ("+ strId_item +", 'armor')");
                    c_item.close();
                }
            } //else Log.d(LOG_TAG, "Table '"+ "Armor_items" +"'not exists");

            if(Attachment.equals("weapon")) {
                if(isTableExists(db, "Weapon_items")) {
                    db.execSQL("INSERT INTO Weapon_items (Name, Damage, Description, id_item) VALUES ('"+ NameItem +"', '"+ Value +"', '"+ Description +"', "+ time +")");

                    Cursor c_item = db.rawQuery("SELECT id_item  FROM Weapon_items WHERE id_item="+time+"", null);
                    c_item.moveToFirst();
                    String strId_item = c_item.getString(c_item.getColumnIndex("id_item"));
                    db.execSQL("INSERT INTO " + NameTable + "_inventory" + " (id_item, attachment)  VALUES ("+ strId_item +", 'weapon')");
                    c_item.close();
                }
            }
            if(Attachment.equals("misc")) {
                if(isTableExists(db, "Misc_items")) {
                    db.execSQL("INSERT INTO Misc_items (Name, Effect, Description, id_item) VALUES ('"+ NameItem +"', '"+ Value +"', '"+ Description +"', "+ time +")");

                    Cursor c_item = db.rawQuery("SELECT id_item  FROM Misc_items WHERE id_item="+time+"", null);
                    c_item.moveToFirst();
                    String strId_item = c_item.getString(c_item.getColumnIndex("id_item"));
                    db.execSQL("INSERT INTO " + NameTable + "_inventory" + " (id_item, attachment)  VALUES ("+ strId_item +", 'misc')");
                    c_item.close();
                }
            }
        }//else Log.d(LOG_TAG, "Database not exists");
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void AddItems (SQLiteDatabase db, String NameTable, ArrayList<ItemList> listItem, String Attachment) {
        if (SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY) != null) {
            if (Attachment.equals("armor")) {
                if(isTableExists(db, "Armor_items")) {
                    Cursor item = null;
                    for (int i=0; i< listItem.size(); i++) {
                        if (listItem.get(i).box) {
                            item = db.rawQuery("Select id_item From Armor_items WHERE Name='"+listItem.get(i).name+"'", null);
                            item.moveToFirst();
                            long id_item = item.getLong(item.getColumnIndex("id_item"));

                            db.execSQL("INSERT INTO "+NameTable+"_inventory"+ " (id_item, attachment) VALUES ("+ id_item +", 'armor')");
                        }
                    } item.close();
                   //LoadArmorItem(db, NameTable, listDataHeader, )
                }
            }
            if (Attachment.equals("weapon")) {
                if(isTableExists(db, "Weapon_items")) {
                    Cursor item = null;
                    for (int i=0; i< listItem.size(); i++) {
                        item = db.rawQuery("Select id_item From Weapon_items WHERE Name='"+listItem.get(i).name+"'", null);
                        item.moveToFirst();
                        long id_item = item.getLong(item.getColumnIndex("id_item"));

                        db.execSQL("INSERT INTO "+NameTable+"_inventory"+ " (id_item, attachment) VALUES ("+ id_item +", 'weapon')");
                    } item.close();
                    //LoadArmorItem(db, NameTable, listDataHeader, )
                }
            }
            if (Attachment.equals("misc")) {
                if(isTableExists(db, "Misc_items")) {
                    Cursor item = null;
                    for (int i=0; i< listItem.size(); i++) {
                        if (listItem.get(i).box) {
                            item = db.rawQuery("Select id_item From Misc_items WHERE Name='"+listItem.get(i).name+"'", null);
                            item.moveToFirst();
                            long id_item = item.getLong(item.getColumnIndex("id_item"));

                            db.execSQL("INSERT INTO "+NameTable+"_inventory"+ " (id_item, attachment) VALUES ("+ id_item +", 'misc')");
                        }
                    } item.close();
                    //LoadArmorItem(db, NameTable, listDataHeader, )
                }
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void RemoveItems (SQLiteDatabase db, String NameTable, ArrayList<ItemList> listItem, String Attachment) {
        if (SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY) != null) {
            if (Attachment.equals("armor")) {
                if(isTableExists(db, "Armor_items")) {
                    Cursor item = null;
                    for (int i=0; i< listItem.size(); i++) {
                        if (listItem.get(i).box) {
                            item = db.rawQuery("Select id_item From Armor_items WHERE Name='"+listItem.get(i).name+"'", null);
                            item.moveToFirst();
                            long id_item = item.getLong(item.getColumnIndex("id_item"));

                            db.execSQL("DELETE FROM "+NameTable+"_inventory"+ " WHERE id_item="+ id_item);
                        }
                    } item.close();
                    //LoadArmorItem(db, NameTable, listDataHeader, )
                }
            }
            if (Attachment.equals("weapon")) {
                if(isTableExists(db, "Weapon_items")) {
                    Cursor item = null;
                    for (int i=0; i< listItem.size(); i++) {
                        if (listItem.get(i).box) {
                            item = db.rawQuery("Select id_item From Weapon_items WHERE Name='"+listItem.get(i).name+"'", null);
                            item.moveToFirst();
                            long id_item = item.getLong(item.getColumnIndex("id_item"));

                            db.execSQL("DELETE FROM "+NameTable+"_inventory"+ " WHERE id_item="+ id_item);
                        }
                    } item.close();
                    //LoadArmorItem(db, NameTable, listDataHeader, )
                }
            }
            if (Attachment.equals("misc")) {
                if(isTableExists(db, "Misc_items")) {

                    Cursor item = null;
                    for (int i=0; i< listItem.size(); i++) {
                        if (listItem.get(i).box) {
                            item = db.rawQuery("Select id_item From Misc_items WHERE Name='"+listItem.get(i).name+"'", null);
                            item.moveToFirst();
                            long id_item = item.getLong(item.getColumnIndex("id_item"));

                            db.execSQL("DELETE FROM "+NameTable+"_inventory"+ " WHERE id_item="+ id_item);
                        }
                    } item.close();
                    //LoadArmorItem(db, NameTable, listDataHeader, )
                }
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void EquipItem (SQLiteDatabase db, String NameTable, ArrayList<ItemList> listItem, String Attachment) {
        if (SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY) != null) {
            if (Attachment.equals("armor")) {
                if(isTableExists(db, "Armor_items")) {

                    Cursor item = null;
                    for (int i=0; i< listItem.size(); i++) {
                        if (listItem.get(i).button) {
                            item = db.rawQuery("Select id_item From Armor_items WHERE Name='"+listItem.get(i).name+"'", null);
                            item.moveToFirst();

                            long id_item = item.getLong(item.getColumnIndex("id_item"));
                            db.execSQL("INSERT INTO "+NameTable+"_equipment"+ " (id_item, attachment) VALUES ("+ id_item +", 'armor')");
                        }
                    } item.close();
                    //LoadArmorItem(db, NameTable, listDataHeader, )
                }
            }
            if (Attachment.equals("weapon")) {
                if(isTableExists(db, "Weapon_items")) {

                    Cursor item = null;
                    for (int i=0; i< listItem.size(); i++) {
                        if (listItem.get(i).button) {
                            item = db.rawQuery("Select id_item From Weapon_items WHERE Name='"+listItem.get(i).name+"'", null);
                            item.moveToFirst();

                            long id_item = item.getLong(item.getColumnIndex("id_item"));
                            db.execSQL("INSERT INTO "+NameTable+"_equipment"+ " (id_item, attachment) VALUES ("+ id_item +", 'weapon')");
                        }
                    } item.close();
                    //LoadArmorItem(db, NameTable, listDataHeader, )
                }
            }
            if (Attachment.equals("misc")) {
                if(isTableExists(db, "Misc_items")) {

                    Cursor item = null;
                    for (int i=0; i< listItem.size(); i++) {
                        if (listItem.get(i).button) {
                            item = db.rawQuery("Select id_item From Misc_items WHERE Name='"+listItem.get(i).name+"'", null);
                            item.moveToFirst();

                            long id_item = item.getLong(item.getColumnIndex("id_item"));
                            db.execSQL("INSERT INTO "+NameTable+"_equipment"+ " (id_item, attachment) VALUES ("+ id_item +", 'misc')");
                        }
                    } item.close();
                    //LoadArmorItem(db, NameTable, listDataHeader, )
                }
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void LoadEquip (SQLiteDatabase db, String NameTable, String fromTable, ArrayList<ItemList> listItem) {
        if (SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY) != null) {
            if(isTableExists(db, NameTable)) {
                if (fromTable.equals("armor")) {
                    Cursor item = db.rawQuery("Select Name, Defense, Description From Armor_items WHERE id_item IN (SELECT id_item FROM " + NameTable + "_equipment WHERE attachment='armor')", null);
                    item.moveToFirst();
                    while (!item.isAfterLast()) {
                        listItem.add(new ItemList(item.getString(item.getColumnIndex("Name")),
                                "Защита: " + item.getString(item.getColumnIndex("Defense")),
                                "Описание: " + item.getString(item.getColumnIndex("Description")), false, false));
                        item.moveToNext();
                    }item.close();
                }
                if (fromTable.equals("weapon")) {
                    Cursor item = db.rawQuery("Select Name, Damage, Description From Weapon_items WHERE id_item IN (SELECT id_item FROM " + NameTable + "_equipment WHERE attachment='weapon')", null);
                    item.moveToFirst();
                    while (!item.isAfterLast()) {
                        listItem.add(new ItemList(item.getString(item.getColumnIndex("Name")),
                                "Урон: " + item.getString(item.getColumnIndex("Damage")),
                                "Описание: " + item.getString(item.getColumnIndex("Description")), false, false));
                        item.moveToNext();
                    }item.close();
                }
                if (fromTable.equals("misc")) {
                    Cursor item = db.rawQuery("Select Name, Effect, Description From Misc_items WHERE id_item IN (SELECT id_item FROM " + NameTable + "_equipment WHERE attachment='misc')", null);
                    item.moveToFirst();
                    while (!item.isAfterLast()) {
                        listItem.add(new ItemList(item.getString(item.getColumnIndex("Name")),
                                "Эффект: " + item.getString(item.getColumnIndex("Effect")),
                                "Описание: " + item.getString(item.getColumnIndex("Description")), false, false));
                        item.moveToNext();
                    }item.close();
                }
            }//else Log.d(LOG_TAG, "Table '"+ NameTable +"'not exists");
        }//else Log.d(LOG_TAG, "Database not exists");
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void RemoveEquip (SQLiteDatabase db, String NameTable, String fromTable, ArrayList<ItemList> listItem) {
        if (SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY) != null) {
            if(isTableExists(db, NameTable)) {
                if (fromTable.equals("armor")) {
                    Cursor item = null;
                    for (int i=0; i< listItem.size(); i++) {
                        Log.d(LOG_TAG, "BOX " + listItem.get(i).box);
                        if (listItem.get(i).box) {
                            Log.d(LOG_TAG, "ARMOR " + listItem.get(i).box);
                            item = db.rawQuery("Select id_item From Armor_items WHERE Name='"+listItem.get(i).name+"'", null);
                            item.moveToFirst();
                            long id_item = item.getLong(item.getColumnIndex("id_item"));

                            db.execSQL("DELETE FROM "+NameTable+"_equipment"+ " WHERE id_item="+ id_item);
                        }
                    } item.close();
                }
                if (fromTable.equals("weapon")) {
                    Cursor item = null;
                    for (int i=0; i< listItem.size(); i++) {
                        if (listItem.get(i).box) {
                            Log.d(LOG_TAG, "WEAPON " + listItem.get(i).box);
                            item = db.rawQuery("Select id_item From Weapon_items WHERE Name='"+listItem.get(i).name+"'", null);
                            item.moveToFirst();
                            long id_item = item.getLong(item.getColumnIndex("id_item"));

                            db.execSQL("DELETE FROM "+NameTable+"_equipment"+ " WHERE id_item="+ id_item);
                        }
                    } item.close();
                }
                if (fromTable.equals("misc")) {
                    Cursor item = null;
                    for (int i=0; i< listItem.size(); i++) {
                        if (listItem.get(i).box) {
                            Log.d(LOG_TAG, "MISC " + listItem.get(i).box);
                            item = db.rawQuery("Select id_item From Misc_items WHERE Name='"+listItem.get(i).name+"'", null);
                            item.moveToFirst();
                            long id_item = item.getLong(item.getColumnIndex("id_item"));

                            db.execSQL("DELETE FROM "+NameTable+"_equipment"+ " WHERE id_item="+ id_item);
                        }
                    } item.close();
                }
            }//else Log.d(LOG_TAG, "Table '"+ NameTable +"'not exists");
        }//else Log.d(LOG_TAG, "Database not exists");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void SaveNotice (SQLiteDatabase db, String NameTable, String Notice) {
        if (SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY) != null) {
            if(isTableExists(db, NameTable+"_notice")) {
                Log.d(LOG_TAG, "SAVE NOTICE");
                db.execSQL("DELETE FROM "+ NameTable+"_notice");
                db.execSQL("INSERT INTO "+ NameTable+"_notice" +"(Notice) VALUES('"+ Notice +"')");
            } else {
                Log.d(LOG_TAG, "CREATE AND SAVE NOTICE");
                db.execSQL("CREATE TABLE " + NameTable+"_notice" + " (_id integer, "
                        + "Notice text, " + "PRIMARY KEY (_id))");
                db.execSQL("INSERT INTO "+ NameTable+"_notice" +"(Notice) VALUES('"+ Notice +"')");
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String LoadNotice (SQLiteDatabase db, String NameTable) {
        String Notice = null;
        if (SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY) != null) {
            if(isTableExists(db, NameTable+"_notice")) {
                Log.d(LOG_TAG, "LOAD NOTICE");
               Cursor item = db.rawQuery("SELECT Notice FROM "+ NameTable+"_notice", null);
               item.moveToFirst();
               Notice = item.getString(item.getColumnIndex("Notice"));
               item.close();
            } else {}
        }
        return  Notice;
    }

}

