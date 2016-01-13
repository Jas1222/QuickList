package com.example.jaspalhayer.quicklist;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyListingsFragment extends Fragment implements AdapterView.OnItemClickListener{

    List<ListingRowItem> rowItems;
    ListView myListView;
    ConnectionHandler handler;
    JSONObject jsOb;
    JSONArray jsArray;
    Books mBook;
    JSONObject jsonObject;


    public MyListingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        handler = new ConnectionHandler();

        handler.getUserListings(getActivity().getApplicationContext(), );

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_browse_result, container, false);

        rowItems = new ArrayList<>();
        jsOb = new JSONObject();

        BrowseCustomAdapter adapter = new BrowseCustomAdapter(getActivity().getApplicationContext(), rowItems);

        myListView = (ListView)rootView.findViewById(R.id.listView);
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(this);

        getJsonObject();

        try {
            jsArray = jsonObject.getJSONArray("books");
        } catch (Exception e) {
            System.out.println("Parsing the JSON object to array fucked up");
        }

        parseJsonToBook(mBook);


        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // Below gets the selected listing details and sends it to the 'view detail listing' and launches it
        final String member_list_id = rowItems.get(position).bookListId;
        handler.getCourseListingsDetails(getActivity().getApplicationContext(), member_list_id, new ConnectionHandler.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                jsOb = result;
                Intent i = new Intent(getActivity().getApplicationContext(), ViewListingActivity.class);
                i.putExtra("keyTitle", member_list_id);
                i.putExtra("jsonObject", jsOb.toString());
                startActivity(i);
            }
        });
    }

    protected void getJsonObject(){
        //*** REFACTOR ***//
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

                mBook.jsonBookTitle.add(jobj.getString("book_title"));
                mBook.jsonBookAuthor.add(jobj.getString("book_author"));
                mBook.jsonBookPrice.add(jobj.getString("book_price"));
                mBook.jsonBookIsbn.add(jobj.getString("book_isbn"));
                mBook.jsonBookYear.add(jobj.getString("book_year"));
                mBook.jsonBookListId.add(jobj.getString("list_id"));

                drawListRows(mBook, i);
            }
        } catch (Exception e) {
            System.out.println("Parsing JSON object to Book object fucked up");
        }
    }

    protected void drawListRows(Books mBook, int i){
        ListingRowItem item = new ListingRowItem(mBook.jsonBookTitle.get(i), mBook.jsonBookAuthor.get(i), mBook.jsonBookPrice.get(i), mBook.jsonBookYear.get(i), mBook.jsonBookListId.get(i));
        rowItems.add(item);
    }

}
