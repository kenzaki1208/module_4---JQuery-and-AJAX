package org.example.demo_jquery_and_ajax.exercise.controller;

import org.example.demo_jquery_and_ajax.exercise.model.Category;
import org.example.demo_jquery_and_ajax.exercise.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/exercise/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "exercise/category/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new Category());
        return "exercise/category/create";
    }

    @PostMapping("/create")
    public String create(Category category) {
        categoryService.save(category);
        return "redirect:/exercise/categories";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("category", categoryService.findById(id).orElseThrow());
        return "exercise/category/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@ModelAttribute Category category) {
        categoryService.save(category);
        return "redirect:/exercise/categories";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        categoryService.deleteById(id);
        return "redirect:/exercise/categories";
    }
}
