package com.revature.bankapp.menus;

import com.revature.bankapp.services.MemberService;
import com.revature.bankapp.util.CustomLogger;
import com.revature.bankapp.util.MenuRouter;
import com.revature.bankapp.util.exceptions.UnauthorizedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Member;

public class LoginMenu extends Menu{

    private final MemberService memberService;
    CustomLogger customLogger = CustomLogger.getLogger(true);

    public LoginMenu(BufferedReader terminalReader, MenuRouter menuRouter, MemberService memberService) {
        super("Login", "/login", terminalReader, menuRouter);
        this.memberService = memberService;
    }

    @Override
    public void render() throws IOException {
        System.out.print("Enter email: \n>"); // \n is a new line character, aka return or enter
        String email = terminalReader.readLine();

        System.out.print("Enter password: \n>");
        String password = terminalReader.readLine();

        try {
            Member member = memberService.login(email, password);
            if (member == null) {
                throw new UnauthorizedException("Invalid information. Please try again");

            }
            menuRouter.transfer("/dashboard");
        } catch (UnauthorizedException e){
            customLogger.warn(e.getMessage());
            System.out.println("Log in credentials do not match, try again");
        }
    }
}