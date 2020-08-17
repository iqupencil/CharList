package com.pencil.charlist;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Armor_item extends Fragment {

    ArrayList<ItemList> arrlistItem = new ArrayList<>();
    MultipleListAdapter listAdapter;
    ListView listItem;
    final String LOG_TAG = "myLogs";

    //List<String> listDataHeader;
   // HashMap<String, List<String>> listDataChild;

    //ListView listItem;
    //ArrayList list;

    public static Button butAdd;
    public static Button butRemove;

    String name = MainActivity.nameHero;
    //public static ArrayAdapter<String> adapter;

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
        butAdd = getView().findViewById(R.id.butAdd);
        butRemove = getView().findViewById(R.id.butRemove);
        listItem = getView().findViewById(R.id.listOfItems);
        // get the listview

        arrlistItem.clear();
        mDBHelper.LoadArmorItem(database, name, arrlistItem);

        listAdapter = new MultipleListAdapter(getActivity().getApplicationContext(), arrlistItem, "armor", true);
        //Log.d(LOG_TAG, "LIST OF ITEM " + listAdapter.getItemToPos(0));
        listItem.setAdapter(listAdapter);

                /*butAdd.setVisibility(View.INVISIBLE);
                butRemove.setVisibility(View.VISIBLE);*/

        // preparing list data
        //prepareListData();
        //listDataHeader = new ArrayList<String>();
        //listDataChild = new HashMap<String, List<String>>();


       // listAdapter = new ExpandableListAdapter(getActivity().getApplicationContext(), listDataHeader, listDataChild, true, butAdd, butRemove);

        // setting list adapter
        //expListView.setAdapter(listAdapter);
//        list = new ArrayList();
//        for(int i=1; i<40; i++) {
//            list.add("Armor "+i);
//        }

      /*  adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.list_item, mDBHelper.LoadArmorItem(database, name)) {
        };
        adapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
        listItem.setAdapter(adapter);*/

        butAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Create_item.class);
                intent.putExtra("Attachment", "armor");
                startActivityForResult(intent, 1);
            }
        });

        butRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (listAdapter.ListOfSelectItem().size() !=0) {
                mDBHelper.RemoveItems(database, name, listAdapter.getBox(), "armor");
                mDBHelper.RemoveEquip(database, name, "armor", arrlistItem);
                UpdateList();
                butAdd.setVisibility(View.VISIBLE);
                butRemove.setVisibility(View.INVISIBLE);

            }
        });

        /*expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                CheckBox checkbox = (CheckBox) view.findViewById(R.id.checkbox);
                TextView text = (TextView) view.findViewById(R.id.lblListHeader);
                //expListView.setSelectedGroup(i);
                if(!checkbox.isChecked()) {
                    checkbox.setChecked(true);
                    view.setBackgroundResource(R.color.colorPrimaryDark);

                    Log.d(LOG_TAG, String.valueOf(text.getText()));
                } else {
                    checkbox.setChecked(false);
                    view.setBackgroundResource(R.color.design_default_color_background);

                    Log.d(LOG_TAG, String.valueOf(text.getText()));
                }
                return false;
            }
        });*/
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       // if (data == null) {return;}
        if (requestCode == 1) {
            UpdateList();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void UpdateList (){
        arrlistItem.clear();
        mDBHelper.LoadArmorItem(database, name, arrlistItem);
        listAdapter.notifyDataSetChanged();
        Log.d("UPDATE", "UPDATE LIST");
    }


    /*private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Туника");
        listDataHeader.add("Кираса");
        listDataHeader.add("Что-то еще");

        // Adding child data
        List<String> group1 = new ArrayList<String>();
        group1.add("Защита 0");
        group1.add("Неплохо выглядит");


        List<String> group2 = new ArrayList<String>();
        group2.add("Защита 3");
        group2.add("Блестит");


        List<String> group3 = new ArrayList<String>();
        group3.add("Защита *");
        group3.add("Остальное");

        listDataChild.put(listDataHeader.get(0), group1); // Header, Child data
        listDataChild.put(listDataHeader.get(1), group2);
        listDataChild.put(listDataHeader.get(2), group3);
    }*/

}
