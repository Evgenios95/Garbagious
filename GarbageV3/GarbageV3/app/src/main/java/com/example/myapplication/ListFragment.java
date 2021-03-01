package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ListFragment extends Fragment {

    private static ItemsDB itemsDB;
    private TextView listThings;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsDB = ItemsDB.get(getActivity());
    }

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,
                              Bundle savedInstanceState) {

        final ScrollView v =
                (ScrollView) inflater.inflate(R.layout.fragment_list, container, false);
        listThings = v.findViewById(R.id.listItems);
        listThings.setText("Garbage list:\n\n" + itemsDB.listItems());
        return v;
    }
}

