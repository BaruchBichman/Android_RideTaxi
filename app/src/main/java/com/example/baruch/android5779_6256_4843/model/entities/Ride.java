package com.example.baruch.android5779_6256_4843.model.entities;

import android.location.Address;

import java.time.LocalTime;

public class Ride {


    private ClientRequestStatus mRideState;
    private Address mStartAddress;
    private Address mDestinationAddress;
    private LocalTime mStartTime;
    private LocalTime mFinishTime;
    private String mClientName;
    private String mClientTelephone;
    private String mClientEmail;


    public ClientRequestStatus getRideState() {
        return mRideState;
    }

    public void setRideState(ClientRequestStatus rideState) {
        mRideState = rideState;
    }

    public Address getStartAddress() {
        return mStartAddress;
    }

    public void setStartAddress(Address startAddress) {
        mStartAddress = startAddress;
    }

    public Address getDestinationAddress() {
        return mDestinationAddress;
    }

    public void setDestinationAddress(Address destinationAddress) {
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

    public String getClientName() {
        return mClientName;
    }

    public void setClientName(String clientName) {
        mClientName = clientName;
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

}
