package com.bizcom.services;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bizcom.database.DBHandler;
import com.bizcom.excel.ExcelService;
import com.bizcom.packingslip.PackingSlip;
import com.bizcom.ppid.PPID;

public class ExcelValidation {

	private ExcelService excel;
	DBHandler dbHandler = new DBHandler();

	public ExcelValidation() {
		excel = new ExcelService();
	}

	public int prealertValidation(String pathFile) throws IOException, ClassNotFoundException, SQLException {

		if (pathFile == null) {
			System.out.println();
			return 0;
		}
		if (!new File(pathFile).exists()) {
			return 0;
		}

		// Create an ExcelService to read file
		excel.read(pathFile);
		List<PPID> ppids = excel.getListOfRowPPID();

		// Scan Packing List to a Set
		Map<String, Integer> mapPL = new HashMap<>();
		List<PackingSlip> packingSlips = excel.getListOfRowPackingSlip();
		packingSlips.remove(packingSlips.size() - 1);

		for (PackingSlip pl : packingSlips) {

			String temp = pl.getPartNumber() + pl.getPoNumber() + pl.getLotNumber();
			if (!mapPL.containsKey(temp)) {
				mapPL.put(temp, pl.getQuality());
			} else {
				return 0;
			}
		}

		Map<String, Integer> mapPPIDs = new HashMap<>();

		for (PPID p : ppids) {
			String temp = p.getPnNumber() + p.getCoNumber() + p.getLotNumber();
			if (!mapPPIDs.containsKey(temp)) {
				mapPPIDs.put(temp, 1);
			} else {
				mapPPIDs.put(temp, mapPPIDs.get(temp) + 1);
			}
		}
		if (!mapPL.equals(mapPPIDs)) {
			return 0;
		}
		boolean testInDB = dbHandler.isRecordPreAlertExist(ppids);

		if (testInDB) {
			return 2;
		}

		if (mapPL.equals(mapPPIDs) && !testInDB)
			return 1;
		else 
			return 0;

	}

	public Map<String, Integer> getError(String pathFile) throws IOException, ClassNotFoundException, SQLException {
		if (prealertValidation(pathFile) == 1) {
			// Create an ExcelService to read file
			excel.read(pathFile);
			return null;

		} else {
			return null;
		}
	}

}
