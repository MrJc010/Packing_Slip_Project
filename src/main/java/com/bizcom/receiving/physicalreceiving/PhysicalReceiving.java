package com.bizcom.receiving.physicalreceiving;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
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
	private static final long serialVersionUID = 1365646760784374827L;
	private DBHandler dbhandler = new DBHandler();
	private List<Item> myList;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if(dbhandler.checkAuthentication(request)) {
			request.getSession().setAttribute("Successfull", "");
			myList = new ArrayList<>();
			String ppid = request.getParameter("ppid");
			int count = dbhandler.getRecordCount(ppid);
			if (count >= 5) {
				response.sendRedirect(request.getContextPath() + "/morethanfive?ppid=" + ppid);
				return;
			}
			if (ppid != null) {
				myList.addAll(dbhandler.fetchRMA(ppid));
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
//				}

			} else {
				response.sendRedirect(request.getContextPath() + "/searchitem");
			}
		}else {
			response.sendRedirect(request.getContextPath() + "/signin?pagerequest=physicalreceiving");
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (myList.size() == 1) {
			
//			String rmaNum = temp.getRma();			
//			String pn = temp.getPn();
//			String description = temp.getDescription();
//			String lot = temp.getLot();
//			String problemCode = temp.getProblemCode();
//			String dps = temp.getDps();
			Item temp = myList.get(0);
			String ppid = temp.getPpid();
			String revision = request.getParameter("revision");
			String mfgPN = request.getParameter("manufactoring");
			String sn = request.getParameter("snNumber");
			String mac = request.getParameter("mac");
			String cpu_sn = request.getParameter("cpu_sn");

			boolean physical = dbhandler.PhysicalReceive(mac, ppid, sn, revision, cpu_sn, mfgPN, "userId");
			boolean updateStatus = false;
			if (physical) {
				updateStatus = dbhandler.addToStatusTable(ppid, sn, "START", "PHYSICAL_RECEIVING");
				if (!updateStatus) {
					request.getSession().setAttribute("Successfull", "Unsuccessfull");
					System.out.println("Cannot update item status");
				} else {

					request.getSession().setAttribute("Successfull", "Successfull");
					request.setAttribute("setHiddenError", "hidden");

				}
			} else {
				System.out.println("Cannot add record to physical table");

			}

			response.sendRedirect(request.getContextPath() + "/searchitem?ppid=" + ppid);


		} else {
			System.out.println("Duplicate Information");
		}

	}
}
