package com.sivaram.expensemanagement.feature.category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.sivaram.expensemanagement.repository.dao.ExpenseDao;
import com.sivaram.expensemanagement.repository.db.DBConnection;
import com.sivaram.expensemanagement.repository.dto.Category;

@WebServlet("/get-category")
public class GetCategoryController extends HttpServlet {
	private ExpenseDao expenseDao = new ExpenseDao();
	protected void doGet(HttpServletRequest req, HttpServletResponse resp){
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
		List<Category> categoryList = new ArrayList<Category>();
		try {
			int userId = Integer.parseInt(req.getParameter("userId"));
			categoryList = expenseDao.getCategory(userId);
			String json = new Gson().toJson(categoryList);
			resp.getWriter().write(json);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
