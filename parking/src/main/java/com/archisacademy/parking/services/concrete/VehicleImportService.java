package com.archisacademy.parking.services.concrete;

import com.archisacademy.parking.repositories.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class VehicleImportService {

    private final VehicleRepository vehicleRepository;
    private final CsvImportService csvImportService;
    private final ExcelImportService excelImportService;

    public VehicleImportService(VehicleRepository vehicleRepository,
                                CsvImportService csvImportService, ExcelImportService excelImportService) {
        this.vehicleRepository = vehicleRepository;
        this.csvImportService = csvImportService;
        this.excelImportService = excelImportService;
    }

    public void importFile(MultipartFile file) throws IOException {
        String fileType = getFileExtension(file);
        if ("csv".equalsIgnoreCase(fileType)) {
            csvImportService.parse(file);
        } else if ("xlsx".equalsIgnoreCase(fileType)) {
            excelImportService.parse(file);
        } else {
            throw new UnsupportedOperationException("Unsupported file format");
        }
    }

    private String getFileExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return "";
    }
}
