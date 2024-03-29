package fsd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
public class FileUploadService {

    @Autowired
    private S3Client s3Client;

    @Value("${spring.datasource.bucket-name}")
    private String bucketName;

    public void uploadFileToS3(MultipartFile file, String fileName) throws IOException {
        if (!file.isEmpty()) {
            // Construct the file key within the S3 bucket
            String fileKey = "img/" + fileName;

            // Create a PutObjectRequest
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileKey)
                    .contentType(file.getContentType())
                    .build();

            // Upload the file
            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        }
    }

}
