public class Employee {
    private int id;
    private String name;
    private int age;
    private String address;
    private float salary;

    // Constructor
    public Employee(int id, String name, int age, String address, float salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.salary = salary;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() { return age; }

    public String getAddress() {
        return address;
    }

    public float getSalary() {
        return salary;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee Details:" +
                "\nID: " + id +
                "\nName: " + name +
                "\nAge: " + age +
                "\nAddress: " + address +
                "\nSalary: " + salary;
    }
}
