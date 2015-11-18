package com.example.jaspalhayer.quicklist;

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
    protected ConnectionHandler handler = new ConnectionHandler();
    protected String year_published[] = {
            "2012",
            "1985"
    };


    List<BrowseRowItem> rowItems;
    ListView myListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Books mBook = new Books();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        try {
            jsonObject = new JSONObject(getIntent().getStringExtra("jsonObject"));
        } catch (Exception e) {
            System.out.println("Fucked up retrieving jsonObject from Intent");
        }

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
            jsArray = jsonObject.getJSONArray("books");
        } catch (Exception e) {
            System.out.println("Parsing the JSON object to array fucked up");
        }

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

                BrowseRowItem item = new BrowseRowItem(mBook.jsonBookTitle.get(i), mBook.jsonBookAuthor.get(i), mBook.jsonBookPrice.get(i), year_published[i]);
                rowItems.add(item);
            }
        } catch (Exception e) {
            System.out.println("Parsing JSON object to Book object fucked up");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        String member_name = rowItems.get(position).bookTitle;
        Toast.makeText(getApplicationContext(), "" + member_name,
                Toast.LENGTH_SHORT).show();
    }

}
