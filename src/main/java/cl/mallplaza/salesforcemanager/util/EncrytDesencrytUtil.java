package cl.mallplaza.salesforcemanager.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cl.mallplaza.salesforcemanager.exception.EncrytDesencrytException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EncrytDesencrytUtil {

	private static String encoding = "UTF-8";
	private static String errorEncoding = "[EncrytDesencrytUtil] - Unsupported Encoding";
	private static String errorEncrypting = "[EncrytDesencrytUtil] - Error encrypting value";
	private static String errorDesencrypting = "[EncrytDesencrytUtil] - Error desencrypting value";
	private static String encriptType = "AES/CBC/PKCS5PADDING";
	private static String aesEncryptionAlgorithm = "AES";
	private static Integer lenKey                = 16;
	
	@Value("${properties.encript.word-for-key}")
	private String wordForKey;
	
	
	 public String encript(String value) {
	    	byte[] bytes = null;
			try {
				bytes = value.getBytes(encoding);
			} catch (UnsupportedEncodingException e) {
				log.error(errorEncoding);
				throw new EncrytDesencrytException(errorEncoding, e);
			}
	    	Cipher cipher = obtainCipher(true);
	    	byte[] encript = null;
			try {
				encript = cipher.doFinal(bytes);
			} catch (IllegalBlockSizeException | BadPaddingException e) {
				log.error(errorEncrypting);
				throw new EncrytDesencrytException(errorEncrypting, e);
			}

	    	return Base64.getEncoder().encodeToString(encript);
	    }

    public String desencript(String value) {
    	final byte[] bytes = Base64.getDecoder().decode(value);
    	final Cipher cipher = obtainCipher(false);	    	
    	byte[] desencript;
		try {
			desencript = cipher.doFinal(bytes);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			log.error(errorDesencrypting);
			throw new EncrytDesencrytException(errorDesencrypting, e);
		}
    	String desencriptStr;
		try {
			desencriptStr = new String(desencript, encoding);
		} catch (UnsupportedEncodingException e) {
			log.error(errorEncoding);
			throw new EncrytDesencrytException(errorEncoding, e);
		}
    	return desencriptStr;
    }
    
    public Cipher obtainCipher(boolean isEncript) {
    	byte[] key = null;
    	Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(encriptType);
            key = wordForKey.getBytes(encoding);
			
		} catch (NoSuchAlgorithmException e) {
			log.error("[EncrytDesencrytUtil] - No Such Algorithm MessageDigest.getInstance(\"SHA\") or Cipher.getInstance(\"AES/ECB/PKCS5Padding\")");
			throw new EncrytDesencrytException("No Such Algorithm", e);
			
		} catch (UnsupportedEncodingException e) {
			log.error(errorEncoding);
			throw new EncrytDesencrytException(errorEncoding, e);
			
		} catch (NoSuchPaddingException e) {
			log.error("[EncrytDesencrytUtil] - No Such Padding");
			throw new EncrytDesencrytException("No Such Padding", e);
		}

        SecretKeySpec secretKey = new SecretKeySpec(zeroPadding(key, lenKey), aesEncryptionAlgorithm);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(zeroPadding(key, lenKey));
 
		try {
	    	if (isEncript) {
				cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
	    	} else {
	    	    cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
	    	}
		} catch (InvalidKeyException e) {
			log.error("[EncrytDesencrytUtil] - Invalid Key");
			throw new EncrytDesencrytException("Invalid Key", e);
		} catch (InvalidAlgorithmParameterException e) {
			log.error("[EncrytDesencrytUtil] - Invalid Algorithm Parameter. failed to initialize cipher.");
			throw new EncrytDesencrytException("Invalid Algorithm Parameter. failed to initialize cipher.", e);
		}
    	return cipher;
    }
    
	private byte[] zeroPadding(byte[] key, Integer len) {
		byte[] bytes = new byte[len];
		try {
			bytes = Arrays.copyOf(key, len);
		} catch (NegativeArraySizeException | NullPointerException e) {
			log.error("[EncrytDesencrytUtil] - Negative Array Size  or  key byte null.");
			throw new EncrytDesencrytException("Negative Array Size  or  key byte null.", e);
		}
		return bytes;
	}
}
