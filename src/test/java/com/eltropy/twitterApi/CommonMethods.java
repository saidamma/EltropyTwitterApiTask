package com.eltropy.twitterApi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


/**
 * 
 * @author saidammad
 *
 */

public class CommonMethods {
	
	
	
	/**
	 * 
	 * @param excelfileName
	 * @param sheetNameOrTestClassName
	 * @return
	 * @throws IOException
	 */
	
	public static int getRowCount(String excelfileName, String sheetNameOrTestClassName) throws IOException {

		String excelFloderPath = System.getProperty("user.dir") + File.separator;

		// Create an object of File class to open xlsx file

		File file = new File(excelFloderPath + "Excelfiles" + File.separator + excelfileName);

		// Create an object of FileInputStream class to read excel file
		FileInputStream inputStream = new FileInputStream(file);

		Workbook workbook = null;

		// Find the file extension by splitting file name in substring and getting only
		// extension name

		String fileExtensionName = excelfileName.substring(excelfileName.indexOf("."));

		// Check condition if the file is xlsx file

		if (fileExtensionName.equals(".xlsx")) {
			// If it is xlsx file then create object of XSSFWorkbook class
			workbook = new XSSFWorkbook(inputStream);

		}
		// Check condition if the file is xls file
		else if (fileExtensionName.equals(".xls")) {

			// If it is xls file then create object of HSSFWorkbook class
			workbook = new HSSFWorkbook(inputStream);
		}

		// Read sheet inside the workbook by its name
		Sheet testSheet = workbook.getSheet(sheetNameOrTestClassName);

		// get the data row from sheet
		int numberOfRows = testSheet.getLastRowNum();
		return numberOfRows;
	}
	
	
	/**
	 * This Method used to take screenshot of the flow
	 * @param driver
	 * @param screenshotName
	 * @throws IOException
	 */

	public String screenshotCapture(WebDriver driver,String screenshotName) throws IOException {
		TakesScreenshot scrShot = ((TakesScreenshot) driver);

		 String screenshotFloderPath = System.getProperty("user.dir") + File.separator+"target"+File.separator;
		// Call getScreenshotAs method to create image file

		screenshotFloderPath = screenshotFloderPath + "screenshots" + File.separator;
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String path=screenshotFloderPath + screenshotName + formatter.format(date) + ".png";
		File DestFile = new File(screenshotFloderPath + screenshotName + formatter.format(date) + ".png");

		FileUtils.copyFile(SrcFile, DestFile);
		return path;

	}
	
	/**
	 * 
	 * @param excelfileName
	 * @param sheetNameOrTestClassName
	 * @param rowNumber
	 * @throws IOException
	 */
	public static HashMap<String, String> readDataFromExcelSheetForSpecificRow(String excelfileName,
			String sheetNameOrTestClassName, int rowNumber) throws IOException {

		String excelFloderPath = System.getProperty("user.dir") + File.separator;

		// Create an object of File class to open xlsx file

		File file = new File(excelFloderPath + "Excelfiles" + File.separator + excelfileName);

		// Create an object of FileInputStream class to read excel file
		FileInputStream inputStream = new FileInputStream(file);

		Workbook workbook = null;

		// Find the file extension by splitting file name in substring and getting only
		// extension name

		String fileExtensionName = excelfileName.substring(excelfileName.indexOf("."));

		// Check condition if the file is xlsx file

		if (fileExtensionName.equals(".xlsx")) {
			// If it is xlsx file then create object of XSSFWorkbook class
			workbook = new XSSFWorkbook(inputStream);

		}
		// Check condition if the file is xls file
		else if (fileExtensionName.equals(".xls")) {

			// If it is xls file then create object of HSSFWorkbook class
			workbook = new HSSFWorkbook(inputStream);
		}

		// Read sheet inside the workbook by its name
		Sheet testSheet = workbook.getSheet(sheetNameOrTestClassName);

		// get the top column name row from sheet
		Row colRow = testSheet.getRow(0);

		// get the data row from sheet
		Row row = testSheet.getRow(rowNumber);

		HashMap<String, String> rowData = new HashMap<>();

		// Create a loop to get all cell values in a row
		for (int j = 0; j < row.getLastCellNum(); j++) {

			// Print Excel data in console
			if (row.getCell(j).getCellType().toString() == "STRING")
				rowData.put(colRow.getCell(j).getStringCellValue(), row.getCell(j).getStringCellValue());
			else if (row.getCell(j).getCellType().toString() == "NUMERIC")
				rowData.put(colRow.getCell(j).getStringCellValue(),
						String.valueOf(row.getCell(j).getNumericCellValue()));
			else
				rowData.put(colRow.getCell(j).getStringCellValue(),
						String.valueOf(row.getCell(j).getBooleanCellValue()));
		}
		return rowData;
	}
	/**
	 * 
	 * @param excelfileName
	 * @param sheetNumber
	 * @param rowNumber
	 * @param cellNumber
	 * @param data
	 * @throws IOException
	 */
	public static void writeDataFromExcelSheetForSpecificRow(String excelfileName,
			String sheetNameOrTestClassName, int rowNumber,int cellNumber,String data) throws IOException {
		
		
		String excelFloderPath = System.getProperty("user.dir") + File.separator;

		// Create an object of File class to open xlsx file

		File file = new File(excelFloderPath + "Excelfiles" + File.separator + excelfileName);

		// Create an object of FileInputStream class to read excel file
		FileInputStream fsIP = new FileInputStream(file);
		//Read the spreadsheet that needs to be updated
		Workbook workbook =null;

		String fileExtensionName = excelfileName.substring(excelfileName.indexOf("."));
		if (fileExtensionName.equals(".xlsx")) {
			// If it is xlsx file then create object of XSSFWorkbook class
			workbook = new XSSFWorkbook(fsIP);

		}
		// Check condition if the file is xls file
		else if (fileExtensionName.equals(".xls")) {

			// If it is xls file then create object of HSSFWorkbook class
			workbook = new HSSFWorkbook(fsIP);
		}
		//Access the workbook                  
		
		//Access the worksheet, so that we can update / modify it. 
		
		// Read sheet inside the workbook by its name
				Sheet testSheet = workbook.getSheet(sheetNameOrTestClassName);
		// declare a Cell object
		Cell cell = null; 
		// Access the second cell in second row to update the value
		cell = testSheet.getRow(rowNumber).getCell(cellNumber);   
		// Get current cell value value and overwrite the value
		cell.setCellValue(data);
		//Close the InputStream  
		fsIP.close(); 
		//Open FileOutputStream to write updates
		FileOutputStream output_file =new FileOutputStream(file);  
		 //write changes
		workbook.write(output_file);
		//close the stream
		output_file.close();
		
	}
	

	/**
	 * This method used to read data from Excel file
	 * @param excelfileName
	 * @param sheetNameOrTestClassName
	 * @return
	 * @throws IOException
	 */
	
	public static String[][] readAllRowsDataFromExcelSheet(String excelfileName, String sheetNameOrTestClassName)
			throws IOException {
		
		String excelFloderPath = System.getProperty("user.dir") + File.separator;

		// Create an object of File class to open xlsx file

		File file = new File(excelFloderPath + "Excelfiles" + File.separator + excelfileName);

		// Create an object of FileInputStream class to read excel file
		FileInputStream inputStream = new FileInputStream(file);

		Workbook workbook = null;

		// Find the file extension by splitting file name in substring and getting only
		// extension name

		String fileExtensionName = excelfileName.substring(excelfileName.indexOf("."));

		// Check condition if the file is xlsx file

		if (fileExtensionName.equals(".xlsx")) {
			// If it is xlsx file then create object of XSSFWorkbook class
			workbook = new XSSFWorkbook(inputStream);

		}
		// Check condition if the file is xls file
		else if (fileExtensionName.equals(".xls")) {

			// If it is xls file then create object of HSSFWorkbook class
			workbook = new HSSFWorkbook(inputStream);
		}

		// Read sheet inside the workbook by its name
		Sheet testSheet = workbook.getSheet(sheetNameOrTestClassName);
		int rowCount = testSheet.getLastRowNum();

		// get the top column name row from sheet
		Row colNameRow = testSheet.getRow(0);

		int colCount = colNameRow.getLastCellNum();

		String[][] arrayExcelData = new String[rowCount][colNameRow.getLastCellNum()];

		// looping all the rows data
		for (int i = 1; i <= rowCount; i++) {

			Row dataRow = testSheet.getRow(i);
			// Create a loop to get all cell values in a row
			for (int j = 0; j < colCount; j++) {

				if (dataRow.getCell(j).getCellType().toString() == "STRING")
					arrayExcelData[i - 1][j] = dataRow.getCell(j).getStringCellValue();
				else if (dataRow.getCell(j).getCellType().toString() == "NUMERIC")
					arrayExcelData[i - 1][j] = String.valueOf(dataRow.getCell(j).getNumericCellValue());
				else
					arrayExcelData[i - 1][j] = String.valueOf(dataRow.getCell(j).getBooleanCellValue());
			}
		}
		return arrayExcelData;
	}
	
	 public void writeFile(String fileData, String fileName) throws IOException {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false));
			writer.append(' ');
			writer.append(fileData);
			writer.close();
		}
	 public String buildTable(List<String[]> rows) {
			StringBuilder allRows = new StringBuilder(10000);
			for (String[] row : rows) {
				String htmlRow = String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>", row[0],
						row[1], row[2], row[3], row[4]);
				allRows.append(htmlRow);
			}
			return allRows.toString();
		}
	
	 
	
	 public String readFromInputStream(InputStream inputStream) throws IOException {
			StringBuilder resultStringBuilder = new StringBuilder();
			try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
				String line;
				while ((line = br.readLine()) != null) {
					resultStringBuilder.append(line).append("\n");
				}
			}
			return resultStringBuilder.toString();
		}


}
