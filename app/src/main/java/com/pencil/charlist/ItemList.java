package com.pencil.charlist;

import android.widget.Button;

public class ItemList {
    String name, value, description;
    Boolean box;
    Boolean button;

    ItemList(String _name, String _value, String _description, boolean _box, boolean _button) {
        name = _name;
        value = _value;
        description = _description;
        box = _box;
        button = _button;
    }

}
