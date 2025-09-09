package com.abhish.hospitalDB.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime appointmentTime;

    @Column(length = 500)
    private String reason;

    @ManyToOne //Many appointments are possible for one Patient, hence the relation @ManyToOne
    @JoinColumn(nullable = false) //patient is required for an appointment hence nullable is false
    private Patient patient;
    /*
    * The table which we wish to make as the owner of the relationship between two entities, we
    * insert the foreign key constraint in that table by mentioning @JoinColumn. The inverse side
    * of this relationship, i.e. the Patient entity will have OneToMany relation with Appointment
    * and will be mappedBy "patient" column in the Appointment table. Here we wish to make
    * appointment entity as the owner of the relationship between appointment and patient table.
    * So we inserted the patient as the foreign key in this entity.
    * */



    @ManyToOne
    @JoinColumn(name= "doctor_id", nullable = false) //if we don't give @JoinColumn annotation,
                                                    // then by default nullable is true
    private Doctor doctor;

}
