package com.bizcom.repair02;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bizcom.database.DBHandler;

/**
 * Servlet implementation class Repair02_Servlet
 */
@WebServlet("/repair02")
public class Repair02_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String ppid = "";
	DBHandler db = new DBHandler();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//		if(db.checkAuthentication(request)) {
		System.out.println("doget called");
		String action = request.getParameter("action");

		if (action != null) {
			switch (action) {
			case "findPPID":
				ppid = request.getParameter("inputPPID0").trim().toUpperCase();
				if (ppid.length() != 0 && db.isPPIDExistIn(ppid,"eco_station")) {

					String[] stationIndo = db.getCurrentStation(ppid);
					// Check If PPID stay at corrected station
					// FROM : MICI TO : REPAIR01_FAIL
					if (((stationIndo[0].equalsIgnoreCase(db.ECO) || stationIndo[0].equalsIgnoreCase(db.MICI)) && stationIndo[1].equalsIgnoreCase(db.QC1_WAITING))
						|| (stationIndo[0].equalsIgnoreCase(db.QC1_WAITING) && stationIndo[1].equalsIgnoreCase(db.QC1)))
					{
						
						
						if(db.updateCurrentStation(stationIndo[1], db.QC1, ppid)) {
							System.out.println(ppid);
							
						
						
						}else {
							System.out.println("FAIL toupdate");
						}
						
						
						request.getRequestDispatcher("/WEB-INF/views/reapir02/repair02.jsp").forward(request, response);
					} 

					else {
						System.out.println("ELSE ");
//						displayError(request, response, ppid, "PPID is Not Found!");
					}

				} else {
//					displayError(request, response, ppid, "PPID is Not Found!");
				}
				break;
			case "transfertoMICI":
				request.getRequestDispatcher("/WEB-INF/views/reapir02/repair02.jsp").forward(request, response);
				break;

			}
		}
		else {
			request.getRequestDispatcher("/WEB-INF/views/reapir02/repair02.jsp").forward(request, response);
		}




		//		}
		//	else {
		//		
		//			response.sendRedirect(request.getContextPath() + "/signin?pagerequest=qc1");
		//		}
		//	
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
