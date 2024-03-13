package com.moh.yehia.tdd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moh.yehia.tdd.model.dto.TaskDTO;
import com.moh.yehia.tdd.model.entity.Task;
import com.moh.yehia.tdd.service.design.TaskService;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

@WebMvcTest(controllers = TaskController.class)
@AutoConfigureMockMvc
class TaskControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenTasksList_whenFindAll_thenReturnAllTasks() throws Exception {
        List<Task> expectedTasks = populateTasks();
        BDDMockito.given(taskService.findAll()).willReturn(expectedTasks);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tasks")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2))).andDo(MockMvcResultHandlers.print()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Assertions.assertThat(contentAsString)
                .isNotNull()
                .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(expectedTasks));
    }

    @Test
    void givenJobId_whenFindByJobId_thenReturnTaskEntity() throws Exception {
        Task expectedTask = populateTask();
        BDDMockito.given(taskService.findByJobId(ArgumentMatchers.anyString())).willReturn(expectedTask);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tasks/{jobId}", "123456798")).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Assertions.assertThat(contentAsString)
                .isNotNull()
                .isEqualTo(objectMapper.writeValueAsString(expectedTask));
    }

    @Test
    void givenTaskDTO_whenSaveTask_thenReturnSavedTaskEntity() throws Exception {
        TaskDTO taskDTO = populateTaskDTO();
        Task task = Task.builder().id(1).name(taskDTO.name()).description(taskDTO.description()).hour(taskDTO.hour()).minute(taskDTO.minute()).executionDays(taskDTO.executionDays()).executionCommand(taskDTO.executionCommand()).build();
        BDDMockito.given(taskService.save(taskDTO)).willReturn(task);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/tasks").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Assertions.assertThat(contentAsString)
                .isNotNull()
                .isEqualTo(objectMapper.writeValueAsString(task));
    }

    @Test
    void givenModifiedTask_whenUpdateTask_thenReturnUpdatedTaskEntity() throws Exception {
        TaskDTO taskDTO = populateTaskDTO();
        Task task = populateTask();
        Task updatedTask = Task.builder().id(1).name(taskDTO.name()).description(taskDTO.description()).hour(taskDTO.hour()).minute(taskDTO.minute()).executionDays(taskDTO.executionDays()).executionCommand(taskDTO.executionCommand()).build();
        BDDMockito.given(taskService.findById(ArgumentMatchers.anyLong())).willReturn(task);
        BDDMockito.given(taskService.update(taskDTO, task)).willReturn(updatedTask);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/tasks/{taskId}", 1).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Assertions.assertThat(contentAsString)
                .isNotNull()
                .isEqualTo(objectMapper.writeValueAsString(updatedTask));
    }

    @Test
    void givenTaskId_whenDeleteById_thenDeleteTask() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/tasks/{taskId}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        Mockito.verify(taskService, Mockito.times(1)).deleteById(1L);
    }

    private List<Task> populateTasks() {
        return List.of(Task.builder().id(1).name("first task").hour(12).build(), Task.builder().id(2).name("second task").hour(10).build());
    }

    private Task populateTask() {
        return Task.builder().id(1).name("first task").hour(12).jobId(UUID.randomUUID().toString()).build();
    }

    private TaskDTO populateTaskDTO() {
        return new TaskDTO("test task", "test task description", 12, 10, "Fri", "java -jar task");
    }
}
