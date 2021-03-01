package com.example.myapplication;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ItemsDB {

    private static ItemsDB sItemsDB;
    private final HashMap<String, String> itemsMap = new HashMap<>();
    private static Context sContext;


    public static ItemsDB get(Context context) {
        if (sItemsDB == null) {
            sContext = context;
            sItemsDB = new ItemsDB();
        }
        return sItemsDB;
    }

    private ItemsDB() {
        fillItemsDB();
    }

    public String listItems() {
        String r = "";
        for (HashMap.Entry<String, String> item : itemsMap.entrySet())
            r = r + item.getKey().toLowerCase() + " is in:" + item.getValue().toLowerCase() +".\n";
            return r;
    }

    public String findWaste(String garbageItem){
        String r = "";
        for (Map.Entry <String, String> item : itemsMap.entrySet()) {
            if (item.getKey().equalsIgnoreCase(garbageItem)) {
                r = r + "Throw " + item.getKey()+ " in" + item.getValue() +"!";
                return r;
            }
        }
        return "Sorry, not found!";
    }

    private void fillItemsDB() {
        try {
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(sContext.getAssets().open(
                            "garbage.txt")));
            String line = reader.readLine();
            while (line != null) {
                String[] garbageCropper = line.split(",");
                itemsMap.put(garbageCropper[0], garbageCropper[1]);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}






//    public void addItem(String what, String where) {
//        itemsMap.put(what, where);
//    }



///**
// //     * In order to have a singleton we need a pattern. A static member, which is the instance of the
// //     * singleton class together with a static public method which will provide access to the
// //     * singleton object and it returns the instance to the client calling class. With the singleton
// //     * we ensure that only one instance of a class is created and allow multiple instances in the
// //     * future without affecting it.
// //     *
// //     * @param context
// //     * @return the list
// //     */