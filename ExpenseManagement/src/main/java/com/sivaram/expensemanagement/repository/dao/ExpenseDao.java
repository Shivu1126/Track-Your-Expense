package com.sivaram.expensemanagement.repository.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.sivaram.expensemanagement.repository.db.DBConnection;
import com.sivaram.expensemanagement.repository.dto.Category;
import com.sivaram.expensemanagement.repository.dto.Record;
import com.sivaram.expensemanagement.repository.dto.Summary;
import com.sivaram.expensemanagement.repository.dto.response.ExpenseResponse;

public class ExpenseDao {	
	
	public ExpenseResponse addExpense(Record record) {
		String sql = "insert into records (user_id, category_id, description, amount, expense_date) values (?, ?, ?, ?, ?)";
		try(Connection con = DBConnection.getConnection();
				PreparedStatement statement = con.prepareStatement(sql)){
			statement.setInt(1, record.getUserId());
			statement.setInt(2, record.getCategoryId());
			statement.setString(3, record.getDescription());
			statement.setBigDecimal(4,record.getAmount());
			statement.setDate(5, Date.valueOf(record.getExpenseDate()));
			
			int row = statement.executeUpdate();
			
			if(row>0)
				return new ExpenseResponse(true, "Record Added Successfully");
			return new ExpenseResponse(false, "Something went wrong !");
		}
		catch (Exception e) {
			return new ExpenseResponse(false, "Server issue !!");
		}
	}
	
	public ExpenseResponse addCategory(int userId, String categoryName) {
		if(isAlreadyExist(userId, categoryName)) {
			return new ExpenseResponse(false, "Category Already Exist");
		}
		else {
			String sql = "insert into categories (user_id, name) values(?,?)";
			try(Connection con = DBConnection.getConnection();
				PreparedStatement statement = con.prepareStatement(sql)){
				statement.setInt(1,userId);
				statement.setString(2, categoryName);
				int row = statement.executeUpdate();
				if(row>0)
					return new ExpenseResponse(true, "Added Successfully");
				else
					return new ExpenseResponse(false, "Something went wrong!");
			}
			catch (Exception e) {
				return new ExpenseResponse(false, "Server Error!!");
			}
		}
	}

	private boolean isAlreadyExist(int userId, String categoryName) {
		String sql = "select * from categories where name= ? and user_id= ?";
		try(Connection con = DBConnection.getConnection();
				PreparedStatement statement = con.prepareStatement(sql)){

			 statement.setString(1, categoryName);
			 statement.setInt(2,userId);
			 ResultSet rs = statement.executeQuery();
			 return rs.next();
		}catch(Exception e) {
			return false;
		}
	}

	public ExpenseResponse updateExpense(Record record) {
		String sql = "update records set category_id=?, description=?, amount=?, expense_date=? where id = ? and user_id=?";
		try(Connection con = DBConnection.getConnection();
			PreparedStatement statement = con.prepareStatement(sql)){
			statement.setInt(1, record.getCategoryId());
			statement.setString(2, record.getDescription());
			statement.setBigDecimal(3, record.getAmount());
			statement.setDate(4, Date.valueOf(record.getExpenseDate()));
			statement.setInt(5, record.getId());
			statement.setInt(6, record.getUserId());
			
			int row = statement.executeUpdate();
			if(row>0)
				return new ExpenseResponse(true, "Update successfully");
			return new ExpenseResponse(false, "Invalid id..");
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ExpenseResponse(false, "Something went wrong");
		}
	}

	public ExpenseResponse deleteExpense(int recId, int userId) {
		String sql = "delete from records where id = ? and user_id = ? ";
		try(Connection con = DBConnection.getConnection();
				PreparedStatement statement = con.prepareStatement(sql)){
			statement.setInt(1, recId);
			statement.setInt(2, userId);
			int rows = statement.executeUpdate();
			if(rows>0)
				return new ExpenseResponse(true, "Deleted Successfully..");
			return new ExpenseResponse(false, "Invalid id..");
		}
		catch (Exception e) {
			return new ExpenseResponse(false, "Something went wrong");
		}
	}
	
	public List<Category> getCategory(int userId){
		String sql = "select id, name from categories where user_id = ?";
		List<Category> categoryList = new ArrayList<Category>();
		try(Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql)) {
			
				ps.setInt(1, userId);
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()) {
					int id = rs.getInt("id");
					String name = rs.getString("name");
					Category category = new Category(id, name);
					categoryList.add(category);
				}
				
			}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return categoryList;
	}
	
	public List<Record> getExpense(int userId, int month, int year){
		String sql = "SELECT e.id, e.user_id, e.category_id, e.description, e.amount, e.created_at, e.expense_date, c.name AS category FROM records e "
				+ "JOIN categories c ON e.category_id = c.id WHERE e.user_id = ? "
				+ "AND MONTH(e.expense_date) = ? "
				+ "AND YEAR(e.expense_date) = ? "
				+ "ORDER BY e.expense_date DESC;";
		
		List<Record> recordList = new ArrayList<>();
		try(Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql)) 
		{
			    ps.setInt(1, userId);
			    ps.setInt(2, month);
			    ps.setInt(3, year);
			    
			    ResultSet rs = ps.executeQuery();

			    while(rs.next()) {
			    	Record record = new Record(
			    			rs.getInt("id"),
			    			userId,
			    			rs.getInt("category_id"),
			    			rs.getString("category"),
			    			rs.getBigDecimal("amount"),
			    			rs.getDate("expense_date").toLocalDate(),
			    			rs.getTimestamp("created_at").toLocalDateTime(),
			    			rs.getString("description")	);
			    	recordList.add(record);
			    }
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return recordList;
	}
	
	public List<Summary> getSummary(int userId){
		
		String sql = """
				SELECT
					YEAR(expense_date) AS year,
					MONTH(expense_date) AS month,
					SUM(CASE WHEN amount < 0 THEN ABS(amount) ELSE 0 END) AS totalExpense,
					SUM(CASE WHEN amount > 0 THEN amount ELSE 0 END) AS totalIncome
				FROM records
				WHERE user_id = ?
				GROUP BY YEAR(expense_date), MONTH(expense_date)
				ORDER BY year DESC, month DESC
				""";
		List<Summary> summaryList = new ArrayList<Summary>();
		try(Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) 
		{
			ps.setInt(1, userId);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int year = rs.getInt("year");
				int month = rs.getInt("month");
				System.out.println(rs.getBigDecimal("totalExpense"));
				BigDecimal totalExpense = rs.getBigDecimal("totalExpense");
				BigDecimal totalIncome = rs.getBigDecimal("totalIncome");
				if (totalExpense == null) totalExpense = BigDecimal.ZERO;
				if (totalIncome == null) totalIncome = BigDecimal.ZERO;
				BigDecimal balance = totalIncome.subtract(totalExpense);
				
				Summary summary = new Summary();
				summary.setYear(year);
				summary.setMonth(month);
				summary.setTotalExpense(totalExpense);
				summary.setTotalIncome(totalIncome);
				summary.setTotalBalance(balance);
				
				summaryList.add(summary);
				System.out.println(summary.getTotalBalance());
				System.out.println(summary.getTotalExpense());
				System.out.println(summary.getTotalIncome());
				System.out.println();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return summaryList;
	}
}
