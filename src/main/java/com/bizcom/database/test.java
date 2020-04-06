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
		db.getConnectionShopFloor();
//		System.out.println(db.getAllLocationTableName());
//		System.out.println(db.getLocationName());
//		System.out.println(db.addingNewRef("testing_table","ref_1"));
//		//System.out.println(db.deleteRef("testing_table","ref_1"));
//		System.out.println(db.renameRef("testing_table","test","newRef"));
		String[] l = new String[]{"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""};
		System.out.println(db.insertIntoUITable("test name","partnumber","from","to",l));
		
	}

}
