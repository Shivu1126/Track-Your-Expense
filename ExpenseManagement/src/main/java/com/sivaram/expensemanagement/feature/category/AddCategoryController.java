package com.sivaram.expensemanagement.feature.category;

import java.io.IOException;

import com.google.gson.Gson;
import com.sivaram.expensemanagement.repository.dao.ExpenseDao;
import com.sivaram.expensemanagement.repository.dto.response.ExpenseResponse;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
	
@WebServlet("/add-category")
public class AddCategoryController extends HttpServlet{
	private ExpenseDao expenseDao = new ExpenseDao();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		try {
			int userId = Integer.parseInt(req.getParameter("userId"));
			String categoryName = req.getParameter("categoryName");
			ExpenseResponse expenseResponse =  expenseDao.addCategory(userId, categoryName);
			String json = new Gson().toJson(expenseResponse);
			
			resp.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
}
