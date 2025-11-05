import java.io.*;
import java.util.*;

public class OrderManager {
    private List<Order> list = new ArrayList<>();
    private final String fileName = "orders.txt";

    public List<Order> all(){ return list; }
    public void add(Order o){ list.add(o); }
    public Order findById(String id){ 
        for(Order o:list) 
            if(o.getId().equals(id)) 
                return o; 
        return null; 
    }
    public void remove(String id){ list.removeIf(o->o.getId().equals(id)); }
    public List<Order> searchByCustomerName(String q){ 
        List<Order> r=new ArrayList<>(); 
        for(Order o:list) 
            if(o.getCustomerName().toLowerCase().contains(q.toLowerCase())) 
                r.add(o); 
        return r; 
    }

    public void saveToFile(){
        try(PrintWriter pw = new PrintWriter(new FileWriter(fileName))){
            for(Order o: list) 
                pw.println(o.toCSV());
            System.out.println("Đã ghi " + list.size() + " đơn hàng ra " + fileName);
        } catch(IOException e){ System.out.println("Lỗi khi ghi orders: " + e.getMessage()); }
    }

    public void loadFromFile(){
        list.clear();
        File f = new File(fileName);
        if (!f.exists()){ System.out.println("File orders.txt không tồn tại."); return; }
        try(BufferedReader br = new BufferedReader(new FileReader(f))){
            String line; int cnt=0;
            while((line=br.readLine())!=null){
                Order o = Order.fromCSV(line);
                if (o!=null){ list.add(o); cnt++; }
            }
            System.out.println("Đã đọc " + cnt + " đơn hàng từ " + fileName);
        } catch(IOException e){ System.out.println("Lỗi khi đọc orders: " + e.getMessage()); }
    }
}