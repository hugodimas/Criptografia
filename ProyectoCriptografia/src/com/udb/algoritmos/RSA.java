package com.udb.algoritmos;

import java.math.BigInteger;
import java.util.*;
import java.io.*;


public class RSA {

    int tamPrimo;
    BigInteger n, q, p;
    BigInteger totient;
    BigInteger e, d;

   
    public RSA(int tamPrimo) {
        this.tamPrimo = tamPrimo;
    }
    
    public void generaPrimos()
    {
        p = new BigInteger(tamPrimo, 10, new Random());
        do q = new BigInteger(tamPrimo, 10, new Random());
            while(q.compareTo(p)==0);
    }
    
    public void generaClaves()
    {
        // n = p * q
        n = p.multiply(q);
        totient = p.subtract(BigInteger.valueOf(1));
        totient = totient.multiply(q.subtract(BigInteger.valueOf(1)));
        
        do e = new BigInteger(2 * tamPrimo, new Random());
            while((e.compareTo(totient) != -1) || (e.gcd(totient).compareTo(BigInteger.valueOf(1)) != 0));
        
        d = e.modInverse(totient);
    }
    
    
    public BigInteger[] encripta(String mensaje)
    {
        int i;
        byte[] temp = new byte[1];
        byte[] digitos = mensaje.getBytes();
        BigInteger[] bigdigitos = new BigInteger[digitos.length];
        
        for(i=0; i<bigdigitos.length;i++){
            temp[0] = digitos[i];
            bigdigitos[i] = new BigInteger(temp);
        }
        
        BigInteger[] encriptado = new BigInteger[bigdigitos.length];
        
        for(i=0; i<bigdigitos.length; i++)
            encriptado[i] = bigdigitos[i].modPow(e,n);
        
        return(encriptado);
    }
    
    
    public String desencripta(BigInteger[] encriptado) {
        BigInteger[] desencriptado = new BigInteger[encriptado.length];
        
        for(int i=0; i<desencriptado.length; i++)
            desencriptado[i] = encriptado[i].modPow(d,n);
        
        char[] charArray = new char[desencriptado.length];
        
        for(int i=0; i<charArray.length; i++)
            charArray[i] = (char) (desencriptado[i].intValue());
        
        return(new String(charArray));
    }
    
    public BigInteger damep() {return(p);}
    public BigInteger dameq() {return(q);}
    public BigInteger dametotient() {return(totient);}
    public BigInteger damen() {return(n);}
    public BigInteger damee() {return(e);}
    public BigInteger damed() {return(d);}
}