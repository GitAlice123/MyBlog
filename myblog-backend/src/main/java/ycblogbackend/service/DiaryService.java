package ycblogbackend.service;

import ycblogbackend.pojo.Diary;

import java.util.List;

public interface DiaryService {
    /**
     * 查询所有文章
     * @return 文章列表
     */
    List<Diary> list();

    /**
     * 按照文章id查询文章
     * @param iddiary 文章id
     * @return 文章
     */
    Diary getdiaryById(String iddiary);

    /**
     * 通过id查询文章标题图片路径
     * @param iddiary 文章id
     * @return 文章标题图片路径
     */
    String getTitleImgById(String iddiary);

    /**
     * 新增文章
     * @param diary 文章
     */
    void adddiary(Diary diary);

    /**
     * 更新文章
     * @param diary 文章
     */
    void updatediary(Diary diary);

    /**
     * 删除文章
     * @param iddiary 文章id
     */
    void deletediary(String iddiary);
}
