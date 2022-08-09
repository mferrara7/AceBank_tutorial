package com.revature.util;

/*
    Singleton Design Patter
        -Creational pattern that restricts only a single instance of the class can be made within the application
    Factory Deign Pattern
        - Creational, used to abstract away creation and instantiation of the class
        - churn out the instance of connection to other files
 */

import org.postgresql.Driver;

import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    // This class OPENS connection and delivers them to our DAO, try-with-resources is very useful

    private static final ConnectionFactory connectionFactory = new ConnectionFactory(); // Eagerly instantiated singleton
    private Properties = new Properties(); // making sure sensitive info is hiden
    private ConnectionFactory() { // default no arg constructor is public, singletons dont want that
        props.load(new FileReader("src/main/resources/db.properties"));
    } catch

    static { // STATIC BLOCK - just runs everything inside the {} (block) at class loading
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    // this is a getter for our instance of a ConnectionFactory
    public static ConnectionFactory getConnectionFactory(){
        return connectionFactory;
    }

    public Connection getConnection(){
        return DriverManager.getConnection(props.getProperty("url"), props.getProperty("username"), props.getProperty("password")
    } catch (SQLException e) {
        e.printStackTrace();
        return null;
    }
}
