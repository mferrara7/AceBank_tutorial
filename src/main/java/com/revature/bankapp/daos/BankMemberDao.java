package com.revature.bankapp.daos;

import com.revature.bankapp.models.BankMembers;
import com.revature.bankapp.services.MemberService;
import com.revature.bankapp.util.ConnectionFactory;
import com.revature.bankapp.util.CustomLogger;
import com.revature.bankapp.util.exceptions.InvalidUserInputException;
import com.revature.bankapp.util.exceptions.ResourcePersistanceException;

import java.lang.reflect.Member;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class BankMemberDao implements Crudable<Member> {

    CustomLogger customLogger = CustomLogger.getLogger(true); // pulls access to the customer logger info

    @Override
    public Member create(Member newMember) {
        try (Connection conn = ConnectionFactory.getConnectionFactory().getConnection()){
            String sql = "insert into members (email, password,balance) values (?,?,?)";
            // set up for a preparedStatement, prevents SQL injection

            //  prevents drop table members
            PreparedStatement ps = conn.prepareStatement(sql);

            // PrepStat now gets adjusted for values, not "?"
            BankMembers newMemberService = null;
            assert false;
            ps.setString(1, newMemberService.getEmail());
            ps.setString(2, newMemberService.getPassword());
            ps.setInt(3,0);
            // at this point it's a full sql statement with values where ? would have been

            int checkInsert = ps.executeUpdate(); // validates info entered for new members

            if(checkInsert == 0){
                throw new ResourcePersistanceException("Member was not entered into the database.");
                // not entered if info already existing
            }

            return newMember;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Member> findAll() {
        try (Connection conn = ConnectionFactory.getConnectionFactory().getConnection()){
            List<Member> members = new LinkedList<>();


            String sql = "select * from members"; //all from members column


            Statement s = conn.createStatement(); // ensures statement is available
            ResultSet rs = s.executeQuery(sql); // finalizes query search for result from the database

            while(rs.next()){ // if there are more values, it continues to add, linked list
                MemberService memberService = new MemberService();

                BankMembers.setEmail(rs.getString("email"));
                BankMembers.setPassword(rs.getString("password"));

                Member BankMembers = null;
                members.add(null);
            }

            return members; // will complete a new member as long as no error

        } catch (SQLException e) { // catchs any exception run errors, if none, moves on
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Member findById(String id) {
        try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()){ // attempt connection back to DB
            String sql = "select * from members where email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, id); // setting the parameter for ID search in the DB

            ResultSet rs = ps.executeQuery();

            if(!rs.next()){
                throw new InvalidUserInputException("Login information invalid, try again"); // confirms print of invalid info
            }

            MemberService memberService = new MemberService(); // creating new member info to be saved in database
            BankMembers.setEmail(rs.getString("Email"));
            BankMembers.setPassword(rs.getString("Password"));

            BankMembers BankMembers = null;
            return null;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Member updatedObject) { // updates member if valid

        return false;
    }

    @Override
    public boolean delete(String id) { // removes false info

        return false;
    }

    public Member loginCredentialCheck(String email, String password) { // valid login verification

        try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()){
            String sql = "select * from members where email = ? and password = ?"; // from DB table, email and PW columns
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if(!rs.next()){
                throw new InvalidUserInputException("Entered information is incorrect, please try again");
            }
            MemberService memberService = new MemberService();
            BankMembers.setEmail(rs.getString("email"));
            BankMembers.setPassword(rs.getString("password"));

            Member BankMembers = null;
            return null;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}