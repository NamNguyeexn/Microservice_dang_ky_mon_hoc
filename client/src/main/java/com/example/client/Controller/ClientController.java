package com.example.client.Controller;

import com.example.client.DTO.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.ser.YearSerializer;
import jakarta.servlet.Registration;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

@Controller
public class ClientController {
    @Autowired
    private HttpSession session;
    private String registrationControllerMessage;
    private String classesControllerMessage;
    private final RestTemplate restTemplate;
    private String studentControllerMessage;
    private String subjectControllerMessage;
    private String presubjectControllerMessage;
    @Autowired
    public ClientController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String getHomePage(Model model) throws IOException {
        List<Classes> classes = getAllClasses();
        List<ClassesDisplay> classesDisplays = new ArrayList<>();
        for(Classes c : classes){
            ClassesDisplay data = new ClassesDisplay();
            data.setId(c.getId());
            data.setIdClassString(c.getIdClassString());
            data.setIdClassString(c.getIdClassString());
            Subject s = getSubjectById(c.getId());
            data.setNameSubject(s.getName());
            data.setIntValuesSubject(s.getIntValues());
            classesDisplays.add(data);
        }
        model.addAttribute("classes", classesDisplays);
        return "home";
    }
//    @PostMapping("/save")
//    public String getAlert(@RequestParam("idClass") String[] idClassesStr, Model model) {
//        String urlSaveRegistration = "http://localhost:8083/api/registration/save";
//        //////
//        for (String s: idClassesStr){
//            System.out.println(s);
//        }
//        ResponseObject<Registration> responseObjectPreSubject = restTemplate.postForObject(
//                urlSaveRegistration,
//                Registration.class,
//                idClassesStr);
//        System.out.println(responseObjectPreSubject);
//        if (classesControllerMessage!=null){
//            model.addAttribute("classesMessage", classesControllerMessage);
//        } else if (subjectControllerMessage!=null) {
//            model.addAttribute("subjectMessage", subjectControllerMessage);
//        }
//        return "alert";
//    }
    private List<Classes> getAllClasses() throws IOException {
        List<Classes> classesList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();
        String externalServiceUrl = "http://localhost:8081/api/classes";
        String responseEntity = restTemplate.getForObject(
                externalServiceUrl,
                String.class,
                String.class
        );
        ResponseObject responseObjectClasses = mapper.readValue(responseEntity, ResponseObject.class);
        classesControllerMessage = responseObjectClasses.getMessage();
        List<Map<String,Object>> maps = parseToListOfMaps(responseEntity);
        for(Map<String,Object> map : maps) {
            Classes classes = new Classes();
            classes.setId((int) map.get("id"));
            classes.setIdClassString((String) map.get("idClassString"));
            classes.setIdSubject((int) map.get("idSubject"));
            classesList.add(classes);
        }
        return classesList;
    }
    private Subject getSubjectById(int id) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        Subject resSubject = new Subject();
        Semester resSemester = new Semester();
        Year resYear = new Year();
        String externalServiceUrl = "http://localhost:8085/api/subject/" + id;
        String responseEntity = restTemplate.getForObject(
                externalServiceUrl,
                String.class,
                id
        );
        ResponseObject responseObjectClasses = mapper.readValue(responseEntity, ResponseObject.class);
        subjectControllerMessage = responseObjectClasses.getMessage();
        String sol = responseObjectClasses
                .getData()
                .toString()
                .replace(",", "")
                .replace("=", " ");
//        res = StringToSubject(responseObjectClasses.getData().toString());
        String[] part = sol.split(" ");
        List<String> resString = new ArrayList<>();
        resSubject.setId(Integer.parseInt(part[1]));
        resSubject.setName(part[3]);
        resSubject.setIntValues(part[5]);
        resSemester.setId(Integer.parseInt(part[8]));
        resSemester.setName(part[10]);
        resSemester.setIdSemesterString(part[12]);
        resYear.setId(Integer.parseInt(part[15]));
        resYear.setName(part[17]);
        resSemester.setYear(resYear);
        resSubject.setSemester(resSemester);
        return resSubject;
    }
    private List<Map<String,Object>> parseToListOfMaps(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> rootMap = mapper.readValue(json, Map.class);
        List<Map<String,Object>> dataList =
                (List<Map<String,Object>>) rootMap.get("data");
        return dataList;
    }
//    private Subject StringToSubject(String s) {
//        Subject subject = new Subject();
//        StringTokenizer st = new StringTokenizer(s, ",");
//        while(st.hasMoreTokens()) {
//            String part = st.nextToken();
//            String[] kv = part.split("=");
//            String key = kv[0].trim();
//            String value = kv[1];
//            if(key.equals("id")) {
//                subject.setId(Integer.parseInt(value));
//            } else if(key.equals("name")) {
//                subject.setName(value);
//            } else if(key.equals("intValues")) {
//                subject.setIntValues(value);
//            } else if(key.equals("semester")) {
//                Semester sem = parseSemester(value);
//                subject.setSemester(sem);
//            }
//        }
//        return subject;
//    }
//    private Semester parseSemester(String semStr) {
//        Semester semester = new Semester();
//        StringTokenizer st = new StringTokenizer(semStr, ",");
//        while(st.hasMoreTokens()) {
//            String part = st.nextToken();
//            String[] kv = part.split("=");
//            String key = kv[0].trim();
//            String value = kv[1];
//            System.out.println(key + " " + value);
//            switch (key) {
//                case "id" -> semester.setId(Integer.parseInt(value));
//                case "name" -> semester.setName(value);
//                case "idSemesterString" -> semester.setIdSemesterString(value);
//                case "idYear" -> {
//                    Year year = parseYear(value);
//                    semester.setYear(year);
//                }
//            }
//        }
//        return semester;
//    }
//    private Year parseYear(String yearValue){
//        Year year = new Year();
//        StringTokenizer st = new StringTokenizer(yearValue, ",");
//        while(st.hasMoreTokens()) {
//            String part = st.nextToken();
//            String[] kv = part.split("=");
//            String key = kv[0].trim();
//            String value = kv[1];
//            if(key.equals("id")) {
//                year.setId(Integer.parseInt(value));
//            } else if(key.equals("name")) {
//                year.setName(value);
//            }
//        }
//        return year;
//    }
}
