package com.webapp.kuze.model.DBEntry;

import com.webapp.kuze.model.Product;
import com.webapp.kuze.service.ProductService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataExtract {

    private final ProductService productService;

    public DataExtract(ProductService productService) {
        this.productService = productService;
        addDataToDB();
    }


    void addDataToDB()
    {
        String excelPath = "C:\\Users\\Nikola\\Desktop\\Projects\\Java\\DataExtraction\\DataExtractionMaven\\src\\main\\resources\\MyGPM_VlezIzlezZel.xlsx"; // replace with actual path
        List<Product> items = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(excelPath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for(Row row : sheet) {
                if(row.getRowNum() == 0 || row.getPhysicalNumberOfCells() < 7) continue;

                Cell codeCell = row.getCell(0);
                Cell descriptionCell = row.getCell(1);
                Cell  unitOfMeasureCell = row.getCell(2);
                Cell priceCell = row.getCell(6);

                if(codeCell == null || descriptionCell == null || unitOfMeasureCell == null || priceCell == null) continue;

                String code = getCellValue(codeCell);
                if(code.equals("Код"))
                {
                    continue;
                }
                String description = getCellValue(descriptionCell);
                String unitOfMeasure = getCellValue(unitOfMeasureCell);
                Long codeLong = Long.parseLong(code);

                double price;

                if(priceCell.getCellType()== CellType.NUMERIC) {
                    price = Double.parseDouble(getCellValue(priceCell));
                }
                else price=0;

                items.add(new Product(codeLong,description,unitOfMeasure,price));
            }





        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        for (int i = 0; i < items.size(); i++) {
            Product product = items.get(i);
            this.productService.save(product.getCode(),product.getDescription(),product.getUnitOfMeasure(),product.getPrice());
        }
    }


    public static String getCellValue(Cell cell) {
        if(cell == null) return "";

        return switch (cell.getCellType())
        {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            case BLANK -> "";
            default -> "";
        };
    }

}

