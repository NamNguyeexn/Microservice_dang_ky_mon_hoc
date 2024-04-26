package com.soa.classes.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "class")
public class Classes {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "idClassString", nullable = false)
    private String idClassString;
    @Column(name = "idSubject", nullable = false)
    private int idSubject;
}
