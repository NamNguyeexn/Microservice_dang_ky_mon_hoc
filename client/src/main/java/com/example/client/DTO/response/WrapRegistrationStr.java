package com.example.client.DTO.response;

import com.example.client.DTO.Registration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WrapRegistrationStr {
    String message;
    Registration registration;
}
