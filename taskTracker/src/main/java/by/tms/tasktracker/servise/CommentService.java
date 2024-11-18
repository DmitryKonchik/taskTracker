package by.tms.tasktracker.servise;

import by.tms.tasktracker.entity.Comment;
import by.tms.tasktracker.entity.Task;
import by.tms.tasktracker.entity.User;
import by.tms.tasktracker.exeption.CommentNotFoundException;
import by.tms.tasktracker.exeption.TaskNotFoundException;
import by.tms.tasktracker.exeption.UserNotFoundException;
import by.tms.tasktracker.repository.CommentRepository;
import by.tms.tasktracker.repository.TaskRepository;
import by.tms.tasktracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private TaskRepository taskRepository;

    public Comment addComment(Comment comment, Long taskId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        comment.setUser(user);
        comment.setTask(task);
        comment.setCreatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findCommentById(id).orElseThrow(() -> new CommentNotFoundException("Comment not found"));
    }

    public Comment updateComment(Comment comment) {
        Comment newComment = commentRepository.findCommentById(comment.getId()).orElseThrow(() -> new CommentNotFoundException("Comment not found"));
        newComment.setContent(comment.getContent());
        newComment.setCreatedAt(LocalDateTime.now());
        return commentRepository.save(newComment);
    }

    public List<Comment> getCommentByTaskId(Long taskId) {
        return commentRepository.findCommentsByTaskId(taskId);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

}
