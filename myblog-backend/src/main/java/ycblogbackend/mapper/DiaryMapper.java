package ycblogbackend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ycblogbackend.pojo.Diary;

import java.util.List;

//@Mapper
@Repository
public interface DiaryMapper extends JpaRepository<Diary,Long> {
}
