package com.bizcom.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bizcom.packingslip.PackingSlip;
import com.bizcom.ppid.PPID;

public class ExcelService {

	//	private static final Logger LOGGER = LogManager.getLogger(ExcelService.class);
	private Workbook myWorkBook;
	private List<PackingSlip> listOfRow;
	private List<PPID> listPPID;
	private ApplicationPath currentpath;
	// === CONSTANT VARIABLE BASED ON EXCEL FILE
	//	private final int COL_NUMBER = 6;
	//	private static final String PO_PATTERN = "^(CMP-)[^\\s.]{5}$";
	private static int PN_COL_NUM;
	private static int PO_COL_NUM;
	private static int LOT_COL_NUM;
	private static int QTY_COL_NUM;
	private static int RMA_COL;

	private static int PN_COL_NUM_PPID;
	private static int PPID_COL_NUM_PPID;
	private static int CO_COL_NUM_PPID;
	private static int SYS_DATE_COL_NUM_PPID;
	private static int LOT_COL_NUM_PPID;
	private static int DPS_COL_NUM_PPID;
	private static int PROPBLEM_CODE_COL_NUM_PPID;
	private static int PROPBLEM_DES_COL_NUM_PPID;

	public ExcelService() {
		currentpath = new ApplicationPath();
		myWorkBook = null;
		listOfRow = new ArrayList<>();
		listPPID = new ArrayList<>();
	}

	public Workbook getWorkbook() {
		return this.myWorkBook;
	}

	public List<PackingSlip> getListOfRowPackingSlip() {
		this.listOfRow.clear();
		getAllPackingSlip();
		return this.listOfRow;
	}

	public List<PPID> getListOfRowPPID() {
		this.listPPID.clear();
		getAllRowsPPID();
		return this.listPPID;
	}

	public void getAllRowsPPID() {
		Sheet sheetOne = myWorkBook.getSheetAt(1);
		DataFormatter formatter = new DataFormatter();
		int count = 0;
		for (Row row : sheetOne) {
			if (count < 1) {
				count++;
				int index = 0;
				for (Cell c : row) {
					String temp = formatter.formatCellValue(c);
					if (temp.equalsIgnoreCase("pn")) {
						PN_COL_NUM_PPID = index;
					} else if (temp.equalsIgnoreCase("ppid#")) {
						PPID_COL_NUM_PPID = index;
					} else if (temp.equalsIgnoreCase("co#")) {
						CO_COL_NUM_PPID = index;
					} else if (temp.equalsIgnoreCase("SYS-DATE-REC")) {
						SYS_DATE_COL_NUM_PPID = index;
					} else if (temp.equalsIgnoreCase("LOT#")) {
						LOT_COL_NUM_PPID = index;
					} else if (temp.equalsIgnoreCase("DPS#")) {
						DPS_COL_NUM_PPID = index;
					} else if (temp.equalsIgnoreCase("PROBLEM-CODE")) {
						PROPBLEM_CODE_COL_NUM_PPID = index;
					} else if (temp.equalsIgnoreCase("PROBLEM-DESC")) {
						PROPBLEM_DES_COL_NUM_PPID = index;
					}
					index++;
				}
			} else {
				String pn = row.getCell(PN_COL_NUM_PPID).getStringCellValue();
				String ppid = row.getCell(PPID_COL_NUM_PPID).getStringCellValue();
				String co = row.getCell(CO_COL_NUM_PPID).getStringCellValue();
				String sys_date_rec = row.getCell(SYS_DATE_COL_NUM_PPID).toString();
				String lot = row.getCell(LOT_COL_NUM_PPID).getStringCellValue();
				String dps = row.getCell(DPS_COL_NUM_PPID).getStringCellValue();
				String problem_code = row.getCell(PROPBLEM_CODE_COL_NUM_PPID).getStringCellValue();
				String problem_desc = row.getCell(PROPBLEM_DES_COL_NUM_PPID).toString();

				listPPID.add(new PPID(pn, ppid, co, sys_date_rec, lot, dps, problem_code, problem_desc));
			}

		}

	}

	public void getAllPackingSlip() {
		Sheet sheetOne = myWorkBook.getSheetAt(0);
		DataFormatter formatter = new DataFormatter();
		int count = 0;
		for (Row row : sheetOne) {
			if (count < 1) {
				int index = 1;
				for (Cell c : row) {
					String temp = formatter.formatCellValue(c);
					if (temp.equalsIgnoreCase("PN")) {
						PN_COL_NUM = index;
					} else if (temp.equalsIgnoreCase("PO#")) {
						PO_COL_NUM = index;

					} else if (temp.equalsIgnoreCase("LOT#")) {
						LOT_COL_NUM = index;
					}else if (temp.equalsIgnoreCase("QTY")) {
						QTY_COL_NUM = index;
						count++;
					} else if (temp.equalsIgnoreCase("RMA#")) {
						RMA_COL = index;
					}
						index++;
				}
			
			} else {	
				String pn = row.getCell(PN_COL_NUM).getStringCellValue();
				String po = row.getCell(PO_COL_NUM).getStringCellValue();
				String lot = row.getCell(LOT_COL_NUM).getStringCellValue();
				int qty = (int) row.getCell(QTY_COL_NUM).getNumericCellValue();

				String rMANumber = row.getCell(RMA_COL).getStringCellValue()!=null?row.getCell(RMA_COL).getStringCellValue():"";
				PackingSlip temp = new PackingSlip(pn, po, lot, qty, rMANumber);
				listOfRow.add(temp);			
			}

		}

	}


	//	public void getAllRowsPackingSlip() {
	//		Sheet sheetOne = myWorkBook.getSheetAt(0);
	//		for (Row row : sheetOne) {
	//			if (row.getPhysicalNumberOfCells() == COL_NUMBER
	//					&& row.getCell(2).getRichStringCellValue().toString().matches(PO_PATTERN)) {
	//
	//				String partNumber = getCellValueObject(row.getCell(PN_COL_NUM)).toString();
	//				String poNumber = getCellValueObject(row.getCell(PO_COL_NUM)).toString();
	//				String lotNumber = getCellValueObject(row.getCell(LOT_COL_NUM)).toString();
	//				int quality = (int) row.getCell(QTY_COL_NUM).getNumericCellValue();
	//
	//
	//			}
	//
	//		}
	//
	//	}

	public List<Sheet> getAllSheet(Workbook wb) {
		ArrayList<Sheet> listOfSheets = new ArrayList<>();
		for (Sheet sheet : wb) {
			if (sheet != null) {
				listOfSheets.add(sheet);
			}
		}

		return listOfSheets;
	}

	public void close() throws IOException {
		myWorkBook.close();
	}

	/**
	 * read will read an excel file with path given
	 * 
	 * @param path
	 * @throws IOException
	 */
	public void read(String path) throws IOException {

		myWorkBook = null;
		listOfRow.clear();
		try {
			myWorkBook = WorkbookFactory.create(new FileInputStream(path));
		} catch (IOException e) {
			e.printStackTrace();
			//			LOGGER.error("Fail to read excel file {}", e.getMessage());
		}
		
		
	}

	public void writeRMA(String RMA) {
		Sheet sheetOne = myWorkBook.getSheetAt(0);
		DataFormatter formatter = new DataFormatter();
		int count = 0;
		for (Row row : sheetOne) {
			if (count < 1) {
				int index = 0;
				for (Cell c : row) {
					String temp = formatter.formatCellValue(c);
					if (temp.equalsIgnoreCase("RMA#")) {
						RMA_COL = index;
						count++;
					}else {
						index++;
					}

				}
			} else {
				row.getCell(RMA_COL).setCellValue(RMA);
			}

		}
		try {
			myWorkBook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean writeToSheet(List<List<String>> listdata, Sheet whatSheet) {
		boolean result = false;
		if (listdata.size() < 1)
			return false;
		int rowNumber = listdata.size();
		int colNumber = listdata.get(0).size();

		// Initial and Style Row 0 (base row)
		Row rowZero = whatSheet.createRow(0);
		for (int i = 0; i < colNumber; i++) {
			rowZero.createCell(i).setCellValue(listdata.get(i).get(i));
		}

		return result;
	}

	public boolean writeNewFile(String fileName, boolean isXSSFWorkbook) {
		boolean result = false;
		Workbook wb = null;
		// Create a new Workbook
		if (isXSSFWorkbook) {
			wb = new XSSFWorkbook();
		} else {
			wb = new HSSFWorkbook();
		}
		Sheet sheet1 = wb.createSheet("Sheet 0111");
		Object[][] datatypes = { { "Datatype", "Type", "Size(in bytes)" }, { "int", "Primitive", 2 },
				{ "float", "Primitive", 4 }, { "double", "Primitive", 8 }, { "char", "Primitive", 1 },
				{ "String", "Non-Primitive", "No fixed size" } };

		int rowNum = 0;

		for (Object[] datatype : datatypes) {
			Row row = sheet1.createRow(rowNum++);
			int colNum = 0;
			for (Object field : datatype) {
				Cell cell = row.createCell(colNum++);
				if (field instanceof String) {
					cell.setCellValue((String) field);
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
				}
			}
		}

		// Write to a new file with fileName above
		String afileName = "";
		try {
			OutputStream fileOut = null;
			afileName = currentpath.getPath() + fileName;
			if (isXSSFWorkbook) {
				fileOut = new FileOutputStream(afileName + ".xlsx");
			} else {
				fileOut = new FileOutputStream(afileName + ".xls");
			}

			if (fileOut != null) {
				wb.write(fileOut);
				result = true;
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				wb.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		return result;
	}

	public Hyperlink getHyperlink(Cell aCell) {
		return aCell == null ? null : aCell.getHyperlink();
	}

	public RichTextString getCellValueRichText(Cell aCell) {
		return aCell == null ? null : aCell.getRichStringCellValue();
	}

	public Date getCellAsDateObject(Cell aCell) {
		if (aCell != null)
			try {
				return aCell.getDateCellValue();
			} catch (final RuntimeException ex) {
				// fall through
				//				LOGGER.warn("Failed to get cell value as date: " + ex.getMessage());
			}
		return null;
	}

	public Number getAsNumberObject(double dValue) {
		if (dValue == (int) dValue) {
			// It's not a real double value, it's an int value
			return Integer.valueOf((int) dValue);
		}
		if (dValue == (long) dValue) {
			// It's not a real double value, it's a long value
			return Long.valueOf((long) dValue);
		}
		// It's a real floating point number
		return Double.valueOf(dValue);
	}

	public Object getCellValueObject(Cell aCell) {
		if (aCell == null)
			return null;

		final CellType eCellType = aCell.getCellType();
		switch (eCellType) {
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(aCell)) {
				return getCellAsDateObject(aCell).toString();
			} else {
				return getAsNumberObject(aCell.getNumericCellValue());
			}
		case STRING:
			return getCellValueRichText(aCell).getString();
		case BOOLEAN:
			return aCell.getBooleanCellValue();
		case FORMULA:
			return aCell.getCellFormula();
		case BLANK:
			return null;
		default:
			throw new IllegalArgumentException("The cell type " + eCellType + " is unsupported!");
		}
	}
}
