package com.bizcom.welcome;


import java.io.IOException;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.bizcom.excel.ExcelService;

//Add Router Link to URL
@WebServlet(urlPatterns = "/home")
public class WelcomeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8462034835164475305L;
	private ExcelService excelService = new ExcelService();

	@Override

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
//		// PPID stay at Sheet 1
//		excelService.read("myfile.xls");
//
//		request.getSession().setAttribute("rows2", excelService.getListOfRowPackingSlip());
		request.getRequestDispatcher("/WEB-INF/views/welcome.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("here");
		String description = request.getParameter("description"); // Retrieves <input type="text" name="description">
	    Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
	    String fileName = extractFileName(filePart);
	    
	    System.out.println("name: "+fileName);
	    //String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
	    //InputStream fileContent = filePart.getInputStream();
		//excelService.read(request.getParameter("fileExcel"));//"C:\\Users\\viet\\Documents\\GitHub\\Packing_Slip_Project\\myfile.xls"
		response.sendRedirect("/packingslip");
		
	}
	
	 private String extractFileName(Part part) {
	        String contentDisp = part.getHeader("content-disposition");
	        String[] items = contentDisp.split(";");
	        for (String s : items) {
	            if (s.trim().startsWith("filename")) {
	                return s.substring(s.indexOf("=") + 2, s.length()-1);
	            }
	        }
	        return "";
	    }

}
