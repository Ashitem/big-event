package com.ashitem.service;

import com.ashitem.pojo.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryService {
    //添加文章分类信息
    void add(@Param("category") Category category);

    //列表查询
    List<Category> list();

    //根据id查询分类
    Category findById(Integer id);

    //更新分类
    void update(Category category);

    //删除分类
    void delete(Integer id);
}
