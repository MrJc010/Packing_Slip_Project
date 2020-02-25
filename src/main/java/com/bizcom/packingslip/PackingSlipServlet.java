package com.bizcom.packingslip;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bizcom.excel.ExcelService;

/**
 * This object is used to read file excel and upload this file's content to website.
 * @author viet
 *
 */
//Add Router Link to URL
@WebServlet(urlPatterns = "/packing_slip")
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
		
//		excelService.writeNewFile("Testing", true);
//		System.out.println("Row # : " + excelService.getListOfRowPackingSlip().size());
//		request.setAttribute("rows", excelService.getListOfRowPackingSlip());
	
		excelService.read(request.getSession().getAttribute("PathFile").toString());
		//System.out.println(request.getSession().getAttribute("PathFile").toString());
		request.setAttribute("rows", excelService.getListOfRowPackingSlip());
		request.getRequestDispatcher("/WEB-INF/views/receiving_station/packingslip/list-packingSlip.jsp").forward(request, response);
	}
}