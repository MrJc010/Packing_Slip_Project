package com.bizcom.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    

public class test {

	public static void main(String[] args) throws ClassNotFoundException, ParseException {
		//Class.forName("com.mysql.cj.jdbc.Driver");
		DBHandler db = new DBHandler();
		db.getConnectionAWS();
		db.testConnection();
		String fromDate = "03/1/2020";
		String endDate = "04/1/2020";
		
		System.out.println(db.searchByStation("mici Station"));
	}

}
