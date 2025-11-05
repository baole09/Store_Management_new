public abstract class Employee implements ICsvDisplay {
    protected String id;
    protected String name;
    protected String phone;
    protected String role;
    protected String shift; // ca làm: Sáng/Chiều/Tối

    public Employee(String id, String name, String phone, String role, String shift){
        this.id = id;
        this.name = name; 
        this.phone = phone; 
        this.role = role; 
        this.shift = shift;
    }

    public String getId(){return id;}
    public String getName(){return name;}
    public String getPhone(){return phone;}
    public String getRole(){return role;}
    public String getShift(){return shift;}

    public void setShift(String s){ shift = s;}
    public void setName(String n){ name = n;}
    public void setPhone(String p){ phone = p;}

    public abstract String toCSV();
    public abstract String toDisplay();
}