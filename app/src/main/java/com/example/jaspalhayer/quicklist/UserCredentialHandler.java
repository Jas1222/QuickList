package com.example.jaspalhayer.quicklist;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.widget.TextView;
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
    protected static final String POST_KEY_FULLNAME = "name";
    protected static final String POST_KEY_PASSWORD = "password";
    protected static final String POST_KEY_EMAIL = "email";

    protected static final String KEY_NAV_NAME = "NAV_NAME";
    protected static final String KEY_NAV_EMAIL = "NAV_EMAIL";
    protected static final String KEY_USER_STATUS = "USER_STATUS";
    protected static final String USER_PREFS = "userNamePrefs";

    protected static final String userLoggedIn = "true";
    protected static final String userNotLoggedIn = "false";

    public boolean isUserLoggedIn = false;
    protected String loginResponse;
    protected String loginMessage;

    protected String registerResponse;
    protected String registerMessage;

    protected String userFullname;
    protected String userEmail;
    protected String userPassword;

    protected String registerUrl = "http://qt003605.webs.sse.reading.ac.uk/android_login_api/register.php";
    protected String loginUrl = "http://qt003605.webs.sse.reading.ac.uk/android_login_api/login.php";

    protected void registerUser(final Context context, final VolleyCallBack callBack) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, registerUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObjectResponse = new JSONObject(response);
                            registerResponse = jsonObjectResponse.getString("error");
                            if(registerError(registerResponse)){
                                registerMessage = jsonObjectResponse.getString("error_msg");
                                Toast.makeText(context, registerMessage, Toast.LENGTH_LONG).show();
                            } else {
                                registerMessage = jsonObjectResponse.getString("rgstr_msg");
                                Toast.makeText(context, registerMessage, Toast.LENGTH_LONG).show();
                                callBack.onSuccess();
                            }
                        } catch (Exception e){
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
                params.put(POST_KEY_FULLNAME, userFullname);
                params.put(POST_KEY_PASSWORD, userPassword);
                params.put(POST_KEY_EMAIL, userEmail);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    protected void loginUser(final Context context, final VolleyCallBack callback){
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
                                userEmail = jsonObjectResponse.getString("email");
                                userFullname = jsonObjectResponse.getString("name");
                                isUserLoggedIn = true;

                                storeUserEmailAndName(context);
                                Toast.makeText(context, loginMessage, Toast.LENGTH_LONG).show();
                                callback.onSuccess();
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
                params.put(POST_KEY_PASSWORD, userPassword);
                params.put(POST_KEY_EMAIL, userEmail);
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

    protected boolean registerError(String error){
        if (error == "false"){
            return false;
        } else {
            return true;
        }
    }

    protected void storeUserEmailAndName(Context context){
        SharedPreferences prefs = context.getSharedPreferences(USER_PREFS, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_NAV_NAME, this.userFullname).apply();
        editor.putString(KEY_NAV_EMAIL, this.userEmail).apply();
        editor.putString(KEY_USER_STATUS, "true").apply();
    }

    protected boolean checkIfUserIsLoggedIn(Context context){
        String getStatus;
        SharedPreferences prefs = context.getSharedPreferences(USER_PREFS, 0);
        getStatus = prefs.getString(KEY_USER_STATUS, "");
        if (getStatus.equals("true")){
            return true;
        } else {
            return false;
        }
    }
    protected void logoutUser(Context context){
        isUserLoggedIn=false;
        SharedPreferences prefs = context.getSharedPreferences(USER_PREFS, 0);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(KEY_USER_STATUS, userNotLoggedIn).apply();
        editor.putString(KEY_NAV_NAME, "").apply();
        editor.putString(KEY_NAV_EMAIL, "").apply();

        Toast.makeText(context, "You have logged out!", Toast.LENGTH_LONG).show();
    }

    protected void setNavHeaderOnLogin(Context context, NavigationView navigationView){
        SharedPreferences prefs = context.getSharedPreferences(USER_PREFS, 0);

        TextView navHeaderFullName = (TextView)navigationView.findViewById(R.id.nav_fullname);
        TextView navHeaderEmail = (TextView)navigationView.findViewById(R.id.nav_email_header);

        navHeaderEmail.setText(prefs.getString(KEY_NAV_EMAIL, this.userFullname));
        navHeaderFullName.setText(prefs.getString(KEY_NAV_NAME, this.userEmail));
    }

    protected void setNavHeaderOnLogout(NavigationView navigationView){
        TextView navHeaderFullName = (TextView)navigationView.findViewById(R.id.nav_fullname);
        TextView navHeaderEmail = (TextView)navigationView.findViewById(R.id.nav_email_header);

        navHeaderFullName.setText("You are not logged in");
        navHeaderEmail.setText("Please login below");
    }

    public interface VolleyCallBack{
        void onSuccess();
    }
}
