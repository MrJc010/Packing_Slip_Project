package com.bizcom.receiving.prealert;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.bizcom.excel.ExcelService;
import com.bizcom.packingslip.PackingSlip;

/**
 * A Java servlet that handles file upload from client.
 * 
 */
@WebServlet(urlPatterns = "/pre_alert")
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ExcelService excelService = new ExcelService();
	// location to store file uploaded
	private static final String UPLOAD_DIRECTORY = "upload";

	// upload settings
	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

	@Override

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.setAttribute("setHidden", "hidden");
		String page = "";
		try {
			page = request.getParameter("page").toLowerCase();
		} catch (NullPointerException e) {
			System.out.println("Null detected");
		}

		switch (page) {
		case "export":
			System.out.println("export to file and download");
			upload_file(request, response);
			break;
		default:
			request.getRequestDispatcher("/WEB-INF/views/receiving_station/pre_alert/pre_alert.jsp").forward(request,
					response);
		}
		
		

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
		String filePath ="";
		try {
			// parses the request's content to extract file data
			@SuppressWarnings("unchecked")
			List<FileItem> formItems = upload.parseRequest(request);

			if (formItems != null && formItems.size() > 0) {
				// iterates over form's fields
				for (FileItem item : formItems) {
					// processes only fields that are not form fields
					if (!item.isFormField()) {
						String fileName = new File(item.getName()).getName();
						filePath = uploadPath + File.separator + fileName;
						File storeFile = new File(filePath);
						request.getSession().setAttribute("PathFile", filePath);
						request.getSession().setAttribute("Path", uploadPath);
						request.getSession().setAttribute("Name", fileName);
						// saves the file on disk
						item.write(storeFile);
//						response.sendRedirect(request.getContextPath() + "/packing_slip");

					}
				}
			}
		} catch (Exception ex) {
			request.setAttribute("message", "There was an error: " + ex.getMessage());
		}
//		request.setAttribute("newValue", "NEWWWW");
//		System.out.println("POST DONE: "  + filePath);
//		// show PACKING SLIP
//	
//		excelService.read(filePath);
//		response.sendRedirect(request.getContextPath() + "/pre_alert");
//		request.setAttribute("rows", excelService.getListOfRowPackingSlip());
	}

	public void refeshPackingSlip(HttpServletRequest request, HttpServletResponse response,String path) throws IOException {
		excelService.read(path);
		request.setAttribute("newValue", "NEWWWW");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for( PackingSlip pl : excelService.getListOfRowPackingSlip() ) {
			System.out.println(pl.toString());
		}
		request.setAttribute("rows", excelService.getListOfRowPackingSlip());
		
	}
	public void errorPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("title", "Error page");
		request.getRequestDispatcher("/WEB-INF/error/error.jsp").forward(request, response);

	}

	public void upload_file(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("run any thing in here ok");

	}
}