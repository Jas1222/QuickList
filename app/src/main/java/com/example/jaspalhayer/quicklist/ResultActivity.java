package com.example.jaspalhayer.quicklist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    protected JSONArray jsArray = new JSONArray();
    protected JSONObject jsonObject;
    protected String courseTitle;
    protected String selectedCourseYear;
    protected String selectedCourseTitle;
    protected String cameFrom;

    boolean cameFromExpired = false;
    boolean cameFromCompleted = false;
    boolean cameFromBrowse = false;
    boolean cameFromActive = false;

    ConnectionHandler handler = new ConnectionHandler();
    Books mBook = new Books();
    JSONObject jsOb = new JSONObject();
    BrowseCustomAdapter adapter;
    List<ListingRowItem> rowItems;
    ListView myListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_result);
        cameFrom();
        getPreviousStringsAndSetTitle();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rowItems = new ArrayList<>();

        adapter = new BrowseCustomAdapter(this, rowItems);
        myListView = (ListView) findViewById(R.id.listView);
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(this);

        registerForContextMenu(myListView);

        getJsonObject();
        convertJsonObjectToArray();
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

    @Override
    public void onCreateContextMenu(final ContextMenu menu,
                                    final View v, final ContextMenu.ContextMenuInfo menuInfo) {
        if (cameFromActive) {
            if (v.getId() == R.id.listView) {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.listing_menu, menu);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final String member_list_id = rowItems.get(info.position).bookListId;
        switch (item.getItemId()) {
            case R.id.listing_edit:
                return true;
            case R.id.listing_delete:
                handler.deleteListing(getApplicationContext(), member_list_id);
                myListView.invalidateViews();
                // edit stuff here
                return true;
            case R.id.listing_completed:
                handler.markListingAsCompleted(getApplicationContext(), member_list_id);
                myListView.invalidateViews();

                // edit stuff here
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    protected void getPreviousStringsAndSetTitle() {
        if (cameFromBrowse) {
            courseTitle = getIntent().getStringExtra("keyTitle");
            selectedCourseTitle = getIntent().getStringExtra("keyTitle");
            selectedCourseYear = getIntent().getStringExtra("keyUniYear");

            getSupportActionBar().setTitle(courseTitle);
        } else if (cameFromActive) {
            getSupportActionBar().setTitle("My Listings");
        } else if (cameFromCompleted) {
            getSupportActionBar().setTitle("My Completed Listings");
        } else if (cameFromExpired) {
            getSupportActionBar().setTitle("My Expired Listings");
        }
    }

    protected void cameFrom() {
        cameFrom = getIntent().getStringExtra("CAME_FROM");
        if (cameFrom.contains("active")) {
            cameFromActive = true;
        } else if (cameFrom.contains("expired")) {
            cameFromExpired = true;
        } else if (cameFrom.contains("complete")) {
            cameFromCompleted = true;
        } else if (cameFrom.contains("browse")) {
            cameFromBrowse = true;
        } else {
            System.out.println("Can't tell where came from");
        }
    }

    protected void getJsonObject() {
        try {
            jsonObject = new JSONObject(getIntent().getStringExtra("jsonObject"));
        } catch (Exception e) {
            System.out.println("Fucked up retrieving jsonObject from Intent");
        }
    }

    protected void convertJsonObjectToArray() {
        try {
            jsArray = jsonObject.getJSONArray("books");
        } catch (Exception e) {
            System.out.println("Casting the JSON object to array fucked up");
        }
    }

    protected void parseJsonToBook(Books mBook) {
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

    protected void drawListRows(Books mBook, int i) {
        ListingRowItem item = new ListingRowItem(mBook.jsonBookTitle.get(i), mBook.jsonBookAuthor.get(i), mBook.jsonBookPrice.get(i), mBook.jsonBookYear.get(i), mBook.jsonBookListId.get(i));
        rowItems.add(item);
    }
}
