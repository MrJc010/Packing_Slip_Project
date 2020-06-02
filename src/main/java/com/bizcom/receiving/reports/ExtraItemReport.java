package com.bizcom.receiving.reports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bizcom.database.DBHandler;

/**
 * Servlet implementation class Shipping_servlet
 */
@WebServlet("/extraitems")
public class ExtraItemReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String ppid = "";
	private static String rma = "";
	private static Map<String,List<String>> receivedList = new HashMap<>();
	private static DBHandler db = new DBHandler();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExtraItemReport() {
		super();
		
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		//TODO: add authentication later
		if(action != null) {

			switch(action) {
			case "checkPPID":
				// Run check PPID in here
				ppid = request.getParameter("ppidNumber");
				rma = request.getParameter("rma");
				// Change isExistInPrePPID if need
				if(ppid != null && ppid.length() !=0 ) {
					
					if(!db.isExistInPPID(ppid)) {
						// exist in list
						System.out.println(receivedList.toString());
						if(receivedList!= null && receivedList.size() != 0  && receivedList.containsKey(ppid)) {
							// show up edit
							List<String> temp = receivedList.get(ppid);

							displayEditView(request, ppid + " is in list. You can edit it now.", temp);
							
						}else {
							// ppid is not in list, extra item
							System.out.println(ppid + " not found!");
							displayInputExtra(request,ppid + " is not in our list. Please Add to Extra Item");
						}	
				
						display(request, response);
						
					}else {
						System.out.println("ppid in packing slip");
						displayError(request, ppid + " is exist in packing slip. Please go to Physical Receiving Station Check again!");
						display(request, response);
					}
				}else {
					System.out.println(ppid +" is invalid or empty!");
					// ppid is iempty or invalid
					displayError(request, ppid + " is INVALID or NULL. Please enter a corrected PPID.");
					display(request, response);
				}
				
				break;
			case "addInformation":
				break;
			default:
				System.out.println("Default Case");
				break;

			}

			//			addInformation
			//			checkPPID
		}else {
			receivedList = new HashMap<>();
			setInitial(request);
			display(request, response);
		}


	}



	public void display(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/receiving_station/reports/extraitemreport.jsp").forward(request, response);
	}


	public void displayEditView(HttpServletRequest request, String message, List<String> l) {
		request.setAttribute("setHiddenCount", "show");
		request.setAttribute("count", receivedList.size());
		request.setAttribute("receivedList", receivedList.values());
		request.setAttribute("setErrorMessage","hidden");
		request.setAttribute("setHiddenResultSucess","hidden");
		request.setAttribute("checkResult", message);
		System.out.println(ppid + " ?????");
		request.setAttribute("ppidInfox", ppid);
		request.setAttribute("ppid", ppid);
		request.setAttribute("hiddenExtrainfo", "show");
		request.setAttribute("setHIddenEditButton", "show");
		request.setAttribute("setHIddenSubmitButton", "hidden");
		
//		add(sn);
//		add(rev);
//		add(manfPN);
//		add(mac);
//		add(cpuSN);
		if(l.size()>=6) {
		request.setAttribute("rma", l.get(1));
		request.setAttribute("sn", l.get(2));
		request.setAttribute("rev", l.get(3));
		request.setAttribute("manfPN", l.get(4));
		request.setAttribute("mac", l.get(5));
		request.setAttribute("cpuSN", l.get(6));		
		}
		
		
	}

	public void displayAfterAddNewExtra (HttpServletRequest request, String message) {
		request.setAttribute("setHiddenCount", "show");
		request.setAttribute("count", receivedList.size());
		request.setAttribute("receivedList", receivedList.values());
		request.setAttribute("setErrorMessage","hidden");
		request.setAttribute("setHiddenResultSucess","show");
		request.setAttribute("messageSuccess",message);
		request.setAttribute("messageSuccess", ppid + " added to extra list");
		request.setAttribute("hiddenExtrainfo","hidden");
		request.setAttribute("checkResult", "");

	}

	public void displayInputExtra(HttpServletRequest request, String message) {
		request.setAttribute("setHiddenCount", "show");
		request.setAttribute("count", receivedList.size());
		request.setAttribute("receivedList", receivedList.values());
		request.setAttribute("ppidInfox", ppid);
		request.setAttribute("ppid", ppid);
		request.setAttribute("setHiddenResultSucess", "hidden");
		request.setAttribute("hiddenExtrainfo", "show");
		request.setAttribute("setHIddenEditButton", "hidden");
		request.setAttribute("setHIddenSubmitButton", "show");
		request.setAttribute("setErrorMessage", "hidden");
		request.setAttribute("checkResult", message);


	}

	public void displayError (HttpServletRequest request, String message) {
		request.setAttribute("setHiddenCount", "show");
		request.setAttribute("count", receivedList.size());
		request.setAttribute("setHiddenResultSucess", "hidden");
		request.setAttribute("hiddenExtrainfo", "hidden");
		request.setAttribute("setErrorMessage", "show");
		request.setAttribute("errorMessage", message);

	}
	public void setInitial(HttpServletRequest request) {
		request.setAttribute("setHiddenCount", "hidden");
		request.setAttribute("setHiddenResultSucess", "hidden");
		request.setAttribute("setErrorMessage", "hidden");
		request.setAttribute("hiddenExtrainfo", "hidden");
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("get requerst" + request.getMethod());
		String action = request.getParameter("action");
		String button = request.getParameter("submitBTN");
		if(action!=null && button!= null) {
			switch(action) {
			case "addInformation":
				System.out.println("addInformation");
				// save all information into list string
				String rma= request.getParameter("rma");
				String sn= request.getParameter("sn");
				String rev= request.getParameter("rev");
				String manfPN= request.getParameter("manfPN");
				String mac= request.getParameter("mac");
				String cpuSN= request.getParameter("cpuSN");
				List<String> temp = new ArrayList<String>() {
					
				{
					add(ppid);
					add(rma);
					add(sn);
					add(rev);
					add(manfPN);
					add(mac);
					add(cpuSN);
				}};		
				
				if(button.equalsIgnoreCase("ADD TO LIST")) {
					System.out.println("ADD TO LIST");
					receivedList.put(ppid,temp);
				} 
				else if(button.equalsIgnoreCase("EDIT")){
					System.out.println("EDIT");
					receivedList.replace(ppid, temp);					
				}
				displayAfterAddNewExtra(request, ppid + " added to extra list!" );
				display(request, response);
				break;
			default:

				break;
			}


		}else {
			setInitial(request);
			display(request, response);
		}
	}

}
