package com.moh.yehia.tdd.service;

import com.moh.yehia.tdd.mapper.TaskMapper;
import com.moh.yehia.tdd.model.dto.TaskDTO;
import com.moh.yehia.tdd.model.entity.Task;
import com.moh.yehia.tdd.repository.TaskRepository;
import com.moh.yehia.tdd.service.impl.TaskServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void givenTaskDTO_whenSave_thenReturnSavedTaskEntity() {
        Task expectedTask = populateTask();
        BDDMockito.given(taskMapper.fromTaskDO(ArgumentMatchers.any(TaskDTO.class))).willReturn(expectedTask);
        BDDMockito.given(taskRepository.save(ArgumentMatchers.any(Task.class))).willReturn(expectedTask);
        TaskDTO taskDTO = populateTaskDTO();
        Task savedTask = taskService.save(taskDTO);
        Assertions.assertThat(savedTask).isNotNull();
        Assertions.assertThat(savedTask.getId()).isPositive();
        Assertions.assertThat(savedTask.getName()).isEqualTo(expectedTask.getName());
        Assertions.assertThat(savedTask.getDescription()).isEqualTo(expectedTask.getDescription());
        Assertions.assertThat(savedTask.getHour()).isEqualTo(expectedTask.getHour());
    }

    @Test
    void whenFindAllTasks_thenReturnAllTasks() {
        Task task = populateTask();
        BDDMockito.given(taskRepository.findAll()).willReturn(Collections.singletonList(task));
        List<Task> tasks = taskService.findAll();
        Assertions.assertThat(tasks).isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .contains(task);
    }

    @Test
    void givenJobId_whenFindByJobId_thenReturnTaskEntity() {
        Task task = populateTask();
        BDDMockito.given(taskRepository.findByJobId(ArgumentMatchers.anyString())).willReturn(Optional.of(task));
        Task retrievedTask = taskService.findByJobId("1234");
        Assertions.assertThat(retrievedTask).isNotNull();
        Assertions.assertThat(retrievedTask.getId()).isPositive()
                .isEqualTo(task.getId());
    }

    @Test
    void givenNotFoundJobId_whenFindByJobId_thenThrowException() {
        BDDMockito.given(taskRepository.findByJobId(ArgumentMatchers.anyString())).willReturn(Optional.empty());
        org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class, () -> taskService.findByJobId("1234"));
    }

    @Test
    void givenTaskObject_whenUpdateTask_thenReturnUpdatedTask() {
        TaskDTO taskDTO = populateTaskDTO();
        Task task = populateTask();
        BDDMockito.given(taskRepository.save(ArgumentMatchers.any(Task.class))).willReturn(task);
        Task updatedTask = taskService.update(taskDTO, task);
        Assertions.assertThat(updatedTask).isNotNull();
        Assertions.assertThat(updatedTask.getName()).isEqualTo(taskDTO.name());
        Assertions.assertThat(updatedTask.getDescription()).isEqualTo(taskDTO.description());
        Assertions.assertThat(updatedTask.getHour()).isEqualTo(taskDTO.hour());
        Assertions.assertThat(updatedTask.getMinute()).isEqualTo(taskDTO.minute());
    }

    @Test
    void givenTaskId_whenDeleteById_thenDeleteTaskEntity() {
        Mockito.doNothing().when(taskRepository).deleteById(ArgumentMatchers.anyLong());
        taskService.deleteById(1);
        Mockito.verify(taskRepository, Mockito.times(1)).deleteById(1L);
    }

    private TaskDTO populateTaskDTO() {
        return new TaskDTO("name", "description", 12, 10, "Sat", "java -jar test");
    }

    private Task populateTask() {
        return Task.builder()
                .id(1)
                .jobId(UUID.randomUUID().toString())
                .name("Test Task")
                .description("Task description!")
                .hour(12)
                .minute(0)
                .executionDays("MON,WED,SUN")
                .executionCommand("java -jar test.java")
                .build();
    }
}