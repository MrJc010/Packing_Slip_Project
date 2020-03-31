package com.bizcom.search;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SearchServler
 */
@WebServlet("/search")
public class SearchServler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	/**
	 * GET
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("doGet Called");	
		System.out.println(request.getParameter("inputppid"));
		request.getRequestDispatcher("/WEB-INF/views/search/search.jsp").forward(request, response);
		System.out.println("after");
		System.out.println(request.getParameter("inputppid"));
		
	}



	/**
	 * POST
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost Called");
	}

}
