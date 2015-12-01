package com.example.jaspalhayer.quicklist;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    List<BrowseRowItem> rowItems;
    ListView myListView;
    protected JSONObject jsonObject;
    protected JSONArray jsArray = new JSONArray();

    Books mBook = new Books();
    protected String titles[]={
            "Book 1",
            "Book 2",
            "Book 3",
            "Book 4"
    };

    protected String authors[] ={
            "Author 1",
            "Author 2",
            "Author 3",
            "Author 4"
    };

    protected String prices[]={
            "12",
            "13",
            "14",
            "15"
    };

    protected String dateListed[]={
            "10/10/10",
            "11/11/11",
            "12/12/12",
            "13/13/13"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rowItems = new ArrayList<>();

        BrowseCustomAdapter adapter = new BrowseCustomAdapter(this, rowItems);
        myListView = (ListView) findViewById(R.id.listView);
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(this);

        /* UNCOMMENT WHEN USING REAL DATA FROM DATABASE
        getJsonObject();

        try {
            jsArray = jsonObject.getJSONArray("books");
        } catch (Exception e) {
            System.out.println("Parsing the JSON object to array fucked up");
        }

        parseJsonToBook(mBook);
        */
        drawListRows();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        String member_name = rowItems.get(position).bookTitle;
        Toast.makeText(getApplicationContext(), "" + member_name,
                Toast.LENGTH_SHORT).show();
    }

    protected void drawListRows(){
        // ** WHEN USING REAL DATA, ADD mBook and i PARAMETERS **
        for(int i=0; i < titles.length; i++){
            BrowseRowItem item = new BrowseRowItem(titles[i], authors[i], prices[i], dateListed[i]);
            rowItems.add(item);
        }
    }

    private void getJsonObject(){
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

                // ** WHEN USING REAL DATA ADD PARAMETERS TO drawListRows(mBook, i) METHOD
                //drawListRows(mBook, i);
            }
        } catch (Exception e) {
            System.out.println("Parsing JSON object to Book object fucked up");
        }
    }}
