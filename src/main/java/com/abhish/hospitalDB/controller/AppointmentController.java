package com.abhish.hospitalDB.controller;

import com.abhish.hospitalDB.entity.Appointment;
import com.abhish.hospitalDB.response.APIResponse;
import com.abhish.hospitalDB.service.AppointmentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllAppointments(){
        return ResponseEntity.ok().body(new APIResponse("Appointments fetched successfully.",appointmentService.getAllAppointments()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getAppointmentById(@PathVariable Long id){
        try {
            Appointment appointment = appointmentService.getAppointmentById(id);
            return ResponseEntity.ok().body(new APIResponse("Appointment fetched successfully.",  appointment));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<APIResponse> createNewAppointment(@RequestBody Appointment appointment,
                                                            @RequestParam Long doctorId, @RequestParam Long patientId){
        try {
            Appointment response = appointmentService.createNewAppointment(appointment, doctorId, patientId);
            return ResponseEntity.ok().body(new APIResponse("Appointment created successfully.", appointment));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        } catch(IllegalArgumentException e){
            return ResponseEntity.status(CONFLICT).body(new APIResponse(e.getMessage(), null));
        }

    }

    @PutMapping("/reassign")
    public ResponseEntity<APIResponse> reassignAppointmentToNewDoc(@RequestParam Long appointmentId, @RequestParam Long newDoctorId){
        try {
            Appointment newAppointment = appointmentService.reassignAppointmentToAnotherDoctor(appointmentId,newDoctorId);
            return ResponseEntity.ok().body(new APIResponse("Doctor for the given appointment reassigned successfully.", newAppointment));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse> deleteAppointment(@PathVariable Long id){
        try {
            appointmentService.deleteAppointment(id);
            return ResponseEntity.ok().body(new APIResponse("Appointment deleted successfully.", null));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/changeTime")
    public ResponseEntity<APIResponse> changeAppointmentTime(@RequestParam Long appointmentId, @RequestParam LocalDateTime newTime){
        try {
            Appointment updatedAppointment = appointmentService.changeAppointmentTime(appointmentId, newTime);
            return ResponseEntity.ok().body(new APIResponse("Appointment time changed successfully.", updatedAppointment));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<APIResponse> getAllAppointmentsForPatient(@PathVariable Long patientId){
        try {
            List<Appointment> appointments = appointmentService.getAllPatientAppointments(patientId);
            if(appointments.isEmpty()) return ResponseEntity.ok().body(new APIResponse("No appointments created for the given patient.", null));
            else return ResponseEntity.ok().body(new APIResponse("Appointments fetched successfully.", appointments));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<APIResponse> getAllAppointmentsForDoctor(@PathVariable Long doctorId){
        try {
            List<Appointment> appointments = appointmentService.getAllDoctorAppointments(doctorId);
            if(appointments.isEmpty()) return ResponseEntity.ok().body(new APIResponse("No appointments assigned for the given doctor.", null));
            else return ResponseEntity.ok().body(new APIResponse("Appointments fetched successfully.", appointments));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }


}
