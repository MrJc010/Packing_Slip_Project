package com.bizcom.receiving.physicalreceiving;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PhysicalReceivingController
 */
@WebServlet("/searchitem")
public class SearchItemController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SearchItemController() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Load Search Item Page
		searchItem(request, response);

	}

	private void searchItem(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("title", "Search Item");
		request.getRequestDispatcher("/WEB-INF/views/receiving_station/physicalreceiving/searchItems.jsp")
				.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ppid = request.getParameter("ppid");
		String dps = request.getParameter("dps");
		String url = request.getContextPath() + "/physicalreceiving?ppid=" + ppid + "&dps=" + dps;
		response.sendRedirect(url);
	}

}
