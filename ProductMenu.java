import java.util.*;

public class ProductMenu {
    private final Scanner sc = new Scanner(System.in);
    
    private final ProductManager pm;

    public ProductMenu(ProductManager pm) {
        this.pm = pm;
    }

    public void showMenu() {
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
                case "1" -> addProduct();
                case "2" -> editProduct();
                case "3" -> deleteProduct();
                case "4" -> searchProduct();
                case "5" -> filterProduct();
                case "6" -> displayAll();
                case "0" -> {
                    pm.saveToFile();
                    System.out.println("Quay lại menu chính!");
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // Thêm sản phẩm mới
    public void addProduct() {
        System.out.print("Tên sản phẩm: ");
        String name = sc.nextLine().trim();

        System.out.print("Giá: ");
        long price = pm.readLong();

        System.out.print("Số lượng: ");
        int qty = pm.readInt();

        System.out.println("Chọn danh mục sản phẩm:");
        System.out.println("1. Laptop");
        System.out.println("2. Dien thoai");
        System.out.println("3. May tinh bang");
        System.out.println("4. Phu kien");

        String category = "";
        while (true) {
            System.out.print("Nhập số lựa chọn (1-4): ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1" -> category = "Laptop";
                case "2" -> category = "Dien thoai";
                case "3" -> category = "May tinh bang";
                case "4" -> category = "Phu kien";
                default -> {
                    System.out.println("Lựa chọn không hợp lệ, vui lòng nhập lại!");
                    continue;
                }
            }
            break;
        }


        Product p = new Product("", name, price, qty, category);
        pm.add(p);
        pm.saveToFile();
        System.out.println("Đã thêm sản phẩm: " + p);
    }

    // Sửa thông tin sản phẩm
    public void editProduct() {
        System.out.print("Nhập ID sản phẩm cần sửa: ");
        String id = sc.nextLine().trim();
        Product p = pm.findById(id);
        if (p == null) {
            System.out.println("Không tìm thấy sản phẩm có ID " + id);
            return;
        }

        System.out.println("Sản phẩm hiện tại: " + p);
        System.out.print("Tên mới (Enter để giữ nguyên): ");
        String name = sc.nextLine().trim();
        if (!name.isEmpty()) p.setName(name);

        System.out.print("Giá mới (Enter để giữ nguyên): ");
        String priceStr = sc.nextLine().trim();
        if (!priceStr.isEmpty()) p.setPrice(Long.parseLong(priceStr));

        System.out.print("Số lượng mới (Enter để giữ nguyên): ");
        String qtyStr = sc.nextLine().trim();
        if (!qtyStr.isEmpty()) p.setQuantity(Integer.parseInt(qtyStr));

        System.out.print("Danh mục mới (Enter để giữ nguyên): ");
        String cat = sc.nextLine().trim();
        if (!cat.isEmpty()) p.setCategory(cat);

        pm.saveToFile();
        System.out.println("Đã cập nhật: " + p);
    }

    // Xóa sản phẩm theo ID hoặc tên chính xác
    public void deleteProduct() {
        System.out.print("Nhập ID hoặc tên sản phẩm cần xóa: ");
        String input = sc.nextLine().trim();

        Product found = null;
        for (Product p : pm.getList()) {
            if (p.getId().equalsIgnoreCase(input) || p.getName().equalsIgnoreCase(input)) {
                found = p;
                break;
            }
        }

        if (found == null) {
            System.out.println("Không tìm thấy sản phẩm!");
            return;
        }

        System.out.println("Xóa sản phẩm: " + found.getName() + "? (y/n)");
        if (sc.nextLine().trim().equalsIgnoreCase("y")) {
            pm.getList().remove(found);
            pm.saveToFile();
            System.out.println("Đã xóa sản phẩm!");
        } else {
            System.out.println("Hủy thao tác.");
        }
    }

    // ====== TÌM KIẾM & LỌC ======

    // Tìm kiếm theo từ khóa (ID, tên, hoặc danh mục)
    public void searchProduct() {
        System.out.print("Nhập từ khóa cần tìm: ");
        String keyword = sc.nextLine().toLowerCase();
        List<Product> result = new ArrayList<>();
        for (Product p : pm.getList()) {
            String combined = (p.getId() + " " + p.getName() + " " + p.getCategory()).toLowerCase();
            if (combined.contains(keyword))
                result.add(p);
        }
        display(result, "===== HIỂN THỊ DANH SÁCH TÌM KIẾM =====");
    }

    // Lọc theo danh mục và khoảng giá
    public void filterProduct() {
        System.out.println("Chọn loại sản phẩm:");
        System.out.println("1. Laptop");
        System.out.println("2. Điện thoại");
        System.out.println("3. Máy tính bảng");
        System.out.println("4. Phụ kiện");
        System.out.println("5. Tất cả");

        String categoryInput = "";
        while (true) {
            System.out.print("Nhập số lựa chọn (1-5): ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1" -> { categoryInput = "Laptop"; break; }
        case "2" -> { categoryInput = "Dien thoai"; break; }
        case "3" -> { categoryInput = "May tinh bang"; break; }
        case "4" -> { categoryInput = "Phu kien"; break; }
        case "5" -> { categoryInput = "Tat ca"; break; }
                default -> {
                    System.out.println("Lựa chọn không hợp lệ, vui lòng nhập lại!");
                    continue;
                }
            }
            break; // thoát vòng while khi chọn đúng
        }

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
        List<Product> result = new ArrayList<>();
        for (Product p : pm.getList()) {
            boolean matchCat = categoryInput.equalsIgnoreCase("Tat ca") || 
                               p.getCategory().equalsIgnoreCase(categoryInput);
            boolean matchPrice = (min == null || p.getPrice() >= min) &&
                                 (max == null || p.getPrice() <= max);
            if (matchCat && matchPrice)
                result.add(p);
        }
        display(result, "===== HIỂN THỊ DANH SÁCH ĐÃ LỌC =====");
    }

    // Hiển thị danh sách sản phẩm
    public void display(List<Product> products, String title) {
        if (products.isEmpty()) {
            System.out.println("Không có sản phẩm nào để hiển thị!");
            return;
        }
        System.out.println(title);
        for (Product p : products) 
            System.out.println(p);
    }

    public void displayAll() {
        display(pm.getList(), "===== DANH SÁCH SẢN PHẨM =====");
    }

}
