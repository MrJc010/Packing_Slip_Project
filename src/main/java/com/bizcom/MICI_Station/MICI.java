package com.bizcom.MICI_Station;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bizcom.database.DBHandler;

/**
 * Servlet implementation class MICI
 */
@WebServlet("/mici")
public class MICI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MICI() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String page = request.getParameter("page");
		if(page == null) {
			errorPage(request, response);
		}
		switch (page) {
		case "display":
			System.out.println("Page: " + page);
			miciDisplay(request, response);
			break;
		case "check":
			System.out.println("Page: " + page);
			checkMICI(request, response);
			break;
		default:
			errorPage(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String page = request.getParameter("page");
		if(page == null) {
			errorPage(request, response);
		}
		switch (page) {
		case "pass":
			miciDisplay(request, response);
			break;
		case "fail":
			miciDisplay(request, response);
			break;
		default:
			request.getRequestDispatcher("/WEB-INF/views/receiving_station/pre_alert/pre_alert.jsp").forward(request,
					response);
			break;
		}
	}

	public void miciDisplay(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("title", "MICI");
		request.getRequestDispatcher("/WEB-INF/views/mici_station/mici.jsp").forward(request, response);
	}

	public void errorPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("title", "Error page");
		request.getRequestDispatcher("/WEB-INF/error/404.jsp").forward(request, response);

	}
	
	public void checkMICI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = request.getParameter("page");
		String ppid = request.getParameter("ppidNumber");
		String sn = request.getParameter("serialnumber");
		DBHandler dbHandler = new DBHandler();
		String[] miciInfo = dbHandler.getMICIInfo(ppid);

		
		request.setAttribute("problemcode", miciInfo[0]);
		request.setAttribute("problemdecription", miciInfo[1]);
		
		// fetch information...
	
		
		request.getRequestDispatcher("/WEB-INF/views/mici_station/mici.jsp").forward(request, response);
	}
}
