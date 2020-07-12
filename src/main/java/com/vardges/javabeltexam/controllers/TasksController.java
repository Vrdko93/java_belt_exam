package com.vardges.javabeltexam.controllers;

import org.springframework.stereotype.Controller;
import com.vardges.javabeltexam.services.TaskService;
import com.vardges.javabeltexam.services.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import java.util.List;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import com.vardges.javabeltexam.models.Task;
import org.springframework.web.bind.annotation.PathVariable;
import com.vardges.javabeltexam.models.User;
import javax.servlet.http.HttpSession;

@Controller
public class TasksController {
    private final TaskService taskService;
    private final UserService userService;
    
    public TasksController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }
    
    // Show all tasks
    @RequestMapping("/tasks")
    public String index(Model model, HttpSession session) {
        List<Task> tasks = taskService.allTasks();
        Long user_id = (Long) session.getAttribute("userId");
        User u = userService.findUserById(user_id);
        model.addAttribute("tasks", tasks);
        model.addAttribute("userName", u.getName());
        return "homePage.jsp";
    }
    
    // Show create page
    @RequestMapping("/tasks/new")
    public String newTask(@ModelAttribute("task") Task task, Model model, HttpSession session) {
    	Long user_id = (Long) session.getAttribute("userId");
        User u = userService.findUserById(user_id);
    	List<User> users = userService.allUsers();
    	session.setAttribute("users", users);
    	model.addAttribute("currentUserName", u.getName());
        return "newTask.jsp";
    }
    
    // Process task creation
    @RequestMapping(value="/tasks", method=RequestMethod.POST)
    public String create(@Valid @ModelAttribute("task") Task task, BindingResult result) {
        if (result.hasErrors()) {
            return "newTask.jsp";
        } else {
        	taskService.createTask(task);
            return "redirect:/tasks";
        }
    }
    
    // Show a task
    @RequestMapping("tasks/{id}")
	public String show(@PathVariable("id") Long id, Model model) {
		model.addAttribute("task", taskService.findTask(id));
		return "showTask.jsp";
	}
    
    // Show edit page
    @RequestMapping("/tasks/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model, HttpSession session) {
    	Long user_id = (Long) session.getAttribute("userId");
        User u = userService.findUserById(user_id);
        Task task = taskService.findTask(id);
        List<User> users = userService.allUsers();
        
        model.addAttribute("task", task);
    	session.setAttribute("users", users);
        model.addAttribute("userName", u.getName());
        return "editTask.jsp";
    }
    
    // Process task update
    @RequestMapping(value="/tasks/{id}", method=RequestMethod.PUT)
    public String update(@Valid @ModelAttribute("task") Task task, BindingResult result) {
        if (result.hasErrors()) {
            return "editTask.jsp";
        } else {
        	taskService.updateTask(task);
            return "redirect:/tasks";
        }
    }
    
    // Delete a task
    @RequestMapping(value="/tasks/{id}", method=RequestMethod.DELETE)
    public String destroy(@PathVariable("id") Long id) {
    	taskService.deleteTask(id);
        return "redirect:/tasks";
    }
}
