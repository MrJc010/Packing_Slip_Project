package com.bizcom.shop_floor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.amazonaws.Request;
import com.bizcom.MICI_Station.ErrorCode;
import com.bizcom.database.DBHandler;

/**
 * Servlet implementation class Create_New_Locations
 */
@WebServlet("/shopfloor/create_new_partnumber_step2")
public class Create_Part_Number_Step02 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String partNumber = "";
	private String model = "";
	private String desc = "";
	private DBHandler db = new DBHandler();
	private List<String> locationList;
	private JSONObject jsonMap;
	private Map<String, Object> mapLocationList = new HashMap<>();

	public Create_Part_Number_Step02() {		
		try {
			db.getConnectionAWS();
			db.getConnectionShopFloor();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		locationList = db.getLocationName();
		for (int i = 0; i < locationList.size(); i++) {
			mapLocationList.put(i + 1 + "", (Object) locationList.get(i));
		}
		jsonMap = new JSONObject(mapLocationList);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("create_new_locations called");
		partNumber = request.getParameter("pn");
		model = request.getParameter("model");
		desc = request.getParameter("desc");
		request.setAttribute("partNumber", partNumber);
		request.setAttribute("model", model);
		request.setAttribute("desc", desc);
		request.setAttribute("locationList", jsonMap);
		request.setAttribute("setHiddenSuccess", "hidden");

		
		request.getRequestDispatcher("/WEB-INF/views/shoop_floor/create_new_part_number_step_2.jsp").forward(request,
				response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// drop down
		System.out.println("Call post step 2");
		String[] listItems = request.getParameterValues("itemStations");
		Set<String> locations = new HashSet<>();
		List<String> currentLocations = db.getLocationName();
		for (String s : listItems) {
			if (!locations.contains(s)) {
				locations.add(s);
				if (!currentLocations.contains(s)) {
					db.createNewLocationTable(s);
				}
			}
		}

		
		// listItems

//		doGet(request, response);
		response.sendRedirect(request.getContextPath()+"/shopfloor/station_config_step_3?partNumber=" + partNumber);
	}

}
