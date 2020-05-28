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
/**
 * @author viet
 *
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
//		System.out.println("doget called");
		String action = request.getParameter("action");
		if (action != null) {
			//			initialDisplay(request,response);
			switch (action) {
			case "findPPID":
				ppid = request.getParameter("inputPPID0").trim().toUpperCase();
				
				if (ppid.length() != 0 && db.isPPIDExistIn(ppid,"eco_station")) {
					request.setAttribute("ppid", ppid);
					String[] stationIndo = db.getCurrentStation(ppid);
					// Check If PPID stay at corrected station
					// FROM : MICI TO : REPAIR01_FAIL
					if (((stationIndo[0].equalsIgnoreCase(db.ECO) || stationIndo[0].equalsIgnoreCase(db.MICI)) && stationIndo[1].equalsIgnoreCase(db.QC1_WAITING))
							|| (stationIndo[0].equalsIgnoreCase(db.QC1_WAITING) && stationIndo[1].equalsIgnoreCase(db.QC1)))
					{

							if(!stationIndo[1].equalsIgnoreCase(db.QC1)) {
								if(db.updateCurrentStation(stationIndo[1], db.QC1, ppid)) {
//									System.out.println(ppid);

									// read text file
									if(service()) {
//										System.out.println("PASS PPID");
										passedDisplay(request, response);

									}else {

//										System.out.println("FAILED PPID");
										failDisplay(request, response);

									}
								}else {
									errorDisplay(request, response, " Fail to update to QC1 Station. Please check database and your system!");
								}
							}else {
								if(service()) {
//									System.out.println("PASS PPID");
									passedDisplay(request, response);

								}else {

//									System.out.println("FAILED PPID");
									failDisplay(request, response);

								}
							}


						//						request.getRequestDispatcher("/WEB-INF/views/qc1/qc1.jsp").forward(request, response);
					} 
					else {						
						errorDisplay(request, response, ppid + " doesn't belong to this station!!!");

					}

				} else {
					errorDisplay(request, response, ppid + " is Not Found!");					
				}
				break;
			case "transfertoMICI":
				initialDisplay(request,response);
				break;

			}
		}
		else {
			initialDisplay(request,response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		System.out.println(action);
		if (action != null) {
			switch (action) {
			case "submitAction":
				String valueButton = request.getParameter("btnSubmit");	
				System.out.println("asdfasdf");
				System.out.println(valueButton);
				if(valueButton.equalsIgnoreCase("Pass")) {
					//go to pass action
					if(db.insertQC1Table(ppid, "QC1 USERS", "Passed")) {									
						if(db.updateCurrentStation(db.QC1, db.VI_WAITING, ppid)) {
//							System.out.println("UPDATE READ DONE");
							notificationDisplay(request,response,ppid + " is transferred and waited for VI Station to receive!");
							ppid="";
							hiddenBTN(request, response);
						}else {
//							System.out.println("CANNOT UPDATE REVISION");
							errorDisplay(request, response, "System Error! Contact with Manager now!");
							hiddenBTN(request, response);
						}
					}else {
//						System.out.println("CANOT INSERT INTO QC1");
						errorDisplay(request, response, "System Error! Contact with Manager now!");
						hiddenBTN(request, response);
					}
				}else {
					if(db.insertQC1Table(ppid, "QC1 USERS", "Failed")) {
						if(db.updateCurrentStation(db.QC1, db.REPAIR02_WAITING, ppid)) {
//							System.out.println("UPDATE READ DONE");
							notificationDisplay(request,response,ppid + " is transferred and waited for REPAIR02 Station to receive!");
							ppid="";
							hiddenBTN(request, response);
						}else {
//							System.out.println("CANNOT UPDATE REVISION");
							errorDisplay(request, response, "System Error! Contact with Manager now!");
							hiddenBTN(request, response);
						}
					}else {
//						System.out.println("CANOT INSERT INTO QC1");
						errorDisplay(request, response, "System Error! Contact with Manager now!");
						hiddenBTN(request, response);
					}
				}
			}

		}
		doGet(request, response);
	}


	public static boolean service() throws IOException  {
		File file = new File("C:\\Users\\viet\\Desktop\\"+ ppid+ ".txt");
		boolean result = false;
		try {
			Scanner scanner = new Scanner(file);
			//now read the file line by line...
			int lineNum = 0;
			while (scanner.hasNextLine()) {
				String str = scanner.nextLine();
				System.out.println(str);
				lineNum++;
				if(str.toUpperCase().contains("PASS") || str.toUpperCase().contains("PASSED")) { 
					result =  true;
				}
			}
		} catch(FileNotFoundException e) { 
			//System.out.println(e);
			result = false;
		}
		return result;
	}



	/** Display the UI of servlet
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void display(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("ppid", "");
		//doGet(request, response);
		request.getRequestDispatcher("/WEB-INF/views/qc1/qc1.jsp").forward(request, response);
	}

	
	/**Initial Screen - hidden all element 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void initialDisplay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("setHiddenBTNPASS", "hidden");
		request.setAttribute("setHiddenBTNFAIL", "hidden");
		request.setAttribute("passedValue", "");
		request.setAttribute("ppid", "");
		request.setAttribute("failValue", "");
		request.setAttribute("setHiddenNotification", "hidden");
		display(request,response);
	}

	//	TODO : improve notification message
	/**Fail display
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void failDisplay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("setHiddenBTNPASS", "hidden");
		request.setAttribute("setHiddenBTNFAIL", "show");
		request.setAttribute("passedValue", "");
		request.setAttribute("ppid",ppid);
		request.setAttribute("failValue", "TRANSFER TO REPAIR02 STATION");
		request.setAttribute("setHiddenNotification", "show");
		request.setAttribute("messageNotification", ppid +" is FAIL. Please click FAIL button to transfer!");
		display(request,response);
	}


	/**Passed display
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void passedDisplay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("setHiddenBTNPASS", "show");
		request.setAttribute("passedValue", "TRANSFER TO VI STATION");
		request.setAttribute("failValue", "");
		request.setAttribute("ppid", "");
		request.setAttribute("setHiddenBTNFAIL", "hidden");
		request.setAttribute("setHiddenNotification", "show");
		request.setAttribute("messageNotification", ppid +" is PASSED. Please click PASSED button to transfer!");
		display(request,response);
	}


	/**Display error message to user
	 * @param request
	 * @param response
	 * @param message = user's message
	 * @throws ServletException
	 * @throws IOException
	 */
	public void errorDisplay(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
		request.setAttribute("setHiddenBTNPASS", "hidden");
		request.setAttribute("setHiddenBTNFAIL", "hidden");
		request.setAttribute("ppid", ppid);
		request.setAttribute("setHiddenNotification", "show");
		request.setAttribute("messageNotification", message);
		display(request,response);
	}

	/** Show notification on screen with custom message and color
	 * @param request
	 * @param response
	 * @param color will update later
	 * @param message : message to user
	 */
	public void notificationDisplay(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
		request.setAttribute("setHiddenBTNPASS", "hidden");
		request.setAttribute("setHiddenBTNFAIL", "hidden");
		request.setAttribute("setHiddenNotification", "show");
		request.setAttribute("ppid", "");
		request.setAttribute("messageNotification", message);
		display(request,response);
	}


	/** This method hidden two button when get any errors or wrong ppid
	 * @param request
	 * @param response
	 */
	public void hiddenBTN(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("setHiddenBTNPASS", "hidden");
		request.setAttribute("setHiddenBTNFAIL", "hidden");
		//display(request,response);
	}
}
