package com.example.jaspalhayer.quicklist;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

/**
 * Created by jaspalhayer on 03/11/2015.
 */
public class CreateListingActivity extends AppCompatActivity {
    public static String bookInfoField;
    public static String authorInfoField;
    public static String yearInfoField;
    public static String descInfoField;
    public static String priceInfoField;




    protected ListView lstView;

    protected Spinner degreeTypeSpinner;
    protected Spinner courseSpinner;
    protected Spinner yearSpinner;

    protected ArrayAdapter<CharSequence> degreeTypeAdapter;
    protected ArrayAdapter<CharSequence> ugCourseAdapter;
    protected ArrayAdapter<CharSequence> pgCourseAdapter;
    protected ArrayAdapter<CharSequence> yearAdapter;
    protected ArrayAdapter<CharSequence> initialCourseAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_listing_activity);

        CreateFragment1 createFragment1 = new CreateFragment1();

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
}
