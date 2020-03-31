package com.bizcom.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.bizcom.MICI_Station.ErrorCode;
import com.bizcom.ppid.PPID;
import com.bizcom.receiving.physicalreceiving.Item;
import com.bizcom.receiving.physicalreceiving.PreAlertItem;
import com.bizcom.repair01.RevesionUpgrade;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;

public class DBHandler {
	private Connection dbconnection;
	private PreparedStatement pst;
	private ResultSet rs;
	private static final DateTimeFormatter sdf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss.SSS");
	private static final SimpleDateFormat dateForSearch = new SimpleDateFormat("MM/dd/yyyy");
	private List<List<String>> instruction;
	private static final String PHYSICAL_RECEIVING = "PHYSICAL_RECEIVING";
	private static final String MICI = "MICI";
	private static final String REPAIR01_FAIL = "REPAIR01_FAIL";
	private static final String REPAIR01_PASS = "REPAIR01_PASS";
	private static final String REPAIR01 = "REPAIR01";
	private static final String QC1 = "QC1";
	private static final String START = "START";
	public DBHandler() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbconnection = null;
		pst = null;
		rs = null;
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

		} catch (SQLException e) {

			System.out.println(e);
		}

		if (dbconnection != null) {
		} else {
			System.out.println("Fail to connect to AWS at GetConnection");
		}
		return dbconnection;

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

	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***********************************************************
	// *                    Physical Receiving                   *
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
	// *                   END Physical Receiving                *
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
	// *                    Pre-Aler Function                  *
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
	// *                   END Pre-Aler Function               *
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
	// *                        MICI STATION                     *
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
				result[0] = pro_code.length() > 1 ? pro_code : "N/A";
				result[1] = desc_code.length() > 1 ? desc_code : "N/A";
				result[2] = ppid;
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
//				multi m = new multi(pst);
//				m.start();
				int[] a = pst.executeBatch();
				if(a.length > 0) result = true;
				else result = false;
				// dbconnection.commit();
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
	// *                     MICI STATION                        *
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
	// *                       REPAIR01 STATION                  *
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
			
			if(rs.next()) {
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
	// Getting Instruction
	// Each of the List is the instruction for upgrading revision base on part
	// number
	*****************************************************************/
	public List<List<String>> createInstruction() {
		instruction = new ArrayList<List<String>>();
		List<String> list1 = Arrays.asList("PU610/PU612/PU613", "to avoid no-power failures",
				"Change PU610/PU612/PU613 from SA0000AHX00 to SA0000AHX10 or SA0000C4800", "SA0000AHX00",
				"SA0000AHX10 SA0000C4800", "https://image.prntscr.com/image/9uUfNxHtSLGQZm4BGmum-Q.png");

		List<String> list2 = Arrays.asList("UT2",
				"Improved the stability of power states/ Improved device compatibility", "TBT FW Upgrade", "NA", "NA",
				"https://image.prntscr.com/image/9uUfNxHtSLGQZm4BGmum-Q.png");

		List<String> list3 = Arrays.asList("PC308/PC309/PC310/PC311", "fix WHEA BSOD issue",
				"Change PC308, PC309,PC310,PC311 from SE0000M00(22uF) to SE000015500 /add CC221(SE000001120)",
				"SE0000M00", "SE000015500 SE000001120", "https://image.prntscr.com/image/9uUfNxHtSLGQZm4BGmum-Q.png");

		List<String> list4 = Arrays.asList("PU610/PU612/PU613",
				"Fairchild DrMOS FDMF3035 UIS testing criteria improvement",
				"Change PU610/PU612/PU613 fromSA0000AHX00 / SA0000AHX10 to  SA0000AHX20 or SA0000C4800",
				"SA0000AHX00/SA0000AHX10", "SA0000AHX20 or SA0000C4800",
				"https://image.prntscr.com/image/9uUfNxHtSLGQZm4BGmum-Q.png");

		List<String> list5 = Arrays.asList("PU610/PU612/PU613",
				"Fairchild DrMOS FDMF3035 UIS testing criteria improvement",
				"Change PU610/PU612/PU613 fromSA0000AHX00 / SA0000AHX10", "SA0000AHX00/SA0000AHX10",
				"SA0000AHX20 or SA0000C4800", "https://image.prntscr.com/image/9uUfNxHtSLGQZm4BGmum-Q.png");

		List<String> list6 = Arrays.asList("NA", "To fix issue cut in new Thunderbolt FW NVM41",
				"Thunderbolt FW upgrade to NVM41 (cover by testing operation)", "NA", "NA",
				"https://image.prntscr.com/image/9uUfNxHtSLGQZm4BGmum-Q.png");

		List<String> list7 = Arrays.asList("RC417 , RC418 , RC419 , RC420",
				"Cut in HW workaround can get further immunity of Kirkwood MLK flicker issue (BITS410252& BITS423117) if any unproper manual assembly process to cause the thermal module deformation. ",
				"Non-AR U42 Replace RC417/ RC418 Change SD028000080 to SD028330A80,Non-AR U22 Replace RC419; RC420 Change SD028000080 to SD028330A80",
				"SD028000080", "SD028330A80", "https://image.prntscr.com/image/9uUfNxHtSLGQZm4BGmum-Q.png");

		instruction.add(list1);
		instruction.add(list2);
		instruction.add(list3);
		instruction.add(list4);
		instruction.add(list5);
		instruction.add(list6);
		instruction.add(list7);

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

	public RevesionUpgrade getInstruction(String part, String oldR, String newR) {
		part = part.toUpperCase();
		oldR = oldR.toUpperCase();
		newR = newR.toUpperCase();

		// Line 1
		if ((part.equals("N6W51") || part.equals("JXP99") || part.equals("XMNM2") || part.equals("C4VVY")
				|| part.equals("71V71") || part.equals("MH7C0") || part.equals("JFGFN") || part.equals("W4DYC")
				|| part.equals("41M0M") || part.equals("1DMJH") || part.equals("2WCVJ") || part.equals("PHP7P"))
				&& oldR.equals("A00") && newR.equals("A01")) {			
			List<String> list = instruction.get(0);
			return new RevesionUpgrade(part, list.get(0), list.get(1), list.get(2), list.get(3), list.get(4),
					list.get(5), 0);
		}

		// Line2
		else if ((part.equals("JFGFN") || part.equals("W4DYC") || part.equals("41M0M") || part.equals("1DMJH")
				|| part.equals("2WCVJ") || part.equals("PHP7P")) && oldR.equals("A01") && newR.equals("A02")) {
			List<String> list = instruction.get(1);
			return new RevesionUpgrade(part, list.get(0), list.get(1), list.get(2), list.get(3), list.get(4),
					list.get(5), 1);
		}

		// line3.1
		else if ((part.equals("71V71") || part.equals("C4VVY") || part.equals("JXP99") || part.equals("MH7C0")
				|| part.equals("N6W51") || part.equals("XMNM2")) && oldR.equals("A02") && newR.equals("A03")) {
			List<String> list = instruction.get(2);
			return new RevesionUpgrade(part, list.get(0), list.get(1), list.get(2), list.get(3), list.get(4),
					list.get(5), 2);
		}

		// line3.2
		else if ((part.equals("1DMJH") || part.equals("2WCVJ") || part.equals("41M0M") || part.equals("JFGFN")
				|| part.equals("PHP7P") || part.equals("W4DYC")) && oldR.equals("A03") && newR.equals("A04")) {
			List<String> list = instruction.get(2);
			return new RevesionUpgrade(part, list.get(0), list.get(1), list.get(2), list.get(3), list.get(4),
					list.get(5), 3);
		}

		// line5
		else if ((part.equals("07RYD") || part.equals("2PK0W") || part.equals("441WF") || part.equals("5FXXY")
				|| part.equals("C35PP") || part.equals("CM3RM") || part.equals("TGTM2") || part.equals("TR16P")
				|| part.equals("HVW90") || part.equals("JK58T") || part.equals("JT36N") || part.equals("NGHF1")
				|| part.equals("P79TK") || part.equals("P86NJ") || part.equals("V0P2N") || part.equals("YNMMF"))
				&& oldR.equals("A02") && newR.equals("A03")) {
			List<String> list = instruction.get(4);
			return new RevesionUpgrade(part, list.get(0), list.get(1), list.get(2), list.get(3), list.get(4),
					list.get(5), 2);
		}

		// line6
		else if ((part.equals("71V71") || part.equals("C4VVY") || part.equals("JXP99") || part.equals("MH7C0")
				|| part.equals("N6W51") || part.equals("XMNM2")) && oldR.equals("A05") && newR.equals("A06")) {
			List<String> list = instruction.get(4);
			return new RevesionUpgrade(part, list.get(0), list.get(1), list.get(2), list.get(3), list.get(4),
					list.get(5), 5);
		}

		// line7
		else if ((part.equals("1DMJH") || part.equals("2WCVJ") || part.equals("41M0M") || part.equals("JFGFN")
				|| part.equals("PHP7P") || part.equals("W4DYC")) && oldR.equals("A06") && newR.equals("A07")) {
			List<String> list = instruction.get(4);
			return new RevesionUpgrade(part, list.get(0), list.get(1), list.get(2), list.get(3), list.get(4),
					list.get(5), 6);
		}

		// line 8
		else if ((part.equals("2PK0W") || part.equals("C35PP") || part.equals("CM3RM") || part.equals("JK58T")
				|| part.equals("JT36N") || part.equals("NGHF1") || part.equals("P86NJ") || part.equals("TGTM2")
				|| part.equals("V0P2N") || part.equals("YNMMF")) && oldR.equals("A01") && newR.equals("A02")) {
			List<String> list = instruction.get(5);
			return new RevesionUpgrade(part, list.get(0), list.get(1), list.get(2), list.get(3), list.get(4),
					list.get(5), 1);
		}

		// line 9
		else if ((part.equals("1DMJH") || part.equals("2WCVJ") || part.equals("41M0M") || part.equals("JFGFN")
				|| part.equals("PHP7P") || part.equals("W4DYC")) && oldR.equals("A05") && newR.equals("A06")) {
			List<String> list = instruction.get(5);
			return new RevesionUpgrade(part, list.get(0), list.get(1), list.get(2), list.get(3), list.get(4),
					list.get(5), 5);
		}

		// line 10
		else if ((part.equals("441WF") || part.equals("HVW90") || part.equals("5FXXY") || part.equals("TR16P")
				|| part.equals("P79TK") || part.equals("07RYD")) && oldR.equals("A01") && newR.equals("A02")) {
			List<String> list = instruction.get(6);
			return new RevesionUpgrade(part, list.get(0), list.get(1), list.get(2), list.get(3), list.get(4),
					list.get(5), 1);
		}

		// line 11
		else if ((part.equals("N6W51") || part.equals("JXP99") || part.equals("XMNM2") || part.equals("C4VVY")
				|| part.equals("71V71") || part.equals("MH7C0")) && oldR.equals("A04") && newR.equals("A05")) {
			List<String> list = instruction.get(6);
			return new RevesionUpgrade(part, list.get(0), list.get(1), list.get(2), list.get(3), list.get(4),
					list.get(5), 4);
		} else {
			return new RevesionUpgrade();
		}
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

	public void updateRevision(String ppid, String newRev) {
		String query = "UPDATE physical_station SET revision=? WHERE ppid = ?";
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, newRev);
			pst.setString(2, ppid);
			pst.execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}

	}
	
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***********************************************************
	// *                     REPAIR01 STATION                    *
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
	// *                       GENERAL STATION                   *
	// ***********************************************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************

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

	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***********************************************************
	// *                     GENERAL STATION                     *
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
	// *                    Searching Function                   *
	// ***********************************************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	// ***************************START***************************
	
	//Test Done
	public List<List<String>> searchByStation(String station){
		List<List<String>> result = new ArrayList<List<String>>();
		if(station.equalsIgnoreCase("physical")) {
			result = searchPhysicalReceivingStation();
		}else if(station.equalsIgnoreCase("mici")) {
			result = searchMICIStation();
		}else if(station.equalsIgnoreCase("repair01")) {
			result = searchRepair01Station();
		}
		return result;
	}
	
	//Test Done
	public List<List<String>> searchPhysicalReceivingStation(){
		
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
	
	//Test Done
	public List<List<String>> searchMICIStation(){
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
			System.out.println("done 1");
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

	//Test Done
	public List<List<String>> searchRepair01Station(){
		List<List<String>> result = new ArrayList<List<String>>();
		String query1 = "SELECT repair01_action.ppid,  repair01_action.errorCode, repair01_action_record.duty, "
		+ "repair01_action_record.old_part_number, repair01_action_record.new_part_number, repair01_action_record.area_repair, "
		+ "repair01_action_record.action, repair01_action_record.userId, repair01_action_record.time "
		+ "FROM repair01_action, repair01_action_record "
		+ "WHERE (repair01_action.repair_action_id = repair01_action_record.count) "
		+ "AND repair01_action.ppid "
		+ "IN (SELECT status_table.ppid FROM status_table "
		+ "WHERE (status_table.from_location ='REPAIR01' "
		+ "AND status_table.to_location ='REPAIR01_PASS') "
		+ "OR (status_table.from_location ='REPAIR01_FAIL' AND status_table.to_location ='REPAIR01'));";
		
		String query2 = "SELECT repair01_action.ppid, repair01_action.errorCode "
		+ "FROM repair01_action "
		+ "WHERE (repair01_action.repair_action_id IS NULL) "
		+ "AND repair01_action.ppid "
		+ "IN (SELECT status_table.ppid FROM status_table "
		+ "WHERE (status_table.from_location ='REPAIR01' "
		+ "AND status_table.to_location ='REPAIR01_PASS') "
		+ "OR (status_table.from_location ='REPAIR01_FAIL' "
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
				temp.add(rs.getString("action"));
				temp.add(rs.getString("userId"));
				temp.add(rs.getString("time"));
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
	
	//Test Done
	public List<List<String>> searchRepair01ByPPID(String ppid){
		List<List<String>> temp = searchRepair01Station();
		List<List<String>> result = new ArrayList<List<String>>();
		for(List<String> list: temp) {
			if(list.get(0).equalsIgnoreCase(ppid)) result.add(list);
		}
		return result;
	}
	
	
	//Test Done
	public List<List<String>> searchMICIStationByPPID(String ppid){
		List<List<String>> temp = searchMICIStation();
		List<List<String>> result = new ArrayList<List<String>>();
		for(List<String> list: temp) {
			if(list.get(0).equalsIgnoreCase(ppid)) result.add(list);
		}
		return result;
	}

	//Test Done
	public List<List<String>> searchPhysicalReceivingStationByPPID(String ppid){
		List<List<String>> temp = searchPhysicalReceivingStation();
		List<List<String>> result = new ArrayList<List<String>>();
		for(List<String> list: temp) {
			if(list.get(0).equalsIgnoreCase(ppid)) result.add(list);
		}
		return result;
	}

	//Test Done
	public List<List<String>> searchPhysicalReceivingStationByDate(String from, String to) throws ParseException{
		List<List<String>> result = new ArrayList<List<String>>();
		String query = "SELECT * FROM physical_station WHERE physical_station.ppid "
		+ "IN (SELECT status_table.ppid FROM status_table WHERE status_table.from_location ='START' "
		+ "AND status_table.to_location ='PHYSICAL_RECEIVING') AND  physical_station.time >= ? AND physical_station.time <= ?";
		if(dateForSearch.parse(to).before(dateForSearch.parse(from))) return result;
		String fromDate = dateForSearch.format(new Date(from));
		String endDate = dateForSearch.format(new Date(to));
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, fromDate+" 00:00:00.000");
			pst.setString(2, endDate+" 23:59:59.999");
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
	
	//Test NOT Done
	public List<List<String>> searchMICIStationByDate(String from, String to) throws ParseException{
		List<List<String>> result = new ArrayList<List<String>>();
		String query = "SELECT * FROM mici_station WHERE mici_station.time >= ? AND mici_station.time <= ?";
		if(dateForSearch.parse(to).before(dateForSearch.parse(from))) return result;
		String fromDate = dateForSearch.format(new Date(from));
		String endDate = dateForSearch.format(new Date(to));
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, fromDate+" 00:00:00.000");
			pst.setString(2, endDate+" 23:59:59.999");
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
	
	
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***************************END*****************************
	// ***********************************************************
	// *                     Searching Function                  *
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

}
