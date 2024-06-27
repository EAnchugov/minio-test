//package org.example.miniotest;
//
//import io.minio.BucketExistsArgs;
//import io.minio.MakeBucketArgs;
//import io.minio.MinioClient;
//import io.minio.PutObjectArgs;
//
//import java.io.FileInputStream;
//
//public class MinioUploader {
//    public static void main(String[] args) {
//        try {
//            // Создайте MinIO клиент
//            MinioClient minioClient = MinioClient.builder()
//                    .endpoint("http://localhost:9000")
//                    .credentials("minioadmin", "minioadmin")
//                    .build();
//
//            // Задайте параметры файла
//            String bucketName = "your-bucket-name";
//            String objectName = "minio.png";  // Имя объекта в MinIO
//            String filePath = "D:\\minio.png";  // Путь к вашему файлу
//
//            // Создайте бакет, если он не существует
//            boolean isExist = minioClient.bucketExists(
//                    BucketExistsArgs.builder().bucket(bucketName).build()
//            );
//            if (!isExist) {
//                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
//            }
//
//            // Загрузите файл в MinIO
//            try (FileInputStream fis = new FileInputStream(filePath)) {
//                minioClient.putObject(
//                        PutObjectArgs.builder()
//                                .bucket(bucketName)
//                                .object(objectName)
//                                .stream(fis, fis.available(), -1)
//                                .contentType("image/png")  // Укажите тип содержимого как image/png
//                                .build()
//                );
//            }
//
//            System.out.println("File uploaded successfully.");
//        } catch (Exception e) {
//            System.err.println("Error occurred: " + e.getMessage());
//        }
//    }
//}
//
