package com.abhish.hospitalDB.repository;

import com.abhish.hospitalDB.entity.Department;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByNameIgnoreCase(String name);

    @Modifying
    @Query("UPDATE Department d SET d.headDoctor = null WHERE d.headDoctor.id = :docId")
    void unassignHeadDoctor(@Param("docId") Long docId);
}
