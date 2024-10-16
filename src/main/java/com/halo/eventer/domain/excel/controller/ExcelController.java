package com.halo.eventer.domain.excel.controller;

import com.halo.eventer.domain.excel.service.ExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/excel")
public class ExcelController {
    private final ExcelService excelService;

    @GetMapping("/stamp/download")
    public void StampUserExcelDownload(HttpServletResponse response, @RequestParam("stampId") Long stampId) throws IOException {
        excelService.createStampUserSheet(response, stampId);
    }
}
