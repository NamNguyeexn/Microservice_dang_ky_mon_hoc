package com.example.client.DTO.response;

import com.example.client.DTO.Classes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WrapClassesStr {
    String message;
    Classes classes;
}
