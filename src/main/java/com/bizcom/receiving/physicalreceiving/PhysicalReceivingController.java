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
@WebServlet("/physicalreceiving")
public class PhysicalReceivingController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PhysicalReceivingController() {
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

	private void errorPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("title", "Error page");
		request.getRequestDispatcher("/WEB-INF/error/404.jsp").forward(request, response);
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

	}

}
