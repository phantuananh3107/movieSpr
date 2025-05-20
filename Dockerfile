# Sử dụng image Java chính thức (JDK 17)
FROM openjdk:17-jdk-slim

# Thiết lập thư mục làm việc
WORKDIR /app

# Sao chép file JAR đã build vào container
COPY target/moviespring-0.0.1-SNAPSHOT.jar app.jar

# Cổng mà ứng dụng sẽ chạy
EXPOSE 8080

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]