package com.bizcom.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

}
