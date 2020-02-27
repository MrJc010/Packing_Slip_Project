package com.bizcom.receiving.physicalreceiving;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bizcom.database.DBHandler;

@WebServlet(urlPatterns = "/physicalreceiving")
public class PhysicalReceiving extends HttpServlet {

	/**
	 * 
	 */
	private String notFound = "";
	private static final long serialVersionUID = 1365646760784374827L;
	private DBHandler dbhandler = new DBHandler();
	private List<Item> myList;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		myList = new ArrayList<>();
		String ppid = request.getParameter("ppid");
		String dps = request.getParameter("dps");

		HttpSession session = request.getSession();
		if (!ppid.isEmpty() && !dps.isEmpty()) {

			myList.addAll(dbhandler.fetchRMA(ppid, dps));

			if (myList.size() == 0) {
				notFound = "Cannot Find Item With Your Info!";
				session.setAttribute("re", notFound);
				response.sendRedirect(request.getContextPath() + "/searchitem");
			} else {
				notFound = "";
				session.setAttribute("re", notFound);
				Item temp = myList.get(0);
				String rma = temp.getRma();
				String ppid2 = temp.getPpid();
				String pn = temp.getPn();
				String co = temp.getCo();
				String problemDescription = temp.getDescription();
				String lotNum = temp.getLot();
				String problemCode = temp.getProblemCode();
				String dpsNumer = temp.getDps();

				request.setAttribute("rma_Number", rma);
				request.setAttribute("ppid_Number", ppid2);
				request.setAttribute("pn_Number", pn);
				request.setAttribute("co_Number", co);
				request.setAttribute("problem_desc", problemDescription);
				request.setAttribute("lot", lotNum);
				request.setAttribute("problem_code", problemCode);
				request.setAttribute("dps", dpsNumer);
				request.setAttribute("title", "Search Item");

				request.getRequestDispatcher("/WEB-INF/views/receiving_station/physicalreceiving/physicalreceiving.jsp")
						.forward(request, response);
			}

		} else {
			response.sendRedirect(request.getContextPath() + "/searchitem");
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (myList.size() == 1) {

			Item temp = myList.get(0);
			String rmaNum = temp.getRma();
			String ppid = temp.getPpid();
			String pn = temp.getPn();

			String description = temp.getDescription();
			String lot = temp.getLot();
			String problemCode = temp.getProblemCode();
			String dps = temp.getDps();
			String cposn = request.getParameter("cpoNumber");
			String revision = request.getParameter("revision");
			String specialInstruction = request.getParameter("specialInstruction");
			String mfgPcN = request.getParameter("manufactoring");
			String sn = request.getParameter("snNumber");

			dbhandler.PhysicalReceive(rmaNum, cposn, ppid, pn, sn, revision, specialInstruction, mfgPcN, lot,
					description, problemCode, dps);

			response.sendRedirect(request.getContextPath() + "/searchitem");

		} else {
			System.out.println("Duplicate Information");
		}

	}
}
