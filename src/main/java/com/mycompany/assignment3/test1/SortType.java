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
public enum SortType 
{
    ID("Employee ID", 1), FIRSTNAME("First name", 2), LASTNAME("Last name", 3),
    POSITION("Position", 4), EMPLOYEETYPE("EmployeeType", 5), DOB("DOB", 6);
    
    private String displayString;
    private int orderNumber;
    
    SortType(String displayString, int orderNumber)
    {
        this.displayString = displayString;
        this.orderNumber = orderNumber;
    }
    
    @Override
    public String toString()
    {
        return displayString;
    }
    
    public int getOrderNumber()
    {
        return orderNumber;
    }
    
    public String getString()
    {
        return displayString;
    }
}
