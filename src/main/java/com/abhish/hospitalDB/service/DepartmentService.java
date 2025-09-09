package com.abhish.hospitalDB.service;

import com.abhish.hospitalDB.DTOs.DepartmentHODResponseDTO;
import com.abhish.hospitalDB.DTOs.DepartmentRequestDTO;
import com.abhish.hospitalDB.entity.Department;
import com.abhish.hospitalDB.entity.Doctor;
import com.abhish.hospitalDB.repository.DepartmentRepository;
import com.abhish.hospitalDB.repository.DoctorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    public List<Department> getAllDepartments(){
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id){
        return departmentRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Department with ID: "+id+" not found."));
    }


    public Department addNewDepartment(DepartmentRequestDTO request){
        if(departmentRepository.existsByNameIgnoreCase(request.getName())){
            throw new IllegalArgumentException("Department already exists.");
        }

        Department dept = new Department();
        dept.setName(request.getName());

        Long docId = request.getHeadDoctorId();
        if(docId != null){
            Doctor doc = doctorRepository.findById(docId)
                    .orElseThrow(()-> new EntityNotFoundException("Doctor with ID: "+docId+" not found."));

            List<Department> departmentList = departmentRepository.findAll();
            for(Department department: departmentList){
                if(department.getHeadDoctor()!= null && Objects.equals(department.getHeadDoctor().getId(), doc.getId())){
                    throw new IllegalArgumentException("Doctor already heading department with id: " +department.getId()+
                            ". A doctor can only head one department.");
                }
            }

            dept.setHeadDoctor(doc);
        }

        return departmentRepository.save(dept);
    }

    @Transactional
    public Department updateDepartment(Long id, DepartmentRequestDTO request){
        Department dept = departmentRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Department with ID: "+id+" not found."));

        dept.setName(request.getName());

        Long docId = request.getHeadDoctorId();
        if(docId != null){
            Doctor doc = doctorRepository.findById(docId)
                    .orElseThrow(()-> new EntityNotFoundException("Doctor with ID: "+docId+" not found."));

            departmentRepository.unassignHeadDoctor(docId);

            dept.setHeadDoctor(doc);
        }
        return departmentRepository.save(dept);
    }

    @Transactional
    public Department addDoctorToDepartment(Long departmentId, Long doctorId){
        Department dept = departmentRepository.findById(departmentId)
                .orElseThrow(()-> new EntityNotFoundException("Department with ID: "+departmentId+" not found."));

        Doctor doc = doctorRepository.findById(doctorId)
                .orElseThrow(()-> new EntityNotFoundException("Doctor with ID: "+doctorId+" not found."));

        if(dept.getDoctors().contains(doc))
            throw new IllegalArgumentException("Doctor already added in the department.");

        dept.getDoctors().add(doc);
        doc.getDepartments().add(dept);
        return dept;
    }
    @Transactional
    public void deleteDepartment(Long id){
        Department dept = departmentRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Department with ID: "+id+" not found."));

        Set<Doctor> docList = dept.getDoctors();
        for(Doctor doc:docList){
            doc.getDepartments().remove(dept);
        }
        departmentRepository.delete(dept);
    }



    @Transactional
    public void removeDoctorFromDepartment(Long departmentId, Long doctorId){
        Department dept = departmentRepository.findById(departmentId)
                .orElseThrow(()-> new EntityNotFoundException("Department with ID: "+departmentId+" not found."));

        Doctor doc = doctorRepository.findById(doctorId)
                .orElseThrow(()-> new EntityNotFoundException("Doctor with ID: "+doctorId+" not found."));

        if(!dept.getDoctors().contains(doc))
            throw new IllegalArgumentException("Doctor not found in the department. " +
                    "Can't perform delete operation on non-existing value.");

        doc.getDepartments().remove(dept);
        dept.getDoctors().remove(doc);

    }

    public DepartmentHODResponseDTO getHOD(Long departmentId){
        Department dept = departmentRepository.findById(departmentId)
                .orElseThrow(()-> new EntityNotFoundException("Department with ID: "+departmentId+" not found."));

        Doctor headDoc = dept.getHeadDoctor();
        if(headDoc == null)
            throw new EntityNotFoundException("No head Doctor assigned for this department.");

        return modelMapper.map(headDoc, DepartmentHODResponseDTO.class);
    }

}
