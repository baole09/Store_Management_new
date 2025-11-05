import java.io.*;
import java.util.*;

public class EmployeeManager {
    private List<Employee> employees = new ArrayList<>();

    // ====== ĐỌC DỮ LIỆU TỪ FILE ======
    public void loadFromFile(String filename) {
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
    public void saveToFile(String filename) {
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
    }

    // ====== XÓA NHÂN VIÊN ======
    public boolean removeById(String id) {
        return employees.removeIf(e -> e.getId().equalsIgnoreCase(id));
    }

    // ====== TÌM NHÂN VIÊN ======
    public Employee findById(String id) {
        for (Employee e : employees) {
            if (e.getId().equalsIgnoreCase(id)) return e;
        }
        return null;
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
        Employee e = findById(id);
        if (e != null) {
            e.setShift(newShift);
            return true;
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

