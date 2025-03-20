package com.archisacademy.parking_reservation.controller;

import com.archisacademy.parking_reservation.apiResponse.ApiResponse;
import com.archisacademy.parking_reservation.dto.request.ParkingReservationRequest;
import com.archisacademy.parking_reservation.dto.request.ParkingReservationUpdateRequest;
import com.archisacademy.parking_reservation.dto.response.ParkingReservationResponse;
import com.archisacademy.parking_reservation.entity.ParkingReservation;
import com.archisacademy.parking_reservation.repository.ParkingReservationRepository;
import com.archisacademy.parking_reservation.service.abstracts.ParkingReservationService;
import com.archisacademy.parking_reservation.service.concretes.CsvExporter;
import com.archisacademy.parking_reservation.service.concretes.ExcelExporter;
import com.archisacademy.parking_reservation.service.concretes.ParkingReservationImportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("api/v1/parking/reservation")
@Tag(name = "Parking Reservation", description = "API endpoints for managing parking reservation, including creation, update, deletion, retrieval, and listing of parking reservations records.")
@RequiredArgsConstructor
public class ParkingReservationController {
    private final ParkingReservationService parkingReservationService;
    private final ParkingReservationImportService parkingReservationImportService;
    private final CsvExporter csvExporter;
    private final ExcelExporter excelExporter;
    private final ParkingReservationRepository reservationRepository;

    @Operation(summary = "Create new Parking Reservation" ,description = "create new parking reservation")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "HTTP Status 201 Created" ,content = { @Content(schema = @Schema(implementation = ParkingReservationResponse.class), mediaType = "application/json") } ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "HTTP Status 400 Bad Request", content = { @Content(schema = @Schema()) })
    })
    @PostMapping
    public ResponseEntity<ApiResponse<ParkingReservationResponse>> createParkingReservation(@Valid @RequestBody ParkingReservationRequest reservationRequest) {
      ApiResponse response=parkingReservationService.addParkingReservation(reservationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get a parking reservation", description = "Get an existing parking reservation")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 OK", content = { @Content(schema = @Schema(implementation = ParkingReservationRequest.class), mediaType = "application/json") } ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "HTTP Status 400 Bad Request", content = { @Content(schema = @Schema()) })
    })
    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<ParkingReservationRequest>> getParkingReservationById(@PathVariable  Long id) {
        ApiResponse response = parkingReservationService.getParkingReservationById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a parking reservation", description = "Update an existing parking reservation")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 OK", content = { @Content(schema = @Schema(implementation = ParkingReservationResponse.class), mediaType = "application/json") } ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "HTTP Status 400 Bad Request", content = { @Content(schema = @Schema()) })
    })
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<ParkingReservationResponse>> updateParkingReservation(@PathVariable Long id,
                                                                                             @RequestBody ParkingReservationUpdateRequest reservationRequest) {
        ApiResponse response=    parkingReservationService.updateParkingReservation(id,reservationRequest);
    return ResponseEntity.ok(response);

    }

    @Operation(summary = "Delete a parking reservation", description = "Delete an existing parking reservation")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 OK", content = { @Content(schema = @Schema()) } ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "HTTP Status 400 Bad Request", content = { @Content(schema = @Schema()) })
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>>  deleteParkingReservation(@PathVariable Long id) {
       ApiResponse response= parkingReservationService.deleteParking(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @PostMapping("import")
    public ResponseEntity<String> importReservations(@RequestParam("file") MultipartFile file) {
        try {
            parkingReservationImportService.importFile(file);
            return ResponseEntity.ok("Data imported successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred during import.");
        }
    }
    @GetMapping("export/{format}")
    public ResponseEntity<byte[]> exportData(@PathVariable String format) {
        List<ParkingReservation> reservations =reservationRepository .findAll();

        byte[] fileContent;
        String contentType;
        String fileName;

        if ("csv".equalsIgnoreCase(format)) {
            fileContent = csvExporter.exportToCsv(reservations);
            contentType = "text/csv";
            fileName = "parking_reservations.csv";
        } else if ("excel".equalsIgnoreCase(format)) {
            fileContent = excelExporter.exportToExcel(reservations);
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

    @GetMapping("/vehicle/{vehicleId}")
    public List<ParkingReservationResponse> getReservationsByVehicleId(@PathVariable long vehicleId) {
        List<ParkingReservationResponse> responses=parkingReservationService.getReservationsByVehicleId(vehicleId);
        return  responses;
    }

}
