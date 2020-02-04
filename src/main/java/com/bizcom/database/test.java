package com.bizcom.database;

public class test {

	public static void main(String[] args) {
		DBHandler db = new DBHandler();
		db.getConnectionAWS();
		db.SavingRMA("testing", "testing", "testing", "testing", "testing", "testing", "testing", "testing", "testing", "testing", "testing", "testing");

	}

}
