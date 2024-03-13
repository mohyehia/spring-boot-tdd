package com.moh.yehia.tdd.service.impl;

import com.moh.yehia.tdd.mapper.TaskMapper;
import com.moh.yehia.tdd.model.dto.TaskDTO;
import com.moh.yehia.tdd.model.entity.Task;
import com.moh.yehia.tdd.repository.TaskRepository;
import com.moh.yehia.tdd.service.design.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public Task save(TaskDTO taskDTO) {
        Task task = taskMapper.fromTaskDO(taskDTO);
        return taskRepository.save(task);
    }

    @Override
    public Task findByJobId(String jobId) {
        return taskRepository.findByJobId(jobId)
                .orElseThrow(() -> new IllegalStateException("No Task with this jobId found!"));
    }

    @Override
    public Task update(TaskDTO taskDTO, Task task) {
        if (taskDTO.name() != null && !taskDTO.name().isEmpty()) {
            task.setName(taskDTO.name());
        }
        if (taskDTO.description() != null && !taskDTO.description().isEmpty()) {
            task.setDescription(taskDTO.description());
        }
        if (taskDTO.hour() > 0) {
            task.setHour(taskDTO.hour());
        }
        if (taskDTO.minute() > 0) {
            task.setMinute(taskDTO.minute());
        }
        return taskRepository.save(task);
    }

    @Override
    public void deleteById(long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task findById(long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalStateException("No Task with this id found!"));
    }
}
