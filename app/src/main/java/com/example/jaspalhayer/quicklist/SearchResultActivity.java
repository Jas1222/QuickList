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

public class SearchResultActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    List<ListingRowItem> rowItems;
    ListView myListView;
    protected JSONObject jsonObject;
    protected JSONObject jsOb = new JSONObject();
    protected JSONArray jsArray = new JSONArray();
    ConnectionHandler handler = new ConnectionHandler();

    Books mBook = new Books();

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

        getJsonObject();

        try {
            jsArray = jsonObject.getJSONArray("listing");
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

    protected void drawListRows(Books mBook, int i){
        ListingRowItem item = new ListingRowItem(mBook.jsonBookTitle.get(i), mBook.jsonBookAuthor.get(i), mBook.jsonBookPrice.get(i), dateListed[i], mBook.jsonBookListId.get(i));
        rowItems.add(item);
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
                mBook.jsonBookYear.add(jobj.getString("book_year"));

                drawListRows(mBook, i);
            }
        } catch (Exception e) {
            System.out.println("Parsing JSON object to Book object fucked up");
        }
    }}
