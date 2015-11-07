package com.example.jaspalhayer.quicklist;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BrowseResultActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    protected String[] book_titles;
    protected String[] book_authors;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rowItems = new ArrayList<BrowseRowItem>();

        book_titles = getResources().getStringArray(R.array.testBookTitles);
        book_authors = getResources().getStringArray(R.array.testBookAuthor);
        dates_listed = getResources().getStringArray(R.array.testDateListed);

        for (int i = 0; i < book_titles.length; i++){
            BrowseRowItem item = new BrowseRowItem(book_titles[i], book_authors[i], book_prices[i], dates_listed[i]);
            rowItems.add(item);
        }

        myListView = (ListView)findViewById(R.id.listView);
        BrowseCustomAdapter adapter = new BrowseCustomAdapter(this, rowItems);
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        String member_name = rowItems.get(position).bookTitle;
        Toast.makeText(getApplicationContext(), "" + member_name,
                Toast.LENGTH_SHORT).show();
    }

}
