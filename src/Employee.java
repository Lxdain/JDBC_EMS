public class Employee {
    private int id;
    private String name;
    private int age;
    private String address;
    private double salary;

    public Employee(int id, String name, int age, String address, double salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Age: " + age + ", Address: " + address + ", Salary: " + salary;
    }
}
