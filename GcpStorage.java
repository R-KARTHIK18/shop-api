package com.pubsub.process.GCPStorage;


import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
@Log4j2
@Configuration
public class GcpStorage {


	@Value("${gcp.bucket.id}")
	private String bucketName;
	
	@Autowired
	private Storage storage;




	public Blob uploadFile(String fileData, String filePath) {
		try {
			byte[] decodedData = Base64.getDecoder().decode(fileData.getBytes(StandardCharsets.UTF_8));
			BlobId blobId = BlobId.of(bucketName, filePath);
			BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
			InputStream targetStream = new ByteArrayInputStream(decodedData);
			targetStream.close();
			Blob bolb = storage.createFrom(blobInfo, targetStream);
			log.info("File uploaded to bucket " + bucketName + " as " + filePath);
			return bolb;
		} catch (Exception e) {
			log.info("info : {}", e.getMessage());
			throw new GcpStorageException(ResponseStatusCode.GCP_STORAGE_ERROR.getValue(),
					ResponseStatusCode.GCP_STORAGE_ERROR.getCode(), e);
		}
	}

	public Blob uploadFile(byte[] fileData, String filePath) {
		try {
			BlobId blobId = BlobId.of(bucketName, filePath);
			BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
			InputStream targetStream = new ByteArrayInputStream(fileData);
			targetStream.close();
			Blob bolb = storage.createFrom(blobInfo, targetStream);
			log.info("File uploaded to bucket " + bucketName + " as " + filePath);
			return bolb;
		} catch (Exception e) {
			throw new GcpStorageException(ResponseStatusCode.GCP_STORAGE_ERROR.getValue(),
					ResponseStatusCode.GCP_STORAGE_ERROR.getCode(), e);
		}
	}

	public String getFileBase64(String filePath) {
		try {
			BlobId blobId = BlobId.of(bucketName, filePath);
			byte[] content = storage.readAllBytes(blobId);
			log.info("File downloaded to bucket " + bucketName + " as " + filePath);
			String encoded = Base64.getEncoder().encodeToString(content);
			return encoded;
		} catch (Exception e) {
			throw new GcpStorageException(ResponseStatusCode.GCP_STORAGE_ERROR.getValue(),
					ResponseStatusCode.GCP_STORAGE_ERROR.getCode(), e);
		}
	}

	public byte[] getFile(String filePath) {
		try {
			BlobId blobId = BlobId.of(bucketName, filePath);
			byte[] content = storage.readAllBytes(blobId);
			log.info("File downloaded to bucket " + bucketName + " as " + filePath);
			return content;
		} catch (Exception e) {
			throw new GcpStorageException(ResponseStatusCode.GCP_STORAGE_ERROR.getValue(),
					ResponseStatusCode.GCP_STORAGE_ERROR.getCode(), e);
		}
	}

	public boolean deleteFile(String filePath) {
		try {
			Blob blob = storage.get(bucketName, filePath);
			if (blob == null) {
				log.info("The object " + filePath + " is not found in " + bucketName);
				return false;
			}
			boolean flag = storage.delete(bucketName, filePath);
			log.info("Files deleted from bucket " + bucketName + " at location " + filePath);
			return flag;
		} catch (Exception e) {
			throw new GcpStorageException(ResponseStatusCode.GCP_STORAGE_ERROR.getValue(),
					ResponseStatusCode.GCP_STORAGE_ERROR.getCode(), e);
		}
	}

	public boolean isFileExist(String filePath) {
		try {
			Blob blob = storage.get(bucketName, filePath);
			if (blob == null) {
				log.info("The object " + filePath + " is not found in " + bucketName);
				return false;
			}
			return true;
		} catch (Exception e) {
			throw new GcpStorageException(ResponseStatusCode.GCP_STORAGE_ERROR.getValue(),
					ResponseStatusCode.GCP_STORAGE_ERROR.getCode(), e);
		}
	}
	
	public List<String> listOfFiles() {
		List<String> list = new ArrayList<>();
		Page<Blob> blobs = storage.list(bucketName);
		for (Blob blob : blobs.iterateAll()) {
			list.add(blob.getName());
		}
		return list;
	}
}
