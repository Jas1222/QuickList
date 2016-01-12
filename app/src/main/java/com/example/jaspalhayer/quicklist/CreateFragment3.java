package com.example.jaspalhayer.quicklist;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class CreateFragment3 extends Fragment {

    protected static String bookTitle;
    protected static String bookAuthor;
    protected static String bookYear;
    protected static String bookDesc;
    protected static String bookPrice;
    protected static String bookIsbn;

    protected static String courseType;
    protected static String courseDegree;
    protected static String courseYear;

    TextView bookTitleLabel;
    TextView bookAuthorLabel;
    TextView bookYearLabel;
    TextView bookIsbnLabel;
    TextView bookDescLabel;
    TextView bookPriceLabel;

    TextView courseTypeLabel;
    TextView courseDegreeLabel;
    TextView courseYearLabel;

    protected CardView mSubmit;


    Bundle create3Bundle;

    public CreateFragment3() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        ConnectionHandler handler = new ConnectionHandler();

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            create3Bundle = this.getArguments();
            getBundleStrings();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_create_fragment3, container, false);

        setVariablesToUi(rootView);
        setLabelText();


        return rootView;
    }

    private void setLabelText(){
        bookTitleLabel.setText("Title: "+bookTitle);
        bookAuthorLabel.setText("Author: "+bookAuthor);
        bookYearLabel.setText("Price: "+bookYear);
        bookIsbnLabel.setText("ISBN: "+bookIsbn);
        bookDescLabel.setText("Description "+bookDesc);
        bookPriceLabel.setText("Price: "+bookPrice);

        courseDegreeLabel.setText("Course: "+courseDegree);
        courseTypeLabel.setText("Type: " +courseType);
        courseYearLabel.setText("Year: "+courseYear);
    }

    private void setVariablesToUi(View rootView){
        bookTitleLabel = (TextView)rootView.findViewById(R.id.create_book_title_label);
        bookAuthorLabel = (TextView)rootView.findViewById(R.id.create_book_author_label);
        bookYearLabel = (TextView)rootView.findViewById(R.id.create_book_year_label);
        bookIsbnLabel = (TextView)rootView.findViewById(R.id.create_book_isbn_label);
        bookDescLabel = (TextView)rootView.findViewById(R.id.create_book_description_label);
        bookPriceLabel = (TextView)rootView.findViewById(R.id.create_book_price_label);

        courseDegreeLabel = (TextView)rootView.findViewById(R.id.create_degree_course_label);
        courseTypeLabel = (TextView)rootView.findViewById(R.id.create_degree_type_label);
        courseYearLabel = (TextView)rootView.findViewById(R.id.create_degree_year_label);

        mSubmit = (CardView)rootView.findViewById(R.id.create_next_3_btn);

    }

    private void getBundleStrings(){
        courseType = create3Bundle.getString("COURSE_TYPE");
        courseDegree = create3Bundle.getString("COURSE_DEGREE");
        courseYear = create3Bundle.getString("COURSE_YEAR");

        bookAuthor = create3Bundle.getString("KEY_AUTHOR");
        bookYear = create3Bundle.getString("KEY_YEAR");
        bookIsbn = create3Bundle.getString("KEY_ISBN");
        bookPrice = create3Bundle.getString("KEY_PRICE");
        bookDesc = create3Bundle.getString("KEY_DESC");
        bookTitle = create3Bundle.getString("KEY_TITLE");
    }
}
