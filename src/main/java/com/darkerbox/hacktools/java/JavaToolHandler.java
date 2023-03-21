package com.darkerbox.hacktools.java;

import burp.IBurpExtenderCallbacks;
import com.darkerbox.hacktools.UIHandler;
import com.darkerbox.utils.UiUtils;
import com.darkerbox.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private JTextArea outputTextarea;
	@Override
	public void init() {

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

		// 序列化文件脏数据生成功能
		serDirtyDataUIHandler(jTabbedPane);
		// BCEL编码解码
		bcelEncodeUIHandler(jTabbedPane);

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
		inputfilebutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(panel);
				if (result == JFileChooser.APPROVE_OPTION) {
					// 如果点击了"确定", 则获取选择的文件路径
					File file = fileChooser.getSelectedFile();
					label.setText(file.getAbsolutePath());
					outputTextarea.append("打开文件: " + file.getAbsolutePath() + "\n\n");

				}
			}
		});

		JButton submitbutton = new JButton("提交");
		submitbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String filepath = getSerDirtyData(new File(label.getText()),field.getText());
				outputTextarea.append("结果保存到："+filepath);
			}
		});

		GridBagConstraints constraints=new GridBagConstraints();
		// BOTH使组件完全填充该区域
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 0, 0, 0);
		// 组件起始x坐标
		constraints.gridx = 0;
		// 组件起始y坐标
		constraints.gridy = 0;
		// 组件宽占用的1个格子
		constraints.gridwidth = 1;
		// 组件高占用的2个格子
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

		// 组件起始x坐标
		constraints.gridx = 2;
		// 组件起始y坐标
		constraints.gridy = 0;
		// 组件宽占用的1个格子
		constraints.gridwidth = 1;
		// 组件高占用的2个格子
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

	public String getbcelencode(File file){
		try {
			return Utils.bcelEncode(Utils.readAllBytes(file.getAbsolutePath()));
		}catch (Exception e){
			outputTextarea.append(e.getMessage());
		}
		return "";

	}

	public void bcelEncodeUIHandler(JTabbedPane jTabbedPane){
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[]{0,0};
		layout.rowHeights = new int[]{0,0};
		//设置了列的宽度
		layout.columnWeights = new double[]{0.4,0.6};
		//第一行的高度占0.5，第二行占0.5
		layout.rowWeights = new double[]{0.7,0.3};
		Panel panel = new Panel(layout);

		JButton inputfilebutton = new JButton("选择class文件直接进行编码");

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
					String encodeContent = getbcelencode(file);
					outputTextarea.setText("");
					outputTextarea.append(encodeContent+"\n");

				}
			}
		});

		GridBagConstraints constraints=new GridBagConstraints();
		// BOTH使组件完全填充该区域
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 0, 0, 0);
		// 组件起始x坐标
		constraints.gridx = 0;
		// 组件起始y坐标
		constraints.gridy = 0;
		// 组件宽占用的1个格子
		constraints.gridwidth = 1;
		// 组件高占用的2个格子
		constraints.gridheight = 2;
		constraints.weightx = 0;
		constraints.weighty = 0;
		panel.add(inputfilebutton,constraints);


		JTextArea bceltextarea = new JTextArea();
		bceltextarea.setLineWrap(true);
		// 组件起始x坐标
		constraints.gridx = 1;
		// 组件起始y坐标
		constraints.gridy = 0;
		// 组件宽占用的1个格子
		constraints.gridwidth = 1;
		// 组件高占用的2个格子
		constraints.gridheight = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		panel.add(new JScrollPane(bceltextarea),constraints);

		JButton decodeButton = new JButton("Bcel解码");
		decodeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showSaveDialog(panel);
				if (result == JFileChooser.APPROVE_OPTION) {
					try {
						// 如果点击了"确定", 则获取选择的保存文件路径
						File file = fileChooser.getSelectedFile();
						byte[] bcelDecodeContent = Utils.bcelDecode(bceltextarea.getText());
						FileOutputStream fileOutputStream = new FileOutputStream(file);
						fileOutputStream.write(bcelDecodeContent);
						fileOutputStream.close();
						outputTextarea.append("保存文件到："+file.getAbsolutePath()+"\n");
					} catch (Exception ex) {
						outputTextarea.append(ex.getMessage()+"\n");
						ex.printStackTrace();
					}

				}
			}
		});
		// 组件起始x坐标
		constraints.gridx = 1;
		// 组件起始y坐标
		constraints.gridy = 1;
		// 组件宽占用的1个格子
		constraints.gridwidth = 1;
		// 组件高占用的2个格子
		constraints.gridheight = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		panel.add(decodeButton,constraints);



		jTabbedPane.addTab("BCEL",panel);

	}



	@Override
	public String getTabName() {
		return tabname;
	}
}
