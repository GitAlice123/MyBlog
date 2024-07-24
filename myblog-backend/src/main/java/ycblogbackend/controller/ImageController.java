package ycblogbackend.controller;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ycblogbackend.pojo.Result;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ImageController {

    private String minioUrl = "http://127.0.0.1:9000"; // Minio服务地址
    private String bucketName = "img"; // 存储桶名称

    private MinioClient minioClient;

    public ImageController() {
        try {
            // 初始化Minio客户端
//            minioClient = new MinioClient(minioUrl, "minioadmin", "minioadmin");
//            后面访问图片的时候不是下载链接，而是直接打开图片，所以需要设置桶的策略为公共读
            minioClient = MinioClient.builder()
                    .endpoint(minioUrl)
                    .credentials("minioadmin", "minioadmin")
                    .build();

            // 检查存储桶是否存在，如果不存在则创建
            boolean isExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!isExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (MinioException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/upload-image")
    public ResponseEntity<Object> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // 生成唯一的文件名
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            // 将文件转换为字节序列
            byte[] bytes = file.getBytes();

            // 将文件上传到Minio
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            // 返回图片的URL
            String objectUrl = minioUrl + "/" + bucketName + "/" + fileName;
            return ResponseEntity.ok(new Result(1, "上传成功", objectUrl));
        } catch (IOException | MinioException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result(0, "上传失败", null));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}