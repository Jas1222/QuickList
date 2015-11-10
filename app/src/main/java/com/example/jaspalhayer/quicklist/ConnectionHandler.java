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
import com.android.volley.*;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by jaspalhayer on 29/10/2015.
 */
public class ConnectionHandler {
    HttpURLConnection urlConnection;
    StringBuilder result = new StringBuilder();
    String postUrl = "http://qt003605.webs.sse.reading.ac.uk/android_connect/create_book_listingTest.php";
    String getCourseListUrl = "http://qt003605.webs.sse.reading.ac.uk/android_connect/get_book_listing.php?param1=%1$s&param2=%2$s";

    public void createListingPost(Context context){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, postUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                    }
                },
                new Response.ErrorListener(){
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

    public void getCourseListing(Context context, String uniCourse, String uniYear){
        String uri = String.format(getCourseListUrl,
                uniCourse,
                uniYear);

        StringRequest myReq = new StringRequest(Request.Method.GET,
                uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(myReq);
    }
}


