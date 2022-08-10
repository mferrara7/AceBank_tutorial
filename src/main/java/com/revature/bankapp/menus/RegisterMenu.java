package com.revature.bankapp.menus;

import com.revature.bankapp.models.BankMembers;
import com.revature.bankapp.services.MemberService;
import com.revature.bankapp.util.CustomLogger;
import com.revature.bankapp.util.MenuRouter;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Member;

public class RegisterMenu extends Menu{
    CustomLogger customLogger = CustomLogger.getLogger(true);
    private final MemberService memberService;

    public RegisterMenu(BufferedReader terminalReader, MenuRouter menuRouter, MemberService memberService) {
        super("Register", "/register", terminalReader, menuRouter);
        this.memberService = memberService;
    }

    @Override
    public void render() throws IOException {
        System.out.print("Please enter email: \n>"); // line break \n
        String email = terminalReader.readLine();
        System.out.print("Please enter your password: \n>");
        String password = terminalReader.readLine();
        BankMembers newBankMembers = new BankMembers(email,password);
        Member member = memberService.registerMember((Member) newBankMembers);
        if(member == null){
            System.out.println("Registration failed, please try again");
            menuRouter.transfer("/register");
        } else {
            System.out.println("Registration complete.");
            menuRouter.transfer("/welcome");
        }
    }
}
