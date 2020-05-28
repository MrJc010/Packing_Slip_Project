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
	private int maxRev = -1;

	private final String MICI = "MICI";
	private final String REPAIR01_FAIL = "REPAIR01_FAIL";
	private final String REPAIR01_PASS = "REPAIR01_PASS";
	private final String REPAIR01 = "REPAIR01";
	private boolean errorCodeFlag = false;
	private boolean isTransferButtonClicked = false;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RepairStaion01() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//if(db.checkAuthentication(request)) {		
			System.out.println("doget called");
			String action = request.getParameter("action01");
			if (action != null) {
				switch (action) {
				case "findPPID":
					ppid = request.getParameter("inputppid").trim().toUpperCase();
					if (!ppid.isEmpty() && db.isPPIDExistInMICI(ppid)) {
						String[] stationIndo = db.getCurrentStation(ppid);
						// Check If PPID stay at corrected station
						// FROM : MICI TO : REPAIR01_FAIL
						if (stationIndo[0].equalsIgnoreCase(MICI) && stationIndo[1].equalsIgnoreCase(REPAIR01_FAIL)) {
							boolean updateFlag = db.generateErrorRecord(ppid);
							if (updateFlag) {
								displayInitialView(request, response, false);
								// Update status also
								db.updateCurrentStation(REPAIR01_FAIL, REPAIR01, ppid);
							} else {
								// TODO: DELETE ME
								System.out.println("doGet ppid valid generate new record NOT OK");
								displayError(request, response, ppid, "Error system! We cannot upload error to system.");
							}
						} else if (stationIndo[0].equalsIgnoreCase(REPAIR01) && stationIndo[1].equalsIgnoreCase(REPAIR01_PASS)) {
							System.out.println("PASSED PPID");
							isTransferButtonClicked = true;
							displayInitialView(request, response, true);		

							request.getRequestDispatcher("/WEB-INF/views/repair_01/repair01.jsp").forward(request,response);
						}
						else if(stationIndo[0].equalsIgnoreCase(REPAIR01_FAIL) && stationIndo[1].equalsIgnoreCase(REPAIR01)){
							System.out.println("ppid exist");
							request.setAttribute("setPPID", ppid);
							displayInitialView(request, response, false);
						}else {
							displayError(request, response, ppid, "PPID is Not Found!");
						}

					} else {
						displayError(request, response, ppid, "PPID is Not Found!");
					}
					break;
				case "TransferAction":
					displayInitialView(request, response, true);
					request.getRequestDispatcher("/WEB-INF/views/repair_01/repair01.jsp").forward(request, response);
					break;
				}
			} else {
				displayInitialView(request, response, true);
				request.getRequestDispatcher("/WEB-INF/views/repair_01/repair01.jsp").forward(request, response);
			}
//		}else {
//			System.out.println("RUN WHEEE");
//			response.sendRedirect(request.getContextPath() + "/signin?pagerequest=repair01");
//		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("doPost posted called");
		String action = request.getParameter("action01");
		if (action != null) {
			switch (action) {
			case "SubmitAction":
				System.out.println("SubmitAction");
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
					System.out.println("Cannot upload infomation to updateRepair01RecordAction");
				}
				getErrors(request, response, ppid);
				displayInitialView(request, response, false);
				break;
			case "TransferAction":
				db.updateCurrentStation(REPAIR01, REPAIR01_PASS, ppid);
				response.sendRedirect(request.getContextPath()+"/repair01?action01=findPPID&inputppid="+ppid+"&actionSubmitRepair01=FIND");
				break;
			}
		}
	}

	public void getErrors(HttpServletRequest request, HttpServletResponse response, String ppid) {
		HashMap<String, String> result = db.fetchErrorFromRepair01(ppid);
		request.setAttribute("setRepair01HiddenFix", "show");
		if (result.size() == 0) {
			errorCodeFlag = true;
			request.setAttribute("setPPID", ppid);
			request.setAttribute("setErrorColor", "success");
			request.setAttribute("setRepair01HiddenError", "");
			request.setAttribute("setSuccessMessage", ppid + " has NO ERROR!");
			// no error ccheck revision....
		} else {
			errorCodeFlag = false;
			// List all error
			request.setAttribute("setPPID", ppid);
			request.setAttribute("setErrorColor", "warning");
			request.setAttribute("sizeErrorList", result.size());
			request.setAttribute("setRepair01Hidden", "");
			request.setAttribute("currentErrorNumber", result.size());
			List<String> listErrorStr = new ArrayList<>();
			result.forEach((k, v) -> {
				listErrorStr.add(k + " --> " + v);
			});
			System.out.println("here");
			request.setAttribute("errorList", listErrorStr);
		}
		if (errorCodeFlag) {
			request.setAttribute("setHiddenTransfer", "show");
			request.setAttribute("setInfoPPIDetails", "hidden");
			request.setAttribute("setRepair01HiddenError", "hidden");
			request.setAttribute("setHiddenBodyRepair01", "hidden");
		} else {
			request.setAttribute("setHiddenTransfer", "hidden");
			request.setAttribute("setRepair01HiddenError", "hidden");
			request.setAttribute("setErrorMessageHidden ", "hidden");
			request.setAttribute("setSuccessMessageHidden ", "hidden");
			request.setAttribute("setSuccessMessage", "hidden");
		}
	}

	public void displayError(HttpServletRequest request, HttpServletResponse response, String ppid, String message) throws ServletException, IOException {

		displayInitialView(request, response, true);
		request.setAttribute("setRepair01HiddenError", "show");
		request.setAttribute("setErrorMessageHidden", "show");
		request.setAttribute("setSuccessMessageHidden", "hidden");
		request.setAttribute("setErrorMessage", ppid + " : " + message);
		request.getRequestDispatcher("/WEB-INF/views/repair_01/repair01.jsp").forward(request, response);
	}

	public void displayInitialView(HttpServletRequest request, HttpServletResponse response, boolean isBegin) throws ServletException, IOException {
		request.setAttribute("curRevNumber", db.getCurrentRev(ppid));
		if (isBegin) {
			if (isTransferButtonClicked) {
				request.setAttribute("setTransferMessageSuccess", "show");
				isTransferButtonClicked = false;
			}else {
				request.setAttribute("setTransferMessageSuccess", "hidden");
			}
			request.setAttribute("setHiddenTransfer", "hidden");
			request.setAttribute("setInfoPPIDetails", "hidden");
			request.setAttribute("setHiddenBodyRepair01", "hidden");
			request.setAttribute("setRepair01HiddenError", "hidden");
			request.setAttribute("setErrorMessageHidden", "hidden");
			request.setAttribute("setErrorMessageHidden", "hidden");
			request.setAttribute("setSuccessMessageHidden", "hidden");
		} else {
			if (errorCodeFlag) {
				if (isTransferButtonClicked) {
					request.setAttribute("setTransferMessageSuccess", "show");
					isTransferButtonClicked = false;
				}else {
					request.setAttribute("setTransferMessageSuccess", "hidden");
				}
				request.setAttribute("setInfoPPIDetails", "hidden");
				request.setAttribute("setHiddenBodyRepair01", "hidden");
			} else {
				if (isTransferButtonClicked) {
					request.setAttribute("setTransferMessageSuccess", "show");
					isTransferButtonClicked = false;
				}else {
					request.setAttribute("setTransferMessageSuccess", "hidden");
				}
				request.setAttribute("setTransferMessageSuccess", "hidden");
				request.setAttribute("setInfoPPIDetails", "show");
				request.setAttribute("setHiddenBodyRepair01", "show");
				getErrors(request, response, ppid);
			}
			request.getRequestDispatcher("/WEB-INF/views/repair_01/repair01.jsp").forward(request, response);
		}
	}


}
