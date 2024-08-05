package myBlogbackend.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idarticle;

    @Column(nullable = false, length = 50)
    String title;

    @Column(columnDefinition = "TEXT")
    String articleContent;

    @Column(nullable = false, length = 500)
    String title_img_path;
}
