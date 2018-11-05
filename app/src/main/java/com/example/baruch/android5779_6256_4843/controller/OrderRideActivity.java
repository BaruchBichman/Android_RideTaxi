package com.example.baruch.android5779_6256_4843.controller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.baruch.android5779_6256_4843.R;
import com.example.baruch.android5779_6256_4843.model.backend.Backend;
import com.example.baruch.android5779_6256_4843.model.backend.BackendFactory;
import com.example.baruch.android5779_6256_4843.model.entities.Ride;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import org.shredzone.commons.suncalc.SunTimes;

public class OrderRideActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton newRideButton;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText phoneNumberEditText;
    private EditText destinationAddressEditText;
    private EditText pickUpAddressEditText;

    private Ride ride;
    private Backend backend;

    // Acquire a reference to the system Location Manager
    private LocationManager locationManager;
    // Define a listener that responds to location updates
    private LocationListener locationListener;

    final int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_ride);
        findViews();
        getLocation();
    }

    private void findViews() {
        newRideButton=(ImageButton)findViewById(R.id.goButton);
        firstNameEditText=(EditText)findViewById(R.id.firstNameEditText);
        lastNameEditText=(EditText)findViewById(R.id.lastNameEditText);
        emailEditText=(EditText)findViewById(R.id.emailEditText);
        phoneNumberEditText=(EditText)findViewById(R.id.phoneNumberEditText);
        destinationAddressEditText=(EditText)findViewById(R.id.destinationEditText);
        pickUpAddressEditText=(EditText)findViewById(R.id.pickupAddressEditText);
        newRideButton.setOnClickListener(this);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {

                showSunTimes(location.getLatitude(), location.getLongitude()); /// ...

                // Called when a new location is found by the network location provider.
                //    Toast.makeText(getBaseContext(), location.toString(), Toast.LENGTH_LONG).show();
                pickUpAddressEditText.setText(getPlace(location));////location.toString());

                // Remove the listener you previously added
                //  locationManager.removeUpdates(locationListener);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
    }

    void showSunTimes( double lat, double lng) {
        Date date = new Date();// date of calculation

        SunTimes times = SunTimes.compute()
                .on(date)       // set a date
                .at(lat, lng)   // set a location
                .execute();     // get the results
        System.out.println("Sunrise: " + times.getRise());
        System.out.println("Sunset: " + times.getSet());
    }

    private void getLocation() {
        //     Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 5);

        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

    }

    public String getPlace(Location location) {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            if (addresses.size() > 0) {
                String address = addresses.get(0).getAddressLine(0);
                return address ;
            }
            else {
                return "no place: \n (" + location.getLongitude() + " , " + location.getLatitude() + ")";
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return "IOException ...";
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the location", Toast.LENGTH_SHORT).show();
            }
        }

    }
    @Override
    public void onClick(View v) {
        if(v == newRideButton){
            loadData();
        }
    }

    private void loadData() {
        ride=new Ride();
        backend= BackendFactory.getBackend();

        ride.setClientFirstName(firstNameEditText.getText().toString());
        ride.setClientLastName(lastNameEditText.getText().toString());
        ride.setClientEmail(emailEditText.getText().toString());
        ride.setClientTelephone(phoneNumberEditText.getText().toString());
        ride.setDestinationAddress(destinationAddressEditText.getText().toString());
        ride.setPickupAddress(pickUpAddressEditText.getText().toString());
        backend.addNewClientRequestToDataBase(ride);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());

                Date date = new Date();// date of calculation

                SunTimes times = SunTimes.compute()
                        .on(date)       // set a date
                        .at(place.getLatLng().latitude,place.getLatLng().longitude)   // set a location
                        .execute();     // get the results

                //toastMsg+= "\nSunrise: " + times.getRise();
                //toastMsg+= "\nSunset: " + times.getSet();
                pickUpAddressEditText.setText(toastMsg);
            }
        }
    }


}
