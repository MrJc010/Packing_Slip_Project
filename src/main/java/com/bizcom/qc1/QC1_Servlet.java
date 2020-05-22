package com.bizcom.qc1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.enterprise.inject.ResolutionException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bizcom.authentication.UrlPatternUtils;
import com.bizcom.database.DBHandler;



/**
 * Servlet implementation class QC1_Servlet
 */
@WebServlet("/qc1")
public class QC1_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBHandler db = new DBHandler();
	private static String ppid = "";
	public QC1_Servlet() {
		super();
	}

	/**
	 * @throws IOException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * "/WEB-INF/views/qc1/qc1.jsp"
	 */



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		

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
							
							// read text file
							if(service()) {
								System.out.println("PASS PPID");
								if(db.insertQC1Table(ppid, "QC1 USERS", "Passed")) {
									if(db.updateCurrentStation(db.QC1, db.VI_WAITING, ppid)) {
										System.out.println("UPDATE READ DONE");
									}else {
										System.out.println("CANNOT UPDATE REVISION");
									}
								}else {
									System.out.println("CANOT INSERT INTO QC1");
								}
							}else {

								System.out.println("FAILED PPID");
								if(db.insertQC1Table(ppid, "QC1 USERS", "Failed")) {
									if(db.updateCurrentStation(db.QC1, db.REPAIR02_WAITING, ppid)) {
										System.out.println("UPDATE READ DONE");
									}else {
										System.out.println("CANNOT UPDATE REVISION");
									}
								}else {
									System.out.println("CANOT INSERT INTO QC1");
								}
							
							}
						}else {
							System.out.println("FAIL toupdate");
						}
						
						
						request.getRequestDispatcher("/WEB-INF/views/qc1/qc1.jsp").forward(request, response);
					} 

					else {
						System.out.println("ELSE ");
						displayError(request, response, ppid, "PPID is Not Found!");
					}

				} else {
					displayError(request, response, ppid, "PPID is Not Found!");
				}
				break;
			case "transfertoMICI":
				request.getRequestDispatcher("/WEB-INF/views/qc1/qc1.jsp").forward(request, response);
				break;

			}
		}
		else {
			request.getRequestDispatcher("/WEB-INF/views/qc1/qc1.jsp").forward(request, response);
		}




		//		}
		//	else {
		//		
		//			response.sendRedirect(request.getContextPath() + "/signin?pagerequest=qc1");
		//		}
		//		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}


	public static boolean service() throws IOException  {
		File file = new File("C:\\Users\\viet\\Desktop\\"+ ppid+ ".txt");
		try {
			Scanner scanner = new Scanner(file);
			//now read the file line by line...
			int lineNum = 0;
			while (scanner.hasNextLine()) {
				String str = scanner.nextLine();
				System.out.println(str);
				lineNum++;
				if(str.toUpperCase().contains("PASS") || str.toUpperCase().contains("PASSED")) { 
					return true;
				}
			}
		} catch(FileNotFoundException e) { 
			System.out.println(e);
			return false;
		}
		return false;
	}



	public void displayError(HttpServletRequest request, HttpServletResponse response, String ppid, String message) throws ServletException, IOException {

		//		displayInitialView(request, response, true);
		request.setAttribute("setRepair01HiddenError", "show");
		request.setAttribute("setErrorMessageHidden", "show");
		request.setAttribute("setSuccessMessageHidden", "hidden");
		request.setAttribute("setErrorMessage", ppid + " : " + message);
		request.getRequestDispatcher("/WEB-INF/views/qc1/qc1.jsp").forward(request, response);
	}

}
