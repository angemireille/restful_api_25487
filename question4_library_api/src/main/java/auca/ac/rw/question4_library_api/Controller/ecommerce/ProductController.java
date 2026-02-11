package auca.ac.rw.question4_library_api.Controller.ecommerce;

import auca.ac.rw.question4_library_api.Model.ecommerce.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private List<Product> products = new ArrayList<>();
    private Long nextProductId = 1L;

    // Initialize with 10 sample products
    public ProductController() {
        products.add(new Product(nextProductId++, "iPhone 15", "Latest Apple smartphone", 999.99, "Electronics", 50, "Apple"));
        products.add(new Product(nextProductId++, "Samsung TV", "55-inch 4K Smart TV", 799.99, "Electronics", 30, "Samsung"));
        products.add(new Product(nextProductId++, "Nike Air Max", "Running shoes", 129.99, "Footwear", 100, "Nike"));
        products.add(new Product(nextProductId++, "Levi's Jeans", "Blue denim jeans", 89.99, "Clothing", 75, "Levi's"));
        products.add(new Product(nextProductId++, "Java Programming Book", "Learn Java programming", 49.99, "Books", 200, "O'Reilly"));
        products.add(new Product(nextProductId++, "Coffee Maker", "Automatic drip coffee maker", 129.99, "Home Appliances", 40, "Keurig"));
        products.add(new Product(nextProductId++, "Yoga Mat", "Non-slip exercise mat", 29.99, "Fitness", 0, "Gaiam"));
        products.add(new Product(nextProductId++, "Backpack", "Water-resistant laptop backpack", 59.99, "Accessories", 60, "SwissGear"));
        products.add(new Product(nextProductId++, "Blender", "High-speed kitchen blender", 89.99, "Home Appliances", 25, "Vitamix"));
        products.add(new Product(nextProductId++, "Watch", "Analog wrist watch", 199.99, "Accessories", 15, "Fossil"));
    }

    // GET /api/products - with pagination
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int limit) {
        
        int start = (page - 1) * limit;
        int end = Math.min(start + limit, products.size());
        
        if (start >= products.size()) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
        
        return new ResponseEntity<>(products.subList(start, end), HttpStatus.OK);
    }

    // GET /api/products/{productId}
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Optional<Product> product = products.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst();
        return product.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // GET /api/products/category/{category}
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        List<Product> result = products.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // GET /api/products/brand/{brand}
    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Product>> getProductsByBrand(@PathVariable String brand) {
        List<Product> result = products.stream()
                .filter(p -> p.getBrand().equalsIgnoreCase(brand))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // GET /api/products/search?keyword={keyword}
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        List<Product> result = products.stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                           p.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // GET /api/products/price-range?min={min}&max={max}
    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> getProductsByPriceRange(
            @RequestParam Double min,
            @RequestParam Double max) {
        List<Product> result = products.stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // GET /api/products/in-stock
    @GetMapping("/in-stock")
    public ResponseEntity<List<Product>> getInStockProducts() {
        List<Product> result = products.stream()
                .filter(p -> p.getStockQuantity() > 0)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // POST /api/products
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        product.setProductId(nextProductId++);
        products.add(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    // PUT /api/products/{productId}
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, 
                                                 @RequestBody Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p.getProductId().equals(productId)) {
                updatedProduct.setProductId(productId);
                products.set(i, updatedProduct);
                return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // PATCH /api/products/{productId}/stock?quantity={quantity}
    @PatchMapping("/{productId}/stock")
    public ResponseEntity<Product> updateStockQuantity(
            @PathVariable Long productId,
            @RequestParam int quantity) {
        for (Product p : products) {
            if (p.getProductId().equals(productId)) {
                p.setStockQuantity(quantity);
                return new ResponseEntity<>(p, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // DELETE /api/products/{productId}
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        boolean removed = products.removeIf(p -> p.getProductId().equals(productId));
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}