import java.util.*;

public class EmployeeMenu {
    private EmployeeManager em;
    private Scanner sc = new Scanner(System.in);

    public EmployeeMenu(EmployeeManager em) {
        this.em = em;
    }

    public void employeeMenu() {
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
                case "1" -> addEmployee();
                case "2" -> displayAll();
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

    // ====== THÊM NHÂN VIÊN ======
    public void addEmployee() {
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
                System.out.print("Cấp bậc (Trưởng/ Phó): ");
                String level = sc.nextLine();
                Employee m = new managerDepartment("", name, phone, role, level, shift);
                em.addEmployee(m);
                em.saveToFile();
                System.out.println("Đã thêm: " + m.getId());
            }
            case "2" -> {
                System.out.print("Nhiệm vụ (Bán hàng/Chăm sóc KH): ");
                String task = sc.nextLine();
                Employee s = new salesDepartment("", name, phone, role, task, shift);
                em.addEmployee(s);
                em.saveToFile();
                System.out.println("Đã thêm: " + s.getId());
            }
            case "3" -> {
                System.out.print("Bộ phận (section): ");
                String section = sc.nextLine();
                System.out.print("Kỹ năng đặc biệt: ");
                String skill = sc.nextLine();
                Employee sp = new supportDepartment("", name, phone, role, section, shift, skill);
                em.addEmployee(sp);
                em.saveToFile();
                System.out.println("Đã thêm: " + sp.getId());
            }
            default -> System.out.println("Lựa chọn không hợp lệ!");
        }
    }

    // ====== XÓA NHÂN VIÊN ======
    public void removeEmployee() {
        System.out.print("Nhập mã nhân viên cần xóa: ");
        String id = sc.nextLine().trim();
        Employee find = null;
        for (Employee e : em.getAll()) {
            if (e.getId().equalsIgnoreCase(id) || e.getName().equalsIgnoreCase(id)) {
                find = e;
                break;
            }
        }
        if (find == null) {
            System.out.println("Không tìm thấy nhân viên nào phù hợp!");
            return;
        }
        System.out.println("Xóa nhân viên: " + find.getName() + "? (y/n)");
        if (sc.nextLine().trim().equalsIgnoreCase("y")) {
            em.removeById(find.getId());
            em.saveToFile();
            System.out.println("Đã xóa sản phẩm!");
        } else {
            System.out.println("Hủy thao tác.");
        }
    }


    // ====== TÌM NHÂN VIÊN ======
    public void findEmployee() {
        System.out.print("Nhập từ khóa cần tìm: ");
        String keyword = sc.nextLine().toLowerCase();
        List<Employee> result = new ArrayList<>();
        for (Employee e : em.getAll()) {
            String combined = (e.getId().toLowerCase() + " " + e.getName().toLowerCase());
            if (combined.contains(keyword))
                result.add(e);
        }
        display(result, "===== HIỂN THỊ KẾT QUẢ TÌM KIẾM =====");
    }

    // ====== HIỂN THỊ DANH SÁCH ======
    public void display(List<Employee> em, String title) {
        if (em.isEmpty()) {
            System.out.println("Không có nhân viên nào.");
            return;
        }
        System.out.println(title);
        for (Employee e : em) {
            System.out.println(e.toDisplay());
        }
    }
    public void displayAll(){
        display(em.getAll(), "===== DANH SÁCH NHÂN VIÊN =====");
    }

    // ====== CẬP NHẬT THÔNG TIN NHÂN VIÊN ======
    public void updateShift() {
    System.out.print("Nhập mã nhân viên: ");
    String id = sc.nextLine().trim();

    Employee found = null;
    for (Employee e : em.getAll()) {
        if (e.getId().equalsIgnoreCase(id)) {
            found = e;
            break;
        }
    }

    if (found == null) {
        System.out.println("Không tìm thấy nhân viên có mã " + id);
        return;
    }

    System.out.println("Nhân viên: " + found.getName() + " (Ca hiện tại: " + found.getShift() + ")");
    System.out.println("Chọn ca làm mới:");
    System.out.println("1. Sáng");
    System.out.println("2. Chiều");
    System.out.println("3. Tối");
    System.out.println("4. Cả ngày");
    System.out.print("Chọn (1-4): ");

    String choice = sc.nextLine().trim();
    String newShift;
    switch (choice) {
        case "1" -> newShift = "Sáng";
        case "2" -> newShift = "Chiều";
        case "3" -> newShift = "Tối";
        case "4" -> newShift = "Cả ngày";
        default -> {
            System.out.println("Lựa chọn không hợp lệ!");
            return;
        }
    }

    found.setShift(newShift);
    em.saveToFile();
    System.out.println("Đã cập nhật ca làm mới cho " + found.getName() + ": " + newShift);
    }
}