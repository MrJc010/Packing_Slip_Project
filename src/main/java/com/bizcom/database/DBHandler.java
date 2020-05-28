package com.bizcom.database;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.json.JSONObject;

import com.bizcom.MICI_Station.ErrorCode;
import com.bizcom.authentication.UrlPatternUtils;
import com.bizcom.ppid.PPID;
import com.bizcom.receiving.physicalreceiving.Item;
import com.bizcom.receiving.physicalreceiving.PreAlertItem;
import com.bizcom.repair01.RevesionUpgrade;
import com.google.gson.Gson;

public class DBHandler {
	private Connection dbconnection;
	private Connection sfconnection;
	private PreparedStatement pst;
	private PreparedStatement pstSF;
	private ResultSet rs;
	private ResultSet rsSF;
	private static final DateTimeFormatter sdf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss.SSS");
	private static final SimpleDateFormat dateForSearch = new SimpleDateFormat("MM/dd/yyyy");
	public static final String PHYSICAL_RECEIVING = "PHYSICAL_RECEIVING";
	public static final String MICI = "MICI";
	public static final String REPAIR01_FAIL = "REPAIR01_FAIL";
	public static final String REPAIR01_PASS = "REPAIR01_PASS";
	public static final String REPAIR01 = "REPAIR01";
	public static final String QC1 = "QC1";
	public static final String QC1_WAITING = "QC1_WAITING";
	public static final String QC2 = "QC2";
	public static final String QC2_WAITING = "QC2_WAITING";
	public static final String QC3 = "QC3";
	public static final String QC3_WAITING = "QC3_WAITING";
	public static final String QC4 = "QC4";
	public static final String QC4_WAITING = "QC4_WAITING";
	public static final String START = "START";
	public static final String ECO = "ECO";
	public static final String ECO_WAITING = "ECO_WAITING";
	public static final String REPAIR02 = "REPAIR02";
	public static final String REPAIR02_WAITING = "REPAIR02_WAITING";
	public static final String BGA = "BGA";
	public static final String VI = "VI";
	public static final String VI_WAITING = "VI_WAITING";
	public static final String CMB2 = "CMB2";



	private static Map<String, List<List<String>>> instruction;
	private static Map<String,List<String>> instructionDetail;

	private static final String[] allRoles = {"manage_account","v1","shopfloor_management","shipping","search","runin2","extraitem","repair02","repair01","file_downLoad","pre_alert","searchitem","rma-receiver","physicalreceiving","receiving","qc3","qc2","qc1","pe_clca_identify","pe_analyze","packing_slip","packing","obe","mici","eco","cosmetic_check","cmb2","signin","signup"};
	public DBHandler() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbconnection = null;
		sfconnection = null;
		pst = null;
		rs = null;
		pstSF = null;
		rsSF = null;
	}

	/**
	 * getConnection method will create a connection to local database
	 * 
	 * @return object Connection
	 */

	/**
	 * getConnectionAWS method makes a connection to AWS RDS
	 * 
	 * @return an object connection to AWS
	 * @throws ClassNotFoundException
	 */
	public Connection getConnectionAWS() throws ClassNotFoundException {
		try {
			dbconnection = DriverManager.getConnection(
					"jdbc:mysql://" + Configs.dbHost + ":" + Configs.dbPort + "/" + Configs.dbName, Configs.dbUsername,
					Configs.dbPassword);
			instruction = createECOInstruction();
			instructionDetail = createDetailInstruction();
		} catch (SQLException e) {

			System.out.println(e);
		}

		if (dbconnection != null) {
		} else {
			System.out.println("Fail to connect to AWS at GetConnection");
		}
		return dbconnection;

	}

	public Connection getConnectionShopFloor() throws ClassNotFoundException {
		try {
			sfconnection = DriverManager.getConnection(
					"jdbc:mysql://" + ConfigsShoopFloor.dbHost + ":" + ConfigsShoopFloor.dbPort + "/" + ConfigsShoopFloor.dbName, ConfigsShoopFloor.dbUsername,
					ConfigsShoopFloor.dbPassword);
		} catch (SQLException e) {

			System.out.println(e);
		}

		if (sfconnection != null) {
		} else {
			System.out.println("Fail to connect to Shop Floor at GetConnection");
		}
		return sfconnection;

	}


	/**
	 * shutdown method will close connection
	 */
	public void shutdown() {
		// release resources
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println("FAILLL");
			}
			rs = null;
		}
		if (pst != null) {
			try {
				pst.close();
			} catch (SQLException e) {
				System.out.println("FAILLL");
			}
			pst = null;
		}
		if (dbconnection != null) {
			try {

				dbconnection.close();
				// LOGGER.info("====Database close====");
			} catch (SQLException e) {
				System.out.println("FAILLL");
			}
		}

	}

	public void shutdownSF() {
		// release resources
		if (rsSF != null) {
			try {
				rsSF.close();
			} catch (SQLException e) {
				System.out.println("FAILLL");
			}
			rsSF = null;
		}
		if (pstSF != null) {
			try {
				pstSF.close();
			} catch (SQLException e) {
				System.out.println("FAILLL");
			}
			pstSF = null;
		}
		if (sfconnection != null) {
			try {

				sfconnection.close();
				// LOGGER.info("====Database close====");
			} catch (SQLException e) {
				System.out.println("FAILLL");
			}
		}

	}
	/**
	 * @param conn  connection object
	 * @param query a query to check
	 * @return
	 */

	public boolean testConnection() {
		String query = "SHOW DATABASES;";
		boolean flag = false;
		try {

			pst = dbconnection.prepareStatement(query);
			if (pst.executeUpdate() != 0) {
				flag = true;
			}

		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Fail to connect");
		} finally {
			// close database resources
			shutdown();
		}

		return flag;
	}

	public boolean testConnectionSF() {
		String query = "SHOW DATABASES;";
		boolean flag = false;
		try {

			pstSF = dbconnection.prepareStatement(query);
			if (pstSF.executeUpdate() != 0) {
				flag = true;
			}

		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Fail to connect");
		} finally {
			// close database resources
			shutdownSF();
		}

		return flag;
	}

	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***********************************************************
	// * Physical Receiving *
	// ***********************************************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************

	public List<Item> fetchRMA(String ppidN) {
		List<Item> result = new ArrayList<>();
		String FETCH_RMA_QUERY = "SELECT pre_ppid.rma, pre_item.ppid, pre_item.pn, pre_item.co, pre_item.lot,"
				+ "pre_item.dps, pre_item.pro_code, pre_item.code_descp"
				+ " FROM pre_item INNER JOIN pre_ppid ON pre_item.ppid = pre_ppid.ppid AND pre_item.ppid = ?";

		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(FETCH_RMA_QUERY);
			pst.setString(1, ppidN);
			rs = pst.executeQuery();
			while (rs.next()) {
				String rma = rs.getString("rma");
				String ppid = rs.getString("ppid");
				String pn = rs.getString("pn");
				String co = rs.getString("co");
				String sn = "";
				String revision = "";
				String description = rs.getString("code_descp");
				String specialInstruction = "";
				String lot = rs.getString("lot");
				String problemCode = rs.getString("pro_code");
				String dps = rs.getString("dps");
				String mfgPN = "";
				LocalDateTime now = LocalDateTime.now();
				result.add(new Item(ppid, pn, sn, revision, description, specialInstruction, co, lot, problemCode, rma,
						dps, mfgPN, "userID", sdf.format(now)));
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("ERROR fetchRMA");
		} finally {
			shutdown();
		}
		return result;
	}

	public int fetchCurrentReceivedCount(Connection conn, String sn) throws SQLException {
		int result = 0;
		String FETCH_CURRENT_COUNT_RECEIVED = "SELECT * FROM pre_sn_record WHERE serial_number=?";

		pst = conn.prepareStatement(FETCH_CURRENT_COUNT_RECEIVED);
		pst.setString(1, sn);
		rs = pst.executeQuery();
		while (rs.next()) {
			result = rs.getInt("count_recevied");

		}
		return result;
	}

	public void updateAPPID(Connection conn, String ppid) throws SQLException {
		String DELETE_A_PPID = "UPDATE pre_ppid SET status = 'Received' WHERE ppid=?";
		pst = conn.prepareStatement(DELETE_A_PPID);
		pst.setString(1, ppid);
		pst.executeUpdate();
	}

	public boolean PhysicalReceive(String mac, String ppid, String sn, String revision, String cpu_sn, String mfgPN,
			String userId) {
		String FETCH_RMA_QUERY = "INSERT INTO physical_station VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		boolean result = false;
		LocalDateTime now = LocalDateTime.now();
		try {
			dbconnection = getConnectionAWS();
			dbconnection.setAutoCommit(false);
			pst = dbconnection.prepareStatement(FETCH_RMA_QUERY);
			pst.setString(1, ppid);
			pst.setString(2, sn);
			pst.setString(3, mac);
			pst.setString(4, cpu_sn);

			pst.setString(5, revision);
			pst.setString(6, mfgPN);
			pst.setString(7, "User ID");
			pst.setString(8, sdf.format(now));

			pst.executeUpdate();
			// delete record in pre_alert table
			updateAPPID(dbconnection, ppid);
			// add to record table
			addToRecord(dbconnection, sn, ppid);

			dbconnection.commit();
			result = true;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			shutdown();
		}

		return result;
	}

	public boolean isExistInPrePPID(String ppid) {
		boolean result = false;
		String query = "SELECT * FROM pre_ppid WHERE ppid=? AND status=?";

		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, ppid);
			pst.setString(2, "UnRecevied");
			rs = pst.executeQuery();

			if (rs.next()) {
				result = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}

		return result;

	}

	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***********************************************************
	// * END Physical Receiving *
	// ***********************************************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************

	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***********************************************************
	// * Pre-Aler Function *
	// ***********************************************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************

	/*
	 * This function used for getting number of record base on ppid on sn_record
	 * table
	 */
	public int getRecordCount(String ppid) {
		String GET_COUNT_IN_RECORD = "SELECT count_recevied FROM pre_sn_record WHERE ppid = ?";
		int result = -1;

		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(GET_COUNT_IN_RECORD);

			pst.setString(1, ppid);

			rs = pst.executeQuery();
			while (rs.next()) {
				result = rs.getInt("count_recevied");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cannot getRecordCount");
		} finally {
			shutdown();
		}

		return result;

	}

	public List<PreAlertItem> fetchPreAlert(String byRMA) {
		List<PreAlertItem> result = new ArrayList<>();
		String FETCH_ALL_PREALERT = "SELECT * FROM pre_alert";
		String FETCH_ALL_PREALERT_BY_RMA = "SELECT * FROM pre_alert WHERE rma=?";

		String finalQuery = byRMA.isEmpty() ? FETCH_ALL_PREALERT : FETCH_ALL_PREALERT_BY_RMA;
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(finalQuery);
			if (!byRMA.isEmpty()) {
				pst.setString(1, byRMA);
			}
			rs = pst.executeQuery();
			while (rs.next()) {

				String ppid = rs.getString("ppid");
				String pn = rs.getString("pn");
				String co = rs.getString("co");
				String date_received = rs.getString("date_received");
				String lot = rs.getString("lot");
				String dps = rs.getString("dps");
				String problem_code = rs.getString("problem_code");
				String problem_desc = rs.getString("problem_desc");
				String rma = rs.getString("rma");
				result.add(new PreAlertItem(ppid, pn, co, date_received, lot, dps, problem_code, problem_desc, rma));
			}

		} catch (Exception e) {

		} finally {
			shutdown();
		}
		return result;
	}

	public int getRMACount(String pattern) {
		String query = "SELECT COUNT(*)  FROM rma_table WHERE rma LIKE '" + pattern + "%'";
		int result = 0;

		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			rs = pst.executeQuery();
			while (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("FAIL getRMACount" + e.getMessage());

		} finally {
			shutdown();
		}

		return result;
	}

	public boolean createNewRMA(String rma, String userId) {
		String query = "INSERT INTO rma_table VALUES(?,?,?)";
		boolean result = false;
		LocalDateTime now = LocalDateTime.now();
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, rma);
			pst.setString(2, userId);
			pst.setString(3, sdf.format(now));
			pst.executeUpdate();
			result = true;
		} catch (Exception e) {
			System.out.println("FAIL createNewRMA" + e.getMessage());

		} finally {
			shutdown();
		}
		return result;
	}

	/**
	 * Check If ppid is exist in the current database
	 * 
	 * @param ppid
	 * @param pn
	 * @param lot
	 * @return
	 */
	public String isRecordPreAlertExist(String ppid, String pn, String lot) {
		String result = "";
		String CHECK_PRE_ALERT_RECORD = "SELECT * FROM pre_item WHERE pn = ? and lot = ? and ppid=? ";

		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(CHECK_PRE_ALERT_RECORD);
			pst.setString(1, pn);
			pst.setString(2, lot);
			pst.setString(3, ppid);
			rs = pst.executeQuery();

			while (rs.next()) {
				result = rs.getString("ppid");
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			shutdown();
		}

		return result;
	}

	public boolean isRecordPreAlertExist(List<PPID> ppids) throws SQLException, ClassNotFoundException {
		boolean result = false;
		String CHECK_PRE_ALERT_RECORD = "SELECT * FROM pre_item WHERE ppid=? ";
		dbconnection = getConnectionAWS();
		pst = dbconnection.prepareStatement(CHECK_PRE_ALERT_RECORD);
		for (PPID p : ppids) {
			try {
				pst.setString(1, p.getPpidNumber());
				rs = pst.executeQuery();

				if (rs.next()) {
					result = true;
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
		}

		shutdown();

		return result;
	}

	public boolean addToPre_PPID(String rma, List<PPID> list) throws SQLException, ClassNotFoundException {
		boolean result = false;
		String query = "INSERT INTO pre_ppid VALUES (?,?,?)";
		dbconnection = getConnectionAWS();
		// dbconnection.setAutoCommit(false);
		pst = dbconnection.prepareStatement(query);
		int i = 0;
		for (PPID aa : list) {
			pst.setString(1, aa.getPpidNumber());
			pst.setString(2, rma);
			pst.setString(3, "UnRecevied");
			pst.addBatch();
			i++;
			if (i == list.size()) {
				multi m = new multi(pst);
				m.start();
				result = true;
				// dbconnection.commit();
			}
		}
		// pst.executeBatch();

		return result;
	}

	public boolean ppidToDB(List<PPID> listPPID) throws SQLException, ClassNotFoundException {
		boolean result = false;
		String INSERT_PPID = "INSERT INTO pre_item VALUES(?,?,?,?,?,?,?,?,?)";
		dbconnection = getConnectionAWS();
		// dbconnection.setAutoCommit(false);
		pst = dbconnection.prepareStatement(INSERT_PPID);
		int i = 0;
		for (PPID aa : listPPID) {
			pst.setString(1, aa.getPpidNumber());
			pst.setString(2, aa.getPnNumber().split("-")[1]);
			pst.setString(3, aa.getCoNumber());
			pst.setString(4, aa.getLotNumber());
			pst.setString(5, aa.getDpsNumber());
			pst.setString(6, aa.getProblemCode());
			pst.setString(7, aa.getProblemDescription());
			pst.setString(8, "userId");
			pst.setString(9, aa.getDateReceived());
			pst.addBatch();
			i++;
			if (i == listPPID.size()) {
				multi m = new multi(pst);
				m.start();
				result = true;
				// dbconnection.commit();
			}
		}
		// pst.executeBatch();

		return result;
	}

	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***********************************************************
	// * END Pre-Aler Function *
	// ***********************************************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************

	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***********************************************************
	// * MICI STATION *
	// ***********************************************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	public boolean isPPIDExistInMICI(String ppid) {
		boolean result = false;
		String query = "SELECT * FROM mici_station WHERE ppid=?";

		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, ppid);
			rs = pst.executeQuery();

			if (rs.next()) {
				result = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}

		return result;
	}

	public List<ErrorCode> getAllErrorCodes() {
		List<ErrorCode> result = new ArrayList<>();
		String query = "SELECT * FROM mici_errorcode";

		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			rs = pst.executeQuery();
			while (rs.next()) {
				String errorCode = rs.getString("errorCode");
				String description = rs.getString("description");
				result.add(new ErrorCode(errorCode, description));
			}
		} catch (Exception e) {
			System.out.println("FAIL getPhysicalInfor " + e.getMessage());

		} finally {
			shutdown();
		}

		return result;
	}

	public String[] getPhysicalInfor(String ppid) {
		String query = "SELECT * FROM pre_item WHERE ppid=?";
		String[] result = new String[3];

		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, ppid);
			rs = pst.executeQuery();
			while (rs.next()) {
				String pro_code = rs.getString("pro_code");
				String desc_code = rs.getString("code_descp");
				String pn = rs.getString("pn");
				result[0] = pro_code.length() > 1 ? pro_code : "N/A";
				result[1] = desc_code.length() > 1 ? desc_code : "N/A";
				result[2] = pn;
			}
		} catch (Exception e) {
			System.out.println("FAIL getPhysicalInfor " + e.getMessage());

		} finally {
			shutdown();
		}

		return result;

	}

	public boolean addToMICITable(String ppid, String sn, Set<String> errors, String user)
			throws ClassNotFoundException, SQLException {
		boolean result = false;
		String query = "INSERT INTO mici_station (ppid,error,userId,time) VALUES(?,?,?,?)";
		dbconnection = getConnectionAWS();
		pst = dbconnection.prepareStatement(query);
		int i = 0;
		LocalDateTime now = LocalDateTime.now();
		for (String e : errors) {
			pst.setString(1, ppid);
			pst.setString(2, e);
			pst.setString(3, user);
			pst.setString(4, sdf.format(now));
			pst.addBatch();
			i++;
			if (i == errors.size()) {
				int[] a = pst.executeBatch();
				if (a.length > 0)
					result = true;
				else
					result = false;
			}

		}

		return result;
	}

	public boolean addMICI(String ppid, String sn) {

		boolean result = false;
		LocalDateTime now = LocalDateTime.now();
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement("INSERT INTO mici_station VALUES(?,?,?,?)");
			pst.setString(1, ppid);
			pst.setString(2, sn);
			pst.setString(3, "User ID");
			pst.setString(4, sdf.format(now));
			pst.execute();
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			shutdown();
		}

		return result;
	}

	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***********************************************************
	// * MICI STATION *
	// ***********************************************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************




	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***********************************************************
	// *                    Report Functions                     *
	// ***********************************************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************

	/**
	 * This function is used for getting download excel file of all extra physical items after
	 * Physical Receive in
	 * @param items
	 * @param rma
	 */
	public String getExtraItemReport(List<List<String>> items) {
		return new Gson().toJson(items);
	}

	/**
	 * This funtion is used for getting download excel file of all extra physical items, and items as left
	 * on database after doing Physical receiving.
	 * @param rma
	 * @param items
	 */
	public JSONObject getIncorrectItem(String rma, List<List<String>> items) {
		String unreceivedItems = getUnReceiveItem(rma);
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("unreceived", unreceivedItems);
		dataMap.put("incorect", new Gson().toJson(items));
		return new JSONObject(dataMap);
	}

	/**
	 * This function is used for getting all un-receive items
	 * @param rma
	 * @return
	 */
	public String getUnReceiveItem(String rma){
		List<List<String>> result = new ArrayList<List<String>>();
		String jsonResult = "";
		String query = "SELECT * FROM pre_item where pre_item.ppid IN " + 
				"(SELECT ppid  FROM pre_ppid WHERE rma = ? AND status = 'UnRecevied');";
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, rma);
			rs = pst.executeQuery();

			while (rs.next()) {
				List<String> temp = new ArrayList<String>();
				temp.add(rs.getString("ppid"));
				temp.add(rs.getString("pn"));
				temp.add(rs.getString("co"));
				temp.add(rs.getString("lot"));
				temp.add(rs.getString("dps"));
				temp.add(rs.getString("pro_code"));
				temp.add(rs.getString("code_descp"));
				temp.add(rma);
				temp.add("UnReceived");
				result.add(temp);
			}
			jsonResult = new Gson().toJson(result);

		} catch (Exception e) {
			System.out.println("Error getUnReceiveItem function in DBHandler: " + e.getMessage());
		} finally {
			shutdown();
		}

		return jsonResult;
	}


	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***********************************************************
	// *                    Report Functions                     *
	// ***********************************************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************





	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***********************************************************
	// * QC1 STATION *
	// ***********************************************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************

	public boolean insertQC1Table(String ppid, String userId, String testResult) {
		boolean result = false;

		LocalDateTime now = LocalDateTime.now();
		String query = "INSERT INTO qc1_station(ppid,result,userId,time) values(?,?,?,?)";


		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, ppid);
			pst.setString(2, testResult);
			pst.setString(3, userId);		
			pst.setString(4, sdf.format(now));
			pst.executeUpdate();
			result = true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}
		return result;
	}
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***********************************************************
	// *                     QC1 STATION                      *
	// ***********************************************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***********************************************************
	// * REPAIR01 STATION *
	// ***********************************************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	public boolean generateErrorRecord(String ppid) {
		boolean result = false;
		String query = "INSERT INTO repair01_action (ppid,errorCode) SELECT ppid,error FROM mici_station  WHERE ppid=? AND refix='YES'";
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, ppid);
			pst.execute();
			result = true;
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			shutdown();
		}
		return result;
	}

	public HashMap<String, String> fetchErrorFromRepair01(String ppid) {

		String query = "SELECT * FROM mici_errorcode WHERE errorCode IN (SELECT errorCode FROM repair01_action WHERE ppid=? AND repair_action_id IS NULL)";
		HashMap<String, String> result = new HashMap<>();
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, ppid);
			rs = pst.executeQuery();

			while (rs.next()) {
				String errorCode = rs.getString("errorCode");
				String description = rs.getString("description");
				if (errorCode != null && description != null) {
					result.put(errorCode, description);
				}
			}

		} catch (Exception e) {
			System.out.println("Error fetchErrorForRepair01FromMICI: " + e.getMessage());
		} finally {
			shutdown();
		}

		return result;
	}

	public List<String> getAllUndoneErrorCode(String ppid) {
		List<String> result = new ArrayList<>();
		String query = "SELECT * FROM repair01_action WHERE ppid =? AND repair_action_id IS NULL";

		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, ppid);
			rs = pst.executeQuery();

			while (rs.next()) {
				result.add(rs.getString("errorCode"));
			}

		} catch (Exception e) {
			System.out.println("Error isPassedAtRepair01: " + e.getMessage());
		} finally {
			shutdown();
		}

		return result;
	}

	public void updateRepair01Action(Connection conn, String errorCode, String ppid, int recordID) throws SQLException {
		String query = "UPDATE repair01_action SET repair_action_id = ? WHERE ppid=? AND errorCode=?";
		pst = conn.prepareStatement(query);
		pst.setInt(1, recordID);
		pst.setString(2, ppid);
		pst.setString(3, errorCode);
		pst.execute();
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println("FAILLL");
			}
			rs = null;
		}

		if (pst != null) {
			try {
				pst.close();
			} catch (SQLException e) {
				System.out.println("FAILLL");
			}
			pst = null;
		}

	}

	public int updateRepair01RecordAction(String errorCode, String ppid, String duty, String oldPN, String newPN,
			String area, String actionJob) {
		String queryInsert = "INSERT INTO repair01_action_record VALUES(?,?,?,?,?,?,?,?)";
		int recordID = -1;
		LocalDateTime now = LocalDateTime.now();
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(queryInsert, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, null);
			pst.setString(2, duty);
			pst.setString(3, oldPN);
			pst.setString(4, newPN);
			pst.setString(5, area);
			pst.setString(6, actionJob);
			pst.setString(7, "userId");
			pst.setString(8, sdf.format(now));
			recordID = pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			if (rs != null && rs.next()) {
				recordID = rs.getInt(1);
				if (recordID != -1) {
					updateRepair01Action(dbconnection, errorCode, ppid, recordID);
				}
			}

		} catch (Exception e) {
			System.out.println("Error updateRepair01RecordAction: " + e.getMessage());
		} finally {
			shutdown();
		}

		return recordID;

	}

	public void updateRefixMICITable(String errorCode, String ppid, String recordID) {
		String queryInsert = "UPDATE mici_station SET refix = ? WHERE (ppid = ? AND error = ? AND refix = 'YES')";
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(queryInsert, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, recordID);
			pst.setString(2, ppid);
			pst.setString(3, errorCode);
			pst.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}

	}

	public String getPartNumber(String ppid) {
		String result = "";
		String query = "SELECT pn FROM pre_item WHERE ppid = ?";

		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, ppid);
			rs = pst.executeQuery();
			while (rs.next()) {
				result = rs.getString("pn");
			}
		} catch (Exception e) {
			System.out.println("Error getPartNumber: " + e.getMessage());
		} finally {
			shutdown();
		}

		return result;
	}

	public boolean checkIfPartNumberExist(String aPartNumber) {
		boolean result = false;
		String query = "SELECT * FROM repair01_part_number WHERE part_number = ?";

		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, aPartNumber);
			rs = pst.executeQuery();

			if (rs.next()) {
				result = true;
			}

		} catch (Exception e) {
			System.out.println("Error getPartNumber: " + e.getMessage());
		} finally {
			shutdown();
		}

		return result;
	}

	/*****************************************************************
	 * // Getting Instruction // Each of the List is the instruction for upgrading
	 * revision base on part // number
	 *****************************************************************/
	public Map<String,List<String>> createDetailInstruction() {
		instructionDetail = new HashMap<>();
		List<String> list1 = Arrays.asList("DDA30 PU610/PU612/PU613",
				"Change PU610/PU612/PU613 from SA0000AHX00 to SA0000AHX10 or SA0000C4800,"
						+ " to avoid no-power failures",
				"https://image.prntscr.com/image/9uUfNxHtSLGQZm4BGmum-Q.png");

		List<String> list2 = Arrays.asList("DDA30 UT2",
				"TBT FW Upgrade, Improved the stability of power states/ Improved device compatibility",
				"https://image.prntscr.com/image/9uUfNxHtSLGQZm4BGmum-Q.png");

		List<String> list3 = Arrays.asList("DDA30 PC308/PC309/PC310/PC311",
				"Change PC308, PC309,PC310,PC311 from SE0000M00(22uF) to SE000015500 /add CC221(SE000001120) ,"
						+ "fix WHEA BSOD issue",
				"https://image.prntscr.com/image/9uUfNxHtSLGQZm4BGmum-Q.png");

		List<String> list4 = Arrays.asList("DDA30 PU610/PU612/PU613",
				"Change PU610/PU612/PU613 fromSA0000AHX00 / SA0000AHX10 " + 
						"to  SA0000AHX20 or SA0000C4800, Fairchild DrMOS FDMF3035 UIS testing criteria improvement ",
				"https://image.prntscr.com/image/9uUfNxHtSLGQZm4BGmum-Q.png");

		List<String> list5 = Arrays.asList("DDA30 Thunderbolt",
				"Thunderbolt FW upgrade to NVM41, To fix issue cut in new Thunderbolt FW NVM41",
				"https://image.prntscr.com/image/9uUfNxHtSLGQZm4BGmum-Q.png");

		List<String> list6 = Arrays.asList("DDA30 RC417 , RC418 , RC419 , RC420", 
				"Non-AR U42 Replace RC417/ RC418 Change SD028000080 to SD028330A80,Non-AR U22 "
						+ "Replace RC419; RC420 Change SD028000080 to SD028330A80, Cut in HW workaround "
						+ "can get further immunity of Kirkwood MLK flicker issue (BITS410252& BITS423117)"
						+ " if any unproper manual assembly process to cause the thermal module deformation. ",
				"https://image.prntscr.com/image/9uUfNxHtSLGQZm4BGmum-Q.png");

		instructionDetail.put("SECO_N1870266",list1);
		instructionDetail.put("SECO_N1850685",list2);
		instructionDetail.put("SECO_N1880712",list3);
		instructionDetail.put("SECO_N1931193",list4);
		instructionDetail.put("SECO_N1980101",list5);
		instructionDetail.put("SECO_N1980546",list6);
		return instructionDetail;

	}

	public static Map<String, List<List<String>>> createECOInstruction() {
		instruction = new HashMap<>();
		ArrayList<List<String>> mapList = new ArrayList<List<String>>();
		List<String> innerList = new ArrayList<>();
		//PartNumber 1
		innerList.add("SECO_N1931193");innerList.add("N/A");innerList.add("N/A");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("A02");innerList.add("A03");
		mapList.add(innerList);innerList= new ArrayList<>();
		instruction.put("07RYD",mapList);mapList = new ArrayList<List<String>>();

		//PartNumber 2
		innerList.add("SECO_N1870266");innerList.add("00");innerList.add("01");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1850685");innerList.add("A01");innerList.add("A02");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1880712");innerList.add("A03");innerList.add("A04");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("N/A");innerList.add("N/A");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("06");innerList.add("07");
		mapList.add(innerList);innerList= new ArrayList<>();
		instruction.put("1DMJH",mapList);mapList = new ArrayList<List<String>>();
		//PartNumber 3
		innerList.add("SECO_N1931193");innerList.add("N/A");innerList.add("N/A");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("A02");innerList.add("A03");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1980101");innerList.add("A01");innerList.add("A02");
		mapList.add(innerList);innerList= new ArrayList<>();
		instruction.put("2PK0W",mapList);mapList = new ArrayList<List<String>>();
		//PartNumber 4 
		innerList.add("SECO_N1870266");innerList.add("00");innerList.add("01");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1850685");innerList.add("A01");innerList.add("A02");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1880712");innerList.add("A03");innerList.add("A04");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("N/A");innerList.add("N/A");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("06");innerList.add("07");
		mapList.add(innerList);innerList= new ArrayList<>();
		instruction.put("2WCVJ",mapList);mapList = new ArrayList<List<String>>();
		//PartNumber 5 
		innerList.add("SECO_N1870266");innerList.add("00");innerList.add("01");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1850685");innerList.add("A01");innerList.add("A02");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1880712");innerList.add("A03");innerList.add("A04");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("N/A");innerList.add("N/A");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("06");innerList.add("07");
		mapList.add(innerList);innerList= new ArrayList<>();
		instruction.put("41M0M",mapList);mapList = new ArrayList<List<String>>();
		//PartNumber 6 441WF
		innerList.add("SECO_N1931193");innerList.add("N/A");innerList.add("N/A");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("02");innerList.add("03");
		mapList.add(innerList);innerList= new ArrayList<>();
		instruction.put("441WF",mapList);mapList = new ArrayList<List<String>>();
		//PartNumber 7 5FXXY
		innerList.add("SECO_N1931193");innerList.add("N/A");innerList.add("N/A");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("02");innerList.add("03");
		mapList.add(innerList);innerList= new ArrayList<>();
		instruction.put("5FXXY",mapList);mapList = new ArrayList<List<String>>();
		//PartNumber 8 
		innerList.add("SECO_N1870266");innerList.add("00");innerList.add("01");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1880712");innerList.add("A02");innerList.add("A03");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("N/A");innerList.add("N/A");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("05");innerList.add("06");
		mapList.add(innerList);innerList= new ArrayList<>();
		instruction.put("71V71",mapList);mapList = new ArrayList<List<String>>();
		//PartNumber 9
		innerList.add("SECO_N1980101");innerList.add("01");innerList.add("02");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("A02");innerList.add("A03");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("N/A");innerList.add("N/A");
		mapList.add(innerList);innerList= new ArrayList<>();
		instruction.put("C35PP",mapList);mapList = new ArrayList<List<String>>();
		//PartNumber 10
		innerList.add("SECO_N1870266");innerList.add("00");innerList.add("01");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1880712");innerList.add("A02");innerList.add("A03");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("N/A");innerList.add("N/A");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("A05");innerList.add("A06");
		mapList.add(innerList);innerList= new ArrayList<>();
		instruction.put("C4VVY",mapList);mapList = new ArrayList<List<String>>();
		//PartNumber 11
		innerList.add("SECO_N1980101");innerList.add("01");innerList.add("02");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("A02");innerList.add("A03");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("N/A");innerList.add("N/A");
		mapList.add(innerList);innerList= new ArrayList<>();
		instruction.put("CM3RM",mapList);mapList = new ArrayList<List<String>>();
		//PartNumber 12
		innerList.add("SECO_N1931193");innerList.add("A02");innerList.add("A03");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("N/A");innerList.add("N/A");
		mapList.add(innerList);innerList= new ArrayList<>();
		instruction.put("HVW90",mapList);mapList = new ArrayList<List<String>>();
		//PartNumber 13
		innerList.add("SECO_N1870266");innerList.add("00");innerList.add("01");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1850685");innerList.add("A01");innerList.add("A02");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1880712");innerList.add("A03");innerList.add("A04");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("N/A");innerList.add("N/A");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("06");innerList.add("07");
		mapList.add(innerList);innerList= new ArrayList<>();
		instruction.put("JFGFN",mapList);mapList = new ArrayList<List<String>>();
		//PartNumber 14
		innerList.add("SECO_N1980101");innerList.add("01");innerList.add("02");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("A02");innerList.add("A03");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("N/A");innerList.add("N/A");
		mapList.add(innerList);innerList= new ArrayList<>();
		instruction.put("CM3RM",mapList);mapList = new ArrayList<List<String>>();
		//PartNumber 15
		innerList.add("SECO_N1931193");innerList.add("N/A");innerList.add("N/A");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("02");innerList.add("03");
		mapList.add(innerList);innerList= new ArrayList<>();
		instruction.put("JT36N",mapList);mapList = new ArrayList<List<String>>();
		//PartNumber 16
		innerList.add("SECO_N1870266");innerList.add("00");innerList.add("01");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1880712");innerList.add("A02");innerList.add("A03");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("N/A");innerList.add("N/A");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("A05");innerList.add("A06");
		mapList.add(innerList);innerList= new ArrayList<>();
		instruction.put("JXP99",mapList);mapList = new ArrayList<List<String>>();
		//PartNumber 17
		innerList.add("SECO_N1870266");innerList.add("00");innerList.add("01");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1880712");innerList.add("A02");innerList.add("A03");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("N/A");innerList.add("N/A");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("A05");innerList.add("A06");
		mapList.add(innerList);innerList= new ArrayList<>();
		instruction.put("MH7C0",mapList);mapList = new ArrayList<List<String>>();
		//PartNumber 18
		innerList.add("SECO_N1870266");innerList.add("00");innerList.add("01");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1880712");innerList.add("A02");innerList.add("A03");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("N/A");innerList.add("N/A");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("A05");innerList.add("A06");
		mapList.add(innerList);innerList= new ArrayList<>();
		instruction.put("N6W51",mapList);mapList = new ArrayList<List<String>>();
		//PartNumber 19
		innerList.add("SECO_N1931193");innerList.add("N/A");innerList.add("N/A");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("02");innerList.add("03");
		mapList.add(innerList);innerList= new ArrayList<>();
		instruction.put("NGHF1",mapList);mapList = new ArrayList<List<String>>();
		//PartNumber 20
		innerList.add("SECO_N1931193");innerList.add("N/A");innerList.add("N/A");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("02");innerList.add("03");
		mapList.add(innerList);innerList= new ArrayList<>();
		instruction.put("P79TK",mapList);mapList = new ArrayList<List<String>>();
		//PartNumber 21
		innerList.add("SECO_N1931193");innerList.add("N/A");innerList.add("N/A");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("02");innerList.add("03");
		mapList.add(innerList);innerList= new ArrayList<>();
		instruction.put("P86NJ",mapList);mapList = new ArrayList<List<String>>();
		//PartNumber 22
		innerList.add("SECO_N1870266");innerList.add("00");innerList.add("01");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1850685");innerList.add("A01");innerList.add("A02");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1880712");innerList.add("A03");innerList.add("A04");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("N/A");innerList.add("N/A");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("06");innerList.add("07");
		mapList.add(innerList);innerList= new ArrayList<>();
		instruction.put("PHP7P",mapList);mapList = new ArrayList<List<String>>();
		//PartNumber 23
		innerList.add("SECO_N1931193");innerList.add("N/A");innerList.add("N/A");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("02");innerList.add("03");
		mapList.add(innerList);innerList= new ArrayList<>();
		instruction.put("TGTM2",mapList);mapList = new ArrayList<List<String>>();
		//PartNumber 24
		innerList.add("SECO_N1931193");innerList.add("N/A");innerList.add("N/A");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("02");innerList.add("03");
		mapList.add(innerList);innerList= new ArrayList<>();
		instruction.put("TR16P",mapList);mapList = new ArrayList<List<String>>();
		//PartNumber 25
		innerList.add("SECO_N1931193");innerList.add("N/A");innerList.add("N/A");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("02");innerList.add("03");
		mapList.add(innerList);innerList= new ArrayList<>();
		instruction.put("V0P2N",mapList);mapList = new ArrayList<List<String>>();
		//PartNumber 26
		innerList.add("SECO_N1870266");innerList.add("00");innerList.add("01");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1850685");innerList.add("A01");innerList.add("A02");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1880712");innerList.add("A03");innerList.add("A04");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("N/A");innerList.add("N/A");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("06");innerList.add("07");
		mapList.add(innerList);innerList= new ArrayList<>();
		instruction.put("W4DYC",mapList);mapList = new ArrayList<List<String>>();
		//PartNumber 27
		innerList.add("SECO_N1870266");innerList.add("00");innerList.add("01");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1880712");innerList.add("A02");innerList.add("A03");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("N/A");innerList.add("N/A");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("A05");innerList.add("A06");
		mapList.add(innerList);innerList= new ArrayList<>();
		instruction.put("XMNM2",mapList);mapList = new ArrayList<List<String>>();
		//PartNumber 28
		innerList.add("SECO_N1931193");innerList.add("N/A");innerList.add("N/A");
		mapList.add(innerList);innerList= new ArrayList<>();
		innerList.add("SECO_N1931193");innerList.add("02");innerList.add("03");
		mapList.add(innerList);innerList= new ArrayList<>();
		instruction.put("YNMMF",mapList);mapList = new ArrayList<List<String>>();
		return instruction;
	}


	public String getMaxRevision(String part) {
		String query = "SELECT max_ver FROM repair01_part_number WHERE part_number = ?";
		String result = "";
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, part);
			rs = pst.executeQuery();
			if (rs.next()) {
				result = rs.getString("max_ver");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}

		return result.toUpperCase();
	}


	public List<List<String>> getInstruction(String part){
		if(instruction.containsKey(part)) return instruction.get(part);
		else return new ArrayList<List<String>>();
	}

	public List<String> getDetailsInstruction(String code){
		if(instructionDetail.containsKey(code)) return instructionDetail.get(code);
		return new ArrayList<String>();
	}

	public Map<String,List<List<String>>> getInstructionMap(){
		return instruction;
	}

	public Map<String, List<String>> getInstructionDetailMap(){
		return instructionDetail;
	}


	public String getCurrentRev(String ppid) {
		String result = "";

		String query = "SELECT revision FROM physical_station WHERE ppid=?";
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, ppid);
			rs = pst.executeQuery();
			if (rs.next()) {
				result = rs.getString("revision");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}

		return result;

	}

	public boolean updateRevision(String ppid, String newRev) {
		boolean result = false;
		String query = "UPDATE physical_station SET revision=? WHERE ppid = ?";
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, newRev);
			pst.setString(2, ppid);
			pst.execute();
			result = true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}

		return result;

	}

	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***********************************************************
	// * REPAIR01 STATION *
	// ***********************************************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************



	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***********************************************************
	// *                   ECO STATION          *
	// ***********************************************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	public boolean updateECOStation(String ppid,String userId) {
		boolean result = false;
		String maxRev = getMaxRevision(getPartNumber(ppid));
		String currentRev = getCurrentRev(ppid);
		LocalDateTime now = LocalDateTime.now();
		String query = "INSERT INTO eco_station(ppid,original_rev,max_rev,userId,time) values(?,?,?,?,?)";


		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, ppid);
			pst.setString(2, currentRev);
			pst.setString(3, maxRev);
			pst.setString(4, userId);
			pst.setString(5, sdf.format(now));
			pst.execute();
			result = true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}
		return result;
	}

	public boolean isPPIDExistIn(String ppid,String tableName) {
		boolean result = false;
		String query = "SELECT * FROM " + tableName + " WHERE ppid=?";

		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, ppid);
			rs = pst.executeQuery();

			if (rs.next()) {
				result = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}

		return result;
	}

	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***********************************************************
	// *                    ECO               *
	// ***********************************************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************


	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***********************************************************
	// * GENERAL STATION *
	// ***********************************************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************

	public boolean validatePPID(String ppid) {
		return ppid.length() != 0 && isPPIDExistInMICI(ppid);
	}

	public String[] getCurrentStation(String ppid) {
		String query = "SELECT * FROM status_table WHERE ppid= ?";
		String[] result = new String[4];
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, ppid);
			rs = pst.executeQuery();

			while (rs.next()) {
				result[0] = rs.getString("from_location");
				result[1] = rs.getString("to_location");
				result[2] = rs.getString("userId");
				result[3] = rs.getString("date");
			}
		} catch (Exception e) {
			System.out.println("FAIL getCurrentStation" + e.getMessage());

		} finally {
			shutdown();
		}
		return result;
	}

	public boolean updateCurrentStation(String from, String to, String ppid) {
		String query = "UPDATE status_table SET from_location=? , to_location=? WHERE ppid= ?";
		boolean result = false;
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, from);
			pst.setString(2, to);
			pst.setString(3, ppid);
			pst.executeUpdate();
			result = true;
		} catch (Exception e) {
			System.out.println("FAIL updateCurrentStation " + e.getMessage());

		} finally {
			shutdown();
		}
		return result;
	}

	public void addToRecord(Connection conn, String sn, String ppid) throws SQLException {

		String INSERT_INTO_RECORD = "INSERT INTO pre_sn_record VALUES(?,?,?)";
		String UPDATE_RECORD = "UPDATE pre_sn_record set count_recevied= ? where serial_number=?";
		String query = "";
		int newCount = fetchCurrentReceivedCount(conn, sn) + 1;
		if (newCount == 1) {
			query = INSERT_INTO_RECORD;
			pst = conn.prepareStatement(query);
			pst.setString(1, sn);
			pst.setInt(2, newCount);
			pst.setString(3, ppid);
		} else {
			query = UPDATE_RECORD;
			pst = conn.prepareStatement(query);
			pst.setInt(1, newCount);
			pst.setString(2, sn);
		}

		pst.executeUpdate();
	}

	public boolean addToStatusTable(String ppid, String sn, String from, String to) {
		LocalDateTime now = LocalDateTime.now();
		boolean result = false;
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement("INSERT INTO status_table VALUES(?,?,?,?,?,?)");
			pst.setString(1, ppid);
			pst.setString(2, sn);
			pst.setString(3, from);
			pst.setString(4, to);
			pst.setString(5, "USER ID");
			pst.setString(6, sdf.format(now));
			pst.execute();
			result = true;
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			shutdown();
		}

		return result;
	}

	public boolean checkAuthentication(HttpServletRequest request) throws IOException {
		StringBuilder stB = new StringBuilder(UrlPatternUtils.getUrlPattern(request));
		stB.deleteCharAt(0);
		String urlPattern = stB.toString();
		System.out.println(urlPattern);
		try {
			String userName = request.getSession().getAttribute("username").toString();
			String roles = request.getSession().getAttribute("user_role").toString();

			if(userName != null ) {		
				String[] roleArray = roles.split(";");
				for(int i=0; i< roleArray.length; i++) {
					if(roleArray[i].equalsIgnoreCase(urlPattern)) {						
						return true;
					}
				}
			}	
		}catch(Exception e) {
			return false;
		}
		return false;
	}

	public String[] getAllRoles () {
		return this.allRoles;
	}
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***********************************************************
	// * GENERAL STATION *
	// ***********************************************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************

	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***********************************************************
	// * Searching Function *
	// ***********************************************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************

	// Test Done
	public List<List<String>> searchByStation(String station) {
		List<List<String>> result = new ArrayList<List<String>>();
		if (station.equalsIgnoreCase("physical")) {
			result = searchPhysicalReceivingStation();
		} else if (station.equalsIgnoreCase("mici")) {
			result = searchMICIStation();
		} else if (station.equalsIgnoreCase("repair01")) {
			result = searchRepair01Station();
		}
		return result;
	}

	// Test Done
	public List<List<String>> searchPhysicalReceivingStation() {

		List<List<String>> result = new ArrayList<List<String>>();
		String query = "SELECT * FROM physical_station WHERE physical_station.ppid "
				+ "IN (SELECT status_table.ppid FROM status_table "
				+ "WHERE status_table.from_location ='START' AND status_table.to_location ='PHYSICAL_RECEIVING');";
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			rs = pst.executeQuery();
			while (rs.next()) {
				List<String> temp = new ArrayList<String>();
				temp.add(rs.getString("ppid"));
				temp.add(rs.getString("sn"));
				temp.add(rs.getString("MAC"));
				temp.add(rs.getString("cpu_sn"));
				temp.add(rs.getString("revision"));
				temp.add(rs.getString("mPN"));
				temp.add(rs.getString("userId"));
				temp.add(rs.getString("time"));
				result.add(temp);
			}
		} catch (Exception e) {
			System.out.println("Error search physical_station: " + e.getMessage());
		} finally {
			shutdown();
		}
		return result;
	}

	// Test Done
	public List<List<String>> searchMICIStation() {
		List<List<String>> result = new ArrayList<List<String>>();
		String query1 = "SELECT * FROM mici_station WHERE mici_station.ppid "
				+ "IN (SELECT status_table.ppid FROM status_table WHERE (status_table.from_location ='MICI' "
				+ "AND status_table.to_location ='REPAIR01_FAIL') "
				+ "OR (status_table.from_location ='REPAIR01_PASS' AND status_table.to_location ='MICI'))";

		String query2 = "SELECT status_table.ppid FROM status_table WHERE status_table.from_location ='PHYSICAL_RECEIVING' "
				+ "AND status_table.to_location ='MICI';";
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query1);
			rs = pst.executeQuery();
			while (rs.next()) {
				List<String> temp = new ArrayList<String>();
				temp.add(rs.getString("ppid"));
				temp.add(rs.getString("error"));
				temp.add(rs.getString("userId"));
				temp.add(rs.getString("time"));
				result.add(temp);
			}
			pst = dbconnection.prepareStatement(query2);
			rs = pst.executeQuery();
			while (rs.next()) {
				List<String> temp = new ArrayList<String>();
				temp.add(rs.getString("ppid"));
				temp.add("");
				temp.add("");
				temp.add("");
				result.add(temp);
			}
		} catch (Exception e) {
			System.out.println("Error search mici_station: " + e.getMessage());
		} finally {
			shutdown();
		}
		return result;
	}

	// Test Done : NAME STATION SHOULD BE repair01
	public List<List<String>> searchRepair01Station() {
		List<List<String>> result = new ArrayList<List<String>>();
		String query1 = "SELECT repair01_action.ppid,  repair01_action.errorCode, repair01_action_record.duty, "
				+ "repair01_action_record.old_part_number, repair01_action_record.new_part_number, repair01_action_record.area_repair, "
				+ "repair01_action_record.action, repair01_action_record.userId, repair01_action_record.time "
				+ "FROM repair01_action, repair01_action_record "
				+ "WHERE (repair01_action.repair_action_id = repair01_action_record.count) "
				+ "AND repair01_action.ppid " + "IN (SELECT status_table.ppid FROM status_table "
				+ "WHERE (status_table.from_location ='REPAIR01' " + "AND status_table.to_location ='REPAIR01_PASS') "
				+ "OR (status_table.from_location ='REPAIR01_FAIL' AND status_table.to_location ='REPAIR01'));";

		String query2 = "SELECT repair01_action.ppid, repair01_action.errorCode " + "FROM repair01_action "
				+ "WHERE (repair01_action.repair_action_id IS NULL) " + "AND repair01_action.ppid "
				+ "IN (SELECT status_table.ppid FROM status_table " + "WHERE (status_table.from_location ='REPAIR01' "
				+ "AND status_table.to_location ='REPAIR01_PASS') " + "OR (status_table.from_location ='REPAIR01_FAIL' "
				+ "AND status_table.to_location ='REPAIR01'));";

		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query1);
			rs = pst.executeQuery();
			while (rs.next()) {
				List<String> temp = new ArrayList<String>();
				temp.add(rs.getString("ppid"));
				temp.add(rs.getString("errorCode"));
				temp.add(rs.getString("duty"));
				temp.add(rs.getString("old_part_number"));
				temp.add(rs.getString("new_part_number"));
				temp.add(rs.getString("area_repair"));
				temp.add(rs.getString("userId"));
				temp.add(rs.getString("time"));
				temp.add(rs.getString("action"));
				result.add(temp);
			}
			pst = dbconnection.prepareStatement(query2);
			rs = pst.executeQuery();
			while (rs.next()) {
				List<String> temp = new ArrayList<String>();
				temp.add(rs.getString("ppid"));
				temp.add(rs.getString("errorCode"));
				temp.add("");
				temp.add("");
				temp.add("");
				temp.add("");
				temp.add("");
				temp.add("");
				temp.add("");
				result.add(temp);
			}
		} catch (Exception e) {
			System.out.println("Error search searchRepair01Station: " + e.getMessage());
		} finally {
			shutdown();
		}
		return result;
	}

	//Test Done
	public List<String> searchByPPID(String ppid){
		String[] curentStation = getCurrentStation(ppid);
		List<String> result = new ArrayList<String>();
		String query = "SELECT * FROM physical_station WHERE ppid = ?";
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, ppid);
			rs = pst.executeQuery();
			while (rs.next()) {
				result.add(rs.getString("ppid"));
				result.add(rs.getString("sn"));
				result.add(rs.getString("MAC"));
				result.add(rs.getString("cpu_sn"));
				result.add(rs.getString("revision"));
				result.add(rs.getString("mPN"));
				result.add(curentStation[0]);
				result.add(curentStation[1]);
				result.add(curentStation[2]);
				result.add(curentStation[3]);
			}
		} catch (Exception e) {
			System.out.println("Error search searchByPPID: " + e.getMessage());
		} finally {
			shutdown();
		}
		return result;
	}

	// Search by PPId and Stations
	public List<List<String>> searchByPPIDAndStation(String ppid, String station) {
		List<List<String>> result = new ArrayList<List<String>>();
		if (station.equalsIgnoreCase("physical")) {
			result = searchPhysicalReceivingStationByPPID(ppid);
		} else if (station.equalsIgnoreCase("mici")) {
			result = searchMICIStationByPPID(ppid);
		} else if (station.equalsIgnoreCase("repair01")) {
			result = searchRepair01ByPPID(ppid);
		}
		return result;
	}

	// Test Done
	public List<List<String>> searchRepair01ByPPID(String ppid) {
		List<List<String>> temp = searchRepair01Station();
		List<List<String>> result = new ArrayList<List<String>>();
		for (List<String> list : temp) {
			if (list.get(0).equalsIgnoreCase(ppid))
				result.add(list);
		}
		return result;
	}

	//Test Done
	public List<List<String>> searchMICIStationByPPID(String ppid){
		List<List<String>> temp = searchMICIStation();
		List<List<String>> result = new ArrayList<List<String>>();
		for (List<String> list : temp) {
			if (list.get(0).equalsIgnoreCase(ppid))
				result.add(list);
		}
		return result;
	}

	// Test Done
	public List<List<String>> searchPhysicalReceivingStationByPPID(String ppid) {
		List<List<String>> temp = searchPhysicalReceivingStation();
		List<List<String>> result = new ArrayList<List<String>>();
		for (List<String> list : temp) {
			if (list.get(0).equalsIgnoreCase(ppid))
				result.add(list);
		}
		return result;
	}

	//Test Done	
	public List<List<String>> searchPhysicalReceivingStationByDate(String from, String to) throws ParseException{
		List<List<String>> result = new ArrayList<List<String>>();
		String query = "SELECT * FROM physical_station WHERE physical_station.ppid "
				+ "IN (SELECT status_table.ppid FROM status_table WHERE status_table.from_location ='START' "
				+ "AND status_table.to_location ='PHYSICAL_RECEIVING') AND  physical_station.time >= ? AND physical_station.time <= ?";
		if (dateForSearch.parse(to).before(dateForSearch.parse(from)))
			return result;
		String fromDate = dateForSearch.format(new Date(from));
		String endDate = dateForSearch.format(new Date(to));
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, fromDate + " 00:00:00.000");
			pst.setString(2, endDate + " 23:59:59.999");
			rs = pst.executeQuery();
			while (rs.next()) {
				List<String> temp = new ArrayList<String>();
				temp.add(rs.getString("ppid"));
				temp.add(rs.getString("sn"));
				temp.add(rs.getString("MAC"));
				temp.add(rs.getString("cpu_sn"));
				temp.add(rs.getString("revision"));
				temp.add(rs.getString("mPN"));
				temp.add(rs.getString("userId"));
				temp.add(rs.getString("time"));
				result.add(temp);
			}
		} catch (Exception e) {
			System.out.println("Error search physical_station: " + e.getMessage());
		} finally {
			shutdown();
		}
		System.out.println("size: " + result.size());
		return result;
	}


	// Get the amount un-receipt items
	// Received : isReveived is true 
	public int fetchAmountReceived(String rma, boolean isReveived) {
		int result = 0;
		String query = "SELECT COUNT(*) AS rowcount FROM pre_ppid WHERE rma= ? and status=?";
		String status = isReveived == true? "Received" : "UnRecevied";
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1,rma);
			pst.setString(2,status);
			rs = pst.executeQuery();

			if (rs.next()) {
				result = rs.getInt(1);
			} 
		} catch (Exception e) {
			System.out.println("Error search physical_station: " + e.getMessage());
		} finally {
			shutdown();
		}

		return result;
	}
	
	public int fetchAmountItemBasedOnRMA(String rma) {
		int result = 0;
		String query = "SELECT COUNT(*) AS rowcount FROM pre_ppid WHERE rma= ?";
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1,rma);
			rs = pst.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			} 
		} catch (Exception e) {
			System.out.println("Error search physical_station: " + e.getMessage());
		} finally {
			shutdown();
		}
		return result;
	}
	
	
	
	// Test Done
	public List<List<String>> searchMICIStationByDate(String from, String to) throws ParseException {
		List<List<String>> result = new ArrayList<List<String>>();
		String query = "SELECT * FROM mici_station WHERE mici_station.ppid "
				+ "IN (SELECT status_table.ppid FROM status_table WHERE (status_table.from_location ='MICI' "
				+ "AND status_table.to_location ='REPAIR01_FAIL') "
				+ "OR (status_table.from_location ='MICI' AND status_table.to_location ='REPAIR01_PASS') "
				+ "OR (status_table.from_location ='REPAIR01_PASS' AND status_table.to_location ='MICI')) "
				+ "AND mici_station.time >= ? AND mici_station.time <= ?";
		if (dateForSearch.parse(to).before(dateForSearch.parse(from)))
			return result;
		String fromDate = dateForSearch.format(new Date(from));
		String endDate = dateForSearch.format(new Date(to));
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, fromDate + " 00:00:00.000");
			pst.setString(2, endDate + " 23:59:59.999");
			rs = pst.executeQuery();
			while (rs.next()) {
				List<String> temp = new ArrayList<String>();
				temp.add(rs.getString("ppid"));
				temp.add(rs.getString("error"));
				temp.add(rs.getString("userId"));
				temp.add(rs.getString("time"));
				temp.add(rs.getString("refix"));
				result.add(temp);
			}
		} catch (Exception e) {
			System.out.println("Error search mici_station: " + e.getMessage());
		} finally {
			shutdown();
		}
		return result;
	}

	// Test Done
	public List<List<String>> searchRepair01ByDate(String from, String to) throws ParseException {
		List<List<String>> result = new ArrayList<List<String>>();
		String query = "SELECT repair01_action.ppid,  repair01_action.errorCode, repair01_action_record.duty, "
				+ "repair01_action_record.old_part_number, repair01_action_record.new_part_number, repair01_action_record.area_repair, "
				+ "repair01_action_record.action, repair01_action_record.userId, repair01_action_record.time "
				+ "FROM repair01_action, repair01_action_record "
				+ "WHERE (repair01_action.repair_action_id = repair01_action_record.count) "
				+ "AND repair01_action.ppid " + "IN (SELECT status_table.ppid FROM status_table "
				+ "WHERE (status_table.from_location ='REPAIR01' " + "AND status_table.to_location ='REPAIR01_PASS') "
				+ "OR (status_table.from_location ='REPAIR01_FAIL' AND status_table.to_location ='REPAIR01')) "
				+ "AND repair01_action_record.time >= ? AND repair01_action_record.time <= ?";
		if (dateForSearch.parse(to).before(dateForSearch.parse(from)))
			return result;
		String fromDate = dateForSearch.format(new Date(from));
		String endDate = dateForSearch.format(new Date(to));
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, fromDate + " 00:00:00.000");
			pst.setString(2, endDate + " 23:59:59.999");
			rs = pst.executeQuery();
			while (rs.next()) {
				List<String> temp = new ArrayList<String>();
				temp.add(rs.getString("ppid"));
				temp.add(rs.getString("errorCode"));
				temp.add(rs.getString("duty"));
				temp.add(rs.getString("old_part_number"));
				temp.add(rs.getString("new_part_number"));
				temp.add(rs.getString("area_repair"));
				temp.add(rs.getString("action"));
				temp.add(rs.getString("userId"));
				temp.add(rs.getString("time"));
				result.add(temp);
			}
		} catch (Exception e) {
			System.out.println("Error search searchRepair01Station: " + e.getMessage());
		} finally {
			shutdown();
		}
		return result;
	}


	//Search by station with time
	public List<List<String>> searchByStationAndTime(String station,String from, String to) throws ParseException{
		List<List<String>> result = new ArrayList<List<String>>();
		if (station.equalsIgnoreCase("physical")) {
			System.out.println("hrere");
			result = searchPhysicalReceivingStationByDate(from, to);
		} else if (station.equalsIgnoreCase("mici")) {
			result = searchMICIStationByDate(from, to);
		} else if (station.equalsIgnoreCase("repair01")) {
			result = searchRepair01ByDate(from, to);
		}
		return result;
	}

	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***********************************************************
	// * Searching Function *
	// ***********************************************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************

	public class multi extends Thread {
		PreparedStatement pst;

		public multi(PreparedStatement pst) {
			this.pst = pst;
		}

		public void run() {
			try {
				pst.executeBatch();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***********************************************************
	// * SIGN IN / SIGN UP*
	// ***********************************************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	public boolean signUp(String username, String password, String role, String firstName, String lastName) {
		if(username.toLowerCase().contains("admin")) return false;
		String saltRd = generateStringRandom(14);
		String hashPassword = hash(password,saltRd.getBytes());
		boolean result = false;
		String query = "INSERT INTO users VALUES(?,?,?,?,?,?)";
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, username);
			pst.setString(2, hashPassword);
			pst.setString(3, saltRd);
			pst.setString(4, role);
			pst.setString(5, firstName);
			pst.setString(6, lastName);
			pst.execute();			
			result = true;
		}catch(Exception e) {			
			e.printStackTrace();
		}finally {
			shutdown();
		}
		return result;
	}
	public String getSaltFromUsername(String username) {
		String result = "";
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement("SELECT salt FROM users WHERE userid=?");
			pst.setString(1, username);
			rs = pst.executeQuery();
			if(rs.next()) {
				result = rs.getString("salt");
			}	
		}catch(Exception e) {			
			e.printStackTrace();
		}finally {
			shutdown();
		}
		return result;
	}
	public String getPasswordFromUsername(String username) {
		String result = "";
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement("SELECT hashpass FROM users WHERE userid=?");
			pst.setString(1, username);
			rs = pst.executeQuery();
			if(rs.next()) {
				result = rs.getString("hashpass");
			}

		}catch(Exception e) {			
			e.printStackTrace();
		}finally {
			shutdown();
		}
		return result;
	}
	public boolean signIn(String username, String password) {
		String userSalt = getSaltFromUsername(username);
		boolean result = false;
		if(!userSalt.isEmpty()) {
			String userHashPass = getPasswordFromUsername(username);
			if(checkPassword(userHashPass,password,userSalt.getBytes())) {
				result = true;
			}
		}
		return result;

	}

	public String getUserRole(String userName) {
		String query = "SELECT roles FROM users WHERE userid=?";
		String result ="";
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, userName);
			rs = pst.executeQuery();

			while(rs.next()) {
				result = rs.getString("roles");
			}

		}catch (Exception e) {
			result = "error";
			System.out.println("getUserRole error " + e.getMessage());
		}finally {
			shutdown();
		}

		return result;
	}

	public String hash(String passwordToHash, byte[] salt) {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt);
			byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}


	public boolean checkPassword(String hash, String attempt, byte[] salt) {

		String generatedHash = hash(attempt, salt);
		return hash.equals(generatedHash);
	}

	public String generateStringRandom(int n) {
		// lower limit for LowerCase Letters 
		int lowerLimit = 97; 

		// lower limit for LowerCase Letters 
		int upperLimit = 122; 

		Random random = new Random(); 

		// Create a StringBuffer to store the result 
		StringBuffer r = new StringBuffer(n); 

		for (int i = 0; i < n; i++) { 

			// take a random value between 97 and 122 
			int nextRandomChar = lowerLimit 
					+ (int)(random.nextFloat() 
							* (upperLimit - lowerLimit + 1)); 

			// append a character at the end of bs 
			r.append((char)nextRandomChar); 
		} 

		// return the resultant string 
		return r.toString(); 
	}

	// *************************SHOP FLOOR************************
	// *************************SHOP FLOOR************************
	// *************************SHOP FLOOR************************
	// *************************SHOP FLOOR************************
	// *************************SHOP FLOOR************************
	// *                        SHOP FLOOR                       *
	// *************************SHOP FLOOR************************
	// *************************SHOP FLOOR************************
	// *************************SHOP FLOOR************************
	// *************************SHOP FLOOR************************
	// *************************SHOP FLOOR************************



	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***********************************************************
	// *                    CREATE NEW PART NUMBER               *
	// ***********************************************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************

	/**
	 * This function is used for getting list of locations from given part number.
	 * @param partNumber
	 * @return List<String> of location
	 */
	public List<String> getLocationsFromPartNumber(String partNumber){
		List<String> result = new ArrayList<String>();
		String query = "SELECT * FROM location_partnumber_table WHERE part_number = ?";
		try {
			sfconnection = getConnectionShopFloor();
			pstSF = sfconnection.prepareStatement(query);
			pstSF.setString(1, partNumber);
			rsSF = pstSF.executeQuery();
			while (rsSF.next()) {
				result.add(rsSF.getString("location"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shutdownSF();
		}
		return result;
	}

	/**
	 * This function is used for checking if part number is exist in side part_number_table or not.
	 * If it does not exist, this function will return false, otherwise return true.
	 * @param partnumber
	 * @return result which is true or false.
	 */
	public boolean checkPartNumber(String partnumber) {
		boolean result = false;
		String query = "SELECT * FROM part_number_table WHERE part_number = ?";
		try {
			sfconnection = getConnectionShopFloor();
			pstSF = sfconnection.prepareStatement(query);
			pstSF.setString(1, partnumber);
			rsSF = pstSF.executeQuery();
			if (rsSF.next()) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shutdownSF();
		}
		return result;
	}

	/**
	 * This method is used to create new part number if the part number does not exist
	 * This method will return TRUE if creating success, otherwise return FALSE
	 * @param partNumber
	 * @param descp
	 * @param model
	 * @param userId
	 * @return result which is true or false.
	 */
	public boolean createNewPartNumber(String partNumber, String descp, String model, String userId) {
		boolean result = false;
		if(checkPartNumber(partNumber)) {
			System.out.println("Displace error for partnumber is already exist inside the database");
			result = false;
		}else {
			String query = "INSERT INTO part_number_table (part_number,descp,model,userID,time) VALUES(?,?,?,?,?)";
			LocalDateTime now = LocalDateTime.now();
			try {
				sfconnection = getConnectionShopFloor();
				pstSF = sfconnection.prepareStatement(query);
				pstSF.setString(1, partNumber);
				pstSF.setString(2, descp);
				pstSF.setString(3, model);
				pstSF.setString(4, userId);
				pstSF.setString(5, sdf.format(now));
				pstSF.executeUpdate();
				result = true;
			} catch (SQLException | ClassNotFoundException e) {
				System.out.println(e.getMessage());
			} finally {
				shutdownSF();
			}
			return result;
		}
		return result;
	}

	/**
	 * This method is for creating Locations for specific partnumber.
	 * This method will return TRUE if it successfully create locations, otherwise return FALSE
	 * @param location
	 * @param partNumber
	 * @return result which is True or FALSE
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public boolean createLocationForPartNumber(List<String> location,String part_number) throws SQLException, ClassNotFoundException {
		boolean result = false;
		String query = "INSERT INTO location_partnumber_table (part_number,location) VALUES (?,?)";
		sfconnection = getConnectionShopFloor();
		pstSF = sfconnection.prepareStatement(query);
		if(!checkLocationForPartNumber(part_number,location)) {
			result = false;
			System.out.println("Dispace error for cannot adding location: "+location.toString()+" for partnumer: "+part_number);
			return result;
		}else {
			sfconnection = getConnectionShopFloor();
			pstSF = sfconnection.prepareStatement(query);
		}
		int i = 0;
		for(String l : location) {
			pstSF.setString(1, part_number);
			pstSF.setString(2, l);
			pstSF.addBatch();
			i++;
			System.out.println(i);
			if (i == location.size()) {
				int[] a = pstSF.executeBatch();
				if (a.length > 0) {
					shutdownSF();
					result = true;
				}
				else{
					shutdownSF();
					result = false;
				}
			}
		}
		shutdownSF();
		return result;
	}

	/**
	 * This method is checking if a location is already created for a part number.
	 * If the location is exist, the function will return FALSE, otherwise return TRUE
	 * @param partNumber
	 * @param location
	 * @return TRUE or FALSE
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public boolean checkLocationForPartNumber(String partNumber, List<String> location) {
		List<String> list = getLocationsFromPartNumber(partNumber);
		List<String> listLocationName = getLocationName();
		for(String l: location) {
			if(list.contains(l)) return false;
			else {
				if(!listLocationName.contains(l)) {
					createNewLocationTable(l);
				}
			}
		}
		return true;
	}

	/**
	 * This function is used to remove the locations from the given part number.
	 * First, it will remove one by one location from the given locations.
	 * Then, it give double check it remove all the locations.
	 * If it successfully remove all locations, it will return TRUE, otherwise return FALSE
	 * @param partNumber
	 * @param location
	 * @return result which is TRUE or FALSE
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean removeLocationForPartNumner(String partNumber, List<String> location) throws ClassNotFoundException, SQLException {
		boolean result = false;
		List<String> list = getLocationsFromPartNumber(partNumber);
		String query = "DELETE FROM location_partnumber_table WHERE part_number = ? AND location = ?";
		sfconnection = getConnectionShopFloor();
		pstSF = sfconnection.prepareStatement(query);
		int i = 0;
		for(String l: location) {
			if(list.contains(l)) {
				pstSF.setString(1, partNumber);
				pstSF.setString(2, l);
				pstSF.addBatch();
				i++;
				if (i == location.size()) {
					int[] a = pstSF.executeBatch();
					if (a.length > 0) {
						shutdownSF();
						result = true;
					}
					else{
						shutdownSF();
						result = false;
					}
				}
			}
		}
		result = checkLocationForPartNumber(partNumber,location);
		shutdownSF();
		return result;
	}

	/**
	 * This function is used for editing the name of Location of a partnumber.
	 * If it successfully rename the Location, it will return TRUE, otherwise return FALSE
	 * @param partNumber
	 * @param oldLocation
	 * @param newLocation
	 * @return result which is TRUE or FALSE
	 */
	public boolean editLocationNameForPartNumber(String partNumber,String oldLocation, String newLocation) {
		boolean result = false;
		String query = "UPDATE location_partnumber_table SET location = ? WHERE (part_number = ? AND location = ?)";
		List<String> list = new ArrayList<String>();
		list.add(oldLocation);
		if(!checkLocationForPartNumber(partNumber,list)) {
			try {
				sfconnection = getConnectionShopFloor();
				pstSF = sfconnection.prepareStatement(query);
				pstSF.setString(1, newLocation);
				pstSF.setString(2, partNumber);
				pstSF.setString(3, oldLocation);
				rsSF = pstSF.executeQuery();
				result = true;
			} catch (SQLException | ClassNotFoundException e) {
				System.out.println(e.getMessage());
			} finally {
				shutdownSF();
			}
		}else {
			result = false;
		}
		return result;
	}

	/**
	 * This function is used for getting all the locations table inside of the database.
	 * It will return a List<String> as the result
	 * @return List<String>
	 */
	public List<String> getAllLocationTableName(){
		List<String> result = new ArrayList<>();
		String query = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='shop_floor'";
		try {
			sfconnection = getConnectionShopFloor();
			pstSF = sfconnection.prepareStatement(query);
			rsSF = pstSF.executeQuery();
			while (rsSF.next()) {
				String temp = rsSF.getString("TABLE_NAME");

				if(temp.substring(temp.length()-14).equalsIgnoreCase("location_table")) result.add(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shutdownSF();
		}
		return result;
	}

	/**
	 * This function is used for getting location name without _location_table part
	 * @return List<String>
	 */
	public List<String> getLocationName(){
		List<String> result = getAllLocationTableName();
		for(int i = 0 ; i < result.size(); i++) {
			String temp = result.get(i);
			temp = temp.substring(0,(temp.length()-15));
			result.set(i,temp);
		}
		return result;
	}

	/**
	 * This function is used for creating new Location Table inside of the database.
	 * @param locationName type String
	 * @return TRUE or FALSE
	 */
	public boolean createNewLocationTable(String locationName) {
		boolean result = false;
		String tableName = locationName+"_location_table";
		String query = "CREATE TABLE "+tableName+" "
				+ "(count INT NOT NULL AUTO_INCREMENT,part_number VARCHAR(45) NULL,"
				+ "serial_number VARCHAR(45) NULL,"
				+ "ref_1 VARCHAR(45) NULL,"
				+ "ref_2 VARCHAR(45) NULL,"
				+ "ref_3 VARCHAR(45) NULL,"
				+ "ref_4 VARCHAR(45) NULL,"
				+ "ref_5 VARCHAR(45) NULL,"
				+ "ref_6 VARCHAR(45) NULL,"
				+ "ref_7 VARCHAR(45) NULL,"
				+ "ref_8 VARCHAR(45) NULL,"
				+ "ref_9 VARCHAR(45) NULL,"
				+ "ref_10 VARCHAR(45) NULL,"
				+ "PRIMARY KEY (count));";
		try {
			sfconnection = getConnectionShopFloor();
			pstSF = sfconnection.prepareStatement(query);
			pstSF.execute();
			result = true;
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			shutdownSF();
		}
		return result;
	}

	/**
	 * This function is used for adding new Ref to Location Table
	 * @param stationName
	 * @param refName
	 * @return TRUE or FALSE
	 */
	public boolean addingNewRef(String stationName,String refName) {
		boolean result = false;
		List<String> list = getLocationName();
		if(list.contains(stationName)) {
			String query = "ALTER TABLE "+stationName+"_location_table ADD COLUMN "+refName+" MEDIUMTEXT NULL;";
			try {
				sfconnection = getConnectionShopFloor();
				pstSF = sfconnection.prepareStatement(query);
				//pstSF.setString(1, refName);
				pstSF.executeUpdate();
				result = true;
			} catch (SQLException | ClassNotFoundException e) {
				System.out.println(e.getMessage());
			} finally {
				shutdownSF();
			}
		}else return false;	
		return result;
	}

	/**
	 * This function is used for deleting Ref from Location table
	 * @param stationName
	 * @param refName
	 * @return TRUE or FALSE
	 */
	public boolean deleteRef(String stationName, String refName) {
		boolean result = false;
		List<String> list = getLocationName();
		if(list.contains(stationName)) {
			String query = "ALTER TABLE "+stationName+"_location_table DROP COLUMN "+refName;
			try {
				sfconnection = getConnectionShopFloor();
				pstSF = sfconnection.prepareStatement(query);
				pstSF.executeUpdate();
				result = true;
			} catch (SQLException | ClassNotFoundException e) {
				System.out.println(e.getMessage());
			} finally {
				shutdownSF();
			}
		}else return false;	
		return result;
	}

	/**
	 * This function is used for changing Ref name of Location table 
	 * @param stationName
	 * @param refName
	 * @param newRefName
	 * @return TRUE or FALSE
	 */
	public boolean renameRef(String stationName, String refName,String newRefName) {
		boolean result = false;
		List<String> list = getLocationName();
		if(list.contains(stationName)) {
			String query = "ALTER TABLE "+stationName+"_location_table CHANGE COLUMN "+refName+" "+ newRefName+" MEDIUMTEXT NULL DEFAULT NULL";
			try {
				sfconnection = getConnectionShopFloor();
				pstSF = sfconnection.prepareStatement(query);
				pstSF.executeUpdate();
				result = true;
			} catch (SQLException | ClassNotFoundException e) {
				System.out.println(e.getMessage());
			} finally {
				shutdownSF();
			}
		}else return false;	
		return result;
	}


	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***********************************************************
	// *                    CREATE NEW PART NUMBER               *
	// ***********************************************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************



	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***********************************************************
	// *                    STATION CONFIGURATION                *
	// ***********************************************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************

	/**
	 * This function is used for inserting data into UI table
	 * This function then Update Station Table
	 * An array of String will be pass into this function as a data configuration
	 * @param l String[]
	 * @return TRUE or FALSE
	 */
	public int insertIntoUITable(String part_number, String from_location, String to_location,String[] l) {
		int result = -1;
		String query = "INSERT INTO default_ui_table (part_number,part_number_pattern,serial_number,serial_number_pattern,"
				+ "ref_1, ref_pattern_1, ref_count_1, ref_max_1,"
				+ " ref_2, ref_pattern_2, ref_count_2, ref_max_2, ref_3, ref_pattern_3, ref_count_3, ref_max_3,"
				+ " ref_4, ref_pattern_4, ref_count_4, ref_max_4, ref_5, ref_pattern_5, ref_count_5, ref_max_5,"
				+ " ref_6, ref_pattern_6, ref_count_6, ref_max_6, ref_7, ref_pattern_7, ref_count_7, ref_max_7,"
				+ " ref_8, ref_pattern_8, ref_count_8, ref_max_8, ref_9, ref_pattern_9, ref_count_9, ref_max_9,"
				+ " ref_10, ref_pattern_10, ref_count_10, ref_max_10)"
				+ " VALUES (? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		try {
			sfconnection = getConnectionShopFloor();
			pstSF = sfconnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pstSF.setString(1, l[0]);
			pstSF.setString(2, l[1]);
			pstSF.setString(3, l[2]);
			pstSF.setString(4, l[3]);
			pstSF.setString(5, l[4]);
			pstSF.setString(6, l[5]);
			pstSF.setString(7, l[6]);pstSF.setString(8, l[7]);pstSF.setString(9, l[8]);pstSF.setString(10, l[9]);
			pstSF.setString(11, l[10]);pstSF.setString(12, l[11]);pstSF.setString(13, l[12]);pstSF.setString(14, l[13]);pstSF.setString(15, l[14]);
			pstSF.setString(16, l[15]);pstSF.setString(17, l[16]);pstSF.setString(18, l[17]);pstSF.setString(19, l[18]);pstSF.setString(20, l[19]);
			pstSF.setString(21, l[20]);pstSF.setString(22, l[21]);pstSF.setString(23, l[22]);pstSF.setString(24, l[23]);pstSF.setString(25, l[24]);
			pstSF.setString(26, l[25]);pstSF.setString(27, l[26]);pstSF.setString(28, l[27]);pstSF.setString(29, l[28]);pstSF.setString(30, l[29]);
			pstSF.setString(31, l[30]);pstSF.setString(32, l[31]);pstSF.setString(33, l[32]);pstSF.setString(34, l[33]);pstSF.setString(35, l[34]);
			pstSF.setString(36, l[35]);pstSF.setString(37, l[36]);pstSF.setString(38, l[37]);pstSF.setString(39, l[38]);pstSF.setString(40, l[39]);
			pstSF.setString(41, l[40]);pstSF.setString(42, l[41]);pstSF.setString(43, l[42]);pstSF.setString(44, l[43]);
			result = pstSF.executeUpdate();
			rsSF = pstSF.getGeneratedKeys();
			if (rsSF != null && rsSF.next()) {
				result = rsSF.getInt(1);
				if (result != -1) {
					int rule_id = insertIntoRulesTable(result,l[44]);					
					if(rule_id != -1 && !updateStationTable(part_number, from_location, to_location, result, rule_id,"userId")) result = -1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shutdownSF();
		}
		return result;
	}

	/**
	 * This function is used for creating new record for the rules
	 * @param ui_id
	 * @param rule
	 * @return INTEGER
	 */
	public int insertIntoRulesTable(int ui_id, String rule) {
		int result = -1;
		String query = "INSERT INTO default_rules_table (ui_id, rule) VALUES (?,?)";
		try {
			sfconnection = getConnectionShopFloor();
			pstSF = sfconnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pstSF.setLong(1, ui_id);
			pstSF.setString(2, rule);
			result = pstSF.executeUpdate();
			rsSF = pstSF.getGeneratedKeys();
			if (rsSF != null && rsSF.next()) {
				result = rsSF.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shutdownSF();
		}
		return result;
	}

	/**
	 * This function is used for setting up initial condition for Location Configuration
	 * @param l List of String
	 * @return Map<String,Boolean>
	 */
	public Map<String,Boolean> setUpConfigure(List<String> l) {
		Map<String,Boolean> map = new HashMap<String,Boolean>();
		for(String s : l) {
			map.put(s,false);
		}
		return map;
	}

	/**
	 * This function is used for update the relationship between location
	 * @param map which is Map<String,Boolean>
	 * @param from which is original location type String
	 * @param to which is the destination location type String
	 * @return Boolean
	 */
	public boolean updateConfigure(Map<String,Boolean> map, String from, String to){
		if(from.equalsIgnoreCase(to)) {
			return false;
		}
		if(map.get(from) == false && map.get(to) == false) {
			map.put(from,true);
			return true;
		}
		return false;
	}

	/**
	 * This function is checking if a Location has it own relationship with another Location
	 * @param map
	 * @return TRUE or FALSE
	 */
	public boolean checkFinishConfigure(Map<String,Boolean> map) {
		int count = 0;

		for (Object key : map.keySet()) {
			System.out.println("get : "+map.get(key));
			if(map.get(key) == false) count++;
		}
		return count == 1;
	}

	/**
	 * This function is used for searching Locations which has not done the configure
	 * @param map
	 * @return List<String>
	 */
	public List<String> getUnFinishLocationConfigure(Map<String,Boolean> map){
		List<String> result = new ArrayList<>();
		if(!checkFinishConfigure(map)) {
			for (Map.Entry<String, Boolean> entry : map.entrySet()){
				String key = entry.getKey();
				Boolean value = entry.getValue();
				if(value == false) result.add(key);
			}
			return result;
		}else return result;


	}

	/**
	 * This function is used for updating default_station_table right after new UI record has created.
	 * @param part_number
	 * @param from_location
	 * @param to_location
	 * @param serial_number
	 * @param ui_id
	 * @return TRUE or FALSE
	 */
	public boolean updateStationTable(String part_number, String from_location, String to_location, int ui_id,int rules_id, String userId) {
		boolean result = false;
		String station_name = part_number+"_From_"+from_location+"_To_"+to_location;
		String query = "INSERT INTO default_station_table (station_name, part_number, from_location, to_location, ui_id, rules_id, userId) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			sfconnection = getConnectionShopFloor();
			pstSF = sfconnection.prepareStatement(query);
			pstSF.setString(1, station_name);
			pstSF.setString(2, part_number);
			pstSF.setString(3, from_location);
			pstSF.setString(4, to_location);
			pstSF.setLong(5, ui_id);
			pstSF.setLong(6, rules_id);
			pstSF.setString(7, userId);
			pstSF.executeUpdate();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shutdownSF();
		}
		return result;
	}

	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***********************************************************
	// *                    STATION CONFIGURATION                *
	// ***********************************************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************



	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***********************************************************
	// *                    LOAD STATION CONFIGURATION           *
	// ***********************************************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************

	/**
	 * This function is used for getting station name for given part number
	 * @param part_number
	 * @return List<String>
	 */
	public List<List<String>> getStationInfor(String part_number){
		List<List<String>> result = new ArrayList<List<String>>();
		String query = "SELECT station_name,part_number,from_location,to_location FROM default_station_table WHERE part_number = ?";
		try {
			sfconnection = getConnectionShopFloor();
			pstSF = sfconnection.prepareStatement(query);
			pstSF.setString(1, part_number);
			rsSF = pstSF.executeQuery();
			while (rsSF.next()) {
				List<String> temp = new ArrayList<>();
				temp.add(rsSF.getString("station_name"));
				temp.add(rsSF.getString("part_number"));
				temp.add(rsSF.getString("from_location"));
				temp.add(rsSF.getString("to_location"));
				result.add(temp);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			shutdownSF();
		}
		return result;
	}

	/**
	 * This function is used for loading configuration of a function based on part number, from location and to location
	 * @param partNumber
	 * @param fromLocation
	 * @param toLocation
	 * @return String[]
	 */
	public String[] loadConfigure(String partNumber, String fromLocation, String toLocation) {
		String[] result = new String[44];
		String query = "SELECT * FROM default_station_table,default_ui_table,default_rules_table "
				+ "WHERE default_station_table.part_number = ? AND default_station_table.from_location = ? "
				+ "AND default_station_table.to_location = ? AND default_rules_table.count "
				+ "IN ( SELECT default_station_table.rules_id FROM default_station_table "
				+ "WHERE default_station_table.part_number = ? AND default_station_table.from_location = ? "
				+ "AND default_station_table.to_location = ?) AND  default_ui_table.count "
				+ "IN ( SELECT default_station_table.ui_id FROM default_station_table WHERE default_station_table.part_number = ? "
				+ "AND default_station_table.from_location = ? AND default_station_table.to_location = ?)";
		try {
			sfconnection = getConnectionShopFloor();
			pstSF = sfconnection.prepareStatement(query);
			pstSF.setString(1, partNumber);
			pstSF.setString(2, fromLocation);
			pstSF.setString(3, toLocation);
			pstSF.setString(4, partNumber);
			pstSF.setString(5, fromLocation);
			pstSF.setString(6, toLocation);
			pstSF.setString(7, partNumber);
			pstSF.setString(8, fromLocation);
			pstSF.setString(9, toLocation);
			rsSF = pstSF.executeQuery();
			while (rsSF.next()) {
				result[0] = rsSF.getString("part_number");result[1] = rsSF.getString("part_number_pattern");
				result[2] = rsSF.getString("serial_number");result[3] = rsSF.getString("serial_number_pattern");
				result[4] = rsSF.getString("ref_1");result[5] = rsSF.getString("ref_pattern_1");
				result[6] = rsSF.getString("ref_count_1");result[7] = rsSF.getString("ref_max_1");
				result[8] = rsSF.getString("ref_2");result[9] = rsSF.getString("ref_pattern_2");
				result[10] = rsSF.getString("ref_count_2");result[11] = rsSF.getString("ref_max_2");
				result[12] = rsSF.getString("ref_3");result[13] = rsSF.getString("ref_pattern_3");
				result[14] = rsSF.getString("ref_count_3");result[15] = rsSF.getString("ref_max_3");
				result[16] = rsSF.getString("ref_4");result[17] = rsSF.getString("ref_pattern_4");
				result[18] = rsSF.getString("ref_count_4");result[19] = rsSF.getString("ref_max_4");
				result[20] = rsSF.getString("ref_5");result[21] = rsSF.getString("ref_pattern_5");
				result[22] = rsSF.getString("ref_count_5");result[23] = rsSF.getString("ref_max_5");
				result[24] = rsSF.getString("ref_6");result[25] = rsSF.getString("ref_pattern_6");
				result[26] = rsSF.getString("ref_count_6");result[27] = rsSF.getString("ref_max_6");
				result[28] = rsSF.getString("ref_7");result[29] = rsSF.getString("ref_pattern_7");
				result[30] = rsSF.getString("ref_count_7");result[31] = rsSF.getString("ref_max_7");
				result[32] = rsSF.getString("ref_8");result[33] = rsSF.getString("ref_pattern_8");
				result[34] = rsSF.getString("ref_count_8");result[35] = rsSF.getString("ref_max_8");
				result[36] = rsSF.getString("ref_9");result[37] = rsSF.getString("ref_pattern_9");
				result[38] = rsSF.getString("ref_count_9");result[39] = rsSF.getString("ref_max_9");
				result[40] = rsSF.getString("ref_10");result[41] = rsSF.getString("ref_pattern_10");
				result[42] = rsSF.getString("ref_count_10");result[43] = rsSF.getString("ref_max_10");
				result[44] = rsSF.getString("rule");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shutdownSF();
		}
		return result;
	}

	/**
	 * This function is used for updating the UI configure
	 * @param count
	 * @param list
	 * @return TRUE or FALSE
	 */
	public boolean updateUIConfigure(int count, String[] list) {
		boolean result = false;
		String query = "UPDATE default_ui_table SET part_number = ?, part_number_pattern = ?, serial_number = ?, serial_number_pattern = ?, "
				+ "ref_1 = ?, ref_pattern_1 = ?, ref_count_1 = ?, ref_max_1 = ?, ref_2 = ?, ref_pattern_2 = ?, ref_count_2 = ?, ref_max_2 = ?, ref_3 = ?, "
				+ "ref_pattern_3 = ?, ref_count_3 = ?, ref_max_3 = ?, ref_4 = ?, ref_pattern_4 = ?, ref_count_4 = ?, ref_max_4 = ?, ref_5 = ?, ref_pattern_5 = ?, "
				+ "ref_count_5 = ?, ref_max_5 = ?, ref_6 = ?, ref_pattern_6 = ?, ref_count_6 = ?, ref_max_6 = ?, ref_7 = ?, ref_pattern_7 = ?, ref_count_7 = ?, "
				+ "ref_max_7 = ?, ref_8 = ?, ref_pattern_8 = ?, ref_count_8 = ?, ref_max_8 = ?, ref_9 = ?, ref_pattern_9 = ?, ref_count_9 = ?, ref_max_9 = ?, "
				+ "ref_10 = ?, ref_pattern_10 = ?, ref_count_10 = ?, ref_max_10 = ? WHERE (count = ?);";
		try {
			sfconnection = getConnectionShopFloor();
			pstSF = sfconnection.prepareStatement(query);
			pstSF.setString(1, list[0]);pstSF.setString(2, list[1]);
			pstSF.setString(3, list[2]);pstSF.setString(4, list[3]);
			pstSF.setString(5, list[4]);pstSF.setString(6, list[5]);
			pstSF.setString(7, list[6]);pstSF.setString(8, list[7]);
			pstSF.setString(9, list[8]);pstSF.setString(10, list[9]);
			pstSF.setString(11, list[10]);pstSF.setString(12, list[11]);
			pstSF.setString(13, list[12]);pstSF.setString(14, list[13]);
			pstSF.setString(15, list[14]);pstSF.setString(16, list[15]);
			pstSF.setString(17, list[16]);pstSF.setString(18, list[17]);
			pstSF.setString(19, list[18]);pstSF.setString(20, list[19]);
			pstSF.setString(21, list[20]);pstSF.setString(22, list[21]);
			pstSF.setString(23, list[22]);pstSF.setString(24, list[23]);
			pstSF.setString(25, list[24]);pstSF.setString(26, list[25]);
			pstSF.setString(27, list[26]);pstSF.setString(28, list[27]);
			pstSF.setString(29, list[28]);pstSF.setString(30, list[29]);
			pstSF.setString(31, list[30]);pstSF.setString(32, list[31]);
			pstSF.setString(33, list[32]);pstSF.setString(34, list[33]);
			pstSF.setString(35, list[34]);pstSF.setString(36, list[35]);
			pstSF.setString(37, list[36]);pstSF.setString(38, list[37]);
			pstSF.setString(39, list[38]);pstSF.setString(40, list[39]);
			pstSF.setString(41, list[40]);pstSF.setString(42, list[41]);
			pstSF.setString(43, list[42]);pstSF.setString(44, list[43]);
			pstSF.executeUpdate();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shutdownSF();
		}
		return result;
	}

	/**
	 * This function is used for updating rules
	 * @param count
	 * @param rules
	 * @return TRUE or FALSE
	 */
	public boolean updateRulesConfigure(int count, String[] list) {
		boolean result = false;
		String query = "UPDATE default_rules_table SET rule = ? WHERE count = ?";
		try {
			sfconnection = getConnectionShopFloor();
			pstSF = sfconnection.prepareStatement(query);
			pstSF.setString(1, list[44]);
			pstSF.setLong(2, count);
			pstSF.executeUpdate();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shutdownSF();
		}
		return result;
	}

	/**
	 * This function is used for updating existing station
	 * @param partNumber
	 * @param from_location
	 * @param to_location
	 * @param list
	 * @return TRUE or FALSE
	 */
	public boolean updateStationFunction(String partNumber, int from_location, int to_location, String[] list) {
		boolean result = false;
		String query = "SELECT ui_id , rules_id FROM default_station_table WHERE part_number = ? AND from_location = ? AND to_location = ?";
		try {
			sfconnection = getConnectionShopFloor();
			pstSF = sfconnection.prepareStatement(query);
			pstSF.setString(1, partNumber);
			pstSF.setLong(2, from_location);
			pstSF.setLong(3, to_location);
			rsSF = pstSF.executeQuery();

			while (rsSF.next()) {
				int ui_id = rsSF.getInt("ui_id");
				int rules_id = rsSF.getInt("rules_id");
				if(!updateUIConfigure(ui_id, list)) return false;
				else {
					if(!updateRulesConfigure(rules_id, list)) return false;
					else return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shutdownSF();
		}
		return result;
	}


	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***********************************************************
	// *                    LOAD STATION CONFIGURATION           *
	// ***********************************************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************



}
