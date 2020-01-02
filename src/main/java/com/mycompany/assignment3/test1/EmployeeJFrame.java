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
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class EmployeeJFrame extends JFrame
{
    private EmployeeSystem system;
    
    private JPanel employeePanel;
    private JPanel employeeTopPanel;
    private JPanel employeeCenterPanel;
    private JPanel employeeBottomPanel;
    
    private JTextField employeeIdTF;
    private JButton searchButton;
    
    private JTextField firstNameTF;
    private JTextField lastNameTF;
    private JTextField dobTF;
    private JRadioButton maleRB;
    private JRadioButton femaleRB;
    private ButtonGroup genderBG;
    private JComboBox<EmployeeType> employeeTypeCB;
    private JTextField positionTF;
    
    private JPanel subTopPanel;
    private JPanel subBottomPanel;
    
    private JButton addButton;
    private JButton updateButton;
    private JButton saveButton;
    //private JButton displayButton;
    
    private JTextArea messageTextField;
    
    private boolean employeeFound;  //to see if an employee is found by search button
    private boolean addConfirm;  //to see if it is in add process
    private boolean updateConfirm;  //to see if it is in update process
    private boolean updateClicked; //to see if the update button is clicked or not
    
    //private EmployeeSubJFrame subFrame;
    
    public EmployeeJFrame(EmployeeSystem system)
    {
        this.system = system;
        
        initGUIComponent();
        initEventHandler();
        
        addConfirm = false;
        updateConfirm = false;
    }
    
    private void initGUIComponent()
    {
        initEmployeePanel();
        add(employeePanel);
    }
    
    private void initEventHandler()
    {
        initSearchButtonHandler();
        initAddButtonHandler();
        initUpdateButtonHandler();
        initSaveButtonHandler();
        //initDisplayButtonHandler();
    }
    
    
    
    private void initEmployeePanel()
    {
        employeePanel = new JPanel();
        
        BorderLayout layout = new BorderLayout();
        employeePanel.setLayout(layout);
        
        initEmployeeTopPanel();
        initEmployeeCenterPanel();
        initEmployeeBottomPanel();
        
        employeePanel.add(employeeTopPanel, BorderLayout.NORTH);
        employeePanel.add(employeeCenterPanel, BorderLayout.CENTER);
        employeePanel.add(employeeBottomPanel, BorderLayout.SOUTH);
    }
    
    
    
    private void initEmployeeTopPanel()
    {
        employeeTopPanel = new JPanel();
        
        FlowLayout layout = new FlowLayout();
        employeeTopPanel.setLayout(layout);
        
        employeeTopPanel.add(new JLabel("Employee ID"));
        
        employeeIdTF = new JTextField(20);
        employeeTopPanel.add(employeeIdTF);
        
        searchButton = new JButton("Search");
        employeeTopPanel.add(searchButton);
    }
    
    
    
    
    private void initEmployeeCenterPanel()
    {
        employeeCenterPanel = new JPanel();
        
        GridLayout layout = new GridLayout(0, 2);
        employeeCenterPanel.setLayout(layout);
        
        initFirstNameGUI();
        initLastNameGUI();
        initDobGUI();
        initGenderGUI();
        initEmployeeTypeGUI();
        initPositionGUI();
    }
    
    private void initFirstNameGUI()
    {
        employeeCenterPanel.add(new JLabel("First Name"));
        firstNameTF = new JTextField(20);
        employeeCenterPanel.add(firstNameTF);
    }
    
    private void initLastNameGUI()
    {
        employeeCenterPanel.add(new JLabel("Last Name"));
        lastNameTF = new JTextField(20);
        employeeCenterPanel.add(lastNameTF);
    }
    
    private void initDobGUI()
    {
        employeeCenterPanel.add(new JLabel("DOB (dd-mm-yyyy)"));
        dobTF = new JTextField(20);
        employeeCenterPanel.add(dobTF);
    }
    
    private void initGenderGUI()
    {
        maleRB = new JRadioButton("M");
        femaleRB = new JRadioButton("F");
        
        genderBG = new ButtonGroup();
        genderBG.add(maleRB);
        genderBG.add(femaleRB);
        
        JPanel genderPanel = new JPanel();
        genderPanel.add(maleRB);
        genderPanel.add(femaleRB);
        
        employeeCenterPanel.add(new JLabel("Gender"));
        employeeCenterPanel.add(genderPanel);
    }
    
    private void initEmployeeTypeGUI()
    {
        EmployeeType[] employeeTypeOptions = {EmployeeType.ACADEMIC, EmployeeType.ADMIN,
                                              EmployeeType.CONTRACTOR};
        employeeTypeCB = new JComboBox<>(employeeTypeOptions);
        
        employeeCenterPanel.add(new JLabel("Employee Type"));
        employeeCenterPanel.add(employeeTypeCB);
        employeeTypeCB.setSelectedIndex(-1);
    }
    
    private void initPositionGUI()
    {
        employeeCenterPanel.add(new JLabel("Position"));
        positionTF = new JTextField(20);
        employeeCenterPanel.add(positionTF);
    } 
    
    private void initEmployeeBottomPanel()
    {
        employeeBottomPanel = new JPanel();
        
        BorderLayout layout = new BorderLayout();
        employeeBottomPanel.setLayout(layout);
        
        initSubTopPanel();
        initSubBottomPanel();
        
        employeeBottomPanel.add(subTopPanel, BorderLayout.NORTH);
        employeeBottomPanel.add(subBottomPanel, BorderLayout.SOUTH);     
    }
    
    private void initSubTopPanel()
    {
        subTopPanel = new JPanel();
        
        FlowLayout layout = new FlowLayout();
        subTopPanel.setLayout(layout);
        
        addButton = new JButton("Add Employee");
        subTopPanel.add(addButton);
        updateButton = new JButton("Update Employee");
        subTopPanel.add(updateButton);
        //displayButton = new JButton("Display All Employees");
        //employeeBottomPanel.add(displayButton);
        saveButton = new JButton("Save Information");
        subTopPanel.add(saveButton);
    }
    
    private void initSubBottomPanel()
    {
        subBottomPanel = new JPanel();
        
        FlowLayout layout = new FlowLayout();
        subBottomPanel.setLayout(layout);
        
        subBottomPanel.add(new JLabel("Message: "));
        messageTextField = new JTextArea(3,25);
        messageTextField.setLineWrap(true);
        messageTextField.setWrapStyleWord(true);
        subBottomPanel.add(messageTextField);
        
        String message = "If you want to add an employee, click Add Employee Button.\n";
        message += "If you want to update an employee, ckick Update Employee Button";
        displayMessage(message);
    }
    
    private void initSearchButtonHandler()
    {
        searchButton.addActionListener((ActionEvent e) -> {
            employeeFound = false;
            String employeeId = employeeIdTF.getText();
            
            if (employeeId.equals(""))
                displayMessage("Employee ID cannot be empty!");
            else
            {
                Employee result = system.searchById(employeeId);
            
                if (result != null)
                {
                    displayEmployee(result);
                    displayMessage("Employee Found.");
                    employeeFound = true;
                }
                else displayMessage("Employee Not Found! Try again");
                
                if (employeeFound && updateClicked) 
                {
                    //initUpdateSaveButtonHandler();
                    updateConfirm = true;
                    displayMessage("Please input the information you want to update for this employee then click Save Information button.\n"
                                    + "If you want to update another employee without saving this one, please click Update Employee button.");               
                }
                else if (updateClicked) displayMessage("Employee Not Found! Try again.");
            }
        });
    }
    
    private void displayEmployee(Employee result)
    {
        firstNameTF.setText(result.getFirstName());
        lastNameTF.setText(result.getLastName());
            
        LocalDate dob = result.getDob();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dobString = formatter.format(dob);
        dobTF.setText(dobString);
            
        Gender gender = result.getGender();
        if (gender == Gender.M)
            maleRB.setSelected(true);
        else femaleRB.setSelected(true);
            
        EmployeeType employeeType = result.getEmployeeType();
        employeeTypeCB.setSelectedItem(employeeType);
            
        positionTF.setText(result.getPosition());
    }
    
    private void displayMessage(String message)
    {
        messageTextField.setText(message);
    }
    
    private void initAddButtonHandler()
    {
        addButton.addActionListener((ActionEvent e) -> {
            employeeIdTF.setText(system.getNewId());
            resetAllFields();
            
            updateConfirm = false;  //make sure that updating employee is not chosen
            
            displayMessage("Please input information for new employee. Click Save Information button when you are ready.");
            addConfirm = true;
        });
    }
    
    private void initUpdateButtonHandler()
    {
        updateButton.addActionListener((ActionEvent e) ->
        {
            resetAllFields(); //reset all fields except the current Employee ID if there is one
            
            employeeFound = false;  //reset just in case the user wants to update another employee without saving this current one
            addConfirm = false;  //confirm that adding employee is not chosen
            updateClicked = true;
            
            displayMessage("Please input the employee ID that you want to update and click Search button.\n");          
        });
    }
    
    private void initSaveButtonHandler()
    {
        saveButton.addActionListener((ActionEvent e) ->
        {
            if ((updateConfirm && employeeFound) || addConfirm)
            {
                String employeeId = employeeIdTF.getText();            
                Employee result = system.searchById(employeeId);
            
                String message = "";
            
            
                boolean check = true;
                
                String firstName = firstNameTF.getText();
                if (firstName.equals(""))
                {
                    message += "Missing first name. ";
                    check = false;
                }
                
                String lastName = lastNameTF.getText();
                if (lastName.equals(""))
                {
                    message += "Missing last name. ";
                    check = false;
                }

                String dobString = dobTF.getText();
                DateTimeFormatter dobFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate dob = null;
                try
                {
                    if (dobString.equals(""))
                    {
                        message += "Missing DOB. ";
                        check = false;
                    }
                    else 
                    {
                        dob = LocalDate.parse(dobString, dobFormat);                
                        if (dob.isBefore(LocalDate.of(1900, 1, 1)))
                        {
                            message += "DOB cannot be before 01-01-1900. ";
                            check = false;
                        }
                    }
                }
                catch(Exception exception)
                {
                    message += "Wrong DOB format. ";
                    check = false;
                }
     
                EmployeeType employeeType = (EmployeeType) employeeTypeCB.getSelectedItem();
                if (employeeType == null)
                    message += "Missing employee type. ";

                Gender gender = null;
                if (maleRB.isSelected())
                    gender = Gender.M;
                if (femaleRB.isSelected())
                    gender = Gender.F;

                if (gender == null)
                {
                    message += "Missing gender.";
                    check = false;
                }

                String position = positionTF.getText();
                if (position.equals(""))
                {
                    message += "Missing position. ";
                    check = false;
                }
                
                if (check)
                {
                    if (result == null)
                    {
                        Employee newEmployee = new Employee(employeeId, employeeType, position,
                                   firstName, lastName, gender, dob);
                        system.addEmployee(newEmployee);
                        displayMessage("Adding Successful!\n If you want to add another employee, please click Add Employee button.");
                        addConfirm = false;
                    }                        
                    else 
                    {
                        system.updateEmployee(result, employeeId, employeeType, position,
                                   firstName, lastName, gender, dob);
                        displayMessage("Updating Successful!\n If you want to update another employee please click "
                                    + "Update Employee Button again");
                        employeeFound = false;
                        updateConfirm = false;
                        updateClicked = false;
                    }                                                          
                }
                else 
                {
                    message += "\n If you want to cancel or reset this process, you can exit the program or click any buttons in the program except Save Information.";
                    displayMessage(message);
                }
            }
            else if (updateClicked)
            {
                displayMessage("Please search with the correct employee ID first");
            }
            else
            {
                displayMessage("Haven't chosen any process. Please choose one before pressing this button");
            }
        });
    }
    
    private void resetAllFields()
    //reset all fields except employeeId
    {
        firstNameTF.setText("");
        lastNameTF.setText("");
        dobTF.setText("");
        genderBG.clearSelection();
        employeeTypeCB.setSelectedIndex(-1);   
        positionTF.setText("");
    }
}
