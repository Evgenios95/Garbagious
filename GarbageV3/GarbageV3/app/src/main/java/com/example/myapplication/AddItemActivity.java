package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {

    private static ItemsDB itemsDB;
    private TextView whatItem, whatPlace;
    private Button addNewItem;


    /**
     * @onCreate we call onCreate when an activity is starting. And it will execute many times.
     * @setContentView inflate the activity's UI (φανερώνει).
     * @findViewById programmatically interact with a specific widget in the UI. garb.xml in our case
     *
     * @param savedInstanceState 	Bundle: If the activity is being re-initialized after
     * previously being shut down then this Bundle contains the data it most recently supplied in
     * onSaveInstanceState(Bundle). Note: Otherwise it is null
     *
     * @R R stands for resources. It is a class containing definitions for all resources of a
     * particular application package. The R classes we deal with is our package, which in
     * our case is dk.itu.shopping and android.R
     */
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        itemsDB = ItemsDB.get(AddItemActivity.this);

        whatItem = findViewById(R.id.whatItem);
        whatPlace = findViewById(R.id.whatPlace);

        addNewItem = findViewById(R.id.addnewItem_button);

        /**
         * new View..... we create a new object which is of type View (class) with an
         * onclicklistener method. It is an anonuymous inner class. We write it inline and we
         * create the object. When we press the addItem button, the white space in our UI will be
         * filled with the information that are in the code body. The whole thing is a lambda
         * function which will be executed. Careful: what's happenning here is that the code is
         * being prepared for execution but not executed. The execution will happen whenever the
         * user wants to. (button) So the user expects the stream to be executed. Each of the
         * listeners is executed independently. Example of concurrency.
         */
        addNewItem.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view){
                String whatS = whatItem.getText().toString().trim();
                String whatP = whatPlace.getText().toString().trim();
                if ((whatS.length() > 0) && (whatP.length() > 0)) {
                    itemsDB.addItem(whatS, whatP);
                    whatItem.setText("");
                    whatPlace.setText("");
                } else {
                    /**
                     * A toast is a view which contains a quick little message for the user. It
                     * appears like a floating view over the application. It will be unobtrusive and
                     * show the user the info you want them to see.
                     *
                     * @Toast.makeText (context, text, duration). Context is about what context to
                     * use. usually the activity object. Text is the text to show. and length_long
                     * means that it will be shown for a long time.
                     */
                    Toast.makeText(AddItemActivity.this, "Please type something in these fields",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
