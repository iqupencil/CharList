package com.pencil.charlist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class MultipleListAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<ItemList> objects;

    String attachment;
    Boolean withEquip;

    Button A_butAdd = Armor_item.butAdd;
    Button A_butRemove = Armor_item.butRemove;
    Button W_butAdd = Weapon_item.butAdd;
    Button W_butRemove = Weapon_item.butRemove;
    Button M_butAdd = Misc_item.butAdd;
    Button M_butRemove = Misc_item.butRemove;

    Button A_equbutRemove = Equip_Armor.butRemove;
    Button W_equbutRemove = Equip_Weapon.butRemove;
    Button M_equbutRemove = Equip_Misc.butRemove;

    Button buttonAdd;
    Button buttonRemove;


    View layoutView;
    ConstraintLayout linHigh;

    ItemList positonItem;

    String name = MainActivity.nameHero;
    public DBHelper mDBHelper = Choose_Hero.mDBHelper;
    SQLiteDatabase database = Choose_Hero.database;


    MultipleListAdapter(Context context, ArrayList<ItemList> item, String _attachment, Boolean _withEquipButton) {
        ctx = context;
        objects = item;
        attachment = _attachment;
        withEquip = _withEquipButton;
        //lInflater = LayoutInflater.from(context);
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;

        if (view == null) {
            if (!withEquip)
                view = lInflater.inflate(R.layout.item_list_inv, parent, false);
            if (withEquip)
                view = lInflater.inflate(R.layout.item_list_equ, parent, false);
        }
        layoutView = view;
        ItemList p = getItemToPos(position);

        Log.d("ADAPTER", "ADAPTER VIEW");
        HighLights(p);

        buttonAdd = view.findViewById(R.id.butAdd);
        buttonRemove = view.findViewById(R.id.butRemove);
//        if (withEquip) {
//            Button but = view.findViewById(R.id.but_equip);;
//            ArrayList<ItemList> equip = new ArrayList<>();
//            mDBHelper.LoadEquip(database, name, "armor", equip);
//            for(int i = 0; i < objects.size(); i++) {
//                for(int j = 0; j <equip.size(); j++) {
//                    if (p.name.equals(equip.get(j).name)) {
//                            linHigh = view.findViewById(R.id.item_highlight);
//                            but.setVisibility(View.INVISIBLE);
//                            linHigh.setBackgroundResource(R.drawable.selected_item);
//                        // Log.d(LOG_TAG, "TRUE " + arrlistItem.get(i).name +"  "+ i+", "+j);pos.add(i);
//                    }
//                }
//            }
//        }



        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        ((TextView) view.findViewById(R.id.itemName)).setText(p.name);
        ((TextView) view.findViewById(R.id.itemValue)).setText(p.value);
        ((TextView) view.findViewById(R.id.itemDescr)).setText(p.description);

        if (withEquip) {
            Button equipBut = view.findViewById(R.id.but_equip);
            equipBut.setOnClickListener(EquipItem);
            equipBut.setTag(position);
            //equipBut.setVisibility(View.VISIBLE);
        }

        CheckBox cbBuy = (CheckBox) view.findViewById(R.id.checkBox);
        // присваиваем чекбоксу обработчик

        cbBuy.setOnCheckedChangeListener(myCheckChangeList);
        // пишем позицию
        cbBuy.setTag(position);
        // заполняем данными из товаров: в корзине или нет
        cbBuy.setChecked(p.box);
        return view;
    }

    // товар по позиции
    ItemList getItemToPos(int position) {
        return ((ItemList) getItem(position));
    }

    // содержимое корзины
    ArrayList<ItemList> getBox() {
        ArrayList<ItemList> box = new ArrayList<>();
        for (ItemList p : objects) {
            // если в корзине
            if (p.box)
                box.add(p);
        }
        return box;
    }

    public void ChangeVisibilityButton (Button Add, Button Remove , Boolean state) {
        if (Remove!=null) {
            if (state == false) {
                if (Add!=null) Add.setVisibility(View.INVISIBLE);
                Remove.setVisibility(View.VISIBLE);
            } else {
                if (Add!=null) Add.setVisibility(View.VISIBLE);
                Remove.setVisibility(View.INVISIBLE);
            }
        }
    }

    // обработчик для чекбоксов
    CompoundButton.OnCheckedChangeListener myCheckChangeList = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // меняем данные товара (в корзине или нет)
            getItemToPos((Integer) buttonView.getTag()).box = isChecked;

            //// Огромный "костыль" по отображению кнопок :3
            for (ItemList p : objects) {
                if (p.box) {
                    if (withEquip) {
                        //buttonAdd.setVisibility(View.INVISIBLE);
                        //buttonRemove.setVisibility(View.VISIBLE);
                        ChangeVisibilityButton(A_butAdd, A_butRemove, false);
                        ChangeVisibilityButton(W_butAdd, W_butRemove, false);
                        ChangeVisibilityButton(M_butAdd, M_butRemove, false);
                        ChangeVisibilityButton(M_butAdd, M_butRemove, false);

                    }else {
                        //buttonRemove.setVisibility(View.VISIBLE);
                        ChangeVisibilityButton(null, A_equbutRemove, false);
                        ChangeVisibilityButton(null, W_equbutRemove, false);
                        ChangeVisibilityButton(null, M_equbutRemove, false);
                        ChangeVisibilityButton(null, M_equbutRemove, false);
                    }
                    break;
                } else {
                    if (withEquip) {
                        //buttonAdd.setVisibility(View.VISIBLE);
                        //buttonRemove.setVisibility(View.INVISIBLE);
                        ChangeVisibilityButton(A_butAdd, A_butRemove, true);
                        ChangeVisibilityButton(W_butAdd, W_butRemove, true);
                        ChangeVisibilityButton(M_butAdd, M_butRemove, true);
                        ChangeVisibilityButton(M_butAdd, M_butRemove, true);
                    } else {
                        //buttonRemove.setVisibility(View.INVISIBLE);
                        ChangeVisibilityButton(null, A_equbutRemove, true);
                        ChangeVisibilityButton(null, W_equbutRemove, true);
                        ChangeVisibilityButton(null, M_equbutRemove, true);
                        ChangeVisibilityButton(null, M_equbutRemove, true);
                    }
                }
            }
        }
    };


    Button.OnClickListener EquipItem = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onClick(View view) {
            //Log.d("But", "TAG " + but.getTag());
            getItemToPos((Integer) view.getTag()).button = true;

            ArrayList<ItemList> item = new ArrayList<>();
            for (ItemList p : objects) {
                if (p.button) {
                   // Log.d("BUTTON", "PRESED BUTTON" + p.name);
                    item.add(new ItemList(p.name, p.value, p.description, p.box, p.button));
                }
            }
            mDBHelper.EquipItem(database, name, item, attachment);

           // Log.d("myLog","TAG #"+ view.getTag());
            notifyDataSetChanged();
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void HighLights(ItemList p) {
        if (withEquip) {
            Button but = layoutView.findViewById(R.id.but_equip);
            ArrayList<ItemList> equip = new ArrayList<>();
            Log.d("ADAPTER","NAME before "+ p.name);
            if (attachment.equals("armor"))
                mDBHelper.LoadEquip(database, name, "armor", equip);
            else if (attachment.equals("weapon"))
                mDBHelper.LoadEquip(database, name, "weapon", equip);
            else if (attachment.equals("misc"))
                mDBHelper.LoadEquip(database, name, "misc", equip);

            but.setVisibility(View.VISIBLE);
            Log.d("ADAPTER","NAME False: "+ p.name);
            for(int j = 0; j <equip.size(); j++)
                if (p.name.equals(equip.get(j).name)) {
                    Log.d("ADAPTER","NAME True: "+ p.name);
                    //linHigh = layoutView.findViewById(R.id.item_highlight);
                    but.setVisibility(View.INVISIBLE);
                    //but.setText("Снять");
                    //linHigh.setBackgroundResource(R.drawable.selected_item);
                }

        }
    }

}
