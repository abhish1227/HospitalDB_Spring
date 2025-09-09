package com.abhish.hospitalDB.service;

import com.abhish.hospitalDB.DTOs.DoctorCreateRequestDTO;
import com.abhish.hospitalDB.DTOs.DoctorUpdateRequestDTO;
import com.abhish.hospitalDB.entity.Department;
import com.abhish.hospitalDB.entity.Doctor;
import com.abhish.hospitalDB.repository.DepartmentRepository;
import com.abhish.hospitalDB.repository.DoctorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;

    public List<Doctor> getAllDoctors(){
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(Long id){
        return doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor with id: "+id+", doesn't exists."));
    }


    public Doctor createNewDoctor(DoctorCreateRequestDTO request){
        if(doctorRepository.existsByEmail(request.getEmail())){
            throw new IllegalArgumentException("Doctor with the provided email already exists.");
        }
        Doctor doctor = new Doctor();
        doctor.setName(request.getName());
        doctor.setEmail(request.getEmail());
        doctor.setSpecialization(request.getSpecialization());

        return doctorRepository.save(doctor);

    }

    @Transactional
    public Doctor updateDoctorDetails(Long id, DoctorUpdateRequestDTO request){
        Doctor doc = doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor with ID: "+id+" not found."));

        if(request.getEmail() != null && doctorRepository.existsByEmail(request.getEmail())){
            throw new IllegalArgumentException("Email id already exists. Please try again with a different email.");
        }

        if(request.getEmail() != null) doc.setEmail(request.getEmail());
        if(request.getSpecialization() != null) doc.setSpecialization(request.getSpecialization());
        if(request.getName() != null) doc.setName(request.getName());

        return doc;
    }

    public void deleteDoctorById(Long id){
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor with ID: "+id+" not found."));

        for (Department department : doctor.getDepartments()) {
            department.getDoctors().remove(doctor); // remove from department side
        }
        doctor.getDepartments().clear();


        List<Department> departmentList = departmentRepository.findAll();
        for(Department dept:departmentList){
            if(dept.getHeadDoctor() != null && Objects.equals(dept.getHeadDoctor().getId(),id)){
                dept.setHeadDoctor(null);
            }
        }
        departmentRepository.saveAll(departmentList);
        doctorRepository.delete(doctor);
    }


}
