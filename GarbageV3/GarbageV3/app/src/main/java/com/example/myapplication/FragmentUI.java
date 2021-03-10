package com.example.myapplication;


import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentUI extends Fragment {

    //GUI variables
    private Button whereItems, listItems, addNewItem;
    private TextView items, whatItem, whatPlace;
    private EditText searchItems;

    //Items database
    private ItemsDB itemsDB;



    public String findWaste() {
        String items = searchItems.getText().toString().toLowerCase();
        return itemsDB.findWaste(items);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsDB = ItemsDB.get(getActivity());
    }


    //Try to remove the where button.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_ui, container, false);

        items = v.findViewById(R.id.items);
        items.setText("Input garbage below: ");
        whereItems = v.findViewById(R.id.where_button);

        searchItems = v.findViewById(R.id.searchItems);

        whereItems.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Log.v("EditText", searchItems.getText().toString());
                items.setText(findWaste());
            }
        });


        listItems = v.findViewById(R.id.list);
        listItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListActivity.class);
                startActivity(intent);
            }
        });


        whatItem = v.findViewById(R.id.whatItem);
        whatPlace = v.findViewById(R.id.whatPlace);
        addNewItem = v.findViewById(R.id.addnewItem_button);

        addNewItem.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                String whatIt = whatItem.getText().toString().trim();
                String whatPl = whatPlace.getText().toString().trim();
                if ((whatIt.length() > 0) && (whatPl.length() > 0)) {
                    itemsDB.addItem(whatIt, whatPl);
                    whatItem.setText("");
                    whatPlace.setText("");
                } else {
                    Toast.makeText(getActivity(),
                            "Please, type something in these fields.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        return v;
    }


}
