package com.example.pracrawling.repository;

import com.example.pracrawling.entity.Ministry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MinistryRepository extends JpaRepository<Ministry, Long> {
    Optional<Ministry> findByDepartment(String department);
}
