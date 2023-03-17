package com.example.SimpleToDoTracker.controller;
import com.example.SimpleToDoTracker.entety.Task;
import com.example.SimpleToDoTracker.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public String getAllTasks(Model model) {
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "index";
    }

    @GetMapping("/addTask")
    public String newTaskForm(Model model) {

        model.addAttribute("task", new Task());
        return "addTask";
    }

    @PostMapping("/task")
    public String newTaskForm(@ModelAttribute("task") Task task) {
        taskService.save(task);
        return "redirect:/";
    }

    @PostMapping("/task/delete")
    public String deleteTasks(@RequestParam("ids") List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return "redirect:/";
        }
        List<Task> tasks = ids.stream().map(taskService::getById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        taskService.deleteAll(tasks);
        return "redirect:/";
    }

    @GetMapping("/editTask/{id}")
    public String showEditTaskForm(@PathVariable("id") Long id, Model model) {
        Optional<Task> task = taskService.getById(id);
        if (task.isPresent()) {
            model.addAttribute("task", task.get());
            return "editTask";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/task/edit")
    public String editTask(@ModelAttribute("task") Task task) {
        taskService.save(task);
        return "redirect:/";
    }


}

//    private final TaskService taskService;
//
//    @Autowired
//    public TaskController(TaskService taskService) {
//        this.taskService = taskService;
//    }
//
//    @GetMapping
//    public String getAllTasks(Model model) {
//        List<Task> tasks = taskService.getAllTasks();
//        model.addAttribute("tasks", tasks);
//        return "index";
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Task> getById(@PathVariable Long id) {
//        Optional<Task> task = taskService.getById(id);
//        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @PostMapping
//    public Task createTask(@RequestBody Task task) {
//        return taskService.save(task);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
//        Optional<Task> existingTask = taskService.getById(id);
//
//        if (!existingTask.isPresent()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        task.setId(id);
//        return ResponseEntity.ok(taskService.save(task));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Task> deleteTask(@PathVariable Long id) {
//        Optional<Task> existingTask = taskService.getById(id);
//
//        if (!existingTask.isPresent()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        taskService.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }
//}


