package com.halo.eventer.domain.excel.service;

import com.halo.eventer.domain.stamp.dto.stampUser.FinishedStampUserDto;
import com.halo.eventer.domain.stamp.service.StampService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExcelService {

    private final StampService stampService;

    public void createStampUserSheet(HttpServletResponse response, Long stampId) throws IOException {
        // excel sheet 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");   // excel sheet 이름

        // 스타일 설정
        CellStyle[] styles = setSheetStyle(workbook, sheet);
        CellStyle headerXSSFCellStyle = styles[0];   // header 스타일
        CellStyle bodyXSSFCellStyle = styles[1];     // body 스타일

        List<FinishedStampUserDto> finishedUsers = stampService.getFinishedStampUsers(stampId);

        // header data
        List<String> headerNames = new ArrayList<>();
        Class<FinishedStampUserDto> finishedStampUserDtoClass = FinishedStampUserDto.class;
        for (Field field: finishedStampUserDtoClass.getDeclaredFields()) {
            headerNames.add(field.getName());
        }
        createHeader(sheet, headerNames, headerXSSFCellStyle);


        // body data
        List<List<String>> bodyData = new ArrayList<>();
        for (FinishedStampUserDto finishedStampUserDto: finishedUsers) {
            List<String> rowData = new ArrayList<>();
            rowData.add(finishedStampUserDto.getSchoolNo());
            rowData.add(finishedStampUserDto.getName());
            rowData.add(finishedStampUserDto.getPhone());

            bodyData.add(rowData);
        }
        createBody(sheet, bodyData, bodyXSSFCellStyle);

        // download
        String fileName = "stamp_tour_excel";

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream servletOutputStream = response.getOutputStream();

        workbook.write(servletOutputStream);
        workbook.close();
        servletOutputStream.flush();
        servletOutputStream.close();
    }

    private CellStyle[] setSheetStyle(Workbook workbook, Sheet sheet) {
        sheet.setDefaultColumnWidth(14);

        // header font style
        XSSFFont headerXSSFFont = (XSSFFont) workbook.createFont();
        headerXSSFFont.setColor(new XSSFColor(new byte[]{(byte) 255, (byte) 255, (byte) 255}));

        // header cell style
        XSSFCellStyle headerXSSFCellStyle = (XSSFCellStyle) workbook.createCellStyle();
        headerXSSFCellStyle.setAlignment(HorizontalAlignment.CENTER);

        // edge
        headerXSSFCellStyle.setBorderLeft(BorderStyle.THIN);
        headerXSSFCellStyle.setBorderRight(BorderStyle.THIN);
        headerXSSFCellStyle.setBorderTop(BorderStyle.THIN);
        headerXSSFCellStyle.setBorderBottom(BorderStyle.THIN);

        // background
        headerXSSFCellStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte) 0, (byte) 0, (byte) 0}));
        headerXSSFCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerXSSFCellStyle.setFont(headerXSSFFont);

        // body cell style
        XSSFCellStyle bodyXSSFCellStyle = (XSSFCellStyle) workbook.createCellStyle();

        // edge
        bodyXSSFCellStyle.setBorderLeft(BorderStyle.THIN);
        bodyXSSFCellStyle.setBorderRight(BorderStyle.THIN);
        bodyXSSFCellStyle.setBorderTop(BorderStyle.THIN);
        bodyXSSFCellStyle.setBorderBottom(BorderStyle.THIN);

        return new CellStyle[] { headerXSSFCellStyle, bodyXSSFCellStyle };
    }

    private void createHeader(Sheet sheet, List<String> headerNames, CellStyle headerXSSFCellStyle) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headerNames.size(); i++) {
            Cell headerCell = headerRow.createCell(i);
            headerCell.setCellValue(headerNames.get(i));    // 데이터 추가
            headerCell.setCellStyle(headerXSSFCellStyle);   // 스타일 추가
        }
    }

    private void createBody(Sheet sheet, List<List<String>> bodyData, CellStyle bodyXSSFCellStyle) {
        Row bodyRow;
        int count = 1;
        for (List<String> rowData: bodyData){
            bodyRow = sheet.createRow(count++);

            for(int i=0; i<rowData.size(); i++) {
                Cell bodyCell = bodyRow.createCell(i);
                bodyCell.setCellValue(rowData.get(i));      // 데이터 추가
                bodyCell.setCellStyle(bodyXSSFCellStyle);   // 스타일 추가
            }
        }
    }
}
