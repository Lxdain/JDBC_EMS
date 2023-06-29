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
    private JLabel outputLabel;
    private JButton showAllButton;
    private Functionality functionality;

    public GUI() {
        setTitle("Employee Management System");
        setContentPane(accountPanel);
        setMinimumSize(new Dimension(1024, 768));
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
                    outputLabel.setText("Employee added successfully!");
                    clearFields();
                } catch (SQLException ex) {
                    outputLabel.setText("Failed to add employee. Error: " + ex.getMessage());
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(searchField.getText());
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String address = addressField.getText();
                double salary = Double.parseDouble(salaryField.getText());
                Employee employee = new Employee(id, name, age, address, salary);
                try {
                    functionality.editEmployee(employee);
                    outputLabel.setText("Employee updated successfully!");
                    clearFields();
                } catch (SQLException ex) {
                    outputLabel.setText("Failed to update employee. Error: " + ex.getMessage());
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(searchField.getText());
                try {
                    functionality.deleteEmployee(id);
                    outputLabel.setText("Employee deleted successfully!");
                    clearFields();
                } catch (SQLException ex) {
                    outputLabel.setText("Failed to delete employee. Error: " + ex.getMessage());
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
                } catch (SQLException ex) {
                    outputLabel.setText("Failed to search employees. Error: " + ex.getMessage());
                }
            }
        });

        showAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Employee> employees = functionality.retrieveEmployees();
                    displayEmployees(employees);
                } catch (SQLException ex) {
                    outputLabel.setText("Failed to retrieve employees. Error: " + ex.getMessage());
                }
            }
        });
    }

    private void displayEmployees(List<Employee> employees) {
        StringBuilder sb = new StringBuilder("<html>");

        for (Employee employee : employees) {
            sb.append("<p>");
            sb.append("ID: ").append(employee.getId()).append("<br>");
            sb.append("Name: ").append(employee.getName()).append("<br>");
            sb.append("Age: ").append(employee.getAge()).append("<br>");
            sb.append("Salary: ").append(employee.getSalary()).append("<br>");
            sb.append("</p>");
            sb.append("<br>"); // Add an extra line break after each employee
        }

        sb.append("</html>");
        outputLabel.setText(sb.toString());
    }

    private void clearFields() {
        nameField.setText("");
        ageField.setText("");
        addressField.setText("");
        salaryField.setText("");
        searchField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI();
            }
        });
    }
}
