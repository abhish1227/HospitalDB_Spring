package com.abhish.hospitalDB.service;

import com.abhish.hospitalDB.DTOs.InsuranceRequestDTO;
import com.abhish.hospitalDB.DTOs.InsuranceResponseDTO;
import com.abhish.hospitalDB.DTOs.InsuranceUpdateRequestDTO;
import com.abhish.hospitalDB.DTOs.PatientResponseDTO;
import com.abhish.hospitalDB.entity.Insurance;
import com.abhish.hospitalDB.entity.Patient;
import com.abhish.hospitalDB.repository.InsuranceRepository;
import com.abhish.hospitalDB.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final PatientRepository patientRepository;
    private final InsuranceRepository insuranceRepository;
    private final ModelMapper modelMapper;

    @Transactional //this is used to save dirty changes in the persistence context, even without manually performing save action
    public PatientResponseDTO assignInsuranceToPatient(InsuranceRequestDTO request, Long patientId){
        Patient patient = patientRepository.findById(patientId) //retrieves the patient from the database to the persistence context
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with the id: "+ patientId));
        if(patient.getInsurance() != null){
            throw new IllegalArgumentException("Insurance details already exists. Please try updating if you wish to update the details.");
        }

        Insurance insurance = new Insurance();
        insurance.setPolicyNumber(request.getPolicyNumber());
        if(request.getProvider() != null) insurance.setProvider(request.getProvider());
        insurance.setValidUntil(request.getValidUntil());
        insuranceRepository.save(insurance);
        patient.setInsurance(insurance); //since patient is the owning side we have to set the insurance in the patient table
        //also when we do this, the patient entity in the persistence context becomes dirty and hence must be saved
        // in the database after this transaction is over, which is done automatically if we use @Transactional
        // but this automatic save operation will happen only when proper cascading rules are defined
        insurance.setPatient(patient); //we can avoid doing this, but we do this to maintain Bi-directional consistency.
        return modelMapper.map(patient, PatientResponseDTO.class);
    }

    @Transactional
    public void deleteInsuranceFromPatient(Long patientId){
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient with id: " + patientId + " not found."));

        if(patient.getInsurance()!=null) patient.setInsurance(null); /*since we made change to patient in the persistence state, it becomes dirty,
        *and hence it will be saved automatically after the transaction is over.
        *Moreover, the cascade rules for insurance field in patient entity defines orphanRemoval = true, which means
        *when we remove insurance from patient, the insurance will automatically be removed from the insurance table.
        */
        else throw new EntityNotFoundException("No insurance record found for the patient.");
    }

    @Transactional
    public List<Insurance> getAllInsurance(){
        return insuranceRepository.findAll();
    }

    @Transactional
    public Insurance getInsuranceById(Long id) {
        return insuranceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Insurance with id: " + id + " not found."));

    }

    @Transactional
    public Insurance updateInsurance(InsuranceUpdateRequestDTO request, Long id){
        Insurance insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Insurance details not found."));

        if(request.getPolicyNumber() != null) insurance.setPolicyNumber(request.getPolicyNumber());
        if(request.getProvider() != null) insurance.setProvider(request.getProvider());
        if(request.getValidUntil() != null) insurance.setValidUntil(request.getValidUntil());

        return insurance;
    }

}
