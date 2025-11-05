import java.text.SimpleDateFormat;
import java.util.*;

public class Order {
            private static String toPlain(double d){
                return new java.math.BigDecimal(Double.toString(d)).toPlainString();
            }
            
    private static int counter = 0;
    private String id;
    private String customerId;
    private String customerName;
    private Date orderDate;
    private String status;
    private List<OrderItem> items = new ArrayList<>();

    public Order(String id, String customerId, String customerName, Date orderDate, String status){
        if (id == null || id.isEmpty()){
            counter++; 
            this.id = "O" + counter;
        } 
        else {
            this.id = id;
            try {
                if (id.startsWith("O")){
                    int v = Integer.parseInt(id.substring(1));
                    if (v > counter) counter = v;
                }
            } catch(Exception e){}
        }
        this.customerId = customerId; 
        this.customerName = customerName; 
        this.orderDate = orderDate; 
        this.status = status;
    }

    public String getId(){ return id;}
    public List<OrderItem> getItems(){ return items;}
    public void addItem(OrderItem it){ items.add(it); }
    public double total(){ 
        double s=0; 
        for(OrderItem it: items) 
            s += it.total(); 
        return s;
    }
    public String getStatus(){ return status;}
    public void setStatus(String s){ status = s;}
    public Date getDate(){ return orderDate;}
    public String getCustomerName(){ return customerName;}
    public String getCustomerId(){ return customerId;}

    public String toCSV(){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<String> its = new ArrayList<>();
        for (OrderItem it: items) its.add(it.toCSV());
        return String.join("|", id, customerId, escape(customerName), fmt.format(orderDate), status, String.join("^", its));
    }

    public static Order fromCSV(String line){
        try {
            String[] p = line.split("\\|", -1);
            if (p.length < 6) return null;
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Order o = new Order(p[0], p[1], unescape(p[2]), fmt.parse(p[3]), p[4]);
            if (!p[5].isEmpty()){
                String[] its = p[5].split("\\^", -1);
                for (String s: its){
                    if (s.trim().isEmpty()) 
                        continue;
                    OrderItem oi = OrderItem.fromCSV(s);
                    if (oi!=null) 
                        o.addItem(oi);
                }
            }
            return o;
        } catch(Exception e){
            return null;
        }
    }

    private static String escape(String s){ return s.replace("|","/p"); }
    private static String unescape(String s){ return s.replace("/p","|"); }

    @Override
    public String toString(){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s | Khách: %s (%s) | Ngày: %s | Trạng thái: %s\n", id, customerName, customerId, fmt.format(orderDate), status));
        for (OrderItem it: items) sb.append("   - ").append(it.toString()).append("\n");
        sb.append(String.format("   Tổng: %.2f", total()));
        return sb.toString();
    }
}