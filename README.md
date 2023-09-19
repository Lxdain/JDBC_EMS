# JDBC_EMS
An Employee Management System utilizing JDBC and MySQL to make different bits of information persistent.

# In Detail
The project is a part of the ITAcademy curriculum, requiring the use of the Java AWT & Swing packages for GUI creation.

The persistance is realized through the use of a JDBC connector utilizing the .jar file provided in the lib folder, so no dependancy managers were used.

The queries look a little something like this:
```java
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
```
It relies heavily on basic SQL queries since no annotations are available through this method.
The project loosely implements a MVC design patters as well.
