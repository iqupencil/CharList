package com.pencil.charlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTabHost;

public class Equipment_Hero extends Fragment {

    private FragmentTabHost mTabHost;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments

        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.layout.content_main);

        Bundle arg1 = new Bundle();
        arg1.putInt("Eqv for Armor", 1);
        mTabHost.addTab(mTabHost.newTabSpec("Tab1").setIndicator("Защита"),
                Equip_Armor.class, arg1);

        Bundle arg2 = new Bundle();
        arg2.putInt("Eqv for Weapon", 2);
        mTabHost.addTab(mTabHost.newTabSpec("Tab2").setIndicator("Оружие"),
                Equip_Weapon.class, arg2);

        Bundle arg3 = new Bundle();
        arg2.putInt("Eqv for Misc", 3);
        mTabHost.addTab(mTabHost.newTabSpec("Tab3").setIndicator("Разное"),
                Equip_Misc.class, arg3);

        return mTabHost;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTabHost = null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Экипировка");
    }
}
