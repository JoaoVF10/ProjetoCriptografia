package org.example;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HexFormat;

public class CriptoAssimetrica {

    //Gera um par de chaves (RSA)
    public static KeyPair geraRSAKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(3072);
        return keyPairGenerator.generateKeyPair();
    }


    public static byte[] encripta(String mensagem, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(mensagem.getBytes(StandardCharsets.UTF_8));
    }

    public static String decripta(byte[] mensagemCifrada, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] mensagemAberta = cipher.doFinal(mensagemCifrada);
        return new String(mensagemAberta);
    }

    public static void main(String[] args) throws Exception {

        KeyPair keyPair = geraRSAKeyPair();

        String mensagemOriginal = "Vamos criptografar essa frase e recuperar depois";

        System.out.println("A mensagem original é: " + mensagemOriginal);

        byte[] mensagemCifrada = encripta(mensagemOriginal, keyPair.getPublic());

        System.out.println("A mensagem cifrada é : " + HexFormat.of().formatHex(mensagemCifrada));

        String mensagemRecuperada = decripta(mensagemCifrada, keyPair.getPrivate());

        System.out.println("Chave privada: " + keyPair.getPrivate());

        System.out.println("A mensagem recuperada é: " + mensagemRecuperada);
    }
}
