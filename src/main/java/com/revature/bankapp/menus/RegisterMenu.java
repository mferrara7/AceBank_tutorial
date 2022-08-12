package com.revature.bankapp.menus;

import com.revature.bankapp.models.BankMembers;
import com.revature.bankapp.services.MemberService;
import com.revature.bankapp.util.MenuRouter;

import java.io.BufferedReader;
import java.io.IOException;

public class RegisterMenu extends Menu{
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
        BankMembers bankMembers = (BankMembers) memberService.registerMember(newBankMembers);
        if(bankMembers == null){ // need != because will make sure to move to else statement below
            System.out.println("Registration failed, please try again");
            menuRouter.transfer("/register");
        } else {
            System.out.println("Registration complete.");
            //memberService.login(BankMembers.getEmail(), bankMembers.getPassword());
            //customLogger.info("Navigating to dashboard for " + BankMembers.getEmail());
            menuRouter.transfer("/welcome");
        }
    }
}
