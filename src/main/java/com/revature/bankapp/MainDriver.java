package com.revature;

public class MainDriver { // Main access to run program
    public static void main(String[] args) {

        boolean isRunning = true; // understands to operate to opening page options

        while(isRunning){ // allows multiple actions regardless the ammount
            String() welcomeMessage = {"Welcome to Ace Bank", "1.) Log Into Accounts", "2.) Open New Accounts", "3.) Reset Login Info"}

            System.out.println(welcomeMessage[0]);
            System.out.println(welcomeMessage[1]);
            System.out.println(welcomeMessage[2]);
            System.out.println(welcomeMessage[3]);

        try { // get clarification, but sure it starts allowing actions to repeat to system to move to next screen
            String firstInput = terminalReader.readline();

            case "1";
                System.out.println("You have selected Login!");
                break;

            case "2";
                System.out.println("You have chosen to create a new account!");
                break;

                case "3";
             System.out.println("You would like to Reset Login Info");
                    break;
        }
    }
}