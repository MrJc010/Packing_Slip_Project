package com.bizcom.receiving.physicalreceiving;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bizcom.database.DBHandler;

/**
 * Servlet implementation class PhysicalReceivingController
 */
@WebServlet("/searchitem")
public class SearchItemController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DBHandler dbHandler = new DBHandler();

	public SearchItemController() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().removeAttribute("Successfull");
		// Load Search Item Page
		request.setAttribute("setHiddenError", "hidden");
		searchItem(request, response);

	}

	private void searchItem(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/receiving_station/physicalreceiving/searchItems.jsp")
				.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().removeAttribute("Successfull");
		request.setAttribute("setHiddenError", "hidden");
		String ppid = request.getParameter("ppid");

		if (ppid != null && ppid.length() > 0) {

			boolean flag = dbHandler.isExistInPrePPID(ppid);
			if (flag) {

				request.setAttribute("setHiddenError", "hidden");
				String url = request.getContextPath() + "/physicalreceiving?ppid=" + ppid;
				response.sendRedirect(url);
			} else {

				request.setAttribute("setHiddenError", "show");
				searchItem(request, response);
			}

		} else {

			request.setAttribute("setHiddenError", "show");
			searchItem(request, response);
		}

	}

}
