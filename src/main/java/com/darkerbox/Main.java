package com.darkerbox;

import burp.IBurpExtenderCallbacks;
import burp.ITab;
import com.darkerbox.hacktools.UIHandler;
import com.darkerbox.hacktools.dfquery.DfqueryUIHandler;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;

public class Main implements ITab {
	public final String tabname = "HackTools";
	// 主Tab对象
	public JTabbedPane mainPanel;

	public static PrintWriter stdout;
	public static PrintWriter stderr;


	public List<String> init(){
		List<String> UiList = new ArrayList<String>();

		UiList.add("com.darkerbox.hacktools.Encrypt.EncryptUIHandler");
		UiList.add("com.darkerbox.hacktools.dfquery.DfqueryUIHandler");
		UiList.add("com.darkerbox.hacktools.texttools.TextToolsUIHandler");
		UiList.add("com.darkerbox.hacktools.java.JavaToolHandler");
		return UiList;
	}

	public void start(IBurpExtenderCallbacks callbacks){

//		System.out.println("-------列出加密服务提供者-----");
//		Provider[] pro=Security.getProviders();
//		for(Provider p:pro){
//			System.out.println("Provider:"+p.getName()+" - version:"+p.getVersion());
//			System.out.println(p.getInfo());
//		}
//		System.out.println("");
//		System.out.println("-------列出系统支持的消息摘要算法：");
//		for(String s:Security.getAlgorithms("MessageDigest")){
//			System.out.println(s);
//		}
//		System.out.println("-------列出系统支持的生成公钥和私钥对的算法：");
//		for(String s:Security.getAlgorithms("KeyPairGenerator")){
//			System.out.println(s);
//		}

		stdout = new PrintWriter(callbacks.getStdout(), true);
		stderr = new PrintWriter(callbacks.getStderr(), true);

		stdout.println("Anthor: Vicl1fe");


		try {
			InitUi(init(),callbacks);
		}catch (Exception e){
			stderr.println(e);
		}

	}

	// 初始化每个子Tab
	public void InitUi(List<String> uilist,IBurpExtenderCallbacks callbacks) throws Exception{
		mainPanel = new JTabbedPane();
		// 反射获取每个类，自动添加到主Tab中
		for (int i = 0; i < uilist.size(); i++) {
			Class a = Class.forName(uilist.get(i));
			UIHandler obj = (UIHandler)a.newInstance();

			mainPanel.addTab(obj.getTabName(),obj.getPanel(callbacks));
		}
		callbacks.addSuiteTab(Main.this);

	}

	// 设置主Tab名字
	@Override
	public String getTabCaption() {
		return this.tabname;
	}

	// 返回主Tab对象
	@Override
	public Component getUiComponent() {
		return this.mainPanel;
	}
}
