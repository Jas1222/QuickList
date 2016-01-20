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
    boolean cameFromScan;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_listing_activity);
        CreateFragment1 createFragment1 = new CreateFragment1();
        jsonObject = new JSONObject();
        b = new Bundle();

        cameFromScan=checkCameFromScanActivity();

        if(cameFromScan) {
            jsonObject = new JSONObject();
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
        getSupportFragmentManager().popBackStack();
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

    protected boolean checkCameFromScanActivity(){
        String cameFrom = getIntent().getStringExtra("CAME_FROM");
        if(cameFrom.contains("scan")){
            return true;
        } else {
            return false;
        }
    }

    protected void putJsonInBundle(){
        b.putString("JSON_STRING_OBJECT", jsonObject.toString());
    }

}
