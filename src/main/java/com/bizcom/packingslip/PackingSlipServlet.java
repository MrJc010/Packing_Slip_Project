package com.bizcom.packingslip;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bizcom.excel.ExcelService;

//Add Router Link to URL
@WebServlet(urlPatterns = "/packingslip")
public class PackingSlipServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 15641656;
	private ExcelService excelService = new ExcelService();

	@Override

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// PackingSlip stay at Sheet 0
		excelService.read("myfile.xls");
//		excelService.writeNewFile("Testing", true);
		System.out.println("Row # : " + excelService.getListOfRowPackingSlip().size());
		request.setAttribute("rows", excelService.getListOfRowPackingSlip());
		request.getRequestDispatcher("/WEB-INF/views/packingslip/list-packingSlip.jsp").forward(request, response);
	}
}