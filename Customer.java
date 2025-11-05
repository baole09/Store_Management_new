public class Customer {
    private static int counter = 0;
    private String id;
    private String name;
    private String phone;
    private String email;

    public Customer(String id, String name, String phone, String email){
        if (id == null || id.isEmpty()){
            counter++;
            this.id = "C" + counter;
        } 
        else {
            this.id = id;
            try {
                if (id.startsWith("C")){
                    int v = Integer.parseInt(id.substring(1));
                    if (v > counter) 
                        counter = v;
                }
            } catch(Exception e){}
        }
        this.name = name;
        this.phone = phone; 
        this.email = email;
    }

    public String getId(){return id;}
    public String getName(){return name;}
    public String getPhone(){return phone;}
    public String getEmail(){return email;}
    public void setName(String n){ name = n;}
    public void setPhone(String p){ phone = p;}
    public void setEmail(String e){ email = e;}

    public String toCSV(){
        return String.join("|", id, escape(name), phone, email);
    }

    public static Customer fromCSV(String line){
        String[] p = line.split("\\|", -1);
        if (p.length < 4) 
            return null;
        return new Customer(p[0], unescape(p[1]), p[2], p[3]);
    }

    private static String escape(String s){ return s.replace("|","/p"); }
    private static String unescape(String s){ return s.replace("/p","|"); }

    @Override
    public String toString(){
        return String.format("%s | %s | %s | %s", id, name, phone, email);
    }
}