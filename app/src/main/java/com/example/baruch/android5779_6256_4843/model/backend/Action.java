package com.example.baruch.android5779_6256_4843.model.backend;

public interface Action<T> {
        void onSuccess(T obj);
        void onFailure(Exception exception);
        void onProgress(String status, double percent);

}
