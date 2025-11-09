import java.io.*;
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
                case "4" -> {
                    System.out.print("Nhập từ khóa cần tìm: ");
                    String keyword = sc.nextLine();
                    var found = search(keyword);
                    display(found);
                }
                case "5" -> filterAndDisplay();
                case "6" -> display(pm.getList());
                case "0" -> {
                    pm.saveToFile();
                    System.out.println("Quay lại menu chính!");
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    public void addProduct() {
        System.out.print("Tên sản phẩm: ");
        String name = sc.nextLine().trim();

        System.out.print("Giá: ");
        long price = pm.readLong();

        System.out.print("Số lượng: ");
        int qty = pm.readInt();

        System.out.print("Danh mục: ");
        String category = sc.nextLine().trim();

        Product p = new Product("", name, price, qty, category);
        pm.add(p);
        pm.saveToFile();
        System.out.println("✅ Đã thêm: " + p);
    }

    public void editProduct() {
        System.out.print("Nhập ID sản phẩm cần sửa: ");
        String id = sc.nextLine().trim();
        Product p = pm.findById(id);
        if (p == null) {
            System.out.println("❌ Không tìm thấy!");
            return;
        }

        System.out.println("🔍 Hiện tại: " + p);
        System.out.print("Tên mới: ");
        String name = sc.nextLine().trim();
        if (!name.isEmpty()) p.setName(name);

        System.out.print("Giá mới: ");
        String priceStr = sc.nextLine().trim();
        if (!priceStr.isEmpty()) p.setPrice(Long.parseLong(priceStr));

        System.out.print("Số lượng mới: ");
        String qtyStr = sc.nextLine().trim();
        if (!qtyStr.isEmpty()) p.setQuantity(Integer.parseInt(qtyStr));

        System.out.print("Danh mục mới: ");
        String cat = sc.nextLine().trim();
        if (!cat.isEmpty()) p.setCategory(cat);

        pm.saveToFile();
        System.out.println("✅ Đã cập nhật!");
    }

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
            System.out.println("❌ Không tìm thấy!");
            return;
        }

        System.out.print("Xóa sản phẩm " + found.getName() + "? (y/n): ");
        if (sc.nextLine().trim().equalsIgnoreCase("y")) {
            pm.getList().remove(found);
            pm.saveToFile();
            System.out.println("✅ Đã xóa!");
        }
    }

    public List<Product> search(String keyword) {
        keyword = keyword.toLowerCase();
        List<Product> result = new ArrayList<>();
        for (Product p : pm.getList()) {
            if ((p.getId() + p.getName() + p.getCategory()).toLowerCase().contains(keyword))
                result.add(p);
        }
        return result;
    }

    public void filterAndDisplay() {
        System.out.print("Nhập loại: ");
        String category = sc.nextLine().trim();

        System.out.print("Nhập khoảng giá (vd: 500000-2000000): ");
        String range = sc.nextLine().trim();
        Double min = null, max = null;
        if (range.contains("-")) {
            String[] parts = range.split("-");
            min = Double.parseDouble(parts[0].trim());
            max = Double.parseDouble(parts[1].trim());
        }

        List<Product> result = new ArrayList<>();
        for (Product p : pm.getList()) {
            boolean matchCat = category.equalsIgnoreCase("Tất cả") ||
                               p.getCategory().equalsIgnoreCase(category);
            boolean matchPrice = (min == null || p.getPrice() >= min) &&
                                 (max == null || p.getPrice() <= max);
            if (matchCat && matchPrice)
                result.add(p);
        }

        display(result);
    }

    public void display(List<Product> products) {
        if (products.isEmpty()) {
            System.out.println("❌ Không có sản phẩm!");
            return;
        }
        System.out.println("===== DANH SÁCH SẢN PHẨM =====");
        for (Product p : products) System.out.println(p);
    }

}
