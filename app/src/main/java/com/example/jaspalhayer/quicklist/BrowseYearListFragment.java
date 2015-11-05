package com.example.jaspalhayer.quicklist;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class BrowseYearListFragment extends Fragment {
    protected ListView lstView;
    protected String yearList[];
    protected String selectedCourseTitle;

    public BrowseYearListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        //Listen for changes in the back stack
        //Handle when activity is recreated like on orientation Change
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        selectedCourseTitle = getArguments().getString("bSelectedText");

        View rootView = inflater.inflate(R.layout.browse_uni_year, container, false);

        TextView courseTitle = (TextView)rootView.findViewById(R.id.courseVariable);
        courseTitle.setText(selectedCourseTitle);

        return rootView;
    }

    protected void drawYearList(final Context context, int i){
        ArrayAdapter<String> yearLstAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, android.R.id.text1, yearList);
        lstView.setAdapter(yearLstAdapter);
        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
                String selectedYear = (String) lstView.getItemAtPosition(position);
            }
        });
    }

}




