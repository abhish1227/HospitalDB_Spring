package com.abhish.hospitalDB.entity;


import com.abhish.hospitalDB.entity.enums.BloodGroup;
import com.abhish.hospitalDB.entity.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_patient_name_birthdate", columnNames = {"name", "birthDate"})
        },
        indexes = {
                @Index(name = "index_on_birthDate", columnList = "birthDate")
        }
)
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Email
    @Column(nullable = false, unique = true)
    private String email;


    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @CreationTimestamp              /*with this annotation the field once created can't be updated
    But one can also make a field immutable by using the annotation @Column(updatable = false)*/
    private LocalDateTime createdAt;


    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;

    @OneToOne (cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "patient_insurance_id") //owning side
    private Insurance insurance;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.REMOVE, //we define cascade in the inverse side of the relationship
            //making the inverse side as the parent entity in the Data domain, which is opposite to that in the JPA domain,
            //where Appointment is the owner of the relation and parent is the inverse.
            //The parent in the data domain is decided based on which entity can survive without the other.
            //And since in this case, appointments can't exist without patient hence when patient details are removed,
            //appointments should also be removed. Therefore, Patient entity becomes the parent entity to govern cascading rules.
            orphanRemoval = true, fetch = FetchType.LAZY) //inverse side so we use mapped by
    /*
    * Even though we created a bidirectional mapping between patient and appointment
    * the patient table won't contain an appointment field in the database, but the JPA
    * will get access to the appointment list if we call appointments through the patient repository.
    * */
    @JsonIgnore
    private List<Appointment> appointments = new ArrayList<>();

}
