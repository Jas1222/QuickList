package com.example.jaspalhayer.quicklist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by jaspalhayer on 03/11/2015.
 */
public class CreateListingActivity extends AppCompatActivity {

    EditText titleText;
    EditText authorText;
    EditText yearText;
    EditText descText;
    EditText priceText;

    Button mListing;

    public static String bookInfoField;
    public static String authorInfoField;
    public static String yearInfoField;
    public static String descInfoField;
    public static String priceInfoField;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.listing_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setVariablesToUiElements();

        mListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTextListing();
                new CreateBookListing().execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_listing_toolbar, menu);
        return true;
    }

    private void saveTextListing(){
        bookInfoField = titleText.getText().toString();
        authorInfoField = authorText.getText().toString();
        yearInfoField = yearText.getText().toString();
        descInfoField = descText.getText().toString();
        priceInfoField = priceText.getText().toString();
    }

    private void setVariablesToUiElements(){
        titleText = (EditText)findViewById(R.id.titleText);
        authorText = (EditText)findViewById(R.id.authorText);
        yearText = (EditText)findViewById(R.id.yearText);
        descText = (EditText)findViewById(R.id.descText);
        priceText = (EditText)findViewById(R.id.priceText);

        mListing = (Button)findViewById(R.id.makeListingBtn);
    }

    class CreateBookListing extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            ConnectionHandler handler = new ConnectionHandler();
            try {
                handler.createListingPost(getApplicationContext());
            } catch (Exception e) {
                System.out.println("Unable to create listing");
            }
            return null;
        }
    }
}
