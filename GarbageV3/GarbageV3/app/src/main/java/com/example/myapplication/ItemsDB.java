package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.database.ItemBaseHelper;
import com.example.myapplication.database.ItemCursorWrapper;
import com.example.myapplication.database.ItemsDbSchema;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class ItemsDB extends Observable {
    private static ItemsDB sItemsDB;
    private static SQLiteDatabase mDatabase;

    private ItemsDB(Context context) {
        if (getAll().size() == 0)  fillItemsDB();
    }

    public static ItemsDB get(Context context) {
        if (sItemsDB == null)  {
            mDatabase= new ItemBaseHelper(context.getApplicationContext()).getWritableDatabase();
            sItemsDB = new ItemsDB(context);
        }
        return sItemsDB;
    }

    public void addItem(String what, String where){
        Item newItem= new Item(what, where);
        ContentValues values= getContentValues(newItem);
        mDatabase.insert(ItemsDbSchema.ItemTable.NAME, null, values);
        this.setChanged(); notifyObservers();
    }

    public void removeItem(String what){
        Item newItem= new Item(what, "");
        String selection= ItemsDbSchema.ItemTable.Cols.WHAT + " LIKE ?";
        int changed= mDatabase.delete(ItemsDbSchema.ItemTable.NAME, selection, new String[]{newItem.getWhat()});
        if (changed > 0) { this.setChanged(); notifyObservers();  }
    }

    public void fillItemsDB() {
        addItem("plastic bottle", "plastic");
        addItem("bucket", "plastic");
        addItem("straw", "plastic");
        addItem("plastic cap", "plastic");
        addItem("can", "metal");
        addItem("cooking pan", "metal");
        addItem("window frame", "metal");
        addItem("telephone wire", "metal");
        addItem("penny", "metal");
        addItem("pennies", "metal");
        addItem("zinc", "metal");
        addItem("tin", "metal");
        addItem("coffee", "food");
        addItem("vegetables", "food");
        addItem("fruit", "food");
        addItem("book", "paper");
        addItem("notebook", "paper");
        addItem("cardboard", "paper");
        addItem("card", "paper");
        addItem("package", "paper");
        addItem("carton", "paper");
        addItem("cheque", "paper");
        addItem("pizza box", "paper");
        addItem("post-it note", "paper");
        addItem("pressboard", "paper");
        addItem("box", "paper");
        addItem("napkin", "paper");
        addItem("banknote", "paper");
        addItem("letter", "paper");
        addItem("magazine", "paper");
        addItem("window", "glass");
        addItem("mirror", "glass");
        addItem("pyrex", "glass");
        addItem("ceramics", "glass");
        addItem("glass bottle", "glass");
        addItem("glasses", "glass");
    }

    public ArrayList<Item> getAll() {
        ArrayList<Item> items= new ArrayList<Item>();
        ItemCursorWrapper cursor= queryItems(null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            items.add(cursor.getItem());
            cursor.moveToNext();
        }
        cursor.close();
        return items;
    }

    // Database helper methods to convert between Items and database rows
    private static ContentValues getContentValues(Item item) {
        ContentValues values=  new ContentValues();
        values.put(ItemsDbSchema.ItemTable.Cols.WHAT, item.getWhat());
        values.put(ItemsDbSchema.ItemTable.Cols.WHERE, item.getWhere());
        return values;
    }

    static private ItemCursorWrapper queryItems(String whereClause, String[] whereArgs) {
        Cursor cursor= mDatabase.query(
                ItemsDbSchema.ItemTable.NAME,
                null, // Columns - null selects all columns
                whereClause, whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new ItemCursorWrapper(cursor);
    }

    public void close() {   mDatabase.close();   }
}

