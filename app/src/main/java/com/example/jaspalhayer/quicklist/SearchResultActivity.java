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

public class SearchResultActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    List<BrowseRowItem> rowItems;
    ListView myListView;
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
        for(int i=0; i < titles.length; i++){
            BrowseRowItem item = new BrowseRowItem(titles[i], authors[i], prices[i], dateListed[i]);
            rowItems.add(item);
        }
    }
}
