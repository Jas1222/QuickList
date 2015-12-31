package com.example.jaspalhayer.quicklist;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jas1222 on 20/12/2015.
 */
public class UserCredentialHandler {
    protected static final String KEY_FULLNAME = "name";
    protected static final String KEY_PASSWORD = "password";
    protected static final String KEY_EMAIL = "email";

    protected JSONObject jsonResult;

    public boolean isUserLoggedIn = false;
    protected String loginResponse;
    protected String loginMessage;

    protected String userFullname;
    protected String userEmail;
    protected String userPassword;

    protected String registerUrl = "http://qt003605.webs.sse.reading.ac.uk/android_login_api/register.php";
    protected String loginUrl = "http://qt003605.webs.sse.reading.ac.uk/android_login_api/login.php";

    protected void registerUser(final Context context) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, registerUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, response, Toast.LENGTH_LONG).show();
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
                params.put(KEY_FULLNAME, userFullname);
                params.put(KEY_PASSWORD, userPassword);
                params.put(KEY_EMAIL, userEmail);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    protected void loginUser(final Context context){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObjectResponse = new JSONObject(response);
                            loginResponse = jsonObjectResponse.getString("error");
                            if (loginError(loginResponse)){
                                loginMessage = jsonObjectResponse.getString("error_msg");
                                Toast.makeText(context, loginMessage, Toast.LENGTH_LONG).show();
                            } else {
                                loginMessage = jsonObjectResponse.getString("login_msg");
                                isUserLoggedIn = true;
                                Toast.makeText(context, loginMessage, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
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
                params.put(KEY_PASSWORD, userPassword);
                params.put(KEY_EMAIL, userEmail);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    protected boolean loginError(String error){
        if (error == "true"){
            return true;
        } else {
            return false;
        }
    }


    protected void updateUserState(){
        if (isUserLoggedIn){

        }
    }
}
