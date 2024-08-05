package myBlogbackend.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import myBlogbackend.mapper.ArticleMapper;
import myBlogbackend.pojo.Article;
import myBlogbackend.service.ArticleService;

import java.util.List;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<Article> list() {
        log.info("查询所有文章：service");
        return articleMapper.findAll();
    }

    @Override
    public Article getArticleById(String idarticle) {
        log.info("按照文章id查询文章：service");
        return articleMapper.findById(Long.parseLong(idarticle)).get();
    }

    @Override
    public String getTitleImgById(String idarticle) {
        log.info("通过id查询文章标题图片路径：service");
        return articleMapper.findById(Long.parseLong(idarticle)).get().getTitle_img_path();
    }

    @Override
    public void addArticle(Article article) {
        log.info("新增文章：service");
        articleMapper.save(article);
    }

    @Override
    public void updateArticle(Article article) {
        log.info("更新文章：service");
        articleMapper.save(article);
    }

    @Override
    public void deleteArticle(String idarticle) {
        log.info("删除文章：service");
        articleMapper.deleteById(Long.parseLong(idarticle));
    }

}
