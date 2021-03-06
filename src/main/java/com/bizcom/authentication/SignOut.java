package com.bizcom.authentication;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SignOut
 */
@WebServlet("/signout")
public class SignOut extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("User Logged Out -== Delete all session");
		System.out.println("Test getSession ( NULL ? )" + request.getSession().getId());
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath()+"/");
	}


}
