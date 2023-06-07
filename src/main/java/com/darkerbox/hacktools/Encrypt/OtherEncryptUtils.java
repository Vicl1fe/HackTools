package com.darkerbox.hacktools.Encrypt;

import com.darkerbox.utils.CommonUtils;
import org.apache.commons.lang3.RandomUtils;

import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class OtherEncryptUtils {
//    public static String  commonfileupload_encode(String filename) throws Exception{
//        String result = "";
//        result += "[---------------Base64---------------]\n";
//        // base64编码 只要不是中文，utf-8和gbk编码都是一样的结果
//        byte[] b = CommonUtils.b64encode(filename.getBytes("Utf-8"));
//        result += String.format("=?utf-8?B?%s?=",new String(b));
//        result += "\n";
//        result += "\t\n\r";
//        result += "[----------Quoted-Printable----------]\n";
//        String temp = "";
//        for (int i = 0; i < filename.length(); i++) {
//            char a = filename.charAt(i);
//            temp += "="+Integer.toHexString(Integer.valueOf(a));
//        }
//        result += String.format("=?gbk?Q?%s?=",temp);
//        result += "\n";
//
//        return result;
//    }

    public static String  commonfileupload_base64encode(String filename) throws Exception{
        // base64编码 只要不是中文，utf-8和gbk编码都是一样的结果
        byte[] b = CommonUtils.b64encode(filename.getBytes("Utf-8"));
        return String.format("=?utf-8?B?%s?=",new String(b));
    }

    public static String  commonfileupload_qpencode(String filename) throws Exception{
        String temp = "";
        for (int i = 0; i < filename.length(); i++) {
            char a = filename.charAt(i);
            temp += "="+Integer.toHexString(Integer.valueOf(a));
        }
        return String.format("=?gbk?Q?%s?=",temp);
    }



    public static String commonfileupload_random(int len) throws Exception{
        // 1.单独\r\n是不会成功解析的，在后面加个\t就可以解析了
        // 2.\n\r是可以成功解析的
        // 3.\r\n\r\t是不会成功解析的
        // 为了方便，直接去除\n
        String specialChar = "\r\t ";
        char[] chars = (char[])Array.newInstance(char.class,len);

        for (int i = 0; i < len; i++) {
            chars[i] = specialChar.charAt(RandomUtils.nextInt(0,specialChar.length()));
        }

        return String.valueOf(chars);
    }

    public static void main(String[] args) throws Exception{

    }

}
