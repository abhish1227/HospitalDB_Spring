package com.abhish.hospitalDB.repository;

import com.abhish.hospitalDB.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}
