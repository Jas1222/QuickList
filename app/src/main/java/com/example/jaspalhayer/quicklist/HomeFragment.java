package com.example.jaspalhayer.quicklist;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment {

    ImageView bookIcon;
    ImageView barcodeIcon;
    ImageView messageIcon;
    ImageView listIcon;


    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

       // setHasOptionsMenu(true);
        setHomeIcons(rootView);
        return rootView;
    }

    public void setHomeIcons(View rootView){
        bookIcon = (ImageView)rootView.findViewById(R.id.bookIcon);
        bookIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launching All products Activity
                Intent i = new Intent(getActivity().getApplicationContext(), BrowseUniCourseActivity.class);
                startActivity(i);
            }
        });

        barcodeIcon = (ImageView)rootView.findViewById(R.id.barcodeIcon);
        barcodeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Will open 'Barcode' Intent", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        messageIcon = (ImageView)rootView.findViewById(R.id.messageIcon);
        messageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Will open 'My Messages' Intent", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        listIcon = (ImageView)rootView.findViewById(R.id.listIcon);
        listIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Will open 'My Listings' Intent", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
