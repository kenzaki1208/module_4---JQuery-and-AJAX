package org.example.demo_jquery_and_ajax.exercise.repository;

import org.example.demo_jquery_and_ajax.exercise.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
