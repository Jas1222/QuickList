package com.example.jaspalhayer.quicklist;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {


    EditText loginEmailField;
    EditText loginPasswordField;
    Button mLoginButton;

    UserCredentialHandler loginHandler = new UserCredentialHandler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View navHeader = inflater.inflate(R.layout.nav_header_main, null);

        setVariablesToUiElements();

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInputText(loginHandler);
                loginHandler.loginUser(getApplicationContext(), new UserCredentialHandler.VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        if (loginHandler.isUserLoggedIn = true){
                            TextView navNameText = (TextView)navHeader.findViewById(R.id.nav_fullname);
                            TextView navNameEmail = (TextView)navHeader.findViewById(R.id.nav_email_header);
                            navNameText.setText(loginHandler.userFullname);
                            navNameEmail.setText(loginHandler.userEmail);
                        }
                    }
                });
//                if (loginHandler.isUserLoggedIn = true){
//                    TextView navNameText = (TextView)findViewById(R.id.nav_fullname);
//                    TextView navNameEmail = (TextView)findViewById(R.id.nav_email_header);
//                    navNameText.setText(loginHandler.userFullname);
//                    navNameEmail.setText(loginHandler.userEmail);
//                }

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
