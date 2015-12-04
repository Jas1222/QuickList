package com.example.jaspalhayer.quicklist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONObject;

public class SearchActivity extends AppCompatActivity {
    protected Button sButton;
    protected EditText field_isbn;
    protected EditText field_title;
    protected EditText field_author;
    protected String input_isbn;
    protected String input_title;
    protected String input_author;

    JSONObject jsOb = new JSONObject();
    ConnectionHandler handler = new ConnectionHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setVariablesToUiElements();
        sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInputText();
                if(validInputFields()) {
                    handler.searchListings(getApplicationContext(), input_isbn, input_title, input_author, new ConnectionHandler.VolleyCallback() {
                        @Override
                        public void onSuccess(JSONObject result) {
                          jsOb = result;
                          Intent i = new Intent(getApplicationContext(), SearchResultActivity.class);
                          i.putExtra("jsonObject", jsOb.toString());
                          startActivity(i);
                        }
                    });
                } else {
                    Snackbar.make(view, "Ensure you have selected all fields", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    private void saveUserInputText(){
        input_isbn = field_isbn.getText().toString();
        input_title = field_title.getText().toString();
        input_author = field_author.getText().toString();
    }

    private void setVariablesToUiElements(){
        field_isbn = (EditText)findViewById(R.id.sIsbn);
        field_title = (EditText)findViewById(R.id.sTitle);
        field_author = (EditText)findViewById(R.id.sAuthor);
        sButton = (Button)findViewById(R.id.search_button);

    }

    private boolean validInputFields(){
        if (input_title.isEmpty() && input_isbn.isEmpty() && input_author.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
