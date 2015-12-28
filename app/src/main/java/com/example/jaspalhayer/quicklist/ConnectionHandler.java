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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jaspalhayer on 29/10/2015.
 */
public class ConnectionHandler {
    String createListingPostUrl = "http://qt003605.webs.sse.reading.ac.uk/android_connect/create_book_listing.php";
    String getCourseListUrl = "http://qt003605.webs.sse.reading.ac.uk/android_connect/get_book_listing.php";
    String getSearchUrl = "http://qt003605.webs.sse.reading.ac.uk/android_connect/search_book_listings.php";
    String getBookListingDetailUrl = "http://qt003605.webs.sse.reading.ac.uk/android_connect/get_book_listing_details.php";

    String localCreatePostUrl = "http://10.0.2.2:8080/quicklist/create_book_listingTest.php";
    String localSearchUrl="http://10.0.2.2:8080/quicklist/search_book_listings.php";
    String localPostUrl="http://10.0.2.2:8080/quicklist/get_book_listingTest.php";

    JSONObject result = new JSONObject();

    public void createListingPost(Context context) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, createListingPostUrl,
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
                Map<String, String> params = new HashMap<>();

                // TODO REFACTOR putParams();
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

    public void getCourseListings(final Context context, final String uniCourse, final String uniYear, final VolleyCallback callback) {
        String getTestUrl = getCourseListUrl+"?uni_year="+uniYear+"&uni_course="+uniCourse;
        getTestUrl = getTestUrl.replaceAll(" ", "%20");

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
                        result=response;
                        callback.onSuccess(result);

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
    }

    public void getCourseListingsDetails(final Context context, final String listId, final VolleyCallback callback) {
        String getTestUrl = getBookListingDetailUrl+"?list_id="+listId;
        getTestUrl = getTestUrl.replaceAll(" ", "%20");

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
                        result=response;
                        callback.onSuccess(result);

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
    }

    public void searchListings(final Context context, final String isbn, final String book_title, final String book_author, final VolleyCallback callback){
        String getTestUrl = getSearchUrl+"?isbn="+isbn+"&book_title="+book_title+"&book_author="+book_author;
        getTestUrl = getTestUrl.replaceAll(" ", "%20");

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
                        result=response;
                        callback.onSuccess(result);

                    } else {
                        Toast.makeText(context,
                                "failed to retrieve search listings", Toast.LENGTH_SHORT)
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
    }

    public interface VolleyCallback{
        void onSuccess(JSONObject result);
    }
}

