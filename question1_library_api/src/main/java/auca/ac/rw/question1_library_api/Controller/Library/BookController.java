package auca.ac.rw.question1_library_api.Controller.Library;

import auca.ac.rw.question1_library_api.Model.Library.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private List<Book> books = new ArrayList<>();
    private Long nextId = 1L;

    // Initialize with 3 sample books
    public BookController() {
        books.add(new Book(nextId++, "Clean Code", "Robert Martin", "978-0132350884", 2008));
        books.add(new Book(nextId++, "The Pragmatic Programmer", "Andrew Hunt", "978-0201616224", 1999));
        books.add(new Book(nextId++, "Design Patterns", "Gang of Four", "978-0201633610", 1994));
    }

    // GET /api/books - Return all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // GET /api/books/{id} - Return specific book
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = books.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();
        return book.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // GET /api/books/search?title={title} - Search books
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooksByTitle(@RequestParam String title) {
        List<Book> result = books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // POST /api/books - Add new book
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        book.setId(nextId++);
        books.add(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    // DELETE /api/books/{id} - Delete book
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean removed = books.removeIf(b -> b.getId().equals(id));
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}