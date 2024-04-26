package com.soa.registration.DTO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mysql.cj.xdevapi.JsonArray;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Classes {
    private int id;
    private String idClassString;
    private int idSubject;
    private Classes StringToClasses(String s) {
        Classes classes = new Classes();
        String[] keys = s.split("=");
        String key = keys[0].trim();
        String value = keys[1];
        if (key.equals("id")) {
            classes.setId(Integer.parseInt(value));
        } else if (key.equals("idClassString")) {
            classes.setIdClassString(value);
        } else {
            classes.setIdSubject(Integer.parseInt(value));
        }
        return classes;
    }
}
