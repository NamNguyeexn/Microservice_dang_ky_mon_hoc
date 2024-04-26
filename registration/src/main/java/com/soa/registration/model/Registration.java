package com.soa.registration.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "registration")
public class Registration {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "idStudent", nullable = false)
    private int idStudent;
    @Column(name = "idClasses")
    private int idClasses;
}
