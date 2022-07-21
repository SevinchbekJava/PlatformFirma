package com.example.firmaplatform.Repository;

import com.example.firmaplatform.Model.Staff;
import com.example.firmaplatform.Model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Tasks, Integer> {
    Optional<Tasks> findByStaff(Staff staff);
}
