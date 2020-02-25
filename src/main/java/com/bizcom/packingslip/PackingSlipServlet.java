package com.bizcom.packingslip;

import javax.servlet.annotation.WebServlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bizcom.excel.ExcelService;

/**
 * This object is used to read file excel and upload this file's content to
 * website.
 * 
 * @author viet
 *
 */
//Add Router Link to URL
@WebServlet(urlPatterns = "/packing_slip")
public class PackingSlipServlet extends HttpServlet {
	private static int PN_COL_NUM;
	private static int PO_COL_NUM;
	private static int LOT_COL_NUM;
	private static int QTY_COL_NUM;
	private static int RMA_COL;
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
		// System.out.println(request.getSession().getAttribute("PathFile").toString());
		request.setAttribute("rows", excelService.getListOfRowPackingSlip());
		request.getRequestDispatcher("/WEB-INF/views/receiving_station/packingslip/list-packingSlip.jsp")
				.forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String newRMA = "";
		if (request.getParameter("_RMA") != null) {
			newRMA = request.getParameter("_RMA");
		}

		saveFile(request, response, newRMA);
		response.sendRedirect(request.getContextPath()+"/packing_slip");

	}

	public void saveFile(HttpServletRequest request, HttpServletResponse response, String newRMA) {
		ExcelService excel = new ExcelService();
		String path = request.getSession().getAttribute("PathFile").toString();
		System.out.println(path);

		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(path));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Workbook workbook = null;
		try {
			if (path.endsWith("xlsx")) {
				workbook = new XSSFWorkbook(inputStream);
			} else if (path.endsWith("xls")) {
				// HSSFWorkbook
				workbook = new HSSFWorkbook(inputStream);
			}
		} catch (Exception e) {
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
					} else if (temp.equalsIgnoreCase("QTY")) {
						QTY_COL_NUM = index;
						count++;
					} else if (temp.equalsIgnoreCase("RMA#")) {
						RMA_COL = index;
					}
					index++;
				}
			} else {
				row.getCell(RMA_COL).setCellValue(newRMA);
			}

		}
		FileOutputStream outFile = null;
		try {
			outFile = new FileOutputStream(path);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			workbook.write(outFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			outFile.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			outFile.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}