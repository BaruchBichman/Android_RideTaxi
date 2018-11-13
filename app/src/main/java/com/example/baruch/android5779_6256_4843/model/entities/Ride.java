package com.example.baruch.android5779_6256_4843.model.entities;

import android.location.Location;

import java.time.LocalTime;

public class Ride {


    private ClientRequestStatus mRideState;
    private Location mPickupAddress;
    private Location mDestinationAddress;
    private LocalTime mStartTime;
    private LocalTime mFinishTime;
    private String mClientFirstName;
    private String mClientLastName;
    private String mClientTelephone;
    private String mClientEmail;
    private String mKey;


    public ClientRequestStatus getRideState() {
        return mRideState;
    }

    public void setRideState(ClientRequestStatus rideState) {
        mRideState = rideState;
    }

    public Location getPickupAddress() {
        return mPickupAddress;
    }

    public void setPickupAddress(Location startAddress) {
        mPickupAddress = startAddress;
    }

    public Location getDestinationAddress() {
        return mDestinationAddress;
    }

    public void setDestinationAddress(Location destinationAddress) {
        mDestinationAddress = destinationAddress;
    }

    public LocalTime getStartTime() {
        return mStartTime;
    }

    public void setStartTime(LocalTime startTime) {
        mStartTime = startTime;
    }

    public LocalTime getFinishTime() {
        return mFinishTime;
    }

    public void setFinishTime(LocalTime finishTime) {
        mFinishTime = finishTime;
    }

    public String getClientFirstName() {
        return mClientFirstName;
    }

    public void setClientFirstName(String clientFirstName) {
        mClientFirstName = clientFirstName;
    }

    public String getClientTelephone() {
        return mClientTelephone;
    }

    public void setClientTelephone(String clientTelephone) {
        mClientTelephone = clientTelephone;
    }

    public String getClientEmail() {
        return mClientEmail;
    }

    public void setClientEmail(String clientEmail) {
        mClientEmail = clientEmail;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getClientLastName() {
        return mClientLastName;
    }
    public void setClientLastName(String ClientLastName) {
        mClientLastName = ClientLastName;
    }
}
