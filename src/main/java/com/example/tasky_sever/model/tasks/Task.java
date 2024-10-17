package com.example.tasky_sever.model.tasks;

import com.example.tasky_sever.model.auth.User;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;

@Entity
@Table(name = "tasks")
@Data
public class Task implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @Column(name = "title")
    String title;

    @Column(name = "start_at")
    String start_at;

    @Column(name = "due_at")
    String due_at;

    @Column(name = "tag")
    String tag;

    public String getStart_at() {
        return start_at;
    }

    public void setStart_at(String start_at) {
        this.start_at = start_at;
    }

    public String getDue_at() {
        return due_at;
    }

    public void setDue_at(String due_at) {
        this.due_at = due_at;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Enumerated(value = EnumType.STRING)
    Priority priority;

    @Enumerated(value = EnumType.STRING)
    Status status;

    @Column(name = "user_id")
    private Integer userId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author", referencedColumnName = "id")
    private User user;

    public Integer getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", start_at='" + start_at + '\'' +
                ", due_at='" + due_at + '\'' +
                ", priority=" + priority +
                ", status=" + status +
                '}';
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
