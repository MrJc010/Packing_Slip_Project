package com.bizcom.repair01;

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

import org.json.JSONObject;

import com.bizcom.MICI_Station.ErrorCode;
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

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RepairStaion01() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//		if(db.checkAuthentication(request)) {		
		String action = request.getParameter("action01");
		if (action != null) {
			switch (action) {
			case "findPPID":
				ppid = request.getParameter("inputppid").trim().toUpperCase();
				if (!ppid.isEmpty() && db.isPPIDExistInMICI(ppid)) {					
					String[] stationIndo = db.getCurrentStation(ppid);
					// Check If PPID stay at corrected station
					// FROM : MICI TO : REPAIR01_FAIL
					if (stationIndo[0].equalsIgnoreCase(DBHandler.MICI) && stationIndo[1].equalsIgnoreCase(DBHandler.REPAIR01_FAIL)) {
						boolean updateFlag = db.generateErrorRecord(ppid);
						// updateFlag true when we can install list error into a table
						if (updateFlag) {							
							if(!db.updateCurrentStation(DBHandler.REPAIR01_FAIL, DBHandler.REPAIR01, ppid)) {
								displayError(request,  ppid + " has problem. Can't update status of this ppid!");
							}else {
								findPPID(request, ppid);
							}
						} else {
							// error cannot insert error to table
							displayError(request,  ppid + " has problem. Can't generate error code to table!");
						}
					} else if (stationIndo[0].equalsIgnoreCase(DBHandler.REPAIR01)
							&& stationIndo[1].equalsIgnoreCase(DBHandler.REPAIR01_PASS)) {
						displayTransferState(request,  ppid + " has NO ERROR! Transfer to MICI now.");
					}
					else if(stationIndo[0].equalsIgnoreCase(DBHandler.REPAIR01_FAIL) && stationIndo[1].equalsIgnoreCase(DBHandler.REPAIR01)){
						findPPID(request, ppid);
					}else {
						//SPECIAL CASE push up error
						displayError(request, ppid + " DOESN\"t BELONG TO THIS STATION");
					}
					request.setAttribute("ppid", "");
					request.getRequestDispatcher("/WEB-INF/views/repair_01/repair01.jsp").forward(request, response);
				} else {
					displayError(request, ppid + " IS NOT FOUND! PLEASE CHECK WITH ANOTHER PPID");
					request.setAttribute("ppid", "");
					request.getRequestDispatcher("/WEB-INF/views/repair_01/repair01.jsp").forward(request, response);
				}
				
				break;
			default: {
				System.out.println("Defaul case called");
				break;
			}

			}
		} else {
			request.setAttribute("setHiddenResultSucess", "hidden");
			request.setAttribute("setInfoHidden", "hidden");
			request.setAttribute("setHiddenTransferButton", "hidden");
			request.setAttribute("seterrorhiddenMICI", "hidden");
			request.setAttribute("reapir01BodyHidden", "hidden");
			request.setAttribute("ppid", "");
			request.getRequestDispatcher("/WEB-INF/views/repair_01/repair01.jsp").forward(request, response);
		}
		//		}else {
		//			response.sendRedirect(request.getContextPath() + "/signin?pagerequest=repair01");
		//		}
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
			case "fix":
				String err = request.getParameter("errorValueAction");
				String errorCode = err.split(" --> ")[0];
				String duty = request.getParameter("duty" + err);
				String oldPN = request.getParameter("oldPN" + err);
				String newPN = request.getParameter("newPN" + err);
				String area = request.getParameter("area" + err);
				String actionJob = request.getParameter("action" + err);
				int recordID = db.updateRepair01RecordAction(errorCode, ppid, duty, oldPN, newPN, area, actionJob);
				if(recordID!= -1) {
					db.updateRefixMICITable(errorCode,ppid,recordID+"");
				}else {
					displayError(request, ppid + " has an error. Please check system");
				}
				findPPID(request, ppid);
				request.getRequestDispatcher("/WEB-INF/views/repair_01/repair01.jsp").forward(request, response);
				break;
			case "transferToMICI":
//				TODO ; CHECK LOGIC
//				AFTER CLICK BUTTON WILL CHANGE THE STATUS ONLY
				db.updateCurrentStation(DBHandler.REPAIR01_PASS, DBHandler.MICI, ppid);
				displayAfterTransfer(request, ppid + " TRANSFERED TO MICI.");
				request.getRequestDispatcher("/WEB-INF/views/repair_01/repair01.jsp").forward(request, response);
				break;
			}
		}
	}


	public void displayError(HttpServletRequest request,  String message) throws ServletException, IOException {
		request.setAttribute("setHiddenResultSucess", "hidden");
		request.setAttribute("seterrorhiddenMICI", "show");
		request.setAttribute("setHiddenTransferButton", "hidden");
		request.setAttribute("reapir01BodyHidden", "hidden");
		request.setAttribute("setInfoHidden", "hidden");
		request.setAttribute("ppid", ppid);
		request.setAttribute("errorMessage", message);
	}

	public void displayTransferState(HttpServletRequest request, String message) throws ServletException, IOException {
		request.setAttribute("setHiddenResultSucess", "show");
		request.setAttribute("setHiddenTransferButton", "show");
		request.setAttribute("seterrorhiddenMICI", "hidden");
		request.setAttribute("setInfoHidden", "hidden");
		request.setAttribute("reapir01BodyHidden", "hidden");
		request.setAttribute("ppid", "");
		request.setAttribute("messageSuccess", message);
	}
	public void displayAfterTransfer(HttpServletRequest request, String message) throws ServletException, IOException {
		request.setAttribute("setHiddenResultSucess", "show");
		request.setAttribute("setHiddenTransferButton", "hidden");
		request.setAttribute("seterrorhiddenMICI", "hidden");
		request.setAttribute("setInfoHidden", "hidden");
		request.setAttribute("reapir01BodyHidden", "hidden");
		request.setAttribute("ppid", "");
		request.setAttribute("messageSuccess", message);

	}
	public void findPPID(HttpServletRequest request, String ppid) throws ServletException, IOException {
		request.setAttribute("setHiddenResultSucess", "hidden");
		request.setAttribute("setHiddenTransferButton", "hidden");
		request.setAttribute("seterrorhiddenMICI", "hidden");
		request.setAttribute("setInfoHidden", "show");
		request.setAttribute("reapir01BodyHidden", "show");
		request.setAttribute("ppid", ppid);
		HashMap<String, String> result = db.fetchErrorFromRepair01(ppid);
		if (result.size() == 0) {			
			displayTransferState(request,  ppid + " has NO ERROR! Transfer to MICI now.");
		} else {
			request.setAttribute("setPPID", ppid);
			request.setAttribute("setErrorColor", "warning");
			request.setAttribute("currentErrorNumber", result.size());			
			List<String> listErrorStr = new ArrayList<>();
			result.forEach((k, v) -> {
				listErrorStr.add(k + " --> " + v);
			});
			System.out.println("here");
			request.setAttribute("errorList", listErrorStr);
		}

	}

}
