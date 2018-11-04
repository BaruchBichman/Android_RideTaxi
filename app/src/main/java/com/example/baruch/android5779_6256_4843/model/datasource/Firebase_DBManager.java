package com.example.baruch.android5779_6256_4843.model.datasource;
import android.support.annotation.NonNull;

import com.example.baruch.android5779_6256_4843.model.backend.Backend;
import com.example.baruch.android5779_6256_4843.model.entities.Ride;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Firebase_DBManager implements Backend {

    private static Firebase_DBManager firebase_dbManager=null;

    /**
     * Private Constructor, to ensure only one instance of the database
     */
    private Firebase_DBManager() {
    }

    /**
     * Getter of FireBase_DbManager, singleton implemented
     * @return Firebase_DBManager
     */
    public static Firebase_DBManager getFirebase_dbManager() {
        if (firebase_dbManager == null)
            firebase_dbManager = new Firebase_DBManager();

        return firebase_dbManager;
    }


    public interface Action<T>{
        void onSuccess(T obj);
        void onFailure(Exception exception);
        void onProgress(String status, double Percent);
    }

    public interface NotifyDataChange<T>{
        void onDataChanged(T obj);
        void onFailure(Exception exception);
    }

    static DatabaseReference OrdersTaxiRef;
    static {
        FirebaseDatabase database =FirebaseDatabase.getInstance();
        OrdersTaxiRef=database.getReference("orders");
    }
    @Override
    public void addNewClientRequestToDataBase(Ride ride) {
        final Action<String> action = null;
        addNewClientRequestToFireBase(ride,action);
    }

    public void addNewClientRequestToFireBase(final Ride ride,final Action<String> action ){
        String key=OrdersTaxiRef.push().getKey();
        ride.setKey(key);
        OrdersTaxiRef.child(key).setValue(ride).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                action.onSuccess(ride.getKey());
                action.onProgress("Request for a Taxi succeed",100);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                action.onFailure(e);
                action.onProgress("Error, Retry to order a Taxi",100);
            }
        });

    }
}
