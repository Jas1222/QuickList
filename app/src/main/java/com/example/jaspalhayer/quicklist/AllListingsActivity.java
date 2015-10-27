package com.example.jaspalhayer.quicklist;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.*;

import java.util.List;

/**
 * Created by jaspalhayer on 26/10/2015.
 */
public class AllListingsActivity extends Activity {

    //JSON Nodes
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_BOOKS = "books";
    private static final String TAG_LID = "list_id";
    private static final String TAG_LISTING_TITLE = "book_title";

    JSONArray books = null;
    public String URL = "http://localhost:8888/quicklist/get_all_listings.php";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hashmap for ListView

        // Loading products in Background Thread
        new LoadAllProducts().execute();

    }

    class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        HttpURLConnection urlConnection;

        /**
         * getting All products from url
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            // getting JSON string from URL
            StringBuilder result = new StringBuilder();

            try {

                URL url = new URL("http://qt003605.webs.sse.reading.ac.uk/android_connect/get_all_listings.php");

                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                    System.out.println(urlConnection.getResponseCode());
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
}