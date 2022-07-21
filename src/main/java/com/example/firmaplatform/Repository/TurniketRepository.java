package com.example.firmaplatform.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "product")
public interface TurniketRepository extends JpaRepository<com.example.firmaplatform.Model.Turniket, Integer> {
}
