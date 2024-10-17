package com.example.tasky_sever.service.task;

import com.example.tasky_sever.model.auth.User;
import com.example.tasky_sever.model.tasks.Task;
import com.example.tasky_sever.repository.TaskRepository;
import com.example.tasky_sever.repository.UserRepository;
import com.example.tasky_sever.service.auth.UserDto;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository repository;
    private final UserRepository userRepository;

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


}
