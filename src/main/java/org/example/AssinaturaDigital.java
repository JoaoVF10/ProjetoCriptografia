package org.example;

import java.security.*;
import java.util.Base64;

public class AssinaturaDigital {

    public static KeyPair geraRSAKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(3072);
        return keyPairGenerator.generateKeyPair();
    }

    public static String assina(byte[] dados, PrivateKey chavePrivada) throws Exception {
        Signature assinatura = Signature.getInstance("SHA256withRSA");
        assinatura.initSign(chavePrivada);
        assinatura.update(dados);
        byte[] assinaturaBytes = assinatura.sign();
        return Base64.getEncoder().encodeToString(assinaturaBytes);
    }

    public static boolean verificaSHA256RSA(byte[] dados, String assinaturaBase64, PublicKey chavePublica) throws Exception {
        Signature assinatura = Signature.getInstance("SHA256withRSA");
        assinatura.initVerify(chavePublica);
        assinatura.update(dados);
        byte[] assinaturaBytes = Base64.getDecoder().decode(assinaturaBase64);
        return assinatura.verify(assinaturaBytes);
    }

    public static void main(String[] args) throws Exception {

        KeyPair parChaves = geraRSAKeyPair();
        KeyPair parChaves2 = geraRSAKeyPair();

        String mensagem = "Dados confidenciais";

        String assinaturaBase64 = assina(mensagem.getBytes(), parChaves.getPrivate());
        System.out.println("Assinatura: " + assinaturaBase64);

//        boolean valida = verificaSHA256RSA(mensagem.getBytes(), assinaturaBase64, parChaves.getPublic());
        boolean valida2 = verificaSHA256RSA(mensagem.getBytes(), assinaturaBase64, parChaves2.getPublic());

        System.out.println("Assinatura válida? " + valida2);


    }

}
