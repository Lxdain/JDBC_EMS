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
    private JScrollPane outputScrollPane;
    private JTextArea outputTextArea;
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
                    outputTextArea.append("Employee added successfully!\n");
                    clearFields();
                } catch (SQLException ex) {
                    outputTextArea.append("Failed to add employee. Error: " + ex.getMessage() + "\n");
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
                    outputTextArea.append("Employee updated successfully!\n");
                    clearFields();
                } catch (SQLException ex) {
                    outputTextArea.append("Failed to update employee. Error: " + ex.getMessage() + "\n");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(searchField.getText());
                try {
                    functionality.deleteEmployee(id);
                    outputTextArea.append("Employee deleted successfully!\n");
                    clearFields();
                } catch (SQLException ex) {
                    outputTextArea.append("Failed to delete employee. Error: " + ex.getMessage() + "\n");
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
                    outputTextArea.append("Failed to search employees. Error: " + ex.getMessage() + "\n");
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
                    outputTextArea.append("Failed to retrieve employees. Error: " + ex.getMessage() + "\n");
                }
            }
        });
    }

    private void displayEmployees(List<Employee> employees) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        for (Employee employee : employees) {
            sb.append("<p>").append(employee.toString()).append("</p>");
        }
        sb.append("</html>");
        outputTextArea.setText(sb.toString());
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