import java.util.*;

public class StoreManagement {
    private static final Scanner sc = new Scanner(System.in);

    // Quản lý dữ liệu
    private static final ProductManager pm = new ProductManager();
    private static final EmployeeManager em = new EmployeeManager();
    private static final CustomerManager cm = new CustomerManager();
    private static final OrderManager om = new OrderManager();

    public static void main(String[] args) {
        System.out.println("=== CHƯƠNG TRÌNH QUẢN LÝ CỬA HÀNG ĐIỆN TỬ (Console) ===");

        // 🔹 Tự động đọc dữ liệu từ file khi khởi động
        autoLoad();

        // 🔹 Vào menu chính
        mainMenu();

        // 🔹 Tự động ghi dữ liệu ra file khi thoát
        autoSave();

        System.out.println("💾 Dữ liệu đã được lưu. Tạm biệt!");
    }

    private static void mainMenu() {
        while (true) {
            System.out.println("\n===== MENU CHÍNH =====");
            System.out.println("1. Quản lý sản phẩm");
            System.out.println("2. Quản lý nhân viên");
            System.out.println("3. Quản lý khách hàng");
            System.out.println("4. Quản lý đơn hàng");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1" -> productMenu();
                case "2" -> employeeMenu();
                case "3" -> customerMenu();
                case "4" -> orderMenu();
                case "0" -> {
                    System.out.println("Đang thoát chương trình...");
                    return;
                }
                default -> System.out.println("❌ Lựa chọn không hợp lệ, thử lại!");
            }
        }
    }

    // ====== SUB-MENUS ======

    private static void productMenu() {
        ProductMenu menu = new ProductMenu(pm);
        menu.showMenu();
    }

    private static void employeeMenu() {
        EmployeeMenu menu = new EmployeeMenu(em);
        menu.employeeMenu();
    }

    private static void customerMenu() {
        CustomerMenu menu = new CustomerMenu(cm);
        menu.customerMenu();
    }

    private static void orderMenu() {
        OrderMenu menu = new OrderMenu(om, cm, pm);
        menu.orderMenu();
    }

    // ====== TỰ ĐỘNG ĐỌC / GHI FILE ======

    private static void autoLoad() {
        try {
            pm.loadFromFile();
            em.loadFromFile();
            cm.loadFromFile();
            om.loadFromFile();
            System.out.println("✅ Dữ liệu đã được đọc từ file.");
        } catch (Exception e) {
            System.out.println("⚠️ Không thể đọc file hoặc file chưa tồn tại, bắt đầu mới.");
        }
    }

    private static void autoSave() {
        try {
            pm.saveToFile();
            em.saveToFile();
            cm.saveToFile();
            om.saveToFile();
        } catch (Exception e) {
            System.out.println("⚠️ Lỗi khi lưu dữ liệu: " + e.getMessage());
        }
    }
}
