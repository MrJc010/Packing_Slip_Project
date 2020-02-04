package com.bizcom.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bizcom.physicalreceiving.Item;
import com.bizcom.ppid.PPID;

public class DBHandler {
	private Connection dbconnection;
	private PreparedStatement pst;
	private ResultSet rs;

	public DBHandler() {
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
	 */
	public Connection getConnectionAWS() {

		System.out.println("MySQL JDBC Driver Registered!");

		try {
			dbconnection = DriverManager.getConnection(
					"jdbc:mysql://" + Configs.dbHost + ":" + Configs.dbPort + "/" + Configs.dbName, Configs.dbUsername,
					Configs.dbPassword);
		} catch (SQLException e) {

			System.out.println("FAILLL");
		}

		if (dbconnection != null) {
			System.out.println("SUCCESS!!!! You made it, take control your database now!");
		} else {
			System.out.println("FAILLL");
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
			System.out.println("FAILLL");
		} finally {
			// close database resources
			shutdown();
		}

		return flag;
	}

	public boolean ppidToDB(List<PPID> listPPID) {
		boolean result = false;
		String INSERT_PPID = "INSERT INTO ppid VALUES(?,?,?,?,?,?,?,?)";

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

	public List<Item> fetchRMA(String rma, String ppidN, String dpsN) {
		List<Item> result = new ArrayList<>();
		String FETCH_RMA_QUERY = "SELECT * FROM ppid WHERE rma=? and ppid = ? and dps = ?";

		try {
			dbconnection = getConnectionAWS();
			pst = dbconnection.prepareStatement(FETCH_RMA_QUERY);
			pst.setString(1, rma);
			pst.setString(2, ppidN);
			pst.setString(3, dpsN);
			rs = pst.executeQuery();
			System.out.println("result: " + rs.getFetchSize());
			while (rs.next()) {
				String CPO_SN = "";
				String ppid = rs.getString("ppid");
				String pn = rs.getString("pn");
				String co = rs.getString("pn");
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
		String DELETE_A_PPID = "DELETE FROM ppid WHERE ppid=?";

		pst = conn.prepareStatement(DELETE_A_PPID);
		pst.setString(1, ppid);
		pst.executeUpdate();
	}

	public void SavingRMA(String rmaNum, String cposn, String ppid, String pn, String sn, String revision,
			String specialInstruction, String mfgPN, String lot, String description, String problemCode, String dps) {
		List<Item> result = new ArrayList<>();
		String FETCH_RMA_QUERY = "INSERT INTO rmaReceiving VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
//			while (rs.next()) {
//				String CPO_SN = "";
//				String ppid = rs.getString("ppid");
//				String pn = rs.getString("pn");
//				String co = rs.getString("pn");
//				String sn = "";
//				String revision = "";
//				String description = rs.getString("problem_desc");
//				String specialInstruction = "";
//				String lot = rs.getString("lot");
//				String problemCode = rs.getString("problem_code");
//				String dps = rs.getString("dps");
//				String mfgPN = "";
//
//				result.add(new Item(CPO_SN, ppid, pn, sn, revision, description, specialInstruction, co, lot,
//						problemCode, rma, dps,mfgPN));
//			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			shutdown();
		}
	}

}
