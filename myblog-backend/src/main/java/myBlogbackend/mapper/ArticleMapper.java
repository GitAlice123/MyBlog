package ycblogbackend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ycblogbackend.pojo.Article;

import java.util.List;

//@Mapper
@Repository
public interface ArticleMapper extends JpaRepository<Article,Long> {
//    @Select("select * from article")
//    public List<Article> list();
//
////    通过id查询文章
//    @Select("select * from article where idarticle = #{idarticle}")
//    public Article getArticleById(String idarticle);
//
////    通过id查询文章标题图片路径
//    @Select("select title_img_path from article where idarticle = #{idarticle}")
//    public String getTitleImgById(String idarticle);
//
////    新增文章
//    @Insert("insert into article (idarticle, title, articleContent, title_img_path) values (#{idarticle}, #{title}, #{articleContent}, #{title_img_path})")
//    public void addArticle(Article article);
}
