package com.example.jaspalhayer.quicklist;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jaspalhayer on 05/11/2015.
 */
public class BrowseCourseListFragment extends Fragment {

    protected Spinner spinner;
    protected ListView lstView;
    protected String underGradCourses[];
    protected String postGradCourses[];


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.browse_uni_course, container, false);
        lstView = (ListView)rootView.findViewById(R.id.courseList);
        spinner = (Spinner)rootView.findViewById(R.id.courseSpinner);

        underGradCourses = getResources().getStringArray(R.array.undergrad_courses);
        postGradCourses = getResources().getStringArray(R.array.postgrad_courses);

        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.course_type, R.layout.course_spinner_layout);

        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(staticAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    drawUndergradList(getActivity(), i);

                } else if (i == 2) {
                    drawPostGradList(getActivity(), i);
                } else {
                    // DO nothing
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // AUTO-generated stub
            }
        });

        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return rootView;
    }

    protected void drawPostGradList(final Context context, int i){
        if (i == 2) {
            ArrayAdapter<String> pgLstAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, android.R.id.text1, postGradCourses);
            lstView.setAdapter(pgLstAdapter);
            lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    int itemPosition = position;

                    // Gets the course string
                    String selectedCourse = (String) lstView.getItemAtPosition(position);
                    Toast.makeText(context, selectedCourse, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    protected void drawUndergradList(final Context context, int i){
        if(i == 1) {
            ArrayAdapter<String> ugLstAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, android.R.id.text1, underGradCourses);
            lstView.setAdapter(ugLstAdapter);
            lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    int itemPosition = position;

                    // Gets the course string
                    String selectedCourse = (String) lstView.getItemAtPosition(position);
                    Toast.makeText(context, selectedCourse, Toast.LENGTH_LONG).show();

                    Fragment fragment = new BrowseYearListFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("bSelectedText", selectedCourse);
                    fragment.setArguments(bundle);

                    FragmentManager fragmentManager = getActivity().getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            });
        }
    }
}