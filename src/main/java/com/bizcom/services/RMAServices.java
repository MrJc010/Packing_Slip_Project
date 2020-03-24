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
		DBHandler db = new DBHandler();		
		int count = db.getRMACount(result);
		return result + (count + 1);
	}

	public boolean addRMAToDB(String rma,String userId) {
		DBHandler db = new DBHandler();
		return db.createNewRMA(rma,userId);
	}

}
