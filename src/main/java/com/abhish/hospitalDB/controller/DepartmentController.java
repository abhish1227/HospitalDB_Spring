package com.abhish.hospitalDB.controller;


import com.abhish.hospitalDB.DTOs.DepartmentHODResponseDTO;
import com.abhish.hospitalDB.DTOs.DepartmentRequestDTO;
import com.abhish.hospitalDB.entity.Department;
import com.abhish.hospitalDB.response.APIResponse;
import com.abhish.hospitalDB.service.DepartmentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllDepartments(){
        List<Department> deptList = departmentService.getAllDepartments();
        return ResponseEntity.ok(new APIResponse("Departments fetched successfully.", deptList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getDeptById(@PathVariable Long id){
        try {
            Department dept = departmentService.getDepartmentById(id);
            return ResponseEntity.ok(new APIResponse("Department fetched successfully.", dept));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<APIResponse> updateDept(@PathVariable Long id, @Valid @RequestBody DepartmentRequestDTO request){
        try {
            Department updatedDept = departmentService.updateDepartment(id, request);
            return ResponseEntity.ok(new APIResponse("Department details updated successfully.", updatedDept));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }

    }

    @PostMapping("/create/department")
    public ResponseEntity<APIResponse> createNewDept(@RequestBody @Valid DepartmentRequestDTO request){

        try {
            Department newDept = departmentService.addNewDepartment(request);
            return ResponseEntity.ok().body(new APIResponse("Department created successfully", newDept));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(CONFLICT).body(new APIResponse(e.getMessage(), null));
        }   catch(EntityNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }

    }

    @DeleteMapping("/delete/department/{id}")
    public ResponseEntity<APIResponse> deleteDoctor(@PathVariable Long id){
        try {
            departmentService.deleteDepartment(id);
            return ResponseEntity.ok().body(new APIResponse("Department record deleted successfully.", null));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add/doctor")
    public ResponseEntity<APIResponse> addDoctorToDepartment(@RequestParam Long departmentId,@RequestParam Long doctorId){

        try {
            Department department = departmentService.addDoctorToDepartment(departmentId, doctorId);
            return ResponseEntity.ok().body(new APIResponse("Doctor added to department successfully.", department));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(CONFLICT).body(new APIResponse(e.getMessage(), null));
        } catch(EntityNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }

    }

    @DeleteMapping("/delete/doctor")
    public ResponseEntity<APIResponse> removeDoctorFromDepartment(@RequestParam Long departmentId, @RequestParam Long doctorId){
        try {
            departmentService.removeDoctorFromDepartment(departmentId, doctorId);
            return ResponseEntity.ok().body(new APIResponse("Doctor deleted from department successfully.", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(CONFLICT).body(new APIResponse(e.getMessage(), null));
        } catch(EntityNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/hod/{departmentId}")
    public ResponseEntity<APIResponse> getHOD(@PathVariable Long departmentId){
        try {
            DepartmentHODResponseDTO response = departmentService.getHOD(departmentId);
            return ResponseEntity.ok()
                    .body(new APIResponse("HOD of the department fetched successfully.", response));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

}
