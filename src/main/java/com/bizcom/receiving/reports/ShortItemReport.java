package com.bizcom.receiving.reports;

import java.io.IOException;
import java.util.List;

import javax.activation.DataHandler;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.bizcom.database.DBHandler;

/**
 * Servlet implementation class Shipping_servlet
 */
@WebServlet("/shortitem")
public class ShortItemReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DBHandler db = new DBHandler();
	private String rma;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShortItemReport() {
		super();

		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if(db.checkAuthentication(request)) {
			if (action != null && action.equalsIgnoreCase("findRMA")) {
				rma = request.getParameter("inputrma").trim().toUpperCase();				
				if (!rma.isEmpty()) {
					List<List<String>> passingList = db.getUnReceiveItem(rma);
					System.out.println("passingList" + passingList);
					request.setAttribute("passingList", passingList);
				}
			}
			request.getRequestDispatcher("/WEB-INF/views/receiving_station/reports/shortitemreport.jsp").forward(request, response);
		}else {
			response.sendRedirect(request.getContextPath() + "/signin?pagerequest=shortitem");
		}
		//
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
