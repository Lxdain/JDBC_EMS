import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class GUI extends JFrame {
    private JTextField nameField;
    private JTextField ageField;
    private JTextField addressField;
    private JTextField salaryField;
    private JButton searchButton;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JTextField searchField;
    private JLabel headerIcon;
    private JLabel titleText;
    private JLabel nameLabel;
    private JLabel ageLabel;
    private JLabel addressLabel;
    private JLabel salaryLabel;
    private JPanel accountPanel;
    private JComboBox<String> outputComboBox;
    private JButton showAllButton;
    private JButton saveButton;
    private JLabel outputLabel;
    private Functionality functionality;
    private Employee selectedEmployee; // Store selected employee

    public GUI() {
        setTitle("Employee Management System");
        setContentPane(accountPanel);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        functionality = new Functionality();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String address = addressField.getText();
                double salary = Double.parseDouble(salaryField.getText());
                Employee employee = new Employee(functionality.getNextEmployeeId(), name, age, address, salary);
                try {
                    functionality.addEmployee(employee);
                    outputComboBox.addItem(employee.getName()); // Add employee name to JComboBox
                    clearFields();
                } catch (SQLException ex) {
                    outputComboBox.addItem("Failed to add employee. Error: " + ex.getMessage()); // Add error message to JComboBox (needs to be corrected so that it displays in the outputLabel)
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedEmployee != null) {
                    String name = nameField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    String address = addressField.getText();
                    double salary = Double.parseDouble(salaryField.getText());
                    Employee updatedEmployee = new Employee(selectedEmployee.getId(), name, age, address, salary);
                    try {
                        functionality.editEmployee(updatedEmployee);
                        outputLabel.setText("Employee edited successfully!");
                        outputComboBox.removeItem(selectedEmployee.getName()); // Remove old employee name from JComboBox
                        outputComboBox.addItem(updatedEmployee.getName()); // Add updated employee name to JComboBox
                        clearFields();
                    } catch (SQLException ex) {
                        outputLabel.setText("Failed to update employee. Error: " + ex.getMessage());
                    }
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedEmployee != null) {
                    try {
                        functionality.deleteEmployee(selectedEmployee.getId());
                        outputComboBox.removeItem(selectedEmployee.getName()); // Remove employee from JComboBox
                        clearFields();
                    } catch (SQLException ex) {
                        outputComboBox.addItem("Failed to delete employee. Error: " + ex.getMessage()); // Add error message to JComboBox (needs to be corrected so that it displays in the outputLabel)
                    }
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchCriteria = searchField.getText();
                try {
                    List<Employee> employees = functionality.searchEmployees(searchCriteria);
                    displayEmployees(employees);
                    clearFields();
                } catch (SQLException ex) {
                    outputComboBox.addItem("Failed to search employees. Error: " + ex.getMessage()); // Add error message to JComboBox (needs to be corrected so that it displays in the outputLabel)
                }
            }
        });

        showAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Employee> employees = functionality.retrieveEmployees();
                    displayEmployees(employees);
                    clearFields();
                } catch (SQLException ex) {
                    outputComboBox.addItem("Failed to retrieve employees. Error: " + ex.getMessage()); // Add error message to JComboBox (needs to be corrected so that it displays in the outputLabel)
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedEmployee != null) {
                    String name = nameField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    String address = addressField.getText();
                    double salary = Double.parseDouble(salaryField.getText());
                    Employee updatedEmployee = new Employee(selectedEmployee.getId(), name, age, address, salary);
                    try {
                        functionality.editEmployee(updatedEmployee);
                        outputComboBox.addItem("Employee updated successfully!"); // Add success message to JComboBox (wrong, needs fixing)
                        clearFields();
                    } catch (SQLException ex) {
                        outputComboBox.addItem("Failed to update employee. Error: " + ex.getMessage()); // Add error message to JComboBox
                    }
                }
            }
        });

        outputComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedEmployeeName = (String) outputComboBox.getSelectedItem();
                if (selectedEmployeeName != null) {
                    try {
                        Employee employee = functionality.getEmployeeByName(selectedEmployeeName);
                        if (employee != null) {
                            selectedEmployee = employee;
                            setEmployeeFields(employee); // Prepopulate fields with selected employee information
                            displayEmployeeInfo(employee);
                        }
                    } catch (SQLException ex) {
                        outputLabel.setText("Failed to retrieve employee. Error: " + ex.getMessage());
                    }
                }
            }
        });
    }

    private void displayEmployees(List<Employee> employees) {
        outputComboBox.removeAllItems(); // Clear previous items
        for (Employee employee : employees) {
            outputComboBox.addItem(employee.getName()); // Add employee name to JComboBox
        }
    }

    private void clearFields() {
        nameField.setText("");
        ageField.setText("");
        addressField.setText("");
        salaryField.setText("");
        searchField.setText("");
        selectedEmployee = null;
    }

    private void setEmployeeFields(Employee employee) {
        nameField.setText(employee.getName());
        ageField.setText(String.valueOf(employee.getAge()));
        addressField.setText(employee.getAddress());
        salaryField.setText(String.valueOf(employee.getSalary()));
    }

    private void displayEmployeeInfo(Employee employee) {
        String info = "<html><b>ID:</b> " + employee.getId() + "<br>"
                + "<b>Name:</b> " + employee.getName() + "<br>"
                + "<b>Age:</b> " + employee.getAge() + "<br>"
                + "<b>Address:</b> " + employee.getAddress() + "<br>"
                + "<b>Salary:</b> " + employee.getSalary() + "</html>";
        outputLabel.setText(info);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI();
            }
        });
    }
}
