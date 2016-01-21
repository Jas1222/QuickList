package com.example.jaspalhayer.quicklist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

public class CreateBookInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Bundle create2Bundle;

    JSONObject jObj;

    EditText titleField;
    EditText authorField;
    EditText yearField;
    EditText descField;
    EditText priceField;
    EditText isbnField;

    Books mBook;
    CardView mNext2;

    protected static String bookTitle;
    protected static String bookAuthor;
    protected static String bookYear;
    protected static String bookDesc;
    protected static String bookPrice;
    protected static String bookIsbn;


    public CreateBookInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBook = new Books();

        if (getArguments() != null) {
            create2Bundle = this.getArguments();
            if (doesScanJsonObjectExist()) {
                getExistingJsonObject();
                parseJsonObject();
            }
        } else {
            create2Bundle = new Bundle();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_create_fragment2, container, false);

        setVariablesToUiElements(rootView);

        mNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CreateConfirmFragment createFragment3 = new CreateConfirmFragment();
                CreateCourseInfoFragment createFragment3 = new CreateCourseInfoFragment();
                saveInputText();
                setBundleValues();

                createFragment3.setArguments(create2Bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();
                fragmentTransaction.replace(R.id.create_main_container, createFragment3).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        if (doesScanJsonObjectExist()) {
            setTextFromJsonScan();
        }

        return rootView;
    }

    public void setVariablesToUiElements(View rootView) {
        mNext2 = (CardView) rootView.findViewById(R.id.create_next_2_btn);

        titleField = (EditText) rootView.findViewById(R.id.create_book_title_field);
        authorField = (EditText) rootView.findViewById(R.id.create_book_author_field);
        yearField = (EditText) rootView.findViewById(R.id.create_book_year_field);
        isbnField = (EditText) rootView.findViewById(R.id.create_ISBN_field);
        priceField = (EditText) rootView.findViewById(R.id.create_book_price_field);
        descField = (EditText) rootView.findViewById(R.id.create_description_field);
    }

    protected void setBundleValues() {
        create2Bundle.putString("KEY_TITLE", bookTitle);
        create2Bundle.putString("KEY_AUTHOR", bookAuthor);
        create2Bundle.putString("KEY_YEAR", bookYear);
        create2Bundle.putString("KEY_PRICE", bookPrice);
        create2Bundle.putString("KEY_DESC", bookDesc);
        create2Bundle.putString("KEY_ISBN", bookIsbn);
    }

    protected void saveInputText() {
        bookTitle = titleField.getText().toString();
        bookAuthor = authorField.getText().toString();
        bookYear = yearField.getText().toString();
        bookIsbn = isbnField.getText().toString();
        bookPrice = priceField.getText().toString();
        bookDesc = descField.getText().toString();
    }

    protected boolean doesScanJsonObjectExist() {
        if (create2Bundle.getString("JSON_STRING_OBJECT") != null) {
            getExistingJsonObject();
            return true;
        } else {
            return false;
        }
    }

    protected void getExistingJsonObject() {
        try {
            jObj = new JSONObject(create2Bundle.getString("JSON_STRING_OBJECT"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void parseJsonObject() {
        try {
            JSONArray jArray = jObj.getJSONArray("items");
            JSONObject volumeInfo = jArray.getJSONObject(0).getJSONObject("volumeInfo");

            JSONArray jsonIndustrialArray = volumeInfo.getJSONArray("industryIdentifiers");

            JSONArray authorArray = volumeInfo.getJSONArray("authors");

            StringBuilder authorBuild = new StringBuilder("");

            //Appends all authors to a string
            for(int a=0; a<authorArray.length(); a++){
                    authorBuild.append(authorArray.getString(a));
                if(authorArray.length()>1) {
                    authorBuild.append(", ");
                }
            }

            //Looks for ISBN in 13 digit format and passes to it
            for(int k = 0; k < jsonIndustrialArray.length(); k++) {
                JSONObject isbn = jsonIndustrialArray.getJSONObject(k);
                if (isbn.getString("type").equals("ISBN_13")) {
                    mBook.bookIsbn = isbn.getString("identifier");
                }

            mBook.bookTitle = volumeInfo.getString("title");
            mBook.bookAuthor = authorBuild.toString();
            mBook.bookYear = volumeInfo.getString("publishedDate");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setTextFromJsonScan() {
        titleField.setText(mBook.bookTitle);
        authorField.setText(mBook.bookAuthor);
        yearField.setText(mBook.bookYear);
        isbnField.setText(mBook.bookIsbn);
    }
}
