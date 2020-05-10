package com.example.shoppers_beta.ui.send;

import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SendViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public enum AuthenticationState {
        UNAUTHENTICATED,        // Initial state, the user needs to authenticate
        AUTHENTICATED,          // The user has authenticated successfully
        INVALID_AUTHENTICATION  // Authentication failed
    }


    public SendViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Category fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}