package org.example.miniotest;

import io.minio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;

@Service
public class MinioService {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    public InputStream getFile(String objectName) throws Exception {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build()
        );
    }

    public void addFile() {
        try {
            // Создайте MinIO клиент
            MinioClient minioClient = MinioClient.builder()
                    .endpoint("http://localhost:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();

            // Задайте параметры файла
            //       String bucketName = bucketName;
            String objectName = "minio.png";  // Имя объекта в MinIO
            String filePath = "D:\\minio.png";  // Путь к вашему файлу

            // Создайте бакет, если он не существует
            boolean isExist = minioClient.bucketExists(
                    BucketExistsArgs.builder().bucket(bucketName).build()
            );
            if (!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            // Загрузите файл в MinIO
            try (FileInputStream fis = new FileInputStream(filePath)) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(objectName)
                                .stream(fis, fis.available(), -1)
                                .contentType("image/png")  // Укажите тип содержимого как image/png
                                .build()
                );
            }

            System.out.println("File uploaded successfully.");
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }
}
