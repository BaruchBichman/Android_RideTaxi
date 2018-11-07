package com.example.baruch.android5779_6256_4843.model.datasource;
import android.support.annotation.NonNull;

import com.example.baruch.android5779_6256_4843.model.backend.Backend;
import com.example.baruch.android5779_6256_4843.model.entities.Ride;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

    private static DatabaseReference OrdersTaxiRef;
    static {
        FirebaseDatabase database =FirebaseDatabase.getInstance();
        OrdersTaxiRef=database.getReference("orders");
    }


    /*--------------------OPERATIONS--------------------*/

    @Override
    public void addNewClientRequestToDataBase(final Ride ride, final Action action) {
        String key=OrdersTaxiRef.push().getKey();
        ride.setKey(key);
        OrdersTaxiRef.child(key).setValue(ride).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                action.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                action.onFailure();
            }
        });
    }

}
