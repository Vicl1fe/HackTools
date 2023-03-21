package com.darkerbox.utils;

import hacktools.com.sun.org.apache.bcel.internale.classfile.Utility;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {

	public static String bcelEncode(byte[] data) throws Exception{
		return "$$BCEL$$"+ Utility.encode(data, true);
	}

	public static byte[] bcelDecode(String cdata) throws Exception{
		if(cdata.startsWith("$$BCEL$$")){
			cdata = cdata.substring(8);
		}
		byte[] array =  Utility.decode(cdata,true);
		return array;
	}

	public static String b64encode(byte[] data) throws Exception{
		return Base64.encodeBase64String(data);
	}

	public static byte[] b64decode(String data) throws Exception{
		return Base64.decodeBase64(data);
	}

	public static byte[] readAllBytes(String path) throws Exception{
		return Files.readAllBytes(Paths.get(path));
	}

}
