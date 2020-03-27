package com.bizcom.database;

import java.util.HashSet;
import java.util.Set;

public class test {

	public static void main(String[] args) throws ClassNotFoundException {
		//Class.forName("com.mysql.cj.jdbc.Driver");
		DBHandler db = new DBHandler();
		db.getConnectionAWS();
		db.testConnection();
	}

}
