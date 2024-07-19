# BlogDemo项目

基于 JDK17，Spring Boot 3.2.7


[toc]

## 模板特点

### 使用框架

- Spring Boot 3.2.7
- Spring MVC
- MyBatis + MyBatis Plus 数据访问（开启分页）
- Spring Boot 调试工具和项目处理器

### 数据存储
- MySQL 数据库

### 工具类

- Hutool 工具库
- Lombok 注解


## 业务功能
- 实现一个简单的 token 生成机制（也可以使用现成的token生成机制）
使用JWT生成token
- 在需要认证的接口中，从请求头获取并验证 token
定义一个GlobalFilter，来验证token
- 注意数据的安全性，如密码加密存储
密码使用md5加密

## 快速上手
部署mysql
```bash
docker run -d \
  --name mysql \
  -p 3306:3306 \
  -e TZ=Asia/Shanghai \
  -e MYSQL_ROOT_PASSWORD=123456 \
  -v /root/mysql/data:/var/lib/mysql \
  -v /root/mysql/conf:/etc/mysql/conf.d \
  -v /root/mysql/init:/docker-entrypoint-initdb.d \
  mysql
```
将sql数据放到/root/mysql/data中

执行命令加载镜像
```bash
docker build -t blog .
```

启动镜像
```bash
docker run -d --name blog -p 8080:8080 blog
```
