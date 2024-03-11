package com.moh.yehia.tdd.mapper;

import com.moh.yehia.tdd.model.dto.TaskDTO;
import com.moh.yehia.tdd.model.entity.Task;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class TaskMapperTest {

    @SpyBean
    private TaskMapper taskMapper;

    @Test
    void givenTaskDTO_whenFromTaskDTO_thenReturnTaskEntity() {
        TaskDTO taskDTO = populateTaskDTO();
        Task task = taskMapper.fromTaskDO(taskDTO);
        Assertions.assertThat(task).isNotNull();
        Assertions.assertThat(task.getName()).isEqualTo(taskDTO.name());
        Assertions.assertThat(task.getDescription()).isEqualTo(taskDTO.description());
        Assertions.assertThat(task.getHour()).isEqualTo(taskDTO.hour());
        Assertions.assertThat(task.getMinute()).isEqualTo(taskDTO.minute());
        Assertions.assertThat(task.getExecutionDays()).isEqualTo(taskDTO.executionDays());
        Assertions.assertThat(task.getExecutionCommand()).isEqualTo(taskDTO.executionCommand());
    }

    private TaskDTO populateTaskDTO() {
        return new TaskDTO("name", "description", 12, 0, "Sat", "java -jar test");
    }
}