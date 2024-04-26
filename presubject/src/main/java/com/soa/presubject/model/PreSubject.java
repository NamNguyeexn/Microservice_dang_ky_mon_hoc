package com.soa.presubject.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "presubject")
public class PreSubject {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private int id;
    @Column(name = "idStudent", nullable = false)
    private int idStudent;
    @Column(name = "idSubject", nullable = false)
    private int idSubject;
}
