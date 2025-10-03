package org.example.demo_jquery_and_ajax.exercise.repository;

import org.example.demo_jquery_and_ajax.exercise.model.Blog;
import org.example.demo_jquery_and_ajax.exercise.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    Page<Blog> findAll(Pageable pageable);
    Page<Blog> findByTitleContaining(String title, Pageable pageable);
    Page<Blog> findAllByCategory(Category category, Pageable pageable);
    Page<Blog> findByTitleContainingAndCategoryId(String title, Long categoryId, Pageable pageable);
}
