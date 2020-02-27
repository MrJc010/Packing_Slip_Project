package com.bizcom.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
		String INSERT_PPID = "INSERT INTO pre_alert VALUES(?,?,?,?,?,?,?,?,?)";

		for (PPID aa : listPPID) {
			try {
				dbconnection = getConnectionAWS();
				dbconnection.setAutoCommit(false);
				pst = dbconnection.prepareStatement(INSERT_PPID);
				pst.setString(1, aa.getPpidNumber());
				pst.setString(2, aa.getPnNumber());
				pst.setString(3, aa.getCoNumber());
				pst.setString(4, aa.getDateReceived());
				pst.setString(5, aa.getLotNumber());
				pst.setString(6, aa.getDpsNumber());
				pst.setString(7, aa.getProblemCode());
				pst.setString(8, aa.getProblemDescription());
				pst.setString(9, aa.getRma());
				pst.executeUpdate();
				dbconnection.commit();
				result = true;

			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				shutdown();
			}
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

	public boolean PhysicalReceive(String rmaNum, String cposn, String ppid, String pn, String sn, String revision,
			String specialInstruction, String mfgPN, String lot, String description, String problemCode, String dps) {
		String FETCH_RMA_QUERY = "INSERT INTO physicalRecevingDB VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
			pst.executeUpdate();
			deletaAPPID(dbconnection, ppid);

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
