package com.example.firmaplatform.Repository;

import com.example.firmaplatform.Model.Roles;
import com.example.firmaplatform.Model.Staff;
import com.example.firmaplatform.Roles.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.Email;
import java.util.List;
import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Integer> {
    Optional<Staff> findByEmailAndEmailCode(@Email String email, String emailCode);
    Optional<Staff> findByEmail(@Email String email);
}
