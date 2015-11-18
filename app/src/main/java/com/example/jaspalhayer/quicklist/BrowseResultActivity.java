package com.example.jaspalhayer.quicklist;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BrowseResultActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    protected String[] book_titles;
    protected String courseTitle;
    protected JSONArray jsArray = new JSONArray();
    protected JSONObject jsonObject;
    protected String selectedCourseYear;
    protected String selectedCourseTitle;
    protected String[] book_authors;
    protected ConnectionHandler handler = new ConnectionHandler();
    protected String[] dates_listed;
    protected int book_prices[] = {
            12,
            12,
            24,
            54
    };

    List<BrowseRowItem> rowItems;
    ListView myListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Books mBook = new Books();

        try {
            jsonObject = new JSONObject(getIntent().getStringExtra("jsonObject"));
        } catch (Exception e) {
            System.out.println("Fucked up retrieving jsonObject from Intent");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        courseTitle = getIntent().getStringExtra("keyTitle");
        selectedCourseTitle = getIntent().getStringExtra("keyTitle");
        selectedCourseYear = getIntent().getStringExtra("keyUniYear");

        rowItems = new ArrayList<>();
        getSupportActionBar().setTitle(courseTitle);

        myListView = (ListView) findViewById(R.id.listView);
        BrowseCustomAdapter adapter = new BrowseCustomAdapter(this, rowItems);
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(this);


        try {
            jsArray = jsonObject.getJSONArray("listing");
            for (int i = 0; i < jsArray.length(); i++) {
                JSONObject jobj = jsArray.getJSONObject(i);

                mBook.jsonBookTitle.add(jobj.getString("book_title"));
                mBook.jsonBookAuthor.add(jobj.getString("book_author"));
                mBook.jsonUniCourse.add(jobj.getString("uni_course"));
                mBook.jsonBookPrice.add(jobj.getString("book_price"));
                mBook.jsonUniYear.add(jobj.getString("uni_year"));
                mBook.jsonBookIsbn.add(jobj.getString("isbn"));
                mBook.jsonBookYear.add(jobj.getString("book_year"));

            }
        } catch (Exception e) {
            System.out.println("Parsing the JSON format fucked up");
        }




        book_titles = getResources().getStringArray(R.array.testBookTitles);
        book_authors = getResources().getStringArray(R.array.testBookAuthor);
        dates_listed = getResources().getStringArray(R.array.testDateListed);

        for (int i = 0; i < mBook.jsonBookTitle.size(); i++) {
            BrowseRowItem item = new BrowseRowItem(mBook.jsonBookTitle.get(i), mBook.jsonBookAuthor.get(i), mBook.jsonBookPrice.get(i), dates_listed[i]);
            rowItems.add(item);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        String member_name = rowItems.get(position).bookTitle;
        Toast.makeText(getApplicationContext(), "" + member_name,
                Toast.LENGTH_SHORT).show();
    }

    class LoadCourseListings extends AsyncTask<String, String, String> {

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
                // handler.getCourseListing(getApplicationContext(), selectedCourseTitle, selectedCourseYear);
                //   handler.getCourseListingTest(getApplicationContext(), selectedCourseTitle, selectedCourseYear);

            } catch (Exception e) {
                System.out.println("Unable to get course listings");
            }
            return null;
        }
    }

}
