package com.revature.bankapp;

import com.revature.bankapp.util.AppState;
public class MainDriver { // needed for app to run

    public static void main(String[] args) {

        AppState appState = new AppState();
        appState.startup();
    }
}

// exception in thread "main", caused from runtime exception occurring
    // Exception in thread "main" java.lang.NullPointerException
