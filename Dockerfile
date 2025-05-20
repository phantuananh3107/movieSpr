# Sử dụng image Java với Maven
FROM maven:3.8.6-openjdk-17 AS build

# Thiết lập thư mục làm việc
WORKDIR /app

# Sao chép mã nguồn (bao gồm pom.xml và source code)
COPY . .

# Chạy Maven để build và tạo file JAR
RUN mvn clean install -DskipTests

# Giai đoạn chạy ứng dụng
FROM openjdk:17-jdk-slim

# Thiết lập thư mục làm việc
WORKDIR /app

# Sao chép file JAR từ giai đoạn build
COPY --from=build /app/target/moviespring-0.0.1-SNAPSHOT.jar app.jar

# Cổng mà ứng dụng sẽ chạy
EXPOSE 8080

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
