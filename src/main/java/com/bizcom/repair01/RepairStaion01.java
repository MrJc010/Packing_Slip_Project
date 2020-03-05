package com.bizcom.repair01;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bizcom.database.DBHandler;

/**
 * Servlet implementation class RepairStaion01
 */
@WebServlet("/repair01")
public class RepairStaion01 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String ppid;
	private DBHandler db = new DBHandler();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RepairStaion01() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// TODO Auto-generated method stub
		ppid = request.getParameter("inputppid");
		request.setAttribute("setRepair01Hidden", "hidden");
		request.setAttribute("setRepair01HiddenError", "hidden");
		
		String actionClick = request.getParameter("actionSubmitRepair01");
		if (actionClick != null) {
			
			if (ppid != null) {
				
				List<String> errorList = db.fetchErrorForRepair01FromMICI(ppid);
				
				if(errorList.size()==0) {
					request.setAttribute("setRepair01HiddenError", "");
					request.setAttribute("setErrorMessage", ppid + " not found at this station.");
				}else {
					// List all error
					request.setAttribute("sizeErrorList", errorList.size());
					request.setAttribute("setRepair01Hidden", "");
					
					request.setAttribute("errorList", errorList);
					
					db.addNewToRepair01Table(ppid, "UserID");
					
					
				}
			} else {
				request.setAttribute("setRepair01HiddenError", "");
				request.setAttribute("setErrorMessage", ppid + " not found at this station.");
			}
		}

		request.getRequestDispatcher("/WEB-INF/views/repair_01/repair01.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
//		
	}

}
