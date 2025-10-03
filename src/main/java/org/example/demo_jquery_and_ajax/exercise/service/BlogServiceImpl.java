package org.example.demo_jquery_and_ajax.exercise.service;

import jakarta.transaction.Transactional;
import org.example.demo_jquery_and_ajax.exercise.model.Blog;
import org.example.demo_jquery_and_ajax.exercise.model.Category;
import org.example.demo_jquery_and_ajax.exercise.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BlogServiceImpl implements BlogService{
    @Autowired
    private BlogRepository repo;

    public BlogServiceImpl(BlogRepository repo) {
        this.repo = repo;
    }


    @Override
    public List<Blog> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Blog> findById(long id) {
        return repo.findById(id);
    }

    @Override
    public void save(Blog blog) {
        repo.save(blog);
    }

    @Override
    public void deleteById(long id) {
        repo.deleteById(id);
    }

    @Override
    public Page<Blog> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public Page<Blog> findByTitleContaining(String title, Pageable pageable) {
        return repo.findByTitleContaining(title, pageable);
    }

    @Override
    public Page<Blog> findAllByCategory(Category category, Pageable pageable) {
        return repo.findAllByCategory(category, pageable);
    }

    @Override
    public Page<Blog> searchByTitle(String keyword, Pageable pageable) {
        return repo.findByTitleContaining(keyword, pageable);
    }

    @Override
    public Page<Blog> searchByTitleAndCategory(String keyword, Long categoryId, Pageable pageable) {
        return repo.findByTitleContainingAndCategoryId(keyword, categoryId, pageable);
    }

    @Override
    public Page<Blog> findByTitleContainingAndCategoryId(String title, Long categoryId, Pageable pageable) {
        return findByTitleContainingAndCategoryId(title, categoryId, pageable);
    }
}
