package com.bizcom.shop_floor;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/shopfloor/create_new_partnumber")
public class Create_New_PartNumber_ShopFloor extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/views/shoop_floor/create_new_project_step_1.jsp").forward(request,
				response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String pn = request.getParameter("partnumber");
		String model = request.getParameter("model");
		String desc = request.getParameter("description");
		String url = "";
		if(!pn.isEmpty() && !model.isEmpty() && desc.isEmpty()) {
			url = "/shopfloor/create_new_locations?pn=" + pn + "&model=" + model + "&desc=" + desc;	
			response.sendRedirect(request.getContextPath() + url);
		}
		else {
			// pop up warnring
		}
		
	}
}
