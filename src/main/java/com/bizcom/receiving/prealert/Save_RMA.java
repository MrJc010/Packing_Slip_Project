package com.bizcom.receiving.prealert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bizcom.excel.ExcelService;
import com.bizcom.packingslip.PackingSlip;

/**
 * Servlet implementation class guru_download
 */
public class Save_RMA extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static int PN_COL_NUM;
	private static int PO_COL_NUM;
	private static int LOT_COL_NUM;
	private static int QTY_COL_NUM;
	private static int RMA_COL;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ExcelService excel = new ExcelService();
		String path = request.getSession().getAttribute("PathFile").toString();
		System.out.println(path);
		
		FileInputStream inputStream = new FileInputStream(new File(path));
		Workbook workbook = null;
		try{
			if (path.endsWith("xlsx")) {
				workbook = new XSSFWorkbook(inputStream);
			} else if (path.endsWith("xls")) {
			//HSSFWorkbook
				workbook = new HSSFWorkbook(inputStream);
			}
		}catch(Exception e) {
			System.out.println("Cannot Read The File!");
		}
		
		Sheet sheetOne = workbook.getSheetAt(0);
		DataFormatter formatter = new DataFormatter();
		int count = 0;
		for (Row row : sheetOne) {
			if (count < 1) {
				int index = 1;
				for (Cell c : row) {
					String temp = formatter.formatCellValue(c);
					if (temp.equalsIgnoreCase("PN")) {
						PN_COL_NUM = index;
					} else if (temp.equalsIgnoreCase("PO#")) {
						PO_COL_NUM = index;

					} else if (temp.equalsIgnoreCase("LOT#")) {
						LOT_COL_NUM = index;
					}else if (temp.equalsIgnoreCase("QTY")) {
						QTY_COL_NUM = index;
						count++;
					} else if (temp.equalsIgnoreCase("RMA#")) {
						RMA_COL = index;
					}
						index++;
				}
			} else {	
				row.getCell(RMA_COL).setCellValue(request.getParameter("_RMA"));			
			}

		}
		FileOutputStream outFile = new FileOutputStream(path);
		workbook.write(outFile);
		//outFile.flush();
		outFile.close();
		inputStream.close();
		workbook.close();
		response.sendRedirect(request.getContextPath()+"/packingslip");
	} 




/**
 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
 */
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
}

}