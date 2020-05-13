package com.bizcom.database;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;    

public class test {

	public static void main(String[] args) throws ClassNotFoundException, ParseException, SQLException {
		//Class.forName("com.mysql.cj.jdbc.Driver");
		DBHandler db = new DBHandler();
		db.getConnectionAWS();

		String list = db.getInstruction("N6W51");
		System.out.println(list);
		System.out.println("**********************************");
		System.out.println();
		
//		//Get all details for all upgrades
//		Map<String, List<String>> instructionDetail = db.getInstructionDetailMap();
//		
//		//get first upgrade revision for partnumber N6W51:
//		System.out.println("First updrage " +list.get(0).toString());
//		System.out.println("************************************");
//		
//		
//		//getting details of the first upgrade
//		String location = instructionDetail.get(list.get(0).get(0)).get(0);
//		String detail = instructionDetail.get(list.get(0).get(0)).get(1);
//		String picture = instructionDetail.get(list.get(0).get(0)).get(2);
//		System.out.println("location: "+ location);
//		System.out.println("detail: "+ detail);
//		System.out.println("picture: "+ picture);
//		
//		try {
//			if(service()) {
//				System.out.println("pass");
//			}else {
//				System.out.println("fail");
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		}	
}
