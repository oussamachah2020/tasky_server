package com.example.tasky_sever.repository;

import com.example.tasky_sever.model.tasks.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    Optional<List<Task>> findTaskByUserId(Integer userId);
}
