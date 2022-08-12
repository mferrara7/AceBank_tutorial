package com.revature.bankapp.menus;

import com.revature.bankapp.util.MenuRouter;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class Menu {// abstract class means methods will not be concretely defined here
    protected String name; // protected means to only allow the class with the package to have access to the attribute
    protected String route;
    protected BufferedReader terminalReader; //Dependency Injection - requirement for the class to function and inject at Instantiate
    protected MenuRouter menuRouter;

    public Menu(String name, String route, BufferedReader terminalReader, MenuRouter menuRouter) {
        // When you create a Menu object, these are the parameters to set
        this.name = name; // sets the parameters to equal with the Menu's attributes with the same name
        this.route = route;
        this.terminalReader = terminalReader;
        this.menuRouter = menuRouter;
    }

    public String getName() { // returns name

        return name;
    }

    public String getRoute() { //returns route

        return route;
    }

    // Adding abstract keyword requires that any class that inherits this Menu class MUST implement it
    public abstract void render() throws IOException; // each menu we use will inherit this(overriding), implemented a different way
}
