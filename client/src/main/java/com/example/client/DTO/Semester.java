package com.example.client.DTO;
import lombok.Data;

@Data
public class Semester {
    private int id;
    private String name;
    private String idSemesterString;
    private Year year;
}
