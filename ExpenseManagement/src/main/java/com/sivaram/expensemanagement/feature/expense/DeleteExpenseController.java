package com.sivaram.expensemanagement.feature.expense;

import java.io.IOException;

import com.google.gson.Gson;
import com.sivaram.expensemanagement.repository.dao.ExpenseDao;
import com.sivaram.expensemanagement.repository.dto.response.ExpenseResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/delete-expense")
public class DeleteExpenseController extends HttpServlet{
	private ExpenseDao expenseDao = new ExpenseDao();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		try {
			int recId = Integer.parseInt(req.getParameter("id"));
			int userId = Integer.parseInt(req.getParameter("userId"));
			
			ExpenseResponse expenseResponse = expenseDao.deleteExpense(recId, userId);
			String json = new Gson().toJson(expenseResponse);
			resp.getWriter().write(json);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
