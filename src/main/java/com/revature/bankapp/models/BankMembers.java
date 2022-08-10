package com.revature.bankapp.models;

public class BankMembers {

    private static String email;
    private static String password;
    public BankMembers() {
        super(); //calling the Object class to inherit from, since we will override toString method below
    }
    public BankMembers(String email) {
        this.email = email;
    }
    public BankMembers(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static BankMembers getSessionMember() {
            return null;
    }

    // the following methods are referred to as setters and getters.
    // They assign information to the objects parameters, and return the information with getters
    public static void setEmail(String email) {
        BankMembers.email = email;
    }
    public static String getEmail() {
        return email;
    }

    public static boolean get(int i) {
        return false;
    }

    public static void setFullName(String s) {

    }

    public String getPassword() {
        return password;
    }

    public String writeToFile() {
        return email + "," + password + "\n";
    }


    @Override
    public String toString() {
        return "Member{" +"email='" + email + '}';
    }

    public static void setPassword(String password) {
        BankMembers.password = password;
    }
}
