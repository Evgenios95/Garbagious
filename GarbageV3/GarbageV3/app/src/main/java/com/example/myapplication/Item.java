package com.example.myapplication;

public class Item {
    private String mWhat;
    private String mWhere;

    public Item(String what, String where) {
        this.mWhat = what;
        this.mWhere = where;
    }

    @Override
    public String toString() {
        return oneLine("", " in: ");
    }

    public String getWhat() {
        return mWhat;
    }

    public String getWhere() {
        return mWhere;
    }

    private String oneLine(String pre, String post) {
        return pre+mWhat + post +mWhere;
    }
}

