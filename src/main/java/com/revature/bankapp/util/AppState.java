package com.revature.bankapp.util;

import com.revature.bankapp.daos.BankMemberDao;
import com.revature.bankapp.menus.Dashboard;
import com.revature.bankapp.menus.LoginMenu;
import com.revature.bankapp.menus.RegisterMenu;
import com.revature.bankapp.menus.WelcomeMenu;
import com.revature.bankapp.services.MemberService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AppState {

    private static boolean isRunning;
    private final MenuRouter menuRouter;
    private final CustomLogger customLogger = CustomLogger.getLogger(true); // this will log our actions

    public AppState() {
        isRunning = true;
        menuRouter = new MenuRouter(); // create the menu router

        BufferedReader terminalReader = new BufferedReader(new InputStreamReader(System.in));
        //this creates the BufferedReader which take an InputStreamReader as a parameter, so we can get user input
        BankMemberDao bankMemberDao = new BankMemberDao();
        MemberService memberService = new MemberService();

        WelcomeMenu welcomeMenu = new WelcomeMenu(terminalReader, menuRouter, memberService);
        RegisterMenu registerMenu = new RegisterMenu(terminalReader, menuRouter, memberService);
        Dashboard dashboard = new Dashboard(terminalReader, menuRouter, memberService);
        LoginMenu loginMenu = new LoginMenu(terminalReader, menuRouter, memberService);

        menuRouter.addMenu(welcomeMenu);
        menuRouter.addMenu(registerMenu);
        menuRouter.addMenu(dashboard);
        menuRouter.addMenu(loginMenu);

        customLogger.info("Application initialized");

    }

    public void startup() {
        while (isRunning) {
            customLogger.logMessageToFile("Routing to welcome menu...");
            menuRouter.transfer("/welcome");
        }
    }

    public static void shutdown() {

        isRunning = false;

    }
}
