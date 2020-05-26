package com.bizcom.MICI_Station;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.bizcom.database.DBHandler;


/**
 * Servlet implementation class MICI
 */
@WebServlet("/mici")
public class MICI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String ppid ="";
	private String sn;
	DBHandler db = new DBHandler();
	private static Set<String> errorCodeSet;
	private static List<ErrorCode> listErrorCodes = new ArrayList<>();
	private String stringError = "";
	private Map<String, Object> mapErrorCodes = new HashMap<>();
	private JSONObject jsonMap;
	private String pn;
	private boolean isBegin = true;
	public MICI() {
		listErrorCodes = db.getAllErrorCodes();
		for (ErrorCode e : listErrorCodes) {
			mapErrorCodes.put(e.getErrorCode(), (Object)e.getDescription());
		}
		jsonMap= new JSONObject(mapErrorCodes);
		System.out.println("isBegin " + isBegin);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		//		if(isBegin) {
		//			request.setAttribute("ppid", "");
		//		}
		//		if(db.checkAuthentication(request)) {			
		request.setAttribute("listErrorCodes", jsonMap);
		errorCodeSet = new HashSet<String>();
		request.setAttribute("seterrorhiddenMICI", "hidden");
		request.setAttribute("PassBTNValue", "PASS");
		request.setAttribute("setHiddenResultSucess", "hidden");
		request.setAttribute("currentCountMICI", 2);
		String page = request.getParameter("page");

		if (page == null) {
			miciDisplay(request, response);
		} else {
			switch (page) {
			case "display":
				System.out.println("display");
				miciDisplay(request, response);
				break;
			case "check":
				System.out.println("display");
				checkMICI(request, response);
				break;
			default:
				System.out.println("default");
				request.setAttribute("ppid", "");
				errorPage(request, response);
			}
		}
		//		}
		//			else {
		//			System.out.println("RUN WHEEE");
		//			response.sendRedirect(request.getContextPath() + "/signin?pagerequest=mici");
		//		}


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		System.out.println(ppid + " on post method");
		if (checkStatus(ppid)) {
			if (validate(ppid, request, db.getPhysicalInfor(ppid))) {
				String currentRevision = db.getCurrentRev(ppid).trim().toUpperCase();
				String maxRevision = db.getMaxRevision(pn).trim().toUpperCase(); 
				try {
					boolean test = db.addToMICITable(ppid, sn, errorCodeSet, "A USER FROM MICI");
					//						 System.out.println(test);
				} catch (ClassNotFoundException | SQLException e) {
					// here
					System.out.println(e.getMessage());
					e.printStackTrace();
				}

				if (action.equalsIgnoreCase("TRANSFER TO QC1")) {

					// GO to QC1
					if(currentRevision.equals(maxRevision) || maxRevision.length() == 0) {	
						if(!db.updateCurrentStation(db.MICI, db.QC1_WAITING, ppid)) {
							System.out.println("Error canot update station to transfer to QC1");

							// eror transfer qc1
							request.setAttribute("setHiddenResultSucess", "hidden");
							request.setAttribute("seterrorhiddenMICI", "show");
							request.setAttribute("errorMessage", ppid  + " can't update station to transfer to QC1_WAITINGcccccc .Check updateCurrentStation");
							request.setAttribute("sethideMICI", "hidden");

						}else {


							if(!db.updateECOStation(ppid, "FROM MICI USER")) {
								// errror
								// eror transfer qc1
								request.setAttribute("setHiddenResultSucess", "hidden");
								request.setAttribute("seterrorhiddenMICI", "show");
								request.setAttribute("errorMessage", ppid  + " can't update station to transfer to ECO .Check updateECOStation");
								request.setAttribute("sethideMICI", "hidden");

								System.out.println("Error cannot update station eco");
							}else {
								// set html qc1	
								request.setAttribute("setHiddenResultSucess", "show");
								request.setAttribute("seterrorhiddenMICI", "hidden");
								request.setAttribute("sethideMICI", "hidden");
								request.setAttribute("messageSuccess", ppid + " is successfully tranfered to QC1!");

							}
						}
					}



				} else if(action.equalsIgnoreCase("TRANSFER TO ECO")) {
					// GO TO ECO
					if(!db.updateCurrentStation(db.MICI, db.ECO_WAITING, ppid))			{

						// eror transfer qc1
						request.setAttribute("setHiddenResultSucess", "hidden");
						request.setAttribute("seterrorhiddenMICI", "show");
						request.setAttribute("errorMessage", ppid  + " can't update station to transfer to ECO_WAITING .Check updateCurrentStation");
						request.setAttribute("sethideMICI", "hidden");
						System.out.println("Error cannot update station to transfer to QC1");
					}else {
						if(!db.updateECOStation(ppid, "FROM MICI USER")) {
							// errror
							// eror transfer qc1
							request.setAttribute("setHiddenResultSucess", "hidden");
							request.setAttribute("seterrorhiddenMICI", "show");
							request.setAttribute("errorMessage", ppid  + " can't update station to transfer to ECO .Check updateECOStation");
							request.setAttribute("sethideMICI", "hidden");
							System.out.println("Error cannot update station eco");
						}else {
							//set html eco
							request.setAttribute("setHiddenResultSucess", "show");
							request.setAttribute("seterrorhiddenMICI", "hidden");
							request.setAttribute("sethideMICI", "hidden");
							request.setAttribute("messageSuccess", ppid + " is successfully tranfered to ECO!");

						}
					}									
				}
				else if (action.equalsIgnoreCase("failButton")) {
					System.out.println(request.getAttribute("currentCountMICI"));
					String errorCode = request.getParameter("errorCode");
					for (int i = 1; i < 10; i++) {
						String temp = request.getParameter("errorCode" + i);
						if (temp != null && !temp.contentEquals("0"))
							errorCodeSet.add(temp);
					}
					System.out.println("Error Code Set:" + errorCodeSet.toString());
					System.out.println("Result Update Station Status : "
							+ db.updateCurrentStation(db.MICI, db.REPAIR01_FAIL, ppid));
					try {
						boolean test = db.addToMICITable(ppid, sn, errorCodeSet, "A USER FROM MICI");
						// System.out.println(test);
					} catch (ClassNotFoundException | SQLException e) {
						// here
						System.out.println(e.getMessage());
						e.printStackTrace();
					}

					request.setAttribute("setHiddenResultSucess", "show");
					request.setAttribute("ppid", ppid);
					request.setAttribute("seterrorhiddenMICI", "hidden");
					request.setAttribute("sethideMICI", "hidden");
					request.getRequestDispatcher("/WEB-INF/views/mici_station/mici.jsp").forward(request, response);
					return;
				}
			} else {
				request.getRequestDispatcher("/WEB-INF/views/mici_station/mici.jsp").forward(request, response);
				System.out.println("Redirect to currentpage on Post Method from inner if");
			}


			request.getRequestDispatcher("/WEB-INF/views/mici_station/mici.jsp").forward(request, response);

		} else {
			request.setAttribute("sethideMICI", "hidden");
			request.setAttribute("seterrorhiddenMICI", "");
			request.setAttribute("currentStatusAtMICI", "This PPID is invalid at this station. ");
			request.getRequestDispatcher("/WEB-INF/views/mici_station/mici.jsp").forward(request, response);
			System.out.println("Redirect to currentpage on Post Method from outer if");

		}
	}

	public void checkLocation(HttpServletRequest request) {
		String currentRevision = db.getCurrentRev(ppid).trim().toUpperCase();
		String maxRevision = db.getMaxRevision(pn).trim().toUpperCase(); 
		// GO to QC1
		if(currentRevision.equals(maxRevision) || maxRevision.length() == 0) {						
			//			db.updateCurrentStation(db.MICI, db.QC1_WAITING, ppid);
			request.setAttribute("PassBTNValue", "TRANSFER TO QC1");

		}else {
			// GO TO ECO
			request.setAttribute("PassBTNValue", "TRANSFER TO ECO");
		}

	}
	public void miciDisplay(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		displayPPIDAndSN(request);
		request.setAttribute("PassBTNValue", "PASS");
		request.setAttribute("titlePageMICI", "MICI");
		request.setAttribute("ppid", "");
		request.setAttribute("sethideMICI", "hidden");
		request.getRequestDispatcher("/WEB-INF/views/mici_station/mici.jsp").forward(request, response);
	}

	public void errorPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("title", "Error page");
		request.getRequestDispatcher("/WEB-INF/error/404.jsp").forward(request, response);

	}

	public void displayPPIDAndSN(HttpServletRequest request) {
		if(ppid!=null && !ppid.isEmpty()) {
			request.setAttribute("ppid", ppid);
		}else {
			request.setAttribute("ppid", "");
		}
	}
	public void checkMICI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("problemcodeAtMICI", "");
		request.setAttribute("problemDescpAtMICI", "");
		checkState(request);

		String page = request.getParameter("page");
		ppid = request.getParameter("ppidNumber");
		sn = request.getParameter("serialnumber");
		String[] miciInfo = db.getPhysicalInfor(ppid);
		if (validate(ppid, request, miciInfo)) {
			boolean valid = false;
			// fetch information
			if (ppid != null) {
				request.setAttribute("sethideMICI", "");
				request.setAttribute("setHidenInfo", "show");
				displayPPIDAndSN(request);
				String[] currenStaions = db.getCurrentStation(ppid);

				request.setAttribute("ppidCheckAtMICI", ppid);
				request.setAttribute("snCheckAtMICI", sn);
				if(currenStaions[0] == null && currenStaions[1] == null) {
					request.setAttribute("currentStatusAtMICI", "Invalid Access This Item At This Station!");
					request.setAttribute("sethideMICI", "hidden");
					request.setAttribute("seterrorhiddenMICI", "");
					checkLocation(request);
					request.getRequestDispatcher("/WEB-INF/views/mici_station/mici.jsp").forward(request, response);					
				}


				if ( currenStaions[0].equalsIgnoreCase(db.START) 
						&& currenStaions[1].equalsIgnoreCase(db.PHYSICAL_RECEIVING)) {
					// no information here
					request.setAttribute("problemcodeAtMICI", miciInfo[0]);
					request.setAttribute("problemDescpAtMICI", miciInfo[1]);
					request.setAttribute("currentStatusAtMICI",
							"This Item Is Received From Physical Receiving Station!");
					checkLocation(request);
					System.out.println("Result Update Station Status : "
							+ db.updateCurrentStation(db.PHYSICAL_RECEIVING, db.MICI, ppid));
				} else if (currenStaions[0].equalsIgnoreCase( db.REPAIR01)
						&& currenStaions[1].equalsIgnoreCase( db.REPAIR01_PASS)) {
					// print repair infomation
					// change from REPAIR01 -> MICI
					request.setAttribute("currentStatusAtMICI", "This Item Is Returned back From Repair 01 Station!");
					checkLocation(request);
					db.updateCurrentStation( db.REPAIR01_PASS,  db.MICI, ppid);
				} 
				// Re-Scan PPID at MICI
				else if ((currenStaions[0].equalsIgnoreCase( db.PHYSICAL_RECEIVING) || 
						currenStaions[0].equalsIgnoreCase( db.REPAIR01_PASS) ||
						currenStaions[0].equalsIgnoreCase( db.REPAIR02) )
						&& currenStaions[1].equalsIgnoreCase( db.MICI)) {					
					request.setAttribute("problemcodeAtMICI", miciInfo[0]);
					request.setAttribute("problemDescpAtMICI", miciInfo[1]);
					request.setAttribute("currentStatusAtMICI",
							"This item is re-scan!!!");
					checkLocation(request);
				} 
				else if ((currenStaions[0].equalsIgnoreCase( db.QC1) || 
						currenStaions[0].equalsIgnoreCase( db.VI) ||
						currenStaions[0].equalsIgnoreCase( db.BGA) )
						&& currenStaions[1].equalsIgnoreCase( db.REPAIR02)) {
					request.setAttribute("problemcodeAtMICI", miciInfo[0]);
					request.setAttribute("problemDescpAtMICI", miciInfo[1]);
					request.setAttribute("currentStatusAtMICI",
							"PPID is from " + currenStaions[0]+" to REPAIR02");
					checkLocation(request);
				} 
				else {
					System.out.println(currenStaions[0]);
					System.out.println(currenStaions[1]);
					System.out.println("SOME THING ELSE");
					request.setAttribute("errorMessage", "Invalid Access This Item At This Station!");
					request.setAttribute("sethideMICI", "hidden");
					request.setAttribute("seterrorhiddenMICI", "");
				}
			}


			request.getRequestDispatcher("/WEB-INF/views/mici_station/mici.jsp").forward(request, response);
		} else {

			request.getRequestDispatcher("/WEB-INF/views/mici_station/mici.jsp").forward(request, response);
			System.out.println("Redirect to currentpage on check!");
		}
	}

	public boolean validate(String ppid, HttpServletRequest request, String[] miciInfo) {

		if (miciInfo[0] != null && miciInfo[1] != null) {
			request.setAttribute("problemcode", miciInfo[0]);
			request.setAttribute("problemdecription", miciInfo[1]);
			if (miciInfo[0].equals("N/A")) {
				request.setAttribute("seterrorhiddenproblemMICI", "hidden");
			}
			pn = miciInfo[2];
			return true;
		} else {
			request.setAttribute("sethideMICI", "hidden");
			request.setAttribute("seterrorhiddenMICI", "");
			displayPPIDAndSN(request);
			request.setAttribute("errorMessage", ppid +" DOESN'T FOUND!");
			pn = "";
			return false;
		}
	}

	public boolean checkStatus(String ppid) {
		String[] status = db.getCurrentStation(ppid);
		System.out.println(status[0]+" "+status[1]);
		if ((status[0].equalsIgnoreCase(db.PHYSICAL_RECEIVING) && status[1].equalsIgnoreCase( db.MICI))|| ( status[0].equalsIgnoreCase(db.REPAIR01_PASS)
				&& status[1].equalsIgnoreCase(db.MICI))) {
			return true;
		} else {
			return false;
		}
	}

	public void checkState(HttpServletRequest request) {
		request.setAttribute("hiddenSelectErrorCode", "hidden");
		request.setAttribute("hiddenFailBTN", "hidden");
		//		request.setAttribute("hiddenAddErrorCodeBTN", "hidden");

	}
}
