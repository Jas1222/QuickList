package com.example.jaspalhayer.quicklist;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class ViewListingActivity extends AppCompatActivity {
    CardView mMessageButton;
    TextView viewMessageButtonText;

    protected JSONArray jsArray = new JSONArray();
    protected Books mBook = new Books();
    protected JSONObject jsonObject;

    String titleListingNumber;

    TextView viewTitleText;
    TextView viewAuthorText;
    TextView viewYearText;
    TextView viewDescText;
    TextView viewPriceText;
    TextView viewIsbnText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listing);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        titleListingNumber = getIntent().getStringExtra("keyTitle");
        getSupportActionBar().setTitle("Listing #" + titleListingNumber);

        setVariablesToUiElements();
        getJsonObject();

        try {
            jsArray = jsonObject.getJSONArray("books");
        } catch (Exception e) {
            System.out.println("Parsing the JSON object to array fucked up");
        }

        parseJsonToBook(mBook);
        getListerFirstName(mBook);
        setValuesToVariables(mBook);

        mMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MessagingActivity.class);
                i.putExtra("USER_TO_MESSAGE_ID", mBook.userId.toString());
                i.putExtra("SENDER_NAME", mBook.bookFirstName.toString());
                startActivity(i);
            }
        });
    }

    protected void setVariablesToUiElements(){
        mMessageButton = (CardView)findViewById(R.id.view_message_button);
        viewMessageButtonText = (TextView)findViewById(R.id.view_card_text);
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
               // mBook.bookListId = jobj.getString("list_id");
                mBook.userId = jobj.getString("user_id");
            }
        } catch (Exception e) {
            System.out.println("Parsing JSON object to Book object fucked up");
        }
    }

    protected void setValuesToVariables(Books mBook){
        viewTitleText.setText(mBook.bookTitle);
        viewAuthorText.setText(mBook.bookAuthor);
        viewYearText.setText(mBook.bookYear);
        viewDescText.setText(mBook.bookDesc);
        viewPriceText.setText("£"+mBook.bookPrice);
        viewIsbnText.setText(mBook.bookIsbn);
        viewMessageButtonText.setText("Message " + mBook.bookFirstName);
    }

    protected void getListerFirstName(Books mBook){
        String listerFullName = mBook.bookFullName;
        if(listerFullName.contains(" ")){
            mBook.bookFirstName = listerFullName.substring(0, listerFullName.indexOf(" "));
        } else {
            mBook.bookFirstName = listerFullName;
        }
    }
}
