package com.bizcom.qc1;

import java.io.IOException;

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

		if(db.checkAuthentication(request, response, "qc1/qc1")) {
			System.out.println("IF soemthing");
		}else {
			System.out.println("ELE soemthing");
		}
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
