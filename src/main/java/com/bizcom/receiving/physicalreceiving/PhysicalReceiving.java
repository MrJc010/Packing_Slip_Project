package com.bizcom.receiving.physicalreceiving;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bizcom.database.DBHandler;

@WebServlet(urlPatterns = "/physicalreceiving")
public class PhysicalReceiving extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1365646760784374827L;
	private DBHandler dbhandler;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
//		String ppid = request.getParameter("ppid");
//		String dps = request.getParameter("dps");
		request.setAttribute("title", "Search Item");
		request.getRequestDispatcher("/WEB-INF/views/receiving_station/physicalreceiving/physicalreceiving.jsp")
				.forward(request, response);
		
		dbhandler = new DBHandler();
		dbhandler.getConnectionAWS();
		dbhandler.testConnection();
		
		
//		if (!ppid.isEmpty() && !dps.isEmpty()) {

			// based on ppid and dps get data

//			System.out.println("=====");
//			dbhandler.testConnection();
//			List<Item> myList = new ArrayList<>();
//			myList.addAll(dbhandler.fetchRMA(ppid, dps));
//
//			for (Item it : myList) {
//				System.out.println(it.toString());
//			}
//		} else {
//			System.out.println("Return Back to Search I tem");
//		}
		
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String rmaNum = request.getParameter("rma");
		String ppid = request.getParameter("ppid");
		String dps = request.getParameter("dps");

		String url = request.getContextPath() + "/rma-receiver?rma=" + rmaNum + "&ppid=" + ppid + "&dps=" + dps;
		response.sendRedirect(url);
	}
}
