package authentication;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bizcom.database.DBHandler;

/**
 * Servlet implementation class singin
 */
@WebServlet("/signin")
public class singin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DBHandler db = new DBHandler();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public singin() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("signin called doget");
		request.getRequestDispatcher("/WEB-INF/signin/signin.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (!username.isEmpty() && !password.isEmpty()) {
			if (db.signIn(username, password)) {
				request.getSession().setMaxInactiveInterval(15);
				request.getSession().setAttribute("username", username);
				response.sendRedirect(request.getContextPath() + "/pre_alert");
			}
		} else {

			response.sendRedirect(request.getContextPath() + "/signin");
		}
	}

}
