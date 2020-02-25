package com.bizcom.receiving.prealert;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Servlet implementation class guru_download
 */
@WebServlet("/File_DownLoad")
public class File_DownLoad extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//		String SAMPLE_XLSX_FILE_PATH = request.getSession().getAttribute("PathFile").toString();
		//		System.out.println(SAMPLE_XLSX_FILE_PATH+"here");
		//		FileInputStream inputStream = new FileInputStream(SAMPLE_XLSX_FILE_PATH);
		//		Workbook workbook = null;
		//		try{
		//			if (SAMPLE_XLSX_FILE_PATH.endsWith("xlsx")) {
		//				workbook = new XSSFWorkbook(inputStream);
		//			} else if (SAMPLE_XLSX_FILE_PATH.endsWith("xls")) {
		//			//HSSFWorkbook
		//				workbook = new HSSFWorkbook(inputStream);
		//			}
		//		}catch(Exception e) {
		//			System.out.println("Cannot read the file!");
		//		}
		//		
		//		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		//		workbook.write(outByteStream);
		//		
		//		
		//		byte [] outArray = outByteStream.toByteArray();
		//		response.setContentType("application/ms-excel");
		//		response.setContentLength(outArray.length); 
		//		response.setHeader("Expires:", "0"); // eliminates browser caching
		//		response.setHeader("Content-Disposition", "attachment; filename=Leave Details.xls");
		//		OutputStream outStream = response.getOutputStream();
		//		outStream.write(outArray);
		//		outStream.flush();

		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();  
		String filename = request.getSession().getAttribute("Name").toString();   
		String filepath = request.getSession().getAttribute("Path").toString()+"\\"; 
		System.out.println(filename+" "+filepath);
		response.setContentType("APPLICATION/OCTET-STREAM");   
		response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\"");

		//use inline if you want to view the content in browser, helpful for pdf file
		//response.setHeader("Content-Disposition","inline; filename=\"" + filename + "\"");
		FileInputStream fileInputStream = new FileInputStream(filepath + filename);  

		int i;   
		while ((i=fileInputStream.read()) != -1) {  
			out.write(i);   
		}   
		fileInputStream.close();   
		out.close();   
	} 




/**
 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
 */
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
}

}