package com.bizcom.eco;

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

import com.bizcom.database.DBHandler;
import com.bizcom.repair01.RevesionUpgrade;

/**
 * Servlet implementation class Eco_Servlet
 */
@WebServlet("/eco")
public class Eco_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String ppid;
	private DBHandler db = new DBHandler();
	private int currentRev = -1;
	private int maxRev = -1;


	private boolean errorCodeFlag = false;
	private boolean updateRevisionFlag = false;
	private boolean isTransferButtonClicked = false;
	private JSONObject jsonMap;
	private String partNumber = "";

	private List<List<String>> revisionList;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Eco_Servlet() {

		super();
		try {
			db.getConnectionAWS();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		jsonMap= new JSONObject(db.createECOInstruction());

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		displayInitialView(request, response);
		//		if(db.checkAuthentication(request)) {		
		System.out.println("doget called");
		String action = request.getParameter("action01");
		request.setAttribute("resultHidden", "hidden");
		request.setAttribute("ins", jsonMap);
		if (action != null) {
			switch (action) {
			case "findPPID":
				ppid = request.getParameter("inputppid").trim().toUpperCase();
				System.out.println("findPPID");
				if (db.validatePPID(ppid)) {

					String[] stationIndo = db.getCurrentStation(ppid);
					// Check If PPID stay at corrected station
					// FROM : MICI TO : REPAIR01_FAIL
					if ((stationIndo[0].equalsIgnoreCase(db.MICI) && stationIndo[1].equalsIgnoreCase(db.ECO_WAITING))|| 
							(stationIndo[0].equalsIgnoreCase(db.ECO_WAITING) && stationIndo[1].equalsIgnoreCase(db.ECO)))
					{
						boolean updateFlag = db.generateErrorRecord(ppid);
						//							displayInitialView(request, response, false);
						partNumber = db.getPartNumber(ppid);						
						revisionList = db.getInstruction(partNumber);							
						if(!revisionList.isEmpty()) {
							request.setAttribute("resultHidden", "show");
							request.setAttribute("listItems", revisionList);
							request.setAttribute("instructionDetail", getInstructionKey(revisionList));
							request.setAttribute("imgLink", "/WEB-INF/images/Q.png");

						}
						else {
							System.out.println("revisionList is empty");
							request.setAttribute("warningMessage", "No Instruction Details Found!!!");
						}
						db.updateCurrentStation(db.ECO_WAITING, db.ECO, ppid);
						request.getRequestDispatcher("/WEB-INF/views/eco/eco.jsp").forward(request, response);

					} 


					else {
						displayError(request, response, ppid+ " is Not Found!");
					}

				} else {
					displayError(request, response, ppid+ " is Not Found!");
				}
				break;
			case "transfertoMICI":
				request.getRequestDispatcher("/WEB-INF/views/eco/eco.jsp").forward(request, response);
				break;

			}
		} else {
			//			displayInitialView(request, response, true);
			request.getRequestDispatcher("/WEB-INF/views/eco/eco.jsp").forward(request, response);
		}
		//		}else {
		//			System.out.println("RUN WHEEE");
		//			response.sendRedirect(request.getContextPath() + "/signin?pagerequest=eco");
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
			case "transfertoMICI":
				if(db.updateCurrentStation(db.ECO, db.QC1_WAITING, ppid)) {

					if(db.updateRevision(ppid, db.getMaxRevision(db.getPartNumber(ppid)))) {
						
						System.out.println("Added updateRevision");
						displaySuccess(request, response, ppid + " is updated successfully. QC1 is the next Station.");
						
						
					}else {
						displayError(request, response, ppid + " can't update revision! Please double check updateRevision_in_ECO_servlet"); 
					}
				}else {

					displayError(request, response, ppid + " can't update current station! Please double check updateCurrentStation_in_ECO_servlet"); 
					System.out.println("Can't update current station! _ updateCurrentStation");
				}

//				request.getRequestDispatcher("/WEB-INF/views/eco/eco.jsp").forward(request, response);
				break;
			}
		}
	}

	public void updateRevision(HttpServletRequest request, HttpServletResponse response, String ppid)
			throws ServletException, IOException {

		//		System.out.println("updateRevision");

		//		String curRev = db.getCurrentRev(ppid);
		//		request.setAttribute("setHiddenTransfer", "hidden");

		//		if (curRev.isEmpty())
		//			currentRev = -1;
		//		else {
		//			currentRev = Integer.parseInt(curRev.substring(curRev.length() - 2, curRev.length()));
		//		}
		//		getErrors(request, response, ppid);
		// Do logic Here
		//		if (currentRev != -1) {
		//			System.out.println("need update currentRev");
		//			String partNumber = db.getPartNumber(ppid);
		//			// If not part number
		//			if (!db.checkIfPartNumberExist(partNumber)) {
		//				ppidUpdatedOk(request, response, true);
		//				// todo: show something
		//				System.out.println("tao o day checkIfPartNumberExist");
		//
		//				request.setAttribute("setInfoPPIDetails", "show");
		//				request.setAttribute("messageIcon", "No Revision Upgrade Needed for this PPID");
		//				request.setAttribute("iconColor", "success");
		//				request.setAttribute("curRevNumber", generatorRev(currentRev) + " No Revision Upgrade Needed for this PPID");
		//
		//				updateRevisionFlag = true;
		//				if (errorCodeFlag && updateRevisionFlag) {
		//					request.setAttribute("setHiddenTransfer", "show");
		//					request.setAttribute("setInfoPPIDetails ", "hidden");
		//					request.setAttribute("setRepair01HiddenError ", "hidden");
		//					request.setAttribute("setHiddenBodyRepair01 ", "hidden");
		//					request.setAttribute("setTransferMessageSuccess", "hidden");
		//				}
		//
		//			} else {
		////				String maxRevesion = db.getMaxRevision(partNumber);
		////				if (maxRevesion.length() > 0) {
		////
		////					maxRev = Integer.parseInt(maxRevesion.substring(maxRevesion.length() - (maxRevesion.length() - 1)));
		////					if (currentRev < maxRev) {
		////						currentLessThanMax(request, response, partNumber);
		////					} else {
		////						System.out.println("currentRev does not to update");
		////						ppidUpdatedOk(request, response, false);
		////
		////						updateRevisionFlag = true;
		////						if (errorCodeFlag && updateRevisionFlag) {
		////							request.setAttribute("setHiddenTransfer", "show");
		////							request.setAttribute("setInfoPPIDetails ", "hidden");
		////							request.setAttribute("setRepair01HiddenError ", "hidden");
		////							request.setAttribute("setHiddenBodyRepair01 ", "hidden");
		////						}
		////					}
		//				} else {
		//					updateRevisionFlag = true;
		//					request.setAttribute("curRevNumber", generatorRev(currentRev));
		//					request.setAttribute("iconColor", "success");
		//					request.setAttribute("messageIcon", "Your ppid is updated");
		//					request.setAttribute("setHiddenBodyRepair01", "show");
		//					request.setAttribute("setRepair01Hidden", "hidden");
		//					request.setAttribute("setRepair01HiddenError", "hidden");
		//					request.setAttribute("setErrorMessageHidden", "hidden");
		//					request.setAttribute("setSuccessMessageHidden", "hidden");
		//					request.setAttribute("setHiddenBodyRepair01", "show");
		//					request.getRequestDispatcher("/WEB-INF/views/eco/eco.jsp").forward(request, response);
		//				}
		//			}
		//		} else {
		//			System.out.println("currentRev is invalid");
		//			request.setAttribute("setRepair01HiddenError", "hidden");
		//			request.getRequestDispatcher("/WEB-INF/views/eco/eco.jsp").forward(request, response);
		//		}
	}

	public JSONObject getInstructionKey(List<List<String>> list) {

		List<String> keyList = new ArrayList<String>();
		List<List<String>> valueList = new ArrayList<List<String>>();

		for(List<String> l : list) {
			String code = l.get(0);
			keyList.add(code);
			valueList.add(db.getDetailsInstruction(code));
		}
		Map<String,Object>map = new HashMap<String, Object>();
		map.put("1",(Object) keyList);
		map.put("2",(Object) valueList);
		return new JSONObject(map);
	}

	public void displayError(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {

		displayInitialView(request, response);
		request.setAttribute("setHiddenResultSucess", "hidden");
		request.setAttribute("seterrorhiddenMICI", "show");
		request.setAttribute("errorMessage",message );
		request.getRequestDispatcher("/WEB-INF/views/eco/eco.jsp").forward(request, response);
	}
	public void displaySuccess(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {

		displayInitialView(request, response);
		request.setAttribute("setHiddenResultSucess", "show");
		request.setAttribute("seterrorhiddenMICI", "hidden");
		request.setAttribute("messageSuccess",message );
		request.getRequestDispatcher("/WEB-INF/views/eco/eco.jsp").forward(request, response);
	}
	public void displayInitialView(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("setHiddenResultSucess", "hidden");
		request.setAttribute("seterrorhiddenMICI", "hidden");
		request.setAttribute("resultHidden", "hidden");

	}

}
