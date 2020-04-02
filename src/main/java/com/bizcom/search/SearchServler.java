package com.bizcom.search;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
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
	private String[] listRefs = new String[] { "Ref_1", "Ref_2", "Ref_3" };
	private String[] listOptions = new String[] { "Option_1", "Option_2", "Option_3" };

	/**
	 * GET
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("listRefs", listRefs);
		request.setAttribute("listOptions", listOptions);

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

		// Function check if input is exits
		checkAllAttribute(request, response);

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

			case 3:// Search PPId and Station
				System.out.println("Case 3 Active");
				List<List<String>> ressults = db.searchByPPIDAndStation(ppid, inputStationName);

				if (ressults.isEmpty()) {
					// Cannot find any result from that station
					request.setAttribute("setError_Case", "show");
					request.setAttribute("errorMessage",
							ppid + " at " + inputStationName + " station doesn't exist!");
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
				
				// NEED TO IMPLEMENT SOOn
				
			case 4:// Search By date
				System.out.println("Case 4 Active");
				System.out.println("PROCESSING......");
				
				
				break;
				
			case 5:
				List<List<String>> caseReseults = new ArrayList<List<String>>();
				try {
					caseReseults = db.searchByStationAndTime(inputStationName,fromDateInput,toDateInput);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				if (caseReseults.isEmpty()) {
					// Cannot find any result from that station
					request.setAttribute("setError_Case", "show");
					request.setAttribute("errorMessage",
							fromDateInput + " to " + toDateInput + " at " + inputStationName +" station doesn't have any result!!");
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
					request.setAttribute("stationResultList", caseReseults);
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
			if (inputStationName.isEmpty()) {
				caseID = 1;
			}
			// PPID and Station Case
			else {
				caseID = 3;
			}
		}

		else if (inputStationName != null && !inputStationName.isEmpty() && ppid.isEmpty()) {
			System.out.println(ppid);
			caseID = 2;
		}

		else  if (toDateInput != null && !toDateInput.isEmpty() && fromDateInput != null && !fromDateInput.isEmpty()) {
			if (inputStationName.isEmpty()) {

				// Search by date only
				caseID = 4;
			} else {

				// serach by date and station
				caseID = 5;
			}
		}else {
			System.out.println(" NO DETECT");
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

	public void checkAllAttribute(HttpServletRequest request, HttpServletResponse response) {

		setAttribute(request, response, "inputppid", ppid);
		setAttribute(request, response, "refInput", refInput);
		setAttribute(request, response, "optionInput", optionInput);
		setAttribute(request, response, "inputRefValue", inputRefValue);
		setAttribute(request, response, "inputEmployee", inputEmployee);
		setAttribute(request, response, "inputStationName", inputStationName);
		setAttribute(request, response, "toDateInput", toDateInput);
		setAttribute(request, response, "fromDateInput", fromDateInput);
		setAttribute(request, response, "inputFromStation", inputFromLocaltion);
		setAttribute(request, response, "inputToStation", inputToLocaltion);

	}

	public void setAttribute(HttpServletRequest request, HttpServletResponse response, String jspName, String idInput) {
		if (!idInput.isEmpty()) {
			request.setAttribute(jspName, idInput);
		} else {
			idInput = "";
			request.setAttribute(jspName, idInput);
		}
	}

}
