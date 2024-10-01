package com.go.denys.selenium.automatization.linkedin.jobs.scaner.excel;

import lombok.AllArgsConstructor;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelWriter {

    private List<List<String>> data;
    private List<String> headers;
    private String path;
    private int row;
    private String sheetName;

    private static final Workbook workbook;
    private static final CreationHelper creationHelper;
    private static final CellStyle headerStyle;
    private static final CellStyle linkStyle;
    private static final CellStyle style;


    public ExcelWriter(List<List<String>> data, List<String> headers, String path, String sheetName) {
        this.data = data;
        this.headers = headers;
        this.path = path;
        this.sheetName = sheetName;
        row = 0;
    }

    static {
        workbook = new XSSFWorkbook();
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
        Sheet sheet = workbook.createSheet(sheetName);

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
            e.printStackTrace();
        }
    }
}
