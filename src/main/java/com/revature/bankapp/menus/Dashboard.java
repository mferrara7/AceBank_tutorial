package com.revature.bankapp.menus;

import com.revature.bankapp.models.BankMembers;
import com.revature.bankapp.services.MemberService;
import com.revature.bankapp.util.ConnectionFactory;
import com.revature.bankapp.util.CustomLogger;
import com.revature.bankapp.util.MenuRouter;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dashboard extends Menu{
    CustomLogger customLogger = CustomLogger.getLogger(true);
    private final MemberService memberService;

    public Dashboard(BufferedReader terminalReader, MenuRouter menuRouter, MemberService memberService) {
        super("Dashboard", "/dashboard", terminalReader, menuRouter);
        this.memberService = memberService;
    }

    @Override
    public void render() throws IOException {

        System.out.println(BankMembers.getEmail() +  "Entered dashboard!\n 1) Make a Withdrawal \n 2) Make a Deposit \n 3) View current balance \n 4) Logout" );

        String userInput = terminalReader.readLine();

        switch (userInput){
            case "1":
                System.out.println("Enter Withdrawal amount?");
                double withdrawalAmount = 0;
                try {
                    withdrawalAmount = Double.parseDouble(terminalReader.readLine());
                } catch (NumberFormatException e) {
                    customLogger.warn("Need to enter an amount, try again");
                }
                if(withdrawalAmount>0) {
                    try (Connection conn = ConnectionFactory.getConnectionFactory().getConnection()) {
                        String sql = "select balance from members where email = ?";
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ps.setString(1, memberService.getSessionMember().getName());
                        // getName() is used for getEmail(), kept getting a rename error that couldnt figure how to fix
                        ResultSet rs = ps.executeQuery();
                        double newBalance = 0;
                        if (rs.next()) {
                            newBalance = rs.getDouble("balance") - withdrawalAmount;
                        }
                        if (newBalance >= 0) {
                            System.out.println("Your previous balance was $" + rs.getInt("balance"));
                            System.out.println("You withdrew $" + Double.toString(withdrawalAmount));
                            sql = "update members set balance=? where email = ?";
                            ps = conn.prepareStatement(sql);
                            ps.setDouble(1, newBalance);
                            ps.setString(2, memberService.getSessionMember().getName());
                            ps.executeUpdate();
                            System.out.println("New balance is $" + Double.toString(newBalance));
                            menuRouter.transfer("/dashboard");
                        } else {
                            System.out.println("Withdrawal amount is greater than balance");
                            menuRouter.transfer("/dashboard");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    System.out.println("Your withdraw amount cannot exceed current balance");
                    menuRouter.transfer("/dashboard");
                }
                break;
            case "2":
                System.out.println("Enter Deposit Amount?");
                double depositAmount = 0;
                try {
                    depositAmount = Double.parseDouble(terminalReader.readLine());
                } catch (NumberFormatException e) {
                    customLogger.warn("Need to enter an Integer, try again");
                }
                if(depositAmount>0){
                    try (Connection conn = ConnectionFactory.getConnectionFactory().getConnection()) {
                        String sql = "select balance from members where email = ?";
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ps.setString(1, memberService.getSessionMember().getName());
                        ResultSet rs = ps.executeQuery();
                        double newBalance=0;
                        if(rs.next()) {
                            newBalance=rs.getDouble("balance")+depositAmount;
                        }
                        System.out.println("Your previous balance was $" + rs.getInt("balance"));
                        System.out.println("You deposited $"+Double.toString(depositAmount));
                        sql="update members set balance=? where email = ?";
                        ps = conn.prepareStatement(sql);
                        ps.setDouble(1, newBalance);
                        ps.setString(2, memberService.getSessionMember().getName());
                        ps.executeUpdate();
                        System.out.println("Your new balance is $" +Double.toString(newBalance));
                        menuRouter.transfer("/dashboard");
                    } catch (SQLException e){
                        e.printStackTrace();
                    }
                }
                else{
                    System.out.println("Your deposit amount has to be greater than zero");
                    menuRouter.transfer("/dashboard");

                }
                break;
            case "3":
                System.out.println("User selected View Balance");
                try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()){
                    String sql = "select balance from members where email = ?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, memberService.getSessionMember().getName());
                    ResultSet rs = ps.executeQuery();
                    if(rs.next()) {
                        System.out.println("Your balance is $" + rs.getDouble("balance"));
                        menuRouter.transfer("/dashboard");
                    }

                } catch (SQLException e){
                    e.printStackTrace();
                }
                break;
            case "4":
                System.out.println("Logout complete");
                memberService.logout();
                menuRouter.transfer("/welcome");
                break;
            default:
                System.out.println("Invalid selection please try again");
                menuRouter.transfer("/dashboard");
        }

    }
}
