package com.abhish.hospitalDB.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Builder
public class User implements UserDetails { /*this is done for the authentication providers such as DAO
                                            to fetch details of users that are stored in the database,
                                            rather than using InMmemoryUserDetailsManager to fetch users
                                            saved in-memory.*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //to grant roles to users
        return List.of();
    }
}
