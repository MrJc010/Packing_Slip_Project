package com.bizcom.database;

import java.util.HashSet;
import java.util.Set;

public class test {

	public static void main(String[] args) throws ClassNotFoundException {
		//Class.forName("com.mysql.cj.jdbc.Driver");
		DBHandler db = new DBHandler();
		db.getConnectionAWS();
		db.createInstruction();
		
		String maxRevesion = db.getMaxRevision("1DMJH");
		int maxRev = -1;
		maxRev= 	Integer.parseInt(maxRevesion.substring(maxRevesion.length()-(maxRevesion.length()-1)));
		System.out.println("maxRev" + maxRev);
		
//		db.updateRepair01RecordAction("111", "1111", "1111", "1111", "1111", "1111");
		
//		//map 1 has 3 keys
//		HashMap<Integer, String> map1 = new HashMap<>();
//		 
//		map1.put(1, "A");
//		map1.put(2, "B");
//		map1.put(3, "C");
//		 
//		//map 2 has 4 keys
//		HashMap<Integer, String> map2 = new HashMap<>();
//		 
//		map2.put(1, "A");
//		map2.put(2, "B");
//		map2.put(3, "C");
//		map2.put(4, "C");
		 
		//Union of keys from both maps
//		HashMap<Integer, String> def = new HashMap<>();
//		unionKeys.addAll(map2.keySet());
//		 
//		unionKeys.removeAll(map1.keySet());
//		 
//		System.out.println(unionKeys);
		
//		db.PhysicalReceive("BZM123", "012345", "CN0J8CVMCMK007BD00QQ", "CMP-PMH0T", "123456789", "Rev.056", "12345", "456343434", "12", "SR Online : networkconnection:Windows 10:", "PB3I", "654656565");
//		db.addMICI("CN0J8CVMCMK007BD00AA", "123456789");
//		db.addToStatusTable("CN0J8CVMCMK007BD00AA", "CN0J8CVMCMK007BD00AA", "A", "B");
		
//		db.deleteFromStatusTable("CN0J8CVMCMK007BD00AA");	
		
		
//		db.updateRepair01("CN0J8CVMCMK007BD00AA", "repair_action", "duty", "area1", "pn1_old", "pn1_new", "area2", "pn2_old", "pn2_new", "area3", "pn3_old", "pn3_new", "pn3_new", "pn4_old", "pn4_new", "area5", "pn5_old", "pn5_new", "status", "USER IDsss");
		
//		db.updateStatusInRepair01("CN0J8CVMCMK007BD00AA", "PASS");
//		db.copyFromRepair01ToRepair01Pass("CN0J8CVMCMK007BD00AA");
		
		
//		db.deletePPIDFromRepair01("CN0J8CVMCMK007BD00AA");
//		db.deletePPIDFromRepair01PASS("CN0J8CVMCMK007BD00AA");
	
//		db.updateCurrentStation("aaa", "abb", "11111");
	}

}
