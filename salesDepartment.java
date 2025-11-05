public class salesDepartment extends Employee {
    private static int counter = 0;
    private String task; // Bán hàng hoặc Chăm sóc khách hàng

    public salesDepartment(String id, String name, String phone, String role, String task, String shift) {
        super(id, name, phone, role, shift);
        if (id == null || id.isEmpty()) {
            counter++;
            this.id = "ES" + counter; // Mã ES1, ES2, ...
        } else {
            this.id = id;
            try {
                if (id.startsWith("ES")) {
                    int v = Integer.parseInt(id.substring(2));
                    if (v > counter) counter = v;
                }
            } catch (Exception e) {}
        }
        this.task = task;
    }

    public String getTask() { return task; }
    public void setTask(String task) { this.task = task; }

    @Override
    public String toCSV() {
        return String.join("|", id, escape(name), phone, role, task, shift);
    }

    @Override
    public String toDisplay() {
        return String.format("%s | %s | %s | %s | %s | %s", id, name, phone, role, task, shift);
    }

    private static String escape(String s) { return s.replace("|", "/p"); }
}
// public class salesDepartment extends Employee implements ICsvDisplay {
//     private String task;

//     public salesDepartment(String id, String name, String phone, String role, String task, String shift){
//         super(id, name, phone, role, shift);
//         this.task = task;
//     }

//     public static salesDepartmente fromParts(String[] p){
//         // p: type|id|name|phone|role|shift|extra
//         if (p.length < 7) return null;
//         return new salesDepartmente(p[1], p[2], p[3], p[4], p[5], p[6]);
//     }

//     public String getTask() { return task; }
//     public void setTask(String task) { this.task = task; }

//     @Override
//     public String toCSV(){
//         return String.join("|", id, escape(name), phone, role, task, shift, "");
//     }

//     @Override
//     public String toDisplay(){
//         return String.format("%s | %s | %s | %s | %s | %s ", id, name, phone, role, task, shift);
//     }

//     private static String escape(String s){ return s.replace("|","/p"); }
// }

