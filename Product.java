

public class Product {
            private static String toPlain(double d){
                return new java.math.BigDecimal(Double.toString(d)).toPlainString();
            }
            
    private static int counter = 0;
    private String id;
    private String name;
    private double price;
    private int quantity;
    private String category;

    public Product(String id, String name, double price, int quantity, String category) {
        if (id == null || id.isEmpty()) {
            counter++; this.id = "P" + counter;
        } else {
            this.id = id;
            try {
                if (id.startsWith("P")) {
                    int v = Integer.parseInt(id.substring(1));
                    if (v > counter) counter = v;
                }
            } catch(Exception e){}
        }
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    // getters
    public String getId(){return id;}
    public String getName(){return name;}
    public double getPrice(){return price;}
    public int getQuantity(){return quantity;}
    public String getCategory(){return category;}
    //setters
    public void setName(String n){name = n;}
    public void setPrice(double p){price = p;}
    public void setQuantity(int q){quantity = q;}
    public void setCategory(String c){category = c;}

    public String toCSV(){
        return String.join("|", id, escape(name), toPlain(price), Integer.toString(quantity), escape(category));
    }

    public static Product fromCSV(String line){
        String[] p = line.split("\\|", -1);
        if (p.length < 5) return null;
        return new Product(p[0], unescape(p[1]), Double.parseDouble(p[2]), Integer.parseInt(p[3]), unescape(p[4]));
    }

    private static String escape(String s){
        return s.replace("|", "/p").replace("\n"," ");
    }
    private static String unescape(String s){
        return s.replace("/p", "|");
    }

    @Override
    public String toString(){
        return String.format("%s | %s | %.2f | qty:%d | %s", id, name, price, quantity, category);
    }
}