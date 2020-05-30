package com.bizcom.homepage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bizcom.database.DBHandler;


@WebServlet(description = "Show Up Home page", urlPatterns = { "" })
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBHandler db = new DBHandler();
    /**
     * @throws ClassNotFoundException 
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() throws ClassNotFoundException {
        super();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getSession().getAttribute("username"));
		System.out.println(request.getSession().getAttribute("user_role"));
		System.out.println(request.getServletPath()+"  in homepage");
		if(db.checkAuthentication(request)) {
			request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);	
		}else {
			System.out.println("else in home");
			response.sendRedirect(request.getContextPath() + "/signin?pagerequest=");
		}

		
//		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
