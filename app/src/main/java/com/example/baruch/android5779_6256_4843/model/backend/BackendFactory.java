package com.example.baruch.android5779_6256_4843.model.backend;

import com.example.baruch.android5779_6256_4843.model.datasource.Firebase_DBManager;

public final class BackendFactory {
    public static Backend getBackend(){
        return Firebase_DBManager.getFirebase_dbManager();
    }
}
