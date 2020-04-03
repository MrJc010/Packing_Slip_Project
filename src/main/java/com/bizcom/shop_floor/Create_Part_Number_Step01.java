package com.bizcom.shop_floor;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/shopfloor/create_new_partnumber_step1")
public class Create_Part_Number_Step01 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("setErrorStep01", "hidden");
		request.getRequestDispatcher("/WEB-INF/views/shoop_floor/create_new_part_number_step_1.jsp").forward(request,
				response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("CALL doPost create_new_partnumber_step1");
		String pn = request.getParameter("partnumber");
		String model = request.getParameter("model");
		String desc = request.getParameter("description");
		String url = "";
		if(pn.isEmpty() || model.isEmpty() || desc.isEmpty()) {
		
			request.setAttribute("setErrorStep01", "show");
			request.setAttribute("errorMessage", "Please fill out all the form field to continue.");
			request.getRequestDispatcher("/WEB-INF/views/shoop_floor/create_new_part_number_step_1.jsp").forward(request,
					response);
		}else
		{
		url = "/shopfloor/create_new_partnumber_step2?pn=" + pn + "&model=" + model + "&desc=" + desc;
		response.sendRedirect(request.getContextPath() + url);
		
		}

	}
}
