import java.util.*;

public class StoreManagement {
    private static Scanner sc = new Scanner(System.in);
    private static ProductManager pm = new ProductManager();
    private static EmployeeManager em = new EmployeeManager();
    private static CustomerManager cm = new CustomerManager();
    private static OrderManager om = new OrderManager();

    public static void main(String[] args){
        System.out.println("=== CHƯƠNG TRÌNH QUẢN LÝ CỬA HÀNG ĐIỆN TỬ (Console) ===");
        mainMenu();
    }

    private static void mainMenu(){
        while(true){
            System.out.println("\n===== MENU CHÍNH =====");
            System.out.println("1. Quản lý sản phẩm");
            System.out.println("2. Quản lý nhân viên");
            System.out.println("3. Quản lý khách hàng");
            System.out.println("4. Quản lý đơn hàng");
            System.out.println("5. Đọc / Ghi file");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            String c = sc.nextLine().trim();
            switch(c){
                case "1": productMenu(); break;
                case "2": employeeMenu(); break;
                case "3": customerMenu(); break;
                case "4": orderMenu(); break;
                case "5": fileMenu(); break;
                case "0": System.out.println("Thoát chương trình. Lưu ý: dữ liệu hiện tại chưa chắc đã được ghi ra file nếu bạn chưa chọn 'Ghi dữ liệu'."); return;
                default: System.out.println("Chọn sai, thử lại.");
            }
        }
    }

    // Product menu
    // private static void productMenu(){
    //     while(true){
    //         System.out.println("\n--- Quản lý Sản phẩm ---");
    //         System.out.println("1. Thêm sản phẩm");
    //         System.out.println("2. Hiển thị danh sách");
    //         System.out.println("3. Sửa sản phẩm");
    //         System.out.println("4. Xóa sản phẩm");
    //         System.out.println("5. Tìm kiếm sản phẩm");
    //         System.out.println("0. Quay lại");
    //         System.out.print("Chọn: ");
    //         String c = sc.nextLine().trim();
    //         switch(c){
    //             case "1": addProduct(); break;
    //             case "2": listProducts(); break;
    //             case "3": editProduct(); break;
    //             case "4": deleteProduct(); break;
    //             case "5": searchProduct(); break;
    //             case "0": return;
    //             default: System.out.println("Chọn sai.");
    //         }
    //     }
    // }

    // private static void addProduct() {
    //     String category = "";
    //     while (true) {
    //         System.out.println("Chọn loại sản phẩm:");
    //         System.out.println("1. Laptop");
    //         System.out.println("2. Điện thoại");
    //         System.out.print("Chọn: ");
    //         String choice = sc.nextLine().trim();
    //         if (choice.equals("1")) {
    //             category = "Laptop";
    //             break;
    //         } else if (choice.equals("2")) {
    //             category = "Điện thoại";
    //             break;
    //         } else {
    //             System.out.println("Lựa chọn không hợp lệ, vui lòng chọn lại!");
    //         }
    //     }

    //     System.out.print("Tên hãng: ");
    //     String brand = sc.nextLine();

    //     System.out.print("Tên máy: ");
    //     String name = sc.nextLine();

    //     System.out.print("Giá: ");
    //     double price = readDouble();

    //     System.out.print("Số lượng: ");
    //     int qty = readInt();

    //     // Ghép tên đầy đủ: Tên hãng + Tên máy
    //     String fullName = brand + " " + name;

    //     Product p = new Product("", fullName, price, qty, category);
    //     pm.add(p);
    //     System.out.println("Đã thêm: " + p);
    // }

    // private static void listProducts(){
    //     List<Product> list = pm.all();
    //     if (list.isEmpty()) { System.out.println("Danh sách rỗng."); return;}
    //     System.out.println("--- Danh sách sản phẩm ---");
    //     for(Product p: list) System.out.println(p);
    // }

    // private static void editProduct(){
    //     System.out.print("Nhập ID cần sửa: "); String id = sc.nextLine().trim();
    //     Product p = pm.findById(id);
    //     if (p==null){ System.out.println("Không tìm thấy ID."); return;}
    //     System.out.println("Sản phẩm hiện tại: " + p);
    //     System.out.print("Tên mới (Enter để giữ): "); String s = sc.nextLine(); if (!s.isEmpty()) p.setName(s);
    //     System.out.print("Giá mới (Enter để giữ): "); s = sc.nextLine(); if (!s.isEmpty()) p.setPrice(Double.parseDouble(s));
    //     System.out.print("Số lượng mới (Enter để giữ): "); s = sc.nextLine(); if (!s.isEmpty()) p.setQuantity(Integer.parseInt(s));
    //     System.out.print("Danh mục mới (Enter để giữ): "); s = sc.nextLine(); if (!s.isEmpty()) p.setCategory(s);
    //     System.out.println("Đã cập nhật: " + p);
    // }

    // private static void deleteProduct(){
    //     System.out.print("Nhập ID cần xóa: "); String id = sc.nextLine().trim();
    //     Product p = pm.findById(id);
    //     if (p==null){ System.out.println("Không tìm thấy."); return;}
    //     pm.remove(id);
    //     System.out.println("Đã xóa " + id);
    // }

    // private static void searchProduct(){
    //     System.out.print("Nhập từ khoá tìm kiếm tên: "); String q = sc.nextLine();
    //     List<Product> r = pm.searchByName(q);
    //     if (r.isEmpty()) System.out.println("Không tìm thấy.");
    //     else for(Product p: r) System.out.println(p);
    // }

    // Product menu
    private static void productMenu() {
        pm.loadFromFile(); // ✅ tự động tải danh sách khi vào menu

        while (true) {
            System.out.println("\n========= MENU QUẢN LÝ SẢN PHẨM =========");
            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Sửa sản phẩm");
            System.out.println("3. Xóa sản phẩm");
            System.out.println("4. Tìm kiếm sản phẩm");
            System.out.println("5. Lọc sản phẩm theo danh mục và giá");
            System.out.println("6. Xem danh sách tất cả sản phẩm");
            System.out.println("0. Quay lại");
            System.out.print("Chọn chức năng: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> pm.addProduct();
                case "2" -> pm.editProduct();
                case "3" -> pm.deleteProduct();
                case "4" -> {
                    System.out.print("Nhập từ khóa cần tìm: ");
                    String keyword = sc.nextLine();
                    var found = pm.search(keyword);
                    pm.display(found);
                }
                case "5" -> {
                    System.out.print("Nhập loại (Laptop/Điện thoại/Máy tính bảng/Phụ kiện hoặc Tất cả): ");
                    String category = sc.nextLine().trim();

                    System.out.print("Nhập khoảng giá (vd: 5000000-20000000 hoặc Enter để bỏ qua): ");
                    String range = sc.nextLine().trim();
                    Double min = null, max = null;
                    if (!range.isBlank() && range.contains("-")) {
                        try {
                            String[] parts = range.split("-");
                            min = Double.parseDouble(parts[0].trim());
                            max = Double.parseDouble(parts[1].trim());
                        } catch (Exception e) {
                            System.out.println("Định dạng khoảng giá không hợp lệ!");
                        }
                    }
                    var filtered = pm.filter(category, min, max);
                    pm.display(filtered);
                }
                case "6" -> pm.displayAll();
                case "0" -> {
                    System.out.println("Đang lưu dữ liệu...");
                    pm.saveToFile();
                    System.out.println("Quay lại menu chính!");
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ, vui lòng nhập lại!");
            }
        }
    }

    // Employee menu
    // private static void employeeMenu(){
    //     while(true){
    //         System.out.println("\n--- Quản lý Nhân viên ---");
    //         System.out.println("1. Nhân viên kỹ thuật");
    //         System.out.println("2. Nhân viên bình thường");
    //         System.out.println("3. Hiển thị tất cả");
    //         System.out.println("4. Tìm kiếm theo tên");
    //         System.out.println("5. Xóa theo ID");
    //         System.out.println("0. Quay lại");
    //         System.out.print("Chọn: ");
    //         String c = sc.nextLine().trim();
    //         switch(c){
    //             case "1": manageTechnician(); break;
    //             case "2": manageRegular(); break;
    //             case "3": listAllEmployees(); break;
    //             case "4": searchEmployees(); break;
    //             case "5": deleteEmployee(); break;
    //             case "0": return;
    //             default: System.out.println("Chọn sai.");
    //         }
    //     }
    // }

    // private static void manageTechnician(){
    //     while(true){
    //         System.out.println("\n--- Kỹ thuật viên ---");
    //         System.out.println("1. Thêm kỹ thuật viên");
    //         System.out.println("2. Hiển thị kỹ thuật viên");
    //         System.out.println("3. Sửa kỹ thuật viên");
    //         System.out.println("4. Phân ca làm");
    //         System.out.println("0. Quay lại");
    //         System.out.print("Chọn: ");
    //         String c = sc.nextLine().trim();
    //         switch(c){
    //             case "1":
    //                 System.out.print("Tên: "); String name = sc.nextLine();
    //                 System.out.print("Phone: "); String phone = sc.nextLine();
    //                 System.out.print("Ca làm (Sáng/Chiều/Tối): "); String shift = sc.nextLine();
    //                 System.out.print("Kỹ năng đặc biệt: "); String skill = sc.nextLine();
    //                 Technician t = new Technician("", name, phone, shift, skill);
    //                 em.add(t);
    //                 System.out.println("Đã thêm: " + t.toDisplay());
    //                 break;
    //             case "2":
    //                 for(Employee e: em.all()) if (e instanceof Technician) System.out.println(e.toDisplay());
    //                 break;
    //             case "3":
    //                 System.out.print("ID cần sửa: "); String id = sc.nextLine().trim();
    //                 Employee ee = em.findById(id);
    //                 if (ee==null || !(ee instanceof Technician)){ System.out.println("Không tìm thấy kỹ thuật viên."); break;}
    //                 Technician tt = (Technician) ee;
    //                 System.out.print("Tên mới (Enter giữ): "); String s = sc.nextLine(); if (!s.isEmpty()) tt.setName(s);
    //                 System.out.print("Phone mới (Enter giữ): "); s = sc.nextLine(); if (!s.isEmpty()) tt.setPhone(s);
    //                 System.out.print("Ca làm mới (Enter giữ): "); s = sc.nextLine(); if (!s.isEmpty()) tt.setShift(s);
    //                 System.out.print("Kỹ năng mới (Enter giữ): "); s = sc.nextLine(); if (!s.isEmpty()) tt.setSpecialSkill(s);
    //                 System.out.println("Đã cập nhật: " + tt.toDisplay());
    //                 break;
    //             case "4":
    //                 System.out.print("ID nhân viên: "); String id2 = sc.nextLine().trim();
    //                 Employee e2 = em.findById(id2);
    //                 if (e2==null){ System.out.println("Không tìm thấy."); break;}
    //                 System.out.print("Ca mới (Sáng/Chiều/Tối): "); String newShift = sc.nextLine();
    //                 e2.setShift(newShift);
    //                 System.out.println("Đã cập nhật ca cho " + id2);
    //                 break;
    //             case "0": return;
    //             default: System.out.println("Chọn sai.");
    //         }
    //     }
    // }

    // private static void manageRegular(){
    //     while(true){
    //         System.out.println("\n--- Nhân viên bán hàng ---");
    //         System.out.println("1. Thêm nhân viên");
    //         System.out.println("2. Hiển thị nhân viên");
    //         System.out.println("3. Sửa nhân viên");
    //         System.out.println("4. Phân ca làm");
    //         System.out.println("0. Quay lại");
    //         System.out.print("Chọn: ");
    //         String c = sc.nextLine().trim();
    //         switch(c){
    //             case "1":
    //                 System.out.print("Tên: "); String name = sc.nextLine();
    //                 System.out.print("Phone: "); String phone = sc.nextLine();
    //                 System.out.print("Công việc: "); String role = sc.nextLine();
    //                 System.out.print("Ca làm (Sáng/Chiều/Tối): "); String shift = sc.nextLine();
    //                 RegularEmployee r = new RegularEmployee("", name, phone, role, shift);
    //                 em.add(r);
    //                 System.out.println("Đã thêm: " + r.toDisplay());
    //                 break;
    //             case "2":
    //                 for(Employee e: em.all()) if (e instanceof RegularEmployee) System.out.println(e.toDisplay());
    //                 break;
    //             case "3":
    //                 System.out.print("ID cần sửa: "); String id = sc.nextLine().trim();
    //                 Employee ee = em.findById(id);
    //                 if (ee==null || !(ee instanceof RegularEmployee)){ System.out.println("Không tìm thấy nhân viên"); break;}
    //                 RegularEmployee rr = (RegularEmployee) ee;
    //                 System.out.print("Tên mới (Enter giữ): "); String s = sc.nextLine(); if (!s.isEmpty()) rr.setName(s);
    //                 System.out.print("Phone mới (Enter giữ): "); s = sc.nextLine(); if (!s.isEmpty()) rr.setPhone(s);
    //                 System.out.print("Ca làm mới (Enter giữ): "); s = sc.nextLine(); if (!s.isEmpty()) rr.setShift(s);
    //                 System.out.println("Đã cập nhật: " + rr.toDisplay());
    //                 break;
    //             case "4":
    //                 System.out.print("ID nhân viên: "); String id2 = sc.nextLine().trim();
    //                 Employee e2 = em.findById(id2);
    //                 if (e2==null){ System.out.println("Không tìm thấy."); break;}
    //                 System.out.print("Ca mới (Sáng/Chiều/Tối): "); String newShift = sc.nextLine();
    //                 e2.setShift(newShift);
    //                 System.out.println("Đã cập nhật ca cho " + id2);
    //                 break;
    //             case "0": return;
    //             default: System.out.println("Chọn sai.");
    //         }
    //     }
    // }

    // private static void listAllEmployees(){
    //     List<Employee> list = em.all();
    //     if (list.isEmpty()){ System.out.println("Chưa có nhân viên."); return;}
    //     for(Employee e: list) System.out.println(e.toDisplay());
    // }

    // private static void searchEmployees(){
    //     System.out.print("Nhập từ khoá tên: "); String q = sc.nextLine();
    //     List<Employee> r = em.searchByName(q);
    //     if (r.isEmpty()) System.out.println("Không tìm thấy.");
    //     else for(Employee e: r) System.out.println(e.toDisplay());
    // }

    // private static void deleteEmployee(){
    //     System.out.print("ID cần xóa: "); String id = sc.nextLine().trim();
    //     Employee e = em.findById(id);
    //     if (e==null){ System.out.println("Không tìm thấy."); return;}
    //     em.remove(id);
    //     System.out.println("Đã xóa " + id);
    // }
    public static void employeeMenu() {
        em.loadFromFile();

        while (true) {
            System.out.println("\n===== MENU QUẢN LÝ NHÂN VIÊN =====");
            System.out.println("1. Thêm nhân viên");
            System.out.println("2. Hiển thị danh sách");
            System.out.println("3. Tìm nhân viên theo từ khóa");
            System.out.println("4. Xóa nhân viên");
            System.out.println("5. Cập nhật ca làm");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> addEmployeeMenu();
                case "2" -> em.displayAll();
                case "3" -> findEmployee();
                case "4" -> removeEmployee();
                case "5" -> updateShift();
                case "0" -> {
                    System.out.println("Thoát chương trình!");
                    em.saveToFile();
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // === Thêm nhân viên mới ===
    private static void addEmployeeMenu() {
        System.out.println("\n--- Chọn bộ phận ---");
        System.out.println("1. Quản lý (Manager)");
        System.out.println("2. Bán hàng (Sales)");
        System.out.println("3. Hỗ trợ (Support)");
        System.out.print("Chọn: ");
        String type = sc.nextLine().trim();

        System.out.print("Họ tên: ");
        String name = sc.nextLine();
        System.out.print("SĐT: ");
        String phone = sc.nextLine();
        System.out.print("Chức vụ: ");
        String role = sc.nextLine();
        System.out.print("Ca làm (Sáng/Chiều/Tối): ");
        String shift = sc.nextLine();

        switch (type) {
            case "1" -> {
                System.out.print("Cấp bậc (level): ");
                String level = sc.nextLine();
                Employee m = new managerDepartment("", name, phone, role, level, shift);
                em.addEmployee(m);
                System.out.println("Đã thêm: " + m.getId());
            }
            case "2" -> {
                System.out.print("Nhiệm vụ (Bán hàng/Chăm sóc KH): ");
                String task = sc.nextLine();
                Employee s = new salesDepartment("", name, phone, role, task, shift);
                em.addEmployee(s);
                System.out.println("Đã thêm: " + s.getId());
            }
            case "3" -> {
                System.out.print("Bộ phận (section): ");
                String section = sc.nextLine();
                System.out.print("Kỹ năng đặc biệt: ");
                String skill = sc.nextLine();
                Employee sp = new supportDepartment("", name, phone, role, section, shift, skill);
                em.addEmployee(sp);
                System.out.println("Đã thêm: " + sp.getId());
            }
            default -> System.out.println("Lựa chọn không hợp lệ!");
        }
    }

    // === Tìm nhân viên theo ID ===
    private static void findEmployee() {
        System.out.print("Nhập từ khóa cần tìm: ");
        String key = sc.nextLine().trim().toLowerCase();
        boolean found = false;

        for (Employee e : em.getAll()) {
            if (e.getId().toLowerCase().contains(key)
            || e.getName().toLowerCase().contains(key)
            || e.getRole().toLowerCase().contains(key)) {
                System.out.println(e.toDisplay());
                found = true;
            }
        }

        if (!found)
            System.out.println("Không tìm thấy nhân viên!");
    }


    // === Xóa nhân viên ===
    private static void removeEmployee() {
        System.out.print("Nhập mã nhân viên cần xóa: ");
        String id = sc.nextLine().trim();
        if (em.removeById(id))
            System.out.println("Đã xóa nhân viên " + id);
        else
            System.out.println("Không tìm thấy mã nhân viên!");
    }


    // === Cập nhật ca làm ===
    private static void updateShift() {
        System.out.print("Nhập mã nhân viên: ");
        String id = sc.nextLine().trim();
        System.out.print("Nhập ca làm mới (Sáng/Chiều/Tối): ");
        String shift = sc.nextLine().trim();
        if (em.updateShift(id, shift))
            System.out.println("Cập nhật thành công!");
        else
            System.out.println("Không tìm thấy nhân viên!");
    }

    // Customer menu
    private static void customerMenu(){
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

    private static void addCustomer(){
        System.out.print("Tên: "); String name = sc.nextLine();
        System.out.print("Phone: "); String phone = sc.nextLine();
        System.out.print("Email: "); String email = sc.nextLine();
        Customer c = new Customer("", name, phone, email);
        cm.add(c);
        System.out.println("Đã thêm: " + c);
    }

    private static void listCustomers(){
        List<Customer> list = cm.all();
        if (list.isEmpty()){ System.out.println("Chưa có khách hàng."); return;}
        for(Customer c: list) System.out.println(c);
    }

    private static void editCustomer(){
        System.out.print("ID khách cần sửa: "); String id = sc.nextLine().trim();
        Customer c = cm.findById(id);
        if (c==null){ System.out.println("Không tìm thấy."); return;}
        System.out.println("Khách hiện tại: " + c);
        System.out.print("Tên mới (Enter giữ): "); String s = sc.nextLine(); if (!s.isEmpty()) c.setName(s);
        System.out.print("Phone mới (Enter giữ): "); s = sc.nextLine(); if (!s.isEmpty()) c.setPhone(s);
        System.out.print("Email mới (Enter giữ): "); s = sc.nextLine(); if (!s.isEmpty()) c.setEmail(s);
        System.out.println("Đã cập nhật: " + c);
    }

    private static void deleteCustomer(){
        System.out.print("ID khách cần xóa: "); String id = sc.nextLine().trim();
        Customer c = cm.findById(id);
        if (c==null){ System.out.println("Không tìm thấy."); return;}
        cm.remove(id);
        System.out.println("Đã xóa " + id);
    }

    private static void searchCustomer(){
        System.out.print("Nhập từ khoá tên: "); String q = sc.nextLine();
        List<Customer> r = cm.searchByName(q);
        if (r.isEmpty()) System.out.println("Không tìm thấy.");
        else for(Customer c: r) System.out.println(c);
    }

    // Order menu
    private static void orderMenu(){
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
                case "0": return;
                default: System.out.println("Chọn sai.");
            }
        }
    }

    private static void createOrder(){
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
            System.out.print("Số lượng mua: "); int q = readInt();
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

    private static void listOrders(){
        List<Order> list = om.all();
        if (list.isEmpty()){ System.out.println("Chưa có đơn hàng."); return;}
        for(Order o: list) System.out.println(o);
    }

    private static void updateOrderStatus(){
        System.out.print("ID đơn cần cập nhật: "); String id = sc.nextLine().trim();
        Order o = om.findById(id);
        if (o==null){ System.out.println("Không tìm thấy."); return;}
        System.out.println("Trạng thái hiện tại: " + o.getStatus());
        System.out.print("Trạng thái mới (New / Processing / Delivered / Cancelled): "); String s = sc.nextLine();
        o.setStatus(s);
        System.out.println("Đã cập nhật trạng thái.");
    }

    private static void deleteOrder(){
        System.out.print("ID đơn cần xóa: "); String id = sc.nextLine().trim();
        Order o = om.findById(id);
        if (o==null){ System.out.println("Không tìm thấy."); return;}
        om.remove(id);
        System.out.println("Đã xóa đơn " + id);
    }

    private static void searchOrders(){
        System.out.print("Nhập từ khoá tên khách: "); String q = sc.nextLine();
        List<Order> r = om.searchByCustomerName(q);
        if (r.isEmpty()) System.out.println("Không tìm thấy.");
        else for(Order o: r) System.out.println(o);
    }

    // File menu
    private static void fileMenu(){
        while(true){
            System.out.println("\n--- Đọc / Ghi file ---");
            System.out.println("1. Ghi tất cả dữ liệu ra file");
            System.out.println("2. Đọc tất cả dữ liệu từ file (ghi đè bộ nhớ hiện tại)");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");
            String c = sc.nextLine().trim();
            switch(c){
                case "1": writeAllFiles(); break;
                case "2": readAllFiles(); break;
                case "0": return;
                default: System.out.println("Chọn sai.");
            }
        }
    }

    private static void writeAllFiles(){
        pm.saveToFile();
        //em.saveToFile();
        cm.saveToFile();
        om.saveToFile();
        System.out.println("Hoàn tất ghi dữ liệu ra file.");
    }

    private static void readAllFiles(){
        System.out.print("Bạn có chắc chắn muốn đọc file và GHI ĐÈ dữ liệu đang có trong bộ nhớ? (y/n): ");
        String ans = sc.nextLine().trim().toLowerCase();
        if (!ans.equals("y")) { System.out.println("Huỷ thao tác đọc file."); return;}
        pm.loadFromFile();
        ///em.loadFromFile();
        cm.loadFromFile();
        om.loadFromFile();
        System.out.println("Hoàn tất đọc dữ liệu từ file.");
    }

    // helpers
    private static int readInt(){
        while(true){
            String s = sc.nextLine().trim();
            try { return Integer.parseInt(s); } catch(Exception e){ System.out.print("Nhập số nguyên: "); }
        }
    }
    private static double readDouble(){
        while(true){
            String s = sc.nextLine().trim();
            try { return Double.parseDouble(s); } catch(Exception e){ System.out.print("Nhập số (vd 1000.0): "); }
        }
    }
}