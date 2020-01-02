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
public enum Gender 
{
    M, F;
  
    public static Gender parseCsv(String string)
    {
        switch(string)
        {
            case "M":
                return Gender.M;

            case "F":
                return Gender.F;
                
            default: return null;
        }
    }
}
