package com.moh.yehia.tdd.repository;

import com.moh.yehia.tdd.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByJobId(String jobId);
}
