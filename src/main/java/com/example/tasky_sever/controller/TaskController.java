package com.example.tasky_sever.controller;

import com.example.tasky_sever.model.tasks.Task;
import com.example.tasky_sever.model.tasks.TaskDto;
import com.example.tasky_sever.service.task.TaskService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    public static class TaskNotFoundException extends RuntimeException {
        public TaskNotFoundException(String message) {
            super(message);
        }
    }

    public static class InvalidTaskDataException extends RuntimeException {
        public InvalidTaskDataException(String message) {
            super(message);
        }
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

    @PutMapping("/edit")
    public ResponseEntity<?> updateTask(@RequestBody TaskDto newTaskData, @RequestParam(name = "taskId") Integer taskId) {
        try {
            // Check for null or invalid task data
            if (newTaskData == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Task data cannot be null");
            }

            // Attempt to update the task
            taskService.updateTask(newTaskData, taskId);

            // Return success message if task is updated
            Map<String, String> response = new HashMap<>();
            response.put("message", "Task updated successfully");

            return ResponseEntity.ok(response);
        } catch (TaskNotFoundException e) {
            // Handle the case where the task doesn't exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found with ID: " + taskId);
        } catch (InvalidTaskDataException e) {
            // Handle invalid task data
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid task data provided");
        } catch (Exception e) {
            // Handle any other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/remove/{taskId}")
    public ResponseEntity<Map<String, String>> deleteTask(@PathVariable Integer taskId) {
        taskService.removeTask(taskId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Task deleted successfully");

        return ResponseEntity.ok(response);
    }
}
