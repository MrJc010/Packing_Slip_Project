package com.bizcom.shop_floor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bizcom.database.DBHandler;

/**
 * Servlet implementation class StationConfig
 */
@WebServlet("/shopfloor/station_config_step_3")
public class StationConfig extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ArrayList<String> listStations = new ArrayList<>();
    private DBHandler db = new DBHandler();
    private String fromLocation = "";
    private String toLocation = "";

	public StationConfig() {
		if(listStations.size() != db.getAllLocationTableName().size()) {
			listStations.clear();
			List<String> tempArrays = db.getAllLocationTableName();
			for(String s : tempArrays) {
				s = s.toUpperCase().trim();
				String a = s.split("_")[0];
				String b = s.split("_")[1];
				listStations.add(a+"_"+b);
			}			
		}		
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("listStations", listStations);
		
		if(fromLocation!= null && !fromLocation.isEmpty()) {
			request.setAttribute("fromLocationValue", fromLocation);
		}
		if(toLocation!= null && !toLocation.isEmpty()) {
			request.setAttribute("toLocationValue", toLocation);
		}
		request.getRequestDispatcher("/WEB-INF/views/shoop_floor/StationConfig.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost");
		fromLocation = request.getParameter("fromLocation");
		toLocation = request.getParameter("toLocation");
		doGet(request, response);
	}

}
