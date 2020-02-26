package com.bizcom.database;

import java.util.Arrays;

import com.bizcom.ppid.PPID;

public class test {

	public static void main(String[] args) throws ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		DBHandler db = new DBHandler();
		int x = db.fetchRMA("CN01NJ1T1296363B03C4", "342811065").size();
		
		System.out.println("xxx:  " + x);
		
		

	}

}
