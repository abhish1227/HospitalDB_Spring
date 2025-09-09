package com.abhish.hospitalDB.service;

import com.abhish.hospitalDB.DTOs.CreatePatientRequestDTO;
import com.abhish.hospitalDB.DTOs.PatientResponseDTO;
import com.abhish.hospitalDB.DTOs.UpdatePatientRequestDTO;
import com.abhish.hospitalDB.configuration.ModelMapperConfig;
import com.abhish.hospitalDB.entity.Patient;
import com.abhish.hospitalDB.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;


    @Transactional
    public PatientResponseDTO getPatientById(Long patientId){

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient with id: " + patientId + " doesn't exists."));
        return modelMapper.map(patient, PatientResponseDTO.class);
    }


    public List<PatientResponseDTO> getAllPatients(){
        return patientRepository.findAll().stream()
                .map(patient -> {
                    return modelMapper.map(patient, PatientResponseDTO.class);
                }).toList();

    }


    public PatientResponseDTO updatePatient(Long id, UpdatePatientRequestDTO request){

        Patient patient = patientRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Patient with id: "+id+" not found."));

        patient.setName(request.getName());
        if(request.getBirthDate() != null) patient.setBirthDate(request.getBirthDate());
        if(request.getBloodGroup() != null) patient.setBloodGroup(request.getBloodGroup());
        if(request.getGender() != null) patient.setGender(request.getGender());

        return modelMapper.map(patientRepository.save(patient), PatientResponseDTO.class);

    }

    @Transactional
    public PatientResponseDTO createNewPatient(CreatePatientRequestDTO request){
        if(patientRepository.existsByEmail(request.getEmail())){
            throw new IllegalArgumentException("Email id already exists.");
        }

        Patient newPatient = new Patient();
        newPatient.setName(request.getName());
        newPatient.setEmail(request.getEmail());
        newPatient.setBirthDate(request.getBirthDate());
        newPatient.setCreatedAt(LocalDateTime.now());
        newPatient.setGender(request.getGender());
        newPatient.setBloodGroup(request.getBloodGroup());

        return modelMapper.map(patientRepository.save(newPatient), PatientResponseDTO.class);
    }

    public void deletePatient(Long id){
        Patient patient = patientRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Patient with id: "+id+" not found."));

        patientRepository.delete(patient);
    }
}
