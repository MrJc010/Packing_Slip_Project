package com.bizcom.receiving.prealert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bizcom.database.DBHandler;
import com.bizcom.excel.ExcelService;
import com.bizcom.packingslip.PackingSlip;
import com.bizcom.ppid.PPID;
import com.bizcom.services.ExcelValidation;
import com.bizcom.services.RMAServices;

/**
 * A Java servlet that handles file upload from client.
 * 
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, maxFileSize = 1024 * 1024 * 50, maxRequestSize = 1024 * 1024
* 100)
@WebServlet(urlPatterns = "/pre_alert")
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static int PN_COL_NUM;
	private static int PO_COL_NUM;
	private static int LOT_COL_NUM;
	private static int QTY_COL_NUM;
	private static int RMA_COL;
	private ExcelService excelService = new ExcelService();
	private String setHiddenExport = "hidden";
	// location to store file uploaded
	private static final String UPLOAD_DIRECTORY = "upload";

	// upload settings
	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

	private String pathFile = "";
	private String fileName = "";
	private boolean gotNewRMA= false;
	// check douplicate reload
	private String tempPathFile = "";
	private DBHandler dbHandler = new DBHandler();

	public FileUploadServlet() throws ClassNotFoundException {
		
		super();
		// Class.forName("com.mysql.jdbc.Driver");
		System.out.println("FileUploadServlet");
	}

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(arg0, arg1);
		System.out.println("service");
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if(tempPathFile.equalsIgnoreCase(pathFile)) {
			request.setAttribute("hiddenRMA", "hidden");
		}
		request.setAttribute("setErrorHidden", "hidden");
		request.setAttribute("setSuccesHidden", "hidden");
		String action = request.getParameter("action");

		if(action!= null && action.equalsIgnoreCase("Export")) {			
			exportExcelFile(request, response, fileName, pathFile);
			return;
		}
		if(dbHandler.checkAuthentication(request)) {


			if (pathFile.length() != 0) {
				//			if (request.getSession().getAttribute("PathFile") != null) {
				hideBody(request, response, false);
				//				pathFile = request.getSession().getAttribute("PathFile").toString();
				request.setAttribute("setSuccesHidden", "show");
				request.setAttribute("messageSuccess", "Excel file was loaded successfully");				
				request.setAttribute("urll", pathFile);
				String rmaPara = request.getParameter("rmaButton");
				// excelService.read(pathFile);

				excelService.read(pathFile);

				List<PPID> list = new ArrayList<>();

				list.addAll(excelService.getListOfRowPPID());
				String isExported = "";

				PPID tempPPID = list.get(0);
				String ppidTest = tempPPID.getPpidNumber();
				String pnTest = tempPPID.getPnNumber();
				String lotTest = tempPPID.getLotNumber();
				isExported = dbHandler.isRecordPreAlertExist(ppidTest, pnTest, lotTest);

				if (rmaPara != null && isExported.isEmpty()) {

					if(!tempPathFile.equalsIgnoreCase(pathFile)) {		
						RMAServices rma = new RMAServices();
						String newRMA = rma.generatorRMA();
						try {
							dbHandler.ppidToDB(excelService.appendRMAForPPID(list, newRMA));
							setHiddenExport = "show";


						} catch (ClassNotFoundException | SQLException e) {
							e.printStackTrace();
						}

						saveRMA(pathFile, newRMA);

						if(dbHandler.createNewRMA(newRMA, "userID")) {
							request.setAttribute("hiddenRMA", "hidden");
							tempPathFile = pathFile;
							gotNewRMA = true;
						}
						try {
							dbHandler.addToPre_PPID(newRMA, list);
						} catch (ClassNotFoundException e) {

							e.printStackTrace();
						} catch (SQLException e) {
							e.printStackTrace();
						}

					}


				} else {										
					if( gotNewRMA) {
						request.setAttribute("hiddenRMA", "hidden");
						request.setAttribute("setErrorHidden", "hidden");
					}else {
//						Can't generate new RMA from database
						request.setAttribute("hiddenRMA", "show");
						request.setAttribute("setErrorHidden", "hidden");
					}
				}
				try {

					refeshPackingSlip(request, response, pathFile);
					refeshPPIDs(request, response, pathFile);
				} catch (Exception e) {
					e.printStackTrace();
				}


			} else {
				hideBody(request, response, true);
			}
			request.setAttribute("setHiddenExport", setHiddenExport);
			request.getRequestDispatcher("/WEB-INF/views/receiving_station/pre_alert/pre_alert.jsp").forward(request,
					response);
		}else {
			response.sendRedirect(request.getContextPath() + "/signin?pagerequest=pre_alert");
		}

	}

	public boolean saveRMA(String path, String rma) throws IOException {
		FileInputStream inputStream = new FileInputStream(new File(path));
		Workbook workbook = null;
		try {
			if (path.endsWith("xlsx")) {
				workbook = new XSSFWorkbook(inputStream);
			} else if (path.endsWith("xls")) {
				// HSSFWorkbook
				workbook = new HSSFWorkbook(inputStream);
			}
		} catch (Exception e) {
//			System.out.println(e.getMessage());
			return false;
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
				row.getCell(RMA_COL).setCellValue(rma);
			}

		}
		FileOutputStream outFile = new FileOutputStream(path);
		workbook.write(outFile);
		// outFile.flush();
		outFile.close();
		inputStream.close();
		workbook.close();	
		return true;

		//		excelService.read(path);
		// response.sendRedirect(request.getContextPath()+"/packingslip");
	}

	/**
	 * Upon receiving file upload submission, parses the request to read upload data
	 * and saves the file on disk.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//		 request.getSession().getAttribute("username");
		setHiddenExport = "hidden";
		// checks if the request actually contains upload file
		if (!ServletFileUpload.isMultipartContent(request)) {
			// if not, we stop here
			PrintWriter writer = response.getWriter();
			writer.println("Error: Form must has enctype=multipart/form-data.");
			writer.flush();
			return;
		}

		// configures upload settings
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// sets memory threshold - beyond which files are stored in disk
		factory.setSizeThreshold(MEMORY_THRESHOLD);
		// sets temporary location to store files
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

		ServletFileUpload upload = new ServletFileUpload(factory);

		// sets maximum size of upload file
		upload.setFileSizeMax(MAX_FILE_SIZE);

		// sets maximum size of request (include file + form data)
		upload.setSizeMax(MAX_REQUEST_SIZE);

		// constructs the directory path to store upload file
		// this path is relative to application's directory
		String uploadPath = getServletContext().getRealPath("") + UPLOAD_DIRECTORY;

//		System.out.println("uploadPath" + uploadPath);

		// creates the directory if it does not exist
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

//		System.out.println("uploadPath_2" + uploadPath);
		fileName = "";
		try {
			// parses the request's content to extract file data
			List<FileItem> formItems = upload.parseRequest(request);

			if (formItems != null && formItems.size() > 0) {
				// iterates over form's fields
				for (FileItem item : formItems) {
					// processes only fields that are not form fields
					if (!item.isFormField()) {
						fileName = new File(item.getName()).getName();
						pathFile = uploadPath + File.separator + fileName;
						File storeFile = new File(pathFile);
						//						request.getSession().setAttribute("PathFile", filePath);
						//						request.getSession().setAttribute("Path", uploadPath);
						//						request.getSession().setAttribute("Name", fileName);
						ExcelValidation validation = new ExcelValidation();
						item.write(storeFile);
						request.setAttribute("setHiddenExport", "hidden");

						int valflag = validation.prealertValidation(pathFile);
//						System.out.println("valflag: " + valflag);

//						System.out.println("pathFil 297" + pathFile);
						if (valflag == 1) {
							request.setAttribute("setErrorHidden", "hidden");
							request.setAttribute("setSuccesHidden", "show");

						} else if (valflag == 0) {
							//							request.getSession().removeAttribute("PathFile");
							pathFile= "";
							request.setAttribute("setErrorHidden", "show");
							request.setAttribute("message",
									"Excel file is not valid. Please check with manager.");

							request.setAttribute("setSuccesHidden", "hidden");
							hideBody(request, response, true);

							request.getRequestDispatcher("/WEB-INF/views/receiving_station/pre_alert/pre_alert.jsp")
							.forward(request, response);
							return;
						} else if (valflag == 2) {
							//							request.getSession().removeAttribute("PathFile");
							pathFile= "";
							request.setAttribute("setErrorHidden", "show");
							request.setAttribute("message",
									"File Excel contains value EXIST in the database");

							request.setAttribute("setSuccesHidden", "hidden");
							hideBody(request, response, true);
							request.getRequestDispatcher("/WEB-INF/views/receiving_station/pre_alert/pre_alert.jsp")
							.forward(request, response);
							return;
						} 
					}
				}
			}
		} catch (Exception ex) {
			request.setAttribute("message", "There was an error: " + ex.getMessage());
		}
		response.sendRedirect(request.getContextPath() + "/pre_alert");
	}

	public void refeshPackingSlip(HttpServletRequest request, HttpServletResponse response, String path)
			throws IOException {
		excelService.read(path);
		List<PackingSlip> packingSlips = excelService.getListOfRowPackingSlip();
		packingSlips.remove(packingSlips.size() - 1);
		request.setAttribute("rows", packingSlips);

	}

	public void refeshPPIDs(HttpServletRequest request, HttpServletResponse response, String path) throws IOException {
		request.setAttribute("rows2", excelService.getListOfRowPPID());
	}

	public void errorPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("title", "Error page");
		request.getRequestDispatcher("/WEB-INF/error/404.jsp").forward(request, response);

	}

	public void saveFile(HttpServletRequest request, HttpServletResponse response, String newRMA)
			throws ServletException, IOException {

		int PN_COL_NUM;
		int PO_COL_NUM;
		int LOT_COL_NUM;
		int QTY_COL_NUM;
		int RMA_COL = -1;
		FileInputStream inputStream = new FileInputStream(new File(pathFile));
		Workbook workbook = null;
		try {
			if (pathFile.endsWith("xlsx")) {
				workbook = new XSSFWorkbook(inputStream);
			} else if (pathFile.endsWith("xls")) {
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
		FileOutputStream outFile = new FileOutputStream(pathFile);
		workbook.write(outFile);
		outFile.flush();
		outFile.close();
		workbook.close();

	}

	public boolean isFileExist(String path) {
		return new File(path).exists();
	}

	public boolean deleteFile(String path) {
		if (isFileExist(path)) {
			return new File(path).delete();
		} else {
			System.out.println("File Does Not Exist");
			return false;
		}
	}

	public void hideBody(HttpServletRequest request, HttpServletResponse response, boolean value) {
		// true will hide
		if (value) {
			request.setAttribute("setHideInfo", "hidden");
		}
		//		false will show
		else {
			request.setAttribute("setHideInfo", "show");
		}

	}

	public void exportExcelFile(HttpServletRequest request, HttpServletResponse response, String filename, String filepath) throws IOException {
		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();  
		response.setContentType("APPLICATION/OCTET-STREAM");   
		response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\"");
		//use inline if you want to view the content in browser, helpful for pdf file
		//response.setHeader("Content-Disposition","inline; filename=\"" + filename + "\"");
		FileInputStream fileInputStream = new FileInputStream(filepath);  

		int i;   
		while ((i=fileInputStream.read()) != -1) {  
			out.write(i);   
		}

		fileInputStream.close();   
		out.close(); 
	}

}
