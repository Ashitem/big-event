package com.ashitem.controller;

import com.ashitem.pojo.Category;
import com.ashitem.pojo.Result;
import com.ashitem.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result add(@RequestBody @Validated(Category.add.class) Category category) {
        categoryService.add(category);
        return Result.success();
    }

    @GetMapping
    public Result<List<Category>> list() {
        List<Category> ls = categoryService.list();
        return Result.success(ls);
    }

    @GetMapping("/detail")
    public Result<Category> detail(Integer id) {
       Category category= categoryService.findById(id);
        return Result.success(category);
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Category.update.class) Category category){
        categoryService.update(category);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(Integer id){
        categoryService.delete(id);
        return Result.success();
    }
}
