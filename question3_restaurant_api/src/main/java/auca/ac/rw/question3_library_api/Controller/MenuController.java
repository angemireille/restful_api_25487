package auca.ac.rw.question3_library_api.Controller;

import auca.ac.rw.question3_library_api.Model.restaurant.MenuItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private List<MenuItem> menuItems = new ArrayList<>();
    private Long nextId = 1L;

    // Initialize with 8 sample items across all categories
    public MenuController() {
        menuItems.add(new MenuItem(nextId++, "Garlic Bread", "Fresh baked bread with garlic butter", 5.99, "Appetizer", true));
        menuItems.add(new MenuItem(nextId++, "Caesar Salad", "Romaine lettuce with Caesar dressing", 8.99, "Appetizer", true));
        menuItems.add(new MenuItem(nextId++, "Grilled Salmon", "Atlantic salmon with lemon butter", 22.99, "Main Course", true));
        menuItems.add(new MenuItem(nextId++, "Beef Burger", "Angus beef with cheese and fries", 15.99, "Main Course", false));
        menuItems.add(new MenuItem(nextId++, "Chocolate Cake", "Rich chocolate layer cake", 7.99, "Dessert", true));
        menuItems.add(new MenuItem(nextId++, "Ice Cream", "Vanilla bean ice cream", 4.99, "Dessert", true));
        menuItems.add(new MenuItem(nextId++, "Coffee", "Fresh brewed coffee", 2.99, "Beverage", true));
        menuItems.add(new MenuItem(nextId++, "Orange Juice", "Freshly squeezed orange juice", 3.99, "Beverage", false));
    }

    // GET /api/menu - Get all
    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        return new ResponseEntity<>(menuItems, HttpStatus.OK);
    }

    // GET /api/menu/{id} - Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
        Optional<MenuItem> item = menuItems.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();
        return item.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // GET /api/menu/category/{category} - Get by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<MenuItem>> getMenuItemsByCategory(@PathVariable String category) {
        List<MenuItem> result = menuItems.stream()
                .filter(m -> m.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // GET /api/menu/available?available=true - Get available items
    @GetMapping("/available")
    public ResponseEntity<List<MenuItem>> getAvailableMenuItems(
            @RequestParam(defaultValue = "true") boolean available) {
        List<MenuItem> result = menuItems.stream()
                .filter(m -> m.isAvailable() == available)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // GET /api/menu/search?name={name} - Search by name
    @GetMapping("/search")
    public ResponseEntity<List<MenuItem>> searchMenuItemsByName(@RequestParam String name) {
        List<MenuItem> result = menuItems.stream()
                .filter(m -> m.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // POST /api/menu - Add new item
    @PostMapping
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody MenuItem menuItem) {
        menuItem.setId(nextId++);
        menuItems.add(menuItem);
        return new ResponseEntity<>(menuItem, HttpStatus.CREATED);
    }

    // PUT /api/menu/{id}/availability - Toggle availability
    @PutMapping("/{id}/availability")
    public ResponseEntity<MenuItem> toggleAvailability(@PathVariable Long id) {
        for (MenuItem item : menuItems) {
            if (item.getId().equals(id)) {
                item.setAvailable(!item.isAvailable());
                return new ResponseEntity<>(item, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // DELETE /api/menu/{id} - Remove item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        boolean removed = menuItems.removeIf(m -> m.getId().equals(id));
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}