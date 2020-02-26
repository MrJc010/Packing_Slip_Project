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
				session.setAttribute("re",notFound);
				response.sendRedirect(request.getContextPath() + "/searchitem");
//				PrintWriter out = response.getWriter();
//				out.println("<script type=\"text/javascript\">");
//				out.println("alert('User or password incorrect');");
//				out.println("location='/WEB-INF/views/receiving_station/physicalreceiving/searchItems.jsp';");
//				out.println("</script>");

			} else {
				notFound = "";
				session.setAttribute("re",notFound);
				for (Item it : myList) {
					System.out.println(it.toString());
				}
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
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Your information doesn't match. Enter again');");
			out.println("</script>");
			response.sendRedirect(request.getContextPath() + "/searchitem");
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		String rmaNum = request.getParameter("rma");
//		String ppid = request.getParameter("ppid");
//		String dps = request.getParameter("dps");
//
//		String url = request.getContextPath() + "/rma-receiver?rma=" + rmaNum + "&ppid=" + ppid + "&dps=" + dps;
//		response.sendRedirect(url);
		
		String 

		
		
	}
}
