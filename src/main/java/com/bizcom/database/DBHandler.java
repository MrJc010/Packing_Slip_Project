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

public class DBHandler {
	private Connection dbconnection;
	private PreparedStatement pst;
	private ResultSet rs;

	public DBHandler() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
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
		// Class.forName("com.mysql.jdbc.Driver");
		try {
			dbconnection = DriverManager.getConnection(
					"jdbc:mysql://" + Configs.dbHost + ":" + Configs.dbPort + "/" + Configs.dbName, Configs.dbUsername,
					Configs.dbPassword);
		} catch (SQLException e) {

			System.out.println(e);
		}

		if (dbconnection != null) {
//			System.out.println("SUCCESS!");
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
//				LOGGER.info("====Database close====");
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

	public boolean ppidToDB(List<PPID> listPPID) throws ClassNotFoundException {
		boolean result = false;

		String INSERT_PPID = "INSERT INTO pre_alert VALUES";
		for (PPID aa : listPPID) {
			INSERT_PPID += " ('" + aa.getPpidNumber() + "','" + aa.getPnNumber() + "','" + aa.getCoNumber() + "','"
					+ aa.getDateReceived() + "','" + aa.getLotNumber() + "','" + aa.getDpsNumber() + "','"
					+ aa.getProblemCode() + "','" + aa.getProblemDescription() + "','" + aa.getRma() + "'),";
		}
		dbconnection = getConnectionAWS();
		INSERT_PPID = INSERT_PPID.substring(0, INSERT_PPID.length() - 1);
		INSERT_PPID += ";";
		try {
			pst = dbconnection.prepareStatement(INSERT_PPID);
			pst.executeUpdate();
			result = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public List<Item> fetchRMA(String ppidN, String dpsN) {
		List<Item> result = new ArrayList<>();
		String FETCH_RMA_QUERY = "SELECT * FROM pre_alert WHERE ppid = ? and dps = ?";

		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(FETCH_RMA_QUERY);
			pst.setString(1, ppidN);
			pst.setString(2, dpsN);
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

	public void deletaAPPID(Connection conn, String ppid) throws SQLException {
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

	public boolean PhysicalReceive(String rmaNum, String cposn, String ppid, String pn, String sn, String revision,
			String specialInstruction, String mfgPN, String lot, String description, String problemCode, String dps) {
		String FETCH_RMA_QUERY = "INSERT INTO physicalRecevingDB VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
		boolean result = false;
		try {
			dbconnection = getConnectionAWS();
			dbconnection.setAutoCommit(false);
			pst = dbconnection.prepareStatement(FETCH_RMA_QUERY);
			pst.setString(1, rmaNum);
			pst.setString(2, cposn);
			pst.setString(3, ppid);
			pst.setString(4, pn);

			pst.setString(5, sn);
			pst.setString(6, revision);
			pst.setString(7, specialInstruction);

			pst.setString(8, mfgPN);
			pst.setString(9, lot);
			pst.setString(10, description);
			pst.setString(11, problemCode);
			pst.setString(12, dps);
			pst.setString(13, "User ID");
			pst.setString(14, new Date().toLocaleString());
			pst.executeUpdate();
			// delete record in pre_alert table
			deletaAPPID(dbconnection, ppid);
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

	public int getRecordCount(String sn, String ppid) {
		String GET_COUNT_IN_RECORD = "SELECT count_recevied FROM sn_record WHERE serial_number=? and ppid = ?";
		int result = -1;
		
		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(GET_COUNT_IN_RECORD);
			pst.setString(1, sn);
			pst.setString(2, ppid);
			
			rs = pst.executeQuery();
			while(rs.next()) {
				result = rs.getInt("count_recevied");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cannot getRecordCount");
		}finally {
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
			System.out.println("===cannot find rma=====");
		} finally {
			shutdown();
		}
		System.out.println("RMA : " + result);
		return result;
	}
	
	public boolean MoveToScrap01(String rmaNum, String cposn, String ppid, String pn, String sn, String revision,
			String specialInstruction, String mfgPN, String lot, String description, String problemCode, String dps) {
		String FETCH_RMA_QUERY = "INSERT INTO scrap01_table VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
		boolean result = false;
		try {
			dbconnection = getConnectionAWS();
			dbconnection.setAutoCommit(false);
			pst = dbconnection.prepareStatement(FETCH_RMA_QUERY);
			pst.setString(1, rmaNum);
			pst.setString(2, cposn);
			pst.setString(3, ppid);
			pst.setString(4, pn);

			pst.setString(5, sn);
			pst.setString(6, revision);
			pst.setString(7, specialInstruction);

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
	
}
