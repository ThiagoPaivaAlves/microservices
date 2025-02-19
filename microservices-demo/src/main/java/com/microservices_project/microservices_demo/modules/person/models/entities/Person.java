package com.microservices_project.microservices_demo.modules.person.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "person")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class Person {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="first_name", nullable = false, length = 100)
    private String firstName;
    
    @Column(name="last_name", nullable = false)
    private String lastName;
    
    @Column(name="address")
    private String address;
    
    @Column(name="gender")
    private String gender;
    
    @Column(name="enabled", nullable = false)
    private Boolean enabled;
}
