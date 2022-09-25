package com.example.pracrawling.repository;

import com.example.pracrawling.entity.LawMinistry;
import com.example.pracrawling.entity.LawMinistryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LawMinistryRepository extends JpaRepository<LawMinistry, LawMinistryId> {
}
