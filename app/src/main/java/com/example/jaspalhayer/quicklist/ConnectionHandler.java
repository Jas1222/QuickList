package com.example.jaspalhayer.quicklist;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jaspalhayer on 29/10/2015.
 */
public class ConnectionHandler {
    HttpURLConnection urlConnection;
    StringBuilder result = new StringBuilder();

    // Reads listings
    public String openHttpConnection() {
        try {

            URL url = new URL("http://qt003605.webs.sse.reading.ac.uk/android_connect/get_all_listings.php");

            try {
                urlConnection = (HttpURLConnection) url.openConnection();

                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    Log.d("OK_TAG", "Connected Okay");
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                } else {
                    // TODO Handle Exception
                }

            } catch (Exception e) {
                e.printStackTrace();
                // TODO Handle Exception
            } finally {
                urlConnection.disconnect();
            }

        } catch (MalformedURLException nameOfTheException) {

        }
        return result.toString();
    }

}
