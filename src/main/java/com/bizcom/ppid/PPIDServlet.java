package com.bizcom.ppid;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bizcom.excel.ExcelService;

//Add Router Link to URL
@WebServlet(urlPatterns = "/list-ppid.do")
public class PPIDServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8462034835164475305L;
	private ExcelService excelService = new ExcelService();

	@Override

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// PPID stay at Sheet 1
		excelService.read("myfile.xls");

		request.getSession().setAttribute("rows2", excelService.getListOfRowPackingSlip());
		request.getRequestDispatcher("/WEB-INF/views/ppid/list-ppid.jsp").forward(request, response);
	}

}
