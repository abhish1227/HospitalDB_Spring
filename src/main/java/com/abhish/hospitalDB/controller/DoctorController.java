package com.abhish.hospitalDB.controller;

import com.abhish.hospitalDB.DTOs.*;
import com.abhish.hospitalDB.entity.Doctor;
import com.abhish.hospitalDB.response.APIResponse;
import com.abhish.hospitalDB.service.DoctorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllDoctors(){
        List<Doctor> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(new APIResponse("Doctors fetched successfully.", doctors));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getDoctorById(@PathVariable Long id){
        try {
            Doctor doctor = doctorService.getDoctorById(id);
            return ResponseEntity.ok(new APIResponse("Doctor fetched successfully.", doctor));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<APIResponse> updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorUpdateRequestDTO request){
        try {
            Doctor updatedDoctor = doctorService.updateDoctorDetails(id, request);
            return ResponseEntity.ok(new APIResponse("Doctor details updated successfully.", updatedDoctor));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }

    }

    @PostMapping("/create")
    public ResponseEntity<APIResponse> createNewDoctor(@RequestBody @Valid DoctorCreateRequestDTO request){

        try {
            Doctor newDoctor = doctorService.createNewDoctor(request);
            return ResponseEntity.ok().body(new APIResponse("Doctor created successfully", newDoctor));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(CONFLICT).body(new APIResponse(e.getMessage(), null));
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse> deleteDoctor(@PathVariable Long id){
        try {
            doctorService.deleteDoctorById(id);
            return ResponseEntity.ok().body(new APIResponse("Doctor record deleted successfully.", null));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }
}
