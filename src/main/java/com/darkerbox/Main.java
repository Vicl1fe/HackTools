package com.darkerbox;

import burp.IBurpExtenderCallbacks;
import burp.ITab;
import com.darkerbox.hacktools.UIHandler;
import com.darkerbox.hacktools.dfquery.DfqueryUIHandler;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class Main implements ITab {
	public final String tabname = "HackTools";
	// 主Tab对象
	public JTabbedPane mainPanel;

	public List<String> init(){
		List<String> UiList = new ArrayList<String>();
		UiList.add("com.darkerbox.hacktools.Encrypt.EncryptUIHandler");
		UiList.add("com.darkerbox.hacktools.dfquery.DfqueryUIHandler");
		UiList.add("com.darkerbox.hacktools.texttools.TextToolsUIHandler");
		return UiList;
	}

	public void start(IBurpExtenderCallbacks callbacks){

		PrintWriter stdout = new PrintWriter(callbacks.getStdout(), true);
		PrintWriter stderr = new PrintWriter(callbacks.getStderr(), true);

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
