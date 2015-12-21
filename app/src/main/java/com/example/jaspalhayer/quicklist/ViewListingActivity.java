package com.example.jaspalhayer.quicklist;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ViewListingActivity extends Activity {
    Button mMessageButton;

    String name = "Jaspal";
    String yearPublished = "2012";

    TextView viewTitleText;
    TextView viewAuthorText;
    TextView viewYearText;
    TextView viewDescText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listing);

        setVariablesToUiElements();

        mMessageButton.setText("Message "+name);
        mMessageButton.setPadding(60,10,60,10);
        viewTitleText.setText("This is a test I want to see how this looks on the device screen and if the content gets wrapped NOW LETS SEE WHAT HAPPENS");
        //viewAuthorText.setText("This is a test I want to see how this looks on the device screen and if the content gets wrapped NOW LETS SEE WHAT HAPPENS");
        viewYearText.setText("("+yearPublished+")");
    }

    protected void setVariablesToUiElements(){
        mMessageButton = (Button)findViewById(R.id.message_button);
        viewTitleText = (TextView)findViewById(R.id.view_title_text);
        viewAuthorText = (TextView)findViewById(R.id.view_author_text);
        viewYearText = (TextView)findViewById(R.id.view_year_text);
        viewDescText = (TextView)findViewById(R.id.view_desc_text);
    }
}
