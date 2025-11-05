public class OrderItem {
            private static String toPlain(double d){
                return new java.math.BigDecimal(Double.toString(d)).toPlainString();
            }
            
    private String productId;
    private String productName;
    private int quantity;
    private double unitPrice;

    public OrderItem(String productId, String productName, int quantity, double unitPrice){
        this.productId = productId; this.productName = productName; this.quantity = quantity; this.unitPrice = unitPrice;
    }

    public double total(){ return unitPrice * quantity;}
    public String toCSV(){ return String.join("~", productId, escape(productName), toPlain(quantity), toPlain(unitPrice)); }
    public static OrderItem fromCSV(String s){
        String[] p = s.split("~", -1);
        if (p.length < 4) 
            return null;
        return new OrderItem(p[0], unescape(p[1]), Integer.parseInt(p[2]), Double.parseDouble(p[3]));
    }
    private static String escape(String s){ return s.replace("~","/t").replace("^","/c"); }
    private static String unescape(String s){ return s.replace("/t","~").replace("/c","^"); }

    @Override
    public String toString(){ return String.format("%s (%s) x%d = %.2f", productName, productId, quantity, total()); }
}