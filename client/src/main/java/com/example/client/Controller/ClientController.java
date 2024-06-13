//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.client.Controller;

import com.example.client.DTO.Classes;
import com.example.client.DTO.ClassesDisplay;
import com.example.client.DTO.Process;
import com.example.client.DTO.Registration;
import com.example.client.DTO.response.*;
import com.example.client.service.ClientService;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClientController {
    @Autowired
    private HttpSession session;
    @Autowired
    private ClientService clientService;

    public ClientController() {
    }

    @GetMapping
    public String getHomePage(Model model) throws IOException {
        List<Classes> classes = clientService.getAllClasses();
        List<ClassesDisplay> classesDisplays = clientService.getAllClassesDisplays(classes);
        model.addAttribute("classes", classesDisplays);
        return "home";
    }

    @PostMapping({"/save"})
    public String saveRegistration(@RequestParam("idClasses") String[] idClassesStr, Model model) {
        List<Process> processes = new ArrayList();
        List<Integer> idClasses = new ArrayList();
        String[] var5 = idClassesStr;
        int var6 = idClassesStr.length;

        int idClass;
        String res;
        String name;
        for(idClass = 0; idClass < var6; ++idClass) {
            res = var5[idClass];
            idClasses.add(Integer.parseInt(res));
            System.out.println(res);
        }

        List<Registration> registrations = new ArrayList();
        Iterator var18 = idClasses.iterator();

        while(var18.hasNext()) {
            idClass = (Integer)var18.next();
            res = "";
            name = "Dang ky lop hoc phan cho sinh vien co ma sinh vien " + clientService.mapToGetStudentById(1).getIdStudentString() + " ";
            int count = 0;
            String classesControllerMessage = "CLASSES PROCESS ";
            String presubjectControllerMessage = "PRESUBJECT PROCESS ";
            String registrationControllerMessage = "REGISTRATION PROCESS ";
            Process process = new Process();
            WrapClassesStr wrapClassesStr = clientService.mapToGetClassesById(idClass);
            Classes classes = wrapClassesStr.getClasses();
            name += " cho lop hoc phan " + classes.getIdClassString() + " ";
            classesControllerMessage += wrapClassesStr.getMessage();
            res = res + classesControllerMessage + " \n ";
            count += 1;
            if (classes == null) {
                create_process(count, res, name, process, processes);
                System.out.println("END LINE = " + 74);
                continue;
            }
            WrapBooleanStr wrapBooleanStr = clientService.mapToCheckIfStudentPassed(classes.getId());
            System.out.println(wrapBooleanStr);
            boolean presubject = wrapBooleanStr.isPresubject();
            presubjectControllerMessage += wrapBooleanStr.getMessage();
            res = res + presubjectControllerMessage + " \n ";
            count += 1;
            if (!presubject) {
                create_process(count, res, name, process, processes);
                System.out.println("END LINE = " + 82);
            } else {
                WrapRegistrationStr wrapRegistrationStr = clientService.mapToUpdateRegistration(classes);
                Registration registration =wrapRegistrationStr.getRegistration();
                get_classes(registration);
                registrationControllerMessage += wrapRegistrationStr.getMessage();
                res = res + registrationControllerMessage + " \n ";
                count += 1;
                create_process(count, res, name, process, processes);
                System.out.println("END LINE = " + 89);
                registrations.add(registration);
            }
        }
        model.addAttribute("processes", processes);
        return "alert";
    }
    private void create_process(int count, String message, String name, Process process, List<Process> processes){
        System.out.println("/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/");
        System.out.println("PROCESS END HERE");
        System.out.println("COUNT = " + count);
        System.out.println("MESSAGE = " + message);
        System.out.println("/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/");
        if (count == 1) {
            process.setCount(33);
        } else if (count == 2) {
            process.setCount(67);
        } else {
            process.setCount(100);
        }
        process.setName(name);
        process.setMessage(process.getMessage() + message);
        processes.add(process);
    }
    private void get_classes(Registration registration) {
        System.out.println("==============================");
        System.out.println("ID CLASSES = " + registration.getIdClasses());
        System.out.println("==============================");
    }
}