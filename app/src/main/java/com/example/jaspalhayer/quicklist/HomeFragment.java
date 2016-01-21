package com.example.jaspalhayer.quicklist;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment {

    ImageView bookIcon;
    ImageView barcodeIcon;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

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
                Intent i = new Intent(getActivity().getApplicationContext(), SimpleScannerActivity.class);
                startActivity(i);
            }
        });
    }
}
