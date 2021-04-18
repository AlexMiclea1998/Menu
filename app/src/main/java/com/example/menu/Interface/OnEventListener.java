package com.example.menu.Interface;

public interface OnEventListener<T> {
     void onSuccess();
     void onFailure(Exception e);
}

