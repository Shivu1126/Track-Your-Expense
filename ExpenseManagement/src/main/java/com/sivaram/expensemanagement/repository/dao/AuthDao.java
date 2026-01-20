package com.sivaram.expensemanagement.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.sivaram.expensemanagement.repository.db.DBConnection;
import com.sivaram.expensemanagement.repository.dto.User;

public class AuthDao {
	public boolean emailExists(String email) {
        String sql = "SELECT id FROM users WHERE email = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        }
        catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
    }

	public int registerUser(User user) {
		String sql = "insert into users (name, email,password) values(?, ?, ?)";
		try(Connection con = DBConnection.getConnection();
			PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			statement.setString(1, user.getName());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getPassword());
			
			int row = statement.executeUpdate();
			if(row>0) {
				ResultSet rs = statement.getGeneratedKeys();
				if(rs.next())
					return rs.getInt(1);
			}
			return -1;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return -1;
	}
	
	public int loginUser(String email, String password) {
		String sql = "select id from users where email= ? and password = ?";
		try(Connection con = DBConnection.getConnection();
			PreparedStatement statement = con.prepareStatement(sql)){
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet rs = statement.executeQuery();
			if(rs.next())
				return rs.getInt("id");
			return -1;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return-1;
	}
}
