package com.sivaram.expensemanagement.feature.expense;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sivaram.expensemanagement.repository.adapter.LocalDateAdapter;
import com.sivaram.expensemanagement.repository.adapter.LocalDateTimeAdapter;
import com.sivaram.expensemanagement.repository.dao.ExpenseDao;
import com.sivaram.expensemanagement.repository.db.DBConnection;
import com.sivaram.expensemanagement.repository.dto.Record;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/get-expense")
public class GetExpenseController extends HttpServlet{
	private ExpenseDao expenseDao = new ExpenseDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
		List<Record> recordList = new ArrayList<>();
		
		try{
			int userId = Integer.parseInt(req.getParameter("userId"));
			int month = Integer.parseInt(req.getParameter("month"));
		    int year = Integer.parseInt(req.getParameter("year"));
		    recordList = expenseDao.getExpense(userId, month, year);
		    Gson gson = new GsonBuilder()
		    		.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
		            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
		            .create();
		    String json = gson.toJson(recordList);
		    resp.getWriter().write(json);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
