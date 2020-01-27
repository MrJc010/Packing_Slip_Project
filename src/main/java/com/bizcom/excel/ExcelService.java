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
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bizcom.packingslip.PackingSlip;
import com.bizcom.path.ApplicationPath;

public class ExcelService {

//	private static final Logger LOGGER = LogManager.getLogger(ExcelService.class);
	private Workbook myWorkBook;
	private List<PackingSlip> listOfRow;
	private ApplicationPath currentpath;
	// === CONSTANT VARIABLE BASED ON EXCEL FILE
	private final int ROW_NUMBER = 6;
	private static final String PO_PATTERN = "^(CMP-)[^\\s.]{5}$";
	private static final int PN_COL_NUM = 2;
	private static final int PO_COL_NUM = 3;
	private static final int LOT_COL_NUM = 4;
	private static final int QTY_COL_NUM = 5;

	public ExcelService() {
		currentpath = new ApplicationPath();
		myWorkBook = null;
		listOfRow = new ArrayList<>();
	}

	public Workbook getWorkbook() {
		return this.myWorkBook;
	}

	public List<PackingSlip> getListOfRowPackingSlip() {
		this.listOfRow.clear();
		getAllRowsPackingSlip();
		return this.listOfRow;
	}

	
	public void getAllRowsPPID() {
		Sheet sheetOne = myWorkBook.getSheetAt(1);
		for (Row row : sheetOne) {
			if (row.getPhysicalNumberOfCells() == ROW_NUMBER
					&& row.getCell(2).getRichStringCellValue().toString().matches(PO_PATTERN)) {

				String partNumber = getCellValueObject(row.getCell(PN_COL_NUM)).toString();
				String poNumber = getCellValueObject(row.getCell(PO_COL_NUM)).toString();
				String lotNumber = getCellValueObject(row.getCell(LOT_COL_NUM)).toString();
				int quality = (int) row.getCell(QTY_COL_NUM).getNumericCellValue();
				String rMANumber = "";
				PackingSlip temp = new PackingSlip(partNumber, poNumber, lotNumber, quality, rMANumber);
				listOfRow.add(temp);

			}

		}

	}
	
	public void getAllRowsPackingSlip() {
		Sheet sheetOne = myWorkBook.getSheetAt(0);
//		System.out.println(sheetOne.getRow(sheetOne.getLastRowNum()).getCell(QTY_COL_NUM).getNumericCellValue());
//		System.out.println(sheetOne.getRow(sheetOne.getLastRowNum()).getCell(1).getNumericCellValue());
		for (Row row : sheetOne) {
			if (row.getPhysicalNumberOfCells() == ROW_NUMBER
					&& row.getCell(2).getRichStringCellValue().toString().matches(PO_PATTERN)) {

				String partNumber = getCellValueObject(row.getCell(PN_COL_NUM)).toString();
				String poNumber = getCellValueObject(row.getCell(PO_COL_NUM)).toString();
				String lotNumber = getCellValueObject(row.getCell(LOT_COL_NUM)).toString();
				int quality = (int) row.getCell(QTY_COL_NUM).getNumericCellValue();
				String rMANumber = "";
				PackingSlip temp = new PackingSlip(partNumber, poNumber, lotNumber, quality, rMANumber);
				listOfRow.add(temp);

			}

		}

	}

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
				return getCellAsDateObject(aCell);
			} else {
				return getAsNumberObject(aCell.getNumericCellValue());
			}
		case STRING:
			return getCellValueRichText(aCell);
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
