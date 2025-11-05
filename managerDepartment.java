public class managerDepartment extends Employee {
    private static int counter = 0;
    private String level;

    public managerDepartment(String id, String name, String phone, String role, String level, String shift) {
        super(id, name, phone, role, shift);
        if (id == null || id.isEmpty()) {
            counter++;
            this.id = "EM" + counter; // Tự sinh mã EM1, EM2,...
        } else {
            this.id = id;
            try {
                if (id.startsWith("EM")) {
                    int v = Integer.parseInt(id.substring(2));
                    if (v > counter) counter = v;
                }
            } catch (Exception e) {}
        }
        this.level = level;
    }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    @Override
    public String toCSV() {
        return String.join("|", id, escape(name), phone, role, level, shift);
    }

    @Override
    public String toDisplay() {
        return String.format("%s | %s | %s | %s | %s | %s", id, name, phone, role, level, shift);
    }

    private static String escape(String s) { return s.replace("|", "/p"); }
}

// public class managerDepartment extends Employee implements ICsvDisplay {
//     private String level;

//     public managerDepartment(String id, String name, String phone, String role, String level, String shift){
//         super(id, name, phone, role, shift);
//         this.level = level;
//     }

//     public static managerDepartment fromParts(String[] p){
//         // p: type|id|name|phone|role|shift|extra
//         if (p.length < 7) return null;
//         return new managerDepartment(p[1], p[2], p[3], p[4], p[5], p[6]);
//     }

//     public String getLevel(){ return level;}
//     public void setLevel(String level){ this.level = level}
//     @Override
//     public String toCSV(){
//         return String.join("|", id, escape(name), phone, role, level, shift, "");
//     }

//     @Override
//     public String toDisplay(){
//         return String.format("%s | %s | %s | %s | %s | %s ", id, name, phone, role, level, shift);
//     }

//     private static String escape(String s){ return s.replace("|","/p"); }
// }
