package myBlogbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import myBlogbackend.pojo.Diary;
import myBlogbackend.pojo.Result;
import myBlogbackend.service.DiaryService;

import java.util.List;

@Slf4j
@RestController()
@RequestMapping("/api")
public class DiaryController {
    @Autowired
    private DiaryService diaryService;

    @CrossOrigin(origins = "*")
    @GetMapping("/diary/list")
    public Object list() {
        log.info("查询所有文章数据：controller");
        List<Diary> diaryList = diaryService.list();
        return Result.success(diaryList);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/diary/getdiaryContent")
    public Object getdiaryContent(String iddiary) {
        log.info("按照文章id查询文章：controller");
        System.out.println("id为"+iddiary);
        Diary diary = diaryService.getdiaryById(iddiary);
        return Result.success(diary);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/diary/getTitleImg")
    public Object getTitleImg(String iddiary) {
        log.info("通过id查询文章标题图片路径：controller");
        String titleImg = diaryService.getTitleImgById(iddiary);
        return Result.success(titleImg);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/diary/adddiary") // 使用 @PostMapping 替代 @GetMapping
    public Object adddiary(@RequestBody Diary diary) { // 添加 @RequestBody 注解
        log.info("新增文章：controller");
        System.out.println(diary);
        diaryService.adddiary(diary);
        return Result.success(null);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/diary/updatediary") // 使用 @PostMapping 替代 @GetMapping
    public Object updatediary(@RequestBody Diary diary) { // 添加 @RequestBody 注解
        log.info("更新文章：controller");
        System.out.println(diary);
        diaryService.updatediary(diary);
        return Result.success(null);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/diary/deletediary")
    public Object deletediary(String iddiary) {
        log.info("删除文章：controller");
        diaryService.deletediary(iddiary);
        return Result.success(null);
    }
}
