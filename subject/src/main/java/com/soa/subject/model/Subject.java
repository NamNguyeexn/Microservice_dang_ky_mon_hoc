package com.soa.subject.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "subject")
public class Subject {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "intValues", nullable = false)
    private String intValues;
    @ManyToOne
    @JoinColumn(name = "idSemester")
    private Semester semester;
}