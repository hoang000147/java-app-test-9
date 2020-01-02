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
import java.util.ArrayList;
import javax.swing.table.*;

public class EmployeeSubJFrame extends JFrame
{
    private EmployeeSystem system;
    
    private JPanel employeePanel;
    private JPanel employeeTopPanel;
    private JPanel employeeCenterPanel;
    //private JPanel employeeBottomPanel;
    
    private JComboBox<SortType> sortOptions;
    private JRadioButton ascendingRB;
    private JRadioButton descendingRB;
    private JButton displayButton;
    
    private JScrollPane scroll;   
    private JTable displayTable;
    private DefaultTableModel model;
    
    public EmployeeSubJFrame(EmployeeSystem system)
    {
        this.system = system;
        initGUIComponent();
        initEventHandler();        
    }
    
    private void initGUIComponent()
    {
        initEmployeePanel();
        add(employeePanel);
    }
    
    private void initEmployeePanel()
    {
        employeePanel = new JPanel();
        
        BorderLayout layout = new BorderLayout();
        employeePanel.setLayout(layout);
        
        initEmployeeTopPanel();
        initEmployeeCenterPanel();
        //initEmployeeBottomPanel();
        
        employeePanel.add(employeeTopPanel, BorderLayout.NORTH);
        employeePanel.add(employeeCenterPanel, BorderLayout.CENTER);
        //employeePanel.add(employeeBottomPanel, BorderLayout.SOUTH);
    }
    
    private void initEmployeeTopPanel()
    {
        employeeTopPanel = new JPanel();
        
        FlowLayout layout = new FlowLayout();
        employeeTopPanel.setLayout(layout);
        
        employeeTopPanel.add(new JLabel("Sorted by"));
        
        SortType[] sortTypeOptions = {SortType.ID, SortType.FIRSTNAME, SortType.LASTNAME,
                                      SortType.POSITION, SortType.EMPLOYEETYPE, SortType.DOB};
        sortOptions = new JComboBox<>(sortTypeOptions);
        employeeTopPanel.add(sortOptions);
        sortOptions.setSelectedIndex(-1);
        
        ascendingRB = new JRadioButton("Ascending");
        descendingRB = new JRadioButton("Descending");       
        ButtonGroup orderRB = new ButtonGroup();
        orderRB.add(ascendingRB);
        orderRB.add(descendingRB);       
        JPanel orderPanel = new JPanel();
        orderPanel.add(ascendingRB);
        orderPanel.add(descendingRB);   
        employeeTopPanel.add(orderPanel);
        
        displayButton = new JButton("Display");
        employeeTopPanel.add(displayButton);
    }
    
    private void initEmployeeCenterPanel()
    {
        employeeCenterPanel = new JPanel();
        
        String[] columnHeader = {"ID", "First name", "Last name", "Gender", 
                                 "DOB", "Employee type", "Position"};
        
        model = new DefaultTableModel();
        model.setColumnIdentifiers(columnHeader);
        
        displayTable = new JTable(model);
        displayTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        displayTable.setPreferredScrollableViewportSize(new Dimension(650, 300));
        displayTable.setPreferredSize(new Dimension(650, 300));
        
        
        JTableHeader header = displayTable.getTableHeader();
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.LEFT);
            
        //scroll = new JScrollPane(displayTable, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll = new JScrollPane(displayTable);
        //scroll.setPreferredSize(new Dimension(650, 300));
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        employeeCenterPanel.add(scroll);
        
        //displayInformation();
        //resizeColumnWidth();
    }
    
    public void resizeColumnWidth()
    {
        TableColumnModel columnModel = displayTable.getColumnModel();
        for (int column = 0; column < displayTable.getColumnCount(); column++)
        {
            int width = 50; //min = 50
            for (int row = 0; row < displayTable.getRowCount(); row++)
            {
                TableCellRenderer renderer = displayTable.getCellRenderer(row, column);
                Component component = displayTable.prepareRenderer(renderer, row, column);
                width = Math.max(component.getPreferredSize().width + 20, width);
            }
            
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }
    
    private void displayInformation()
    {
        model.setRowCount(0);
        ArrayList<Employee> employees = system.getEmployeeList();
        String[] row = new String[7];
        
        for (Employee employee : employees)
        {
            row[0] = employee.getId();
            row[1] = employee.getFirstName();
            row[2] = employee.getLastName();
            row[3] = employee.getGender().toString();
            row[4] = employee.getDob().toString();
            row[5] = employee.getEmployeeType().toString();
            row[6] = employee.getPosition();
            
            model.addRow(row);
        }
    }
    
    private void initEventHandler()
    {
        initDisplayButtonHandler();
    }
    
    private void initDisplayButtonHandler()
    {
        displayButton.addActionListener((ActionEvent e) ->
        {
            boolean check = true;
            
            int orderNumber = 0;
            SortType employeeType = (SortType) sortOptions.getSelectedItem();
            if (employeeType != null) orderNumber = employeeType.getOrderNumber();

            int orderType = 0;
            if (ascendingRB.isSelected())
                orderType = 1;
            if (descendingRB.isSelected())
                orderType = 2;
            
            if (orderNumber == 0)
            {
                check = false;
                JOptionPane.showMessageDialog(this, "Please choose what attribute you want to sort by");
            }
            if (orderType == 0)
            {
                check = false;
                JOptionPane.showMessageDialog(this, "Please choose the order you want to sort by");               
            }
            
            if (check)
            {
                system.sort(orderNumber, orderType);

                displayInformation();
                resizeColumnWidth();
            } 
        });
    }
}
