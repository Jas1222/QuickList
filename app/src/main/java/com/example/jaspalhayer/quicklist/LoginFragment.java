package com.example.jaspalhayer.quicklist;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    EditText loginEmailField;
    EditText loginPasswordField;
    CardView mLoginButton;
    LoginButton facebookLoginButton;
    CallbackManager callbackManager;


    OnLoginCallback mCallback;

    UserCredentialHandler loginHandler = new UserCredentialHandler();

    public LoginFragment() {
        // Required empty public constructor
    }

    public interface OnLoginCallback {
        void onLoginSuccess();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_login, container, false);
        setVariablesToUiElements(rootView);
        facebookLoginButton.setReadPermissions("public_profile, email");
        facebookLoginButton.setFragment(this);

        mCallback = (OnLoginCallback) getActivity();

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInputText(loginHandler);
                loginHandler.loginUser(getActivity().getApplicationContext(), new UserCredentialHandler.VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        mCallback.onLoginSuccess();
                    }
                });
            }
        });
        return rootView;
    }

    private void setVariablesToUiElements(View rootView) {
        loginEmailField = (EditText) rootView.findViewById(R.id.login_emailField);
        loginPasswordField = (EditText) rootView.findViewById(R.id.login_passwordField);
        mLoginButton = (CardView) rootView.findViewById(R.id.login_login_button);
        facebookLoginButton = (LoginButton) rootView.findViewById(R.id.facebook_login_button);
    }

    private void saveUserInputText(UserCredentialHandler loginHandler) {
        loginHandler.userEmail = loginEmailField.getText().toString();
        loginHandler.userPassword = loginPasswordField.getText().toString();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println(loginResult);
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {

                                try {
                                    String userName = object.getString("name");
                                    String userEmail = object.getString("email");
                                    System.out.println("User email: " + userEmail + " \n User name: " + userName);
                                    loginHandler.saveUserEmailAndName(getActivity().getApplicationContext(), userName, userEmail);
                                    loginHandler.storeUserEmailAndName(getActivity().getApplicationContext());

                                } catch (Exception e) {
                                    System.out.println("Parsing and saving name email from fb fucked up");
                                }


                                //TODO parse and save user details
                                mCallback.onLoginSuccess();
                                // Application code
                                Log.v("LoginActivity", response.toString());
                                // Possibly run callback onsuccess?
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
                Log.v("LoginActivity", "cancel");
                System.out.println("attempted canceled");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                System.out.println("attempted failed");
                Log.v("LoginActivity", exception.getCause().toString());
            }
        });
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
