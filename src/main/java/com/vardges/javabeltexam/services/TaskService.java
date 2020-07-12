package com.vardges.javabeltexam.services;

import org.springframework.stereotype.Service;
import com.vardges.javabeltexam.models.Task;
import com.vardges.javabeltexam.repositories.TaskRepository;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    
    private final TaskRepository taskRepository;
    
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    // returns all the tasks
    public List<Task> allTasks() {
        return taskRepository.findAll();
    }
    // creates a task
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }
    // retrieves a task
    public Task findTask(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isPresent()) {
            return optionalTask.get();
        } 
        else {
            return null;
        }
    }
    // update a task
    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }
    
    // delete a task
    public void deleteTask(Long id) {
    	taskRepository.deleteById(id);
    }
}
