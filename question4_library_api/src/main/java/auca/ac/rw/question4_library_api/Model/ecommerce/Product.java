package auca.ac.rw.question4_library_api.Model.ecommerce;

public class Product {
    private Long productId;
    private String name;
    private String description;
    private Double price;
    private String category;
    private int stockQuantity;
    private String brand;

    public Product() {}

    public Product(Long productId, String name, String description, Double price, 
                   String category, int stockQuantity, String brand) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity;
        this.brand = brand;
    }

    // Getters and Setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
}