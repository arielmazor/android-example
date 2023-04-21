package io.nexttutor.android_example.src.shared;

import androidx.lifecycle.MutableLiveData;

public enum firebaseJava {
    INSTANCE;

    private MutableLiveData<String> fbUser;

    public MutableLiveData<String> getFbUser() {
        if (fbUser == null) {
            fbUser = new MutableLiveData<String>("");
        }
        return fbUser;
    }
}