# 使用官方Java运行环境作为父镜像
FROM openjdk:19-slim


# 添加一个卷点，可以外部挂载数据卷
VOLUME /tmp

# 复制可执行jar文件到Docker镜像内部的/app目录
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# 暴露8080端口，供外部访问
EXPOSE 8080

# 使用java命令启动Spring Boot应用
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
