package com.example.baruch.android5779_6256_4843.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baruch.android5779_6256_4843.R;
import com.example.baruch.android5779_6256_4843.model.backend.Action;
import com.example.baruch.android5779_6256_4843.model.backend.Backend;
import com.example.baruch.android5779_6256_4843.model.backend.BackendFactory;
import com.example.baruch.android5779_6256_4843.model.entities.Ride;

import java.lang.ref.WeakReference;

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
    private MyTask myTask;

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
        final OrderRideActivity context=this;

        newRideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ride.setClientFirstName(firstNameEditText.getText().toString());
                ride.setClientLastName(lastNameEditText.getText().toString());
                ride.setClientEmail(emailEditText.getText().toString());
                ride.setClientTelephone(phoneNumberEditText.getText().toString());
                ride.setDestinationAddress(destinationAddressEditText.getText().toString());
                ride.setPickupAddress(pickUpAddressEditText.getText().toString());
                myTask=new MyTask(context);
                myTask.execute(ride);
            }
        });
    }

    private static class MyTask extends AsyncTask<Ride,Double,Boolean>{

        private WeakReference<OrderRideActivity> activityWeakReference;
        private Boolean result;

        public MyTask(OrderRideActivity context) {
            activityWeakReference=new WeakReference<>(context);
        }

        @Override
        protected Boolean doInBackground(Ride... rides) {
            backend.addNewClientRequestToDataBase(rides[0], new Action() {
                @Override
                public void onSuccess(Object obj) {
                }

                @Override
                public void onFailure(Exception exception) {
                }

                @Override
                public void onProgress(String status, double percent) {

                }
            });
            return true;
        }

      /*  @Override
        protected void onPreExecute() {
            super.onPreExecute();
            OrderRideActivity activity=activityWeakReference.get();
            activity.newRideButton.setEnabled(false);

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            OrderRideActivity activity=activityWeakReference.get();
            if(!aBoolean) {
                activity.newRideButton.setEnabled(true);
                Toast.makeText(activity,"Sorry! An error appeared, Try again",Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(activity,"We get your order!",Toast.LENGTH_LONG).show();
        }
*/

    }
}
