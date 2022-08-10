package com.revature.bankapp.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    // This class OPENS connection and delivers them to our DAO

    private static final ConnectionFactory connectionFactory = new ConnectionFactory();
    // instantiated singleton means it is created immediately
    private Properties props = new Properties(); // this will be used to read from DB properties

    private ConnectionFactory(){
        try {
            props.load(new FileReader("src/main/resources/db.properties")); // read from db.properties
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static { // static block, just runs everything inside the {} (block) at class loading
        try{
            Class.forName("org.postgresql.Driver"); // grab the driver before anything else happens
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    // getter for our instance of a ConnectionFactory
    public static ConnectionFactory getConnectionFactory() {

        return connectionFactory;
    }

    public Connection getConnection(){
        try {
            return DriverManager.getConnection(props.getProperty("url"), props.getProperty("username"), props.getProperty("password"));
            // return the connection, this is where the url for DV would have been entered if completed.
        } catch (SQLException e) { //otherwise let us know what wrong and return null because we have to return something since this is not a void method
            e.printStackTrace();
            return null;
        }
    }
}