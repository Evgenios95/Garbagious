package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Model: Database of items
    private ItemsDB itemsDB;
    private Button listItems, addNew;
    private TextView items;
    private EditText searchItems;


    /**
     *
     * @return returns the garbage
     */
    public String findWaste() {
        String items = searchItems.getText().toString().toLowerCase();
        return itemsDB.findWaste(items);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.garb);

        items= findViewById(R.id.items);
        items.setText("Input garbage below: ");
        listItems= findViewById(R.id.where_button);

        itemsDB = ItemsDB.get(MainActivity.this);
        searchItems= findViewById(R.id.searchItems);

        listItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("EditText", searchItems.getText().toString());
                items.setText(findWaste());
            }
        });

        addNew = findViewById(R.id.addNew_button);

        /**
         * @setOnClickListener listItems is a button in this case. An instance
         * of View.OnclickListener will be created and wired the listener to the
         * button. After the user presses the button then something will happen
         * which is onClick(View).
         *
         * startActivity(Intent) is used to start a new activity which will be placed on top of
         * the activity stack. It takes an argument (Intent) and describes the activity which is
         * going to be executed.
         *
         * @Intent(context,class) Creating an intent for a specific component.
         * The context is a reference to link the resources to the program, you can simply write
         * this or ShoppingActivity. this. And then the class that is to be run is provided. In
         * our case Listactivity. Context is the starting point and class is the class activity
         * we need to start. Writing intent will make the first page disappear and listactivity
         * will take over the screen. Concurrency as well.
         */
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
                startActivity(intent);
            }
        });
    }
}