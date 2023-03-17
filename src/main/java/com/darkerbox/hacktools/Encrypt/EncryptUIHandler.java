package com.darkerbox.hacktools.Encrypt;

import burp.IBurpExtenderCallbacks;
import com.alibaba.druid.filter.config.ConfigTools;
import com.darkerbox.hacktools.UIHandler;
import com.darkerbox.utils.AesUtils;
import com.darkerbox.utils.DesUtils;
import com.darkerbox.utils.CommonUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;

import com.darkerbox.utils.UiUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class EncryptUIHandler implements UIHandler {
	private String tabname = "加解密";
	public IBurpExtenderCallbacks callbacks;

	public JPanel mainPanel;
	public JTextArea inputJTextArea;
	public JTextArea outputJTextarea;
	public JTextField jTextFieldOne;
	public JTextField jTextFieldTwo;
	public JComboBox jEncryptComboBox;
	public JComboBox jEncodeComboBox;
	public JButton jEncbutton;
	public JButton jDecbutton;
	public JLabel jLabel1;
	public JLabel jLabel2;
	public JRadioButton jsaveFileRadioButton;

	public Color textFieldEnableBorderColor = Color.blue;
	public Color textFieldDisableBorderColor = Color.gray;


	public final String[] encryptType = new String[]{
			"MD5",
			"Weblogic",
			"Druid",
			"Jasypt",
			"FindReport",
			"Unicode_CN",
			"BASE64",
			"BASE32",
			"Xshell",
			"Godzilla",
			"AES/ECB/PKCS5Padding",
			"AES/CBC/PKCS5Padding",
			"DES/ECB/PKCS5Padding",
	};

	@Override
	public void init() {

	}

	@Override
	public JPanel getPanel(IBurpExtenderCallbacks callbacks) {
		this.callbacks = callbacks;

		mainPanel = new JPanel();


		mainPanel.setAlignmentX(0.0f);

		GridBagLayout gridBagLayout = new GridBagLayout();
		//设置了总共有2列
		gridBagLayout.columnWidths = new int[]{0,0,0,0,0};
		//设置了总共有2行
		gridBagLayout.rowHeights = new int[]{0,0,0,0};
		//设置了列的宽度，貌似没用
		gridBagLayout.columnWeights = new double[]{0.01,0.01,0.1,0.1,0.6};
		//第一行的高度占0.5，第二行占0.5
		gridBagLayout.rowWeights = new double[]{0.4,0.1,0.1,0.4};
		mainPanel.setLayout(gridBagLayout);

		inputJTextArea = getInputJTextArea();
		outputJTextarea = getOutputJTextArea();
		jTextFieldOne = getTextFieldOne();
		jTextFieldTwo = getTextFieldTwo();
		jLabel1 = getLabel1();
		jLabel2 = getLabel2();
		jEncbutton = getEncryptButton();
		jDecbutton = getDecryptButton();
		jEncryptComboBox = getEncryptJComboBox();
		jEncodeComboBox = getEncodeJComboBox();
		jsaveFileRadioButton = getSaveFileRadioButton();

		GridBagConstraints constraints=new GridBagConstraints();
		// BOTH使组件完全填充该区域
		constraints.fill = GridBagConstraints.BOTH;
		// 组件起始x坐标
		constraints.gridx = 0;
		// 组件起始y坐标
		constraints.gridy = 0;
		// 组件宽占用的1个格子
		constraints.gridwidth = 5;
		// 组件高占用的2个格子
		constraints.gridheight = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
//		constraints.insets = new Insets(0, 0, 5, 5);
		mainPanel.add(new JScrollPane(inputJTextArea),constraints);


		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
//		constraints.insets = new Insets(0, 0, 5, 5);
		mainPanel.add(jLabel1,constraints);

		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
//		constraints.insets = new Insets(0, 0, 5, 5);
		mainPanel.add(jEncryptComboBox,constraints);
//
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 2;
		constraints.weightx = 0;
		constraints.weighty = 0;
//		constraints.insets = new Insets(0, 0, 5, 5);
		mainPanel.add(jEncbutton,constraints);
//
		constraints.gridx = 3;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 2;
		constraints.weightx = 0;
		constraints.weighty = 0;
//		constraints.insets = new Insets(0, 0, 5, 5);
		mainPanel.add(jDecbutton,constraints);

		constraints.gridx = 4;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.insets = new Insets(0, 0, 0, 300);
		mainPanel.add(jTextFieldOne,constraints);
		constraints.insets = new Insets(0, 0, 0, 0);

		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
//		constraints.insets = new Insets(0, 0, 5, 5);
		mainPanel.add(jLabel2,constraints);

		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
//		constraints.insets = new Insets(0, 0, 5, 5);
		mainPanel.add(jEncodeComboBox,constraints);


		constraints.gridx = 4;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;

		constraints.insets = new Insets(0, 0, 0, 300);
		mainPanel.add(jTextFieldTwo,constraints);
		constraints.insets = new Insets(0, 0, 0, 0);

		constraints.gridx = 4;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
//
		constraints.insets = new Insets(0, 800, 0, 0);
		mainPanel.add(jsaveFileRadioButton,constraints);
		constraints.insets = new Insets(0, 0, 0, 0);

		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 6;
		constraints.gridheight = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
//		constraints.insets = new Insets(0, 0, 5, 5);
		mainPanel.add(new JScrollPane(outputJTextarea),constraints);




		return mainPanel;
	}

	@Override
	public String getTabName() {
		return this.tabname;
	}

	public JTextArea getInputJTextArea() {
		JTextArea inputTextarea = new JTextArea();

		// 设置字体大小
		inputTextarea.setFont(UiUtils.getSerifFont());
		// 设置自动换行
		inputTextarea.setLineWrap(true);
		// 设置大小
		Dimension preferredSize = new Dimension(1000,350);
		inputTextarea.setPreferredSize(preferredSize);
//		inputTextarea.setSize(10,10);
//		StringBuilder text = new StringBuilder();
//		text.append("1. 解密时默认加密数据为Base64");
//		text.append("1. 解密时默认输出数据为Base64编码");
//		UiUtils.setPrompt(text.toString(),inputTextarea);
		return inputTextarea;
	}

	public JTextArea getOutputJTextArea() {
		JTextArea outputTextarea = new JTextArea();

		// 设置字体大小
		outputTextarea.setFont(UiUtils.getSerifFont());
		// 设置自动换行
		outputTextarea.setLineWrap(true);
		// 设置大小
		Dimension preferredSize = new Dimension(1000,350);
		outputTextarea.setPreferredSize(preferredSize);
//		outputTextarea.setSize(10,10);
		return outputTextarea;
	}


	public JTextField getTextFieldOne() {
		JTextField textField = new JTextField();

		// 设置大小
		Dimension preferredSize = new Dimension(150,30);
		textField.setPreferredSize(preferredSize);
		textField.setSize(10,10);

		return textField;
	}

	public JTextField getTextFieldTwo() {
		JTextField textField = new JTextField();

		// 设置大小
		Dimension preferredSize = new Dimension(200,30);
		textField.setPreferredSize(preferredSize);
		textField.setSize(10,10);

//		textField.setDisabledTextColor(Color.BLACK);
		// 设置边框颜色
//		textField.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		return textField;
	}
	// 加密方式下拉列表
	public JComboBox getEncryptJComboBox(){
		JComboBox comboBox =new JComboBox();

		// 设置大小
		Dimension preferredSize = new Dimension(180,30);
		comboBox.setPreferredSize(preferredSize);
//		comboBox.setSize(10,10);

		// 初始化的时候是MD5,禁用iv和key框
		jTextFieldOne.setEnabled(false);
		jTextFieldTwo.setEnabled(false);
		jDecbutton.setEnabled(false);

//		for (int i = 0; i < encryptType.length; i++) {
//			comboBox.addItem(encryptType[i]);
//		}
		for (EncType encType: EncType.values()){
			comboBox.addItem(encType.toString());
		}


		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				int index = comboBox.getSelectedIndex();
				// 获取当前选中的字符串
//				String option = comboBox.getSelectedItem().toString();
				// 转换为枚举类型
				EncType option = EncType.valueOf(comboBox.getSelectedItem().toString());

				try {
					switch (option){
						case Md5:
							jTextFieldOne.setText("");
							jTextFieldTwo.setText("");
							jTextFieldOne.setEnabled(false);
							jTextFieldTwo.setEnabled(false);
							jDecbutton.setEnabled(false);
							jEncbutton.setEnabled(true);
//							jTextFieldOne.setBorder(javax.swing.BorderFactory.createLineBorder(textFieldDisableBorderColor));
//							jTextFieldTwo.setBorder(javax.swing.BorderFactory.createLineBorder(textFieldDisableBorderColor));


							UiUtils.setPrompt("",jTextFieldOne);
							UiUtils.setPrompt("",jTextFieldTwo);
							
							break;
						case Weblogic:
							jTextFieldTwo.setText("");
							jTextFieldOne.setEnabled(true);
							jTextFieldTwo.setEnabled(false);
							jEncbutton.setEnabled(false);
							jDecbutton.setEnabled(true);
							UiUtils.setPrompt(" DAT文件绝对路径",jTextFieldOne);
							UiUtils.setPrompt("",jTextFieldTwo);

							break;
						case BASE64:
							jTextFieldOne.setEnabled(false);
							jTextFieldTwo.setEnabled(false);
							jEncbutton.setEnabled(true);
							jDecbutton.setEnabled(true);

							break;
						case BASE32:
							jTextFieldOne.setEnabled(false);
							jTextFieldTwo.setEnabled(false);
							jEncbutton.setEnabled(true);
							jDecbutton.setEnabled(true);

							break;
						case Unicode_CN:
							jTextFieldOne.setEnabled(false);
							jTextFieldTwo.setEnabled(false);
							jEncbutton.setEnabled(true);
							jDecbutton.setEnabled(true);

							break;
						case Druid:
							jTextFieldTwo.setText("");
							jTextFieldOne.setEnabled(true);
							jTextFieldTwo.setEnabled(false);
							jEncbutton.setEnabled(false);
							jDecbutton.setEnabled(true);
							UiUtils.setPrompt(" 这里写公钥，密文写上面的输入框中",jTextFieldOne);
							UiUtils.setPrompt("",jTextFieldTwo);

							break;

						case Jasypt:
							jTextFieldOne.setText("");
							jTextFieldTwo.setText("");
							jTextFieldOne.setEnabled(true);
							jTextFieldTwo.setEnabled(true);
							jEncbutton.setEnabled(false);
							jDecbutton.setEnabled(true);
							UiUtils.setPrompt(" 加密方式 eg: PBEWithMD5AndDES",jTextFieldOne);
							UiUtils.setPrompt(" 加密的密钥 eg: 82o2932DS43983AE",jTextFieldTwo);
							break;
						case Xshell:
							jTextFieldTwo.setText("");
							jTextFieldOne.setEnabled(true);
							jTextFieldTwo.setEnabled(true);
							jEncbutton.setEnabled(false);
							jDecbutton.setEnabled(true);
							UiUtils.setPrompt(" (上面的输入框无需填写)这里写xsh文件所在目录的绝对路径，不是文件路径",jTextFieldOne);
							UiUtils.setPrompt(" 格式：username:SID 例：Administrator:S-1-5-21-522326296-3075860275-115714520-500",jTextFieldTwo);

							break;
						case FindReport:
							jTextFieldTwo.setText("");
							jTextFieldOne.setEnabled(false);
							jTextFieldTwo.setEnabled(false);
							jEncbutton.setEnabled(false);
							jDecbutton.setEnabled(true);
							UiUtils.setPrompt("",jTextFieldOne);
							UiUtils.setPrompt("",jTextFieldTwo);

							break;
						case Godzilla:
							jTextFieldTwo.setText("");
							jTextFieldOne.setEnabled(true);
							jTextFieldTwo.setEnabled(false);
							jEncbutton.setEnabled(false);
							jDecbutton.setEnabled(true);
							UiUtils.setPrompt(" key的MD5值前16位",jTextFieldOne);
							UiUtils.setPrompt("",jTextFieldTwo);
							break;
						case Landray:
							jTextFieldTwo.setText("");
							jTextFieldOne.setEnabled(true);
							jTextFieldTwo.setEnabled(false);
							jEncbutton.setEnabled(false);
							jDecbutton.setEnabled(true);
							jTextFieldOne.setText("kmssAdminKey");
							UiUtils.setPrompt(" kmssAdminKey",jTextFieldOne);
							UiUtils.setPrompt("",jTextFieldTwo);
							break;
						case Aes_ECB:
							jTextFieldOne.setText("");

							jTextFieldOne.setEnabled(true);
							jTextFieldTwo.setEnabled(false);
//							jTextFieldOne.setBorder(javax.swing.BorderFactory.createLineBorder(textFieldEnableBorderColor));
//							jTextFieldTwo.setBorder(javax.swing.BorderFactory.createLineBorder(textFieldDisableBorderColor));
							jDecbutton.setEnabled(true);
							jEncbutton.setEnabled(true);
							UiUtils.setPrompt(" 密钥",jTextFieldOne);
							UiUtils.setPrompt("",jTextFieldTwo);

							break;
						case Aes_CBC:
							jTextFieldOne.setEnabled(true);
							jTextFieldTwo.setEnabled(true);
//							jTextFieldOne.setBorder(javax.swing.BorderFactory.createLineBorder(textFieldEnableBorderColor));
//							jTextFieldTwo.setBorder(javax.swing.BorderFactory.createLineBorder(textFieldEnableBorderColor));
							jDecbutton.setEnabled(true);
							jEncbutton.setEnabled(true);
							UiUtils.setPrompt(" 密钥",jTextFieldOne);
							UiUtils.setPrompt(" IV",jTextFieldTwo);
							break;
						case Des_ECB:
							jTextFieldOne.setText("");

							jTextFieldOne.setEnabled(true);
							jTextFieldTwo.setEnabled(false);
							jDecbutton.setEnabled(true);
							jEncbutton.setEnabled(true);
//							jTextFieldOne.setBorder(javax.swing.BorderFactory.createLineBorder(textFieldEnableBorderColor));
//							jTextFieldTwo.setBorder(javax.swing.BorderFactory.createLineBorder(textFieldDisableBorderColor));
							UiUtils.setPrompt(" 密钥",jTextFieldOne);
							UiUtils.setPrompt("",jTextFieldTwo);
							break;
						default:
							break;
					}
				}catch (Exception p){
					p.printStackTrace();
					try {
						outputJTextarea.setText(p.toString());
						callbacks.getStderr().write(p.toString().getBytes());
					} catch (IOException ex) {
					}
				}
			}
		});

		return comboBox;
	}

	// 编码方式下拉列表
	public JComboBox getEncodeJComboBox(){
		JComboBox cmb =new JComboBox();

		// 设置大小
		Dimension preferredSize = new Dimension(100,30);
		cmb.setPreferredSize(preferredSize);
		cmb.setSize(10,10);

		cmb.addItem("BASE64");
		cmb.addItem("TEXT");
		return cmb;
	}

	// 加密按钮
	public JButton getEncryptButton(){
		JButton button = new JButton();
		button.setText("加密");
		button.setAlignmentX(0.0f);
		//设置按钮尺寸
//		Dimension preferredSize = new Dimension(100,50);
//		button.setPreferredSize(preferredSize);

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				EncType option = EncType.valueOf(jEncryptComboBox.getSelectedItem().toString());
				if (!inputJTextArea.getText().equals("")){
					try {
						byte[] data;
						byte[] key;
						String result = null;
						switch (option){
							case Md5:
								if (!inputJTextArea.getText().equals("")){
									result = CommonUtils.md5(inputJTextArea.getText());
								}
								break;
							case BASE64:

								result = new String(CommonUtils.b64encode(inputJTextArea.getText().getBytes()));
								break;
							case BASE32:

								result = new String(CommonUtils.b32encode(inputJTextArea.getText().getBytes()));
								break;

							case Unicode_CN:
								result = CommonUtils.Cn2Unicode(inputJTextArea.getText());
								break;
							case Aes_ECB:
								AesUtils.AES_ECB_PADDING = "AES/ECB/PKCS5Padding";
								data = inputJTextArea.getText().trim().getBytes();
								// 判断key的加密方式是文本还是base64编码的
								key = jEncodeComboBox.getSelectedItem().toString().equals("BASE64")? CommonUtils.b64decode(jTextFieldOne.getText().trim()):jTextFieldOne.getText().trim().getBytes();
								result = new String(CommonUtils.b64encode(AesUtils.encryptByECB(data,key)));
								outputJTextarea.setText(result);
								break;
							case Aes_CBC:
								AesUtils.AES_ECB_PADDING = "AES/ECB/PKCS5Padding";
								data = inputJTextArea.getText().trim().getBytes();
								// 判断key的加密方式是文本还是base64编码的
								key = jEncodeComboBox.getSelectedItem().toString().equals("BASE64")? CommonUtils.b64decode(jTextFieldOne.getText().trim()):jTextFieldOne.getText().trim().getBytes();
								byte[] IV = jTextFieldTwo.getText().getBytes();
								result = new String(CommonUtils.b64encode(AesUtils.encryptByCBC(data,key,IV)));

								break;
							case Des_ECB:
								data = inputJTextArea.getText().trim().getBytes();
								// 判断key的加密方式是文本还是base64编码的
								key = jEncodeComboBox.getSelectedItem().toString().equals("BASE64")? CommonUtils.b64decode(jTextFieldOne.getText().trim()):jTextFieldOne.getText().trim().getBytes();

								result = new String(CommonUtils.b64encode(DesUtils.encrypt(data,key)));
								break;
							default:
								break;
						}
						outputJTextarea.setText(result);
					}catch (Exception p){
						p.printStackTrace();
						try {
							outputJTextarea.setText(p.toString());
							callbacks.getStderr().write(p.toString().getBytes());
						} catch (IOException ex) {

						}
					}
				}
			}
		});

		return button;
	}

	// 解密按钮
	public JButton getDecryptButton(){
		JButton button = new JButton();
		button.setText("解密");
		button.setAlignmentX(0.0f);
		//设置按钮尺寸
//		Dimension preferredSize = new Dimension(100,50);
//		button.setPreferredSize(preferredSize);

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				EncType option = EncType.valueOf(jEncryptComboBox.getSelectedItem().toString());
				// Xshell的
				if (!inputJTextArea.getText().equals("") || option.equals(EncType.Xshell)){
					try {
						byte[] data;
						byte[] key;
						String result = null;
						switch (option){
							case Md5:
								break;
							case Weblogic:
								String datPath = jTextFieldOne.getText().trim();
								String encryptText = inputJTextArea.getText().trim();

								Security.addProvider((Provider)new BouncyCastleProvider());

								if (encryptText.startsWith("{AES}")) {
									encryptText = encryptText.replaceAll("^[{AES}]+", "");

									result = WeblogicDecrypt.decryptAES(datPath, encryptText);

								} else if (encryptText.startsWith("{3DES}")) {
									encryptText = encryptText.replaceAll("^[{3DES}]+", "");
									result = WeblogicDecrypt.decrypt3DES(datPath, encryptText);
								} else {
									result = "密文输入错误";
								}
								break;
							case Druid:
								String publickey = jTextFieldOne.getText().trim();

								result = ConfigTools.decrypt(publickey,inputJTextArea.getText().trim());
								break;
							case Jasypt:
								String algorithm = jTextFieldOne.getText().trim();
								String key2 = jTextFieldTwo.getText().trim();
								String encryptpassword = inputJTextArea.getText().replace("ENC(","").replace(")","");

								result = JasyptUtils.decrypt(algorithm,key2,encryptpassword);
								break;
							case BASE64:
								result = output(CommonUtils.b64decode(inputJTextArea.getText()));
								break;
							case BASE32:

								result = new String(CommonUtils.b32decode(inputJTextArea.getText().getBytes()));
								break;
							case Unicode_CN:
								result = CommonUtils.unicode2Cn(inputJTextArea.getText());
								break;
							case FindReport:
								// 密文
								String cipher = inputJTextArea.getText().trim();
								// 掩码
								int[] PASSWORD_MASK_ARRAY = new int[]{19, 78, 10, 15, 100, 213, 43, 23};
								String password = "";
								cipher = cipher.substring(3);
								for (int i = 0; i < cipher.length()/4; i++) {
									int c1 = Integer.parseInt(cipher.substring(i * 4,(i + 1) * 4),16);
									int c2 = c1 ^ PASSWORD_MASK_ARRAY[i%8];
									password = password + (char)c2;
								}
								result = password;
								break;
							case Xshell:
								String xshpath = jTextFieldOne.getText().trim();
								String temp = jTextFieldTwo.getText().trim();
								int op = temp.indexOf(":");

								String username = "";
								String sid = "";
								// 判断是否输入用户名
								if (op != -1){
									username = temp.substring(0,op);
									sid = temp.substring(op+1);
								}else{
									username = "";
									sid = temp;
								}

								result = "";
								XshellDecrypt xshellDecrypt = new XshellDecrypt(xshpath,username,sid,result);
								ArrayList<XshellResult> results = xshellDecrypt.Xdecrypt();
								result += xshellDecrypt.result;
								result += "[*] Your Input Username && SID\n";
								result += "    Username: "+username+"\n";
								result += "    Sid: "+sid+"\n";
								result += "[*] Start Decrypt.."+"\n";
								result += "\n";
								for (int i = 0; i < results.size(); i++) {
									result += "[+] XSHPath: "+results.get(i).getXshellpath()+"\n";
									result += "  Host: "+results.get(i).getHost()+":"+results.get(i).getPort()+"\n";
									result += "  Username: "+results.get(i).getUsername()+"\n";
									result += "  Password: "+results.get(i).getPassword()+"\n";
									result += "  Version: "+results.get(i).getVersion()+"\n";
									result += "\n\n";
								}
								result += "[*] Decrypt End"+"\n";
								result += "\n";
								result += "PS: 本地只测试过Xshell5，如果有其他问题，希望可以提ISSUES";
								break;
							case Godzilla:
								AesUtils.AES_ECB_PADDING = "AES/ECB/PKCS5Padding";
								String temp2  = inputJTextArea.getText().trim().substring(16);
								temp2 = temp2.substring(0,temp2.length()-16);
								data = CommonUtils.b64decode(temp2);
								// 判断key的加密方式是文本还是base64编码的
								key = jEncodeComboBox.getSelectedItem().toString().equals("BASE64")? CommonUtils.b64decode(jTextFieldOne.getText().trim()):jTextFieldOne.getText().trim().getBytes();
								AesUtils.decryptByECB(data,key);
								result = output(CommonUtils.gzipD(AesUtils.decryptByECB(data,key)));
//								result = new String(CommonUtils.gzipD(AesUtils.decryptByECB(data,key)));
								break;
							case Aes_ECB:
								AesUtils.AES_ECB_PADDING = "AES/ECB/PKCS5Padding";
								data = CommonUtils.b64decode(inputJTextArea.getText().trim());
								// 判断key的加密方式是文本还是base64编码的
								key = jEncodeComboBox.getSelectedItem().toString().equals("BASE64")? CommonUtils.b64decode(jTextFieldOne.getText().trim()):jTextFieldOne.getText().trim().getBytes();
								result = output(AesUtils.decryptByECB(data,key));
//								result = new String(AesUtils.decryptByECB(data,key));
								break;
							case Aes_CBC:
								AesUtils.AES_ECB_PADDING = "AES/ECB/PKCS5Padding";
								data = CommonUtils.b64decode(inputJTextArea.getText().trim());
								// 判断key的加密方式是文本还是base64编码的
								key = jEncodeComboBox.getSelectedItem().toString().equals("BASE64")? CommonUtils.b64decode(jTextFieldOne.getText().trim()):jTextFieldOne.getText().trim().getBytes();
								byte[] IV = jTextFieldTwo.getText().getBytes();

								result = new String(AesUtils.decryptByCBC(data,key,IV));

								break;
							case Landray:
							case Des_ECB:
								data = CommonUtils.b64decode(inputJTextArea.getText().trim());
								// 判断key的加密方式是文本还是base64编码的
								key = jEncodeComboBox.getSelectedItem().toString().equals("BASE64")? CommonUtils.b64decode(jTextFieldOne.getText().trim()):jTextFieldOne.getText().trim().getBytes();

								result = new String(DesUtils.decrypt(data,key));
								break;
							default:
								break;
						}

						outputJTextarea.setText(result);
					}catch (Exception p){
						p.printStackTrace();
						try {
							outputJTextarea.setText(p.toString());
							callbacks.getStderr().write(p.toString().getBytes());
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		});

		return button;
	}

	//  加密方式-label
	public JLabel getLabel1(){
		JLabel label = new JLabel();
		label.setText("加密方式：");
//		button.setText("解密");
//		button.setAlignmentX(0.0f);
		//设置按钮尺寸
//		Dimension preferredSize = new Dimension(100,50);
//		button.setPreferredSize(preferredSize);


		return label;
	}

	// 编码方式-label
	public JLabel getLabel2(){
		JLabel label = new JLabel();
		label.setText("密钥编码：");
//		button.setText("解密");
//		button.setAlignmentX(0.0f);
		//设置按钮尺寸
//		Dimension preferredSize = new Dimension(100,50);
//		button.setPreferredSize(preferredSize);


		return label;
	}

	// 保存到文件单选按钮
	public JRadioButton getSaveFileRadioButton(){
		JRadioButton radioBtn01 = new JRadioButton("将结果保存到文件");
		Dimension preferredSize = new Dimension(1,30);
		radioBtn01.setPreferredSize(preferredSize);
		radioBtn01.setSelected(false);
		return radioBtn01;
	}

	public void ErrorOuput(String t) throws Exception{
		callbacks.getStderr().write(t.getBytes());
	}

	public String output(byte[] result) throws Exception {
		if (jsaveFileRadioButton.isSelected()){
			JFileChooser fileChooser = new JFileChooser();
			int option = fileChooser.showSaveDialog(new Frame());
			if(option == JFileChooser.APPROVE_OPTION){
				File file = fileChooser.getSelectedFile();
				FileOutputStream outputStream =  new FileOutputStream(file);
				outputStream.write(result);
				outputStream.close();
			}
		}
		return new String(result);
	}


	public enum EncType
	{
		Md5,
		Weblogic,
		Druid,
		Jasypt,
		FindReport,
		Unicode_CN,
		BASE64,
		BASE32,
		Xshell,
		Godzilla,
		Aes_ECB,// AES/ECB/PKCS5Padding
		Aes_CBC,// AES/CBC/PKCS5Padding
		Des_ECB,// DES/ECB/PKCS5Padding
		Landray,
	}

	public static void main(String[] args) throws Exception {


	}
}
