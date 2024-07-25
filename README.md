# 个人博客
## 技术
* springboot
* vue3
* OSS使用阿里云的
## 前期准备
* JDK版本：21
* node版本：v16.20.2
* 由于使用阿里云的oss时验证方式我选择了AK方式，两个密钥都是放在环境变量里的，配置方式参考[阿里云oss+Java配置使用教程](https://help.aliyun.com/zh/oss/developer-reference/java-installation?spm=a2c4g.11186623.0.i1 "阿里云oss+Java配置使用教程")
* **注意配置环境变量之后可能需要重启电脑**
## 运行方式
* 后端：进入myblog-backend文件夹，输入
```shell
mvn spring-boot:run
```
* 前端：进入myblog-frontend文件夹，输入
```shell
# 如果需要修改前端并及时显示效果
npm run serve
# 如果不需要
npm run dev
```
## 测试登录密码
* 进入页面之后，如果不登录则仅能浏览查看
* 如果要登录，点击右上角“登录”，测试用户名为Alice，测试密码为yc030316