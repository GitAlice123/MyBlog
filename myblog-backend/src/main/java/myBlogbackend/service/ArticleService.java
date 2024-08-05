package ycblogbackend.service;

import ycblogbackend.pojo.Article;

import java.util.List;

public interface ArticleService {
    /**
     * 查询所有文章
     * @return 文章列表
     */
    List<Article> list();

    /**
     * 按照文章id查询文章
     * @param idarticle 文章id
     * @return 文章
     */
    Article getArticleById(String idarticle);

    /**
     * 通过id查询文章标题图片路径
     * @param idarticle 文章id
     * @return 文章标题图片路径
     */
    String getTitleImgById(String idarticle);

    /**
     * 新增文章
     * @param article 文章
     */
    void addArticle(Article article);

    /**
     * 更新文章
     * @param article 文章
     */
    void updateArticle(Article article);

    /**
     * 删除文章
     * @param idarticle 文章id
     */
    void deleteArticle(String idarticle);
}
