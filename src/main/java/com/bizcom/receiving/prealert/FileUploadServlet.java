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
	// location to store file uploaded
	private static final String UPLOAD_DIRECTORY = "upload";

	// upload settings
	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

	private String pathFile;
	private DBHandler dbHandler = new DBHandler();

	public FileUploadServlet() throws ClassNotFoundException {

		super();
		// Class.forName("com.mysql.jdbc.Driver");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		pathFile = "";
		if (request.getSession().getAttribute("PathFile") != null) {
			pathFile = request.getSession().getAttribute("PathFile").toString();
			request.setAttribute("urll", pathFile);
			String rmaPara = request.getParameter("RMA Number");
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

				System.out.println("RUN NEW RMA");

//				Multi thread = new Multi(list, rmaPara, excelService, dbHandler);
//				thread.start();

				try {
					dbHandler.ppidToDB(excelService.appendRMAForPPID(list, rmaPara));
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				saveRMA(pathFile, rmaPara);

			} else {
//				System.out.println("uploaded show uppp");
			}
			try {
				refeshPackingSlip(request, response, pathFile);
				refeshPPIDs(request, response, pathFile);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}

		}

		String page = "";
		try {
			page = request.getParameter("page").toLowerCase();
		} catch (NullPointerException e) {

		}

		switch (page) {
		case "save":
			break;
		default:
			request.getRequestDispatcher("/WEB-INF/views/receiving_station/pre_alert/pre_alert.jsp").forward(request,
					response);
		}

	}

	// TODO: Validate if file have RMA
	public void saveRMA(String path, String rma) throws IOException {
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
				row.getCell(RMA_COL).setCellValue(rma);
			}

		}
		FileOutputStream outFile = new FileOutputStream(path);
		workbook.write(outFile);
		// outFile.flush();
		outFile.close();
		inputStream.close();
		workbook.close();

//		excelService.read(path);
		// response.sendRedirect(request.getContextPath()+"/packingslip");
	}

	/**
	 * Upon receiving file upload submission, parses the request to read upload data
	 * and saves the file on disk.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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

		// creates the directory if it does not exist
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		String filePath = "";
		String fileName = "";
		try {
			// parses the request's content to extract file data
			List<FileItem> formItems = upload.parseRequest(request);

			if (formItems != null && formItems.size() > 0) {
				// iterates over form's fields
				for (FileItem item : formItems) {
					// processes only fields that are not form fields
					if (!item.isFormField()) {
						fileName = new File(item.getName()).getName();
						filePath = uploadPath + File.separator + fileName;
						File storeFile = new File(filePath);
						request.getSession().setAttribute("PathFile", filePath);
						request.getSession().setAttribute("Path", uploadPath);
						request.getSession().setAttribute("Name", fileName);
						ExcelValidation validation = new ExcelValidation();
						item.write(storeFile);
						
						if (validation.prealertValidation(filePath)) {
							System.out.println("=====test ginsfn sdf============");
						} else {
							System.out.println(" ==== erroor not match ====");
						}

					}
				}
			}
		} catch (Exception ex) {
			request.setAttribute("message", "There was an error: " + ex.getMessage());
		}
		response.sendRedirect(request.getContextPath() + "/pre_alert");

		// get the s3Client
//		AmazonS3 s3Client = new AmazonS3Client(new ProfileCredentialsProvider());
//		String uploadFilePath = uploadPath;
//		File fileSaveDir = new File(uploadFilePath);
//		if (!fileSaveDir.exists()) {
//			fileSaveDir.mkdirs();
//		}
//
//		File uploadFileName = new File(filePath);
//
//		try {
//			System.out.println("Uploading file to s3");
//			s3Client.putObject("bizcom-us-prealert", fileName, uploadFileName);
//
//		} catch (AmazonServiceException ase) {
//			System.out.println("Caught an AmazonServiceException, which " + "means your request made it "
//					+ "to Amazon S3, but was rejected with an error response" + " for some reason.");
//			System.out.println("Error Message:    " + ase.getMessage());
//			System.out.println("HTTP Status Code: " + ase.getStatusCode());
//			System.out.println("AWS Error Code:   " + ase.getErrorCode());
//			System.out.println("Error Type:       " + ase.getErrorType());
//			System.out.println("Request ID:       " + ase.getRequestId());
//		} catch (AmazonClientException ace) {
//			System.out.println("Caught an AmazonClientException, which " + "means the client encountered "
//					+ "an internal error while trying to " + "communicate with S3, "
//					+ "such as not being able to access the network.");
//			System.out.println("Error Message: " + ace.getMessage());
//		}

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

}
