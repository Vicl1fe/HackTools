package com.darkerbox.utils;

import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EncUtils {
    public static byte[] b64encode(byte[] plain){
        return Base64.getEncoder().encode(plain);
    }

    public static byte[] b64decode(String plain){
        return Base64.getDecoder().decode(plain);
    }

    public static String urlEncode(String body){
        return URLEncoder.encode(body);
    }

    public static String urlDecode(String body){
        return URLDecoder.decode(body);
    }

    public static int randomInt(int max){
        return new Random().nextInt(max);
    }

    public static String reCompile(String compile,String body) throws Exception{
        try {
            String result = null;
            Pattern pattern = Pattern.compile(compile);
            Matcher matcher = pattern.matcher(body);
            if(matcher.find()){
                result = new String(EncUtils.b64decode(matcher.group(1)));
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public static String ranndonClassname(int length){
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return randomString(2,str) + randomString(length-2,null);
    }
    public static String randomString(int length){
        return randomString(length,null);
    }
    public static String randomString(int length,String str){
        if(str ==null){
            str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        }
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length());
            stringBuffer.append(str.charAt(number));
        }
        return stringBuffer.toString();
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

}


