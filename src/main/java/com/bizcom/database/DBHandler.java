package com.bizcom.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bizcom.ppid.PPID;
import com.bizcom.receiving.physicalreceiving.Item;
import com.bizcom.receiving.physicalreceiving.PreAlertItem;

public class DBHandler {
	private Connection dbconnection;
	private PreparedStatement pst;
	private ResultSet rs;

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
			System.out.println("Fail to connect");
		} finally {
			// close database resources
			shutdown();
		}

		return flag;
	}

	// public boolean ppidToDB(List<PPID> listPPID) throws ClassNotFoundException {
	// boolean result = false;
	// String INSERT_PPID = "INSERT INTO pre_alert VALUES";
	// for (PPID aa : listPPID) {
	// INSERT_PPID += " ('" + aa.getPpidNumber() + "','" + aa.getPnNumber() + "','"
	// + aa.getCoNumber() + "','"
	// + aa.getDateReceived() + "','" + aa.getLotNumber() + "','" +
	// aa.getDpsNumber() + "','"
	// + aa.getProblemCode() + "','" + aa.getProblemDescription() + "','" +
	// aa.getRma() + "'),";
	// }
	// dbconnection = getConnectionAWS();
	// INSERT_PPID = INSERT_PPID.substring(0, INSERT_PPID.length() - 1);
	// INSERT_PPID += ";";
	// try {
	// pst = dbconnection.prepareStatement(INSERT_PPID);
	// pst.executeUpdate();
	// result = true;
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return result;
	// }

	public boolean ppidToDB(List<PPID> listPPID) throws SQLException, ClassNotFoundException {
		boolean result = false;
		String INSERT_PPID = "INSERT INTO pre_alert VALUES(?,?,?,?,?,?,?,?,?)";
		dbconnection = getConnectionAWS();
		// dbconnection.setAutoCommit(false);
		pst = dbconnection.prepareStatement(INSERT_PPID);
		int i = 0;
		for (PPID aa : listPPID) {
			pst.setString(1, aa.getPpidNumber());
			pst.setString(2, aa.getPnNumber());
			pst.setString(3, aa.getCoNumber());
			pst.setString(4, aa.getDateReceived());
			pst.setString(5, aa.getLotNumber());
			pst.setString(6, aa.getDpsNumber());
			pst.setString(7, aa.getProblemCode());
			pst.setString(8, aa.getProblemDescription());
			pst.setString(9, aa.getRma());
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

	public boolean checkArray(int[] a) {
		if (a.length == 0)
			return false;
		if (a.length == 1)
			return a[0] == 1;
		if (a[0] == 0)
			return false;
		for (int i = 1; i < a.length; i++) {
			if (a[0] != a[i])
				return false;
		}
		return true;
	}

	public List<Item> fetchRMA(String ppidN) {
		List<Item> result = new ArrayList<>();
		String FETCH_RMA_QUERY = "SELECT * FROM pre_alert WHERE ppid = ?";

		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(FETCH_RMA_QUERY);
			pst.setString(1, ppidN);
			rs = pst.executeQuery();
			while (rs.next()) {
				String rma = rs.getString("rma");
				String CPO_SN = "";
				String ppid = rs.getString("ppid");
				String pn = rs.getString("pn");
				String co = rs.getString("co");
				String sn = "";
				String revision = "";
				String description = rs.getString("problem_desc");
				String specialInstruction = "";
				String lot = rs.getString("lot");
				String problemCode = rs.getString("problem_code");
				String dps = rs.getString("dps");
				String mfgPN = "";

				result.add(new Item(CPO_SN, ppid, pn, sn, revision, description, specialInstruction, co, lot,
						problemCode, rma, dps, mfgPN));
			}

		} catch (Exception e) {
			System.out.println("ERROR fetchRMA");
		} finally {
			shutdown();
		}
		return result;
	}

	public List<String> fetchErrorForRepair01FromMICI(String ppid) {
		String query = "SELECT * FROM mici_table WHERE ppid = ?";
		List<String> result = new ArrayList<>();
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, ppid);
			rs = pst.executeQuery();

			while (rs.next()) {
				int i = 1;
				while (i < 11) {
					String temp = rs.getString("error" + i);

					if (temp != null) {
						result.add(temp);
					}
					i++;
				}
			}

		} catch (Exception e) {
			System.out.println("Error fetchErrorForRepair01FromMICI: " + e.getMessage());
		} finally {
			shutdown();
		}

		return result;
	}

	public void deleteAPPID(Connection conn, String ppid) throws SQLException {
		String DELETE_A_PPID = "DELETE FROM pre_alert WHERE ppid=?";

		pst = conn.prepareStatement(DELETE_A_PPID);
		pst.setString(1, ppid);
		pst.executeUpdate();
	}

	public void deletaPhysicalRecord(Connection conn, String ppid) throws SQLException {
		String DELETE_A_PPID = "DELETE FROM physicalRecevingDB WHERE ppid=?";

		pst = conn.prepareStatement(DELETE_A_PPID);
		pst.setString(1, ppid);
		pst.executeUpdate();
	}

	public boolean PhysicalReceive(String rmaNum, String mac, String ppid, String pn, String sn, String revision,
			String cpu_sn, String mfgPN, String lot, String description, String problemCode, String dps) {
		String FETCH_RMA_QUERY = "INSERT INTO physicalRecevingDB VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,? ,?)";
		boolean result = false;
		try {
			dbconnection = getConnectionAWS();
			dbconnection.setAutoCommit(false);
			pst = dbconnection.prepareStatement(FETCH_RMA_QUERY);
			pst.setString(1, rmaNum);
			pst.setString(2, ppid);
			pst.setString(3, pn);
			pst.setString(4, sn);

			pst.setString(5, mac);
			pst.setString(6, cpu_sn);
			pst.setString(7, revision);

			pst.setString(8, mfgPN);
			pst.setString(9, lot);
			pst.setString(10, description);
			pst.setString(11, problemCode);
			pst.setString(12, dps);
			pst.setString(13, "User ID");
			pst.setString(14, new Date().toLocaleString());

			pst.executeUpdate();
			// delete record in pre_alert table
			deleteAPPID(dbconnection, ppid);
			// add to record table
			addToRecord(dbconnection, sn, pn, ppid, dps);

			dbconnection.commit();
			result = true;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			shutdown();
		}

		return result;
	}

	public boolean addMICI(String ppid, String sn) {

		boolean result = false;
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement("INSERT INTO mici_table VALUES(?,?,?,?)");
			pst.setString(1, ppid);
			pst.setString(2, sn);
			pst.setString(3, "User ID");
			pst.setString(4, new Date().toLocaleString());
			pst.execute();
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			shutdown();
		}

		return result;
	}

	public boolean deleteFromMICI(String ppid) {

		boolean result = false;
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement("DELETE FROM mici_table WHERE ppid = ?");
			pst.setString(1, ppid);
			pst.executeUpdate();
			result = true;
			System.out.println("DELETE FROM mici_table ppid " + ppid);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			shutdown();
		}

		return result;
	}

	public boolean addToStatusTable(String ppid, String sn, String from, String to) {

		boolean result = false;
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement("INSERT INTO status_table VALUES(?,?,?,?,?,?)");
			pst.setString(1, ppid);
			pst.setString(2, sn);
			pst.setString(3, from);
			pst.setString(4, to);
			pst.setString(5, "USER ID");
			pst.setString(6, new Date().toLocaleString());
			pst.execute();
			result = true;
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			shutdown();
		}

		return result;
	}

	public boolean deleteFromStatusTable(String ppid) {

		boolean result = false;
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement("DELETE FROM status_table WHERE ppid = ?");
			pst.setString(1, ppid);
			pst.executeUpdate();
			result = true;
			System.out.println("DELETE FROM status_table ppid " + ppid);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			shutdown();
		}

		return result;
	}

	public boolean copyFromRepair01ToRepair01Pass(String ppid) {
		boolean result = false;
		String query = "INSERT INTO repair1_table_pass SELECT * FROM repair1_table WHERE ppid=?";
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, ppid);

			pst.executeUpdate();
			System.out.println("COPY TO repair1_table_pass");
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			shutdown();
		}

		return result;
	}

	public boolean deletePPIDFromRepair01(String ppid) {

		boolean result = false;
		String query = "DELETE FROM repair1_table WHERE ppid=?";
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, ppid);

			pst.executeUpdate();
			System.out.println("DELETE in repair1_table ppid " + ppid);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			shutdown();
		}

		return result;

	}

	public boolean deletePPIDFromRepair01PASS(String ppid) {

		boolean result = false;
		String query = "DELETE FROM repair1_table_pass WHERE ppid=?";
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, ppid);

			pst.executeUpdate();
			System.out.println("DELETE in repair1_table_pass ppid " + ppid);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			shutdown();
		}

		return result;

	}

	public void updateStatusInRepair01(String ppid, String status) {
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement("UPDATE repair1_table SET status=? WHERE  ppid = ?");
			pst.setString(1, status);
			pst.setString(2, ppid);
			pst.executeUpdate();
			System.out.println("UPDATED STATUS repair1_table status " + status);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			shutdown();
		}

	}

	public boolean updateRepair01(String ppid, String repair_action, String duty, String area1, String pn1_old,
			String pn1_new, String area2, String pn2_old, String pn2_new, String area3, String pn3_old, String pn3_new,
			String area4, String pn4_old, String pn4_new, String area5, String pn5_old, String pn5_new, String status,
			String userId) {
		boolean result = false;
		String query = "UPDATE repair1_table SET repair_action=?, duty=?, " + "area1=?, pn1_old=?, pn1_new=?, "
				+ "area2=?, pn2_old=?, pn2_new=?, " + "area3=?, pn3_old=?, pn3_new=?, "
				+ "area4=?, pn4_old=?, pn4_new=?, " + "area5=?, pn5_old=?, pn5_new=?, " + "status=?, userId=?,  date=? "
				+ "WHERE ppid = ?";

		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, repair_action);
			pst.setString(2, duty);
			pst.setString(3, area1);
			pst.setString(4, pn1_old);
			pst.setString(5, pn1_new);
			pst.setString(6, area2);
			pst.setString(7, pn2_old);
			pst.setString(8, pn2_new);
			pst.setString(9, area3);
			pst.setString(10, pn3_old);
			pst.setString(11, pn3_new);
			pst.setString(12, area4);
			pst.setString(13, pn4_old);
			pst.setString(14, pn4_new);
			pst.setString(15, area5);
			pst.setString(16, pn5_old);
			pst.setString(17, pn5_new);
			pst.setString(18, status);
			pst.setString(19, userId);
			pst.setString(20, new Date().toLocaleString());
			pst.setString(21, ppid);

			pst.executeUpdate();
			System.out.println("UPDATED repair1_table");
			result = true;
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			shutdown();
		}

		return result;

	}

	public String[] addRepair01Helper(String ppid) {
		String query = "SELECT sn, problemCode FROM physicalrecevingdb WHERE ppid=?";
		String[] phy = new String[2];
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, ppid);
			rs = pst.executeQuery();
			while (rs.next()) {
				phy[0] = rs.getString("sn");
				phy[1] = rs.getString("problemCode");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			shutdown();
		}

		return phy;
	}

	public boolean addToMICITable(String ppid, String sn, String user) {
		boolean result = false;
		String query = " INSERT INTO mici_table (ppid,sn,date,userId) VALUES(?,?,?,?)";
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, ppid);
			pst.setString(2, sn);
			pst.setString(3, new Date().toLocaleString());
			pst.setString(4, user);

			pst.execute();
			result = true;

		} catch (Exception e) {
			System.out.println("addToMICITable : " + e.getMessage());
		} finally {
			shutdown();
		}

		return result;
	}

	public boolean isExistInRepair01Table(String ppid) {
		boolean result = false;
		String query = "SELECT COUNT(*) FROM repair1_table WHERE ppid = ?";

		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, ppid);
			rs = pst.executeQuery();

			if (rs.next()) {
				result = true;
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			shutdown();
		}

		return result;
	}

	public boolean addNewToRepair01Table(String ppid, String userId) {

		// status is PASS OR FAIL
		boolean result = false;
		String query = "INSERT INTO repair1_table (ppid,sn,currentStatus,userId,receivedDate) VALUES(?,?,?,?,?)";
//		"INSERT INTO repair1_table (ppid, sn, problem_code, status, userId, date) VALUES(?,?,?,?,?,?)";
		userId = " DELETE ME LATER 377";
		// Status is FAIL at beginning

		String[] support = addRepair01Helper(ppid);

		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, ppid);
			pst.setString(2, support[0]);
			pst.setString(3, "FAIL");
			pst.setString(4, userId);
			pst.setString(5, new Date().toLocaleString());
			pst.execute();
			result = true;
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			shutdown();
		}

		return result;

	}

	public int fetchCurrentReceivedCount(Connection conn, String sn) throws SQLException {
		int result = 0;
		String FETCH_CURRENT_COUNT_RECEIVED = "SELECT * FROM sn_record WHERE serial_number=?";

		pst = conn.prepareStatement(FETCH_CURRENT_COUNT_RECEIVED);
		pst.setString(1, sn);
		rs = pst.executeQuery();
		while (rs.next()) {
			result = rs.getInt("count_recevied");

		}
		return result;
	}

	public int getRecordCount(String ppid) {
		String GET_COUNT_IN_RECORD = "SELECT count_recevied FROM sn_record WHERE ppid = ?";
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

	public boolean updateErrorCodeMICI(String ppid, String errorCode, int location) {
		boolean result = false;

		String query = "UPDATE mici_table SET error" + location + "=? WHERE ppid=?";
		System.out.println("QUERY addErrorCode" + query);

		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);

			pst.setString(1, errorCode);
			pst.setString(2, ppid);
			pst.executeUpdate();
			result = true;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cannot getRecordCount");
		} finally {
			shutdown();
		}

		return result;
	}

	public void addToRecord(Connection conn, String sn, String pn, String ppid, String dps) throws SQLException {

		String INSERT_INTO_RECORD = "INSERT INTO sn_record VALUES(?,?,?,?,?)";
		String UPDATE_RECORD = "UPDATE sn_record set count_recevied= ? where serial_number=?";
		String query = "";
		int newCount = fetchCurrentReceivedCount(conn, sn) + 1;
		if (newCount == 1) {
			query = INSERT_INTO_RECORD;
			pst = conn.prepareStatement(query);
			pst.setString(1, sn);
			pst.setString(2, pn);
			pst.setInt(3, newCount);
			pst.setString(4, ppid);
			pst.setString(5, dps);
		} else {
			query = UPDATE_RECORD;
			pst = conn.prepareStatement(query);
			pst.setInt(1, newCount);
			pst.setString(2, sn);
		}

		pst.executeUpdate();
	}

	/**
	 * Check If ppid is exist in the current database
	 * @param ppid
	 * @param pn
	 * @param lot
	 * @return
	 */
	public String isRecordPreAlertExist(String ppid, String pn, String lot) {
		String result = "";
		String CHECK_PRE_ALERT_RECORD = "SELECT * FROM pre_alert WHERE pn = ? and lot = ? and ppid=? ";

		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(CHECK_PRE_ALERT_RECORD);
			pst.setString(1, pn);
			pst.setString(2, lot);
			pst.setString(3, ppid);
			rs = pst.executeQuery();

			while (rs.next()) {
				result = rs.getString("rma");
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			shutdown();
		}
		
		return result;
	}

	public boolean MoveToScrap01(String rmaNum, String mac, String ppid, String pn, String sn, String revision,
			String cpu_sn, String mfgPN, String lot, String description, String problemCode, String dps) {
		String FETCH_RMA_QUERY = "INSERT INTO scrap01_table VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
		boolean result = false;
		try {
			dbconnection = getConnectionAWS();
			dbconnection.setAutoCommit(false);
			pst = dbconnection.prepareStatement(FETCH_RMA_QUERY);
			pst.setString(1, rmaNum);
			pst.setString(2, ppid);
			pst.setString(3, pn);
			pst.setString(4, sn);

			pst.setString(5, mac);
			pst.setString(6, cpu_sn);
			pst.setString(7, revision);

			pst.setString(8, mfgPN);
			pst.setString(9, lot);
			pst.setString(10, description);
			pst.setString(11, problemCode);
			pst.setString(12, dps);
			pst.setString(13, "User ID");
			pst.setString(14, new Date().toLocaleString());
			pst.executeUpdate();
			// delete record in pre_alert table
			deletaPhysicalRecord(dbconnection, ppid);
			// add to record table
			addToRecord(dbconnection, sn, pn, ppid, dps);
			dbconnection.commit();
			result = true;

		} catch (Exception e) {
			System.out.println(e);
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
		String query = "SELECT COUNT(*)  FROM rmaTable WHERE rma LIKE '" + pattern + "%'";
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

	public boolean createNewRMA(String rma) {
		String query = "INSERT INTO rma_table VALUES(?,?)";
		boolean result = false;
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, null);
			pst.setString(2, rma);
			pst.executeUpdate();
			result = true;
		} catch (Exception e) {
			System.out.println("FAIL createNewRMA" + e.getMessage());

		} finally {
			shutdown();
		}

		return result;
	}

	// ====== MICI ======

	public String[] getPhysicalInfor(String ppid) {
		String query = "SELECT * FROM physicalRecevingDB WHERE ppid=?";
		String[] result = new String[3];

		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, ppid);
			rs = pst.executeQuery();
			while (rs.next()) {
				result[0] = rs.getString("problemCode");
				result[1] = rs.getString("desc");
				result[2] = rs.getString("sn");
			}
		} catch (Exception e) {
			System.out.println("FAIL getMICIInfo" + e.getMessage());

		} finally {
			shutdown();
		}

		return result;

	}

	public String[] getCurrentStation(String ppid) {
		String query = "SELECT * FROM status_table WHERE ppid= ?";
		String[] result = new String[2];
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(query);
			pst.setString(1, ppid);
			rs = pst.executeQuery();

			while (rs.next()) {
				result[0] = rs.getString("from_location");
				result[1] = rs.getString("to_location");
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
			System.out.println("FAIL updateCurrentStation" + e.getMessage());

		} finally {
			shutdown();
		}
		return result;
	}

	public class multi extends Thread {
		PreparedStatement pst;

		public multi(PreparedStatement pst) {
			this.pst = pst;
		}

		public void run() {
			System.out.println("running...");
			try {
				int[] a = pst.executeBatch();
				System.out.println(a.length);
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}
}
