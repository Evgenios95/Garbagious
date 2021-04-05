package com.example.myapplication;

public class httpThread implements Runnable {
    // to pass parameter to thread
    String url;

    public httpThread(String url) {
        this.url= url;
    }

    // doInBackGround
    public void run() {
        new NetworkFetcher().fetchItems(url);
    }
}

