package com.sivaram.expensemanagement.feature.expense;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.google.gson.Gson;
import com.sivaram.expensemanagement.repository.dao.ExpenseDao;
import com.sivaram.expensemanagement.repository.dto.Record;
import com.sivaram.expensemanagement.repository.dto.response.ExpenseResponse;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/update-expense")
public class UpdateExpenseController extends HttpServlet{
	private ExpenseDao expenseDao = new ExpenseDao();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp){
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		try {
			int recId = Integer.parseInt(req.getParameter("id"));
			int userId = Integer.parseInt(req.getParameter("userId"));
			int categoryId = Integer.parseInt(req.getParameter("categoryId"));
			BigDecimal amount = new BigDecimal(req.getParameter("amount"));
			LocalDate expenseDate = LocalDate.parse(req.getParameter("expenseDate"));
			String description = req.getParameter("description");
			
			Record record = new Record(recId,userId, categoryId, null, amount, expenseDate, null, description);
			ExpenseResponse expenseResponse = expenseDao.updateExpense(record);
			String json = new Gson().toJson(expenseResponse);
			resp.getWriter().write(json);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

}
