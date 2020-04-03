package com.bizcom.shop_floor;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Create_New_Locations
 */
@WebServlet("/shopfloor/create_new_partnumber_step2")
public class Create_Part_Number_Step02 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	 private String pn = "";
	 private String sn = "";
	 private String desc = "";


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("create_new_locations called");
		
		System.out.println(request.getParameter("pn"));
		System.out.println(request.getParameter("model"));
		System.out.println(request.getParameter("desc"));
		request.getRequestDispatcher("/WEB-INF/views/shoop_floor/create_new_part_number_step_2.jsp").forward(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
