package com.bizcom.authentication;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bizcom.database.DBHandler;

/**
 * Servlet implementation class singin
 */
@WebServlet("/signin")
public class singin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DBHandler db = new DBHandler();
	private String page="";
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public singin() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		page = request.getParameter("pagerequest");
		request.getRequestDispatcher("/WEB-INF/signin/signin.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("GET APGE ?? " + page);
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (!username.isEmpty() && !password.isEmpty()) {
			if (db.signIn(username, password)) {
				String role = db.getUserRole(username);
				request.getSession().setAttribute("username", username);
				request.getSession().setAttribute("user_role", role);
				if(page.length()== 0) {					

//					request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);	
					System.out.println("HEREEEE" + request.getServletPath());
					response.sendRedirect(request.getContextPath()+"/" );
				}else {

					response.sendRedirect(request.getContextPath() + "/"+page);
					}
			}
		} else {
			response.sendRedirect(request.getContextPath() + "/signin");
		}
	}

}
