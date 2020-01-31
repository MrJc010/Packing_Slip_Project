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

@WebServlet(urlPatterns = "/physicalreceiving")
public class PhysicalReceiving extends HttpServlet {
	

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/physicalreceiving/physicalreceiving.jsp").forward(request,
				response);
//		String rmaNum = request.getParameter("rma");
//
//		System.out.println("i am here RMA : " + rmaNum);
//		List<Item> myList = new ArrayList<>();
//		myList.addAll(dbHandler.fetchRMA(rmaNum));
//		System.out.println("SIZE LIST " + myList.size());
//		String url = "/rma-receiver?rma="+rmaNum;
//		request.getRequestDispatcher("/rma-receiver");

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String rmaNum = request.getParameter("rma");
		String ppid = request.getParameter("ppid");
		String dps = request.getParameter("dps");

		String url = "/rma-receiver?rma=" + rmaNum + "&ppid=" + ppid+"&dps=" + dps;
		response.sendRedirect(url);
	}
}
