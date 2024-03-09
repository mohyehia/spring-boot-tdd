package com.moh.yehia.tdd.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "city_tasks")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "job_id", length = 40, nullable = false)
    private String jobId;

    @Column(name = "task_hour")
    private int hour;

    @Column(name = "task_minute")
    private int minute;

    @Column(name = "execution_days", length = 30, nullable = false)
    private String executionDays;

    @Column(name = "execution_command", nullable = false)
    private String executionCommand;

    @Column(name = "execute_until")
    private ZonedDateTime executeUntil;
}
