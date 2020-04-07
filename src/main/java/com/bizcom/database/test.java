package com.bizcom.database;

import java.text.ParseException;
import java.util.Arrays;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;    

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
		String[] l = new String[]{"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","abc"};
		System.out.println(db.insertIntoUITable("part","A","B",l));
		
		
    }

}
