package com.ashitem.service.impl;

import com.ashitem.mapper.ArticleMapper;
import com.ashitem.pojo.Article;
import com.ashitem.pojo.PageBean;
import com.ashitem.service.ArticleService;
import com.ashitem.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {
        //补充属性值
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        article.setCreateUser(userId);
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, String categoryId, String state) {
        //创建PageBean对象
        PageBean<Article> pb = new PageBean<>();
        //开启分页查询
        PageHelper.startPage(pageNum, pageSize);
        //调用mapper
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Article> ls = articleMapper.list(userId, categoryId, state);
        //Page中提供了方法，可以获取PageHelper分页查询后得到的总记录条数和当前页数据
        Page<Article> p = (Page<Article>) ls;
        //把数据填充到PageBean对象中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    @Override
    public Article findById(Integer id) {
        return articleMapper.findById(id);
    }

    @Override
    public void delete(Integer id) {
        articleMapper.delete(id);
    }

    @Override
    public void update(Article article) {
        articleMapper.update(article);
    }
}
