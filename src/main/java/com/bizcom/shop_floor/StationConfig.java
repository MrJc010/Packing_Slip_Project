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

import com.bizcom.database.DBHandler;

/**
 * Servlet implementation class StationConfig
 */
@WebServlet("/shopfloor/station_config_step_3")
public class StationConfig extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String partNumberURL = "";

	// Map Handle Location Finished or Unfinished
	private Map<String, Boolean> mapLocationCheck = new HashMap<>();

	// keep user's work _ create new staion info
	private Map<String, String[]> userConfigs = new HashMap<>();

	private List<List<String>> listAvaiStations = new ArrayList<List<String>>();
	private Set<String> avaiStationsDropDown = new HashSet<>();
	private String avaiStationsDropDownSelected = "";
	private ArrayList<String> listStations = new ArrayList<>();
	private ArrayList<String> listCountOptions = new ArrayList<>();

	private DBHandler db = new DBHandler();
	private String fromLocation = "";
	private String toLocation = "";
	private String stationAvaiable = "";
	private String serialnumber = "";
	private String snPattern = "";
	private String partNumber = "";
	private String patternPN = "";
	private String comparison = "";
	private String userList = "";

	// Ref_1
	private String Ref_1Name = "";
	private String count_Ref1Pattern = "";
	private String ref1Pattern = "";
	private String maxref1Pattern = "";

	// Ref_2
	private String Ref_2Name = "";
	private String Ref_2Pattern = "";
	private String count_Ref_2Pattern = "";
	private String maxRef_2Pattern = "";

	// Ref_3
	private String Ref_3Name = "";
	private String Ref_3Pattern = "";
	private String count_Ref_3Pattern = "";
	private String maxRef_3Pattern = "";

	// Ref_4
	private String Ref_4Name = "";
	private String Ref_4Pattern = "";
	private String count_Ref_4Pattern = "";
	private String maxRef_4Pattern = "";

	// Ref_5
	private String Ref_5Name = "";
	private String Ref_5Pattern = "";
	private String count_Ref_5Pattern = "";
	private String maxRef_5Pattern = "";

	// Ref_6
	private String Ref_6Name = "";
	private String Ref_6Pattern = "";
	private String count_Ref_6Pattern = "";
	private String maxRef_6Pattern = "";

	// Ref_7
	private String Ref_7Name = "";
	private String Ref_7Pattern = "";
	private String count_Ref_7Pattern = "";
	private String maxRef_7Pattern = "";

	// Ref_8
	private String Ref_8Name = "";
	private String Ref_8Pattern = "";
	private String count_Ref_8Pattern = "";
	private String maxRef_8Pattern = "";

	// Ref_9
	private String Ref_9Name = "";
	private String Ref_9Pattern = "";
	private String count_Ref_9Pattern = "";
	private String maxRef_9Pattern = "";

	// Ref_10
	private String Ref_10Name = "";
	private String Ref_10Pattern = "";
	private String count_Ref_10Pattern = "";
	private String maxRef_10Pattern = "";

	private String action = "";

	public StationConfig() {
		listCountOptions.add("No");
		listCountOptions.add("Yes");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (partNumberURL.isEmpty()) {
			try {
				partNumberURL = request.getParameter("partNumber");
			} catch (Exception e) {

			}
			System.out.println("GET pN: " + partNumberURL);
		}
		if (!listCountOptions.isEmpty()) {
			request.setAttribute("listCountOptions", listCountOptions);
		}
		if (!partNumberURL.isEmpty()) {
			request.setAttribute("partNumberURL", partNumberURL);
//			if (listStations.size() != db.getLocationsFromPartNumber(partNumberURL).size()) {
			listStations.clear();
			List<String> tempArrays = db.getLocationsFromPartNumber(partNumberURL);
			for (String s : tempArrays) {
				s = s.toUpperCase().trim();
				String a = s.split("_")[0];
				String b = s.split("_")[1];
				listStations.add(a + "_" + b);
			}
			listStations.add("BIZ_START");
			listStations.add("DEFAULT_FAIL");

			mapLocationCheck = db.setUpConfigure(listStations);

//			}
		} else {
			System.out.println("ERORR CANNOT GET PARTNUMBER>>>");

		}

		System.out.println("partNumber from get: " + partNumberURL);
		// Set avaiable station
		if (!avaiStationsDropDown.isEmpty()) {
			request.setAttribute("avaiStationsDropDown", avaiStationsDropDown);
		}

		// Set location
		request.setAttribute("listStations", listStations);

		String action = request.getParameter("action");
		System.out.println("Action from get " + action);
		if (action != null) {
			switch (action) {
			case "Save":
				saveAction(request, response);
				System.out.println("Save action called");
				break;
			case "get_avaiable_stations":
				System.out.println("get_avaiable_stations");

				String keyStation = request.getParameter("stationAvaiable");
				System.out.println("Key: " + keyStation);
				addAllInput(request, response, keyStation);
				break;
			}
		} else {
			request.getRequestDispatcher("/WEB-INF/views/shoop_floor/StationConfig.jsp").forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doPost called");
		String action = request.getParameter("action");
		System.out.println("do post action: " + action);
		if (action != null) {
			switch (action) {
			case "Save":
				System.out.println("Save action called Posted");
				doGet(request, response);
				break;

			case "get_avaiable_stations":
				System.out.println("get_avaiable_stations called Posted");
				avaiStationsDropDownSelected = getAnInput(request, response, "stationAvaiable");
				System.out.println("detected : avaiStationsDropDownSelected = " + avaiStationsDropDownSelected);
				doGet(request, response);
				break;
			}

		}

//		partNumberURL = request.getParameter("partNumber");
//		System.out.println(toString());
		// detect save action
//		System.out.println("int insertIntoUITable's result " + db.insertIntoUITable(partNumberURL, fromLocation, toLocation, lTemp));
//		1233_From_Select Location_To_SCAN_BOX

//		request.getRequestDispatcher("/WEB-INF/views/shoop_floor/StationConfig.jsp").forward(request, response);
//		response.sendRedirect(request.getContextPath()+"/shopfloor/station_config_step_3?partNumber=" + partNumberURL);
	}

	/**
	 * Active when save button clicked Add data to avaiStationsDropDown
	 * 
	 * @param request
	 * @param response
	 * @param pn
	 * @throws IOException
	 * @throws ServletException
	 */
	public void saveAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("saveAction called");
		System.out.println("partNumberURL" + partNumberURL);
		String[] lTemp = getAllInput(request, response);
		userConfigs.put(partNumberURL + "_From_" + fromLocation + "_To_" + toLocation, lTemp);
		avaiStationsDropDown.add(partNumberURL + "_From_" + fromLocation + "_To_" + toLocation);
		request.setAttribute("avaiStationsDropDown", avaiStationsDropDown);
		System.out.println("SaveAction======User Configs Map Size: " + userConfigs.size());
//		request.getRequestDispatcher("/WEB-INF/views/shoop_floor/StationConfig.jsp").forward(request, response);

		response.sendRedirect(
				request.getContextPath() + "/shopfloor/station_config_step_3?partNumber=" + partNumberURL);
	}

	public void reloadBasedOnStationName(HttpServletRequest request, HttpServletResponse response, String stationName) {

	}

	public String[] getAllInput(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] lTemp = null;
		try {
			fromLocation = getAnInput(request, response, "fromLocation");
			toLocation = getAnInput(request, response, "toLocation");
			stationAvaiable = getAnInput(request, response, "stationAvaiable");
			serialnumber = getAnInput(request, response, "serialnumber");
			snPattern = getAnInput(request, response, "snPattern");
			partNumber = getAnInput(request, response, "partNumber");
			patternPN = getAnInput(request, response, "patternPN");

			Ref_1Name = getAnInput(request, response, "RefName_1");
			count_Ref1Pattern = getAnInput(request, response, "RefCount_1");
			ref1Pattern = getAnInput(request, response, "RefPattern_1");
			maxref1Pattern = getAnInput(request, response, "RefMax_1");

			Ref_2Name = getAnInput(request, response, "RefName_2");
			count_Ref_2Pattern = getAnInput(request, response, "RefCount_2");
			Ref_2Pattern = getAnInput(request, response, "RefPattern_2");
			maxRef_2Pattern = getAnInput(request, response, "RefMax_2");

			Ref_3Name = getAnInput(request, response, "RefName_3");
			count_Ref_3Pattern = getAnInput(request, response, "RefCount_3");
			Ref_3Pattern = getAnInput(request, response, "RefPattern_3");
			maxRef_3Pattern = getAnInput(request, response, "RefMax_3");

			Ref_4Name = getAnInput(request, response, "RefName_4");
			count_Ref_4Pattern = getAnInput(request, response, "RefCount_4");
			Ref_4Pattern = getAnInput(request, response, "RefPattern_4");
			maxRef_4Pattern = getAnInput(request, response, "RefMax_4");

			Ref_5Name = getAnInput(request, response, "RefName_5");
			count_Ref_5Pattern = getAnInput(request, response, "RefCount_5");
			Ref_5Pattern = getAnInput(request, response, "RefPattern_5");
			maxRef_5Pattern = getAnInput(request, response, "RefMax_5");

			Ref_6Name = getAnInput(request, response, "RefName_6");
			count_Ref_6Pattern = getAnInput(request, response, "RefCount_6");
			Ref_6Pattern = getAnInput(request, response, "RefPattern_6");
			maxRef_6Pattern = getAnInput(request, response, "RefMax_6");

			Ref_7Name = getAnInput(request, response, "RefName_7");
			count_Ref_7Pattern = getAnInput(request, response, "RefCount_7");
			Ref_7Pattern = getAnInput(request, response, "RefPattern_7");
			maxRef_7Pattern = getAnInput(request, response, "RefMax_7");

			Ref_8Name = getAnInput(request, response, "RefName_8");
			count_Ref_8Pattern = getAnInput(request, response, "RefCount_8");
			Ref_8Pattern = getAnInput(request, response, "RefPattern_8");
			maxRef_8Pattern = getAnInput(request, response, "RefMax_8");

			Ref_9Name = getAnInput(request, response, "RefName_9");
			count_Ref_9Pattern = getAnInput(request, response, "RefCount_9");
			Ref_9Pattern = getAnInput(request, response, "RefPattern_9");
			maxRef_9Pattern = getAnInput(request, response, "RefMax_9");

			Ref_10Name = getAnInput(request, response, "RefName_10");
			count_Ref_10Pattern = getAnInput(request, response, "RefCount_10");
			Ref_10Pattern = getAnInput(request, response, "RefPattern_10");
			maxRef_10Pattern = getAnInput(request, response, "RefMax_10");

			comparison = getAnInput(request, response, "comparison");
			userList = getAnInput(request, response, "userList");
			lTemp = new String[] { partNumber, patternPN, serialnumber, snPattern, Ref_1Name, ref1Pattern,
					count_Ref1Pattern, maxref1Pattern, Ref_2Name, Ref_2Pattern, count_Ref_2Pattern, maxRef_2Pattern,
					Ref_3Name, Ref_3Pattern, count_Ref_3Pattern, maxRef_3Pattern, Ref_4Name, Ref_4Pattern,
					count_Ref_4Pattern, maxRef_4Pattern, Ref_5Name, Ref_5Pattern, count_Ref_5Pattern, maxRef_5Pattern,
					Ref_6Name, Ref_6Pattern, count_Ref_6Pattern, maxRef_6Pattern, Ref_7Name, Ref_7Pattern,
					count_Ref_7Pattern, maxRef_7Pattern, Ref_8Name, Ref_8Pattern, count_Ref_8Pattern, maxRef_8Pattern,
					Ref_9Name, Ref_9Pattern, count_Ref_9Pattern, maxRef_9Pattern, Ref_10Name, Ref_10Pattern,
					count_Ref_10Pattern, maxRef_10Pattern, comparison.trim() };

		} catch (Exception e) {
		}
		return lTemp;
	}

	public String getAnInput(HttpServletRequest request, HttpServletResponse response, String name)
			throws ServletException, IOException {
		try {
			if (request.getParameter(name.trim()) != null) {
				return request.getParameter(name);
			}
		} catch (Exception e) {
			return "";
		}
		return "";

	}

	public void addAllInput(HttpServletRequest request, HttpServletResponse response, String keyStation)
			throws IOException, ServletException {
		// end Set location
		if (avaiStationsDropDownSelected != null && !avaiStationsDropDownSelected.isEmpty()) {
			request.setAttribute("avaiStationsDropDownSelected", avaiStationsDropDownSelected);
		}
		// avaiStationsDropDown
		if (!userConfigs.isEmpty()) {
			String[] tempInputs = userConfigs.get(keyStation);
			for (String s : tempInputs) {
				System.out.println("tempInputField " + s);
			}
			request.setAttribute("partNumber", tempInputs[0]);
			request.setAttribute("patternPN", tempInputs[1]);
			request.setAttribute("serialnumber", tempInputs[2]);
			request.setAttribute("snPattern", tempInputs[3]);

			// ref1
			request.setAttribute("RefName_1", tempInputs[4]);
			request.setAttribute("RefPattern_1", tempInputs[5]);
			request.setAttribute("RefCount_1", tempInputs[6]);
			request.setAttribute("RefMax_1", tempInputs[7]);

			// ref2
			request.setAttribute("RefName_2", tempInputs[8]);
			request.setAttribute("RefPattern_2", tempInputs[9]);
			request.setAttribute("RefCount_2", tempInputs[10]);
			request.setAttribute("RefMax_2", tempInputs[11]);

			// ref3
			request.setAttribute("RefName_3", tempInputs[12]);
			request.setAttribute("RefPattern_3", tempInputs[13]);
			request.setAttribute("RefCount_3", tempInputs[14]);
			request.setAttribute("RefMax_3", tempInputs[15]);

			// ref4
			request.setAttribute("RefName_4", tempInputs[16]);
			request.setAttribute("RefPattern_4", tempInputs[17]);
			request.setAttribute("RefCount_4", tempInputs[18]);
			request.setAttribute("RefMax_4", tempInputs[19]);

			// ref5
			request.setAttribute("RefName_5", tempInputs[20]);
			request.setAttribute("RefPattern_5", tempInputs[21]);
			request.setAttribute("RefCount_5", tempInputs[22]);
			request.setAttribute("RefMax_5", tempInputs[23]);

			// ref6
			request.setAttribute("RefName_6", tempInputs[24]);
			request.setAttribute("RefPattern_6", tempInputs[25]);
			request.setAttribute("RefCount_6", tempInputs[26]);
			request.setAttribute("RefMax_6", tempInputs[27]);

			// ref7
			request.setAttribute("RefName_7", tempInputs[28]);
			request.setAttribute("RefPattern_7", tempInputs[29]);
			request.setAttribute("RefCount_7", tempInputs[30]);
			request.setAttribute("RefMax_7", tempInputs[31]);

			// ref8
			request.setAttribute("RefName_8", tempInputs[32]);
			request.setAttribute("RefPattern_8", tempInputs[33]);
			request.setAttribute("RefCount_8", tempInputs[34]);
			request.setAttribute("RefMax_8", tempInputs[35]);

			// ref9
			request.setAttribute("RefName_9", tempInputs[36]);
			request.setAttribute("RefPattern_9", tempInputs[37]);
			request.setAttribute("RefCount_9", tempInputs[38]);
			request.setAttribute("RefMax_9", tempInputs[39]);

			// ref10
			request.setAttribute("RefName_10", tempInputs[40]);
			request.setAttribute("RefPattern_10", tempInputs[41]);
			request.setAttribute("RefCount_10", tempInputs[42]);
			request.setAttribute("RefMax_10", tempInputs[43]);
			

			// cut cut
			// jj_From_DELL_1_To_DELL_2
			String[] cutStringTemp1 = keyStation.trim().split("_From_");
			String[] temp2 = cutStringTemp1[1].split("_To_");
			request.setAttribute("fromLocationValue", temp2[0]);
			request.setAttribute("toLocationValue", temp2[1]);

//			if (fromLocation != null && !fromLocation.isEmpty()) {
//				request.setAttribute("fromLocationValue", fromLocation);
//			}
//			if (toLocation != null && !toLocation.isEmpty()) {
//				request.setAttribute("toLocationValue", toLocation);
//			}
//			

		} else {
			System.out.println("userConfigs is empty...redirect");
		}
		request.setAttribute("OptionStationAvaiableSelected", keyStation);
		request.getRequestDispatcher("/WEB-INF/views/shoop_floor/StationConfig.jsp").forward(request, response);
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		return String.format(
				"StationConfig [listStations=%s\r\n, fromLocation=%s\r\n, toLocation=%s\r\n, stationAvaiable=%s\r\n, serialnumber=%s\r\n, snPattern=%s\r\n, partNumber=%s\r\n, patternPN=%s\r\n, comparison=%s\r\n, userList=%s\r\n, Ref_1Name=%s\r\n, count_Ref1Pattern=%s\r\n, ref1Pattern=%s\r\n, maxref1Pattern=%s\r\n, Ref_2Name=%s\r\n, Ref_2Pattern=%s\r\n, count_Ref_2Pattern=%s\r\n, maxRef_2Pattern=%s\r\n, Ref_3Name=%s\r\n, Ref_3Pattern=%s\r\n, count_Ref_3Pattern=%s\r\n, maxRef_3Pattern=%s\r\n, Ref_4Name=%s\r\n, Ref_4Pattern=%s\r\n, count_Ref_4Pattern=%s\r\n, maxRef_4Pattern=%s\r\n, Ref_5Name=%s\r\n, Ref_5Pattern=%s\r\n, count_Ref_5Pattern=%s\r\n, maxRef_5Pattern=%s\r\n, Ref_6Name=%s\r\n, Ref_6Pattern=%s\r\n, count_Ref_6Pattern=%s\r\n, maxRef_6Pattern=%s\r\n, Ref_7Name=%s\r\n, Ref_7Pattern=%s\r\n, count_Ref_7Pattern=%s\r\n, maxRef_7Pattern=%s\r\n, Ref_8Name=%s\r\n, Ref_8Pattern=%s\r\n, count_Ref_8Pattern=%s\r\n, maxRef_8Pattern=%s\r\n, Ref_9Name=%s\r\n, Ref_9Pattern=%s\r\n, count_Ref_9Pattern=%s\r\n, maxRef_9Pattern=%s\r\n, Ref_10Name=%s\r\n, Ref_10Pattern=%s\r\n, count_Ref_10Pattern=%s\r\n, maxRef_10Pattern=%s\r\n]",
				listStations != null ? listStations.subList(0, Math.min(listStations.size(), maxLen)) : null,
				fromLocation, toLocation, stationAvaiable, serialnumber, snPattern, partNumber, patternPN, comparison,
				userList, Ref_1Name, count_Ref1Pattern, ref1Pattern, maxref1Pattern, Ref_2Name, Ref_2Pattern,
				count_Ref_2Pattern, maxRef_2Pattern, Ref_3Name, Ref_3Pattern, count_Ref_3Pattern, maxRef_3Pattern,
				Ref_4Name, Ref_4Pattern, count_Ref_4Pattern, maxRef_4Pattern, Ref_5Name, Ref_5Pattern,
				count_Ref_5Pattern, maxRef_5Pattern, Ref_6Name, Ref_6Pattern, count_Ref_6Pattern, maxRef_6Pattern,
				Ref_7Name, Ref_7Pattern, count_Ref_7Pattern, maxRef_7Pattern, Ref_8Name, Ref_8Pattern,
				count_Ref_8Pattern, maxRef_8Pattern, Ref_9Name, Ref_9Pattern, count_Ref_9Pattern, maxRef_9Pattern,
				Ref_10Name, Ref_10Pattern, count_Ref_10Pattern, maxRef_10Pattern);
	}

}
