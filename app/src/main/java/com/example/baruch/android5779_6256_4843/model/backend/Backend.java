package com.example.baruch.android5779_6256_4843.model.backend;

import com.example.baruch.android5779_6256_4843.model.entities.Ride;
import com.google.android.gms.tasks.Task;

public interface Backend {
    Task addNewClientRequestToDataBase(Ride ride);
}
