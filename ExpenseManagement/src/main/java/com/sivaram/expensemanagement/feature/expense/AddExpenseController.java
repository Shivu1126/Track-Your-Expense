package com.sivaram.expensemanagement.feature.expense;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.sivaram.expensemanagement.repository.dao.ExpenseDao;
import com.sivaram.expensemanagement.repository.dto.Record;
import com.sivaram.expensemanagement.repository.dto.response.ExpenseResponse;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/add-expense")
public class AddExpenseController extends HttpServlet{
	private ExpenseDao expenseDao = new ExpenseDao();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		try {
			int userId = Integer.parseInt(req.getParameter("userId"));
			int categoryId = Integer.parseInt(req.getParameter("categoryId"));
			System.out.println(req.getParameter("amount"));
			BigDecimal amount = new BigDecimal(req.getParameter("amount"));
			LocalDate expenseDate = LocalDate.parse(req.getParameter("expenseDate"));
			String description = req.getParameter("description");
			Record record = new Record(0,userId, categoryId, null, amount, expenseDate, null, description);
			ExpenseResponse expenseResponse = expenseDao.addExpense(record);
			String json = new Gson().toJson(expenseResponse);
			resp.getWriter().write(json);
		}catch (Exception e) {
			
			System.out.println(e.getMessage());
		}
	}
}
