package com.bizcom.qc1;

import java.io.IOException;

import javax.enterprise.inject.ResolutionException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bizcom.authentication.UrlPatternUtils;



/**
 * Servlet implementation class QC1_Servlet
 */
@WebServlet("/qc1")
public class QC1_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public QC1_Servlet() {
		super();
	}

	/**
	 * @throws IOException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * "/WEB-INF/views/qc1/qc1.jsp"
	 */
	
	public boolean checkAuthentication(HttpServletRequest request, HttpServletResponse response,String patternURL) throws IOException {
		StringBuilder stB = new StringBuilder(UrlPatternUtils.getUrlPattern(request));
		stB.deleteCharAt(0);
		String urlPattern = stB.toString();		
		try {
			String userName = request.getSession().getAttribute("username").toString();
			String roles = request.getSession().getAttribute("user_role").toString();

			if(userName != null ) {		
				String[] roleArray = roles.split(";");
				for(int i=0; i< roleArray.length; i++) {
					System.out.println("Role: >> "  +roleArray[i]);
					if(roleArray[i].equalsIgnoreCase(urlPattern)) {
						request.getRequestDispatcher("/WEB-INF/views/"+patternURL+".jsp").forward(request, response);
						return true;
					}
				}
				response.sendRedirect(request.getContextPath() + "/signin");
			}	
		}catch(Exception e) {
			System.out.println(e.getMessage());	
			response.sendRedirect(request.getContextPath() + "/signin");
		}
		return false;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		

		if(checkAuthentication(request, response, "qc1/qc1")) {
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
