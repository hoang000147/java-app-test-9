/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.assignment3.test1;

/**
 *
 * @author Asus
 */
import java.io.*;
import java.util.*;
import static java.lang.System.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmployeeSystem 
{
    private final String fileName = "./A3.csv";
    private ArrayList<Employee> employees;
    private String lastId = "0000000";  //current biggest ID in the system
        
    public EmployeeSystem()
    {
        this.employees = new ArrayList<>();
    }
    
    public ArrayList<Employee> getEmployeeList()
    {
        return employees;
    }
    
    public String getNewId()  //return new id for adding employee
    {
        if (searchById(lastId) != null)
        //make sure lastId only increases after successfully adding the information
        //in case the user want to reset adding employee process
        {
            int idInt = Integer.parseInt(lastId);
            idInt++;
            lastId = String.format("%07d", idInt);
            //make sure to have at least 7 digits if there are 0s at the start
        }     
        return lastId;
    }
    
    public void readData()
    {       
        try
        {
            File file = new File(fileName);
            
            if (file.exists())
            {
                FileReader fr = new FileReader(file);
                BufferedReader reader = new BufferedReader(fr);
                
                String header = reader.readLine();
                    
                while (true)
                {
                    String line = reader.readLine();
                    
                    if (line == null)
                    {
                        reader.close();
                        break;
                    }
                    Employee newEmployee = Employee.parseCsv(line);                    
                    addEmployee(newEmployee);
                    
                    if (newEmployee.getId().compareTo(lastId) > 0)
                        lastId = newEmployee.getId();
                    //update lastId if the new id is bigger than lastId
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void saveData()
    {
        try
        {
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter writer = new BufferedWriter(fw);
            
            writer.write(getCsvHeader());
            writer.newLine();
            
            for (Employee employee : employees)
            {
                writer.write(getCsvString(employee));
                writer.newLine();
            }
            
            writer.close();
        }
        catch(IOException exception)
        {
            exception.printStackTrace();
        }            
    }
    
    private String getCsvString(Employee employee)
    {
        DateTimeFormatter dobFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        String csv = employee.getId() + ","
                    + employee.getEmployeeType() + ","
                    + employee.getPosition() + "," 
                    + employee.getFirstName() + ","
                    + employee.getLastName() + ","
                    + employee.getGender() + ","
                    + dobFormat.format(employee.getDob());
        
        return csv;
    }
    
    private String getCsvHeader()
    {
        return "EmployeeId,EmployeeType,Position,FirstName,LastName,Gender,DOB";
    }
    
    public void addEmployee(Employee employee)
    {
        employees.add(employee);
    }
    
    public void updateEmployee(Employee employee, String employeeId, 
                                EmployeeType employeeType, String position,
                                String firstName, String lastName, 
                                Gender gender, LocalDate dob)
    {
        employee.setId(employeeId);
        employee.setEmployeeType(employeeType);
        employee.setPosition(position);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setGender(gender);
        employee.setDob(dob);
    }
    
    public void printAllEmployees()
    {
        for (Employee employee : employees)
            out.println(employee);
    }
    
    public Employee searchById(String id)
    {
        for (Employee employee : employees)
            if (employee.getId().equals(id))
                return employee;
        return null;
    }
    
    public Comparator<Employee> ascIdComparator = (Employee object1, Employee object2) 
            -> object1.getId().compareTo(object2.getId());
    
    public Comparator<Employee> descIdComparator = ascIdComparator.reversed();
    
    public Comparator<Employee> ascFirstNameComparator = (Employee object1, Employee object2) 
            -> object1.getFirstName().compareTo(object2.getFirstName());
    
    public Comparator<Employee> descFirstNameComparator = ascFirstNameComparator.reversed();
    
    public Comparator<Employee> ascLastNameComparator = (Employee object1, Employee object2)
            -> object1.getLastName().compareTo(object2.getLastName());
    
    public Comparator<Employee> descLastNameComparator = ascLastNameComparator.reversed();
    
    public Comparator<Employee> ascPositionComparator = (Employee object1, Employee object2)
            -> object1.getPosition().compareTo(object2.getPosition());
    
    public Comparator<Employee> descPositionComparator = ascPositionComparator.reversed();
    
    public Comparator<Employee> ascEmployeeTypeComparator = (Employee object1, Employee object2)
            -> object1.getEmployeeType().getString().compareTo(object2.getEmployeeType().getString());
    
    public Comparator<Employee> descEmployeeTypeComparator = ascEmployeeTypeComparator.reversed();
    
    public Comparator<Employee> ascDobComparator = (Employee object1, Employee object2)
            -> object1.getDob().compareTo(object2.getDob());
    
    public Comparator<Employee> descDobComparator = ascDobComparator.reversed();
    
    /*public void systemSort(String sortType, int order)
    {
        if (sortType.equals("Employee ID") && order == 1)
            Collections.sort(employees, ascIdComparator);
        if (sortType.equals("Employee ID") && order == 2)
            Collections.sort(employees, descIdComparator);
        
        if (sortType.equals("First name") && order == 1)
            Collections.sort(employees, ascFirstNameComparator);
        if (sortType.equals("First name") && order == 2)
            Collections.sort(employees, descFirstNameComparator);
        
        if (sortType.equals("Last name") && order == 1)
            Collections.sort(employees, ascLastNameComparator);
        if (sortType.equals("Last name") && order == 2)
            Collections.sort(employees, descLastNameComparator);
        
        if (sortType.equals("Position") && order == 1)
            Collections.sort(employees, ascPositionComparator);
        if (sortType.equals("Position") && order == 2)
            Collections.sort(employees, descPositionComparator);
        
        if (sortType.equals("Employee Type") && order == 1)
            Collections.sort(employees, ascEmployeeTypeComparator);
        if (sortType.equals("Employee Type") && order == 2)
            Collections.sort(employees, descEmployeeTypeComparator);
        
        if (sortType.equals("DOB") && order == 1)
            Collections.sort(employees, ascDobComparator);
        if (sortType.equals("DOB") && order == 2)
            Collections.sort(employees, descDobComparator);
    }*/
    
    public void sort(int orderNumber, int orderType)
    {
        switch(orderNumber)
        {
            case 1: 
                if (orderType == 1) Collections.sort(employees, ascIdComparator);
                else Collections.sort(employees, descIdComparator);
                break;
        
            case 2: 
                if (orderType == 1) Collections.sort(employees, ascFirstNameComparator);
                else Collections.sort(employees, descFirstNameComparator);
                break;
        
            case 3:
                if (orderType == 1) Collections.sort(employees, ascLastNameComparator);
                else Collections.sort(employees, descLastNameComparator);
                break;
            
            case 4:
                if (orderType == 1) Collections.sort(employees, ascPositionComparator);
                else Collections.sort(employees, descPositionComparator);
                break;
        
            case 5:
                if (orderType == 1) Collections.sort(employees, ascEmployeeTypeComparator);
                else Collections.sort(employees, descEmployeeTypeComparator);
                break;
        
            case 6:
                if (orderType == 1) Collections.sort(employees, ascDobComparator);
                else Collections.sort(employees, descDobComparator);
                break;
            
            default: break;//do nothing
        }
    }
}
