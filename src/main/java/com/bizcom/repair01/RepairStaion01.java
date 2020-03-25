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

	private final String MICI = "MICI";
	private final String REPAIR01_FAIL = "REPAIR01_FAIL";
	private final String REPAIR01 = "REPAIR01";

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
				if (!ppid.isEmpty() && db.isPPIDExistInMICI(ppid)) {

					String[] stationIndo = db.getCurrentStation(ppid);

					// Check If PPID stay at corrected station
					// FROM : MICI TO : REPAIR01_FAIL
					if (stationIndo[0].equalsIgnoreCase("MICI") && stationIndo[1].equalsIgnoreCase(REPAIR01_FAIL)) {
						boolean updateFlag = db.generateErrorRecord(ppid);

						if (updateFlag) {
							// Update status also
							db.updateCurrentStation(MICI, REPAIR01, ppid);
							updateUI(request, response, ppid);

						} else {
							displayError(request, response, ppid, "Error system! We cannot upload error to system.");
						}

					} else {
						getErrors(request, response, ppid);
						updateUI(request, response, ppid);
					}

				} else {
					displayError(request, response, ppid, "PPID is Empty or Not Found!");
				}
				break;
			case "updateRevision":

				updateRevision(request, response, ppid);
				break;
			}
		} else {
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

			case "SubmitAction":
				String err = request.getParameter("errorValueAction");
				String errorCode = err.split(" --> ")[0];
				String duty = request.getParameter("duty" + err);
				String oldPN = request.getParameter("oldPN" + err);
				String newPN = request.getParameter("newPN" + err);
				String area = request.getParameter("area" + err);
				String actionJob = request.getParameter("action" + err);
				db.updateRepair01RecordAction(errorCode, ppid, duty, oldPN, newPN, area, actionJob);
				getErrors(request, response, ppid);
				updateUI(request, response, ppid);
//				request.getRequestDispatcher("/WEB-INF/views/repair_01/repair01.jsp").forward(request, response);				
				break;

			}

		}
	}

	public void updateRevision(HttpServletRequest request, HttpServletResponse response, String ppid)
			throws ServletException, IOException {
		request.setAttribute("setRepair01Hidden", "hidden");

		request.setAttribute("setRepair01HiddenError", "hidden");
		String curRev = db.getCurrentRev(ppid);
		if (curRev.isEmpty())
			currentRev = -1;
		else {
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
				request.setAttribute("nextRevNumber", (temp.getCurrentRev() + 1));
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
			request.setAttribute("setRepair01HiddenError", "hidden");
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
		// improve later
		List<String> errorList = db.fetchErrorForRepair01FromMICI(ppid);
		// try to fetch in
		List<String> undoneList = db.getAllUndoneErrorCode(ppid);
		List<String> errorListModifed = new ArrayList<>();
		if (undoneList.isEmpty()) {
			errorList.clear();
			errorListModifed.clear();
		}

		if (errorList.size() > 0) {
			for (String s : errorList) {
				String tempErroCode = s.split(" --> ")[0];

				if (undoneList.contains(tempErroCode)) {
					errorListModifed.add(s);
				}
			}
		}

		request.setAttribute("setRepair01HiddenFix", "show");
		if (errorListModifed.size() == 0) {
			request.setAttribute("setRepair01HiddenError", "");
			request.setAttribute("setSuccessMessage",
					ppid + " has NO ERROR! Please check REVESION UPGRADE IF REQUIRE!");
		} else {
			// List all error
			request.setAttribute("sizeErrorList", errorListModifed.size());
			request.setAttribute("setRepair01Hidden", "");
			request.setAttribute("errorList", errorListModifed);

		}
	}

	public void updateUI(HttpServletRequest request, HttpServletResponse response, String ppid)
			throws ServletException, IOException {
		request.setAttribute("setPPID", ppid);
		String curRev = db.getCurrentRev(ppid);
		currentRev = Integer.parseInt(curRev.substring(curRev.length() - 2, curRev.length()));

		if (currentRev >= maxRev) {

			request.setAttribute("setRepair01Hidden", "hidden");
			request.getRequestDispatcher("/WEB-INF/views/repair_01/repair01.jsp").forward(request, response);
		} else {
			updateRevision(request, response, ppid);
		}
	}

	public void displayError(HttpServletRequest request, HttpServletResponse response, String ppid, String message)
			throws ServletException, IOException {
		request.setAttribute("setRepair01HiddenError", "show");
		request.setAttribute("setPPID", ppid);
		request.setAttribute("setErrorMessage", message);
		request.getRequestDispatcher("/WEB-INF/views/repair_01/repair01.jsp").forward(request, response);
	}
}
