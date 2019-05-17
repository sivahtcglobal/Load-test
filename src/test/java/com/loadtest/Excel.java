package com.loadtest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel extends Baseclass {

	private static final Logger log = LogManager.getLogger(Excel.class.getName());

	private static Workbook wb;
	private static Sheet sh;
	private static XSSFCell col = null;
	private static Row row;
	public static FileInputStream f = null;
	public static HashMap<String, String> currentHash = new HashMap<String, String>();
	public static List<HashMap<String, String>> mydata = new ArrayList<HashMap<String, String>>();

	public static String getExceldata(String excelFileName, String sheetname, int colnum, int rownum) {
		try {

			File file = new File(System.getProperty("user.dir") + "\\src\\test\\resources\\TestData.xlsx");
			f = new FileInputStream(file);
			wb = new XSSFWorkbook(f);
			sh = wb.getSheet(sheetname);
			row = sh.getRow(rownum);
			col = (XSSFCell) row.getCell(colnum);
			return col.toString();
			// System.out.println(col.get);
		} catch (Exception e) {
			return sh + "Not Exist";
		}
	}

	public static boolean writeToExcelSheet(String avgLoadTime, Double url, int executionTimes)
			throws FileNotFoundException, IOException {
		String fileName = System.getProperty("user.dir") + "\\src\\test\\resources\\TestData.xlsx";
		String sheetName = "AvgLoadTimeData";
		FileInputStream file = new FileInputStream(fileName);
		wb = new XSSFWorkbook(file);
		// HSSFWorkbook workbook = new HSSFWorkbook(file);
		sh = wb.getSheet(sheetName);

		int rowCount = sh.getLastRowNum() + 1;
		Row empRow = sh.createRow(rowCount);

		Cell c1 = empRow.createCell(0);
		c1.setCellValue(url);
		Cell c2 = empRow.createCell(1);
		c2.setCellValue(avgLoadTime);
		Cell c3 = empRow.createCell(2);
		c3.setCellValue(executionTimes);
		file.close();

		FileOutputStream outFile = new FileOutputStream(fileName);
		wb.write(outFile);
		outFile.close();
		return true;

	}

	public static List<HashMap<String, String>> readSpecificTestData( String testCaseId)
			throws IOException {
	    String fileName = System.getProperty("user.dir") + "\\src\\test\\resources\\TestData.xlsx";
	    String sheetName= "TestCaseRun";
		File file = new File(fileName);
		wb = new XSSFWorkbook(new FileInputStream(file));

		sh = wb.getSheet(sheetName);

		Row HeaderRow = sh.getRow(0);

		for (int i = 1; i < sh.getPhysicalNumberOfRows(); i++) {
			Row currentRow = sh.getRow(i);
			
			if (testCaseId.equalsIgnoreCase(currentRow.getCell(0).getStringCellValue())) {
				
				for (int j = 0; j < currentRow.getPhysicalNumberOfCells(); j++) {
					Cell currentCell = currentRow.getCell(j);
					switch (currentCell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						currentHash.put(HeaderRow.getCell(j).getStringCellValue(), currentCell.getStringCellValue());
						break;
					case Cell.CELL_TYPE_NUMERIC:
						currentHash.put(HeaderRow.getCell(j).getStringCellValue(),
								String.valueOf(currentCell.getNumericCellValue()));
						break;
					}

				}
				mydata.add(currentHash);
				//System.out.println("Username" + currentHash.get("Username"));

			}
		}
		return mydata;
	}

}