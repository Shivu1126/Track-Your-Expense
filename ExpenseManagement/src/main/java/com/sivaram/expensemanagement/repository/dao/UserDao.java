package com.sivaram.expensemanagement.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.sivaram.expensemanagement.repository.db.DBConnection;
import com.sivaram.expensemanagement.repository.dto.User;

public class UserDao {
	
	public User getUser(int userId) {
		String sql = "select id, name, email from users where id = ?";
		
		try(Connection con = DBConnection.getConnection();
				PreparedStatement statement = con.prepareStatement(sql)){
			statement.setInt(1, userId);
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				return new User(id, name, email, "");
			}
			return new User(-1, "", "", "");			
		}catch (Exception e) {
			return new User(-1, "", "", "");			
		}
		
	}
}
