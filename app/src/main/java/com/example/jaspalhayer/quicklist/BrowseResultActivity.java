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

    ConnectionHandler handler = new ConnectionHandler();
    Books mBook = new Books();
    JSONObject jsOb = new JSONObject();


    //TODO Get real dates from database
    protected String year_published[] = {
            "2012",
            "1985"
    };

    List<ListingRowItem> rowItems;
    ListView myListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_result);
        getPreviousUserInputStrings();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(courseTitle);
        rowItems = new ArrayList<>();

        BrowseCustomAdapter adapter = new BrowseCustomAdapter(this, rowItems);
        myListView = (ListView) findViewById(R.id.listView);
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(this);

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
        final String member_list_id = rowItems.get(position).bookListId;
        handler.getCourseListingsDetails(getApplicationContext(), member_list_id, new ConnectionHandler.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                jsOb = result;
                Intent i = new Intent(getApplicationContext(), ViewListingActivity.class);
                i.putExtra("keyTitle", member_list_id);
                i.putExtra("jsonObject", jsOb.toString());
                startActivity(i);
            }
        });
    }

    protected void getPreviousUserInputStrings(){
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
                mBook.jsonBookPrice.add(jobj.getString("book_price"));
                mBook.jsonBookIsbn.add(jobj.getString("book_isbn"));
                mBook.jsonBookYear.add(jobj.getString("book_year"));
                mBook.jsonBookListId.add(jobj.getString("list_id"));

                drawListRows(mBook, i);
            }
        } catch (Exception e) {
            System.out.println("Parsing JSON object to Book object fucked up");
        }
    }

    protected void drawListRows(Books mBook, int i){
        ListingRowItem item = new ListingRowItem(mBook.jsonBookTitle.get(i), mBook.jsonBookAuthor.get(i), mBook.jsonBookPrice.get(i), year_published[i], mBook.jsonBookListId.get(i));
        rowItems.add(item);
    }
}
