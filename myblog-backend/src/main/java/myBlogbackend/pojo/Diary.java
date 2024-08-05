package myBlogbackend.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iddiary;

    @Column(nullable = false, length = 50)
    String title;

    @Column(columnDefinition = "TEXT")
    String diaryContent;

    @Column(nullable = false, length = 500)
    String title_img_path;
}
