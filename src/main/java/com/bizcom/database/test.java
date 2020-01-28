package com.bizcom.database;

import com.bizcom.ppid.PPID;

public class test {

	public static void main(String[] args) {
		DBHandler dbHandler = new DBHandler();
		dbHandler.getConnectionAWS();
		dbHandler.testConnection();

		PPID temp = new PPID("CMP-PMH0T", "CN0PMH0T1296361EA034", "18343956W", "2/12/2018", "017340459", "344433183", "PB3I", "SR Online : networkconnection:Windows 10:");
//		if(dbHandler.ppidToDB(temp)) {
//			System.out.println("ok");
//		}else {
//			System.out.println("Fail upload");
//		}
	}

}
