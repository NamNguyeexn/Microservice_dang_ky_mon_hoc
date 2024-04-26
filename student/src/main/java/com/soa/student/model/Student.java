package com.soa.student.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "student")
public class Student {
    @Id
    @Column(name = "id", unique = true)
    private int id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "idStudentString", nullable = false)
    private String idStudentString;
}
