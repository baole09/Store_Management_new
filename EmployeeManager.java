
import java.io.*;
import java.util.*;

// public class EmployeeManager {

//     private List<Employee> employees = new ArrayList<>();
//     private final String filename = "employee.txt";

//     public EmployeeManager() {
//         loadFromFile();
//     }

//     // 🔹 Lấy danh sách
//     public List<Employee> getAll() {
//         return employees;
//     }

//     // 🔹 Thêm nhân viên (tự động lưu)
//     public void add(Employee e) {
//         employees.add(e);
//         saveToFile();
//     }

//     // 🔹 Xóa nhân viên theo ID (tự động lưu)
//     public boolean removeById(String id) {
//         boolean removed = employees.removeIf(e -> e.getId().equalsIgnoreCase(id));
//         if (removed) {
//             saveToFile();
//         }
//         return removed;
//     }

//     // 🔹 Tìm nhân viên theo từ khóa
//     public List<Employee> findEmployee(String key) {
//         key = key.toLowerCase();
//         List<Employee> result = new ArrayList<>();
//         for (Employee e : employees) {
//             if (e.getId().toLowerCase().contains(key)
//                     || e.getName().toLowerCase().contains(key)
//                     || e.getRole().toLowerCase().contains(key)) {
//                 result.add(e);
//             }
//         }
//         return result;
//     }

//     // Hiển thị danh sách
//     public void displayAll() {
//         if (employees.isEmpty()) {
//             System.out.println("Danh sách nhân viên trống!");
//             return;
//         }

//         System.out.println("\n===== DANH SÁCH NHÂN VIÊN =====");
//         employees.forEach(e -> System.out.println(e.toDisplay()));
//         System.out.println("Tổng số nhân viên: " + employees.size());
//     }

//     // ====== CẬP NHẬT THÔNG TIN NHÂN VIÊN ======
//     public boolean updateShift(String id, String newShift) {
//         for (Employee e : employees) {
//             if (e.getId().equalsIgnoreCase(id)) {
//                 e.setShift(newShift);
//                 saveToFile();
//                 return true;
//             }
//         }
//         return false;
//     }

//     // 🔹 Lưu file
//     public void saveToFile() {
//         try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
//             for (Employee e : employees) {
//                 pw.println(e.toCSV());
//             }
//         } catch (IOException e) {
//             System.out.println("Lỗi lưu file: " + e.getMessage());
//         }
//     }

//     // 🔹 Đọc file (tự động xác định loại nhân viên)
//     // 🔹 Đọc file (tự động xác định loại nhân viên)
//     public void loadFromFile() {
//         employees.clear();
//         try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
//             String line;
//             while ((line = br.readLine()) != null) {
//                 String[] parts = line.split("\\|");
//                 if (parts.length < 5) {
//                     System.out.println("⚠️ Dòng lỗi, bỏ qua: " + line);
//                     continue;
//                 }

//                 String id = parts[0].replace("\uFEFF", "");
//                 String name = parts[1].replace("/p", "|");
//                 String phone = parts[2];
//                 String role = parts[3];

//                 // ⚠️ Quan trọng: kiểm tra ESP trước ES
//                 if (id.startsWith("ESP")) {
//                     if (parts.length >= 7) {
//                         String section = parts[4];
//                         String specialSkill = parts[5];
//                         String shift = parts[6];
//                         employees.add(new supportDepartment(id, name, phone, role, section, shift, specialSkill));
//                     }
//                 } else if (id.startsWith("ES")) {
//                     if (parts.length >= 6) {
//                         String task = parts[4];
//                         String shift = parts[5];
//                         employees.add(new salesDepartment(id, name, phone, role, task, shift));
//                     }
//                 } else if (id.startsWith("EM")) {
//                     if (parts.length >= 6) {
//                         String level = parts[4];
//                         String shift = parts[5];
//                         employees.add(new managerDepartment(id, name, phone, role, level, shift));
//                     }
//                 }
//             }
//             System.out.println("✅ Đã tải " + employees.size() + " nhân viên từ file.");
//         } catch (IOException e) {
//             System.out.println("(Chưa có dữ liệu nhân viên)");
//         }
//     }
// }

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
    // ====== HIỂN THỊ DANH SÁCH ======
    public void displayAll() {
        if (employees.isEmpty()) {
            System.out.println("Không có nhân viên nào.");
            return;
        }
        System.out.println("===== DANH SÁCH NHÂN VIÊN =====");
        for (Employee e : employees) {
            System.out.println(e.toDisplay());
        }
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
// import java.io.*;
// import java.util.*;
// public class EmployeeManager {
//     private List<Employee> list = new ArrayList<>();
//     private final String fileName = "employees.txt";
//     public List<Employee> all(){ 
//         return list; 
//     }
//     public void add(Employee e){ 
//         list.add(e); 
//     }
//     public Employee findById(String id){ 
//         for(Employee e:list) 
//             if(e.getId().equals(id)) 
//                 return e; 
//         return null; 
//     }
//     public void remove(String id){ 
//         list.removeIf(e->e.getId().equals(id)); 
//     }
//     public List<Employee> searchByName(String q){ 
//         List<Employee> r=new ArrayList<>(); 
//         for(Employee e:list) 
//             if(e.getName().toLowerCase().contains(q.toLowerCase())) 
//                 r.add(e); 
//         return r; 
//     }
//     public void saveToFile(){
//         try(PrintWriter pw = new PrintWriter(new FileWriter(fileName))){
//             for(Employee e: list) 
//                 pw.println(e.toCSV());
//             System.out.println("Đã ghi " + list.size() + " nhân viên ra " + fileName);
//         } 
//         catch(IOException e){ 
//             System.out.println("Lỗi khi ghi employees: " + e.getMessage()); 
//         }
//     }
//     public void loadFromFile(){
//         list.clear();
//         File f = new File(fileName);
//         if (!f.exists()){ 
//             System.out.println("File employees.txt không tồn tại."); 
//             return; 
//         }
//         try(BufferedReader br = new BufferedReader(new FileReader(f))){
//             String line; int cnt=0;
//             while((line=br.readLine())!=null){
//                 String[] p = line.split("\\|", -1);
//                 if (p.length>=2 && p[0].equals("EMP")){
//                     if (p.length>=7 && p[4].equals("Technician")){
//                         Technician t = Technician.fromParts(p);
//                         if (t!=null){ 
//                             list.add(t); 
//                             cnt++; 
//                         }
//                     } 
//                     else {
//                         RegularEmployee r = RegularEmployee.fromParts(p);
//                         if (r!=null){ 
//                             list.add(r); 
//                             cnt++; 
//                         }
//                     }
//                 }
//             }
//             System.out.println("Đã đọc " + cnt + " nhân viên từ " + fileName);
//         } 
//         catch(IOException e){ 
//             System.out.println("Lỗi khi đọc employees: " + e.getMessage()); 
//         }
//     }
// }

