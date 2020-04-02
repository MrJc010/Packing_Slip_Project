package com.bizcom.database;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.List;
import java.util.Random;    

public class test {

	public static void main(String[] args) throws ClassNotFoundException, ParseException {
		//Class.forName("com.mysql.cj.jdbc.Driver");
		DBHandler db = new DBHandler();
		db.getConnectionAWS();
//		db.testConnection();
		//System.out.println(db.searchByPPID("CN00D1521296359K001C"));
		db.testConnection();
		byte[] array = new byte[7]; // length is bounded by 7
	    new Random().nextBytes(array);
	    String generatedString = new String(array, Charset.forName("UTF-8"));
	    System.out.println(generatedString);
//		String fromDate = "03/27/2020";
//		String endDate = "03/31/2020";
//		List<List<String>> list = db.searchByStationAndTime("physical",fromDate,endDate);
//		for(List<String> l:list) {
//			System.out.println(l);
//		}
//		System.out.println("-----------------------");
		//list = db.searchRepair01ByDate(fromDate,endDate);;
		
//		List<List<String>> list = db.searchByPPIDAndStation("CN02XPCXCMK007CQ0003","repair01");
//		for(List<String> l:list) {
//			System.out.println(l);
//		}
		

		
//		System.out.println(db.searchByStation("mici"));
	}

}
