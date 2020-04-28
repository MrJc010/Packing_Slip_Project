package com.bizcom.database;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;    

public class test {

	public static void main(String[] args) throws ClassNotFoundException, ParseException, SQLException {
		//Class.forName("com.mysql.cj.jdbc.Driver");
		DBHandler db = new DBHandler();
		db.getConnectionAWS();
		db.signUp("aaaa", "1234567Aa@", "test");
//		db.getConnectionShopFloor();
//		System.out.println(db.getAllLocationTableName());
//		System.out.println(db.getLocationName());
//		System.out.println(db.addingNewRef("testing_table","ref_1"));
//		//System.out.println(db.deleteRef("testing_table","ref_1"));
//		System.out.println(db.renameRef("testing_table","test","newRef"));
//		String[] l = new String[]{"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","abc"};
//		System.out.println(db.insertIntoUITable("part","A","B",l));
//		List<String> list = new ArrayList<>();
//		list.add("scan_box");
//		list.add("open_box");
//		db.createLocationForPartNumber(list, "12345");
		
		
    }

}
