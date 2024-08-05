package myBlogbackend.controller;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import myBlogbackend.pojo.Result;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ImageController {

    // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
    String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
    EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
    // 填写Bucket名称，例如examplebucket。
    String bucketName = "img-alice";
    // 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称。
    String objectName;
    OSS ossClient;

    public ImageController() throws com.aliyuncs.exceptions.ClientException {
        // 创建OSSClient实例。
        ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/upload-image")
    public ResponseEntity<Object> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // 生成唯一的文件名
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            objectName = "images/" + fileName;
            // 上传文件
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(file.getBytes()));
            String url = "https://img-alice.oss-cn-beijing.aliyuncs.com/" + objectName;
            return new ResponseEntity<>(new Result(1, "上传成功", url), HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
            return new ResponseEntity<>(new Result(0, "上传失败", null), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
            return new ResponseEntity<>(new Result(0, "上传失败", null), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            if (ossClient != null) {
//                ossClient.shutdown();
            }
        }
    }
}