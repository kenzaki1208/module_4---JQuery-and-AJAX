package org.example.demo_jquery_and_ajax.exercise.service;

import org.example.demo_jquery_and_ajax.exercise.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();
    Optional<Category> findById(long id);
    Category save(Category category);
    void deleteById(long id);
}
