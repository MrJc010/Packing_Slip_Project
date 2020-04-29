package com.bizcom.authentication;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.bizcom.database.DBHandler;


/**
 * Servlet implementation class SignUp
 */
@WebServlet("/signup")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String manageCode= "08950c6b1295b314a272e70c2efdf3174a065ae65f4e0eefcb0e572fe0f881b3dd99014e04d377587ac07195c1a5d93cd85458e6d406ecd200d10a7f43591a2a";
	private final static String salt = "bqlpbbjiuazmka";
	private DBHandler db = new DBHandler();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("db", db);
		request.getRequestDispatcher("/WEB-INF/signup/signup.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String roles = request.getParameter("roles");
		if(roles != null && roles.equalsIgnoreCase("employee")) {		
			String firstName = request.getParameter("firstName2");
			String lastName = request.getParameter("lastName2");
			String employeeID = request.getParameter("employeeID2");
			String password1 = request.getParameter("password2");	
			db.signUp(employeeID, password1, "Employee", firstName, lastName);
		}
		
		else if(roles != null && roles.equalsIgnoreCase("manager")) {
			String manageCode = request.getParameter("manageCode1");
			String firstName = request.getParameter("firstName1");
			String lastName = request.getParameter("lastName1");
			String employeeID = request.getParameter("employeeID1");
			String password1 = request.getParameter("password1");						
			if(getHashFromString(manageCode)) {
				if(db.signUp(employeeID, password1, "Manager", firstName, lastName)) {
					//If Create user-account Successfull
					
				}else {
					//Else Fail to create account
				}
			}else {				
				HashMap<String,String> data = new HashMap<>();
				data.put("firstName1", firstName);
				data.put("lastName1", lastName);
				data.put("employeeID1", employeeID);
				data.put("password1", password1);
				 JSONObject jsonMap= new JSONObject(data);
				request.setAttribute("jsonMap", jsonMap);
				doGet(request,response);
			}			
			
		}
		
		else {
			System.out.println("Some Thing other roles");
		}
	}

	public boolean getHashFromString(String codeString) {
		
		boolean result = false;
		if(!codeString.isEmpty()) {
			
		 if(db.checkPassword(manageCode,codeString,salt.getBytes())) {
				result = true;
			}
		}
		return result;
	}
}
