package com.example.jaspalhayer.quicklist;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    EditText loginEmailField;
    EditText loginPasswordField;
    CardView mLoginButton;

    OnLoginCallback mCallback;

    UserCredentialHandler loginHandler = new UserCredentialHandler();

    public LoginFragment() {
        // Required empty public constructor
    }

    public interface OnLoginCallback{
        void onLoginSuccess();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_login, container, false);
        setVariablesToUiElements(rootView);


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

    private void setVariablesToUiElements(View rootView){
        loginEmailField = (EditText)rootView.findViewById(R.id.login_emailField);
        loginPasswordField = (EditText)rootView.findViewById(R.id.login_passwordField);
        mLoginButton = (CardView)rootView.findViewById(R.id.login_login_button);

    }

    private void saveUserInputText(UserCredentialHandler loginHandler){
        loginHandler.userEmail = loginEmailField.getText().toString();
        loginHandler.userPassword = loginPasswordField.getText().toString();
    }
}
