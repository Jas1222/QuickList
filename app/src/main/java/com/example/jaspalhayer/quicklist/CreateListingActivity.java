package com.example.jaspalhayer.quicklist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

/**
 * Created by jaspalhayer on 03/11/2015.
 */
public class CreateListingActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.listing_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // ** OPENS CONNECTION TO DATABASE TO RETRIEVE ALL LISTINGS - TODO MOVE
        //new LoadAllListings().execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_listing_toolbar, menu);
        return true;
    }
}
