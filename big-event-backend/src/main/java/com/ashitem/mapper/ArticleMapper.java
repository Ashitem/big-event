package com.ashitem.mapper;

import com.ashitem.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {
    //新增
    @Insert("insert into article(title,content,cover_img,state,category_id,create_user,create_time,update_time)" +
            " values (#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},now(),now())"
    )
    void add(Article article);

    //查询所有信息
    List<Article> list(Integer userId, String categoryId, String state);

    //根据id查询
    @Select("select * from article where id=#{id}")
    Article findById(Integer id);

    //删除
    @Delete("delete from article where id=#{id}")
    void delete(Integer id);

    //更新
    @Update("update article set title=#{title},content=#{content},cover_img=#{coverImg},state=#{state},category_id=#{categoryId},update_time=now() where id=#{id}")
    void update(Article article);
}
