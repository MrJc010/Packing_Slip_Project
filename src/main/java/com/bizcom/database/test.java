package com.bizcom.database;

import java.util.Arrays;

import com.bizcom.ppid.PPID;

public class test {

	public static void main(String[] args) throws ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		DBHandler db = new DBHandler();
		
//		db.PhysicalReceive("BZM123", "012345", "CN0J8CVMCMK007BD00AA", "CMP-PMH0T", "123456789", "Rev.056", "12345", "456343434", "12", "SR Online : networkconnection:Windows 10:", "PB3I", "654656565");
//		db.addMICI("CN0J8CVMCMK007BD00AA", "123456789");
//		db.addToStatusTable("CN0J8CVMCMK007BD00AA", "CN0J8CVMCMK007BD00AA", "A", "B");
		
//		db.deleteFromStatusTable("CN0J8CVMCMK007BD00AA");	
//		db.addNewToRepair01Table("CN0J8CVMCMK007BD00AA","1111", "12asdasd","USER ID" );
		
		
//		db.updateRepair01("CN0J8CVMCMK007BD00AA", "repair_action", "duty", "area1", "pn1_old", "pn1_new", "area2", "pn2_old", "pn2_new", "area3", "pn3_old", "pn3_new", "pn3_new", "pn4_old", "pn4_new", "area5", "pn5_old", "pn5_new", "status", "USER IDsss");
		
//		db.updateStatusInRepair01("CN0J8CVMCMK007BD00AA", "PASS");
//		db.copyFromRepair01ToRepair01Pass("CN0J8CVMCMK007BD00AA");
		
		
//		db.deletePPIDFromRepair01("CN0J8CVMCMK007BD00AA");
//		db.deletePPIDFromRepair01PASS("CN0J8CVMCMK007BD00AA");
	
		db.updateCurrentStation("aaa", "abb", "11111");
	}

}
