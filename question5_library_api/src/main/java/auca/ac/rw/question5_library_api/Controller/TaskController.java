package auca.ac.rw.question5_library_api.Controller;

import auca.ac.rw.question5_library_api.Model.taskmanagement.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private List<Task> tasks = new ArrayList<>();
    private Long nextTaskId = 1L;

    public TaskController() {
        tasks.add(new Task(nextTaskId++, "Complete project", "Finish REST API assignment", false, "HIGH", "2026-02-15"));
        tasks.add(new Task(nextTaskId++, "Buy groceries", "Milk, eggs, bread", true, "MEDIUM", "2026-02-11"));
        tasks.add(new Task(nextTaskId++, "Call mom", "Weekly catch-up call", false, "LOW", "2026-02-12"));
        tasks.add(new Task(nextTaskId++, "Gym workout", "Cardio and strength training", true, "MEDIUM", "2026-02-10"));
        tasks.add(new Task(nextTaskId++, "Read book", "Finish Chapter 5", false, "LOW", "2026-02-20"));
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        Optional<Task> task = tasks.stream()
                .filter(t -> t.getTaskId().equals(taskId))
                .findFirst();
        return task.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/status")
    public ResponseEntity<List<Task>> getTasksByStatus(@RequestParam boolean completed) {
        List<Task> result = tasks.stream()
                .filter(t -> t.isCompleted() == completed)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Task>> getTasksByPriority(@PathVariable String priority) {
        List<Task> result = tasks.stream()
                .filter(t -> t.getPriority().equalsIgnoreCase(priority))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        task.setTaskId(nextTaskId++);
        task.setCompleted(false);
        tasks.add(task);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody Task updatedTask) {
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            if (t.getTaskId().equals(taskId)) {
                updatedTask.setTaskId(taskId);
                tasks.set(i, updatedTask);
                return new ResponseEntity<>(updatedTask, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<Task> markTaskAsCompleted(@PathVariable Long taskId) {
        for (Task t : tasks) {
            if (t.getTaskId().equals(taskId)) {
                t.setCompleted(true);
                return new ResponseEntity<>(t, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        boolean removed = tasks.removeIf(t -> t.getTaskId().equals(taskId));
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}