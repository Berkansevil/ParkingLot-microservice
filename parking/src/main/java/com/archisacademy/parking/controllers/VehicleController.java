package com.archisacademy.parking.controllers;

import com.archisacademy.parking.ApiResponse.ApiResponse;
import com.archisacademy.parking.dtos.request.VehicleRequest;
import com.archisacademy.parking.dtos.request.VehicleUpdateRequest;
import com.archisacademy.parking.dtos.response.ParkingReservationResponse;
import com.archisacademy.parking.dtos.response.VehicleResponse;
import com.archisacademy.parking.services.abstracts.VehicleService;
import com.archisacademy.parking.services.concrete.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/vehicles")
@Tag(name = "Vehicle", description = "API endpoints for managing vehicles, including creation, update, deletion, retrieval, and listing of vehicle records.")
public class VehicleController {

    private final VehicleService vehicleService;
    private final VehicleImportService vehicleImportService;
    private final ExcelImportService excelImportService;
    private final CsvImportService csvImportService;
    private final CsvExporterService csvExporterService;
    private final Logger logger = LoggerFactory.getLogger(VehicleController.class);
    private final ExcelExporterService excelExporterService;

    public VehicleController(VehicleService vehicleService, ExcelImportService excelImportService,
                             CsvImportService csvImportService, VehicleImportService vehicleImportService, CsvExporterService csvExporterService, ExcelExporterService excelExporterService) {
        this.vehicleService = vehicleService;
        this.excelImportService = excelImportService;
        this.csvImportService = csvImportService;
        this.vehicleImportService = vehicleImportService;
        this.csvExporterService = csvExporterService;
        this.excelExporterService = excelExporterService;
    }

    @PostMapping
    @Operation(
            summary = "Save a vehicle",
            description = "Save a new vehicle")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "HTTP Status 201 Created" ,content = { @Content(schema = @Schema(implementation = VehicleResponse.class), mediaType = "application/json") }),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "HTTP Status 403 Forbidden", content = { @Content(schema = @Schema()) }),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "HTTP Status 404 Not Found", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity<ApiResponse<VehicleResponse>> save(@Valid @RequestBody VehicleRequest vehicleRequest) {
        logger.info("Request to save vehicle: {}", vehicleRequest);
        ApiResponse<VehicleResponse> apiResponse = vehicleService.save(vehicleRequest);
        logger.info("Vehicle saved: {}", apiResponse);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{vehicleId}")
    @Operation(
            summary = "Update a vehicle",
            description = "Update an existing vehicle"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 OK", content = { @Content(schema = @Schema(implementation = VehicleResponse.class), mediaType = "application/json") }),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "HTTP Status 403 Forbidden", content = { @Content(schema = @Schema()) }),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "HTTP Status 404 Not Found", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<ApiResponse<VehicleResponse>> update(@PathVariable Long vehicleId, @RequestBody VehicleUpdateRequest vehicleUpdateRequest) {
        logger.info("Request to update vehicle: {}", vehicleUpdateRequest);
        ApiResponse<VehicleResponse> apiResponse = vehicleService.update(vehicleId, vehicleUpdateRequest);
        logger.info("Vehicle updated: {}", apiResponse);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{vehicleId}")
    @Operation(
            summary = "Delete a vehicle",
            description = "Delete an existing vehicle"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 OK", content = { @Content(schema = @Schema()) }),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "HTTP Status 403 Forbidden", content = { @Content(schema = @Schema()) }),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "HTTP Status 404 Not Found", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long vehicleId) {
        logger.info("Request to delete vehicle: {}", vehicleId);
        ApiResponse<String> apiResponse = vehicleService.delete(vehicleId);
        logger.info("Vehicle deleted: {}", apiResponse);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{vehicleId}")
    @Operation(
            summary = "Get a vehicle",
            description = "Get an existing vehicle"
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 OK", content = { @Content(schema = @Schema(implementation = VehicleResponse.class), mediaType = "application/json") }),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "HTTP Status 403 Forbidden", content = { @Content(schema = @Schema()) }),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "HTTP Status 404 Not Found", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<ApiResponse<VehicleResponse>> get(@PathVariable Long vehicleId) {
        logger.info("Request to get vehicle: {}", vehicleId);
        ApiResponse<VehicleResponse> apiResponse = vehicleService.get(vehicleId);
        logger.info("Vehicle found: {}", apiResponse);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/all")
    @Operation(
            summary = "Get all vehicles",
            description = "Get all existing vehicles"
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 OK", content = { @Content(schema = @Schema(implementation = VehicleResponse.class), mediaType = "application/json") }),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "HTTP Status 403 Forbidden", content = { @Content(schema = @Schema()) }),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "HTTP Status 404 Not Found", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<ApiResponse<List<VehicleResponse>>> getAll() {
        logger.info("Request to get all vehicles");
        ApiResponse<List<VehicleResponse>> apiResponse = vehicleService.getAll();
        logger.info("Fetched all vehicles successfully, total count: {}", apiResponse.getData().size());
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/{vehicleId}/reservations")
    public ResponseEntity<List<ParkingReservationResponse>> getVehicleReservations(@PathVariable Long vehicleId) {
        List<ParkingReservationResponse> reservations = vehicleService.getParkingReservations(vehicleId);
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("import")
    public ResponseEntity<String> importReservations(@RequestParam("file") MultipartFile file) {
        try {
            vehicleImportService.importFile(file);
            return ResponseEntity.ok("Data imported successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred during import: " + e.getMessage());
        }
    }

    @GetMapping("export/{format}")
    public ResponseEntity<byte[]> exportData(@PathVariable String format) {
        byte[] fileContent;
        String contentType;
        String fileName;

        if ("csv".equalsIgnoreCase(format)) {
            fileContent = csvExporterService.exportToCsv();
            contentType = "text/csv";
            fileName = "parking_reservations.csv";
        } else if ("excel".equalsIgnoreCase(format)) {
            fileContent = excelExporterService.exportToExcel();
            contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            fileName = "parking_reservations.xlsx";
        } else {
            return ResponseEntity.badRequest().body("Unsupported format".getBytes());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.parseMediaType(contentType));

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(fileContent);

    }

    @GetMapping("/priority/{vehicleId}")
    public ResponseEntity<String> getVehiclePriority(@PathVariable Long vehicleId) {
        String priorityStatus = vehicleService.getVehiclePriority(vehicleId);
        return ResponseEntity.ok(priorityStatus);
    }

}
