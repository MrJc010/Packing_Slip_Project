package com.bizcom.receiving.physicalreceiving;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MoreThan5Case
 */
@WebServlet(description = "PPID received more than 5 times", urlPatterns = { "/morethanfive" })
public class MoreThan5Case extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/views/receiving_station/physicalreceiving/morethan5.jsp")
				.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String buttonValue = request.getParameter("buttonAction");

		if (buttonValue != null) {
			request.getSession().removeAttribute("Alert_More_Than_5");
			request.getSession().removeAttribute("Successfull");
			if (buttonValue.equalsIgnoreCase("RECYCLE")) {
				System.out.println("MOVE TO MoveToScrap01");
				//dbhandler.MoveToScrap01(rmaNum, mac, ppid, pn, sn, revision, cpu_sn, mfgPN, lot, description,	problemCode, dps);
				System.out.println("=========RE-LOCATION BACK SEARCH PAGE=========");
				response.sendRedirect(request.getContextPath()+"/searchitem");
			} else {
				response.sendRedirect(request.getContextPath()+"/searchitem");
				return;
			}

		}

	}

}
