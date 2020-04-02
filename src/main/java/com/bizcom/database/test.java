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
		String fromDate = "03/30/2020";
		String endDate = "04/04/2020";

		System.out.println("-----------------------");
		//list = db.searchRepair01ByDate(fromDate,endDate);;
		
		List<List<String>> list = db.searchByStationAndTime("physical",fromDate,endDate);
		for(List<String> l:list) {
			System.out.println(l);
		}
		

		
//		System.out.println(db.searchByStation("mici"));
	}

}
