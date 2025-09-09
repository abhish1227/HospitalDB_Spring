package com.abhish.hospitalDB.service;

import com.abhish.hospitalDB.entity.Appointment;
import com.abhish.hospitalDB.entity.Doctor;
import com.abhish.hospitalDB.entity.Patient;
import com.abhish.hospitalDB.repository.AppointmentRepository;
import com.abhish.hospitalDB.repository.DoctorRepository;
import com.abhish.hospitalDB.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Transactional
    public Appointment createNewAppointment(Appointment appointment, Long doctorId, Long patientId){
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor with id: " + doctorId + " does not exists."));
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient with id: " + patientId + " does not exists."));

        //if appointment have id it means it is coming from database and hence we shouldn't update the already
        // existing appointment. Hence, we perform this check.
        if(appointment.getId() != null) throw new IllegalArgumentException("Appointment cannot have id.");

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        patient.getAppointments().add(appointment); //just to maintain Bi-directional relationship
        doctor.getAppointments().add(appointment);

        return appointmentRepository.save(appointment); //Here we still need to save this appointment in the repository even
        //with @Transactional annotation, because we are creating a fresh new appointment. We are not doing
        // doctor.setAppointment(appointment) or patient.setAppointment(appointment) - which doesn't makes sense here.
        //So a newly created appointment must be saved.


    }

    @Transactional
    public Appointment reassignAppointmentToAnotherDoctor(Long appointmentId, Long newDocId){

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment with id: " + appointmentId + " does not exists."));

        Doctor newDoctor = doctorRepository.findById(newDocId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor with id: " + newDocId + " does not exists."));

        Doctor oldDoctor = appointment.getDoctor();
        if(oldDoctor != null){
            oldDoctor.getAppointments().remove(appointment);
        }

        appointment.setDoctor(newDoctor); //By doing this appointment in the persistence state gets dirty and
        // will be automatically saved after this transaction is over.
        newDoctor.getAppointments().add(appointment);


        return appointment;
    }

    @Transactional
    public void deleteAppointment(Long appointmentId){
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment with id: " + appointmentId + " does not exists."));

        appointment.getDoctor().getAppointments().remove(appointment);
        appointment.getPatient().getAppointments().remove(appointment);

        appointmentRepository.delete(appointment);

    }

    @Transactional
    public Appointment changeAppointmentTime(Long appointmentId, LocalDateTime newTime){
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment with id: " + appointmentId + " does not exists."));

        appointment.setAppointmentTime(newTime);

        return appointment;
    }

    public List<Appointment> getAllAppointments(){
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(Long appointmentId){
       return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment with id: " + appointmentId + " does not exists."));
    }


    public List<Appointment> getAllPatientAppointments(Long patientId){
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(()->new EntityNotFoundException("Patient with id: "+patientId+" not found."));

        return patient.getAppointments();
    }

    public List<Appointment> getAllDoctorAppointments(Long doctorId){
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(()->new EntityNotFoundException("Doctor with id: "+doctorId+" not found."));

        return doctor.getAppointments();
    }
}
