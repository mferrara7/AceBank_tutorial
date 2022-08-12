package com.revature.bankapp.services;

import com.revature.bankapp.daos.BankMemberDao;
import com.revature.bankapp.menus.Menu;
import com.revature.bankapp.models.BankMembers;
import com.revature.bankapp.util.CustomLogger;
import com.revature.bankapp.util.MenuRouter;
import com.revature.bankapp.util.exceptions.InvalidUserInputException;
import com.revature.bankapp.util.exceptions.ResourcePersistanceException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import static com.revature.bankapp.util.AppState.shutdown;

public class MemberService {

    CustomLogger customLogger = CustomLogger.getLogger(true);
    private final BankMemberDao bankMemberDao;
    private BankMembers sessionMember = null;
    // CONSTRUCTOR
    public MemberService(){
        this.bankMemberDao = new BankMemberDao();
    }
    // Methods
    public BankMembers registerMember(BankMembers newBankMembers) {
        try {

            if (!isMemberValid(newBankMembers)) {
                throw new InvalidUserInputException("User input was invalid");
            }

            if(!isEmailAvailable(newBankMembers.getEmail())){
                throw new ResourcePersistanceException("Email is already registered.");
            }

            assert bankMemberDao != null;
            bankMemberDao.create(newBankMembers);

            return newBankMembers;

        } catch (Exception e) {
            e.printStackTrace();
            customLogger.warn(e.toString());
            return null;
        }
    }
    public BankMembers login(String email, String password){
        BankMembers bankMembers = null;
        if (bankMemberDao != null) {// this fixed the NullPoint error, and access into dashboard options
            bankMembers = bankMemberDao.loginCredentialCheck(email, password);
        }
        // find why bankMemberDao can be null?
        sessionMember = (BankMembers) bankMembers;
        return (BankMembers) bankMembers;
    }
    public List<BankMembers> readAll(){
        return bankMemberDao.findAll();
    }
    public boolean isMemberValid(BankMembers newMember){
        if(newMember == null) return false;
        if(newMember.getEmail() == null || newMember.getEmail().trim().equals(""))
            // if "||" entered, means to complete if either tradition is true
             return false;
        return newMember.getPassword() != null && !newMember.getPassword().trim().equals("");
    }
    public boolean isEmailAvailable(String email){
        List<BankMembers> bankMembers = readAll();
        for(int i = 0; i < bankMembers.size(); i++){
            if(bankMembers.get(i) == null) break;
            if(bankMembers.get(i).getEmail().equals(email)){
                return false;
            }
        }
        return true;
    }
    public BankMembers getSessionMember(){

        return sessionMember;
    }
    public void logout(){

        sessionMember = null;
    }
    public boolean isSessionActive(){

        return sessionMember != null;
    }

    public static class WelcomeMenu extends Menu {
        //inheritance
        CustomLogger customLogger = CustomLogger.getLogger(true); // create the customLogger object

        public WelcomeMenu(BufferedReader terminalReader, MenuRouter menuRouter, MemberService memberService) {
            super("Welcome", "/welcome", terminalReader, menuRouter);
            // declaration, technically null
        }

        @Override // this indicates we are overriding the method we are inheriting
        public void render() throws IOException {

            String[] welcomeMessages = {"Welcome to Ace Bank", "1) Login", "2) Register", "3) Exit application"};

            System.out.println(welcomeMessages[0]);
            System.out.println(welcomeMessages[1]);
            System.out.println(welcomeMessages[2]);
            System.out.println(welcomeMessages[3]);

            String firstInput = terminalReader.readLine(); // it throws an IOException, this MUST be handled before compile time (checked)


            switch (firstInput) {
                case "1": // if firstInput.equals("1") then this case will execute
                    System.out.println("User selected login.");
                    menuRouter.transfer("/login");
                    break; // the keyword break prevents any fall-through
                case "2":
                    System.out.println("User selected register.");
                    customLogger.info("Now routing user to registration page");
                    menuRouter.transfer("/register");
                    break;
                case "3":
                    System.out.println("User is exiting");
                    shutdown();
                    break;
                default:
                    System.out.println("User has not selected a valid input.");

            }
        }

    }
}
