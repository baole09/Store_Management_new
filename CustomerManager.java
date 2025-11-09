import java.io.*;
import java.util.*;

public class CustomerManager {
    private List<Customer> list = new ArrayList<>();
    private final String fileName = "customers.txt";
    Scanner sc = new Scanner(System.in);
    
    public void add(Customer c){ 
        list.add(c); 
    }

    public List<Customer> all(){ 
        return list; 
    }

    public Customer findById(String id){ 
        for(Customer c:list) 
            if(c.getId().equals(id)) 
                return c; 
        return null; 
    }

    public void remove(String id){ 
        list.removeIf(c->c.getId().equals(id)); 
    }
    
    public List<Customer> searchByName(String q){ 
        List<Customer> r=new ArrayList<>(); 
        for(Customer c:list) 
            if(c.getName().toLowerCase().contains(q.toLowerCase())) 
                r.add(c); 
        return r; 
    }

    public void saveToFile(){
        try(PrintWriter pw = new PrintWriter(new FileWriter(fileName))){
            for(Customer c: list) 
                pw.println(c.toCSV());
        } 
        catch(IOException e){ 
            System.out.println("Lỗi khi ghi customers: " + e.getMessage()); 
        }
    }

    public void loadFromFile(){
        list.clear();
        File f = new File(fileName);
        if (!f.exists()){ 
            System.out.println("File customers.txt không tồn tại."); 
            return; 
        }
        try(BufferedReader br = new BufferedReader(new FileReader(f))){
            String line; int cnt=0;
            while((line=br.readLine())!=null){
                Customer c = Customer.fromCSV(line);
                if (c!=null){ 
                    list.add(c); 
                    cnt++; 
                }
            }
            System.out.println("Đã đọc " + cnt + " khách hàng từ " + fileName);
        } 
        catch(IOException e){ 
            System.out.println("Lỗi khi đọc customers: " + e.getMessage()); 
        }
    }
    
}