package com.example.jaspalhayer.quicklist;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;


public class CreateFragment1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    CardView mNextButton1;

    protected String selectedCourse;
    protected String selectedCourseType;
    protected String selectedYear;

    protected ListView lstView;

    protected Spinner degreeTypeSpinner;
    protected Spinner courseSpinner;
    protected Spinner yearSpinner;

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


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateFragment1 newInstance(String param1, String param2) {
        CreateFragment1 fragment = new CreateFragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public CreateFragment1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_create_fragment1, container, false);

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

        mNextButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (degreeTypeSet && courseSet && yearSet) {
                    // Capture all selected options
                    // Pass selection options to next fragment

                    CreateFragment2 createFragment2 = new CreateFragment2();
                    Bundle create1Bundle = new Bundle();

                    create1Bundle.putString("COURSE_TYPE", selectedCourseType);
                    create1Bundle.putString("COURSE_DEGREE", selectedCourse);
                    create1Bundle.putString("COURSE_YEAR", selectedYear);

                    createFragment2.setArguments(create1Bundle);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager
                            .beginTransaction();
                    fragmentTransaction.replace(R.id.create_main_container, createFragment2).addToBackStack(null);
                    fragmentTransaction.commit();

                } else {
                    Snackbar.make(getView(), "Ensure you have entered all fields", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        return rootView;
    }


    private void setVariablesToUiElements(View rootView){
        lstView = (ListView)rootView.findViewById(R.id.courseList);
        degreeTypeSpinner = (Spinner)rootView.findViewById(R.id.create_degree_type_spinner);
        courseSpinner = (Spinner)rootView.findViewById(R.id.create_course_spinner);
        yearSpinner = (Spinner)rootView.findViewById(R.id.create_year_spinner);
        mNextButton1 = (CardView)rootView.findViewById(R.id.create_next_1_btn);
    }

    protected void createArrayAdapters(){
        degreeTypeAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.course_type, R.layout.course_spinner_layout_create);
        ugCourseAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.undergrad_courses, R.layout.course_spinner_layout_create);
        pgCourseAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.postgrad_courses, R.layout.course_spinner_layout_create);
        yearAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.course_year_array, R.layout.course_spinner_layout_create);
        initialCourseAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.initial_course, R.layout.course_spinner_layout_create);
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

        selectedCourseType = courseSpinner.getItemAtPosition(i).toString();

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
}
