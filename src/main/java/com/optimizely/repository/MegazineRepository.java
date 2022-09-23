package com.optimizely.repository;

import com.optimizely.model.Megazine;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MegazineRepository extends JpaRepository<Megazine, Long> {
//    List<Megazine> findByAuthor(String author);

    Megazine findByIsbn(String isbn);
}

