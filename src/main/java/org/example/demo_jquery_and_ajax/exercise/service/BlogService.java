package org.example.demo_jquery_and_ajax.exercise.service;

import org.example.demo_jquery_and_ajax.exercise.model.Blog;
import org.example.demo_jquery_and_ajax.exercise.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BlogService {
    List<Blog> findAll();
    Optional<Blog> findById(long id);
    void save(Blog blog);
    void deleteById(long id);
    Page<Blog> findAll(Pageable pageable);
    Page<Blog> findByTitleContaining(String title, Pageable pageable);
    Page<Blog> findAllByCategory(Category category, Pageable pageable);
    Page<Blog> searchByTitle(String keyword, Pageable pageable);
    Page<Blog> searchByTitleAndCategory(String keyword, Long categoryId, Pageable pageable);
    Page<Blog> findByTitleContainingAndCategoryId(String title, Long categoryId, Pageable pageable);
}
