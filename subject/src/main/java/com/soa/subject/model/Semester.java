package com.soa.subject.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "semester")
public class Semester {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "idSemesterString", nullable = false)
    private String idSemesterString;
    @ManyToOne
    @JoinColumn(name = "idYear")
    private Year year;
}
