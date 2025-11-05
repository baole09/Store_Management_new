// import java.io.*;
// import java.util.*;

// public class ProductManager {
//     private List<Product> list = new ArrayList<>();
//     private final String fileName = "products.txt";

//     // Lay toan bo danh sach
//     public List<Product> all(){ 
//         return list; 
//     }

//     // Them san pham
//     public void add(Product p){ 
//         list.add(p);
//         saveToFile(); 
//     }

//     // Tim theo ki tu
//     public List<Product> findByKeyword(String keyword) {
//         if (keyword == null || keyword.isBlank()) return List.of();

//         String kw = keyword.toLowerCase();
//         List<Product> result = new ArrayList<>();

//         for (Product p : list) {
//             String combined = (p.getId() + " " + p.getName() + " " + p.getCategory()).toLowerCase();
//             if (combined.contains(kw))
//                 result.add(p);
//         }
//         return result;
//     }

//     // Loc theo loai san pham, khoang gia
//     public List<Product> filter(String category, Double minPrice, Double maxPrice) {
//         List<Product> result = new ArrayList<>();

//         for (Product p : list) {
//             boolean match = true;

//             if (category != null && !category.equalsIgnoreCase("Tất cả")) {
//                 match &= p.getCategory().equalsIgnoreCase(category);
//             }

//             if (minPrice != null) match &= p.getPrice() >= minPrice;
//             if (maxPrice != null) match &= p.getPrice() <= maxPrice;

//             if (match) result.add(p);
//         }

//         return result;
//     }

//     // Tim kiem va loc san pham
//     public List<Product> searchAndFilter(String keyword, String category, Double minPrice, Double maxPrice) {
//         List<Product> filtered = filter(category, minPrice, maxPrice);

//         if (keyword == null || keyword.isBlank()) return filtered;

//         String kw = keyword.toLowerCase();
//         List<Product> result = new ArrayList<>();

//         for (Product p : filtered) {
//             String combined = (p.getId() + " " + p.getName() + " " + p.getCategory()).toLowerCase();
//             if (combined.contains(kw)) {
//                 result.add(p);
//             }
//         }

//         return result;
//     }


//     // public Product findById(String id){ 
//     //     for(Product p:list) 
//     //         if(p.getId().equals(id)) 
//     //             return p; 
//     //     return null;
//     // }

//     // public void remove(String id){ list.removeIf(p->p.getId().equals(id)); }
//     // public List<Product> searchByName(String q){ 
//     //     List<Product> r=new ArrayList<>(); 
//     //     for(Product p:list) 
//     //         if(p.getName().toLowerCase().contains(q.toLowerCase())) 
//     //             r.add(p); 
//     //     return r; 
//     // }

//     // Xoa theo ki tu
//     public void remove(String keyword) {
//         if (keyword == null || keyword.isEmpty()) {
//             System.out.println("Từ khóa không hợp lệ!");
//             return;
//         }

//         String kw = keyword.toLowerCase();
//         boolean removed = list.removeIf(p ->
//             p.getId().toLowerCase().contains(kw) ||
//             p.getName().toLowerCase().contains(kw)
//         );

//         if (removed) {
//             saveToFile(); 
//             System.out.println("Đã xóa sản phẩm có chứa: " + keyword);
//         } else {
//             System.out.println("Không tìm thấy sản phẩm phù hợp với: " + keyword);
//         }
//     }

//     public void saveToFile(){
//         try(PrintWriter pw = new PrintWriter(new FileWriter(fileName))){
//             for(Product p: list) 
//                 pw.println(p.toCSV());
//             System.out.println("Đã ghi " + list.size() + " sản phẩm ra " + fileName);
//         } catch(IOException e){ System.out.println("Lỗi khi ghi products: " + e.getMessage()); }
//     }
//     public void loadFromFile(){
//         list.clear();
//         File f = new File(fileName);
//         if (!f.exists()){ System.out.println("File products.txt không tồn tại."); return; }
//         try(BufferedReader br = new BufferedReader(new FileReader(f))){
//             String line; int cnt=0;
//             while((line=br.readLine())!=null){
//                 Product p = Product.fromCSV(line);
//                 if (p!=null){ list.add(p); cnt++; }
//             }
//             System.out.println("Đã đọc " + cnt + " sản phẩm từ " + fileName);
//         } catch(IOException e){ System.out.println("Lỗi khi đọc products: " + e.getMessage()); }
//     }
    
// }

import java.io.*;
import java.util.*;

public class ProductManager {
    private List<Product> list = new ArrayList<>();
    private final String fileName = "products.txt";
    private final Scanner sc = new Scanner(System.in);

    // ====== QUẢN LÝ DANH SÁCH ======

    // Thêm sản phẩm mới
    public void addProduct() {
        System.out.print("Tên sản phẩm: ");
        String name = sc.nextLine().trim();

        System.out.print("Giá: ");
        long price = readLong();

        System.out.print("Số lượng: ");
        int qty = readInt();

        System.out.print("Danh mục (Laptop/Điện thoại/Máy tính bảng/Phụ kiện): ");
        String category = sc.nextLine().trim();

        Product p = new Product("", name, price, qty, category);
        list.add(p);
        saveToFile();
        System.out.println("✅ Đã thêm sản phẩm: " + p);
    }

    // Sửa thông tin sản phẩm
    public void editProduct() {
        System.out.print("Nhập ID sản phẩm cần sửa: ");
        String id = sc.nextLine().trim();
        Product p = findById(id);
        if (p == null) {
            System.out.println("❌ Không tìm thấy sản phẩm có ID " + id);
            return;
        }

        System.out.println("🔍 Sản phẩm hiện tại: " + p);
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

        saveToFile();
        System.out.println("✅ Đã cập nhật: " + p);
    }

    // Xóa sản phẩm theo ID hoặc tên chính xác
    public void deleteProduct() {
        System.out.print("Nhập ID hoặc tên sản phẩm cần xóa: ");
        String input = sc.nextLine().trim();

        Product found = null;
        for (Product p : list) {
            if (p.getId().equalsIgnoreCase(input) || p.getName().equalsIgnoreCase(input)) {
                found = p;
                break;
            }
        }

        if (found == null) {
            System.out.println("❌ Không tìm thấy sản phẩm!");
            return;
        }

        System.out.println("🗑️ Xóa sản phẩm: " + found.getName() + "? (y/n)");
        if (sc.nextLine().trim().equalsIgnoreCase("y")) {
            list.remove(found);
            saveToFile();
            System.out.println("✅ Đã xóa sản phẩm!");
        } else {
            System.out.println("❎ Hủy thao tác.");
        }
    }

    // ====== TÌM KIẾM & LỌC ======

    // Tìm kiếm theo từ khóa (ID, tên, hoặc danh mục)
    public List<Product> search(String keyword) {
        keyword = keyword.toLowerCase();
        List<Product> result = new ArrayList<>();
        for (Product p : list) {
            String combined = (p.getId() + " " + p.getName() + " " + p.getCategory()).toLowerCase();
            if (combined.contains(keyword))
                result.add(p);
        }
        return result;
    }

    // Lọc theo danh mục và khoảng giá
    public List<Product> filter(String category, Double minPrice, Double maxPrice) {
        List<Product> result = new ArrayList<>();
        for (Product p : list) {
            boolean matchCat = category.equalsIgnoreCase("Tất cả") || 
                               p.getCategory().equalsIgnoreCase(category);
            boolean matchPrice = (minPrice == null || p.getPrice() >= minPrice) &&
                                 (maxPrice == null || p.getPrice() <= maxPrice);
            if (matchCat && matchPrice)
                result.add(p);
        }
        return result;
    }

    // Hiển thị danh sách sản phẩm
    public void display(List<Product> products) {
        if (products.isEmpty()) {
            System.out.println("❌ Không có sản phẩm nào để hiển thị!");
            return;
        }
        System.out.println("===== DANH SÁCH SẢN PHẨM =====");
        for (Product p : products) System.out.println(p);
    }

    public void displayAll() {
        display(list);
    }

    // ====== LƯU / ĐỌC FILE ======
    public void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (Product p : list)
                pw.println(p.toCSV());
        } catch (IOException e) {
            System.out.println("⚠️ Lỗi khi lưu file: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        list.clear();
        File f = new File(fileName);
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                Product p = Product.fromCSV(line);
                if (p != null) list.add(p);
            }
            System.out.println("📂 Đã tải " + list.size() + " sản phẩm từ " + fileName);
        } catch (IOException e) {
            System.out.println("⚠️ Lỗi khi đọc file: " + e.getMessage());
        }
    }

    // ====== CÁC HÀM PHỤ ======
    public Product findById(String id) {
        for (Product p : list)
            if (p.getId().equalsIgnoreCase(id))
                return p;
        return null;
    }

    private int readInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("⚠️ Nhập lại số nguyên: ");
            }
        }
    }

    private long readLong() {
        while (true) {
            try {
                return Long.parseLong(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("⚠️ Nhập lại số hợp lệ: ");
            }
        }
    }
}
