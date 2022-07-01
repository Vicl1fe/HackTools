package com.darkerbox.hacktools.Encrypt;

import burp.IBurpExtenderCallbacks;
import com.darkerbox.hacktools.UIHandler;
import com.darkerbox.utils.AesUtil;
import com.darkerbox.utils.DesUtil;
import com.darkerbox.utils.EncUtils;
import com.darkerbox.utils.StrUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.jdesktop.swingx.prompt.PromptSupport;

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


	public final String[] encryptType = new String[]{
			"MD5",
			"AES/ECB/PKCS5Padding",
			"AES/CBC/PKCS5Padding",
			"DES/ECB/PKCS5Padding"
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

		// 设置自动换行
		inputTextarea.setLineWrap(true);
		// 设置大小
		Dimension preferredSize = new Dimension(1000,350);
		inputTextarea.setPreferredSize(preferredSize);
//		inputTextarea.setSize(10,10);
//		StringBuilder text = new StringBuilder();
//		text.append("1. 解密时默认加密数据为Base64");
//		text.append("1. 解密时默认输出数据为Base64编码");
//		PromptSupport.setPrompt(text.toString(),inputTextarea);
		return inputTextarea;
	}

	public JTextArea getOutputJTextArea() {
		JTextArea outputTextarea = new JTextArea();

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
		Dimension preferredSize = new Dimension(200,30);
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

		textField.setDisabledTextColor(Color.BLACK);

		return textField;
	}
	// 加密方式下拉列表
	public JComboBox getEncryptJComboBox(){
		JComboBox comboBox =new JComboBox();

		// 设置大小
		Dimension preferredSize = new Dimension(180,30);
		comboBox.setPreferredSize(preferredSize);
//		comboBox.setSize(10,10);

		comboBox.addItem(encryptType[0]);
		// 初始化的时候是MD5,禁用iv和key框
		jTextFieldOne.setEnabled(false);
		jTextFieldTwo.setEnabled(false);
		jDecbutton.setEnabled(false);
		comboBox.addItem(encryptType[1]);
		comboBox.addItem(encryptType[2]);
		comboBox.addItem(encryptType[3]);

		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				int index = comboBox.getSelectedIndex();
				// 获取当前选中的字符串
				String option = comboBox.getSelectedItem().toString();

				try {
					switch (option){
						case "MD5":
							jTextFieldOne.setEnabled(false);
							jTextFieldTwo.setEnabled(false);
							jDecbutton.setEnabled(false);
							break;
						case "AES/ECB/PKCS5Padding":
							jTextFieldOne.setEnabled(true);
							jTextFieldTwo.setEnabled(false);
							jDecbutton.setEnabled(true);
							PromptSupport.setPrompt("密钥",jTextFieldOne);
							break;
						case "AES/CBC/PKCS5Padding":
							jTextFieldOne.setEnabled(true);
							jTextFieldTwo.setEnabled(true);
							jDecbutton.setEnabled(true);
							PromptSupport.setPrompt("密钥",jTextFieldOne);
							PromptSupport.setPrompt("IV",jTextFieldTwo);
							break;
						case "DES/ECB/PKCS5Padding":
							jTextFieldOne.setEnabled(true);
							jTextFieldTwo.setEnabled(false);
							jDecbutton.setEnabled(true);
							PromptSupport.setPrompt("密钥",jTextFieldOne);
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
				String option = jEncryptComboBox.getSelectedItem().toString();
				if (!inputJTextArea.getText().equals("")){
					try {
						byte[] data;
						byte[] key;
						String result = null;
						switch (option){
							case "MD5":
								if (!inputJTextArea.getText().equals("")){
									outputJTextarea.setText(EncUtils.md5(inputJTextArea.getText()));
								}
								break;
							case "AES/ECB/PKCS5Padding":
								AesUtil.AES_ECB_PADDING = "AES/ECB/PKCS5Padding";
								data = inputJTextArea.getText().trim().getBytes();
								// 判断key的加密方式是文本还是base64编码的
								key = jEncodeComboBox.getSelectedItem().toString().equals("BASE64")?EncUtils.b64decode(jTextFieldOne.getText().trim()):jTextFieldOne.getText().trim().getBytes();
								result = new String(EncUtils.b64encode(AesUtil.encryptByECB(data,key)));
								outputJTextarea.setText(result);
								break;
							case "AES/CBC/PKCS5Padding":
								AesUtil.AES_ECB_PADDING = "AES/ECB/PKCS5Padding";
								data = inputJTextArea.getText().trim().getBytes();
								// 判断key的加密方式是文本还是base64编码的
								key = jEncodeComboBox.getSelectedItem().toString().equals("BASE64")?EncUtils.b64decode(jTextFieldOne.getText().trim()):jTextFieldOne.getText().trim().getBytes();
								byte[] IV = jTextFieldTwo.getText().getBytes();
								result = new String(EncUtils.b64encode(AesUtil.encryptByCBC(data,key,IV)));

								break;
							case "DES/ECB/PKCS5Padding":
								data = inputJTextArea.getText().trim().getBytes();
								// 判断key的加密方式是文本还是base64编码的
								key = jEncodeComboBox.getSelectedItem().toString().equals("BASE64")?EncUtils.b64decode(jTextFieldOne.getText().trim()):jTextFieldOne.getText().trim().getBytes();

								result = new String(EncUtils.b64encode(DesUtil.encrypt(data,key)));
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
				String option = jEncryptComboBox.getSelectedItem().toString();
				if (!inputJTextArea.getText().equals("")){
					try {
						byte[] data;
						byte[] key;
						String result = null;
						switch (option){
							case "MD5":
								break;
							case "AES/ECB/PKCS5Padding":
								AesUtil.AES_ECB_PADDING = "AES/ECB/PKCS5Padding";
								data = EncUtils.b64decode(inputJTextArea.getText().trim());
								// 判断key的加密方式是文本还是base64编码的
								key = jEncodeComboBox.getSelectedItem().toString().equals("BASE64")?EncUtils.b64decode(jTextFieldOne.getText().trim()):jTextFieldOne.getText().trim().getBytes();
								result = new String(AesUtil.decryptByECB(data,key));
								break;
							case "AES/CBC/PKCS5Padding":
								AesUtil.AES_ECB_PADDING = "AES/ECB/PKCS5Padding";
								data = EncUtils.b64decode(inputJTextArea.getText().trim());
								// 判断key的加密方式是文本还是base64编码的
								key = jEncodeComboBox.getSelectedItem().toString().equals("BASE64")?EncUtils.b64decode(jTextFieldOne.getText().trim()):jTextFieldOne.getText().trim().getBytes();
								byte[] IV = jTextFieldTwo.getText().getBytes();
								result = new String(AesUtil.decryptByCBC(data,key,IV));

								break;
							case "DES/ECB/PKCS5Padding":
								data = EncUtils.b64decode(inputJTextArea.getText().trim());
								// 判断key的加密方式是文本还是base64编码的
								key = jEncodeComboBox.getSelectedItem().toString().equals("BASE64")?EncUtils.b64decode(jTextFieldOne.getText().trim()):jTextFieldOne.getText().trim().getBytes();

								result = new String(DesUtil.decrypt(data,key));
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
}
