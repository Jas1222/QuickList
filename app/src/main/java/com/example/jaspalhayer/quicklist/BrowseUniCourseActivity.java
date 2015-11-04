package com.example.jaspalhayer.quicklist;

import android.app.Fragment;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class BrowseUniCourseActivity extends AppCompatActivity {
    protected Spinner spinner;
    protected ListView lstView;
    protected String underGradCourses[];
    protected String postGradCourses[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_uni_course);

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new fragmentTest())
                    .commit();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lstView = (ListView)findViewById(R.id.courseList);
        spinner = (Spinner)findViewById(R.id.courseSpinner);

        underGradCourses = getResources().getStringArray(R.array.undergrad_courses);
        postGradCourses = getResources().getStringArray(R.array.postgrad_courses);

        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.course_type, R.layout.course_spinner_layout);

        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(staticAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 1) {
                    drawUndergradList(i);
                } else if (i == 2) {
                    drawPostGradList(i);
                } else {
                    // DO nothing
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // AUTO-generated stub
            }
        });
    }

    public static class fragmentTest extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.test_layout, container, false);
        }
    }


    protected void drawPostGradList(int i){
        if (i == 2) {
            ArrayAdapter<String> pgLstAdapter = new ArrayAdapter<String>(BrowseUniCourseActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, postGradCourses);
            lstView.setAdapter(pgLstAdapter);
            lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    int itemPosition = position;

                    // Gets the course string
                    String selectedCourse = (String) lstView.getItemAtPosition(position);
                    Toast.makeText(getApplicationContext(), itemPosition + selectedCourse, Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    protected void drawUndergradList(int i){
        if(i == 1) {
            ArrayAdapter<String> ugLstAdapter = new ArrayAdapter<String>(BrowseUniCourseActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, underGradCourses);
            lstView.setAdapter(ugLstAdapter);
            lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    int itemPosition = position;

                    // Gets the course string
                    String selectedCourse = (String) lstView.getItemAtPosition(position);
                    Toast.makeText(getApplicationContext(), itemPosition + selectedCourse, Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
