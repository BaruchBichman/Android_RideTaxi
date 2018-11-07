package com.example.baruch.android5779_6256_4843.controller;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.baruch.android5779_6256_4843.R;
import com.example.baruch.android5779_6256_4843.model.backend.Backend;
import com.example.baruch.android5779_6256_4843.model.backend.BackendFactory;
import com.example.baruch.android5779_6256_4843.model.entities.ClientRequestStatus;
import com.example.baruch.android5779_6256_4843.model.entities.Ride;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import static android.widget.Toast.LENGTH_LONG;

public class OrderRideActivity extends AppCompatActivity{

    final int PLACE_PICKER_REQUEST_DESTINATION = 1;
    final int PLACE_PICKER_REQUEST_PICKUP = 2;
    private Button newRideButton;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText phoneNumberEditText;
    private EditText destinationAddressEditText;
    private EditText pickUpAddressEditText;
    private Ride ride;

    private static Backend backend;
    // Acquire a reference to the system Location Manager
    private LocationManager locationManager;
    // Define a listener that responds to location updates
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_ride);

        ride=new Ride();
        backend=BackendFactory.getBackend();
        newRideButton = (Button) findViewById(R.id.orderTaxiButton);
        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        phoneNumberEditText = (EditText) findViewById(R.id.phoneNumberEditText);
        destinationAddressEditText = (EditText) findViewById(R.id.destinationEditText);
        destinationAddressEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(OrderRideActivity.this), PLACE_PICKER_REQUEST_DESTINATION);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        pickUpAddressEditText = (EditText) findViewById(R.id.pickupAddressEditText);
        pickUpAddressEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(OrderRideActivity.this), PLACE_PICKER_REQUEST_PICKUP);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                pickUpAddressEditText.setText(getPlace(location));
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        newRideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ride.setClientFirstName(firstNameEditText.getText().toString());
                ride.setClientLastName(lastNameEditText.getText().toString());
                ride.setClientEmail(emailEditText.getText().toString());
                ride.setClientTelephone(phoneNumberEditText.getText().toString());
                ride.setDestinationAddress(destinationAddressEditText.getText().toString());
                ride.setPickupAddress(pickUpAddressEditText.getText().toString());
                ride.setRideState(ClientRequestStatus.WAITING);
                addClientRequestToDataBase(ride);
            }
        });

        getLocation();
    }


    private void addClientRequestToDataBase(Ride ride) {
        newRideButton.setEnabled(false);
        backend.addNewClientRequestToDataBase(ride, new Backend.Action() {
            @Override
            public void onSuccess() {
                Toast.makeText(getBaseContext(),"We get your Order!",LENGTH_LONG).show();
                }
            @Override
            public void onFailure() {
                newRideButton.setEnabled(true);
                Toast.makeText(getBaseContext(),"We are sorry! The order didn't success\n Try Again!",LENGTH_LONG).show();
            }
        });

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
                return address;
            } else {
                return "no place: \n (" + location.getLongitude() + " , " + location.getLatitude() + ")";
            }
        } catch (IOException e) {
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST_DESTINATION) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                destinationAddressEditText.setText(place.getAddress());
            }
        }
        if (requestCode == PLACE_PICKER_REQUEST_PICKUP) {
            if (resultCode == RESULT_OK) {
                locationManager.removeUpdates(locationListener);
                Place place = PlacePicker.getPlace(data, this);
                pickUpAddressEditText.setText(place.getAddress());
            }
        }
    }

}
