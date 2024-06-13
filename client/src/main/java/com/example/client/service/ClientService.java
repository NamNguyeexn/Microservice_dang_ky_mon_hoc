//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.client.service;

import com.example.client.DTO.*;
import com.example.client.DTO.response.WrapBooleanStr;
import com.example.client.DTO.response.WrapClassesStr;
import com.example.client.DTO.response.WrapRegistrationStr;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClientService {
    private RestTemplate restTemplate;
    private final String urlCheckIfStudentPassed = "http://localhost:8082/api/presubject/{id}";
    private final String urlGetClassesById = "http://localhost:8081/api/classes/{id}";
    private final String urlUpdateRegistration = "http://localhost:8083/api/registration/save";
    private final String urlGetStudentById = "http://localhost:8084/api/student/{id}";

    @Autowired
    public ClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Classes> getAllClasses() throws IOException {
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

    private List<Map<String, Object>> parseToListOfMaps(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> rootMap = (Map)mapper.readValue(json, Map.class);
        List<Map<String, Object>> dataList = (List)rootMap.get("data");
        return dataList;
    }

    public List<ClassesDisplay> getAllClassesDisplays(List<Classes> classes) throws JsonProcessingException {
        List<ClassesDisplay> res = new ArrayList();
        Iterator var3 = classes.iterator();

        while(var3.hasNext()) {
            Classes c = (Classes)var3.next();
            ClassesDisplay data = new ClassesDisplay();
            data.setId(c.getId());
            data.setIdClassString(c.getIdClassString());
            data.setIdClassString(c.getIdClassString());
            Subject s = this.getSubjectById(c.getId());
            data.setNameSubject(s.getName());
            data.setIntValuesSubject(s.getIntValues());
            res.add(data);
        }

        return res;
    }

    public WrapClassesStr mapToGetClassesById(int idClass) {
        ResponseObject responseObjectClasses =
                restTemplate.getForObject(
                        urlGetClassesById,
                        ResponseObject.class,
                        idClass
                );
        WrapClassesStr wrapClassesStr = new WrapClassesStr();
        assert responseObjectClasses != null;
        wrapClassesStr.setMessage(responseObjectClasses.getMessage());
        wrapClassesStr.setClasses(StringToClasses(responseObjectClasses.getData().toString()));
        return wrapClassesStr;
    }

    public Student mapToGetStudentById(int idStu){
        ResponseObject responseObject =
                restTemplate.getForObject(
                        urlGetStudentById,
                        ResponseObject.class,
                        idStu
                );
        assert responseObject != null;
        return StringToStudent(responseObject.getData().toString());
    }

    public WrapBooleanStr mapToCheckIfStudentPassed(int idClass) {
        ResponseObject responseObjectPreSubject =
                restTemplate.getForObject(
                        urlCheckIfStudentPassed,
                        ResponseObject.class,
                        idClass
                );
        WrapBooleanStr wrapBooleanStr = new WrapBooleanStr();
        assert responseObjectPreSubject != null;
        wrapBooleanStr.setMessage(responseObjectPreSubject.getMessage());
        wrapBooleanStr.setPresubject(StringToPreSubject(responseObjectPreSubject.getData().toString()));
        System.out.println(wrapBooleanStr.isPresubject());
        return wrapBooleanStr;
    }

    public WrapRegistrationStr mapToUpdateRegistration(Classes classes) {
        ResponseObject responseObjectRegistration =
                restTemplate.postForObject(
                        urlUpdateRegistration,
                        classes,
                        ResponseObject.class
        );
        WrapRegistrationStr wrapRegistrationStr = new WrapRegistrationStr();
        assert responseObjectRegistration != null;
        wrapRegistrationStr.setMessage(responseObjectRegistration.getMessage());
        wrapRegistrationStr.setRegistration(StringToRegistration(responseObjectRegistration.getData().toString()));
        return wrapRegistrationStr;

    }

    private Subject getSubjectById(int id) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        Subject resSubject = new Subject();
        Semester resSemester = new Semester();
        Year resYear = new Year();
        String externalServiceUrl = "http://localhost:8085/api/subject/" + id;
        String responseEntity =
                (String)restTemplate.getForObject(
                        externalServiceUrl,
                        String.class,
                        id
                );
        ResponseObject responseObjectClasses =
                (ResponseObject)mapper.readValue(
                        responseEntity,
                        ResponseObject.class
                );
        String subjectControllerMessage = responseObjectClasses.getMessage();
        String sol = responseObjectClasses.getData().toString().replace(",", "").replace("=", " ");
        String[] part = sol.split(" ");
        new ArrayList();
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

    private Classes StringToClasses(String s) {
        Classes classes = new Classes();
        s = s.replaceAll("^\\{|}$", "").replaceAll("=", " ").replaceAll(",", "");
        String[] key = s.split(" ");
        classes.setId(Integer.parseInt(key[1].trim()));
        classes.setIdClassString(key[3].trim());
        classes.setIdSubject(Integer.parseInt(key[5].trim()));
        return classes;
    }
    private Student StringToStudent(String s){
        Student student = new Student();
        s = s.replaceAll("^\\{|}$", "");
        String[] part = s.split(",");

        part[0] = part[0].replace("id=", "");
        part[1] = part[1].replace("name=", "");
        part[2] = part[2].replace("idStudentString=", "");

        student.setId(Integer.parseInt(part[0].trim()));
        student.setName(part[1].trim());
        student.setIdStudentString(part[2].trim());
        return student;
    }
    private Registration StringToRegistration(String s) {
        Registration registration = new Registration();
        s = s.replaceAll("^\\{|}$", "").replaceAll("=", " ").replaceAll(",", "");
        String[] key = s.split(" ");
        registration.setId(Integer.parseInt(key[1].trim()));
        registration.setIdStudent(Integer.parseInt(key[3].trim()));
        registration.setIdClasses(Integer.parseInt(key[5].trim()));
        return registration;
    }

    private boolean StringToPreSubject(String s) {
        s = s.replaceAll("^\\{|}$", "").replaceAll("=", " ").replaceAll(",", "");
        if(s.equalsIgnoreCase("true")) {
            return true;
        }
        else if (s.equalsIgnoreCase("false")) {
            return false;
        }
        else {
            return false;
        }
    }
}