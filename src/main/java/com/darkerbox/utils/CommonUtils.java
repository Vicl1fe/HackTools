package com.darkerbox.utils;

import org.apache.commons.codec.binary.Hex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {
    public static byte[] b64encode(byte[] plain){
        return Base64.getEncoder().encode(plain);
    }

    public static byte[] b64decode(String plain){
        return Base64.getDecoder().decode(plain);
    }

    public static byte[] sha256(String str) throws Exception{
        MessageDigest messageDigest;

        messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hash = messageDigest.digest(str.getBytes());

        return hash;
    }

    public static String md5(String enc){
        byte[] digest = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            digest  = md5.digest(enc.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //16是表示转换为16进制数
        String md5Str = new BigInteger(1, digest).toString(16);
        return md5Str;
    }


    public static List<String> listAllFile(File f) {
        File[] files = f.listFiles();
        List<String> fileList = new ArrayList<String>();
        for (File file : files) {
            fileList.add(file.getName());
        }
        return fileList;

    }

    public static String getFileContent(String path) throws Exception{
        BufferedReader in = new BufferedReader(new FileReader(path));
        String str;
        StringBuilder temp = new StringBuilder();
        while ((str = in.readLine()) != null) {
            temp.append(str+"\n");
        }
        return temp.toString();
    }

    public static String reCompile(String compile,String body) throws Exception{
        try {
            String result = null;
            Pattern pattern = Pattern.compile(compile);
            Matcher matcher = pattern.matcher(body);
            if(matcher.find()){
                result = matcher.group(1);
                return result;
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}


