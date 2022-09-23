package com.example.file_convertor;

import com.example.file_convertor.entity.Game;
import com.google.gson.Gson;
import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.hamcrest.CoreMatchers.is;

@Slf4j
@SpringBootTest
class FileConvertorApplicationTests extends AbstractConfiguration{

    @Test
    void testPdf() throws IOException {
        try(ZipFile zipFile = new ZipFile(System.getProperty("user.dir") + "/src/main/resources/static/file.zip");) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                InputStream stream = zipFile.getInputStream(entry);
                if (entry.getName().contains(".pdf")) {
                    try (PDDocument pdfDocument = PDDocument.load(stream.readAllBytes())) {
                       String content = new PDFTextStripper().getText(pdfDocument);
                       MatcherAssert.assertThat(content.contains("A Simple PDF File"), is(true));
                    }
                }
            }
        }
    }

    @Test
    void testCSV() throws IOException {
        boolean isContain = false;
        List<String> test = List.of("1", "Dulce", "Abril", "Female", "United States", "32", "15/10/2017", "1562");
        List<List<String>> records = new ArrayList<>();
        try(ZipFile zipFile = new ZipFile(System.getProperty("user.dir") + "/src/main/resources/static/file.zip");) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                InputStream stream = zipFile.getInputStream(entry);
                if (entry.getName().contains(".csv")) {
                    try (CSVReader csvReader = new CSVReader(new InputStreamReader(stream))) {
                        String[] values;
                        while ((values = csvReader.readNext()) != null) {
                            records.add(Arrays.asList(values));
                        }
                        for (List<String> list: records){
                            if (list.equals(test)) {
                                log.info(Arrays.toString(list.toArray()) + " - list");
                                isContain = true;
                                break;
                            }
                        }
                        MatcherAssert.assertThat(isContain, is(true));
                    }
                }
            }
        }
    }

    @Test
    void testXLS() throws IOException {
        List<List<String>> records = new ArrayList<>();
        try(ZipFile zipFile = new ZipFile(System.getProperty("user.dir") + "/src/main/resources/static/file.zip");) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                InputStream stream = zipFile.getInputStream(entry);
                if (entry.getName().contains(".xls")) {
                    Workbook workbook = WorkbookFactory.create(stream);
                    Row row = workbook.getSheetAt(0).getRow(13);
                    MatcherAssert.assertThat(row.getCell(1).getStringCellValue(), is("Sherron"));
                    MatcherAssert.assertThat(row.getCell(2).getStringCellValue(), is("Ascencio"));
                    MatcherAssert.assertThat(Objects.equals(row.getCell(3).getStringCellValue(), "Female"), is(true));
                    MatcherAssert.assertThat(row.getCell(4).getStringCellValue().equals("Great Britain"), is(true));
                    MatcherAssert.assertThat(row.getCell(5).getNumericCellValue(), is(32.0));
                    MatcherAssert.assertThat(row.getCell(6).getStringCellValue().contains("16/08/2016"), is(true));
                    MatcherAssert.assertThat(row.getCell(7).getNumericCellValue(), is(3256.0));

                    }
                }
            }
        }

    @Test
    void testJSON() {
        Game game = new Game();
        game.setName("Halo");
        MatcherAssert.assertThat(fileController.convertObjectToJson(game).getBody(), is(new Gson().toJson(game)));
    }


}
