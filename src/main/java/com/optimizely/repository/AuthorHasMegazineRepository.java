package com.optimizely.repository;

import com.optimizely.model.AuthorHasMegazine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorHasMegazineRepository extends JpaRepository<AuthorHasMegazine, Long> {
//    Book findByMegazineId(Integer authorId);
}
