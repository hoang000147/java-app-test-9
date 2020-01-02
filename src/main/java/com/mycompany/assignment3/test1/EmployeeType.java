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
public enum EmployeeType 
{
    ACADEMIC("ACADEMIC"), CONTRACTOR("CONTRACTOR"), ADMIN("ADMIN");
    
    private String string;
    
    EmployeeType(String string)
    {
        this.string = string;
    }
    
    public String getString()
    {
        return string;
    }
    
    public static EmployeeType parseCsv(String string)
    {
        switch(string)
        {
            case "ACADEMIC":
                return EmployeeType.ACADEMIC;

            case "CONTRACTOR":
                return EmployeeType.CONTRACTOR;

            case "ADMIN":
                return EmployeeType.ADMIN;

            default: return null;
        }
    }
}
