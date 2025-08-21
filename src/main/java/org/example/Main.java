package org.example;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.KeyPair;
import java.util.Base64;

public class Main {
    public static void main(String[] args) throws Exception {


        // Aqui a Escola irá gerar a chave para que depois os pais consigam acessar o boletim
        SecretKey chaveAES = CriptoSimetrica.geraChave();
        IvParameterSpec iv = CriptoSimetrica.geraIv();

        //Escola tem a chave privada
        KeyPair parChavesEscola = AssinaturaDigital.geraRSAKeyPair();

        // Pais têm a chave pública
        KeyPair parChavesPais = CriptoAssimetrica.geraRSAKeyPair();


        String boletim = "Nota de Thalita: 10\nNota de João: 10\n Nota de Matheus: 10";
        System.out.println("📄 Boletim :\n" + boletim);


        // Boletim passa pelo processamento de encriptografia
        byte[] boletimCriptografado = CriptoSimetrica.encripta(boletim, chaveAES, iv);


        //criptografa a chave AES e a chave dos Pais
        byte[] chaveAESCriptografada = CriptoAssimetrica.encripta(
                Base64.getEncoder().encodeToString(chaveAES.getEncoded()),
                parChavesPais.getPublic()
        );


        // Assina digitalmente o boletim
        String assinaturaBase64 = AssinaturaDigital.assina(boletimCriptografado, parChavesEscola.getPrivate());


        // Simulando os dados para envio
        System.out.println("\n📦 Enviando boletim aos responsáveis...\n");

        System.out.println("🔐 Boletim criptografado com AES:");
        System.out.println(Base64.getEncoder().encodeToString(boletimCriptografado));

        System.out.println("\n🔒 Chave AES criptografada com a chave pública dos pais:");
        System.out.println(Base64.getEncoder().encodeToString(chaveAESCriptografada));

        System.out.println("\n✍️ Assinatura digital feita com a chave privada da escola:");
        System.out.println(assinaturaBase64);

    }
}