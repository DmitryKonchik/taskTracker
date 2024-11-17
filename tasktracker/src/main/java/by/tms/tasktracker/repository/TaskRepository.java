package by.tms.tasktracker.repository;

import by.tms.tasktracker.entity.Task;
import by.tms.tasktracker.entity.TaskStatus;
import by.tms.tasktracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findTaskById(Long id);
    List<Task> findTasksByAssignedTo(User assignedTo);
    List<Task> findTasksByAssignedToAndStatus(User assignedTo, TaskStatus status);
}
