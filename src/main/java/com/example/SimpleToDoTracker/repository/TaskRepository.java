package com.example.SimpleToDoTracker.repository;

import com.example.SimpleToDoTracker.entety.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
