package com.moh.yehia.tdd.service.design;

import com.moh.yehia.tdd.model.dto.TaskDTO;
import com.moh.yehia.tdd.model.entity.Task;

import java.util.List;

public interface TaskService {
    Task save(TaskDTO taskDTO);

    Task findByJobId(String jobId);

    Task update(TaskDTO taskDTO, Task task);

    void deleteById(long taskId);

    List<Task> findAll();
}
