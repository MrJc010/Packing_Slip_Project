package com.bizcom.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.bizcom.database.DBHandler;

public class RMAServices {

//	private String pattern;
	
	public RMAServices() {

	}

//	public RMAServices(String pattern) {
//		this.pattern = pattern;
//	}

	public String generatorRMA() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
		String result = "BZM" + format.format(date);
		System.out.println("pattern  " + result);
		DBHandler db = new DBHandler();		
		int count = db.getRMACount(result);
		System.out.println("Count : " + count);
		return result + (count + 1);
	}

	public boolean addRMAToDB(String rma) {
		DBHandler db = new DBHandler();
		return db.createNewRMA(rma);
	}

}
