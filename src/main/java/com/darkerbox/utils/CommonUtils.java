package com.darkerbox.utils;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CommonUtils {
    public static byte[] b64encode(byte[] plain){
        return Base64.getEncoder().encode(plain);
    }

    public static byte[] b32encode(byte[] plain){
        return new Base32().encode(plain);
    }

    public static byte[] b32decode(byte[] plain){
        return new Base32().decode(plain);
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

    public static byte[] gzipD(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }


    /**
     * 将字符串转化成unicode码
     * @author shuai.ding
     * @param string
     * @return
     */
    public static String Cn2Unicode(String string) {

        if (StringUtils.isBlank(string)) {
            return null;
        }

        char[] bytes = string.toCharArray();
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            char c = bytes[i];

            // 标准ASCII范围内的字符，直接输出
            if (c >= 0 && c <= 127) {
                unicode.append(c);
                continue;
            }
            String hexString = Integer.toHexString(bytes[i]);

            unicode.append("\\u");

            // 不够四位进行补0操作
            if (hexString.length() < 4) {
                unicode.append("0000".substring(hexString.length(), 4));
            }
            unicode.append(hexString);
        }
        return unicode.toString();
    }


    /**
     * 将unicode码转化成字符串
     * @author shuai.ding
     * @param unicode
     * @return
     */
    public static String unicode2Cn(String unicode) {
        if (StringUtils.isBlank(unicode)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;

        while ((i = unicode.indexOf("\\u", pos)) != -1) {
            sb.append(unicode.substring(pos, i));
            if (i + 5 < unicode.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(unicode.substring(i + 2, i + 6), 16));
            }
        }
        //如果pos位置后，有非中文字符，直接添加
        sb.append(unicode.substring(pos));

        return sb.toString();
    }
}


