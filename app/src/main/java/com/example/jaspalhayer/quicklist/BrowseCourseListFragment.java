package com.example.jaspalhayer.quicklist;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.ParseError;

import org.json.JSONObject;

import java.lang.reflect.Array;

/**
 * Created by jaspalhayer on 05/11/2015.
 */
public class BrowseCourseListFragment extends Fragment {

    protected Spinner degreeTypeSpinner;
    protected Spinner courseSpinner;
    protected Spinner yearSpinner;
    protected JSONObject jsOb = new JSONObject();

    protected ListView lstView;
    protected Button mLookup;
    protected String selectedCourse;
    protected String selectedYear;

    protected ConnectionHandler handler = new ConnectionHandler();

    protected boolean degreeTypeSet = false;
    protected boolean yearSet = false;
    protected boolean courseSet = false;

    protected boolean underGradCourse;
    protected boolean postGradCourse;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.browse_uni_course, container, false);
        lstView = (ListView)rootView.findViewById(R.id.courseList);
        degreeTypeSpinner = (Spinner)rootView.findViewById(R.id.degreeTypeSpinner);
        courseSpinner = (Spinner)rootView.findViewById(R.id.courseSpinner);
        yearSpinner = (Spinner)rootView.findViewById(R.id.yearSpinner);
        mLookup = (Button)rootView.findViewById(R.id.makeListingBtn);

        final ArrayAdapter<CharSequence> degreeTypeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.course_type, R.layout.course_spinner_layout);
        final ArrayAdapter<CharSequence> ugCourseAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.undergrad_courses, R.layout.course_spinner_layout);
        final ArrayAdapter<CharSequence> pgCourseAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.postgrad_courses, R.layout.course_spinner_layout);
        final ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.course_year_array, R.layout.course_spinner_layout);
        final ArrayAdapter<CharSequence> initialCourseAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.initial_course, R.layout.course_spinner_layout);

        ugCourseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pgCourseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        degreeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        initialCourseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        yearSpinner.setAdapter(yearAdapter);
        courseSpinner.setAdapter(initialCourseAdapter);
        degreeTypeSpinner.setAdapter(degreeTypeAdapter);

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    yearSet = true;
                    selectedYear = yearSpinner.getItemAtPosition(i).toString();
                } else {
                    yearSet = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // AUTO-generated stub
            }
        });

        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    courseSet = true;
                    selectedCourse = courseSpinner.getItemAtPosition(i).toString();
                } else {
                    courseSet = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // AUTO-generated stub
            }
        });

        degreeTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    degreeTypeSet = true;
                    underGradCourse = true;

                    courseSpinner.setAdapter(ugCourseAdapter);
                } else if (i == 2) {
                    degreeTypeSet = true;
                    postGradCourse = true;

                    courseSpinner.setAdapter(pgCourseAdapter);

                } else {
                    degreeTypeSet = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // AUTO-generated stub
            }
        });

        mLookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (degreeTypeSet == true && courseSet == true && yearSet == true) {
                    handler.getCourseListingTest(getActivity().getApplicationContext(), selectedCourse, selectedYear, new ConnectionHandler.VolleyCallback() {
                        @Override
                        public void onSuccess(JSONObject result) {
                            jsOb = result;
                            Intent i = new Intent(getActivity(), BrowseResultActivity.class);
                            i.putExtra("keyTitle", selectedCourse);
                            i.putExtra("jsonObject", jsOb.toString());
                            startActivity(i);
                        }
                    });
                } else {
                    Snackbar.make(getView(), "Ensure you have selected all fields", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        return rootView;
    }


}