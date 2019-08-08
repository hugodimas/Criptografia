package com.udb.algoritmos;

import org.bouncycastle.asn1.x500.X500Name;  
import org.bouncycastle.asn1.x509.BasicConstraints;  
import org.bouncycastle.asn1.x509.Extension;  
import org.bouncycastle.asn1.x509.KeyUsage;  
import org.bouncycastle.cert.X509v3CertificateBuilder;  
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;  
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;  
import org.bouncycastle.jce.provider.BouncyCastleProvider;  
import org.bouncycastle.operator.OperatorCreationException;  
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.io.FileOutputStream;  
import java.io.IOException;  
import java.math.BigInteger;  
import java.security.*;  
import java.security.cert.CertificateEncodingException;  
import java.security.cert.CertificateException;  
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class GeneradorCertificados {
	
	public static void generarCertificados(String ruta, Date fechaExpiracion) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, OperatorCreationException, InvalidKeyException, NoSuchProviderException, SignatureException, UnrecoverableKeyException {  
	     Security.addProvider(new BouncyCastleProvider());  
	     
	     Calendar calendar = Calendar.getInstance();
	     calendar.set(2025, 12, 31, 0, 0, 0);
	   
	     // Creacion de certificado raiz - self signed certificate
	     KeyPair rootCAKeyPair = generateKeyPair();  
	     X509v3CertificateBuilder builder = new JcaX509v3CertificateBuilder(  
	         new X500Name("CN=rootCA"), // issuer authority  
	         BigInteger.valueOf(new Random().nextInt()), //serial number of certificate  
	         new Date(), // start of validity  
	         fechaExpiracion, //end of certificate validity  
	         new X500Name("CN=rootCA"), // subject name of certificate  
	         rootCAKeyPair.getPublic() // public key of certificate
	     );   
	     // key usage restrictions  
	     builder.addExtension(Extension.keyUsage, true, new KeyUsage(KeyUsage.keyCertSign));  
	     builder.addExtension(Extension.basicConstraints, false, new BasicConstraints(true));  
	     // private key of signing authority , here it is self signed
	     X509Certificate rootCA = new JcaX509CertificateConverter().getCertificate(builder.build(new JcaContentSignerBuilder("SHA256withRSA").setProvider("BC").build(rootCAKeyPair.getPrivate())));  
	     //saveToFile(rootCA, "C:\\Users\\hp\\Desktop\\ejemplos\\rootCA.cer");  
	     saveToFile(rootCA, ruta + "\\rootCA.cer");
	   
	     //Creacion de certificado intermedio firmado por Certificado Raiz
	     KeyPair intermedCAKeyPair = generateKeyPair();  
	     builder = new JcaX509v3CertificateBuilder(  
	         rootCA, // here rootCA is issuer authority  
	         BigInteger.valueOf(new Random().nextInt()), 
	         new Date(),  
	         fechaExpiracion,  
	         new X500Name("CN=IntermedCA"), 
	         intermedCAKeyPair.getPublic()
	     );  
	     builder.addExtension(Extension.keyUsage, true, new KeyUsage(KeyUsage.keyCertSign));  
	     builder.addExtension(Extension.basicConstraints, false, new BasicConstraints(true));  
	     X509Certificate intermedCA = new JcaX509CertificateConverter().getCertificate(builder  
	         .build(new JcaContentSignerBuilder("SHA256withRSA").setProvider("BC").  
	             build(rootCAKeyPair.getPrivate())));// private key of signing authority , here it is signed by rootCA  
	     saveToFile(intermedCA, ruta + "\\intermedCA.cer");  
	   
	     //create end user cert signed by Intermediate CA  
	     KeyPair endUserCertKeyPair = generateKeyPair();  
	     builder = new JcaX509v3CertificateBuilder(  
	         intermedCA, //here intermedCA is issuer authority  
	         BigInteger.valueOf(new Random().nextInt()), 
	         new Date(),  
	         fechaExpiracion,  
	         new X500Name("CN=endUserCert"), 
	         endUserCertKeyPair.getPublic()
	     );  
	     builder.addExtension(Extension.keyUsage, true, new KeyUsage(KeyUsage.digitalSignature));  
	     builder.addExtension(Extension.basicConstraints, false, new BasicConstraints(false));  
	     X509Certificate endUserCert = new JcaX509CertificateConverter().getCertificate(builder  
	         .build(new JcaContentSignerBuilder("SHA256withRSA").setProvider("BC").  
	             build(intermedCAKeyPair.getPrivate())));// private key of signing authority , here it is signed by intermedCA  
	     saveToFile(endUserCert, ruta + "\\endUserCert.cer");  
	   }  
	   
	   private static KeyPair generateKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException {  
	     KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA", "BC");  
	     kpGen.initialize(2048, new SecureRandom());  
	     return kpGen.generateKeyPair();  
	   }  
	   
	   private static void saveToFile(X509Certificate certificate, String filePath) throws IOException, CertificateEncodingException {  
	     FileOutputStream fileOutputStream = new FileOutputStream(filePath);  
	     fileOutputStream.write(certificate.getEncoded());  
	     fileOutputStream.flush();  
	     fileOutputStream.close();  
	   }  

}
