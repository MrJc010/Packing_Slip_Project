package com.bizcom.database;

import java.text.ParseException;
import java.util.List;    

public class test {

	public static void main(String[] args) throws ClassNotFoundException, ParseException {
		//Class.forName("com.mysql.cj.jdbc.Driver");
		DBHandler db = new DBHandler();
		db.getConnectionAWS();
//		db.testConnection();
		//System.out.println(db.searchByPPID("CN00D1521296359K001C"));
		db.testConnection();
//		String radString  = (db.generateStringRandom(14));
//		String hashOut = db.hash("admin", radString.getBytes());
//		System.out.println(hashOut);
//		
//		// check by get salt from database   + input user
//		// compare
//		System.out.println(db.checkPassword( hashOut,"admin", radString.getBytes()));

//		db.signUp("admin1", "admin");
		System.out.println(db.getSaltFromUsername("admin11"));
		System.out.println(db.signIn("admin1","admin"));
//		System.out.println(db.searchByStation("mici"));
	}

}
