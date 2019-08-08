package com.udb.algoritmos;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
	//private static final String TOKEN = "passwd";
	private String salt;
	private int pwdIterations = 65536;
	private int keySize = 256;
	private byte[] ivBytes;
	private String keyAlgorithm = "AES";
	private String encryptAlgorithm = "AES/CBC/PKCS5Padding";
	private String secretKeyFactoryAlgorithm = "PBKDF2WithHmacSHA1";
	private String stringSALT;
	private String stringIV;
	private String stringKEY;
	private String claveCompleta;
	
	public AES(){
		this.salt = getSalt();
	}
	
	public String getStringSALT() {
		return stringSALT;
	}
	public String getStringIV() {
		return stringIV;
	}
	public String getStringKEY() {
		return stringKEY;
	}
	
	private String getSalt(){
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);
		String text = new String(bytes);
		return text;
	}
	
	public String generarClave(String password) throws Exception{
		
		claveCompleta = "";
		//Generando SALT
		byte[] saltBytes = salt.getBytes("UTF-8");
		stringSALT = Base64.getEncoder().encodeToString(saltBytes);
		
		//Generando llave
		SecretKeyFactory skf = SecretKeyFactory.getInstance(this.secretKeyFactoryAlgorithm);
		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, this.pwdIterations, this.keySize);
		SecretKey secretKey = skf.generateSecret(spec);
		System.out.println(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
		
		SecretKeySpec key = new SecretKeySpec(secretKey.getEncoded(), keyAlgorithm);
		System.out.println(Base64.getEncoder().encodeToString(key.getEncoded()));
		stringKEY = Base64.getEncoder().encodeToString(key.getEncoded());
		
		//Inicializacion AES
		Cipher cipher = Cipher.getInstance(encryptAlgorithm);
		cipher.init(Cipher.ENCRYPT_MODE, key);
				
		//Generando IV
		this.ivBytes = cipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
		stringIV = Base64.getEncoder().encodeToString(ivBytes);
		System.out.println(Arrays.toString(stringIV.getBytes()));
		
		//Completando llave
		claveCompleta = stringIV + "," + stringKEY;
		//System.out.println(Arrays.toString(claveCompleta.getBytes("UTF-8")));
		claveCompleta = Base64.getEncoder().encodeToString(claveCompleta.getBytes("UTF-8"));
		//System.out.println(Arrays.toString(claveCompleta.getBytes("UTF-8")));
		return claveCompleta;
	}
	
	/**
	 * 
	 * @param plainText
	 * @return encrypted text
	 * @throws Exception
	 */
	public String encyrpt(String password, String plainText) throws Exception{
		
		String primeraConversion = new String(Base64.getDecoder().decode(password.getBytes("UTF-8")), Charset.forName("UTF-8"));
		String[] componentes = primeraConversion.split(",");
		stringIV = componentes[0];
		stringKEY = componentes[1];
		
		Key key = new SecretKeySpec(Base64.getDecoder().decode(stringKEY.getBytes("UTF-8")), "AES");
		
		//AES initialization
		Cipher cipher = Cipher.getInstance(encryptAlgorithm);
		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(Base64.getDecoder().decode(stringIV.getBytes())));
		
		//generate IV
		//this.ivBytes = stringIV.getBytes();
		byte[] encryptedText = cipher.doFinal(plainText.getBytes("UTF-8"));
		return Base64.getEncoder().encodeToString(encryptedText);
		
		 /*Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		 
	     byte[] keyBytes = new byte[16];
	     byte[] b = stringKEY.getBytes("UTF-8");
	     int len = b.length;
	     if (len > keyBytes.length) len = keyBytes.length;
	     System.arraycopy(b, 0, keyBytes, 0, len);
	     
	     SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
	     AlgorithmParameterSpec spec = new IvParameterSpec(keyBytes);
	     
	     cipher.init(Cipher.ENCRYPT_MODE, keySpec, spec);
	     
		System.out.println(Arrays.toString(stringIV.getBytes()));
		byte[] encryptedText = cipher.doFinal(plainText.getBytes("UTF-8"));
		return Base64.getEncoder().encodeToString(encryptedText);*/
		
		
		/*//generate key
		byte[] saltBytes = salt.getBytes("UTF-8");
		
		SecretKeyFactory skf = SecretKeyFactory.getInstance(this.secretKeyFactoryAlgorithm);
		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, this.pwdIterations, this.keySize);
		SecretKey secretKey = skf.generateSecret(spec);
		SecretKeySpec key = new SecretKeySpec(secretKey.getEncoded(), keyAlgorithm);
		
		
		//AES initialization
		Cipher cipher = Cipher.getInstance(encryptAlgorithm);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		
		//generate IV
		this.ivBytes = cipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
		byte[] encryptedText = cipher.doFinal(plainText.getBytes("UTF-8"));
		return Base64.getEncoder().encodeToString(encryptedText);*/
	}
	
	/**
	 * 
	 * @param encryptText
	 * @return decrypted text
	 * @throws Exception
	 */
	public String decrypt(String password, String encryptText) throws Exception {
		byte[] saltBytes = salt.getBytes("UTF-8");
		byte[] encryptTextBytes = Base64.getDecoder().decode(encryptText);
		
		String primeraConversion = new String(Base64.getDecoder().decode(password.getBytes("UTF-8")), Charset.forName("UTF-8"));
		String[] componentes = primeraConversion.split(",");
		stringIV = componentes[0];
		stringKEY = componentes[1];
		
		Key key = new SecretKeySpec(Base64.getDecoder().decode(stringKEY.getBytes("UTF-8")), "AES");
		
		//AES initialization
		Cipher cipher = Cipher.getInstance(encryptAlgorithm);
		cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(Base64.getDecoder().decode(stringIV.getBytes())));
	     
		/*System.out.println(Arrays.toString(stringIV.getBytes()));
		byte[] encryptedText = cipher.doFinal(plainText.getBytes("UTF-8"));
		return Base64.getEncoder().encodeToString(encryptedText);*/
		
		/*SecretKeyFactory skf = SecretKeyFactory.getInstance(this.secretKeyFactoryAlgorithm);
		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, this.pwdIterations, this.keySize);
		SecretKey secretKey = skf.generateSecret(spec);
		SecretKeySpec key = new SecretKeySpec(secretKey.getEncoded(), keyAlgorithm);
		
		//decrypt the message
		Cipher cipher = Cipher.getInstance(encryptAlgorithm);
		cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivBytes));*/
		
		byte[] decyrptTextBytes = null;
		try {
			decyrptTextBytes = cipher.doFinal(encryptTextBytes);
		} catch (IllegalBlockSizeException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		String text = new String(decyrptTextBytes);
		return text;
	}
}