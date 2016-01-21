package com.example.jaspalhayer.quicklist;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.Result;

import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class SimpleScannerActivity extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    protected ConnectionHandler request;


    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
        request = new ConnectionHandler();
        // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        request.getGoogleBookRequest2(getApplicationContext(), rawResult.getText(), new ConnectionHandler.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                //CHECK IF GOOD RESPONSE, IF SO LAUNCH BELOW
                Intent i = new Intent(getApplicationContext(), CreateListingActivity.class);
                i.putExtra("jsonObject", result.toString());
                i.putExtra("CAME_FROM", "scan");
                startActivity(i);
            }
        });
        Log.v("SCAN_TAG", rawResult.getText()); // Prints scan results
        Log.v("SCAN_TAG", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
    }
}