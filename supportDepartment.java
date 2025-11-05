public class supportDepartment extends Employee {
    private static int counter = 0;
    private String section;
    private String specialSkill;

    public supportDepartment(String id, String name, String phone, String role, String section, String shift, String specialSkill) {
        super(id, name, phone, role, shift);
        if (id == null || id.isEmpty()) {
            counter++;
            this.id = "ESP" + counter; // Mã ESP1, ESP2, ...
        } else {
            this.id = id;
            try {
                if (id.startsWith("ESP")) {
                    int v = Integer.parseInt(id.substring(3));
                    if (v > counter) counter = v;
                }
            } catch (Exception e) {}
        }
        this.section = section;
        this.specialSkill = specialSkill;
    }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public String getSpecialSkill() { return specialSkill; }
    public void setSpecialSkill(String specialSkill) { this.specialSkill = specialSkill; }

    @Override
    public String toCSV() {
        return String.join("|", id, escape(name), phone, role, section, specialSkill, shift);
    }

    @Override
    public String toDisplay() {
        return String.format("%s | %s | %s | %s | %s | %s | %s", id, name, phone, role, section, specialSkill, shift);
    }

    private static String escape(String s) { return s.replace("|", "/p"); }
}

// public class supportDepartment extends Employee implements ICsvDisplay {
//     private String section;
//     private String specialSkill;

//     public supportDepartment(String id, String name, String phone, String role, String section, String shift, String specialSkill){
//         super(id, name, phone, role, shift);
//         this.section = section;
//         this.specialSkill = specialSkill;
//     }

//     public String getSection() { return section; }
//     public void setSection(String section) { this.section = section; }

//     public String getSpecialSkill(){ return specialSkill;}
//     public void setSpecialSkill(String s){ this.specialSkill = s;}

//     public static supportDepartment fromParts(String[] p){
//         // p: type|id|name|phone|role|shift|extra
//         if (p.length < 8) return null;
//         return new supportDepartment(p[1], p[2], p[3], p[4], p[5], p[6], p[7]);
//     }

//     @Override
//     public String toCSV(){
//         return String.join("|", id, escape(name), phone, escape(role), escape(section),  escape(specialSkill), shift);
//     }

//     @Override
//     public String toDisplay(){
//         return String.format("%s | %s | %s | %s | %s | %s | %s ", id, name, phone, role, section, specialSkill, shift);
//     }

//     private static String escape(String s){ return s.replace("|","/p"); }
// }
