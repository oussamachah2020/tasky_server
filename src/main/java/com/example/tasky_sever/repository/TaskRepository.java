package com.example.tasky_sever.repository;

import com.example.tasky_sever.model.tasks.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Tasks, Integer> {
}
