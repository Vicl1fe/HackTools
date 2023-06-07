package com.darkerbox.hacktools.java;

import burp.IBurpExtenderCallbacks;
import com.darkerbox.hacktools.Encrypt.EncryptUIHandler;
import com.darkerbox.hacktools.UIHandler;
import com.darkerbox.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


// TODO 1.反序列化文件脏数据生成。2:bcel文件编码、解码
public class JavaToolHandler implements UIHandler {
	private String tabname = "JavaUtils";
	private JPanel mainPanel;

	private IBurpExtenderCallbacks callbacks;

	private JTextArea inputJTextArea;
	private JTextArea outputTextarea;
	
	private JComboBox encodeComboBox;

	private JTextField jTextFieldOne;

	public final String[] encodeType = new String[]{
			"BCEL",
			"Gzip",
			"SmallCLassFile",
	};
	@Override
	public void init() {

	}

	public JComboBox getEncodeJComboBox(){
		JComboBox comboBox =new JComboBox();

		// 设置大小
		Dimension preferredSize = new Dimension(50,30);
		comboBox.setPreferredSize(preferredSize);
//		comboBox.setSize(10,10);
		

//		for (int i = 0; i < encryptType.length; i++) {
//			comboBox.addItem(encryptType[i]);
//		}
		for (JavaToolHandler.EncType encType: JavaToolHandler.EncType.values()){
			comboBox.addItem(encType.toString());
		}

		return comboBox;
	}
	
	@Override
	public JPanel getPanel(IBurpExtenderCallbacks callbacks) {

		this.callbacks = callbacks;
		GridLayout layout = new GridLayout(2, 1);
		mainPanel = new JPanel(layout);

		// 输出窗口
		outputTextarea = getOututTextarea();
		// TabPane 功能窗口
		JTabbedPane jTabbedPane = getJTabbedPane();

		// 编码解码功能
		EncodeUIHandler(jTabbedPane);
		// 序列化文件脏数据生成功能
		serDirtyDataUIHandler(jTabbedPane);

		mainPanel.add(jTabbedPane);
		mainPanel.add(new JScrollPane(outputTextarea));

		return mainPanel;
	}

	public JTabbedPane getJTabbedPane(){
		JTabbedPane jTabbedPane = new JTabbedPane();
		return jTabbedPane;

	}

	public JTextArea getOututTextarea(){
		JTextArea outputTextarea = new JTextArea();
		outputTextarea.setLineWrap(true);
		outputTextarea.setFont(UiUtils.getSerifFont());
		return outputTextarea;
	}

	public void serDirtyDataUIHandler(JTabbedPane jTabbedPane){

		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[]{0,0,0};
		layout.rowHeights = new int[]{0,0};
		//设置了列的宽度，貌似没用
		layout.columnWeights = new double[]{0.3,0.4,0.3};
		//第一行的高度占0.5，第二行占0.5
		layout.rowWeights = new double[]{0.1,0.9};

//		GridLayout layout = new GridLayout(1, 3);
		Panel panel = new Panel(layout);

		JButton inputfilebutton = new JButton("选择序列化文件");



		JTextField field = new JTextField();
		UiUtils.setPrompt("垃圾数据长度(只能输入数字)",field);
		Font x = UiUtils.getSerifFont();
		field.setFont(x);
		JLabel label = new JLabel();
		label.setFont(x);


		JButton submitbutton = new JButton("提交");
		submitbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String filepath = getSerDirtyData(new File(label.getText()),field.getText());
				outputTextarea.append("结果保存到："+filepath);
			}
		});

		inputfilebutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(panel);
				if (result == JFileChooser.APPROVE_OPTION) {
					// 如果点击了"确定", 则获取选择的文件路径
					File file = fileChooser.getSelectedFile();
//						outputTextarea.append(String.valueOf(Files.readAllBytes(Paths.get(file.getAbsolutePath())).length));
//					outputTextarea.append("打开文件: " + file.getAbsolutePath() + "\n\n");
					label.setText(file.getAbsolutePath());

				}
			}
		});

		GridBagConstraints constraints=new GridBagConstraints();
		// BOTH使组件完全填充该区域
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 0, 0, 0);
		
		constraints.gridx = 0;
		
		constraints.gridy = 0;
		
		constraints.gridwidth = 1;
		
		constraints.gridheight = 2;
		constraints.weightx = 0;
		constraints.weighty = 0;
		panel.add(inputfilebutton,constraints);

		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		panel.add(field,constraints);

		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		panel.add(label,constraints);

		
		constraints.gridx = 2;
		
		constraints.gridy = 0;
		
		constraints.gridwidth = 1;
		
		constraints.gridheight = 2;
		constraints.weightx = 0;
		constraints.weighty = 0;
		panel.add(submitbutton,constraints);

		jTabbedPane.addTab("SerDirtyData",panel);

	}

	public String  getSerDirtyData(File file,String len) {
		try {
			int length = Integer.parseInt(len);
			String oldpath = file.getAbsolutePath();
			String oldfilename = oldpath.substring(oldpath.lastIndexOf(File.separator)+1);
			byte[] filebytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
			ByteArrayOutputStream resultByteArray = new ByteArrayOutputStream();
			resultByteArray.write(filebytes,0,4);

			for (int i = 0; i < length; i++) {
				resultByteArray.write(0x79);
			}
			resultByteArray.write(filebytes,4,filebytes.length-4);



			String newpath = oldpath.substring(0,oldpath.lastIndexOf(File.separator)+1);
			String newfilename = newpath+oldfilename+"_dirty";
			FileOutputStream resultFile = new FileOutputStream(newfilename);

			resultFile.write(resultByteArray.toByteArray());
			resultFile.close();
			return newfilename;

		}catch (Exception e){
			outputTextarea.append(e.getMessage());
		}

		return "";
	}

	public String getbcelencode(byte[] classBytes){
		try {
			return Utils.bcelEncode(classBytes);
		}catch (Exception e){
			outputTextarea.append(e.getMessage());
		}
		return "";

	}

	public void EncodeUIHandler(JTabbedPane jTabbedPane){
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[]{0,0,0,0,0,0,0};
		layout.rowHeights = new int[]{0,0};
		//设置了列的宽度
		layout.columnWeights = new double[]{0.01,0.1,0.1,0.1,0.1,0.1,0.2,0.4};
		//第一行的高度占0.5，第二行占0.5
		layout.rowWeights = new double[]{0.7,0.3};

		Panel panel = new Panel(layout);


		encodeComboBox = getEncodeJComboBox();
		inputJTextArea = getInputJTextArea();
		JLabel label = getLabel1();
		JLabel labe2 = getLabel2();
		JButton inputfilebutton = getSelectFileButton(panel);
		JButton encodeButton = getEncodeButton(panel);
		JButton decodeButton = getDecodeButton(panel);
		jTextFieldOne = getTextFieldOne();
		
//		inputfilebutton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				JFileChooser fileChooser = new JFileChooser();
//				int result = fileChooser.showOpenDialog(panel);
//				if (result == JFileChooser.APPROVE_OPTION) {
//					// 如果点击了"确定", 则获取选择的文件路径
//					File file = fileChooser.getSelectedFile();
////						outputTextarea.append(String.valueOf(Files.readAllBytes(Paths.get(file.getAbsolutePath())).length));
////					outputTextarea.append("打开文件: " + file.getAbsolutePath() + "\n\n");
//					String encodeContent = getbcelencode(file);
//					outputTextarea.setText("");
//					outputTextarea.append(encodeContent+"\n");
//
//				}
//			}
//		});



		GridBagConstraints constraints=new GridBagConstraints();


		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 0, 5, 0);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 4;
		constraints.gridheight = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		panel.add(inputJTextArea,constraints);

		// BOTH使组件完全填充该区域
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 20, 0, 0);

		constraints.gridx = 4;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		panel.add(labe2,constraints);
		constraints.insets = new Insets(0, 0, 0, 0);
		constraints.gridx = 5;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		panel.add(inputfilebutton,constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		panel.add(label,constraints);


		
		constraints.gridx =1;
		
		constraints.gridy = 1;
		
		constraints.gridwidth = 1;
		
		constraints.gridheight = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		panel.add(encodeComboBox,constraints);

		
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		panel.add(encodeButton,constraints);
		
		
		constraints.gridx = 3;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		panel.add(decodeButton,constraints);

		constraints.gridx = 5;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		panel.add(jTextFieldOne,constraints);



		jTabbedPane.addTab("EncodeUtils",panel);

	}

	public JTextArea getInputJTextArea() {
		JTextArea inputTextarea = new JTextArea();

		// 设置字体大小
		inputTextarea.setFont(UiUtils.getSerifFont());
		// 设置自动换行
		inputTextarea.setLineWrap(true);
		// 设置大小
		Dimension preferredSize = new Dimension(1000,300);
		inputTextarea.setPreferredSize(preferredSize);

		return inputTextarea;
	}
	public JTextField getTextFieldOne() {
		JTextField textField = new JTextField();

		// 设置大小
		Dimension preferredSize = new Dimension(150,30);
		textField.setPreferredSize(preferredSize);
		textField.setSize(10,10);


		UiUtils.setPrompt("可直接使用文件绝对路径",textField);
		return textField;
	}


	public JButton getEncodeButton(Panel panel){
		JButton button = new JButton();
		button.setText("编码");
		button.setAlignmentX(0.0f);

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					JavaToolHandler.EncType option = JavaToolHandler.EncType.valueOf(encodeComboBox.getSelectedItem().toString());
					byte[] content;
					if (!jTextFieldOne.getText().trim().equals("")){
						outputTextarea.append("使用文件 "+jTextFieldOne.getText());
						content = Files.readAllBytes(Paths.get(new File(jTextFieldOne.getText()).getAbsolutePath()));
					}else{
						if (inputJTextArea.getText().equals("")){
							outputTextarea.append("输入内容或文件绝对路径为空");
							return;
						}else{
							content = inputJTextArea.getText().getBytes();
						}
					}
					String result = null;
					switch (option){
						case BCEL:
							if (content.length!=0){
								result = getbcelencode(content);
							}
							break;
						case Gzip:
							byte[] gzip = Utils.gzipE(content);
							result = getFileChooseAndWriteFile(panel,gzip);
							break;
						default:
							result = "不支持的编码类型";
							break;
					}
					outputTextarea.setText(result);
				}catch (Exception p){
					p.printStackTrace();
					try {
						outputTextarea.setText(p.toString());
						callbacks.getStderr().write(p.toString().getBytes());
					} catch (IOException ex) {

					}
				}

			}
		});

		return button;
	}



	public JButton getSelectFileButton(Panel panel){
		JButton button = new JButton();
		button.setText("选择文件进行编解码");
		button.setAlignmentX(0.0f);

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(panel);
				if (result == JFileChooser.APPROVE_OPTION) {
//					 如果点击了"确定", 则获取选择的文件路径
					File file = fileChooser.getSelectedFile();
					jTextFieldOne.setText(file.getAbsolutePath());
					outputTextarea.append("打开文件: " + file.getAbsolutePath() + "\n\n");
				}
			}
		});

		return button;
	}

	public JButton getDecodeButton(Panel panel){
		JButton decodeButton = new JButton();
		decodeButton.setText("解码");
		decodeButton.setAlignmentX(0.0f);

		decodeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					JavaToolHandler.EncType option = JavaToolHandler.EncType.valueOf(encodeComboBox.getSelectedItem().toString());
					byte[] content;
					if (!jTextFieldOne.getText().trim().equals("")){
						outputTextarea.append("使用文件 "+jTextFieldOne.getText());
						content = Files.readAllBytes(Paths.get(new File(jTextFieldOne.getText()).getAbsolutePath()));
					}else{
						if (inputJTextArea.getText().equals("")){
							outputTextarea.append("[-] 输入内容或文件绝对路径为空");
							return;
						}else{
							content = inputJTextArea.getText().getBytes();
						}
					}
					String result = null;
					switch (option){
						case BCEL:
							if (content.length!=0){
								result = getFileChooseAndWriteFile(panel,Utils.bcelDecode(new String(content)));
							}
							break;
						case Gzip:
							byte[] gzip = Utils.gzipD(content);
							result = getFileChooseAndWriteFile(panel,gzip);
						case SmallCLassFile:
							content = Utils.smallClassFile(content);
							result = getFileChooseAndWriteFile(panel,content);
							break;
						default:
							break;
					}
					outputTextarea.setText(result);
				}catch (Exception p){
					p.printStackTrace();
					try {
						outputTextarea.setText(p.toString());
						callbacks.getStderr().write(p.toString().getBytes());
					} catch (IOException ex) {

					}
				}

			}
		});
		
		return decodeButton;
	}



	public File getFileChoose(Panel panel){
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showSaveDialog(panel);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				// 如果点击了"确定", 则获取选择的保存文件路径
				File file = fileChooser.getSelectedFile();
				return file;
			} catch (Exception ex) {
				outputTextarea.append(ex.getMessage()+"\n");
				ex.printStackTrace();
			}
		}
		return null;
	}


	public String getFileChooseAndWriteFile(Panel panel,byte[] bytes){
		File file = getFileChoose(panel);
		try {
			if (file!=null){
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				fileOutputStream.write(bytes);
				String result =  "保存文件到："+file.getAbsolutePath()+"\n";
				fileOutputStream.close();
				return result;
			}else {
				return "文件选择失败";
			}
		}catch (Exception e){
			return e.getMessage();
		}
	}

	@Override
	public String getTabName() {
		return tabname;
	}

	public JLabel getLabel1(){
		JLabel label = new JLabel();
		label.setText("编码方式：");
//		button.setText("解密");
//		button.setAlignmentX(0.0f);
		//设置按钮尺寸
//		Dimension preferredSize = new Dimension(100,50);
//		button.setPreferredSize(preferredSize);


		return label;
	}
	public JLabel getLabel2(){
		JLabel label = new JLabel();
		label.setText("或者");
//		button.setText("解密");
//		button.setAlignmentX(0.0f);
		//设置按钮尺寸
//		Dimension preferredSize = new Dimension(100,50);
//		button.setPreferredSize(preferredSize);


		return label;
	}

	public enum EncType
	{
		BCEL,
		Gzip,
		SmallCLassFile,
	}
}
