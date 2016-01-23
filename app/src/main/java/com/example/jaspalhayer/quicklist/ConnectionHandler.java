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
    String getUserListingsUrl = "http://qt003605.webs.sse.reading.ac.uk/android_connect/get_user_listing.php";
    String postDeleteUrl = "http://qt003605.webs.sse.reading.ac.uk/android_connect/delete_listing.php";
    String postUpdateListingStatus = "http://qt003605.webs.sse.reading.ac.uk/android_connect/update_list_status.php";
    String googleBooksUrl = "https://www.googleapis.com/books/v1/volumes?q=isbn:";
    String googleBooksCountry = "&country=UK";

    String localCreatePostUrl = "http://10.0.2.2:8080/quicklist/create_book_listingTest.php";
    String localSearchUrl = "http://10.0.2.2:8080/quicklist/search_book_listings.php";
    String localPostUrl = "http://10.0.2.2:8080/quicklist/get_book_listingTest.php";

    JSONObject result = new JSONObject();

    public void createListingPost(final Context context, final String title, final String author, final String year, final String isbn, final String price, final String desc, final String courseType, final String courseDegree, final String courseYear, final String status_code, final String userId, final String fullName) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, createListingPostUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObjectResponse = new JSONObject(response);
                            String msg = jsonObjectResponse.getString("message");
                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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

                params.put("book_title", title);
                params.put("book_author", author);
                params.put("book_year", year);
                params.put("book_desc", desc);
                params.put("book_price", price);
                params.put("book_isbn", isbn);
                params.put("uni_course_type", courseType);
                params.put("uni_year", courseYear);
                params.put("uni_course", courseDegree);
                params.put("list_status", status_code);
                params.put("user_id", userId);
                params.put("full_name", fullName);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void getCourseListings(final Context context, final String uniCourse, final String uniYear, final VolleyCallback callback) {
        String getTestUrl = getCourseListUrl + "?uni_year=" + uniYear + "&uni_course=" + uniCourse;
        getTestUrl = getTestUrl.replaceAll(" ", "%20");

        JsonObjectRequest update_request = new JsonObjectRequest(getTestUrl,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);

                try {
                    int success = response.getInt("success");

                    if (success == 1) {
                        result = response;
                        callback.onSuccess(result);

                    } else {
                        Toast.makeText(context,
                                "No listings found", Toast.LENGTH_SHORT)
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
        //TODO refactor getTestUrl var
        String getTestUrl = getBookListingDetailUrl + "?list_id=" + listId;
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
                        result = response;
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

    public void getUserListings(final Context context, final String listStatus, final String userId, final VolleyCallback callback) {
        String getTestUrl = getUserListingsUrl + "?list_status=" + listStatus + "&user_id=" + userId;
        getTestUrl = getTestUrl.replaceAll(" ", "%20");

        JsonObjectRequest update_request = new JsonObjectRequest(getTestUrl,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    int success = response.getInt("success");

                    if (success == 1) {
                        Toast.makeText(context,
                                "Retrieved Successfully",
                                Toast.LENGTH_SHORT).show();
                        result = response;
                        callback.onSuccess(result);

                    } else {
                        Toast.makeText(context,
                                "No listings found", Toast.LENGTH_SHORT)
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

    //POST or GET?
    protected void deleteListing(final Context context, final String listId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, postDeleteUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObjectResponse = new JSONObject(response);
                            // TODO rename variables
                            String msg = jsonObjectResponse.getString("message");
                            String r = jsonObjectResponse.getString("success");
                            if (r.contains("1")) {
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("list_id", listId);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    protected void markListingAsCompleted(final Context context, final String listId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, postUpdateListingStatus,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObjectResponse = new JSONObject(response);
                            // TODO rename variables
                            String msg = jsonObjectResponse.getString("message");
                            String r = jsonObjectResponse.getString("success");
                            if (r.contains("1")) {
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("list_id", listId);
                params.put("list_status", "2");

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void searchListings(final Context context, final String isbn, final String book_title, final String book_author, final VolleyCallback callback) {
        String getTestUrl = getSearchUrl + "?isbn=" + isbn + "&book_title=" + book_title + "&book_author=" + book_author;
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
                        result = response;
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

    public void getGoogleBookDetails(final Context context, final String isbn, final VolleyCallback callback) {
        String getTestUrl = googleBooksUrl + isbn + googleBooksCountry;
        getTestUrl = getTestUrl.replaceAll(" ", "%20");

        JsonObjectRequest update_request = new JsonObjectRequest(getTestUrl,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                callback.onSuccess(response);
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

    protected void getGoogleBookRequest2(final Context context, final String isbn, final VolleyCallback callback) {
        String getTestUrl = googleBooksUrl + isbn + googleBooksCountry;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getTestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObjectResponse = new JSONObject(response);
                            callback.onSuccess(jsonObjectResponse);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public interface VolleyCallback {
        void onSuccess(JSONObject result);
    }
}

