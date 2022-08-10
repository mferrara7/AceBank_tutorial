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
import java.lang.reflect.Member;
import java.util.List;

import static com.revature.bankapp.util.AppState.shutdown;

public class MemberService {

    CustomLogger customLogger = CustomLogger.getLogger(true);
    private final BankMemberDao bankMemberDao;
    private Member sessionMember = null;

    // CONSTRUCTOR
    public MemberService(){
        BankMemberDao BankMemberDao = null;
        this.bankMemberDao = null;
    }
    // Methods
    public Member registerMember(Member newMember) {
        try {

            if (!isMemberValid(newMember)) {
                throw new InvalidUserInputException("User input was invalid");
            }

            BankMembers newBankMembers = null;
            if(!isEmailAvailable(BankMembers.getEmail())){
                throw new ResourcePersistanceException("Email is already registered.");
            }

            bankMemberDao.create(newMember);

            return newMember;

        } catch (InvalidUserInputException | ResourcePersistanceException e) {

            customLogger.warn(e.getMessage());
            return null;
        } catch (RuntimeException e){
            customLogger.warn(e.getMessage());
            return null;
        } catch (Exception e) {
            customLogger.warn(e.getMessage());
            return null;
        }
    }
    public Member login(String email, String password){
        Member member = bankMemberDao.loginCredentialCheck(email, password);
        sessionMember = member;
        return member;
    }
    public List<Member> readAll(){
        return bankMemberDao.findAll();
    }
    public boolean isMemberValid(Member newMember){
        if(newMember == null) return false;
        BankMembers newBankMembers = null;
        if(BankMembers.getEmail() == null || BankMembers.getEmail().trim().equals(""))
            // if "||" entered, means to complete if either tradition is true
             return false;
        assert false;
        return newBankMembers.getPassword() != null && !newBankMembers.getPassword().trim().equals("");
    }
    public boolean isEmailAvailable(String email){
        List<Member> members = readAll();
        for(int i = 0; i < members.size(); i++){
            if(BankMembers.get(i) == Boolean.parseBoolean(null)) break;
            if(BankMembers.getEmail().equals(email)){
                return false;
            }
        }
        return true;
    }
    public Member getSessionMember(){

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
        private final MemberService memberService; // declaration, technically null

        public WelcomeMenu(BufferedReader terminalReader, MenuRouter menuRouter, MemberService memberService) {
            super("Welcome", "/welcome", terminalReader, menuRouter);
            this.memberService = memberService;
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
