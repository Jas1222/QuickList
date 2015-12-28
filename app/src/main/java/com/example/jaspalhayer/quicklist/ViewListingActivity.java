package com.example.jaspalhayer.quicklist;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class ViewListingActivity extends AppCompatActivity {
    Button mMessageButton;
    protected JSONArray jsArray = new JSONArray();
    protected Books mBook = new Books();
    protected JSONObject jsonObject;

    String name = "Jaspal";
    String yearPublished = "2012";

    TextView viewTitleText;
    TextView viewAuthorText;
    TextView viewYearText;
    TextView viewDescText;
    TextView viewPriceText;
    TextView viewIsbnText;
    TextView viewFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listing);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setVariablesToUiElements();

        getJsonObject();

        try {
            jsArray = jsonObject.getJSONArray("books");
        } catch (Exception e) {
            System.out.println("Parsing the JSON object to array fucked up");
        }

        parseJsonToBook(mBook);
        setValuesToVariables(mBook);
    }

    protected void setVariablesToUiElements(){
        mMessageButton = (Button)findViewById(R.id.message_button);
        viewTitleText = (TextView)findViewById(R.id.view_title_text);
        viewAuthorText = (TextView)findViewById(R.id.view_author_text);
        viewYearText = (TextView)findViewById(R.id.view_year_text);
        viewDescText = (TextView)findViewById(R.id.view_desc_text);
        viewPriceText = (TextView)findViewById(R.id.view_price_text);
        viewIsbnText = (TextView)findViewById(R.id.view_isbn_text);
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

                mBook.bookTitle = jobj.getString("book_title");
                mBook.bookAuthor = jobj.getString("book_author");
                mBook.bookPrice = jobj.getString("book_price");
                mBook.bookIsbn = jobj.getString("book_isbn");
                mBook.bookYear = jobj.getString("book_year");
                mBook.bookDesc = jobj.getString("book_desc");
                mBook.bookFullName = jobj.getString("full_name");
            }
        } catch (Exception e) {
            System.out.println("Parsing JSON object to Book object fucked up");
        }
    }


    protected void setValuesToVariables(Books mBook){
        mMessageButton.setText(mBook.bookFullName);
        viewTitleText.setText(mBook.bookTitle);
        viewAuthorText.setText(mBook.bookAuthor);
        viewYearText.setText(mBook.bookYear);
        viewDescText.setText(mBook.bookDesc);
        viewPriceText.setText("£"+mBook.bookPrice);
        viewIsbnText.setText(mBook.bookIsbn);
        mMessageButton.setText("Message "+mBook.bookFullName);
        mMessageButton.setPadding(40, 10, 40, 10);
    }
}
