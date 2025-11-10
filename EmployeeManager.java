
import java.io.*;
import java.util.*;

public class EmployeeManager {
    private List<Employee> employees = new ArrayList<>();
    private final String filename = "employees.txt";

    // ====== ĐỌC DỮ LIỆU TỪ FILE ======
    public void loadFromFile() {
        employees.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|", -1);
                if (p.length < 5) continue; // dòng lỗi -> bỏ qua
                String id = p[0];
                if (id.startsWith("EM")) {
                    // Quản lý: id|name|phone|role|level|shift
                    if (p.length >= 6)
                        employees.add(new managerDepartment(p[0], unescape(p[1]), p[2], p[3], p[4], p[5]));
                } 
                else if (id.startsWith("ES")) {
                    // Bán hàng: id|name|phone|role|task|shift
                    if (p.length >= 6)
                        employees.add(new salesDepartment(p[0], unescape(p[1]), p[2], p[3], p[4], p[5]));
                } 
                else if (id.startsWith("ESP")) {
                    // Hỗ trợ: id|name|phone|role|section|specialSkill|shift
                    if (p.length >= 7)
                        employees.add(new supportDepartment(p[0], unescape(p[1]), p[2], p[3], p[4], p[6], p[5]));
                }
            }
            System.out.println("Đã tải " + employees.size() + " nhân viên từ file.");
        } catch (IOException e) {
            System.out.println("Không thể đọc file: " + e.getMessage());
        }
    }
    // ====== LƯU DỮ LIỆU RA FILE ======
    public void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Employee e : employees) {
                pw.println(e.toCSV());
            }
            System.out.println("Đã lưu danh sách nhân viên vào file.");
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }
    // ====== THÊM NHÂN VIÊN ======
    public void addEmployee(Employee e) {
        employees.add(e);
        saveToFile();
    }
    // ====== XÓA NHÂN VIÊN ======
    public boolean removeById(String id) {
        boolean removed = employees.removeIf(e -> e.getId().equalsIgnoreCase(id));
        if (removed) {
            saveToFile();
        }
        return removed;
    }
    // ====== TÌM NHÂN VIÊN ======
    public List<Employee> findEmployee(String key) {
        key = key.toLowerCase();
        List<Employee> result = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getId().toLowerCase().contains(key)
                    || e.getName().toLowerCase().contains(key)
                    || e.getRole().toLowerCase().contains(key)) {
                result.add(e);
            }
        }
        return result;
    }
    
    // ====== CẬP NHẬT THÔNG TIN NHÂN VIÊN ======
    public boolean updateShift(String id, String newShift) {
        for (Employee e : employees) {
            if (e.getId().equalsIgnoreCase(id)) {
                e.setShift(newShift);
                saveToFile();
                return true;
            }
        }
        return false;
    }
    
    // ====== ESCAPE/DESCAPE ======
    private static String unescape(String s) { return s.replace("/p", "|"); }
    
    // ====== GET LIST ======
    public List<Employee> getAll() {
        return employees;
    }
}


