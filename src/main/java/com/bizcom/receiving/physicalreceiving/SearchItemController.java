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
		request.setAttribute("setHiddenSuccess", "hidden");
		request.setAttribute("setHiddenError", "hidden");
		if(dbHandler.checkAuthentication(request)) {		
			System.out.println("Run doget");
			try {
				isSuccess = (String) request.getSession().getAttribute("Successfull");
				if (isSuccess.equalsIgnoreCase("Successfull")) {
					System.out.println("found Successfull");
					request.setAttribute("setHiddenSuccess", "show");
					request.setAttribute("successMessage", request.getParameter("ppid") + " updated successfully!");
					request.getSession().setAttribute("Successfull", "");
				} else if (isSuccess.equalsIgnoreCase("Unsuccessfull")) {

					System.out.println("found Unsuccessfull");
					request.setAttribute("setHiddenSuccess", "hidden");
					request.setAttribute("successMessage", "");
					errorDisplay(request, response, request.getParameter("ppid") + " cannot update to database.");
					request.getSession().setAttribute("Successfull", "");
				} else {
					System.out.println("not found ");
					request.setAttribute("setHiddenSuccess", "hidden");
					request.setAttribute("setHiddenError", "hidden");
					request.setAttribute("ppidValue", "");
				}
			} catch (Exception e) {
				System.out.println("catch");
				// first time access
				request.getSession().setAttribute("Successfull", "");
				request.setAttribute("setHiddenSuccess", "hidden");
				request.setAttribute("setHiddenError", "hidden");
				request.setAttribute("ppidValue", "");
			}
			request.getRequestDispatcher("/WEB-INF/views/receiving_station/physicalreceiving/searchitem.jsp").forward(request, response);

		}else {
			response.sendRedirect(request.getContextPath() + "/signin?pagerequest=searchitem");
		}
	}

	private void searchItem(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/receiving_station/physicalreceiving/searchitem.jsp")
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
