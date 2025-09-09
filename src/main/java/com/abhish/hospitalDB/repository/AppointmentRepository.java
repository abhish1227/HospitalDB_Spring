package com.abhish.hospitalDB.repository;

import com.abhish.hospitalDB.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
