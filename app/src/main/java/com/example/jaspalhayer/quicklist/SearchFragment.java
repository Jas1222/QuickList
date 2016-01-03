package com.example.jaspalhayer.quicklist;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    protected Button sButton;
    protected EditText field_isbn;
    protected EditText field_title;
    protected EditText field_author;
    protected String input_isbn;
    protected String input_title;
    protected String input_author;

    JSONObject jsOb = new JSONObject();
    ConnectionHandler handler = new ConnectionHandler();

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_activity, container, false);

        setVariablesToUiElements(rootView);
        sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInputText();
                if(validInputFields()) {
                    handler.searchListings(getActivity().getApplicationContext(), input_isbn, input_title, input_author, new ConnectionHandler.VolleyCallback() {
                        @Override
                        public void onSuccess(JSONObject result) {
                            jsOb = result;
                            Intent i = new Intent(getActivity().getApplicationContext(), SearchResultActivity.class);
                            i.putExtra("jsonObject", jsOb.toString());

                            startActivity(i);
                        }
                    });
                } else {
                    Snackbar.make(view, "Ensure you have selected all fields", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        return rootView;
    }

    private void saveUserInputText(){
        input_isbn = field_isbn.getText().toString();
        input_title = field_title.getText().toString();
        input_author = field_author.getText().toString();
    }

    private void setVariablesToUiElements(View rootView){
        field_isbn = (EditText)rootView.findViewById(R.id.search_isbn_text);
        field_title = (EditText)rootView.findViewById(R.id.search_title_text);
        field_author = (EditText)rootView.findViewById(R.id.search_author_text);
        sButton = (Button)rootView.findViewById(R.id.search_button);

    }

    private boolean validInputFields(){
        if (input_title.isEmpty() && input_isbn.isEmpty() && input_author.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

}
