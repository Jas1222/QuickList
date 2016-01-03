package com.example.jaspalhayer.quicklist;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    Button mLoginButton;

    UserCredentialHandler loginHandler = new UserCredentialHandler();

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_login, container, false);
        setVariablesToUiElements(rootView);


        SharedPreferences prefs = getActivity().getApplicationContext().getSharedPreferences("userNamePrefs", 0);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("NAV_HEADER_EMAIL", "test value").apply();


        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveUserInputText(loginHandler);
                loginHandler.loginUser(getActivity().getApplicationContext(), new UserCredentialHandler.VolleyCallBack() {
                    @Override
                    public void onSuccess() {

                        if (loginHandler.isUserLoggedIn = true){
//                            TextView navNameText = (TextView)navHeader.findViewById(R.id.nav_fullname);
//                            TextView navNameEmail = (TextView)navHeader.findViewById(R.id.nav_email_header);
//                            navNameText.setText(loginHandler.userFullname);
//                            navNameEmail.setText(loginHandler.userEmail);
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
        return rootView;
    }

    private void setVariablesToUiElements(View rootView){
        loginEmailField = (EditText)rootView.findViewById(R.id.login_emailField);
        loginPasswordField = (EditText)rootView.findViewById(R.id.login_passwordField);
        mLoginButton = (Button)rootView.findViewById(R.id.login_login_button);

    }

    private void saveUserInputText(UserCredentialHandler loginHandler){
        loginHandler.userEmail = loginEmailField.getText().toString();
        loginHandler.userPassword = loginPasswordField.getText().toString();
    }

}
