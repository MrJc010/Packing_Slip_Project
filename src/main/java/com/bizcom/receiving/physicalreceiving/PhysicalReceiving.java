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

import com.bizcom.database.DBHandler;

@WebServlet(urlPatterns = "/physicalreceiving")
public class PhysicalReceiving extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1365646760784374827L;
	private DBHandler dbhandler = new DBHandler();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String ppid = request.getParameter("ppid");
		String dps = request.getParameter("dps");

		if (!ppid.isEmpty() && !dps.isEmpty()) {
			List<Item> myList = new ArrayList<>();
			myList.addAll(dbhandler.fetchRMA(ppid, dps));

			if (myList.size() == 0) {

				response.sendRedirect(request.getContextPath() + "/searchitem");
//				PrintWriter out = response.getWriter();
//				out.println("<script type=\"text/javascript\">");
//				out.println("alert('User or password incorrect');");
//				out.println("location='/WEB-INF/views/receiving_station/physicalreceiving/searchItems.jsp';");
//				out.println("</script>");

			} else {

				for (Item it : myList) {
					System.out.println(it.toString());
				}
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
		String rmaNum = request.getParameter("rma");
		String ppid = request.getParameter("ppid");
		String dps = request.getParameter("dps");

		String url = request.getContextPath() + "/rma-receiver?rma=" + rmaNum + "&ppid=" + ppid + "&dps=" + dps;
		response.sendRedirect(url);
	}
}
