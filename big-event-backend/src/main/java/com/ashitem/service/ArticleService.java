package com.ashitem.service;

import com.ashitem.pojo.Article;
import com.ashitem.pojo.PageBean;

public interface ArticleService {
    //新增文章
    void add(Article article);

    //条件分页列表查询
    PageBean<Article> list(Integer pageNum, Integer pageSize, String categoryId, String state);

    //根据id查询分类
    Article findById(Integer id);

    //更新分类
    void delete(Integer id);

    //删除分类
    void update(Article article);
}
