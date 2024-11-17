package by.tms.tasktracker.controller;

import by.tms.tasktracker.entity.Comment;
import by.tms.tasktracker.servise.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public List<Comment> getComments() {
        return commentService.getComments();
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<Comment>> getCommentsByTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(commentService.getCommentByTaskId(taskId));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getComment(@PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.getCommentById(commentId));
    }

    @PostMapping("/{taskId}/{userId}")
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment, @PathVariable Long taskId, @PathVariable Long userId) {
        return ResponseEntity.ok(commentService.addComment(comment, taskId, userId));
    }

    @PutMapping
    public ResponseEntity<Comment> updateComment(@RequestBody Comment comment) {
        return ResponseEntity.ok(commentService.updateComment(comment));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}
