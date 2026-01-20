package com.sivaram.expensemanagement.feature.auth;

import java.io.IOException;

import com.google.gson.Gson;
import com.sivaram.expensemanagement.repository.dao.AuthDao;
import com.sivaram.expensemanagement.repository.dto.response.AuthResponse;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginController extends HttpServlet{
	private AuthDao authDao = new AuthDao();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp){
		AuthResponse authResponse = new AuthResponse();
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
		try {
			String email = req.getParameter("email");
			String password = req.getParameter("password");
			int userId = authDao.loginUser(email, password);
			if(userId > 0)
			{
				authResponse.setId(userId);
				authResponse.setMessage("Login Successfully..");
				authResponse.setSuccess(true);
			}
			else {
				authResponse.setId(userId);
				authResponse.setMessage("Invalid email or password..");
				authResponse.setSuccess(false);
			}
		}
		catch (Exception e) {
			e.printStackTrace(); 
		    authResponse.setSuccess(false);
		    authResponse.setId(-1);
		    authResponse.setMessage("Unknown server error");
		}
		String json = new Gson().toJson(authResponse);
		try {
			resp.getWriter().write(json);
		} catch (IOException io) {
			io.printStackTrace();
		}
	}
}
