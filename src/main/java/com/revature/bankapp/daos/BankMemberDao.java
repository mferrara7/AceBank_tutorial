package com.revature.bankapp.daos;

import com.revature.bankapp.models.BankMembers;
import com.revature.bankapp.util.ConnectionFactory;
import com.revature.bankapp.util.CustomLogger;
import com.revature.bankapp.util.exceptions.InvalidUserInputException;
import com.revature.bankapp.util.exceptions.ResourcePersistanceException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class BankMemberDao implements Crudable<BankMembers> {

    CustomLogger customLogger = CustomLogger.getLogger(true); // pulls access to the customer logger info

    @Override
    public BankMembers create(BankMembers newMember) {
        try (Connection conn = ConnectionFactory.getConnectionFactory().getConnection()){
            String sql = "insert into members (email, password,balance) values (?,?,?)";
            // set up for a preparedStatement, prevents SQL injection

            //  prevents drop table members
            PreparedStatement ps = conn.prepareStatement(sql);

            // PrepStat now gets adjusted for values, not "?"
            ps.setString(1, newMember.getEmail());
            ps.setString(2, newMember.getPassword());
            ps.setDouble(3, newMember.getBalance());
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
    public List<BankMembers> findAll() {
        try (Connection conn = ConnectionFactory.getConnectionFactory().getConnection()){
            List<BankMembers> members = new LinkedList<>();


            String sql = "select * from members"; //all from members column


            Statement s = conn.createStatement(); // ensures statement is available
            ResultSet rs = s.executeQuery(sql); // finalizes query search for result from the database

            while(rs.next()){ // if there are more values, it continues to add, linked list
                BankMembers bankMembers = new BankMembers();

                bankMembers.setEmail(rs.getString("email"));
                bankMembers.setPassword(rs.getString("password"));
                bankMembers.setBalance(rs.getDouble("balance"));

                members.add(bankMembers);
            }

            return members; // will complete a new member as long as no error

        } catch (SQLException e) { // catchs any exception run errors, if none, moves on
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public BankMembers findById(String id) {
        try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()){ // attempt connection back to DB
            String sql = "select * from members where email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, id); // setting the parameter for ID search in the DB

            ResultSet rs = ps.executeQuery();

            if(!rs.next()){
                throw new InvalidUserInputException("Login information invalid, try again"); // confirms print of invalid info
            }
            BankMembers bankMembers = new BankMembers();

            bankMembers.setEmail(rs.getString("email"));
            bankMembers.setPassword(rs.getString("password"));
            bankMembers.setBalance(rs.getDouble("balance"));

            return bankMembers;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(BankMembers updatedObject) { // updates member if valid

        return false;
    }

    @Override
    public boolean delete(String id) { // removes false info

        return false;
    }

    public BankMembers loginCredentialCheck(String email, String password) { // valid login verification

        try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()){
            String sql = "select * from members where email = ? and password = ?"; // from DB table, email and PW columns
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if(!rs.next()){
                throw new InvalidUserInputException("Entered information is incorrect, please try again");
            }
            BankMembers bankMembers = new BankMembers();
            bankMembers.setEmail(rs.getString("email"));
            bankMembers.setPassword(rs.getString("password"));
            bankMembers.setBalance(rs.getDouble(("balance")));

            return bankMembers;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}