package com.example.jaspalhayer.quicklist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        final BrowseCourseListFragment browseCourseList = new BrowseCourseListFragment();

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, browseCourseList)
                    .addToBackStack(null)
                    .commit();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
