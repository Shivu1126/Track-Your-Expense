package com.sivaram.expensemanagement.feature.auth;


import java.io.IOException;

import com.google.gson.Gson;
import com.sivaram.expensemanagement.repository.dao.AuthDao;
import com.sivaram.expensemanagement.repository.dto.User;
import com.sivaram.expensemanagement.repository.dto.response.AuthResponse;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterController extends HttpServlet{
	private AuthDao authDao = new AuthDao();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp){

		AuthResponse authResponse = new AuthResponse();
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		try {
		    String userName = req.getParameter("name");
		    String email = req.getParameter("email");
		    String password = req.getParameter("password");

		    if (authDao.emailExists(email)) {
		        authResponse.setSuccess(false);
		        authResponse.setId(-1);
		        authResponse.setMessage("Email already exists !!");
		    }
		    else {
		        User user = new User(0, userName, email, password);
		        int userId = authDao.registerUser(user);

		        if (userId > 0) {
//		        	System.out.println("user-id "+userId);
		            authResponse.setSuccess(true);
		            authResponse.setId(userId);
		            authResponse.setMessage("Register successfully done");
		        } else {
		            authResponse.setSuccess(false);
		            authResponse.setId(-1);
		            authResponse.setMessage("Register failed");
		        }
		    }

		} catch (Exception e) {
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
