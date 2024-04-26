package com.soa.subject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "year")
public class Year {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "string", nullable = false)
    private String name;
}
