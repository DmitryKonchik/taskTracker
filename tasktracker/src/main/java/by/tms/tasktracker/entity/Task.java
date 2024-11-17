package by.tms.tasktracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ManyToOne
    private User assignedTo;

    @ManyToMany
    private List<User> watchers;

    @OneToMany(mappedBy = "task" , cascade = CascadeType.ALL)
    private List<Comment> comments;

    private Duration timeSpent = Duration.ZERO;

    private boolean timerActive = false;

    private LocalDateTime lastStarted;
}
