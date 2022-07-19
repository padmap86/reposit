package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils extends TestBase {

	public FileInputStream fis = null;
	public XSSFWorkbook wb = null;
	public Sheet sheet = null;
	public Row row = null;
	public Cell cell = null;

	public ExcelUtils() {
		// Create an object of File class to open xlsx file

		File file = new File("./src/test/resources/TestData/testdata.xlsx");

		// Create an object of FileInputStream class to read excel file

		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			wb = new XSSFWorkbook(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// To get no of Rows
	public int getNoofRow(String sheetname) throws EncryptedDocumentException, InvalidFormatException, IOException {

		int Data = wb.getSheet(sheetname).getLastRowNum();
		System.out.println(Data + 1);
		return Data + 1;

	}

	// Read sheet inside the workbook by its name
	public String getdata(String sheetname, String columnname, int rownum) throws IOException {
		int colnum = 0;

		sheet = wb.getSheet(sheetname);
		row = sheet.getRow(0);

		for (int i = 0; i < row.getLastCellNum(); i++) {
			String colvalue = row.getCell(i).getStringCellValue();
			if (colvalue.trim().equals(columnname.trim())) {
				colnum = i;
				break;
			}
		}
		row = sheet.getRow(rownum);
		cell = row.getCell(colnum);
		String value = null;

		if (cell.getCellType() == CellType.STRING)
			value = cell.getStringCellValue();
		else if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
			String cellValue = String.valueOf(cell.getNumericCellValue());
			value = cellValue;
		} else if (cell.getCellType() == CellType.BLANK)
			value = "";
		else
			return String.valueOf(cell.getBooleanCellValue());

		wb.close();
		return value;

	}

	// To write result in sheet
	public void writeExcel(String sheetname, String value, String rowname, int colnum) throws IOException {

		File file1 = new File("./src/test/resources/TestData/resultstatus.xlsx");
		FileInputStream fis1 = new FileInputStream(file1);
		XSSFWorkbook wb1 = new XSSFWorkbook(fis1);
		Sheet sheet = wb1.getSheet(sheetname);
		// Find number of rows in excel file
		fis1.close();
		int rowCount = sheet.getLastRowNum();
		System.out.println(rowCount);

		// Create a loop over all the rows of excel file to read it

		int rownum = 0;
		for (int i = 0; i < rowCount + 1; i++) {
			Row row1 = sheet.getRow(i);
			String rowName = row1.getCell(0).getStringCellValue();
			if (rowName.trim().equals(rowname.trim())) {
				rownum = i;

				Cell cell = row1.createCell(colnum);
				cell.setCellValue(value);
				break;
			} // System.out.println();
		}

		FileOutputStream fos = new FileOutputStream(
				System.getProperty("user.dir") + File.separator + "/src/test/resources/TestData/resultstatus.xlsx");
		wb1.write(fos);
		wb1.close();
		fos.close();
		System.out.println("END OF WRITING DATA IN EXCEL");
	}

}
