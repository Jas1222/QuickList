package com.example.jaspalhayer.quicklist;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import org.json.JSONObject;

/**
 * Created by jaspalhayer on 03/11/2015.
 */
public class CreateListingActivity extends AppCompatActivity {
    JSONObject jsonObject;
    Bundle b;
    boolean cameFromScan = false;
    String cameFrom;
    boolean cameFromEdit = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_listing_activity);

        CreateBookInfoFragment createFragment1 = new CreateBookInfoFragment();

        jsonObject = new JSONObject();
        b = new Bundle();

        cameFromScan = checkCameFromScanActivity();
        cameFromEdit = checkCameFromEdit();

        // TODO refactor into a case statement, rethink design of 'CAME_FROM'
        if (cameFromScan) {
            jsonObject = new JSONObject();
            getJsonObject();
            putJsonInBundle();
            createFragment1.setArguments(b);
        }

        if (cameFromEdit) {
            setTitle("Edit a Listing");
            getJsonObject();
            putJsonInBundle();
            createFragment1.setArguments(b);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        fragmentTransaction.replace(R.id.create_main_container, createFragment1);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        int backstackCount = getSupportFragmentManager().getBackStackEntryCount();

        if (backstackCount == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // DONT NEED TOOLBAR?
        //getMenuInflater().inflate(R.menu.create_listing_toolbar, menu);
        return true;
    }

    protected void getJsonObject() {
        try {
            jsonObject = new JSONObject(getIntent().getStringExtra("jsonObject"));
        } catch (Exception e) {
            System.out.println("Fucked up retrieving jsonObject from Intent");
        }
    }

    protected boolean checkCameFromScanActivity() {
        if (getIntent().getStringExtra("CAME_FROM") != null) {
            cameFrom = getIntent().getStringExtra("CAME_FROM");
            cameFromScan = cameFrom.contains("scan");
        }

        if (cameFromScan) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean checkCameFromEdit() {
        if (getIntent().getStringExtra("CAME_FROM") != null) {
            cameFrom = getIntent().getStringExtra("CAME_FROM");
            cameFromEdit = cameFrom.contains("EDIT");
        }

        if (cameFromEdit) {
            return true;
        } else {
            return false;
        }
    }

    protected void putJsonInBundle() {
        b.putString("JSON_STRING_OBJECT", jsonObject.toString());
        if (cameFromEdit) {
            b.putString("CAME_FROM_EDIT", "EDIT");

            String listid = getIntent().getStringExtra("LIST_ID");
            b.putString("LIST_ID", listid);
        }
    }
}
