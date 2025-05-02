package com.pubsub.process.config;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class EncryptDecryptUtil {

	@Value("${iv}")
	public String iv;
	
	@Value("${key}")
	public String key;
	
	
	
	public String encryptData(String req) throws Exception {
		
		String encriptreq=encrypt(req,key,iv);
		log.info("Encrypted text :{}",encriptreq);
		return encriptreq;
	}

	private String encrypt(String req, String key2, String iv2) throws Exception {
		SecretKeySpec secretKeySpec=new SecretKeySpec(key.getBytes(), "AES");
		IvParameterSpec ivspec=new IvParameterSpec(iv2.getBytes()); 
		Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec,ivspec);
		byte[] encryptedbytes=cipher.doFinal(req.getBytes());
		return Base64.getEncoder().encodeToString(encryptedbytes);
	}
	
	public String decript(String req) throws Exception {
		String decryptedText=decript(req,key,iv);
		log.info("Decrypted text :{}",decryptedText);
		return req;
		
		
	}

	private String decript(String req, String key2, String iv2) throws Exception{
		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
		byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(req));
		return new String(decryptedBytes);
	}
	
	
	
}
