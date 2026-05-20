package com.sharafatdin.talgatbek.taskmanager.repository;

import com.sharafatdin.talgatbek.taskmanager.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/** @author Talgatbek Sharafatdin */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
    boolean existsByName(String name);
}
