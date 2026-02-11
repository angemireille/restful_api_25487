package auca.ac.rw.question2_library_api.Controller.studentRegistration;

import auca.ac.rw.question2_library_api.Model.studentRegistration.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private List<Student> students = new ArrayList<>();
    private Long nextStudentId = 1L;

    public StudentController() {
        students.add(new Student(nextStudentId++, "Ange", "Mireille", "angemireille@gmail.com", "Computer Science", 3.8));
        students.add(new Student(nextStudentId++, "Ange", "Kimberly", "angekimberly@gamil.com", "Mathematics", 3.5));
        students.add(new Student(nextStudentId++, "Christian", "Yves", "christianyves@gmail.com", "Computer Science", 3.9));
        students.add(new Student(nextStudentId++, "Nelly", "Mugisha", "nellymugisha@gmail.com", "Physics", 3.2));
        students.add(new Student(nextStudentId++, "Shema", "Cedric", "shemacedric@gamil.com", "Mathematics", 3.7));
    }

    // GET /api/students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // GET /api/students/{studentId}
    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long studentId) {
        Optional<Student> student = students.stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .findFirst();
        return student.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // GET /api/students/major/{major}
    @GetMapping("/major/{major}")
    public ResponseEntity<List<Student>> getStudentsByMajor(@PathVariable String major) {
        List<Student> result = students.stream()
                .filter(s -> s.getMajor().equalsIgnoreCase(major))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // GET /api/students/filter?gpa={minGpa}
    @GetMapping("/filter")
    public ResponseEntity<List<Student>> filterStudentsByGPA(@RequestParam Double gpa) {
        List<Student> result = students.stream()
                .filter(s -> s.getGpa() >= gpa)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // POST /api/students
    @PostMapping
    public ResponseEntity<Student> registerStudent(@RequestBody Student student) {
        student.setStudentId(nextStudentId++);
        students.add(student);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    // PUT /api/students/{studentId}
    @PutMapping("/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long studentId, 
                                                 @RequestBody Student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            if (s.getStudentId().equals(studentId)) {
                updatedStudent.setStudentId(studentId);
                students.set(i, updatedStudent);
                return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}