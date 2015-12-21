package com.example.jaspalhayer.quicklist;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;

public class ViewListingActivity extends Activity {
    Button mMessageButton;
    String name = "Jaspal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listing);

        setVariablesToUiElements();

        mMessageButton.setText("Message "+name);
        mMessageButton.setPadding(60,10,60,10);
    }

    protected void setVariablesToUiElements(){
        mMessageButton = (Button)findViewById(R.id.message_button);
    }
}
