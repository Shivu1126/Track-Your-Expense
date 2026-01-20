package com.sivaram.expensemanagement.feature.user;

import com.google.gson.Gson;
import com.sivaram.expensemanagement.repository.dao.UserDao;
import com.sivaram.expensemanagement.repository.dto.User;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/get-user")
public class GetUserController extends HttpServlet{
	private UserDao userDao = new UserDao();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		try {
			int userId = Integer.parseInt(req.getParameter("userId"));
			User user = userDao.getUser(userId);
			String json = new Gson().toJson(user);
			resp.getWriter().write(json);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
