package by.tms.tasktracker.servise;

import by.tms.tasktracker.entity.Task;
import by.tms.tasktracker.entity.TaskStatus;
import by.tms.tasktracker.entity.User;
import by.tms.tasktracker.exeption.TaskNotFoundException;
import by.tms.tasktracker.exeption.UserNotFoundException;
import by.tms.tasktracker.repository.TaskRepository;
import by.tms.tasktracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionUsageException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findTaskById(id);
    }

    public List<Task> getTasksByAssignedToAndStatus(Long userId, TaskStatus status) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        return taskRepository.findTasksByAssignedToAndStatus(user, status);
    }

    public List<Task> getTasksByAssignedTo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        return taskRepository.findTasksByAssignedTo(user);
    }

    public Task createTask(Task task, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        task.setAssignedTo(user);
        task.setStatus(TaskStatus.NEW);
        return taskRepository.save(task);
    }

    public Task updateTaskDescription(Long taskId, String description) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        task.setDescription(description);
        return taskRepository.save(task);
    }

    public Task updateTaskAssignedTo(Long taskId, User assignedTo) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        task.setAssignedTo(assignedTo);
        return taskRepository.save(task);
    }

    public Task updateTaskStatus(Long taskId, TaskStatus status) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        task.setStatus(status);
        return taskRepository.save(task);
    }

    public Task toggleTimer(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        if (task.isTimerActive()) {
            task.setTimeSpent(task.getTimeSpent().plus(Duration.between(task.getLastStarted(), LocalDateTime.now())));
            task.setTimerActive(false);
        } else {
            task.setLastStarted(LocalDateTime.now());
            task.setTimerActive(true);
        }
        return taskRepository.save(task);
    }

    public void deleteTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        taskRepository.delete(task);
    }

}

