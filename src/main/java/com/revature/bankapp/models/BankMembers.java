package com.revature.bankapp.models;

public class BankMembers {

    private double balance;
    private String email;
    private String password;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public BankMembers getSessionMember() {
            return null;
    }

    // the following methods are referred to as setters and getters.
    // They assign information to the objects parameters, and return the information with getters
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public boolean get(int i) {
        return false;
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

    public void setPassword(String password) {
        this.password = password;
    }
}
