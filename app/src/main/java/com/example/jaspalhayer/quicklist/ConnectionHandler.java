package com.example.jaspalhayer.quicklist;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.json.JSONException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by jaspalhayer on 29/10/2015.
 */
public class ConnectionHandler {
    HttpURLConnection urlConnection;
    StringBuilder result = new StringBuilder();
    String postUrl = "http://qt003605.webs.sse.reading.ac.uk/android_connect/create_book_listingTest.php";

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }


    // TODO Rename class to read
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

    public void createListingPost(Context context){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, postUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       System.out.println(error);
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("book_title",CreateListingActivity.bookInfoField);
                params.put("book_author",CreateListingActivity.authorInfoField);
                params.put("book_year",CreateListingActivity.yearInfoField);
                params.put("book_desc",CreateListingActivity.descInfoField);
                params.put("book_price", CreateListingActivity.priceInfoField);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void saveParams() {

    }
}


