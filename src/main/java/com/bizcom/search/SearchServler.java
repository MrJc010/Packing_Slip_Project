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
 * this is the thing I type
 * Servlet implementation class SearchServler
 */
@WebServlet("/search")
public class SearchServler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// UI Field
	private String ppidInput = "";
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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet Called");	
		try {
			ppidInput = request.getParameter("inputppid");
			refInput = request.getParameter("refInput");
			optionInput = request.getParameter("optionInput");
			inputRefValue = request.getParameter("inputRefValue");
			inputEmployee = request.getParameter("inputEmployee");
			inputStationName = request.getParameter("inputStationName");
			toDateInput = request.getParameter("toDateInput");
			fromDateInput = request.getParameter("fromDateInput");
			inputFromLocaltion = request.getParameter("inputFromLocaltion");
			inputToLocaltion = request.getParameter("inputToLocaltion");
			
		}catch(Exception e) {			
			System.out.println("Exception called");
			e.printStackTrace();
		}
		setInitial(request,response);
		int tempCaseID = searchCase();
	
		if(tempCaseID != -1) {
			switch (tempCaseID) {
			case 1:
				// Get all infomation of ppid
				List<String> ppidInfo = db.searchByPPID(ppidInput);
				// Display PPID SECTION Search View
				request.setAttribute("set_Hidden_PPID_Case", "show");
				request.setAttribute("setSuccess_PPID_Case", "hidden");
				request.setAttribute("errorPPIDMessage", "hidden");
				
				if(ppidInfo.size() != 0) {
					// Hide success part
					request.setAttribute("setError_PPID_Case", "hidden");
					
				}else {
					
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost Called");
	}

	public int searchCase() {
		int caseID =-1;
		
		
		// Case 1 : ppid input without location (all history called)
		if(ppidInput!= null && !ppidInput.isEmpty()) {
			caseID = 1;
		}
		
		
		
		return caseID;
	}
	
	public void showInput() {
		String temp =  "SearchServler [ppidInput=" + ppidInput + ", refInput=" + refInput + ", optionInput=" + optionInput
				+ ", inputRefValue=" + inputRefValue + ", inputEmployee=" + inputEmployee + ", inputStationName="
				+ inputStationName + ", fromDateInput=" + fromDateInput + ", toDateInput=" + toDateInput
				+ ", inputFromLocaltion=" + inputFromLocaltion + ", inputToLocaltion=" + inputToLocaltion + "]";
		System.out.println("All INPUT: " + temp);
	}
	
	public void setInitial(HttpServletRequest request, HttpServletResponse response) {
		// PPID Case Hidden at begin
		request.setAttribute("set_Hidden_PPID_Case", "hidden");
		
	}
	
}
