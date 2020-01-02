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
import javax.swing.JFrame;


public class Main
{
    
    public static void main(String[] args)
    {       
        OuterJFrame outerJFrame = new OuterJFrame();
        
        outerJFrame.setTitle("Employee System");
        outerJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        outerJFrame.pack();
        outerJFrame.setVisible(true);
    }
}
