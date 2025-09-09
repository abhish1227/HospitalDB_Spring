package com.abhish.hospitalDB.controller;

import com.abhish.hospitalDB.DTOs.InsuranceRequestDTO;
import com.abhish.hospitalDB.DTOs.InsuranceUpdateRequestDTO;
import com.abhish.hospitalDB.DTOs.PatientResponseDTO;
import com.abhish.hospitalDB.entity.Insurance;
import com.abhish.hospitalDB.response.APIResponse;
import com.abhish.hospitalDB.service.InsuranceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("/insurance")
public class InsuranceController {

    private final InsuranceService insuranceService;


    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllInsurance(){
        List<Insurance> insuranceList = insuranceService.getAllInsurance();
        return ResponseEntity.ok().body(new APIResponse("Insurance details fetched successfully.", insuranceList));

    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> findInsuranceById(@PathVariable Long id){
        try {
            Insurance insurance = insuranceService.getInsuranceById(id);
            return ResponseEntity.ok().body(new APIResponse("Insurance record fetched successfully.", insurance));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add/{patientId}")
    public ResponseEntity<APIResponse> createInsurance(@RequestBody InsuranceRequestDTO request,@PathVariable Long patientId){
        try {
            PatientResponseDTO response = insuranceService.assignInsuranceToPatient(request, patientId);
            return ResponseEntity.ok()
                    .body(new APIResponse("Insurance details successfully associated with patient with id: "+patientId+".",response));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(CONFLICT).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<APIResponse> updateInsuranceRecord(@RequestBody InsuranceUpdateRequestDTO request, @PathVariable Long id){
        try {
            Insurance insurance = insuranceService.updateInsurance(request, id);
            return ResponseEntity.ok()
                    .body(new APIResponse("Insurance details updated successfully", insurance));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{patientId}")
    public ResponseEntity<APIResponse> deleteInsuranceById(@PathVariable Long patientId){
        try {
            insuranceService.deleteInsuranceFromPatient(patientId);
            return ResponseEntity.ok()
                    .body(new APIResponse("Insurance details removed from patient successfully.", null));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }
}
