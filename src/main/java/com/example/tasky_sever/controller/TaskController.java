package com.example.tasky_sever.controller;

import com.example.tasky_sever.model.tasks.Task;
import com.example.tasky_sever.service.task.TaskService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {
    private final TaskService taskService;


    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public ResponseEntity<Task> createNewTask(@RequestBody Task task) {
        Task newTask = taskService.createTask(task);
        return ResponseEntity.ok(newTask);
    }

    @GetMapping("/all/{userId}")
    public HttpEntity<Optional<List<Task>>> getUserTasks(@PathVariable Integer userId) {
        Optional<List<Task>> tasks = taskService.getUserTasks(userId);
       
        return ResponseEntity.ok(tasks);
    }
}
