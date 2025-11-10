import java.util.*;

public class OrderMenu {
    private OrderManager om;
    private CustomerManager cm;
    private ProductManager pm;
    private Scanner sc = new Scanner(System.in);

    public OrderMenu(OrderManager om, CustomerManager cm, ProductManager pm) {
        this.om = om;
        this.cm = cm;
        this.pm = pm;
    }

    public void orderMenu(){
        while(true){
            System.out.println("\n--- Quản lý Đơn hàng ---");
            System.out.println("1. Tạo đơn hàng");
            System.out.println("2. Hiển thị danh sách");
            System.out.println("3. Sửa trạng thái đơn");
            System.out.println("4. Xóa đơn");
            System.out.println("5. Tìm kiếm đơn theo tên khách");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");
            String c = sc.nextLine().trim();
            switch(c){
                case "1": createOrder(); break;
                case "2": listOrders(); break;
                case "3": updateOrderStatus(); break;
                case "4": deleteOrder(); break;
                case "5": searchOrders(); break;
                case "0":
                return;
                default: System.out.println("Chọn sai.");
            }
        }
    }

    public  void createOrder(){
        System.out.print("Nhập ID khách (hoặc Enter để tạo mới): "); String cid = sc.nextLine().trim();
        Customer cust = null;
        if (!cid.isEmpty()) cust = cm.findById(cid);
        if (cust==null){
            System.out.println("Tạo khách mới nhanh:");
            System.out.print("Tên: "); String name = sc.nextLine();
            System.out.print("Phone: "); String phone = sc.nextLine();
            System.out.print("Email: "); String email = sc.nextLine();
            cust = new Customer("", name, phone, email);
            cm.add(cust);
            System.out.println("Đã tạo khách: " + cust.getId());
        }
        Order o = new Order("", cust.getId(), cust.getName(), new Date(), "New");
        while(true){
            System.out.print("Nhập ID sản phẩm thêm vào (Enter để kết thúc): ");
            String pid = sc.nextLine().trim();
            if (pid.isEmpty()) break;
            Product p = pm.findById(pid);
            if (p==null){ System.out.println("Không tìm thấy sản phẩm."); continue;}
            System.out.println("Sản phẩm: " + p.getName() + " | Giá: " + p.getPrice() + " | Số lượng kho: " + p.getQuantity());
            System.out.print("Số lượng mua: ");
            int q = Integer.parseInt(sc.nextLine().trim());
            if (q > p.getQuantity()){ System.out.println("Kho không đủ, thử lại."); continue;}
            p.setQuantity(p.getQuantity() - q); // cập nhật trong bộ nhớ (không tự save)
            OrderItem it = new OrderItem(p.getId(), p.getName(), q, p.getPrice());
            o.addItem(it);
            System.out.println("Đã thêm: " + it);
        }
        if (o.getItems().isEmpty()){ System.out.println("Đơn rỗng, hủy."); return;}
        om.add(o);
        System.out.println("Đã tạo đơn: " + o.getId());
    }

    public  void listOrders(){
        List<Order> list = om.all();
        if (list.isEmpty()){ System.out.println("Chưa có đơn hàng."); return;}
        for(Order o: list) System.out.println(o);
    }

    public  void updateOrderStatus(){
        System.out.print("ID đơn cần cập nhật: "); String id = sc.nextLine().trim();
        Order o = om.findById(id);
        if (o==null){ System.out.println("Không tìm thấy."); return;}
        System.out.println("Trạng thái hiện tại: " + o.getStatus());
        System.out.print("Trạng thái mới (New / Processing / Delivered / Cancelled): "); String s = sc.nextLine();
        o.setStatus(s);
        System.out.println("Đã cập nhật trạng thái.");
    }

    public  void deleteOrder(){
        System.out.print("ID đơn cần xóa: "); String id = sc.nextLine().trim();
        Order o = om.findById(id);
        if (o==null){ System.out.println("Không tìm thấy."); return;}
        om.remove(id);
        System.out.println("Đã xóa đơn " + id);
    }

    public  void searchOrders(){
        System.out.print("Nhập từ khoá tên khách: "); String q = sc.nextLine();
        List<Order> r = om.searchByCustomerName(q);
        if (r.isEmpty()) System.out.println("Không tìm thấy.");
        else for(Order o: r) System.out.println(o);
    }
}
