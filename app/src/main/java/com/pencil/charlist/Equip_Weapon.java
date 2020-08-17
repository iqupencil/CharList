package com.pencil.charlist;

import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Equip_Weapon extends Fragment {
    ArrayList<ItemList> arrlistItem = new ArrayList<>();
    MultipleListAdapter listAdapter;

    public static Button butRemove;

    String name = MainActivity.nameHero;

    public DBHelper mDBHelper = Choose_Hero.mDBHelper;
    SQLiteDatabase database = Choose_Hero.database;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.equip_weapon, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        butRemove = getView().findViewById(R.id.butRemove);
        ListView listItem = getView().findViewById(R.id.listOfItems);

        arrlistItem.clear();
        mDBHelper.LoadEquip(database, name, "weapon", arrlistItem);

        listAdapter = new MultipleListAdapter(getActivity().getApplicationContext(), arrlistItem, "weapon", false);
        listItem.setAdapter(listAdapter);

        butRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDBHelper.RemoveEquip(database, name, "weapon", arrlistItem);
                UpdateList();
                butRemove.setVisibility(View.INVISIBLE);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void UpdateList (){
        arrlistItem.clear();
        mDBHelper.LoadEquip(database, name, "weapon", arrlistItem);
        listAdapter.notifyDataSetChanged();
    }
}
