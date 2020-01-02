package com.mycompany.assignment3.test1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Asus
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Employee 
{
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private Gender gender;
    private String position;
    private EmployeeType employeeType;
    
    private static ArrayList<Employee> employees = new ArrayList<Employee>();
    
    public Employee(String id, EmployeeType employeeType, String position,
                    String firstName, String lastName, Gender gender, LocalDate dob)
    {
        this.id = id;
        this.employeeType = employeeType;
        this.position = position;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dob = dob;      
    }
    
    public String getId()
    {
        return id;
    }
    
    public String getFirstName()
    {
        return firstName;
    }
    
    public String getLastName()
    {
        return lastName;
    }
    
    public LocalDate getDob()
    {
        return dob;
    }
    
    public String getPosition()
    {
        return position;
    }
    
    public Gender getGender()
    {
        return gender;
    }
    
    public EmployeeType getEmployeeType()
    {
        return employeeType;
    }
    
    public void setId(String employeeId)
    {
        this.id = employeeId;
    }
    
    public void setEmployeeType(EmployeeType employeeType)
    {
        this.employeeType = employeeType;
    }
    
    public void setPosition(String position)
    {
        this.position = position;
    }
    
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    
    public void setGender(Gender gender)
    {
        this.gender = gender;
    }
    
    public void setDob(LocalDate dob)
    {
        this.dob = dob;
    }
    
    
    public static Employee parseCsv(String csvString)
    {
        DateTimeFormatter dobFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String[] parts = csvString.split(",");
        
        EmployeeType employeeType = EmployeeType.parseCsv(parts[1]);
        Gender gender = Gender.parseCsv(parts[5]);
        LocalDate dob = LocalDate.parse(parts[6], dobFormat);
                
        return new Employee(parts[0], employeeType, parts[2], parts[3], parts[4], gender, dob);
    }
    
    @Override
    public String toString() 
    {
        return "Employee (employeeId=" + id + ", firstName=" + firstName 
                + ", lastName=" + lastName + ", gender=" + gender
                + ", dob=" + dob  + ", employeeType=" + employeeType 
                + ", position=" + position + ")";
    }
    
    public static Comparator<Employee> FirstNameComparator = (Employee object1, Employee object2) -> object1.getFirstName().compareTo(object2.getFirstName());
    
    public static Comparator<Employee> LastNameComparator = (Employee object1, Employee object2) -> object1.getLastName().compareTo(object2.getLastName());
    
    public static Comparator<Employee> YoungerComparator = (Employee object1, Employee object2) -> {
        if (object1.getDob().isAfter((object2.getDob())))
            return -1;
        if (object1.getDob().isBefore((object2.getDob())))
            return 1;
        else return 0;
    };
    
    public static Comparator<Employee> OlderComparator = (Employee object1, Employee object2) -> {
        if (object1.getDob().isAfter((object2.getDob())))
            return 1;
        if (object1.getDob().isBefore((object2.getDob())))
            return -1;
        else return 0;
    };
    
    public static void printAllEmployee()
    {
        for (Employee employee : employees)
        {
            System.out.println(employee);
        }
    }
    
    public static void sortByFirstName()
    {
        Collections.sort(employees, FirstNameComparator);
    }
    
    public static void sortByLastName()
    {
        Collections.sort(employees, LastNameComparator);
    }
    
    public static void sortYounger()
    {
        Collections.sort(employees, YoungerComparator);
    }
    
    public static void sortOlder()
    {
        Collections.sort(employees, OlderComparator);
    }
}
