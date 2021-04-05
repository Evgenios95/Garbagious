package com.example.myapplication;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkFetcher {
    private static final String TAG = "NetworkFetchr";

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +  urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public void fetchItems(String url) {
        try {
            parseItems(new String(getUrlBytes(url)));
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }
    }

    private void parseItems(String jsonString) throws IOException, JSONException {
        ItemsDB itemsDB= ItemsDB.get(null); // Singleton has already been created
        JSONArray itemsA= new JSONArray(jsonString);
        if (itemsA.length()>0) {
            for (int i= 0;(i<itemsA.length()); i++) {
                    itemsDB.initItem( itemsA.getJSONObject(i).getString("what"),
                            itemsA.getJSONObject(i).getString("whereC"));
            }
        }
    }
}

