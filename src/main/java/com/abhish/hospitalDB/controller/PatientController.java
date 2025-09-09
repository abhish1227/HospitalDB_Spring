package com.abhish.hospitalDB.controller;

import com.abhish.hospitalDB.DTOs.CreatePatientRequestDTO;
import com.abhish.hospitalDB.DTOs.PatientResponseDTO;
import com.abhish.hospitalDB.DTOs.UpdatePatientRequestDTO;
import com.abhish.hospitalDB.entity.Patient;
import com.abhish.hospitalDB.response.APIResponse;
import com.abhish.hospitalDB.service.PatientService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequestMapping("/patients")
@RestController
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllPatients(){
        List<PatientResponseDTO> patients = patientService.getAllPatients();
        return ResponseEntity.ok(new APIResponse("Patients fetched successfully.", patients));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getPatientById(@PathVariable Long id){
        try {
            PatientResponseDTO patient = patientService.getPatientById(id);
            return ResponseEntity.ok(new APIResponse("Patient fetched successfully.", patient));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<APIResponse> updatePatient(@PathVariable Long id, @Valid @RequestBody UpdatePatientRequestDTO request){
        try {
            PatientResponseDTO updatedPatient = patientService.updatePatient(id, request);
            return ResponseEntity.ok(new APIResponse("Patient details updated successfully.", updatedPatient));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }

    }

    @PostMapping("/create")
    public ResponseEntity<APIResponse> createPatient(@RequestBody @Valid CreatePatientRequestDTO request){

        try {
            PatientResponseDTO newPatient = patientService.createNewPatient(request);
            return ResponseEntity.ok().body(new APIResponse("Patient created successfully", newPatient));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(CONFLICT).body(new APIResponse(e.getMessage(), null));
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse> deletePatient(@PathVariable Long id){
        try {
            patientService.deletePatient(id);
            return ResponseEntity.ok().body(new APIResponse("Patient record deleted successfully.", null));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }


}
