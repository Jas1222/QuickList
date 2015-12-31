package com.example.jaspalhayer.quicklist;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {


    EditText loginEmailField;
    EditText loginPasswordField;
    Button mLoginButton;

    UserCredentialHandler loginHandler = new UserCredentialHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setVariablesToUiElements();

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInputText(loginHandler);
                loginHandler.loginUser(getApplicationContext());
            }
        });

    }

    private void setVariablesToUiElements(){
        loginEmailField = (EditText)findViewById(R.id.login_emailField);
        loginPasswordField = (EditText)findViewById(R.id.login_passwordField);
        mLoginButton = (Button)findViewById(R.id.login_login_button);

    }

    private void saveUserInputText(UserCredentialHandler loginHandler){
        loginHandler.userEmail = loginEmailField.getText().toString();
        loginHandler.userPassword = loginPasswordField.getText().toString();
    }

}
