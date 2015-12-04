package com.example.jaspalhayer.quicklist;

import android.os.AsyncTask;
import android.os.Bundle;
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
        setContentView(R.layout.listing_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setVariablesToUiElements();
        createArrayAdapters();
        setSpinnerToAdapter();
        setAdapter();


        mListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInputText();
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

    private void saveUserInputText(){
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
        lstView = (ListView)findViewById(R.id.courseList);

        degreeTypeSpinner = (Spinner)findViewById(R.id.degreeTypeSpinner);
        courseSpinner = (Spinner)findViewById(R.id.courseSpinner);
        yearSpinner = (Spinner)findViewById(R.id.yearSpinner);
        mListing = (Button)findViewById(R.id.makeListingBtn);
    }

    protected void createArrayAdapters(){
        degreeTypeAdapter = ArrayAdapter.createFromResource(this, R.array.course_type, R.layout.course_spinner_layout_create);
        ugCourseAdapter = ArrayAdapter.createFromResource(this, R.array.undergrad_courses, R.layout.course_spinner_layout_create);
        pgCourseAdapter = ArrayAdapter.createFromResource(this, R.array.postgrad_courses, R.layout.course_spinner_layout_create);
        yearAdapter = ArrayAdapter.createFromResource(this, R.array.course_year_array, R.layout.course_spinner_layout_create);
      //  initialCourseAdapter = ArrayAdapter.createFromResource(this, R.array.initial_course, R.layout.course_spinner_layout);
    }

    protected void setSpinnerToAdapter(){
        ugCourseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pgCourseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        degreeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      //  initialCourseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    protected void setAdapter(){
        yearSpinner.setAdapter(yearAdapter);
        courseSpinner.setAdapter(ugCourseAdapter);
        degreeTypeSpinner.setAdapter(degreeTypeAdapter);
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
