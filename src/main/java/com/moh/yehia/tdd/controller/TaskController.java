package com.moh.yehia.tdd.controller;

import com.moh.yehia.tdd.model.dto.TaskDTO;
import com.moh.yehia.tdd.model.entity.Task;
import com.moh.yehia.tdd.service.design.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> save(@RequestBody TaskDTO taskDTO) {
        return new ResponseEntity<>(taskService.save(taskDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<Task> findByJobId(@PathVariable("jobId") String jobId) {
        return new ResponseEntity<>(taskService.findByJobId(jobId), HttpStatus.OK);
    }
}
