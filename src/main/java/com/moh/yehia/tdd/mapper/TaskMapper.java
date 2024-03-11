package com.moh.yehia.tdd.mapper;

import com.moh.yehia.tdd.model.dto.TaskDTO;
import com.moh.yehia.tdd.model.entity.Task;
import org.springframework.stereotype.Component;

import java.time.*;

@Component
public class TaskMapper {
    public Task fromTaskDO(TaskDTO taskDTO) {
        return Task.builder()
                .name(taskDTO.name())
                .description(taskDTO.description())
                .hour(taskDTO.hour())
                .minute(taskDTO.minute())
                .executionDays(taskDTO.executionDays())
                .executionCommand(taskDTO.executionCommand())
                .executeUntil(ZonedDateTime.of(LocalDateTime.of(LocalDate.now(), LocalTime.MAX), ZoneId.of("Africa/Cairo")))
                .build();
    }
}
