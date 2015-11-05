package com.example.jaspalhayer.quicklist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class BrowseUniCourseActivity extends AppCompatActivity {
    protected static Spinner spinner;
    protected static ListView lstView;
    protected static String underGradCourses[];
    protected static String postGradCourses[];
    protected static String yearList[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse);

        final BrowseCourseList browseCourseList = new BrowseCourseList();

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, browseCourseList)
                    .commit();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        lstView = (ListView)findViewById(R.id.courseList);
//        spinner = (Spinner)findViewById(R.id.courseSpinner);
//
//        underGradCourses = getResources().getStringArray(R.array.undergrad_courses);
//        postGradCourses = getResources().getStringArray(R.array.postgrad_courses);
//        yearList = getResources().getStringArray(R.array.course_year_array);
//
//        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.course_type, R.layout.course_spinner_layout);
//
//        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(staticAdapter);
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if(i == 1) {
//                    browseCourseList.drawUndergradList(getApplicationContext(), i);
//                } else if (i == 2) {
//                    browseCourseList.drawPostGradList(getApplicationContext(), i);
//                    lstView = (ListView)findViewById(R.id.courseList);
//                    spinner = (Spinner)findViewById(R.id.courseSpinner);
//
//                    underGradCourses = getResources().getStringArray(R.array.undergrad_courses);
//                    postGradCourses = getResources().getStringArray(R.array.postgrad_courses);
//                } else {
//                    // DO nothing
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                // AUTO-generated stub
//            }
//        });
    }

//    public class browseCourseList extends Fragment {
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            // Inflate the layout for this fragment
//            View rootView = inflater.inflate(R.layout.browse_uni_course, container, false);
//            lstView = (ListView)rootView.findViewById(R.id.courseList);
//            spinner = (Spinner)rootView.findViewById(R.id.courseSpinner);
//
//            underGradCourses = getResources().getStringArray(R.array.undergrad_courses);
//            postGradCourses = getResources().getStringArray(R.array.postgrad_courses);
//            ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.course_type, R.layout.course_spinner_layout);
//
//            staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spinner.setAdapter(staticAdapter);
//            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                    if (i == 1) {
//                        drawUndergradList(i);
//                    } else if (i == 2) {
//                        drawPostGradList(i);
//                        lstView = (ListView) findViewById(R.id.courseList);
//                        spinner = (Spinner) findViewById(R.id.courseSpinner);
//
//                        underGradCourses = getResources().getStringArray(R.array.undergrad_courses);
//                        postGradCourses = getResources().getStringArray(R.array.postgrad_courses);
//                    } else {
//                        // DO nothing
//                    }
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
//                    // AUTO-generated stub
//                }
//            });
//
//            return rootView;
//        }
//    }
//
//    public static class browseYearList extends Fragment {
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            // Inflate the layout for this fragment
//            return inflater.inflate(R.layout.browse_uni_year, container, false);
//        }
//    }
//
//
//    protected void drawPostGradList(int i){
//        if (i == 2) {
//            ArrayAdapter<String> pgLstAdapter = new ArrayAdapter<String>(BrowseUniCourseActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, postGradCourses);
//            lstView.setAdapter(pgLstAdapter);
//            lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                    int itemPosition = position;
//
//                    // Gets the course string
//                    String selectedCourse = (String) lstView.getItemAtPosition(position);
//                    Toast.makeText(getApplicationContext(), itemPosition + selectedCourse, Toast.LENGTH_LONG).show();
//
//                }
//            });
//        }
//    }
//
//    protected void drawUndergradList(int i){
//        if(i == 1) {
//            ArrayAdapter<String> ugLstAdapter = new ArrayAdapter<String>(BrowseUniCourseActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, underGradCourses);
//            lstView.setAdapter(ugLstAdapter);
//            lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                    int itemPosition = position;
//
//                    // Gets the course string
//                    String selectedCourse = (String) lstView.getItemAtPosition(position);
//                    Toast.makeText(getApplicationContext(), itemPosition + selectedCourse, Toast.LENGTH_LONG).show();
//                }
//            });
//        }
//    }
}
