package com.example.client.DTO;
import lombok.Data;

@Data
public class Subject {
    private Integer id;
    private String name;
    private String intValues;
    private Semester semester;
}
