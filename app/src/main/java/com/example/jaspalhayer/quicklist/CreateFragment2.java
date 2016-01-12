package com.example.jaspalhayer.quicklist;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class CreateFragment2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Bundle create2Bundle;

    EditText titleField;
    EditText authorField;
    EditText yearField;
    EditText descField;
    EditText priceField;
    EditText isbnField;

    CardView mNext2;

    protected static String bookTitle;
    protected static String bookAuthor;
    protected static String bookYear;
    protected static String bookDesc;
    protected static String bookPrice;
    protected static String bookIsbn;

    public CreateFragment2() {
        // Required empty public constructor
    }

//    // TODO: Rename and change types and number of parameters
//    public static CreateFragment2 newInstance(String param1, String param2) {
//        CreateFragment2 fragment = new CreateFragment2();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
        create2Bundle = this.getArguments();
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
                CreateFragment3 createFragment3 = new CreateFragment3();

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

        return rootView;
    }

    public void setVariablesToUiElements(View rootView){
        mNext2 = (CardView)rootView.findViewById(R.id.create_next_2_btn);

        titleField = (EditText)rootView.findViewById(R.id.create_book_title_field);
        authorField = (EditText)rootView.findViewById(R.id.create_book_author_field);
        yearField = (EditText)rootView.findViewById(R.id.create_book_year_field);
        isbnField = (EditText)rootView.findViewById(R.id.create_ISBN_field);
        priceField = (EditText)rootView.findViewById(R.id.create_book_price_field);
        descField = (EditText)rootView.findViewById(R.id.create_description_field);
    }

    protected void setBundleValues(){
        create2Bundle.putString("KEY_TITLE", bookTitle);
        create2Bundle.putString("KEY_AUTHOR", bookAuthor);
        create2Bundle.putString("KEY_YEAR", bookYear);
        create2Bundle.putString("KEY_PRICE", bookPrice);
        create2Bundle.putString("KEY_DESC", bookDesc);
        create2Bundle.putString("KEY_ISBN", bookIsbn);
    }

    protected void saveInputText(){
        bookTitle = titleField.getText().toString();
        bookAuthor = authorField.getText().toString();
        bookYear = yearField.getText().toString();
        bookIsbn = isbnField.getText().toString();
        bookPrice = priceField.getText().toString();
        bookDesc = descField.getText().toString();
    }
}
