package com.moh.yehia.tdd.controller;

import com.moh.yehia.tdd.model.dto.TaskDTO;
import com.moh.yehia.tdd.model.entity.Task;
import com.moh.yehia.tdd.service.design.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> findAll() {
        return new ResponseEntity<>(taskService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Task> save(@RequestBody TaskDTO taskDTO) {
        return new ResponseEntity<>(taskService.save(taskDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<Task> findByJobId(@PathVariable("jobId") String jobId) {
        return new ResponseEntity<>(taskService.findByJobId(jobId), HttpStatus.OK);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable("taskId") long taskId, @RequestBody TaskDTO taskDTO) {
        Task task = taskService.findById(taskId);
        return new ResponseEntity<>(taskService.update(taskDTO, task), HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteById(@PathVariable("taskId") long taskId) {
        taskService.deleteById(taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
