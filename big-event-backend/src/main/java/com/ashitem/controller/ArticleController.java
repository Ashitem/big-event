package com.ashitem.controller;

import com.ashitem.pojo.Article;
import com.ashitem.pojo.PageBean;
import com.ashitem.pojo.Result;
import com.ashitem.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/article")
public class ArticleController {
//    @GetMapping("/list")
//    public Result<String> list() {
////        //验证token
////        try {
////            Map<String,Object> claims= JwtUtil.parseToken(token);
////            return Result.success("文章数据........");
////        } catch (Exception e) {
////            //http响应状态码为401
////            response.setStatus(401);
////            return Result.error("未登录！");
////        }
//        return Result.success("文章数据........");
//    }

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result add(@RequestBody @Validated(Article.add.class) Article article) {
        articleService.add(article);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Article>> list(Integer pageNum, Integer pageSize, @RequestParam(required = false) String categoryId, @RequestParam(required = false) String state) {
       PageBean<Article> pb=  articleService.list(pageNum,pageSize,categoryId,state);
        return Result.success(pb);
    }

    @GetMapping("/detail")
    public Result<Article> detail(Integer id){
       Article article= articleService.findById(id);
        return Result.success(article);
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Article.update.class) Article article){
        articleService.update(article);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(Integer id){
        articleService.delete(id);
        return Result.success();
    }


}
