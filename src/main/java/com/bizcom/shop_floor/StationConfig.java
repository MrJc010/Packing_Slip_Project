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
	private String partNumberURL ="";
	
	// Map Handle Location Finished or Unfinished
	private Map<String, Boolean> mapLocationCheck = new HashMap<>();
	
	// keep user's work _ create new staion info
	private Map<String,String[]> userConfigs = new HashMap<>();
	
	private List<List<String>> listAvaiStations = new ArrayList<List<String>>();
	private Set<String> avaiStationsDropDown = new HashSet<>();
	private String avaiStationsDropDownSelected = "";
    private ArrayList<String> listStations = new ArrayList<>();
    private DBHandler db = new DBHandler();
    private String fromLocation = "";
    private String toLocation = "";
    private String stationAvaiable = "";
    private String serialnumber = "";
    private String snPattern = "";
    private String partNumber = "";
    private String patternPN = "";
    private String comparison= "";
    private String userList="";
    
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
    
    private String action="";
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
			
			mapLocationCheck = db.setUpConfigure(listStations);
		
		}
		
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(partNumberURL.isEmpty())	{
			partNumberURL = request.getParameter("partNumber");
		}
		System.out.println("partNumber from get: " + partNumberURL);
		request.setAttribute("listStations", listStations);
		if(fromLocation!= null && !fromLocation.isEmpty()) {
			request.setAttribute("fromLocationValue", fromLocation);
		}
		if(toLocation!= null && !toLocation.isEmpty()) {
			request.setAttribute("toLocationValue", toLocation);
		}
		if(avaiStationsDropDownSelected!= null && !avaiStationsDropDownSelected.isEmpty()) {
			request.setAttribute("avaiStationsDropDownSelected", avaiStationsDropDownSelected);
		}
		String action = request.getParameter("action");
		if(action!= null) {
			switch (action) {
			case "Save":
				saveAction(request, response);
				System.out.println("Save action called");
				break;

			}
		}else {
			request.getRequestDispatcher("/WEB-INF/views/shoop_floor/StationConfig.jsp").forward(request, response);
		}	
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost called");
		String action = request.getParameter("action");
		System.out.println("do post action: " + action);
		if(action!= null) {
			switch (action) {
			case "Save":
				System.out.println("Save action called Posted");
				doGet(request, response);
				break;
			}
		}else {
			doGet(request, response);
		}	
		
//		partNumberURL = request.getParameter("partNumber");
//		System.out.println(toString());
		// detect save action
//		System.out.println("int insertIntoUITable's result " + db.insertIntoUITable(partNumberURL, fromLocation, toLocation, lTemp));
//		1233_From_Select Location_To_SCAN_BOX
		
		
		doGet(request, response);
//		request.getRequestDispatcher("/WEB-INF/views/shoop_floor/StationConfig.jsp").forward(request, response);
//		response.sendRedirect(request.getContextPath()+"/shopfloor/station_config_step_3?partNumber=" + partNumberURL);
	}
	
	/**
	 * Active when save button clicked
	 * Add data to avaiStationsDropDown
	 * @param request
	 * @param response
	 * @param pn
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void saveAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("saveAction called");
		System.out.println("partNumberURL" + partNumberURL);
		String[] lTemp = getAllInput(request, response);
		userConfigs.put(partNumberURL+"_From_"+fromLocation+"_To_"+toLocation, lTemp);
		avaiStationsDropDown.add(partNumberURL+"_From_"+fromLocation+"_To_"+toLocation);
		request.setAttribute("avaiStationsDropDown", avaiStationsDropDown);
		System.out.println("SaveAction======User Configs Map Size: " + userConfigs.size());
		request.getRequestDispatcher("/WEB-INF/views/shoop_floor/StationConfig.jsp").forward(request, response);
//		response.sendRedirect(request.getContextPath()+"/shopfloor/station_config_step_3?partNumber=" + partNumberURL);
	}
	
	
	
	public String[] getAllInput(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] lTemp = null;
		try {
			fromLocation = getAnInput(request,response,"fromLocation");
			toLocation= getAnInput(request,response,"toLocation");
			stationAvaiable= getAnInput(request,response,"stationAvaiable");
			serialnumber= getAnInput(request,response,"serialnumber");
			snPattern= getAnInput(request,response,"snPattern");
			partNumber= getAnInput(request,response,"partNumber");
			patternPN= getAnInput(request,response,"patternPN");
						
			Ref_1Name= getAnInput(request,response,"Ref_1Name");
			count_Ref1Pattern= getAnInput(request,response,"count_Ref1Pattern");
			ref1Pattern= getAnInput(request,response,"ref1Pattern");
			maxref1Pattern= getAnInput(request,response,"maxref1Pattern");
			
			Ref_2Name= getAnInput(request,response,"Ref_2Name");
			count_Ref_2Pattern= getAnInput(request,response,"count_Ref_2Pattern");
			Ref_2Pattern= getAnInput(request,response,"Ref_2Pattern");
			maxRef_2Pattern= getAnInput(request,response,"maxRef_2Pattern");
			
			Ref_3Name= getAnInput(request,response,"Ref_3Name");
			count_Ref_3Pattern= getAnInput(request,response,"count_Ref_3Pattern");
			Ref_3Pattern= getAnInput(request,response,"Ref_3Pattern");
			maxRef_3Pattern= getAnInput(request,response,"maxRef_3Pattern");
			
			Ref_4Name= getAnInput(request,response,"Ref_4Name");
			count_Ref_4Pattern= getAnInput(request,response,"count_Ref_4Pattern");
			Ref_4Pattern= getAnInput(request,response,"Ref_4Pattern");
			maxRef_4Pattern= getAnInput(request,response,"maxRef_4Pattern");
			
			Ref_5Name= getAnInput(request,response,"Ref_5Name");
			count_Ref_5Pattern= getAnInput(request,response,"count_Ref_5Pattern");
			Ref_5Pattern= getAnInput(request,response,"Ref_5Pattern");
			maxRef_5Pattern= getAnInput(request,response,"maxRef_5Pattern");
			
			Ref_6Name= getAnInput(request,response,"Ref_6Name");
			count_Ref_6Pattern= getAnInput(request,response,"count_Ref_6Pattern");
			Ref_6Pattern= getAnInput(request,response,"Ref_6Pattern");
			maxRef_6Pattern= getAnInput(request,response,"maxRef_6Pattern");
			
			Ref_7Name= getAnInput(request,response,"Ref_7Name");
			count_Ref_7Pattern= getAnInput(request,response,"count_Ref_7Pattern");
			Ref_7Pattern= getAnInput(request,response,"Ref_7Pattern");
			maxRef_7Pattern= getAnInput(request,response,"maxRef_7Pattern");
			
			Ref_8Name= getAnInput(request,response,"Ref_8Name");
			count_Ref_8Pattern= getAnInput(request,response,"count_Ref_8Pattern");
			Ref_8Pattern= getAnInput(request,response,"Ref_8Pattern");
			maxRef_8Pattern= getAnInput(request,response,"maxRef_8Pattern");
			
			Ref_9Name= getAnInput(request,response,"Ref_9Name");
			count_Ref_9Pattern= getAnInput(request,response,"count_Ref_9Pattern");
			Ref_9Pattern= getAnInput(request,response,"Ref_9Pattern");
			maxRef_9Pattern= getAnInput(request,response,"maxRef_9Pattern");
			
			Ref_10Name= getAnInput(request,response,"Ref_10Name");
			count_Ref_10Pattern= getAnInput(request,response,"count_Ref_10Pattern");
			Ref_10Pattern= getAnInput(request,response,"Ref_10Pattern");
			maxRef_10Pattern= getAnInput(request,response,"maxRef_10Pattern");
			
			comparison= getAnInput(request, response, "comparison");
			userList= getAnInput(request, response, "userList");
			lTemp = new String[] {
					partNumber,patternPN,serialnumber,snPattern,
					Ref_1Name,ref1Pattern,count_Ref1Pattern,maxref1Pattern,
					Ref_2Name,Ref_2Pattern,count_Ref_2Pattern,maxRef_2Pattern,
					Ref_3Name,Ref_3Pattern,count_Ref_3Pattern,maxRef_3Pattern,
					Ref_4Name,Ref_4Pattern,count_Ref_4Pattern,maxRef_4Pattern,
					Ref_5Name,Ref_5Pattern,count_Ref_5Pattern,maxRef_5Pattern,
					Ref_6Name,Ref_6Pattern,count_Ref_6Pattern,maxRef_6Pattern,
					Ref_7Name,Ref_7Pattern,count_Ref_7Pattern,maxRef_7Pattern,
					Ref_8Name,Ref_8Pattern,count_Ref_8Pattern,maxRef_8Pattern,
					Ref_9Name,Ref_9Pattern,count_Ref_9Pattern,maxRef_9Pattern,
					Ref_10Name,Ref_10Pattern,count_Ref_10Pattern,maxRef_10Pattern,
					comparison.trim()
			};
			
		}catch(Exception e) {
		}
		return lTemp;
	}
	
	public String getAnInput(HttpServletRequest request, HttpServletResponse response, String name) throws ServletException, IOException {
		try {
			if(request.getParameter(name.trim()) != null) {
				return request.getParameter(name);
			}
		}catch(Exception e) {
			return "";	
		}
		return "";
		
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
