package com.example.firmaplatform.Repository;


import com.example.firmaplatform.Model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepository extends JpaRepository<Salary, Integer> {
}
