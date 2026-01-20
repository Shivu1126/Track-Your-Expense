package com.sivaram.expensemanagement.feature.expense;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.sivaram.expensemanagement.repository.dao.ExpenseDao;
import com.sivaram.expensemanagement.repository.dto.Summary;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/get-summary")
public class GetSummaryController extends HttpServlet{
	private ExpenseDao expenseDao = new ExpenseDao();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
		List<Summary> summaryList = new ArrayList<Summary>();
		try{
			int userId = Integer.parseInt(req.getParameter("userId"));
			summaryList = expenseDao.getSummary(userId);
			String json = new Gson().toJson(summaryList);
			resp.getWriter().write(json);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
