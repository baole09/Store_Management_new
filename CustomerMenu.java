
import java.util.List;
import java.util.Scanner;

public class CustomerMenu {
    private CustomerManager cm;
    private Scanner sc = new Scanner(System.in);

    public CustomerMenu(CustomerManager cm){
        this.cm = cm;
    }

    public void customerMenu(){
        
        while(true){
            System.out.println("\n--- Quản lý Khách hàng ---");
            System.out.println("1. Thêm khách hàng");
            System.out.println("2. Hiển thị danh sách");
            System.out.println("3. Sửa khách hàng");
            System.out.println("4. Xóa khách hàng");
            System.out.println("5. Tìm kiếm khách hàng");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");
            String c = sc.nextLine().trim();
            switch(c){
                case "1": addCustomer(); break;
                case "2": listCustomers(); break;
                case "3": editCustomer(); break;
                case "4": deleteCustomer(); break;
                case "5": searchCustomer(); break;
                case "0": return;
                default: System.out.println("Chọn sai.");
            }
        }
        
    }
    // các phương thức chức năng
    public void addCustomer(){
        System.out.print("Tên: "); String name = sc.nextLine();
        System.out.print("Phone: "); String phone = sc.nextLine();
        System.out.print("Email: "); String email = sc.nextLine();
        Customer c = new Customer("", name, phone, email);
        cm.add(c);
        System.out.println("Đã thêm: " + c);
    }

    public void listCustomers(){
        List<Customer> list = cm.all();
        if (list.isEmpty()){ System.out.println("Chưa có khách hàng."); return;}
        for(Customer c: list) System.out.println(c);
    }

    public void editCustomer(){
        System.out.print("ID khách cần sửa: "); String id = sc.nextLine().trim();
        Customer c = cm.findById(id);
        if (c==null){ System.out.println("Không tìm thấy."); return;}
        System.out.println("Khách hiện tại: " + c);
        System.out.print("Tên mới (Enter giữ): ");
        String s = sc.nextLine();
        if (!s.isEmpty()) c.setName(s);

        System.out.print("Phone mới (Enter giữ): ");
        s = sc.nextLine();
        if (!s.isEmpty()) c.setPhone(s);

        System.out.print("Email mới (Enter giữ): ");
        s = sc.nextLine();
        if (!s.isEmpty()) c.setEmail(s);

        System.out.println("Đã cập nhật: " + c);
    }

    public void deleteCustomer(){
        System.out.print("ID khách cần xóa: "); String id = sc.nextLine().trim();
        Customer c = cm.findById(id);
        if (c==null){ System.out.println("Không tìm thấy."); return;}
        cm.remove(id);
        System.out.println("Đã xóa " + id);
    }

    public void searchCustomer(){
        System.out.print("Nhập từ khoá tên: "); String q = sc.nextLine();
        List<Customer> r = cm.searchByName(q);
        if (r.isEmpty()) System.out.println("Không tìm thấy.");
        else for(Customer c: r) System.out.println(c);
    }
}

