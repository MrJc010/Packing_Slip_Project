package com.bizcom.search;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bizcom.database.DBHandler;

/**
 * this is the thing I type Servlet implementation class SearchServler
 */
@WebServlet("/search")
public class SearchServler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// UI Field
	private String ppid = "";
//	private String btnSearch = "";
	private String refInput = "";
	private String optionInput = "";
	private String inputRefValue = "";
	private String inputEmployee = "";
	private String inputStationName = "";
	private String fromDateInput = "";
	private String toDateInput = "";
	private String inputFromLocaltion = "";
	private String inputToLocaltion = "";
	private DBHandler db = new DBHandler();

	/**
	 * GET
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			ppid = request.getParameter("inputppid").trim();
			refInput = request.getParameter("refInput").trim();
			optionInput = request.getParameter("optionInput").trim();
			inputRefValue = request.getParameter("inputRefValue").trim();
			inputEmployee = request.getParameter("inputEmployee").trim();
			inputStationName = request.getParameter("inputStationName").trim().toLowerCase();
			toDateInput = request.getParameter("toDateInput").trim();
			fromDateInput = request.getParameter("fromDateInput").trim();
			inputFromLocaltion = request.getParameter("inputFromStation").trim();
			inputToLocaltion = request.getParameter("inputToStation").trim();

		} catch (Exception e) {
			
			System.out.println("Exception called");
			
		}
		setInitial(request, response);
		int tempCaseID = searchCase();

		if (tempCaseID != -1) {
			switch (tempCaseID) {
			case 1: // SEARCH ONLY PPID other null
				// Get all infomation of ppid
				System.out.println("Case 1 Active");
				List<String> ppidInfo = db.searchByPPID(ppid);

				// Display PPID SECTION Search View Logic
				if (ppidInfo.isEmpty()) {
					// Hide success part
					request.setAttribute("setError_Case", "show");
					request.setAttribute("errorMessage", ppid + " doesn't exist!");

				} else {
					request.setAttribute("set_Hidden_PPID_Case", "show");
					request.setAttribute("ppidInfo", ppidInfo);
				}
				break;
			case 2: // Search Station only all other null
				System.out.println("Case 2 Active");

				List<List<String>> stationResultList = db.searchByStation(inputStationName);

				if (stationResultList.isEmpty()) {
					// Cannot find any result from that station
					request.setAttribute("setError_Case", "show");
					request.setAttribute("errorMessage", inputStationName + " doesn't have any items!");
				} else {
					// Based on input to design out come table

					switch (inputStationName.toUpperCase()) {
					case "REPAIR01":						
						request.setAttribute("stationName", "REPAIR01");
						break;
					case "PHYSICAL":						
						request.setAttribute("stationName", "PHYSICAL");
						break;
					case "MICI":						
						request.setAttribute("stationName", "MICI");
						break;
					}
					request.setAttribute("set_Hidden_Station_Search", "show");
					request.setAttribute("stationResultList", stationResultList);
				}

				break;
				
			case 3 :// Search PPId and Station
				System.out.println("Case 3 Active");
				List<List<String>> ressults = db.searchByPPIDAndStation(ppid,inputStationName);
				
				if (ressults.isEmpty()) {
					// Cannot find any result from that station
					request.setAttribute("setError_Case", "show");
					request.setAttribute("errorMessage", ppid + " at "+ inputStationName + " station doesn't exist!");
				} else {
					// Based on input to design out come table

					switch (inputStationName.toUpperCase()) {
					case "REPAIR01":						
						request.setAttribute("stationName", "REPAIR01");
						break;
					case "PHYSICAL":						
						request.setAttribute("stationName", "PHYSICAL");
						break;
					case "MICI":						
						request.setAttribute("stationName", "MICI");
						break;
					}
					request.setAttribute("set_Hidden_Station_Search", "show");
					request.setAttribute("stationResultList", ressults);
				}
				break;
			default:
				break;
			}
		}
//		showInput();

		request.getRequestDispatcher("/WEB-INF/views/search/search.jsp").forward(request, response);
	}

	/**
	 * POST
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doPost Called");
	}

	// TODO : improve all logic
	public int searchCase() {
		int caseID = -1;
		// Case 1 : ppid input without location (all history called)
		if (ppid != null && !ppid.isEmpty()) {
			
			// Only PPID 
			if(inputStationName.isEmpty()){				
				caseID = 1;
			}
			// PPID and Station Case
			else {
				caseID = 3;
			}
			
			
		}

		// Code will check if case 3 or not, so don't need to recheck PPID at here again
		if (inputStationName != null && !inputStationName.isEmpty()) {
			System.out.println(ppid);
			caseID = 2;
		}
		return caseID;
	}

	public void showInput() {
		String temp = "SearchServler [ppidInput=" + ppid + ", refInput=" + refInput + ", optionInput=" + optionInput
				+ ", inputRefValue=" + inputRefValue + ", inputEmployee=" + inputEmployee + ", inputStationName="
				+ inputStationName + ", fromDateInput=" + fromDateInput + ", toDateInput=" + toDateInput
				+ ", inputFromLocaltion=" + inputFromLocaltion + ", inputToLocaltion=" + inputToLocaltion + "]";
		System.out.println("All INPUT: " + temp);
	}

	public void setInitial(HttpServletRequest request, HttpServletResponse response) {
		// PPID Case Hidden at begin
		request.setAttribute("setError_Case", "hidden");
		request.setAttribute("set_Hidden_PPID_Case", "hidden");
		request.setAttribute("set_Hidden_Station_Search", "hidden");

	}

}
