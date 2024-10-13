package com.go.denys.selenium.automatization.linkedin.jobs.scaner.excel;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ExcelWriter {

    private final List<List<String>> data;
    private final List<String> headers;
    private final String path;
    private int row;
    private final String sheetName;

    private static Workbook workbook;
    private static CreationHelper creationHelper;
    private static CellStyle headerStyle;
    private static CellStyle linkStyle;
    private static CellStyle style;


    public ExcelWriter(List<List<String>> data, List<String> headers, String path, String sheetName) {
        this.data = data;
        this.headers = headers;
        this.path = path;
        this.sheetName = sheetName;
        row = 0;
    }

    public void initializeStyles() {
        creationHelper = workbook.getCreationHelper();

        headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        style = workbook.createCellStyle();
        style.setWrapText(true);

        linkStyle = workbook.createCellStyle();
        Font linkFont = workbook.createFont();
        linkFont.setUnderline(Font.U_SINGLE);
        linkFont.setColor(IndexedColors.BLUE.getIndex());
        linkStyle.setFont(linkFont);
    }

    public void write() {
        File file = new File(path);
        Sheet sheet;
        try {
            if (file.exists()) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    workbook = new XSSFWorkbook(fis);
                    sheet = workbook.getSheet(sheetName);
                    if (sheet == null) {
                        sheet = workbook.createSheet(sheetName);
                    }
                }
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet(sheetName);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to open the Excel file.", e);
        }

        initializeStyles();

        writeHeader(sheet);
        writeRows(sheet);

        saveFile();
    }

    private void writeRows(Sheet sheet) {
        for (List<String> datum : data) {
            Row r = sheet.createRow(row++);
            for (int j = 0; j < datum.size(); j++) {
                Cell cell = r.createCell(j);
                cell.setCellValue(datum.get(j));
                cell.setCellStyle(style);
                if (j == 5) {
                    Hyperlink hyperlink = creationHelper.createHyperlink(HyperlinkType.URL);
                    hyperlink.setAddress(datum.get(j));
                    cell.setHyperlink(hyperlink);
                    cell.setCellStyle(linkStyle);
                }
            }
        }
    }

    private void writeHeader(Sheet sheet) {
        Row header = sheet.createRow(row++);
        for (int j = 0; j < headers.size(); j++) {
            Cell headerCell = header.createCell(j);
            headerCell.setCellValue(headers.get(j));
            headerCell.setCellStyle(headerStyle);
        }
        sheet.setColumnWidth(0, 30 * 256);
        sheet.setColumnWidth(1, 30 * 256);
        sheet.setColumnWidth(2, 30 * 256);
        sheet.setColumnWidth(3, 10 * 256);
        sheet.setColumnWidth(4, 10 * 256);
        sheet.setColumnWidth(5, 100 * 256);
    }

    private void saveFile() {
        try (FileOutputStream fileOut = new FileOutputStream(path)) {
            workbook.write(fileOut);
            workbook.close();
        } catch (IOException e) {
            System.out.println("The file is already open. Please close the file and press Enter to continue...");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine(); // Waiting for input from the user

            saveFile();
        }
    }
}
