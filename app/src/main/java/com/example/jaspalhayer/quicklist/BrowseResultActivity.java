package com.example.jaspalhayer.quicklist;

import android.content.Intent;
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

    protected String courseTitle;
    protected JSONArray jsArray = new JSONArray();
    protected JSONObject jsonObject;
    protected String selectedCourseYear;
    protected String selectedCourseTitle;

    //TODO Get real dates from database
    protected String year_published[] = {
            "2012",
            "1985"
    };

    List<BrowseRowItem> rowItems;
    ListView myListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_result);
        getInputtedStrings();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(courseTitle);
        Books mBook = new Books();
        rowItems = new ArrayList<>();

        BrowseCustomAdapter adapter = new BrowseCustomAdapter(this, rowItems);
        myListView = (ListView) findViewById(R.id.listView);
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(this);

        getInputtedStrings();
        getJsonObject();

        try {
            jsArray = jsonObject.getJSONArray("books");
        } catch (Exception e) {
            System.out.println("Parsing the JSON object to array fucked up");
        }

        parseJsonToBook(mBook);
    }
    
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        String member_name = rowItems.get(position).bookTitle;
        Toast.makeText(getApplicationContext(), "" + member_name,
                Toast.LENGTH_SHORT).show();
    }

    protected void getInputtedStrings(){
        courseTitle = getIntent().getStringExtra("keyTitle");
        selectedCourseTitle = getIntent().getStringExtra("keyTitle");
        selectedCourseYear = getIntent().getStringExtra("keyUniYear");
    }

    protected void getJsonObject(){
        try {
            jsonObject = new JSONObject(getIntent().getStringExtra("jsonObject"));
        } catch (Exception e) {
            System.out.println("Fucked up retrieving jsonObject from Intent");
        }
    }

    protected void parseJsonToBook(Books mBook){
        try {
            for (int i = 0; i < jsArray.length(); i++) {
                JSONObject jobj = jsArray.getJSONObject(i);

                mBook.jsonBookTitle.add(jobj.getString("book_title"));
                mBook.jsonBookAuthor.add(jobj.getString("book_author"));
                mBook.jsonUniCourse.add(jobj.getString("uni_course"));
                mBook.jsonBookPrice.add(jobj.getString("book_price"));
                mBook.jsonUniYear.add(jobj.getString("uni_year"));
                mBook.jsonBookIsbn.add(jobj.getString("isbn"));
                mBook.jsonBookYear.add(jobj.getString("book_year"));

                drawListRows(mBook, i);
            }
        } catch (Exception e) {
            System.out.println("Parsing JSON object to Book object fucked up");
        }
    }

    protected void drawListRows(Books mBook, int i){
        BrowseRowItem item = new BrowseRowItem(mBook.jsonBookTitle.get(i), mBook.jsonBookAuthor.get(i), mBook.jsonBookPrice.get(i), year_published[i]);
        rowItems.add(item);
    }
}
