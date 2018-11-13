package com.example.baruch.android5779_6256_4843.controller;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baruch.android5779_6256_4843.R;
import com.example.baruch.android5779_6256_4843.model.backend.Backend;
import com.example.baruch.android5779_6256_4843.model.backend.BackendFactory;
import com.example.baruch.android5779_6256_4843.model.entities.ClientRequestStatus;
import com.example.baruch.android5779_6256_4843.model.entities.Ride;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.FusedLocationProviderClient;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.widget.Toast.LENGTH_LONG;

public class OrderRideActivity extends AppCompatActivity {

    final int PLACE_PICKER_REQUEST_DESTINATION = 1;
    final int PLACE_PICKER_REQUEST_PICKUP = 2;
    final int REQUEST_CHECK_SETTINGS = 3;
    private Button newRideButton;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText phoneNumberEditText;
    private EditText destinationAddressEditText;
    private EditText pickUpAddressEditText;
    private Ride ride;

    private static Backend backend;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private Geocoder mGeocoder;
    private Location mLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_ride);

        ride = new Ride();
        backend = BackendFactory.getBackend();
        findViews();
        getLocation();

    }

    private void getLocation() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CHECK_SETTINGS);
        } else {
            //if permission is granted
            enableGps();
            buildLocationRequest();
            buildLocationCallBack();

            //create FusedProviderClient
            mFusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CHECK_SETTINGS);
                return;
            }
            mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            }
    }

    private void enableGps() {
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }
    }
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick( final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                        Toast.makeText(OrderRideActivity.this,"Sorry, you must allow gps location to use this app",LENGTH_LONG).show();
                        finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void buildLocationCallBack() {
        mLocationCallback=new LocationCallback()
        {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for(Location location: locationResult.getLocations())
                    mLocation=location;
                ride.setPickupAddress(mLocation);
                displayAddress();
            }
        };
    }

    private void displayAddress() {
                mGeocoder = new Geocoder(OrderRideActivity.this, Locale.getDefault());
                Address address;
                List<Address> addresses;
                try {
                    addresses = mGeocoder.getFromLocation(mLocation.getLatitude(), mLocation.getLongitude(), 1);
                    if(addresses!=null && addresses.size()>0) {
                        address = addresses.get(0);
                        pickUpAddressEditText.setText(address.getAddressLine(0));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

    }

    private void buildLocationRequest() {
        mLocationRequest=new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(3000);
        mLocationRequest.setSmallestDisplacement(10);
    }


    private void findViews() {
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

        newRideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRide();
                addClientRequestToDataBase(ride);
            }
        });
    }
    private void setRide() {
        ride.setClientFirstName(firstNameEditText.getText().toString());
        ride.setClientLastName(lastNameEditText.getText().toString());
        ride.setClientEmail(emailEditText.getText().toString());
        ride.setClientTelephone(phoneNumberEditText.getText().toString());
        //ride.setPickupAddress(pickUpAddressEditText.getText().toString());
        ride.setRideState(ClientRequestStatus.WAITING);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST_DESTINATION) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                destinationAddressEditText.setText(place.getAddress());
                Location location=new Location("destination");
                location.setLatitude(place.getLatLng().latitude);
                location.setLongitude(place.getLatLng().longitude);
                ride.setDestinationAddress(location);
            }
        }
        if (requestCode == PLACE_PICKER_REQUEST_PICKUP) {
            if (resultCode == RESULT_OK) {
                mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
                Place place = PlacePicker.getPlace(data, this);
                pickUpAddressEditText.setText(place.getAddress());
                Location location=new Location("pickup");
                location.setLatitude(place.getLatLng().latitude);
                location.setLongitude(place.getLatLng().longitude);
                ride.setPickupAddress(location);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode)
        {
            case REQUEST_CHECK_SETTINGS:
            {
                if(grantResults.length>0)
                {
                    if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    {

                    }
                    else if(grantResults[0]==PackageManager.PERMISSION_DENIED)
                    {

                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
    }
}
