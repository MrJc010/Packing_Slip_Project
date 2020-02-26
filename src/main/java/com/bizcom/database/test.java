package com.bizcom.database;

import java.util.Arrays;

import com.bizcom.ppid.PPID;

public class test {

	public static void main(String[] args) throws ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		DBHandler db = new DBHandler();
		db.getConnectionAWS();
		
		db.testConnection();

	}

}
