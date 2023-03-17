package com.darkerbox.hacktools.Encrypt;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

// 命令行解密：java -cp jasypt-1.9.2.jar org.jasypt.intf.cli.JasyptPBEStringDecryptionCLI input="jEGDEVcXT1FnzzvJmkXh13xfVVN3VgpI" password=82e1009B2439836D algorithm=PBEWithMD5AndDES
public class JasyptUtils {

    /*
    * algorithm 加密方式
    * key 密钥
    * password 解密的密码
    * */
    public static String decrypt(String algorithm,String key,String password){
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(key);
        encryptor.setAlgorithm(algorithm);
        String deCode = encryptor.decrypt(password);
        return deCode;


    }

}
