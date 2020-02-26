//package com.bizcom.ppid;
//
//import javax.servlet.annotation.WebServlet;
//import java.io.IOException;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.bizcom.database.DBHandler;
//import com.bizcom.excel.ExcelService;
//
////Add Router Link to URL
//@WebServlet(urlPatterns = "/listppid")
//public class PPIDServlet extends HttpServlet {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -8462034835164475305L;
//	private ExcelService excelService = new ExcelService();
//	private DBHandler dbHandler = new DBHandler();
//
//	@Override
//
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws IOException, ServletException {
//		// PPID stay at Sheet 1
//		excelService.read(request.getSession().getAttribute("PathFile").toString());
//		request.setAttribute("rows2", excelService.getListOfRowPPID());
//		dbHandler.ppidToDB(excelService.getListOfRowPPID());
//		request.getRequestDispatcher("/WEB-INF/views/receiving_station/ppid/list-ppid.jsp").forward(request, response);
//	}
//
//}
