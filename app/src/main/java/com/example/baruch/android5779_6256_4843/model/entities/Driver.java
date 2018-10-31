package com.example.baruch.android5779_6256_4843.model.entities;


public class Driver {
    private String mLastName;
    private String mFirstName;
    private String mIdDriver;
    private String mDriverTelephoneNumber;
    private String mDriverEmail;
    private String mDriverCreditCard;

    public Driver(String mLastName, String mFirstName, String mIdDriver,
                  String mDriverTelephoneNumber, String mDriverEmail, String mDriverCreditCard) {
        this.mLastName = mLastName;
        this.mFirstName = mFirstName;
        this.mIdDriver = mIdDriver;
        this.mDriverTelephoneNumber = mDriverTelephoneNumber;
        this.mDriverEmail = mDriverEmail;
        this.mDriverCreditCard = mDriverCreditCard;
    }


    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getIdDriver() {
        return mIdDriver;
    }

    public String getDriverTelephoneNumber() {
        return mDriverTelephoneNumber;
    }

    public void setDriverTelephoneNumber(String mDriverTelephoneNumber) {
        this.mDriverTelephoneNumber = mDriverTelephoneNumber;
    }

    public String getDriverEmail() {
        return mDriverEmail;
    }

    public void setDriverEmail(String mDriverEmail) {
        this.mDriverEmail = mDriverEmail;
    }

    public String getDriverCreditCard() {
        return mDriverCreditCard;
    }

    public void setDriverCreditCard(String mDriverCreditCard) {
        this.mDriverCreditCard = mDriverCreditCard;
    }
}
