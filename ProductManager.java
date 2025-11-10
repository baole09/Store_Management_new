import java.io.*;
import java.util.*;

public class ProductManager {
    private List<Product> list = new ArrayList<>();
    private final String fileName = "products.txt";
    private final Scanner sc = new Scanner(System.in);

    public List<Product> getList() {
        return list;
    }

    public void add(Product p) {
        list.add(p);
    }

    public Product findById(String id) {
        for (Product p : list)
            if (p.getId().equalsIgnoreCase(id))
                return p;
        return null;
    }

    public void loadFromFile() {
        list.clear();
        File f = new File(fileName);
        if (!f.exists()) {
            System.out.println("⚠️ File " + fileName + " chưa tồn tại, sẽ được tạo khi lưu.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                Product p = Product.fromCSV(line);
                if (p != null) list.add(p);
            }
            System.out.println("✅ Đã đọc " + list.size() + " sản phẩm từ file " + fileName);
        } catch (IOException e) {
            System.out.println("⚠️ Lỗi đọc file: " + e.getMessage());
        }
    }

    // === Ghi dữ liệu ra file (theo định dạng |) ===
    public void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (Product p : list) {
                pw.println(p.toCSV());
            }
            System.out.println("💾 Đã lưu " + list.size() + " sản phẩm vào " + fileName);
        } catch (IOException e) {
            System.out.println("⚠️ Lỗi ghi file: " + e.getMessage());
        }
    }

    public int readInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("⚠️ Nhập lại số nguyên: ");
            }
        }
    }

    public long readLong() {
        while (true) {
            try {
                return Long.parseLong(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("⚠️ Nhập lại số hợp lệ: ");
            }
        }
    }
}
