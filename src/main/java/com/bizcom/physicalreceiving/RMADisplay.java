package com.bizcom.physicalreceiving;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bizcom.database.DBHandler;

@WebServlet(urlPatterns = "/rma-receiver")
public class RMADisplay extends HttpServlet {
	private DBHandler dbHandler = new DBHandler();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String rmaNum = request.getParameter("rma");
		String ppidNum = request.getParameter("ppid");
		String dpsNum = request.getParameter("dps");
		List<Item> myList = new ArrayList<>();
		myList.addAll(dbHandler.fetchRMA(rmaNum,ppidNum,dpsNum));
		request.setAttribute("items", myList);
		request.getRequestDispatcher("/WEB-INF/views/physicalreceiving/rmareceiving.jsp").forward(request, response);
	}
}
