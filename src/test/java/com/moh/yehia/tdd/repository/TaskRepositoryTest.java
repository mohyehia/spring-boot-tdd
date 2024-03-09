package com.moh.yehia.tdd.repository;

import com.moh.yehia.tdd.model.entity.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;

    @Test
    @DisplayName("Create new task")
    void givenTaskObject_whenSave_thenReturnSavedTask() {
        Task savedTask = taskRepository.save(populateTask());
        assertThat(savedTask.getId()).isPositive();
    }

    @Test
    @DisplayName("Find task by jobId")
    void givenJobId_whenFindByJobId_thenReturnTaskEntity() {
        Task savedTask = taskRepository.save(populateTask());
        Task retrievedTask = taskRepository.findByJobId(savedTask.getJobId()).orElse(null);
        assertThat(retrievedTask).isNotNull();
        assertThat(retrievedTask.getId()).isPositive()
                .isEqualTo(savedTask.getId());
    }

    @Test
    @DisplayName("Update task")
    void givenTaskObject_whenUpdate_thenReturnUpdatedTask() {
        Task savedTask = taskRepository.save(populateTask());
        savedTask.setName("Updated task");
        savedTask.setExecutionDays("Fri");
        savedTask = taskRepository.save(savedTask);
        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getName()).isEqualTo("Updated task");
        assertThat(savedTask.getExecutionDays()).isEqualTo("Fri");
    }

    @Test
    @DisplayName("Delete task by taskId")
    void givenTaskId_whenDeleteById_thenDeleteTaskEntity() {
        Task savedTask = taskRepository.save(populateTask());
        taskRepository.deleteById(savedTask.getId());
        Optional<Task> taskOptional = taskRepository.findById(savedTask.getId());
        assertThat(taskOptional).isEmpty();
    }

    private Task populateTask() {
        return Task.builder()
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