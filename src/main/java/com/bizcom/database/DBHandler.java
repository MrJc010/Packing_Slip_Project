package com.bizcom.database;

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

import com.bizcom.MICI_Station.ErrorCode;
import com.bizcom.ppid.PPID;
import com.bizcom.receiving.physicalreceiving.Item;
import com.bizcom.receiving.physicalreceiving.PreAlertItem;
import com.bizcom.repair01.RevesionUpgrade;

public class DBHandler {
	private Connection dbconnection;
	private Connection sfconnection;
	private PreparedStatement pst;
	private ResultSet rs;
	private PreparedStatement pstSF;
	private ResultSet rsSF;
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
				if (a.length > 0)
					result = true;
				else
					result = false;
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
	// * GENERAL STATION *
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
	public boolean signUp(String username, String password) {
		String tempRan = generateStringRandom(14);
		String hashPassword = hash(password,tempRan.getBytes());
		boolean result = false;
		String query = "INSERT INTO users VALUES(?,?,?)";
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, username);
			pst.setString(2, hashPassword);
			pst.setString(3, tempRan);
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
//			String hashPassword = hash(password, userSalt.getBytes());
			String userHashPass = getPasswordFromUsername(username);
			if(checkPassword(userHashPass,password,userSalt.getBytes())) {
				result = true;
			}
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
		String query = "SELECT * FROM part_number_table WHERE part_number = ?";
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
		if(!checkPartNumber(partNumber)) {
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
				rsSF = pstSF.executeQuery();
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
	public boolean createLocationForPartNumber(List<String> location,String partNumber) throws SQLException, ClassNotFoundException {
		boolean result = false;
		String query = "INSERT INTO location_partnumber_table (part_number,location) VALUES(?,?)";
		sfconnection = getConnectionShopFloor();
		pstSF = sfconnection.prepareStatement(query);
		if(!checkLocationForPartNumber(partNumber,location)) {
			result = false;
			System.out.println("Dispace error for cannot adding location: "+location.toString()+" for partnumer: "+partNumber);
			return result;
		}
		int i = 0;
		for(String l : location) {
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
	public int insertIntoUITable(String part_number, String from_location, String to_location, String serial_number,String[] l) {
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
					int rule_id = insertIntoRulesTable(result,l[43]);
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
	 * @return Map<String,Boolean>
	 */
	public Map<String,Boolean> updateConfigure(Map<String,Boolean> map, String from, String to){
		if(map.get(from) == false && map.get(to) == false) {
			map.put(from,true);
		}
		return map;
	}
	
	/**
	 * This function is checking if a Location has it own relationship with another Location
	 * @param map
	 * @return TRUE or FALSE
	 */
	public boolean checkFinishConfigure(Map<String,Boolean> map) {
		int count = 0;
		for(int i = 0 ; i < map.size(); i++) {
			if(map.get(i) == false) count++;
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

	//

	
	
}
