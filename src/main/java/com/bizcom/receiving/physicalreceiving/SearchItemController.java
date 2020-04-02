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

	private String ppid = "";

	public SearchItemController() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String isSuccess = "";

		try {
			isSuccess = (String) request.getSession().getAttribute("Successfull");
			if (isSuccess.equalsIgnoreCase("Successfull")) {
				request.setAttribute("setHiddenSuccess", "show");
				request.setAttribute("successMessage", request.getParameter("ppid") + " updated successfully!");
				request.getSession().setAttribute("Successfull", "");
			} else if (isSuccess.equalsIgnoreCase("Unsuccessfull")) {
				request.setAttribute("setHiddenSuccess", "hidden");
				request.setAttribute("successMessage", "");
				errorDisplay(request, response, request.getParameter("ppid") + " cannot update to database.");
				request.getSession().setAttribute("Successfull", "");
			} else {
				request.setAttribute("setHiddenSuccess", "hidden");
			}
		} catch (Exception e) {
			request.getSession().setAttribute("Successfull", "");
			request.setAttribute("setHiddenSuccess", "hidden");
		}

		// Load Search Item Page

		request.setAttribute("setHiddenError", "hidden");
		request.setAttribute("ppidValue", "");
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
		ppid = request.getParameter("ppid");

		if (ppid != null && ppid.length() > 0) {

			boolean flag = dbHandler.isExistInPrePPID(ppid);
			if (flag) {

				request.setAttribute("setHiddenError", "hidden");
				String url = request.getContextPath() + "/physicalreceiving?ppid=" + ppid;
				response.sendRedirect(url);
			} else {
				errorDisplay(request, response, ppid + " is not valid at this station.");
			}

		} else {
			errorDisplay(request, response, ppid + " is not valid at this station.");
		}

	}

	public void errorDisplay(HttpServletRequest request, HttpServletResponse response, String ppidx)
			throws ServletException, IOException {
		request.setAttribute("setHiddenError", "show");
		request.setAttribute("ppidValue", ppidx);
		request.setAttribute("errorMessage", ppidx );
		request.setAttribute("setHiddenSuccess", "hidden");
		searchItem(request, response);
	}
}
