package com.bizcom.MICI_Station;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
	private static final String START ="START";
	private static Set<String> errorCodeSet;

	public MICI() {
		super();
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		errorCodeSet = new HashSet<String>();
		request.setAttribute("seterrorhiddenMICI", "hidden");
		request.setAttribute("currentCountMICI", 2);
		String page = request.getParameter("page");
		if (page == null) {
			miciDisplay(request, response);
		} else {
			switch (page) {
			case "display":				
				miciDisplay(request, response);
				break;
			case "check":				
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
			System.out.println(request.getAttribute("currentCountMICI"));
			String errorCode = request.getParameter("errorCode");
			for(int i = 1; i < 10; i++) {
				String temp = request.getParameter("errorCode"+i);
				if(temp != null && !temp.contentEquals("0")) errorCodeSet.add(temp);
			}
			System.out.println(errorCodeSet.toString());
			System.out.println("OPTION: " + errorCode);
			System.out.println("RESULT : " + dbHandler.updateCurrentStation(MICI, REPAIR01_FAIL, ppid));


		} else {
			System.out.println("ERROR");
		}

	}

	public void miciDisplay(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("titlePageMICI", "MICI");
		request.setAttribute("sethideMICI", "hidden");
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
			request.setAttribute("sethideMICI", "");
			String[] currenStaions = dbHandler.getCurrentStation(ppid);
			System.out.println("From: " + currenStaions[0] + "====");
			System.out.println("To: " + currenStaions[1] + "====");
			request.setAttribute("ppidCheckAtMICI", ppid);
			request.setAttribute("snCheckAtMICI", sn);
			if (currenStaions[0].equalsIgnoreCase(START) && currenStaions[1].equalsIgnoreCase(PHYSICAL_RECEIVING)) {
				// no information here
				request.setAttribute("currentStatusAtMICI", "This Item Is Received From Physical Receiving Station!");
			} else if (currenStaions[0].equalsIgnoreCase(REPAIR01)
					&& currenStaions[1].equalsIgnoreCase(REPAIR01_PASS)) {
				// print repair infomation
				// change from REPAIR01 -> MICI
				request.setAttribute("currentStatusAtMICI", "This Item Is Returned back From Repair 01 Station!");
				dbHandler.updateCurrentStation(REPAIR01_PASS, MICI, ppid);
			} else {
				System.out.println("SOME THING ELSE");
				request.setAttribute("currentStatusAtMICI", "Invalid Access This Item At This Station!");
				request.setAttribute("sethideMICI", "hidden");
				request.setAttribute("seterrorhiddenMICI", "");
			}
		}
		request.getRequestDispatcher("/WEB-INF/views/mici_station/mici.jsp").forward(request, response);
	}
}
