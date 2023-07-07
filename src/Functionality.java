import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Functionality {
    private Connection conn;
    private int nextEmployeeId;

    public Functionality() {
        checkConnection();
        getNextEmployeeIdFromDatabase();
    }

    public void checkConnection() {
        String url = "jdbc:mysql://localhost:3306/jdbc_ems";
        String username = "root";
        String password = "root";

        try {
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connection to the database successful!");
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database. Error message: " + e.getMessage());
        }
    }

    private void getNextEmployeeIdFromDatabase() {
        try {
            String query = "SELECT MAX(id) FROM employees";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                nextEmployeeId = resultSet.getInt(1) + 1;
            } else {
                nextEmployeeId = 1;
            }
            resultSet.close();
            statement.close();

            while (isEmployeeIdExists(nextEmployeeId)) {
                nextEmployeeId++;
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while retrieving the next employee ID: " + e.getMessage());
        }
    }

    private boolean isEmployeeIdExists(int id) throws SQLException {
        String query = "SELECT id FROM employees WHERE id = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        boolean exists = resultSet.next();
        resultSet.close();
        statement.close();
        return exists;
    }

    public int getNextEmployeeId() {
        return nextEmployeeId;
    }

    public void addEmployee(Employee employee) throws SQLException {
        String query = "INSERT INTO employees (name, age, address, salary) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, employee.getName());
        statement.setInt(2, employee.getAge());
        statement.setString(3, employee.getAddress());
        statement.setDouble(4, employee.getSalary());
        statement.executeUpdate();

        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            int generatedId = generatedKeys.getInt(1);
            employee.setId(generatedId);
            nextEmployeeId = generatedId + 1;
        }

        statement.close();
    }

    public void editEmployee(Employee employee) throws SQLException {
        String query = "UPDATE employees SET name = ?, age = ?, address = ?, salary = ? WHERE id = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, employee.getName());
        statement.setInt(2, employee.getAge());
        statement.setString(3, employee.getAddress());
        statement.setDouble(4, employee.getSalary());
        statement.setInt(5, employee.getId());
        statement.executeUpdate();
        statement.close();
    }

    public void deleteEmployee(int id) throws SQLException {
        String query = "DELETE FROM employees WHERE id = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeUpdate();
        statement.close();
    }

    public List<Employee> retrieveEmployees() throws SQLException {
        String query = "SELECT * FROM employees";
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<Employee> employees = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");
            String address = resultSet.getString("address");
            double salary = resultSet.getDouble("salary");
            Employee employee = new Employee(id, name, age, address, salary);
            employees.add(employee);
        }
        resultSet.close();
        statement.close();
        return employees;
    }

    public List<Employee> searchEmployees(String searchCriteria) throws SQLException {
        String query = "SELECT * FROM employees WHERE id <> ? AND (name LIKE ? OR age = ? OR address LIKE ? OR salary = ?)";
        PreparedStatement statement = conn.prepareStatement(query);
        try {
            int id = Integer.parseInt(searchCriteria);
            statement.setInt(1, id);
        } catch (NumberFormatException e) {
            statement.setInt(1, 0);
        }
        statement.setString(2, "%" + searchCriteria + "%");
        try {
            int age = Integer.parseInt(searchCriteria);
            statement.setInt(3, age);
        } catch (NumberFormatException e) {
            statement.setInt(3, 0);
        }
        statement.setString(4, "%" + searchCriteria + "%");
        try {
            double salary = Double.parseDouble(searchCriteria);
            statement.setDouble(5, salary);
        } catch (NumberFormatException e) {
            statement.setDouble(5, 0);
        }
        ResultSet resultSet = statement.executeQuery();
        List<Employee> employees = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");
            String address = resultSet.getString("address");
            double salary = resultSet.getDouble("salary");
            Employee employee = new Employee(id, name, age, address, salary);
            employees.add(employee);
        }
        resultSet.close();
        statement.close();
        return employees;
    }

    public Employee getEmployeeById(int id) throws SQLException {
        String query = "SELECT * FROM employees WHERE id = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        Employee employee = null;
        if (resultSet.next()) {
            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");
            String address = resultSet.getString("address");
            double salary = resultSet.getDouble("salary");
            employee = new Employee(id, name, age, address, salary);
        }
        resultSet.close();
        statement.close();
        return employee;
    }

    public Employee getEmployeeByName(String name) throws SQLException {
        String query = "SELECT * FROM employees WHERE name = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        Employee employee = null;
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            int age = resultSet.getInt("age");
            String address = resultSet.getString("address");
            double salary = resultSet.getDouble("salary");
            employee = new Employee(id, name, age, address, salary);
        }
        resultSet.close();
        statement.close();
        return employee;
    }
}
