package com.example.jaspalhayer.quicklist;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jaspalhayer on 29/10/2015.
 */
public class ConnectionHandler {
    HttpURLConnection urlConnection;
    StringBuilder result = new StringBuilder();
    String postUrl = "http://qt003605.webs.sse.reading.ac.uk/android_connect/create_book_listingTest.php";
    String getCourseListUrl = "http://qt003605.webs.sse.reading.ac.uk/android_connect/get_book_listingTest.php";

    public static final String UNI_YEAR ="uni_year";
    public static final String UNI_COURSE="uni_course";
    public static final String BOOK_TITLE="book_title";
    public static final String BOOK_AUTHOR="book_author";
    public static final String BOOK_PRICE="book_price";
    public static final String BOOK_YEAR="book_year";
    public static final String ISBN ="isbn";

    public void createListingPost(Context context) {
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
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("book_title", CreateListingActivity.bookInfoField);
                params.put("book_author", CreateListingActivity.authorInfoField);
                params.put("book_year", CreateListingActivity.yearInfoField);
                params.put("book_desc", CreateListingActivity.descInfoField);
                params.put("book_price", CreateListingActivity.priceInfoField);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public Books getCourseListingTest(final Context context, final String uniCourse, final String uniYear) {
        String getTestUrl = getCourseListUrl+"?uni_year="+uniYear+"&uni_course="+uniCourse;
        getTestUrl = getTestUrl.replaceAll(" ", "%20");

        System.out.println(getTestUrl);

        JsonObjectRequest update_request = new JsonObjectRequest(getTestUrl,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);

                try {
                    int success = response.getInt("success");

                    if (success == 1) {
                        Toast.makeText(context,
                                "Retrieved Successfully",
                                Toast.LENGTH_SHORT).show();

                        JSONArray ja = response.getJSONArray("listing");

                        Books jsonBook = new Books();

                        for(int i = 0; i < ja.length(); i++){
                            JSONObject jobj = ja.getJSONObject(i);
                            HashMap<String, String> item = new HashMap<>();

                            jsonBook.jsonBookTitle.add(jobj.getString("book_title"));
                            jsonBook.jsonBookAuthor.add(jobj.getString("book_author"));
                            jsonBook.jsonUniCourse.add(jobj.getString("uni_course"));
                            jsonBook.jsonBookPrice.add(jobj.getString("book_price"));
                            jsonBook.jsonUniYear.add(jobj.getString("uni_year"));
                            jsonBook.jsonBookIsbn.add(jobj.getString("isbn"));
                            jsonBook.jsonBookYear.add(jobj.getString("book_year"));

                            System.out.println(item);
                        }


                    } else {
                        Toast.makeText(context,
                                "failed to update", Toast.LENGTH_SHORT)
                                .show();
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(update_request);

        // Adding request to request queue

//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, getCourseListUrl,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        System.out.println(response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        System.out.println(error);
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("uni_course", uniCourse);
//                params.put("uni_year", uniYear);
//                return params;
//            }
//
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        requestQueue.add(stringRequest);

    }
}

