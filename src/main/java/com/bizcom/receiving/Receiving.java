package com.bizcom.receiving;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Receiving
 */
@WebServlet(description = "Receiving Station", urlPatterns = { "/receiving" })
public class Receiving extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Receiving() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String page = request.getParameter("page");
		String pathJSP = "/WEB-INF/views/receiving_station/receiving_home.jsp";
		switch(page) {
		case "packing_slip":
			pathJSP = "/WEB-INF/views/receiving_station/packingslip/list-packingSlip.jsp";
			break;
		case "ppid_list":
			pathJSP = "/WEB-INF/views/receiving_station/ppid/list-ppid.jsp";
			break;
		default :
			pathJSP = "/WEB-INF/views/receiving_station/receiving_home.jsp";
			break;
			
		}
		request.getRequestDispatcher(pathJSP).forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
