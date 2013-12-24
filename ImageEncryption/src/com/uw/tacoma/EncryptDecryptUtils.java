/**
 * This code instance has been taken from 
 * http://stackoverflow.com/questions/14260734/java-how-to-decrypt-a-byte-array-given-the-method
 */
package com.uw.tacoma;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptDecryptUtils {
	public static byte[] encryptByteArrayWithKey(byte[] array, byte[] key) throws Exception
	{
	    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
	    SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
	    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	    return cipher.doFinal(array);
	}

	public static byte[] decryptByteArrayWithKey(byte[] encryptedArray, byte[] key) throws Exception
	{
	    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
	    SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
	    cipher.init(Cipher.DECRYPT_MODE, secretKey);
	    return cipher.doFinal(encryptedArray);
	}
	
	public static SecretKey generateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		SecureRandom random = new SecureRandom(); // cryptograph. secure random 
		keyGen.init(random);
		SecretKey secretKey = keyGen.generateKey();
		return secretKey;
	}
}
