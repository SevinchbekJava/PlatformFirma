package com.example.firmaplatform.Repository;

import com.example.firmaplatform.Model.Roles;
import com.example.firmaplatform.Roles.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Roles, Integer> {
    Roles findByRoleName(RoleName roleName);
}
