package com.revature.bankapp.menus;

import com.revature.bankapp.services.MemberService;
import com.revature.bankapp.util.CustomLogger;
import com.revature.bankapp.util.MenuRouter;

import java.io.BufferedReader;
import java.io.IOException;

import static com.revature.bankapp.util.AppState.shutdown;

public class WelcomeMenu extends Menu { // this is an example of inheritance
    CustomLogger customLogger = CustomLogger.getLogger(true); // create the customLogger object
    private final MemberService memberService; // declaration, technically null

    public WelcomeMenu(BufferedReader terminalReader, MenuRouter menuRouter, MemberService memberService) { //(MemberService memberService) will be the final parameter for welcome menu
        super("Welcome", "/welcome", terminalReader, menuRouter); // calls the parent constructor which is menu with these and sets those attributes to these arguments.
        this.memberService = memberService; // initiates value of memberService
    }

    @Override // this indicates we are overriding the method we are inheriting
    public void render() throws IOException {

        String[] welcomeMessages = {"Welcome to Ace Bank", "1) Login", "2) Register", "3) Exit application"};

        System.out.println(welcomeMessages[0]); // welcome statement
        System.out.println(welcomeMessages[1]); // login
        System.out.println(welcomeMessages[2]); // register
        System.out.println(welcomeMessages[3]); // exit

        String firstInput = terminalReader.readLine(); // throws an IOException,needs to be handled before compile time


        switch (firstInput) {
            case "1": // allows first input to follow selected case: firstInput.equals(1)
                System.out.println("User selected login....");
                menuRouter.transfer("/login");
                break; //prevents fall-through
            case "2":
                System.out.println("User selected register....");
                customLogger.info("Taking to registration page");
                menuRouter.transfer("/register");
                break;
            case "3":
                System.out.println("User is exiting.");
                shutdown();
                break;
            default:
                System.out.println("User has not selected a valid input");

        }
    }

}