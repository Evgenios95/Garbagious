package com.example.myapplication;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class FragmentUI extends Fragment {

    //GUI variables
    private Button listItems, addNewItem, deleteItem;
    private TextView whatItem, whatPlace;

    //Items database
    private ItemsDB itemsDB;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsDB = ItemsDB.get(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_ui, container, false);


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            listItems = v.findViewById(R.id.list);


            listItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ListActivity.class);
                    startActivity(intent);
                }
            });
        }

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

        deleteItem= v.findViewById(R.id.deleteItem);
        deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!whatItem.getText().toString().trim().isEmpty()) {
                    itemsDB.removeItem(whatItem.getText().toString());
                    Toast.makeText(getActivity(), "Removed "+whatItem.getText(), Toast.LENGTH_SHORT).show();
                }else Toast.makeText(getActivity(), "Removed", Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }


}
