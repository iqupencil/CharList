package com.pencil.charlist;

import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Status_Hero extends Fragment {
    final String LOG_TAG = "myLogs";
    String name = MainActivity.nameHero;
    TextView TextName;

    EditText TextStrenght;
    EditText TextConstitution;
    EditText TextAgility;
    EditText TextIntelligence;
    EditText TextCharisma;
    EditText TextHealth;
    EditText TextMana;
    ArrayList<Integer> statsHero;
    int someStateValue;

    public String myPath = Choose_Hero.myPath;
    public DBHelper mDBHelper = Choose_Hero.mDBHelper;
    public static SQLiteDatabase database = Choose_Hero.database;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status_hero, container, false);
        if (savedInstanceState != null) {
            someStateValue = savedInstanceState.getInt("State");
            // Do something with value if needed
        }
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Герой");


        TextName = (TextView) getView().findViewById(R.id.viewNameHero);
        statsHero =  new ArrayList();

        TextStrenght = getView().findViewById(R.id.editStrenght);
        TextConstitution = getView().findViewById(R.id.editConstitution);
        TextAgility = getView().findViewById(R.id.editAgility);
        TextIntelligence = getView().findViewById(R.id.editIntellegence);
        TextCharisma = getView().findViewById(R.id.editCharisma);
        TextHealth = getView().findViewById(R.id.editHealth);
        TextMana = getView().findViewById(R.id.editMana);

        TextName.setText(name);
        Log.d(LOG_TAG, "THIS NAME " + name);

        PastLoad(mDBHelper.LoadTable(database, name));

    }

    public void PastLoad (ArrayList<String> arrStr) {
        TextStrenght.setText(arrStr.get(0));
        TextConstitution.setText(arrStr.get(1));
        TextAgility.setText(arrStr.get(2));
        TextIntelligence.setText(arrStr.get(3));
        TextCharisma.setText(arrStr.get(4));
        TextHealth.setText(arrStr.get(5));
        TextMana.setText(arrStr.get(6));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onPause() {
        statsHero.add(Integer.valueOf(String.valueOf(TextStrenght.getText())));
        statsHero.add(Integer.valueOf(String.valueOf(TextConstitution.getText())));
        statsHero.add(Integer.valueOf(String.valueOf(TextAgility.getText())));
        statsHero.add(Integer.valueOf(String.valueOf(TextIntelligence.getText())));
        statsHero.add(Integer.valueOf(String.valueOf(TextCharisma.getText())));
        statsHero.add(Integer.valueOf(String.valueOf(TextHealth.getText())));
        statsHero.add(Integer.valueOf(String.valueOf(TextMana.getText())));
        mDBHelper.SaveStatusHero(database, name, statsHero.get(0), statsHero.get(1), statsHero.get(2), statsHero.get(3), statsHero.get(4), statsHero.get(5), statsHero.get(6));
        super.onPause();
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("State", someStateValue);
        super.onSaveInstanceState(outState);
    }

}
