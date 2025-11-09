import java.util.Scanner;

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
    public  void addEmployeeMenu() {
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
    public void findEmployee() {
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
    public  void removeEmployee() {
        System.out.print("Nhập mã nhân viên cần xóa: ");
        String id = sc.nextLine().trim();
        if (em.removeById(id))
            System.out.println("Đã xóa nhân viên " + id);
        else
            System.out.println("Không tìm thấy mã nhân viên!");
    }


    // === Cập nhật ca làm ===
    public  void updateShift() {
        System.out.print("Nhập mã nhân viên: ");
        String id = sc.nextLine().trim();
        System.out.print("Nhập ca làm mới (Sáng/Chiều/Tối): ");
        String shift = sc.nextLine().trim();
        if (em.updateShift(id, shift))
            System.out.println("Cập nhật thành công!");
        else
            System.out.println("Không tìm thấy nhân viên!");
    }
}
