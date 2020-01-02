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
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OuterJFrame extends JFrame
{
    private EmployeeSystem system;
    
    private JPanel outerPanel;
    
    private JPanel topOuterPanel;
    private JPanel centerOuterPanel;
    private JPanel bottomOuterPanel;
    
    private JTextArea displayArea;
    private JScrollPane scroll;
    
    private JButton mainButton;
    private JButton reportButton;
    
    private EmployeeJFrame employeeJFrame;
    private EmployeeSubJFrame subFrame;
    
    public OuterJFrame()
    {
        system = new EmployeeSystem();
        system.readData();

        initGUIComponent();
        initEventHandler();
        
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e)
            {
                system.saveData();
            }
        });
    }
    
    private void initGUIComponent()
    {
        initOuterPanel();
        add(outerPanel);
    }
    
    private void initOuterPanel()
    {
        outerPanel = new JPanel();
        
        BorderLayout layout = new BorderLayout();
        outerPanel.setLayout(layout);
        
        initTopOuterPanel();
        initCenterOuterPanel();
        initBottomOuterPanel();
        
        outerPanel.add(topOuterPanel, BorderLayout.NORTH);
        outerPanel.add(centerOuterPanel, BorderLayout.CENTER);
        outerPanel.add(bottomOuterPanel, BorderLayout.SOUTH);
    }
    
    private void initTopOuterPanel()
    {
        topOuterPanel = new JPanel();
        
        displayArea = new JTextArea(20, 40);
        scroll = new JScrollPane(displayArea);
        
        topOuterPanel.add(scroll);
        
        displayInformation();
    }
    
    private void displayInformation()
    {
        for (Employee employee : system.getEmployeeList())
            displayArea.append(employee + "\n");
    }
    
    
    
    private void initCenterOuterPanel()
    {
        centerOuterPanel = new JPanel();
        
        FlowLayout layout = new FlowLayout();
        centerOuterPanel.setLayout(layout);
        
        centerOuterPanel.add(new JLabel("Total Employees Read:"));
        
        JTextField textField = new JTextField(10);
        textField.setText("" + system.getEmployeeList().size());
        centerOuterPanel.add(textField);
    }
    
    
    
    private void initBottomOuterPanel()
    {
        bottomOuterPanel = new JPanel();
        
        FlowLayout layout = new FlowLayout();
        bottomOuterPanel.setLayout(layout);
        
        mainButton = new JButton("Main Program");
        bottomOuterPanel.add(mainButton);
        
        reportButton = new JButton("Report Program");
        bottomOuterPanel.add(reportButton);
    }
    
    private void initEventHandler()
    {
        initMainButtonHandler();
        initReportButtonHandler();
    }
    
    private void initMainButtonHandler()
    {
        mainButton.addActionListener((ActionEvent e) -> {
            employeeJFrame = new EmployeeJFrame(system);
            
            employeeJFrame.setTitle("Employee System Program");
            employeeJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            employeeJFrame.pack();
            employeeJFrame.setVisible(true);
        });
    }
    
    private void initReportButtonHandler()
    {
        reportButton.addActionListener((ActionEvent e) -> {
            subFrame = new EmployeeSubJFrame(system);
            
            subFrame.setTitle("All Employees Display Function");
            subFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            subFrame.pack();
            subFrame.setVisible(true);
        });
    }
}
