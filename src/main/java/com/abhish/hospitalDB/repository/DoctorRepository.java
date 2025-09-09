package com.abhish.hospitalDB.repository;

import com.abhish.hospitalDB.entity.Doctor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    boolean existsByEmail(@Email @NotNull String email);
}
