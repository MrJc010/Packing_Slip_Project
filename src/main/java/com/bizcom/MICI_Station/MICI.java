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
	private String ppid;
	DBHandler dbHandler = new DBHandler();

	private static final String PHYSICAL_RECEIVING = "PHYSICAL_RECEIVING";
	private static final String MICI = "MICI";
	private static final String REPAIR01_FAIL = "REPAIR01_FAIL";
	private static final String REPAIR01_PASS = "REPAIR01_PASS";
	private static final String REPAIR01 = "REPAIR01";
	private static final String QC1 = "QC1";

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
		if (page == null) {
			errorPage(request, response);
		} else {
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
			System.out.println(request.getParameter("errorCode"));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		//

		if (action.equalsIgnoreCase("passButton")) {
			// go to QC1
			System.out.println("PASSSS");
		} else if (action.equalsIgnoreCase("failButton")) {
			String errorCode = request.getParameter("errorCode");
			System.out.println("OPTION: " + errorCode);
			System.out.println("RESULT : " + dbHandler.updateCurrentStation("MICI", "REPAIR01_FAIL", ppid));

			System.out.println("FAILLL");
		} else {
			System.out.println("ERROR");
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
		ppid = request.getParameter("ppidNumber");
		String sn = request.getParameter("serialnumber");
		String[] miciInfo = dbHandler.getMICIInfo(ppid);

		request.setAttribute("problemcode", miciInfo[0]);
		request.setAttribute("problemdecription", miciInfo[1]);

		// fetch information...
		if (ppid != null) {
			String[] currenStaions = dbHandler.getCurrentStation(ppid);

			if (currenStaions[0].equalsIgnoreCase(PHYSICAL_RECEIVING) && currenStaions[1].equalsIgnoreCase(MICI)) {
				// no information here

			} else if (currenStaions[0].equalsIgnoreCase(REPAIR01)
					&& currenStaions[1].equalsIgnoreCase(REPAIR01_PASS)) {
				// print repair infomation
				// change from REPAIR01 -> MICI
				dbHandler.updateCurrentStation(REPAIR01_PASS, MICI, ppid);
			} else {
				System.out.println("SOME THING ELSE");
				// show errror
			}
		}
		request.getRequestDispatcher("/WEB-INF/views/mici_station/mici.jsp").forward(request, response);
	}
}
