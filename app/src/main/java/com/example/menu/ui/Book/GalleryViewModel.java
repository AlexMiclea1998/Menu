package com.example.menu.ui.Book;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GalleryViewModel() {
        mText = new MutableLiveData<>();

        mText.setValue("Will be loaded in the browser");
    }

    public LiveData<String> getText() {
        return mText;
    }
}