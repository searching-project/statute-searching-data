package com.example.lawComponents.ministry;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MinistryRepository extends JpaRepository<Ministry, Long> {
    Optional<Ministry> findByDepartment(String department);
}
