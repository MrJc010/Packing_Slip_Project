package com.bizcom.services;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bizcom.excel.ExcelService;
import com.bizcom.packingslip.PackingSlip;
import com.bizcom.ppid.PPID;

public class ExcelValidation {

	private ExcelService excel;

	public ExcelValidation() {
		excel = new ExcelService();
	}

	public boolean prealertValidation(String pathFile) throws IOException {

		if (pathFile == null) {
			System.out.println();
			return false;
		}
		if (!new File(pathFile).exists()) {
			return false;
		}

		// Create an ExcelService to read file
		excel.read(pathFile);
		// Scan Packing List to a Set
		Map<String, Integer> mapPL = new HashMap<>();
		List<PackingSlip> packingSlips = excel.getListOfRowPackingSlip();
		packingSlips.remove(packingSlips.size() - 1);

		for (PackingSlip pl : packingSlips) {

			String temp = pl.getPartNumber() + pl.getPoNumber() + pl.getLotNumber();
			if (!mapPL.containsKey(temp)) {
				mapPL.put(temp, pl.getQuality());
			} else {
				return false;
			}
		}

		Map<String, Integer> mapPPIDs = new HashMap<>();
		List<PPID> ppids = excel.getListOfRowPPID();
		for (PPID p : ppids) {
			String temp = p.getPnNumber() + p.getCoNumber() + p.getLotNumber();
			if (!mapPPIDs.containsKey(temp)) {
				mapPPIDs.put(temp, 1);
			} else {
				mapPPIDs.put(temp, mapPPIDs.get(temp) + 1);
			}
		}

		return mapPL.equals(mapPPIDs);
	}

}
