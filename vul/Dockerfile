FROM openjdk:17-alpine
#作者
MAINTAINER gaoweifeng
# 复制文件到容器
ADD target/vul-0.0.1-SNAPSHOT.jar /app.jar


#声明运行时容器提供服务端口，这只是一个声明，在运行时并不会因为这个声明应用就会开启这个端口的服务
EXPOSE 8081
ENV jasypt.encryptor.password e!Jd&ljyJ^e4I5oU
#指定容器启动程序及参数   <ENTRYPOINT> "<CMD>"
ENTRYPOINT ["java","-jar","app.jar"]
