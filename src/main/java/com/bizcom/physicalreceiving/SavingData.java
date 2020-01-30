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

@WebServlet(urlPatterns = "/savingdata")
public class SavingData extends HttpServlet {

	private DBHandler dbHandler = new DBHandler();

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
		String pn = request.getParameter("pn");

		String sn = request.getParameter("sn");
		String revision = request.getParameter("revision");
		String specialInstruction = request.getParameter("specialInstruction");

		String mfgPN = request.getParameter("mfgPN");
		String co = request.getParameter("co");
		String lot = request.getParameter("lot");

		String problemCode = request.getParameter("problemCode");
		String description = request.getParameter("description");
		String dps = request.getParameter("dps");

		String cpoSN = request.getParameter("CPO_SN");
//		String url = "/rma-receiver?rma=" + rmaNum + "&ppid=" + ppid+"&pn=" + pn+"&sn=" + sn + "&revision=" + revision+"&specialInstruction=" + specialInstruction + 
//				"&mfgPN=" + mfgPN + "&co=" + co+"&lot=" + lot+"&problemCode=" + problemCode + "&description=" + description+"&dps=" + dps;
		dbHandler.SavingRMA(rmaNum, cpoSN, ppid, pn, sn, revision, specialInstruction, mfgPN, lot, description,
				problemCode, dps);
		response.sendRedirect("/physicalreceiving");

	}
}
