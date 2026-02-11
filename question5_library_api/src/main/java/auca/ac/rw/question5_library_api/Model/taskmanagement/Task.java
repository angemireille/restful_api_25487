package auca.ac.rw.question5_library_api.Model.taskmanagement;

public class Task {
    private Long taskId;
    private String title;
    private String description;
    private boolean completed;
    private String priority; // "LOW", "MEDIUM", "HIGH"
    private String dueDate;   // format: "YYYY-MM-DD"

    public Task() {}

    public Task(Long taskId, String title, String description, 
                boolean completed, String priority, String dueDate) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    // Getters and Setters
    public Long getTaskId() { return taskId; }
    public void setTaskId(Long taskId) { this.taskId = taskId; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    
    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
}