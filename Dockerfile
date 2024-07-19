# 基础镜像
FROM centos:latest

# 设定时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
# 拷贝jar包
COPY ./BlogDemo-0.0.1-SNAPSHOT.jar /app.jar
# 入口
ENTRYPOINT ["/usr/lib/jvm/jdk-17-oracle-x64/bin/java", "-jar", "/app.jar"]