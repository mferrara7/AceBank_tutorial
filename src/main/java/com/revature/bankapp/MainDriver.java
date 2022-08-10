package com.revature.bankapp;

import com.revature.bankapp.util.AppState;

public class MainDriver { // needed for app to run

    public static void main(String[] args) {

        AppState appState = new AppState();
        appState.startup();
    }
}
        // application not printing anything from the options most likely due to info printed in the welcome menu, but should printed here as the priority
        // there was info but when working on it, clicked and nothing came back when trying to undo error
        // not sure where to start to match with application
        // LOG.TXT shows the file error continue to reprint, "Routing to welcomeMenu"
        // Something in my Connection Factory, not stopping reprint and actually connecting over to the welcome menu print
        // believe if this were corrected, everything would move forward without errors
        // unsure how to resolve memory error