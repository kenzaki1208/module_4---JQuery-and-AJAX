package org.example.demo_jquery_and_ajax.exercise.controller;

import org.example.demo_jquery_and_ajax.exercise.model.Blog;
import org.example.demo_jquery_and_ajax.exercise.model.Category;
import org.example.demo_jquery_and_ajax.exercise.service.BlogService;
import org.example.demo_jquery_and_ajax.exercise.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/exercise/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @ModelAttribute("categories")
    public List<Category> categories() {
        return categoryService.findAll();
    }

//    @GetMapping("")
//    public String list(Model model) {
//        model.addAttribute("blogs", blogService.findAll());
//        return "exercise/list";
//    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("blog", new Blog());
        model.addAttribute("categories", categoryService.findAll());
        return "exercise/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Blog blog, RedirectAttributes redirect) {
        blog.setCreatedAt(LocalDateTime.now());
        blogService.save(blog);
        redirect.addFlashAttribute("message", "Saved successfully");
        return "redirect:/exercise/blogs";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable("id") long id, Model model, RedirectAttributes redirect) {
        return blogService.findById(id).map(b -> {
            model.addAttribute("blog", b);
            return "exercise/view";
        }).orElseGet(() -> {
            redirect.addFlashAttribute("error", "Blog not found");
            return "redirect:/exercise/blogs";
        });
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") long id, Model model, RedirectAttributes redirect) {
        return blogService.findById(id).map(b -> {
            model.addAttribute("blog", b);
            return "exercise/edit";
        }).orElseGet(() -> {
            redirect.addFlashAttribute("error", "Blog not found");
            return "redirect:/exercise/blogs";
        });
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, @ModelAttribute Blog blog, RedirectAttributes redirect) {
        blogService.findById(id).ifPresent(exist -> {
            exist.setTitle(blog.getTitle());
            exist.setAuthor(blog.getAuthor());
            exist.setContent(blog.getContent());
            exist.setUpdatedAt(LocalDateTime.now());
            blogService.save(exist);
        });
        redirect.addFlashAttribute("message", "Updated successfully");
        return "redirect:/exercise/blogs";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") long id, RedirectAttributes redirect) {
        blogService.deleteById(id);
        redirect.addFlashAttribute("message", "Deleted successfully");
        return "redirect:/exercise/blogs";
    }

    //page
    @GetMapping("")
    public String list(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("createdAt").descending());
        Page<Blog> blogs = blogService.findAll(pageable);
        model.addAttribute("blogs", blogs);
        return "exercise/list";
    }

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, @RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("createdAt").descending());
        Page<Blog> blogs = blogService.findByTitleContaining(keyword, pageable);
        model.addAttribute("blogs", blogs);
        model.addAttribute("keyword", keyword);
        return "exercise/list";
    }

    @GetMapping("/category/{id}")
    public String blogsByCategory(@PathVariable("id") Long id, @RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Category category = categoryService.findById(id).orElseThrow();
        Pageable pageable = PageRequest.of(page, 5, Sort.by("createdAt").descending());
        Page<Blog> blogs = blogService.findAllByCategory(category, pageable);
        model.addAttribute("blogs", blogs);
        model.addAttribute("selectedCategory", category);
        return "exercise/list";
    }

    // DTO gọn cho phần list item (có thể tách file riêng nếu muốn)
    static final class BlogItemDto {
        public Long id;
        public String title;
        public String author;
        public String createdAt;
        public String categoryName;

        BlogItemDto(Long id, String title, String author, String createdAt, String categoryName) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.createdAt = createdAt;
            this.categoryName = categoryName;
        }
    }

    // GET /exercise/blogs/search.json?keyword=&page=0&size=5
    @GetMapping(value = "/search.json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> searchJson(
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Blog> blogs = blogService.findByTitleContaining(keyword, pageable);

        List<BlogItemDto> items = blogs.getContent().stream()
                .map(b -> new BlogItemDto(
                        b.getId(),
                        b.getTitle(),
                        b.getAuthor(),
                        b.getCreatedAt() != null ? b.getCreatedAt().toString() : null,
                        (b.getCategory() != null && b.getCategory().getName() != null) ? b.getCategory().getName() : null
                ))
                .toList();

        Map<String, Object> payload = new HashMap<>();
        payload.put("items", items);
        payload.put("page", page);
        payload.put("hasNext", blogs.hasNext());
        return payload;
    }
}