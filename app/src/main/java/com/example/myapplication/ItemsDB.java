package com.example.myapplication;

import android.content.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

public class ItemsDB extends Observable {
    private final static String GarbageURL = "https://garbageServer.jstaunstrup.repl.co";
    private static ItemsDB sItemsDB;
    private final HashMap<String, String> itemsMap= new HashMap<String, String>();


    private ItemsDB(Context context) {
        networkdDB(GarbageURL, "");
    }

    public static ItemsDB get(Context context) {
        if (sItemsDB == null)  sItemsDB = new ItemsDB(context);
        return sItemsDB;
    }

    public int size() { return itemsMap.size(); }


    public synchronized void addItem(String what, String where){
        itemsMap.put(what, where);
        this.setChanged(); notifyObservers();
        networkdDB(GarbageURL, "?op=insert&what="+  what + "&whereC=" + where) ;
    }

    public synchronized void initItem(String what, String where){
        itemsMap.put(what, where);
    }

    public synchronized ArrayList<Item> getAll() {
        ArrayList<Item> result= new ArrayList<>();
        for (HashMap.Entry <String, String> item: itemsMap.entrySet()) {
            result.add(new Item(item.getKey(), item.getValue()));
        }
        return result;
    }

    public synchronized void removeItem(String what){
        if (itemsMap.get(what) != null) {
            itemsMap.remove(what);
            this.setChanged(); notifyObservers();
            networkdDB(GarbageURL, "?op=remove&what="+what) ;
        }
    }



    private synchronized void networkdDB(String url, String command) {
        Runnable r= new httpThread(url+command);
        new Thread(r).start();
    }
}

