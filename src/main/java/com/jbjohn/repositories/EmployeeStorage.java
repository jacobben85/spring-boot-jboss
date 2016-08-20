package com.jbjohn.repositories;

import com.jbjohn.objects.Employee;
import org.springframework.data.repository.CrudRepository;

/**
 */
public interface EmployeeStorage extends CrudRepository<Employee, String> {
    Employee findFirstByDisplayNameIsNull();
}