package vn.edu.hcmuaf.fit.service;

import vn.edu.hcmuaf.fit.model.Product;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {
    private static ProductService instance;
    private List<Product> products;

    private ProductService() {
        products = new ArrayList<>();
        // Mock data based on Catalog.js
        products.add(new Product(1, "Máy Xông Hơi Mặt Công Nghệ Ion Reiwa WT-300", "Reiwa",
                "https://sieuthiyte.com.vn/data/images/San-Pham/combo-reiwa-may-xong-hoi-mat-may-rua-mat-avt1.jpg",
                499000, 900000, 5, 0, "45%", true, 100));
        products.add(new Product(2, "Máy Rửa Mặt Làm Sạch Sâu 3 Trong 1 Reiwa WT-222-2", "Reiwa",
                "https://sieuthiyte.com.vn/data/product/201910/touchbeauty-tb1581-11571905034.nv.jpg", 499000, 900000,
                4, 0, "45%", true, 93));
        products.add(new Product(3, "Máy Hút Mụn Đầu Đen 2 Trong 1 Reiwa BR-150", "Reiwa",
                "https://sieuthiyte.com.vn/data/images/San-Pham/may-hut-mun-dau-den-2-trong-1-reiwa-av1.jpg", 650000,
                1100000, 5, 0, "41%", true, 86));
        products.add(new Product(4, "Cây Lăn Massage Mặt Và Body Kakusan KB-213", "Kakusan",
                "https://sieuthiyte.com.vn/data/images/San-Pham/cay-lan-massage-mat-va-body-kakusan-kb-213-av1-f1.jpg",
                590000, 1100000, 4, 0, "46%", true, 79));
        products.add(new Product(5, "Máy Xông Hơi Mặt TouchBeauty TB-1586", "TouchBeauty",
                "https://sieuthiyte.com.vn/data/product/201910/touchbeauty-tb14838-1-2-1571906196.nv.jpg", 650000,
                1160000, 3, 0, "44%", true, 72));
        products.add(new Product(6, "Máy Massage Mặt ROAMAN M7 Làm Sạch Và Trẻ Hóa", "ROAMAN",
                "https://sieuthiyte.com.vn/assets/uploads/roamanm7-101572593119.nv.png", 450000, 900000, 3, 0, "50%",
                true, 65));
        products.add(new Product(7, "Máy Cạo Râu ROAMAN RMS7109", "ROAMAN",
                "https://sieuthiyte.com.vn/data/product/202207/may-cao-rau-roaman-rms7109-av-22072022-v31658463335.nv.jpg",
                450000, 1090000, 4, 0, "59%", true, 58));
        products.add(new Product(8, "Gen Nịt Bụng Tạo Dáng Microfiber Art.605", "Microfiber",
                "https://sieuthiyte.com.vn/assets/uploads/21545706638.nv.png", 199000, 390000, 4, 0, "49%", true, 51));
    }

    public static ProductService getInstance() {
        if (instance == null) {
            instance = new ProductService();
        }
        return instance;
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public List<Product> getProducts(String[] brands, String priceRange, String sort) {
        List<Product> result = new ArrayList<>(products);

        // Filter by Brand
        if (brands != null && brands.length > 0) {
            List<String> brandList = List.of(brands);
            result = result.stream()
                    .filter(p -> brandList.contains(p.getBrand()))
                    .collect(Collectors.toList());
        }

        // Filter by Price
        if (priceRange != null) {
            switch (priceRange) {
                case "p1": // < 2tr
                    result = result.stream().filter(p -> p.getPrice() < 2000000).collect(Collectors.toList());
                    break;
                case "p2": // 2tr - 4tr
                    result = result.stream().filter(p -> p.getPrice() >= 2000000 && p.getPrice() < 4000000)
                            .collect(Collectors.toList());
                    break;
                case "p3": // 4tr - 6tr
                    result = result.stream().filter(p -> p.getPrice() >= 4000000 && p.getPrice() < 6000000)
                            .collect(Collectors.toList());
                    break;
                case "p4": // > 6tr
                    result = result.stream().filter(p -> p.getPrice() >= 6000000).collect(Collectors.toList());
                    break;
            }
        }

        // Sort
        if (sort != null) {
            switch (sort) {
                case "priceAsc":
                    result.sort(Comparator.comparingDouble(Product::getPrice));
                    break;
                case "priceDesc":
                    result.sort(Comparator.comparingDouble(Product::getPrice).reversed());
                    break;
                case "newest":
                    // Mock: assuming higher ID is newer for now
                    result.sort(Comparator.comparingInt(Product::getId).reversed());
                    break;
                case "best":
                    result.sort(Comparator.comparingInt(Product::getSold).reversed());
                    break;
            }
        }

        return result;
    }
}
