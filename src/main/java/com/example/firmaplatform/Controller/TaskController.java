package com.example.firmaplatform.Controller;

import com.example.firmaplatform.DTO.ApiResponse;
import com.example.firmaplatform.DTO.TaskDTO;
import com.example.firmaplatform.Repository.TaskRepository;
import com.example.firmaplatform.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class TaskController {
    @Autowired
    TaskService taskService;
    @Autowired
    TaskRepository taskRepository;

    @PostMapping("/createTask")
    public HttpEntity<?> createTask(@RequestBody TaskDTO taskDTO){
        ApiResponse apiResponse = taskService.createTask(taskDTO);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }
    @GetMapping("/getTask/{id}")
    public HttpEntity<?> readTask(@PathVariable Integer id){
        ApiResponse apiResponse=taskService.readTask(id);
        return ResponseEntity.status(apiResponse.getType()?201:409).body(apiResponse.getMessage());
    }
    @GetMapping("/getAllTask")
    public HttpEntity<?> readAllTask(){
        return ResponseEntity.ok(taskRepository.findAll());
    }
}
