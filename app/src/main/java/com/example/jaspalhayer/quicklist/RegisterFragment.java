package com.example.jaspalhayer.quicklist;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class RegisterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button mRegister;

    protected String editTextName;
    protected String editTextEmail;
    protected String editTextPassword;

    protected EditText nameField;
    protected EditText emailField;
    protected EditText passwordField;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    LoginFragment.OnLoginCallback mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final UserCredentialHandler registerHandler = new UserCredentialHandler();
        View rootView = inflater.inflate(R.layout.activity_register, container, false);

        final SharedPreferences prefs = getActivity().getApplicationContext().getSharedPreferences("userNamePrefs", 0);

        setVariablesToUiElements(rootView);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInputText();

                if(validInputFields()) {
                    registerHandler.userFullname = editTextName;
                    registerHandler.userEmail = editTextEmail;
                    registerHandler.userPassword = editTextPassword;
                    registerHandler.registerUser(getActivity().getApplicationContext(), new UserCredentialHandler.VolleyCallBack() {
                        @Override
                        public void onSuccess() {
                            registerHandler.loginUser(getActivity().getApplicationContext(), prefs, new UserCredentialHandler.VolleyCallBack() {
                                @Override
                                public void onSuccess() {
                                    mCallback = (LoginFragment.OnLoginCallback) getActivity();
                                    mCallback.onLoginSuccess();
                                }
                            });
                        }
                    });
                } else {
                    Snackbar.make(v, "Ensure you have filled in all fields", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();                }
            }
        });
        return rootView;
    }


    private void setVariablesToUiElements(View rootView){
        mRegister = (Button)rootView.findViewById(R.id.signup_button);

        nameField = (EditText)rootView.findViewById(R.id.nameField);
        emailField = (EditText)rootView.findViewById(R.id.emailField);
        passwordField = (EditText)rootView.findViewById(R.id.passwordField);
    }

    //TODO remove editText variables, store in registerHandler object directly instead
    private void saveUserInputText(){
        editTextName = nameField.getText().toString();
        editTextEmail = emailField.getText().toString();
        editTextPassword = passwordField.getText().toString();
    }

    private boolean validInputFields(){
        if (editTextName.isEmpty() && editTextName.isEmpty() && editTextPassword.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }


}
