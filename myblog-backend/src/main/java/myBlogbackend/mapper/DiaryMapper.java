package myBlogbackend.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import myBlogbackend.pojo.Diary;

//@Mapper
@Repository
public interface DiaryMapper extends JpaRepository<Diary,Long> {
}
