package com.pencil.charlist;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Misc_item extends Fragment {
   // ExpandableListAdapter listAdapter;
   // ExpandableListView expListView;

    //List<String> listDataHeader;
   // HashMap<String, List<String>> listDataChild;
    ArrayList<ItemList> arrlistItem = new ArrayList<>();
    MultipleListAdapter listAdapter;

    ListView listItem;
    //ArrayList list;
    public static Button butAdd;
    public static Button butRemove;
    String name = MainActivity.nameHero;

    public DBHelper mDBHelper = Choose_Hero.mDBHelper;
    SQLiteDatabase database = Choose_Hero.database;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.frag_inventory_item, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        listItem = getView().findViewById(R.id.listOfItems);
       // expListView = (ExpandableListView) getView().findViewById(R.id.listOfItems);

        butAdd = getView().findViewById(R.id.butAdd);
        butRemove = getView().findViewById(R.id.butRemove);
        //listItem = getView().findViewById(R.id.listOfItems);
        // get the listview
        //expListView = (ExpandableListView) getView().findViewById(R.id.listOfItems);

        // preparing list data
        //prepareListData();
       // listDataHeader = new ArrayList<String>();
        //listDataChild = new HashMap<String, List<String>>();

        //mDBHelper.LoadMiscItem(database, name, listDataHeader, listDataChild);
       // listAdapter = new ExpandableListAdapter(getActivity().getApplicationContext(), listDataHeader, listDataChild, true,butAdd, butRemove);
        //expListView.setAdapter(listAdapter);


        arrlistItem.clear();
        mDBHelper.LoadMiscItem(database, name, arrlistItem);

        listAdapter = new MultipleListAdapter(getActivity().getApplicationContext(), arrlistItem, "misc", true);
        //Log.d(LOG_TAG, "LIST OF ITEM " + listAdapter.getItemToPos(0));
        listItem.setAdapter(listAdapter);

        butAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Create_item.class);
                intent.putExtra("Attachment", "misc");
                startActivityForResult(intent, 3);
            }
        });

        butRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDBHelper.RemoveItems(database, name, listAdapter.getBox(), "misc");
                mDBHelper.RemoveEquip(database, name, "misc", arrlistItem);
                UpdateList();
                butAdd.setVisibility(View.VISIBLE);
                butRemove.setVisibility(View.INVISIBLE);
            }
        });
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if (data == null) {return;}
        if (requestCode == 3) {
            UpdateList ();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void UpdateList (){
        arrlistItem.clear();
        mDBHelper.LoadMiscItem(database, name, arrlistItem);
        listAdapter.notifyDataSetChanged();
    }
}
