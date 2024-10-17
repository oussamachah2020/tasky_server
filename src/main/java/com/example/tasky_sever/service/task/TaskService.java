package com.example.tasky_sever.service.task;

import com.example.tasky_sever.model.tasks.Task;
import com.example.tasky_sever.model.tasks.TaskDto;
import com.example.tasky_sever.repository.TaskRepository;
import com.example.tasky_sever.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository repository;
    private final UserRepository userRepository;

    public static class TaskNotFoundException extends RuntimeException {
        public TaskNotFoundException(String message) {
            super(message);
        }
    }


    public TaskService(TaskRepository repository, UserRepository userRepository1) {
        this.repository = repository;
        this.userRepository = userRepository1;
    }

    public Task createTask(Task req) {
        if (req == null) {
            throw new IllegalArgumentException("Task request cannot be null");
        }

        if (req.getTitle() == null || req.getStart_at() == null || req.getDue_at() == null) {
            throw new IllegalArgumentException("Title, Start date, and Due date are required fields");
        }

        Task task = new Task();
        task.setTitle(req.getTitle());
        task.setStart_at(req.getStart_at());
        task.setDue_at(req.getDue_at());
        task.setPriority(req.getPriority());
        task.setStatus(req.getStatus());
        task.setTag(req.getTag());
        task.setUserId(req.getUserId());

        return repository.save(task);
    }



    public Optional<List<Task>> getUserTasks(Integer userId) {
        if(userId != null) {
            return repository.findTaskByUserId(userId);
        }
        return Optional.empty();
    }

    public Object getTaskDetails(Integer taskId) throws BadRequestException {
        if(taskId == null) {
            throw new BadRequestException("Task id required");
        }

        return repository.findById(taskId);
    }

    public void updateTask(TaskDto req, Integer taskId) {
        if (req == null || taskId == null) {
            throw new IllegalArgumentException("Task data and taskId cannot be null");
        }

        // Fetch the task from the repository by its ID
        Task existingTask = repository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + taskId));

        // Update the fields with the new values
        existingTask.setTitle(req.getTitle());
        existingTask.setStart_at(req.getStart_at());
        existingTask.setDue_at(req.getDue_at());
        existingTask.setPriority(req.getPriority());
        existingTask.setStatus(req.getStatus());
        existingTask.setTag(req.getTag());

        repository.save(existingTask);
    }

    public void removeTask(Integer taskId) {
        if (taskId == null) {
            throw new IllegalArgumentException("Task id is required");
        }

        // Fetch the task, and throw TaskNotFoundException if it doesn't exist
        Task existingTask = repository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));

        // Proceed with deletion if the task exists
        repository.deleteById(taskId);
    }


}
