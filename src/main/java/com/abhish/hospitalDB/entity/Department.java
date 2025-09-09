package com.abhish.hospitalDB.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true, length = 100)
    private String name;

    @OneToOne
    private Doctor headDoctor;

    @ManyToMany //since one doctor can be in many departments and one department can have many doctors
    /*
    * When we create a many to many relationship, a @JoinTable is created by default
    * which has the IDs of both the entities on which this relationship has been defined.
    * Both the IDs are primary as well as Foreign Keys in that table.
    * */

    @JoinTable(
            name = "Department_Doctors", //name of the relationship table
            joinColumns =  @JoinColumn(name = "dpt_id"), //owner of the relation name
            inverseJoinColumns = @JoinColumn(name = "doc_id") //inverse relation name
    )
    private Set<Doctor> doctors = new HashSet<>();
}
