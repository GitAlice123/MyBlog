package ycblogbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ycblogbackend.pojo.Article;
import ycblogbackend.pojo.Result;
import ycblogbackend.service.ArticleService;

import java.util.List;

@Slf4j
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @CrossOrigin(origins = "*")
    @GetMapping("/list")
    public Object list() {
        log.info("查询所有文章数据：controller");
        List<Article> articleList = articleService.list();
        return Result.success(articleList);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getArticleContent")
    public Object getArticleContent(String idarticle) {
        log.info("按照文章id查询文章：controller");
        System.out.println("id为"+idarticle);
        Article article = articleService.getArticleById(idarticle);
        return Result.success(article);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getTitleImg")
    public Object getTitleImg(String idarticle) {
        log.info("通过id查询文章标题图片路径：controller");
        String titleImg = articleService.getTitleImgById(idarticle);
        return Result.success(titleImg);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/addArticle") // 使用 @PostMapping 替代 @GetMapping
    public Object addArticle(@RequestBody Article article) { // 添加 @RequestBody 注解
        log.info("新增文章：controller");
        System.out.println(article);
        articleService.addArticle(article);
        return Result.success(null);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/updateArticle") // 使用 @PostMapping 替代 @GetMapping
    public Object updateArticle(@RequestBody Article article) { // 添加 @RequestBody 注解
        log.info("更新文章：controller");
        System.out.println(article);
        articleService.updateArticle(article);
        return Result.success(null);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/deleteArticle")
    public Object deleteArticle(String idarticle) {
        log.info("删除文章：controller");
        articleService.deleteArticle(idarticle);
        return Result.success(null);
    }
}
