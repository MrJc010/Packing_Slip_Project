package com.bizcom.repair01;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bizcom.database.DBHandler;

/**
 * Servlet implementation class RepairStaion01
 */
@WebServlet("/repair01")
public class RepairStaion01 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String ppid;
	private DBHandler db = new DBHandler();
	private int currentRev = -1;
	private int maxRev = 6;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RepairStaion01() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action01");

		request.setAttribute("setRepair01Hidden", "hidden");
		request.setAttribute("setRepair01HiddenError", "hidden");

		if (action != null) {
			switch (action) {
			case "findPPID":
				ppid = request.getParameter("inputppid");
				if (!ppid.isEmpty()) {
					request.setAttribute("setPPID", ppid);
					String curRev = db.getCurrentRev(ppid);

					currentRev = Integer.parseInt(curRev.substring(curRev.length() - 2, curRev.length()));

					System.out.println("currentRev : " + currentRev);
					if (currentRev >= maxRev) {

						request.setAttribute("setRepair01Hidden", "hidden");
						request.getRequestDispatcher("/WEB-INF/views/repair_01/repair01.jsp").forward(request,
								response);
					} else {
						updateRevision(request, response, ppid);
					}
				} else {
					request.setAttribute("setRepair01HiddenError", "show");
					request.setAttribute("setErrorMessage", "PPID is Empty or Not Found!");
				}
				break;
			case "updateRevision":
				System.out.println("updateRevision");
				updateRevision(request, response, ppid);
				break;
			default:
//				if (currentRev >= maxRev) {
//
//					request.setAttribute("setRepair01Hidden", "hidden");
//					request.getRequestDispatcher("/WEB-INF/views/repair_01/repair01.jsp").forward(request, response);
//				} else {
//					updateRevision(request, response, ppid);
//				}
				request.getRequestDispatcher("/WEB-INF/views/repair_01/repair01.jsp").forward(request, response);
				break;
			}
		} else {
//			if (currentRev >= maxRev) {
//
//				request.setAttribute("setRepair01Hidden", "hidden");
//				request.getRequestDispatcher("/WEB-INF/views/repair_01/repair01.jsp").forward(request, response);
//			} else {
//				updateRevision(request, response, ppid);
//			}
			request.getRequestDispatcher("/WEB-INF/views/repair_01/repair01.jsp").forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action01");
		if (action != null) {
			switch (action) {
			case "updateRevision":
				String newRev = generatorRev(currentRev + 1);
				db.updateRevision(ppid, newRev);
				doGet(request, response);
				break;
			}

		}
	}

	public void updateRevision(HttpServletRequest request, HttpServletResponse response, String ppid)
			throws ServletException, IOException {

//		currentRev = db.getCurrentRev(ppid);

		request.setAttribute("setRepair01Hidden", "hidden");

		request.setAttribute("setRepair01HiddenError", "hidden");

		String curRev = db.getCurrentRev(ppid);
		if (!curRev.isEmpty()) {
			currentRev = Integer.parseInt(curRev.substring(curRev.length() - 2, curRev.length()));
		}
		getErrors(request, response, ppid);

		// Do logic Here
		if (currentRev != -1) {

			if (currentRev < maxRev) {
				request.setAttribute("setRepair01Hidden", "show");

				// Fetch data base on currentNum
				RevesionUpgrade temp = new RevesionUpgrade("1233", "middle", "abbb", "ecoAction", "122233", "9999",
						"12345678", currentRev);
				request.setAttribute("curRevNumber", temp.getCurrentRev());
				request.setAttribute("nextRevNumber", temp.getCurrentRev() + 1);
				request.setAttribute("partNumber", temp.getPn());
				request.setAttribute("location", temp.getLocation());
				request.setAttribute("desc", temp.getDesc());
				request.setAttribute("ecoAction", temp.getEcoAction());
				request.setAttribute("oldMaterialPN", temp.getOldMaterial());
				request.setAttribute("newMaterialPN", temp.getNewMaterial());
				request.setAttribute("shortcut", temp.getShortcut());
				request.getRequestDispatcher("/WEB-INF/views/repair_01/repair01.jsp").forward(request, response);

			} else {

				request.setAttribute("setRepair01Hidden", "hidden");

				
				request.getRequestDispatcher("/WEB-INF/views/repair_01/repair01.jsp").forward(request, response);
			}
			// error code

		} else {
			request.getRequestDispatcher("/WEB-INF/views/repair_01/repair01.jsp").forward(request, response);
		}

	}

	public String generatorRev(int x) {
		if (x / 10 < 1) {
			return "A0" + x;
		} else {
			return "A" + x;
		}
	}

	public void getErrors(HttpServletRequest request, HttpServletResponse response, String ppid) {
		List<String> errorList = db.fetchErrorForRepair01FromMICI(ppid);
		request.setAttribute("setRepair01HiddenFix", "show");
		if (errorList.size() == 0) {
			request.setAttribute("setRepair01HiddenError", "");
			request.setAttribute("setErrorMessage", ppid + " not found at this station.");
		} else {
			// List all error
			request.setAttribute("sizeErrorList", errorList.size());
			request.setAttribute("setRepair01Hidden", "");
			request.setAttribute("errorList", errorList);

		}
	}
}
