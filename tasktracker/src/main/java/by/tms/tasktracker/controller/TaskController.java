package by.tms.tasktracker.controller;

import by.tms.tasktracker.entity.Task;
import by.tms.tasktracker.entity.TaskStatus;
import by.tms.tasktracker.servise.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.of(Optional.ofNullable(taskService.getAllTasks()));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        return ResponseEntity.of(Optional.ofNullable(taskService.getTaskById(taskId)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getTasksByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getTasksByAssignedTo(userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Task>> getUserTasksByStatus(@PathVariable Long userId, @RequestParam TaskStatus status) {
        return ResponseEntity.ok(taskService.getTasksByAssignedToAndStatus(userId, status));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Task> createTask(@RequestBody Task task, @PathVariable Long userId) {
        return ResponseEntity.ok(taskService.createTask(task, userId));
    }

    @PutMapping("/{taskId}/status")
    public ResponseEntity<Task> updateTaskStatus(@PathVariable Long taskId, @RequestParam TaskStatus status) {
        return ResponseEntity.ok(taskService.updateTaskStatus(taskId, status));
    }

    @PutMapping("/{taskId}/timer")
    public ResponseEntity<Task> toggleTimer(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.toggleTimer(taskId));
    }

    @PatchMapping("/description")
    public ResponseEntity<Task> updateTaskDescription(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.updateTaskDescription(task.getId(), task.getDescription()));
    }

    @PatchMapping("/assigned")
    public ResponseEntity<Task> updateTaskAssignedTo(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.updateTaskAssignedTo(task.getId(), task.getAssignedTo()));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTaskById(taskId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

