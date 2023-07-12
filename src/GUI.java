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
                String ageText = ageField.getText();
                String address = addressField.getText();
                String salaryText = salaryField.getText();

                if (!isValidNumber(ageText) || !isValidNumber(salaryText)) {
                    outputLabel.setText("Invalid input type. These fields only accept numeric parameters.");
                    clearOutputLabelAfterDelay(3000);
                    return;
                }

                int age = Integer.parseInt(ageText);
                double salary = Double.parseDouble(salaryText);

                Employee employee = new Employee(functionality.getNextEmployeeId(), name, age, address, salary);
                try {
                    functionality.addEmployee(employee);
                    outputComboBox.addItem(employee.getName()); // Add employee name to JComboBox
                    clearFields();
                } catch (SQLException ex) {
                    outputLabel.setText("Failed to add employee. Error: " + ex.getMessage());
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
                        outputLabel.setText("Employee Successfully Deleted!");

                        // Delayed clearing of outputLabel after 3 seconds
                        Timer timer = new Timer(3000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                outputLabel.setText("");
                            }
                        });
                        timer.setRepeats(false);
                        timer.start();
                    } catch (SQLException ex) {
                        outputLabel.setText("Failed to delete employee. Error: " + ex.getMessage()); // Update outputLabel with the error message
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
                    outputLabel.setText("Failed to search employees. Error: " + ex.getMessage()); // Update outputLabel with the error message
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
                    outputLabel.setText("Failed to retrieve employees. Error: " + ex.getMessage()); // Update outputLabel with the error message
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedEmployee != null) {
                    String name = nameField.getText();
                    String ageText = ageField.getText();
                    String address = addressField.getText();
                    String salaryText = salaryField.getText();

                    if (!isValidNumber(ageText)) {
                        outputLabel.setText("Invalid input type. Age field only accepts numeric parameters.");
                        clearOutputLabelAfterDelay(3000);
                        return;
                    }

                    int age = Integer.parseInt(ageText);
                    double salary = 0.0;

                    try {
                        salary = Double.parseDouble(salaryText);
                    } catch (NumberFormatException ex) {
                        outputLabel.setText("Invalid input type. Salary field only accepts numeric parameters.");
                        clearOutputLabelAfterDelay(3000);
                        return;
                    }

                    Employee updatedEmployee = new Employee(selectedEmployee.getId(), name, age, address, salary);
                    try {
                        functionality.editEmployee(updatedEmployee);
                        outputLabel.setText("Employee edited successfully!");
                        clearFields();
                    } catch (SQLException ex) {
                        outputLabel.setText("Failed to update employee. Error: " + ex.getMessage());
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
                            setEmployeeFields(employee); // Populate the fields with the selected employee info
                            displayEmployeeInfo(employee);
                        }
                    } catch (SQLException ex) {
                        outputLabel.setText("Failed to retrieve employee. Error: " + ex.getMessage()); // Update outputLabel with the error message
                    }
                }
            }
        });
    }

    private boolean isValidNumber(String text) {
        return text.matches("\\d+");
    }

    private void clearOutputLabelAfterDelay(int delay) {
        Timer timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputLabel.setText("");
            }
        });
        timer.setRepeats(false);
        timer.start();
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
