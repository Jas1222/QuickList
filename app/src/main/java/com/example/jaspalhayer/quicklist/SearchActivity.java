package com.example.jaspalhayer.quicklist;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SearchActivity extends AppCompatActivity {
    protected int mIsbn;
    protected String mBookTitle;
    protected String mAuthor;
    protected Button sButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sButton = (Button)findViewById(R.id.makeListingBtn);
        sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // launch GET query
            }
        });
    }



}
