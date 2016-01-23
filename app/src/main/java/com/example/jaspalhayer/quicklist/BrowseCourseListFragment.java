package com.example.jaspalhayer.quicklist;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONObject;

/**
 * Created by jaspalhayer on 05/11/2015.
 */
public class BrowseCourseListFragment extends Fragment {

    protected Spinner degreeTypeSpinner;
    protected Spinner courseSpinner;
    protected Spinner yearSpinner;
    protected JSONObject jsOb = new JSONObject();

    protected ListView lstView;
    protected CardView mLookup;
    protected String selectedCourse;
    protected String selectedYear;

    protected ImageView degreeTypeError;
    protected ImageView yearError;
    protected ImageView courseError;

    protected ConnectionHandler handler = new ConnectionHandler();

    protected boolean degreeTypeSet = false;
    protected boolean yearSet = false;
    protected boolean courseSet = false;

    protected boolean underGradCourse;
    protected boolean postGradCourse;

    protected ArrayAdapter<CharSequence> degreeTypeAdapter;
    protected ArrayAdapter<CharSequence> ugCourseAdapter;
    protected ArrayAdapter<CharSequence> pgCourseAdapter;
    protected ArrayAdapter<CharSequence> yearAdapter;
    protected ArrayAdapter<CharSequence> initialCourseAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.browse_uni_course, container, false);
        setVariablesToUiElements(rootView);
        createArrayAdapters();
        setSpinnerToAdapter();
        setAdapter();

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setValidYearField(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // AUTO-generated stub
            }
        });

        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setValidCourseField(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // AUTO-generated stub
            }
        });

        degreeTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setValidCourseTypeField(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // AUTO-generated stub
            }
        });

        mLookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    highlightInvalidFields();
                if (degreeTypeSet && courseSet && yearSet) {
                    handler.getCourseListings(getActivity().getApplicationContext(), selectedCourse, selectedYear, new ConnectionHandler.VolleyCallback() {
                        @Override
                        public void onSuccess(JSONObject result) {
                            jsOb = result;
                            Intent i = new Intent(getActivity(), ResultActivity.class);
                            i.putExtra("CAME_FROM", "browse");
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

    protected void createArrayAdapters(){
        degreeTypeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.course_type, R.layout.course_spinner_layout);
        ugCourseAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.undergrad_courses, R.layout.course_spinner_layout);
        pgCourseAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.postgrad_courses, R.layout.course_spinner_layout);
        yearAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.course_year_array, R.layout.course_spinner_layout);
        initialCourseAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.initial_course, R.layout.course_spinner_layout);
    }

    protected void setSpinnerToAdapter(){
        ugCourseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pgCourseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        degreeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        initialCourseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    protected void setAdapter(){
        yearSpinner.setAdapter(yearAdapter);
        courseSpinner.setAdapter(initialCourseAdapter);
        degreeTypeSpinner.setAdapter(degreeTypeAdapter);
    }

    protected void setValidCourseTypeField(int i){
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

    protected void setValidYearField(int i){
        if (i != 0) {
            yearSet = true;
            selectedYear = yearSpinner.getItemAtPosition(i).toString();
        } else {
            yearSet = false;
        }
    }

    protected void setValidCourseField(int i){
        if (i != 0) {
            courseSet = true;
            selectedCourse = courseSpinner.getItemAtPosition(i).toString();
        } else {
            courseSet = false;
        }
    }

    protected void setVariablesToUiElements(View rootView){
        lstView = (ListView)rootView.findViewById(R.id.courseList);
        degreeTypeSpinner = (Spinner)rootView.findViewById(R.id.degreeTypeSpinner);
        courseSpinner = (Spinner)rootView.findViewById(R.id.courseSpinner);
        yearSpinner = (Spinner)rootView.findViewById(R.id.yearSpinner);
        mLookup = (CardView) rootView.findViewById(R.id.mLookupButton);
        courseError = (ImageView)rootView.findViewById(R.id.course_error);
        degreeTypeError = (ImageView)rootView.findViewById(R.id.degree_type_error);
        yearError = (ImageView)rootView.findViewById(R.id.year_error);
    }

    protected void highlightInvalidFields(){
        if(!yearSet){
            yearError.setVisibility(View.VISIBLE);
        } else {
            yearError.setVisibility(View.INVISIBLE);
        }

        if(!courseSet){
            courseError.setVisibility(View.VISIBLE);
        } else {
            courseError.setVisibility(View.INVISIBLE);
        }

        if (!degreeTypeSet){
            degreeTypeError.setVisibility(View.VISIBLE);
        } else {
            degreeTypeError.setVisibility(View.INVISIBLE);
        }
    }
}