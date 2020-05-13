package com.bizcom.qc1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.enterprise.inject.ResolutionException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bizcom.authentication.UrlPatternUtils;
import com.bizcom.database.DBHandler;



/**
 * Servlet implementation class QC1_Servlet
 */
@WebServlet("/qc1")
public class QC1_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBHandler db = new DBHandler();

	public QC1_Servlet() {
		super();
	}

	/**
	 * @throws IOException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * "/WEB-INF/views/qc1/qc1.jsp"
	 */
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		

		if(db.checkAuthentication(request)) {
			request.getRequestDispatcher("/WEB-INF/views/qc1/qc1.jsp").forward(request, response);
		}else {
		
			response.sendRedirect(request.getContextPath() + "/signin?pagerequest=qc1");
		}
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	
	public static boolean service() throws IOException  {
	    File file = new File("C:\\Users\\viet\\Desktop\\name.txt");
	    try {
	        Scanner scanner = new Scanner(file);
	        //now read the file line by line...
	        int lineNum = 0;
	        while (scanner.hasNextLine()) {
	            String str = scanner.nextLine();
	            System.out.println(str);
	            lineNum++;
	            if(str.toUpperCase().contains("PASS") || str.toUpperCase().contains("PASSED")) { 
	                return true;
	            }
	        }
	    } catch(FileNotFoundException e) { 
	        System.out.println(e);
	        return false;
	    }
	    return false;
	  }
}
