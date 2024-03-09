package com.moh.yehia.tdd.repository;

import com.moh.yehia.tdd.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByJobId(String jobId);
}
