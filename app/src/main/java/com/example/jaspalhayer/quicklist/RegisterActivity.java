package com.example.jaspalhayer.quicklist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    Button mRegister;

    protected String editTextName;
    protected String editTextEmail;
    protected String editTextPassword;

    protected EditText nameField;
    protected EditText emailField;
    protected EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final UserCredentialHandler registerHandler = new UserCredentialHandler();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setVariablesToUiElements();

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInputText();

                registerHandler.userFullname = editTextName;
                registerHandler.userEmail = editTextEmail;
                registerHandler.userPassword = editTextPassword;
                registerHandler.registerUser(getApplicationContext());
            }
        });

    }

    private void setVariablesToUiElements(){
        mRegister = (Button)findViewById(R.id.signup_button);

        nameField = (EditText)findViewById(R.id.nameField);
        emailField = (EditText)findViewById(R.id.emailField);
        passwordField = (EditText)findViewById(R.id.passwordField);
    }

    //TODO remove editText variables, store in registerHandler object directly instead
    private void saveUserInputText(){
        editTextName = nameField.getText().toString();
        editTextEmail = emailField.getText().toString();
        editTextPassword = passwordField.getText().toString();
    }
}
