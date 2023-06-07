package com.darkerbox.utils;

import com.darkerbox.asm.ShortClassVisitor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import hacktools.com.sun.org.apache.bcel.internale.classfile.Utility;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.objectweb.asm.*;

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


	public static HashMap<String,Object> str2map(String str){
		return new Gson().fromJson(str, new TypeToken<HashMap<String,Object>>(){}.getType());
	}

	public static byte[] gzipE(byte[] bytes) {
		if (bytes.length==0) {
			return null;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(bytes);
			gzip.close();
		} catch ( Exception e) {
			e.printStackTrace();
		}
		return out.toByteArray();
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
			return null;
		}
		return out.toByteArray();
	}


	public static byte[] smallClassFile(byte[] classBytes){
		ClassReader cr = new ClassReader(classBytes);
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		int api = Opcodes.ASM9;
		ClassVisitor cv = new ShortClassVisitor(api, cw);
		int parsingOptions = ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES;
		cr.accept(cv, parsingOptions);
		classBytes = cw.toByteArray();
		return classBytes;
	}

	public static void main(String[] args) throws Exception {
		String a = java.util.Base64.getEncoder().encodeToString(gzipE(Files.readAllBytes(Paths.get("/opt/WebAttack/8 Webshell/JavaAgent/SpringMemShell.jar"))));
		System.out.println(a);
	}

	
}