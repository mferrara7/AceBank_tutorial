package com.revature.bankapp.daos;

import com.revature.bankapp.util.ConnectionFactory;
import com.revature.bankapp.util.CustomLogger;
import com.revature.bankapp.util.exceptions.InvalidUserInputException;
import com.revature.bankapp.util.exceptions.ResourcePersistanceException;

import java.lang.reflect.Member;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class BankMemeberDao implements Crudable<Member> {

    CustomLogger customLogger = CustomLogger.getLogger(true);


    @Override
    public Member create(Member newMember) {
        try (Connection conn = ConnectionFactory.getConnectionFactory().getConnection()){
            String sql = "insert into members (email, password,balance) values (?,?,?)"; // we want to set up for a preparedStatement (this prevents SQL injection)

            // ; drop table members will be prevented
            PreparedStatement ps = conn.prepareStatement(sql);

            // our ps now needs to be adjusted with the appropriate values instead of the ?
            ps.setString(1, newMember.getEmail());
            ps.setString(2, newMember.getPassword());
            ps.setInt(3,0);
            // at this point it's a full sql statement with values where ? are

            int checkInsert = ps.executeUpdate(); // INSERT, UPDATE or DELETE

            if(checkInsert == 0){
                throw new ResourcePersistanceException("Member was not entered into the database.");
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


            String sql = "select * from members"; // sql string should always be a sql statement


            Statement s = conn.createStatement(); // establish a statement is available
            ResultSet rs = s.executeQuery(sql); // actually execute the query statement and take in a ResultSet (the data from our database)

            while(rs.next()){ // as long as there is a next value (another record) it will continue to add to the linkedList
                Member member = new Member();

                member.setEmail(rs.getString("email"));
                member.setPassword(rs.getString("password"));

                // we now have a completed member
                members.add(member);
            }

            return members;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Member findById(String id) {
        try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()){
            String sql = "select * from members where email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();

            if(!rs.next()){
                throw new InvalidUserInputException("Enter information is incorrect for login, please try again");
            }

            Member member = new Member();
            member.setEmail(rs.getString("email"));
            member.setPassword(rs.getString("password"));

            return member;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Member updatedObject) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    public Member loginCredentialCheck(String email, String password) {

        try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()){
            String sql = "select * from members where email = ? and password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if(!rs.next()){
                throw new InvalidUserInputException("Enter information is incorrect for login, please try again");
            }
            Member member = new Member();
            member.setEmail(rs.getString("email"));
            member.setPassword(rs.getString("password"));

            return member;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}