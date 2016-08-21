package com.jbjohn.controller;

import com.jbjohn.objects.Employee;
import com.jbjohn.properties.Configurations;
import com.jbjohn.repositories.EmployeeStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

/**
 * Web controller
 */
@Controller
public class DisplayController {

    @Autowired
    private Configurations config;

    @Autowired
    private EmployeeStorage repo;

    @RequestMapping("/")
    String index(final Model model) {
        String dockerUri = "http://" + config.getEshost() + "/";
        model.addAttribute("dockerUri", dockerUri);
        return "default";
    }

    @RequestMapping("/search")
    String search(final Model model) {
        String dockerUri = "http://" + config.getEshost() + "/";
        model.addAttribute("dockerUri", dockerUri);
        return "search";
    }

    @RequestMapping("/postgres")
    String postgres(final Model model) {
        String dockerUri = "http://" + config.getEshost() + "/";
        model.addAttribute("dockerUri", dockerUri);
        return "postgres";
    }

    // add record
    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    String employee(Model model) {
        String dockerUri = "http://" + config.getEshost() + "/";
        model.addAttribute("dockerUri", dockerUri);
        model.addAttribute("employee", new Employee());
        return "employee";
    }

    // store and display
    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    String employeeResult(@ModelAttribute Employee employee, Model model) {
        String dockerUri = "http://" + config.getEshost() + "/";
        model.addAttribute("dockerUri", dockerUri);
        repo.save(employee);
        model.addAttribute("employee", employee);
        return "result";
    }

    // listing
    @RequestMapping("/employees")
    String employeeResult(Model model) {
        String dockerUri = "http://" + config.getEshost() + "/";
        model.addAttribute("dockerUri", dockerUri);

        Iterable<Employee> list = repo.findAll();

        ArrayList<Employee> employees = new ArrayList<>();

        if (repo.count() > 0) {
            for (Employee employee : list) {
                employees.add(employee);
            }
        }

        model.addAttribute("employees", employees);
        return "list";
    }
}
