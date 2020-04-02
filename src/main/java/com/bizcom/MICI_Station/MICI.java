package com.bizcom.MICI_Station;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.bizcom.database.DBHandler;

/**
 * Servlet implementation class MICI
 */
@WebServlet("/mici")
public class MICI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String ppid;
	private String sn;
	DBHandler dbHandler = new DBHandler();

	private static final String PHYSICAL_RECEIVING = "PHYSICAL_RECEIVING";
	private static final String MICI = "MICI";
	private static final String REPAIR01_FAIL = "REPAIR01_FAIL";
	private static final String REPAIR01_PASS = "REPAIR01_PASS";
	private static final String REPAIR01 = "REPAIR01";
	private static final String QC1 = "QC1";
	private static final String START = "START";
	private static Set<String> errorCodeSet;
	private static List<ErrorCode> listErrorCodes = new ArrayList<>();
	private String stringError = "";
	private Map<String, Object> mapErrorCodes = new HashMap<>();
	private JSONObject jsonMap;
	public MICI() {
		listErrorCodes = dbHandler.getAllErrorCodes();
		for (ErrorCode e : listErrorCodes) {
			mapErrorCodes.put(e.getErrorCode(), (Object)e.getDescription());
		}
		jsonMap= new JSONObject(mapErrorCodes);
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// listErrorCodes have List Object ErrorCode
		// ErrorCode ( errocode , description)

//		System.out.println(stringError);
		request.setAttribute("listErrorCodes", jsonMap);

		/// END ==============

		errorCodeSet = new HashSet<String>();
		request.setAttribute("seterrorhiddenMICI", "hidden");
		request.setAttribute("setHiddenResultSucess", "hidden");
//		request.setAttribute("setHidenResult", "hidden");
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
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		System.out.println(ppid + " on post method");
		if (checkStatus(ppid)) {
			if (validate(ppid, request, dbHandler.getPhysicalInfor(ppid))) {
				if (action.equalsIgnoreCase("passButton")) {
					// go to QC1
					System.out.println("GoTo QC1 PASS");
				} else if (action.equalsIgnoreCase("failButton")) {
					System.out.println(request.getAttribute("currentCountMICI"));
					String errorCode = request.getParameter("errorCode");
					for (int i = 1; i < 10; i++) {
						String temp = request.getParameter("errorCode" + i);
						if (temp != null && !temp.contentEquals("0"))
							errorCodeSet.add(temp);
					}
					System.out.println("Error Code Set:" + errorCodeSet.toString());
					System.out.println("Result Update Station Status : "
							+ dbHandler.updateCurrentStation(MICI, REPAIR01_FAIL, ppid));
					try {
						boolean test = dbHandler.addToMICITable(ppid, sn, errorCodeSet, "A USER FROM MICI");
						// System.out.println(test);
					} catch (ClassNotFoundException | SQLException e) {
						// here
						System.out.println(e.getMessage());
						e.printStackTrace();
					}

					request.setAttribute("setHiddenResultSucess", "show");
					request.setAttribute("ppid", ppid);
					request.setAttribute("seterrorhiddenMICI", "hidden");
					request.setAttribute("sethideMICI", "hidden");
					request.getRequestDispatcher("/WEB-INF/views/mici_station/mici.jsp").forward(request, response);
					return;
				}
			} else {
				request.getRequestDispatcher("/WEB-INF/views/mici_station/mici.jsp").forward(request, response);
				System.out.println("Redirect to currentpage on Post Method from inner if");
			}
			request.getRequestDispatcher("/WEB-INF/views/mici_station/mici.jsp").forward(request, response);

		} else {
			request.setAttribute("sethideMICI", "hidden");
			request.setAttribute("seterrorhiddenMICI", "");
			request.setAttribute("currentStatusAtMICI", "This PPID is invalid at this station. ");
			request.getRequestDispatcher("/WEB-INF/views/mici_station/mici.jsp").forward(request, response);
			System.out.println("Redirect to currentpage on Post Method from outer if");

		}
	}

	public void miciDisplay(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		displayPPIDAndSN(request);
		request.setAttribute("titlePageMICI", "MICI");
		request.setAttribute("sethideMICI", "hidden");
		request.getRequestDispatcher("/WEB-INF/views/mici_station/mici.jsp").forward(request, response);
	}

	public void errorPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("title", "Error page");
		request.getRequestDispatcher("/WEB-INF/error/404.jsp").forward(request, response);

	}

	public void displayPPIDAndSN(HttpServletRequest request) {
		if(ppid!=null && !ppid.isEmpty()) {
			request.setAttribute("ppid", ppid);
		}
		if(sn!=null && !sn.isEmpty()) {			
			request.setAttribute("sn", sn);
		}
	}
	public void checkMICI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("problemcodeAtMICI", "");
		request.setAttribute("problemDescpAtMICI", "");
		String page = request.getParameter("page");
		ppid = request.getParameter("ppidNumber");
		sn = request.getParameter("serialnumber");
		String[] miciInfo = dbHandler.getPhysicalInfor(ppid);
		if (validate(ppid, request, miciInfo)) {

			// fetch information
			if (ppid != null) {
				request.setAttribute("sethideMICI", "");
				request.setAttribute("setHidenInfo", "show");
				displayPPIDAndSN(request);
				String[] currenStaions = dbHandler.getCurrentStation(ppid);

				request.setAttribute("ppidCheckAtMICI", ppid);
				request.setAttribute("snCheckAtMICI", sn);
				if (currenStaions[0].equalsIgnoreCase(START) && currenStaions[1].equalsIgnoreCase(PHYSICAL_RECEIVING)) {
					// no information here
					request.setAttribute("problemcodeAtMICI", miciInfo[0]);
					request.setAttribute("problemDescpAtMICI", miciInfo[1]);
					request.setAttribute("currentStatusAtMICI",
							"This Item Is Received From Physical Receiving Station!");
					System.out.println("Result Update Station Status : "
							+ dbHandler.updateCurrentStation(PHYSICAL_RECEIVING, MICI, ppid));
				} else if (currenStaions[0].equalsIgnoreCase(REPAIR01)
						&& currenStaions[1].equalsIgnoreCase(REPAIR01_PASS)) {
					// print repair infomation
					// change from REPAIR01 -> MICI
					request.setAttribute("currentStatusAtMICI", "This Item Is Returned back From Repair 01 Station!");
					dbHandler.updateCurrentStation(REPAIR01_PASS, MICI, ppid);
				} else if (currenStaions[0].equalsIgnoreCase(PHYSICAL_RECEIVING)
						&& currenStaions[1].equalsIgnoreCase(MICI)) {
					request.setAttribute("problemcodeAtMICI", miciInfo[0]);
					request.setAttribute("problemDescpAtMICI", miciInfo[1]);
					request.setAttribute("currentStatusAtMICI",
							"This Item Is Received From Physical Receiving Station!");
				} else if (currenStaions[0].equalsIgnoreCase(REPAIR01_PASS)
						&& currenStaions[1].equalsIgnoreCase(MICI)) {
					request.setAttribute("problemcodeAtMICI", miciInfo[0]);
					request.setAttribute("problemDescpAtMICI", miciInfo[1]);
					request.setAttribute("currentStatusAtMICI","This Item Is Returned back From Repair 01 Station!");
				} else {
					System.out.println("SOME THING ELSE");
					request.setAttribute("currentStatusAtMICI", "Invalid Access This Item At This Station!");
					request.setAttribute("sethideMICI", "hidden");
					request.setAttribute("seterrorhiddenMICI", "");
				}
			}
			request.getRequestDispatcher("/WEB-INF/views/mici_station/mici.jsp").forward(request, response);
//			response.sendRedirect(request.getContextPath()+"/mici?page=display");
		} else {
			
			request.getRequestDispatcher("/WEB-INF/views/mici_station/mici.jsp").forward(request, response);
//			response.sendRedirect(request.getContextPath()+"/mici?page=display");
			System.out.println("Redirect to currentpage on check!");
		}
	}

	public boolean validate(String ppid, HttpServletRequest request, String[] miciInfo) {

		if (miciInfo[0] != null && miciInfo[1] != null) {
			request.setAttribute("problemcode", miciInfo[0]);
			request.setAttribute("problemdecription", miciInfo[1]);
			if (miciInfo[0].equals("N/A")) {
				request.setAttribute("seterrorhiddenproblemMICI", "hidden");
			}
			return true;
		} else {
			request.setAttribute("sethideMICI", "hidden");
			request.setAttribute("seterrorhiddenMICI", "");
			displayPPIDAndSN(request);
			request.setAttribute("currentStatusAtMICI", "This PPID and Serial Number don't stay at this station!");
			return false;
		}
	}

	public boolean checkStatus(String ppid) {
		String[] status = dbHandler.getCurrentStation(ppid);
		System.out.println(status[0]+" "+status[1]);
		if ((status[0].equalsIgnoreCase(PHYSICAL_RECEIVING) && status[1].equalsIgnoreCase(MICI))|| ( status[0].equalsIgnoreCase(REPAIR01_PASS)
				&& status[1].equalsIgnoreCase(MICI))) {
			return true;
		} else {
			return false;
		}
	}
}
