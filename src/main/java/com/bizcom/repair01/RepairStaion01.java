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
	private boolean updateRevisionFlag = false;
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
							updateRevision(request, response, ppid);
						} else {
							// TODO: DELETE ME
							System.out.println("doGet ppid valid generate new record NOT OK");

//							displayError(request, response, ppid, "Error system! We cannot upload error to system.");
						}
					} else if (stationIndo[0].equalsIgnoreCase(REPAIR01)
							&& stationIndo[1].equalsIgnoreCase(REPAIR01_PASS)) {
						System.out.println("PASSED PPID");
						isTransferButtonClicked = true;
						displayInitialView(request, response, true);		
						
						request.getRequestDispatcher("/WEB-INF/views/repair_01/repair01.jsp").forward(request,response);
					}
					else if(stationIndo[0].equalsIgnoreCase(REPAIR01_FAIL) && stationIndo[1].equalsIgnoreCase(REPAIR01)){
						System.out.println("ppid exist");
						request.setAttribute("setPPID", ppid);
						displayInitialView(request, response, false);
						updateRevision(request, response, ppid);
					}else {
						displayError(request, response, ppid, "PPID is Not Found!");
					}

				} else {
					displayError(request, response, ppid, "PPID is Not Found!");
				}
				break;
			case "updateRevision":
				updateRevision(request, response, ppid);
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
			case "updateRevision":
				String newRev = generatorRev(currentRev + 1);
				db.updateRevision(ppid, newRev);
				doGet(request, response);
				break;
			case "SubmitAction":
				System.out.println("SubmitAction");
				String err = request.getParameter("errorValueAction");
				String errorCode = err.split(" --> ")[0];
				String duty = request.getParameter("duty" + err);
				String oldPN = request.getParameter("oldPN" + err);
				String newPN = request.getParameter("newPN" + err);
				String area = request.getParameter("area" + err);
				String actionJob = request.getParameter("action" + err);
				db.updateRepair01RecordAction(errorCode, ppid, duty, oldPN, newPN, area, actionJob);
				db.updateRefixMICITable(errorCode,ppid);
				getErrors(request, response, ppid);
				updateRevision(request, response, ppid);
				break;
			case "TransferAction":
				db.updateCurrentStation(REPAIR01, REPAIR01_PASS, ppid);
				response.sendRedirect(request.getContextPath()+"/repair01?action01=findPPID&inputppid="+ppid+"&actionSubmitRepair01=FIND");
				break;
			}
		}
	}

	public void updateRevision(HttpServletRequest request, HttpServletResponse response, String ppid)
			throws ServletException, IOException {

		System.out.println("updateRevision");

		String curRev = db.getCurrentRev(ppid);
		request.setAttribute("setHiddenTransfer", "hidden");

		if (curRev.isEmpty())
			currentRev = -1;
		else {
			currentRev = Integer.parseInt(curRev.substring(curRev.length() - 2, curRev.length()));
		}
		getErrors(request, response, ppid);
		// Do logic Here
		if (currentRev != -1) {
			System.out.println("need update currentRev");
			String partNumber = db.getPartNumber(ppid);
			// If not part number
			if (!db.checkIfPartNumberExist(partNumber)) {
				ppidUpdatedOk(request, response, true);
				// todo: show something
				System.out.println("tao o day checkIfPartNumberExist");

				request.setAttribute("setInfoPPIDetails", "show");
				request.setAttribute("messageIcon", "No Revision Upgrade Needed for this PPID");
				request.setAttribute("iconColor", "success");
				request.setAttribute("curRevNumber", generatorRev(currentRev) + " No Revision Upgrade Needed for this PPID");

				updateRevisionFlag = true;
				if (errorCodeFlag && updateRevisionFlag) {
					request.setAttribute("setHiddenTransfer", "show");
					request.setAttribute("setInfoPPIDetails ", "hidden");
					request.setAttribute("setRepair01HiddenError ", "hidden");
					request.setAttribute("setHiddenBodyRepair01 ", "hidden");
					request.setAttribute("setTransferMessageSuccess", "hidden");
				}

			} else {
				String maxRevesion = db.getMaxRevision(partNumber);
				if (maxRevesion.length() > 0) {

					maxRev = Integer.parseInt(maxRevesion.substring(maxRevesion.length() - (maxRevesion.length() - 1)));
					if (currentRev < maxRev) {
						currentLessThanMax(request, response, partNumber);
					} else {
						System.out.println("currentRev does not to update");
						ppidUpdatedOk(request, response, false);

						updateRevisionFlag = true;
						if (errorCodeFlag && updateRevisionFlag) {
							request.setAttribute("setHiddenTransfer", "show");
							request.setAttribute("setInfoPPIDetails ", "hidden");
							request.setAttribute("setRepair01HiddenError ", "hidden");
							request.setAttribute("setHiddenBodyRepair01 ", "hidden");
						}
					}
				} else {
					updateRevisionFlag = true;
					request.setAttribute("curRevNumber", generatorRev(currentRev));
					request.setAttribute("iconColor", "success");
					request.setAttribute("messageIcon", "Your ppid is updated");
					request.setAttribute("setHiddenBodyRepair01", "show");
					request.setAttribute("setRepair01Hidden", "hidden");
					request.setAttribute("setRepair01HiddenError", "hidden");
					request.setAttribute("setErrorMessageHidden", "hidden");
					request.setAttribute("setSuccessMessageHidden", "hidden");
					request.setAttribute("setHiddenBodyRepair01", "show");
					request.getRequestDispatcher("/WEB-INF/views/repair_01/repair01.jsp").forward(request, response);
				}
			}
		} else {
			System.out.println("currentRev is invalid");
			request.setAttribute("setRepair01HiddenError", "hidden");
			request.getRequestDispatcher("/WEB-INF/views/repair_01/repair01.jsp").forward(request, response);
		}
	}

	public String generatorRev(int x) {
		if (x / 10 < 1) {
			return "A0" + x;
		} else {
			return "A" + x;
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
			request.setAttribute("setSuccessMessage", ppid + " has NO ERROR! Please check REVESION UPGRADE IF REQUIRE!");
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
			request.setAttribute("errorList", listErrorStr);
		}
		if (errorCodeFlag && updateRevisionFlag) {
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

	public void displayInitialView(HttpServletRequest request, HttpServletResponse response, boolean isBegin) {
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
			if (errorCodeFlag && updateRevisionFlag) {
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
			}
		}
	}

	public void ppidUpdatedOk(HttpServletRequest request, HttpServletResponse response, boolean flag) throws ServletException, IOException {
		displayInitialView(request, response, false);
		request.setAttribute("setRepair01Hidden", "hidden");
		request.setAttribute("setRepair01HiddenFix  ", "show");
		request.setAttribute("iconColor", "success");
		request.setAttribute("messageIcon", "Your ppid is updated");
		if (flag) {
			request.setAttribute("curRevNumber",
					generatorRev(currentRev) + " No Revision Upgrade Needed for this PPID");
		} else {
			request.setAttribute("curRevNumber", generatorRev(currentRev));
		}
		request.getRequestDispatcher("/WEB-INF/views/repair_01/repair01.jsp").forward(request, response);
	}

	public void currentLessThanMax(HttpServletRequest request, HttpServletResponse response, String partNumber) {
		request.setAttribute("setRepair01Hidden", "show");
		updateRevisionFlag = false;
		// Fetch data base on currentNum
		db.createInstruction();
		// TODO if PART NUMBER DOES NOT EXIST
		RevesionUpgrade temp = db.getInstruction(partNumber, generatorRev(currentRev), generatorRev(currentRev + 1));
		if (temp.getOldMaterial() == null) {
			request.setAttribute("curRevNumber", generatorRev(currentRev));
			request.setAttribute("iconColor", "warning");
			request.setAttribute("messageIcon", "You must to upgrade revision to pass");
			request.setAttribute("nextRevNumber", generatorRev(currentRev + 1));
			request.setAttribute("partNumber", "NA");
			request.setAttribute("location", "NA");
			request.setAttribute("desc", "NA");
			request.setAttribute("ecoAction", "NA");
			request.setAttribute("oldMaterialPN", "NA");
			request.setAttribute("newMaterialPN", "NA");
			request.setAttribute("shortcut", "NA");
			request.setAttribute("setPPID", ppid);

		} else {
			request.setAttribute("curRevNumber", generatorRev(temp.getCurrentRev()));
			request.setAttribute("iconColor", "warning");
			request.setAttribute("messageIcon", "You must to upgrade revision to pass");
			request.setAttribute("nextRevNumber", generatorRev(temp.getCurrentRev() + 1));
			request.setAttribute("partNumber", temp.getPn());
			request.setAttribute("location", temp.getLocation());
			request.setAttribute("desc", temp.getDesc());
			request.setAttribute("ecoAction", temp.getEcoAction());
			request.setAttribute("oldMaterialPN", temp.getOldMaterial());
			request.setAttribute("newMaterialPN", temp.getNewMaterial());
			request.setAttribute("shortcut", temp.getShortcut());
			request.setAttribute("setPPID", ppid);
		}
		displayInitialView(request, response, false);
		request.setAttribute("setRepair01HiddenError", "hidden");
		try {
			request.getRequestDispatcher("/WEB-INF/views/repair_01/repair01.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
