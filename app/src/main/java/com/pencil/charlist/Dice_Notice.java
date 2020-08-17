package com.pencil.charlist;

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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Dice_Notice extends Fragment {

    Button butDice;
    Spinner sp_typeDice;
    ListView historyRoll;
    MultiAutoCompleteTextView notice;
    int typedice = 6;

    final ArrayList listHistory = MainActivity.listHistory;

    int valueDice = 1;

    String name = MainActivity.nameHero;
    public DBHelper mDBHelper = Choose_Hero.mDBHelper;
    public static SQLiteDatabase database = Choose_Hero.database;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dice_notice, container, false);
        return view;
    };

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Кубы и заметки");

        butDice = getView().findViewById(R.id.butDice);
        sp_typeDice = getView().findViewById(R.id.typeDice);
        historyRoll = getView().findViewById(R.id.listHistory);
        notice = getView().findViewById(R.id.textNotice);

        notice.setText(mDBHelper.LoadNotice(database, name));
        butDice.setText(Integer.toString(valueDice));

        ArrayList list = new ArrayList();
        list.add("6");
        list.add("8");
        list.add("12");
        list.add("20");


        final ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(getView().getContext(), android.R.layout.simple_spinner_dropdown_item, list) {};
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_typeDice.setAdapter(spinAdapter);

        sp_typeDice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typedice  = Integer.parseInt(parent.getItemAtPosition(position).toString());
                //Log.d(LOG_TAG, "Selected "+selectItem);
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getView().getContext(), android.R.layout.simple_list_item_1, listHistory) {
        };
        historyRoll.setAdapter(listAdapter);

        butDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar rightNow = Calendar.getInstance();
                int hour = rightNow.get(Calendar.HOUR_OF_DAY);
                int minutes = rightNow.get(Calendar.MINUTE);
                Random rand = new Random();
                int n = rand.nextInt(typedice)+1;
                butDice.setText(Integer.toString(n));
                valueDice = n;
                if (minutes<10)
                    if (n<10)
                        listHistory.add("куб:  "+String.valueOf(n) +"         "+hour+":"+ "0"+ minutes);
                    else
                        listHistory.add("куб: "+String.valueOf(n) +"        "+hour+":"+ "0"+ minutes);
                else if (n<10)
                    listHistory.add("куб:  "+String.valueOf(n) +"         "+hour+":"+ minutes);
                else
                    listHistory.add("куб: "+String.valueOf(n) +"        "+hour+":"+ minutes);

                listAdapter.notifyDataSetChanged();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onPause() {
        mDBHelper.SaveNotice(database, name, notice.getText().toString());
        super.onPause();
    }


}
