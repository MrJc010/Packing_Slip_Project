package com.bizcom.database;

import java.util.Arrays;

import com.bizcom.ppid.PPID;

public class test {

	public static void main(String[] args) throws ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		DBHandler db = new DBHandler();
		db.getConnectionAWS();
		db.testConnection();
		//db.ppidToDB(Arrays.asList(new PPID("111", "2222", "3333",  "3333",  "3333",  "3333",  "3333",  "3333",  "3333"))) ;

	}

}
