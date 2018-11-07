package com.example.baruch.android5779_6256_4843.controller;

import android.os.Bundle;
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

import static android.widget.Toast.LENGTH_LONG;

public class OrderRideActivity extends AppCompatActivity {

    private Button newRideButton;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText phoneNumberEditText;
    private EditText destinationAddressEditText;
    private EditText pickUpAddressEditText;

    private Ride ride;
    private static Backend backend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_ride);

        newRideButton=(Button)findViewById(R.id.orderTaxiButton);
        firstNameEditText=(EditText)findViewById(R.id.firstNameEditText);
        lastNameEditText=(EditText)findViewById(R.id.lastNameEditText);
        emailEditText=(EditText)findViewById(R.id.emailEditText);
        phoneNumberEditText=(EditText)findViewById(R.id.phoneNumberEditText);
        destinationAddressEditText=(EditText)findViewById(R.id.destinationEditText);
        pickUpAddressEditText=(EditText)findViewById(R.id.pickupAddressEditText);
        ride=new Ride();
        backend=BackendFactory.getBackend();


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

}
