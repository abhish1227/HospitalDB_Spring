package com.abhish.hospitalDB.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true, length = 50)
    private String policyNumber;

    @Column(nullable = false, length = 100)
    private String provider;

    @Column(nullable = false)
    private LocalDate validUntil;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "insurance") /*inverse side.
    We created Bi-Directional mapping from patient to insurance, but the patient table is defined as
    the owner of the relationship with the insurance table being the inverse side.
    */
    /*mappedBy fields are never visible in the database. It just tells the JPA that the field is
    * available via the Insurance table for querying. @JoinColumn is redundant to use here*/
    @JsonIgnore
    private Patient patient;
}
