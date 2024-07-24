package ycblogbackend.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ycblogbackend.mapper.DiaryMapper;
import ycblogbackend.pojo.Diary;
import ycblogbackend.service.DiaryService;

import java.util.List;

@Slf4j
@Service
public class DiaryServiceImpl implements DiaryService {

    @Autowired
    private DiaryMapper diaryMapper;

    @Override
    public List<Diary> list() {
        log.info("查询所有文章：service");
        return diaryMapper.findAll();
    }

    @Override
    public Diary getdiaryById(String iddiary) {
        log.info("按照文章id查询文章：service");
        return diaryMapper.findById(Long.parseLong(iddiary)).get();
    }

    @Override
    public String getTitleImgById(String iddiary) {
        log.info("通过id查询文章标题图片路径：service");
        return diaryMapper.findById(Long.parseLong(iddiary)).get().getTitle_img_path();
    }

    @Override
    public void adddiary(Diary diary) {
        log.info("新增文章：service");
        diaryMapper.save(diary);
    }

    @Override
    public void updatediary(Diary diary) {
        log.info("更新文章：service");
        diaryMapper.save(diary);
    }

    @Override
    public void deletediary(String iddiary) {
        log.info("删除文章：service");
        diaryMapper.deleteById(Long.parseLong(iddiary));
    }

}
