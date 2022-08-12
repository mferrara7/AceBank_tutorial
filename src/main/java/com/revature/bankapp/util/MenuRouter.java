package com.revature.bankapp.util;

import com.revature.bankapp.menus.Menu;

import java.io.IOException;

public class MenuRouter {
    //this class will hold all the menus that the application will utilize

    private final Menu[] menus; // this array will hold the different menus

    public MenuRouter() { // when a menuRouter object is created it will automatically create an array of length 10 to hold the different menus. All 10 elements in this array are Null by default
        menus = new Menu[10];
    }

    // the void keyword allows us to create a method that is not expected to return something
    public void addMenu(Menu addedMenu) {// this method takes in a Menu object called addedMenu. It then reassigns it to the first element of the array it finds that is Null.
        for (int i = 0; i < menus.length; i++) {
            if (menus[i] == null) {
                menus[i] = addedMenu;// reassign that null element to be the addedMenu
                break;
            }
        }
    } // handles any menu inherited, because was OL, calling everything below next class

    public void transfer(String route) { // the parameter here is the route which will be how we get to the menu we want to go to
        for (Menu menu : menus) {
            if (menu == null)
                break;
            if (menu.getRoute().equals(route)) {
                try {
                    menu.render();
                } catch (IOException e) {// if there is an IOException, prints what is wrong
                    e.printStackTrace();
                }
            }
        }
    }
}