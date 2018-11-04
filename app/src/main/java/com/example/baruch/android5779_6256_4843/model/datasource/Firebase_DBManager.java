package com.example.baruch.android5779_6256_4843.model.datasource;

import com.example.baruch.android5779_6256_4843.model.backend.Backend;
import com.example.baruch.android5779_6256_4843.model.entities.Ride;

public class Firebase_DBManager implements Backend {

    private static Firebase_DBManager firebase_dbManager=null;

    private Firebase_DBManager(){
    };

    public static Firebase_DBManager getFirebase_dbManager() {
        if (firebase_dbManager == null)
            firebase_dbManager = new Firebase_DBManager();

        return firebase_dbManager;
    }
    @Override
    public void addNewClientRequest(Ride ride) {

    }
}
